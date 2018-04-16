package com.qingyii.hxtz.circle;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andbase.library.cache.image.AbImageCache;
import com.andbase.library.cache.image.AbImageCacheImpl;
import com.andbase.library.image.AbImageLoader;
import com.andbase.library.util.AbFileUtil;
import com.andbase.library.util.AbImageUtil;
import com.andbase.library.util.AbStrUtil;
import com.qingyii.hxtz.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import com.ab.image.AbImageCache;

/**
 * 创建动态图片显示
 *
 * @author Lee
 */
public class CircleAlbumAdapter extends RecyclerView.Adapter<ViewHolder> {

    public static final int TYPE_PRIVATE = 0x01;
    public static final int TYPE_PUBLIC = 0x02;

    private Activity activity;
    private ArrayList<String> mPhotos;
    private List<Integer> ImagesID;

    private AbImageLoader mAbImageLoader = null;
    private AbImageCache abImageCache = null;

    //弹窗
    private Dialog dialog;

    private Callback mCallback;

    /**
     * 选中item的回调
     *
     * @author lijiang
     */
    public interface Callback {
        public void onItemClick(int position);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public CircleAlbumAdapter(Activity activity, List<Integer> ImagesID, ArrayList<String> photos) {
        this.activity = activity;
        this.ImagesID = ImagesID;
        this.mPhotos = photos;

        //弹窗设置
        dialog = new Dialog(activity, R.style.ActionSheetDialogStyle);
        mAbImageLoader = new AbImageLoader(activity);
    }

    @Override
    public int getItemCount() {
        int size = mPhotos.size();
        return size;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup arg0, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_circle_album, arg0, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NormalViewHolder tempHolder = (NormalViewHolder) holder;
        tempHolder.mImgView.setImageBitmap(null);

        String imagePath = mPhotos.get(position);
        if (position > 0)
            tempHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    View affirmContentLayout = View.inflate(activity, R.layout.user_context_affirm_menu, null);
                    TextView affirm_context = (TextView) affirmContentLayout.findViewById(R.id.affirm_context);
                    TextView affirm_cancel = (TextView) affirmContentLayout.findViewById(R.id.affirm_cancel);
                    TextView affirm_submit = (TextView) affirmContentLayout.findViewById(R.id.affirm_submit);
                    affirm_context.setText("是否删除图片？");
                    affirm_cancel.setText("否");
                    affirm_submit.setText("是");
                    affirm_cancel.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    affirm_submit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPhotos.remove(position);
                            ImagesID.remove(position - 1);
                            CircleAlbumAdapter.this.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                    //将布局设置给Dialog
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    } else {
                        dialog.setContentView(affirmContentLayout);
//                            dialog.getWindow().setGravity(Gravity.BOTTOM);
                        //获得屏幕看都，并传给dialog
                        dialog.getWindow().getAttributes().width = (int) (activity.getWindowManager().getDefaultDisplay().getWidth() * 0.8);
                        dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                        dialog.show();
                    }

                    return false;
                }
            });

        if (!AbStrUtil.isEmpty(imagePath)) {
            /**
             * AbImageCache变为接口 修改为将AbImageCacheImpl注入
             */
            //从缓存中获取图片，很重要否则会导致页面闪动
//            Bitmap bitmap = AbImageCache.getInstance().getBitmap(imagePath);
            if (abImageCache == null)
                abImageCache = new AbImageCacheImpl(this.activity);
            Bitmap bitmap = abImageCache.getBitmap(imagePath);

            //缓存中没有则从网络和SD卡获取
            if (bitmap == null) {
                tempHolder.mImgView.setImageResource(R.drawable.image_loading);
                if (imagePath.indexOf("http://") != -1) {
                    //图片的下载
                    mAbImageLoader.display(tempHolder.mImgView, imagePath);

                } else if (imagePath.indexOf("/") == -1) {
                    //索引图片
                    try {
                        int res = Integer.parseInt(imagePath);
                        tempHolder.mImgView.setImageDrawable(activity.getResources().getDrawable(res));
                    } catch (Exception e) {
                        tempHolder.mImgView.setImageResource(R.drawable.image_error);
                    }
                } else {
                    Bitmap mBitmap = AbFileUtil.getBitmapFromSD(new File(imagePath), AbImageUtil.CUTIMG, 300, 300);
                    if (mBitmap != null) {
                        tempHolder.mImgView.setImageBitmap(mBitmap);
                    } else {
                        // 无图片时显示
                        tempHolder.mImgView.setImageResource(R.drawable.image_empty);
                    }
                }
            } else {
                //直接显示
                tempHolder.mImgView.setImageBitmap(bitmap);
            }
        } else {
            // 无图片时显示
            tempHolder.mImgView.setImageResource(R.drawable.image_empty);
        }
        tempHolder.mImgView.setAdjustViewBounds(true);
    }

    public class NormalViewHolder extends ViewHolder {

        SquareImage mImgView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mImgView = (SquareImage) itemView.findViewById(R.id.item_circle_album_image);
            mImgView.setImageResource(R.mipmap.ic_launcher);
            if (mCallback != null) {
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        mCallback.onItemClick(getPosition());
                    }
                });
            }
        }
    }

}
