package com.qingyii.hxtz.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingle.widget.ShapeLoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.CircleReportWebActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.httpway.TSQUpload;
import com.qingyii.hxtz.pojo.Associates;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;
import com.qingyii.hxtz.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by XRJ on 16/9/19.
 */
public class ShiGuangZhouAdapter extends BaseAdapter {

    private AppCompatActivity mActivity;
    private ShapeLoadingDialog shapeLoadingDialog;
    private List<Associates.DataBean.DocsBean> aDataDocsBeen;
    private Handler handler;

    private Dialog dialog;
    private TSQUpload tsqUpload = TSQUpload.getTSQUpload();

    //年月日选择器
    private Calendar calendar = Calendar.getInstance();
    //时间
    final int YEAR = calendar.get(Calendar.YEAR), MONTH = calendar.get(Calendar.MONTH) + 1;


    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public ShiGuangZhouAdapter(AppCompatActivity mActivity, ShapeLoadingDialog shapeLoadingDialog,
                               List<Associates.DataBean.DocsBean> aDataDocsBeen, Handler handler) {
        this.mActivity = mActivity;
        this.shapeLoadingDialog = shapeLoadingDialog;
        this.aDataDocsBeen = aDataDocsBeen;
        this.handler = handler;
        this.dialog = new Dialog(mActivity, R.style.DialogStyle);
    }

    @Override
    public int getCount() {
        return aDataDocsBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return aDataDocsBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mActivity, R.layout.item_shiguangzhou_main, null);
            holder = new ViewHolder();
            holder.textCategory = (TextView) convertView.findViewById(R.id.circle_main_category);
            holder.textContent = (TextView) convertView.findViewById(R.id.circle_main_content);
            holder.textTime = (TextView) convertView.findViewById(R.id.circle_main_time);
            holder.textLike = (TextView) convertView.findViewById(R.id.circle_main_like);

            holder.textDelete = (TextView) convertView.findViewById(R.id.circle_main_delete);
            holder.textReport = (TextView) convertView.findViewById(R.id.circle_main_report);
            holder.textComment = (TextView) convertView.findViewById(R.id.circle_main_comment);

            holder.gridPhoto = (ImageView) convertView.findViewById(R.id.circle_main_photo);
            holder.circle_main_photo_num = (TextView) convertView.findViewById(R.id.circle_main_photo_num);
            setDrawLeft(new Rect(0, 0, 60, 60), R.drawable.circle_love_selector_style, holder.textLike);
            setDrawLeft(new Rect(0, 0, 60, 60), R.mipmap.ic_circle_comment, holder.textComment);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        final Associates.DataBean.DocsBean aDocsBean = aDataDocsBeen.get(position);

        //判断是否打开删除按钮
        if (aDocsBean.getUser_id() == MyApplication.userUtil.getId() && (DateUtils.getStringToLong2Date(aDocsBean.getCreated_at()) > DateUtils.getStringToShortDate(YEAR + "-" + MONTH))) {
            holder.textDelete.setVisibility(View.VISIBLE);
            holder.textReport.setVisibility(View.GONE);
        } else {
            holder.textDelete.setVisibility(View.GONE);
            holder.textReport.setVisibility(View.VISIBLE);
        }

        holder.textContent.setText("" + aDocsBean.getContent());
//        holder.textTime.setText("" + AbDateUtil.getStringByFormat(info.getCreatetime(), AbDateUtil.dateFormatMD + " " + AbDateUtil.dateFormatHMS));

        Date d = new Date(DateUtils.getStringToLong2Date(aDocsBean.getCreated_at()));
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日");

        holder.textTime.setText(sf.format(d) + "");
        holder.textLike.setText("" + aDocsBean.getUpvote());
        holder.textComment.setText("" + aDocsBean.getComments().size());
        holder.textCategory.setText("类型：" + aDocsBean.getDoctypename());
//        holder.textComment.setText("" + aDocsBean.getCmtnums());

        if (aDocsBean.getIs_upvote() == 1)
            holder.textLike.setSelected(true);
        else
            holder.textLike.setSelected(false);

        holder.textLike.setText(aDocsBean.getUpvote() + "");

        //点赞 点击事件
        holder.textLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog = new Dialog(mActivity, R.style.DialogStyle);
                if (aDocsBean.getIs_upvote() == 0) {
                    View menuContentLayout = View.inflate(mActivity, R.layout.neikan_dialog, null);
                    TextView neikan_dialog_context = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_context);
                    TextView neikan_dialog_l = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_l);
                    TextView neikan_dialog_r = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_r);
                    neikan_dialog_context.setVisibility(View.VISIBLE);
                    neikan_dialog_context.setText("确定要点赞吗");
                    neikan_dialog_l.setText("取消");
                    neikan_dialog_r.setText("点赞");
                    neikan_dialog_l.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    neikan_dialog_r.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tsqUpload.associatesLike(mActivity, aDataDocsBeen.get(position), dialog, holder.textLike, handler);
                            shapeLoadingDialog.setLoadingText("正在戳对方,请等一小会");
                            shapeLoadingDialog.show();
                        }
                    });

                    dialog.setContentView(menuContentLayout);
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show();
                }

            }
        });

        //内容图片（暂时未出接口）
        Log.e("图片张数", aDocsBean.getPicture().size() + "");
        holder.circle_main_photo_num.setText("共" + aDocsBean.getPicture().size() + "张");
        if (aDocsBean.getPicture().size() > 0) {
            ImageLoader.getInstance().displayImage(aDocsBean.getPicture().get(0).getUri(), holder.gridPhoto, MyApplication.options, animateFirstListener);
            holder.gridPhoto.setBackgroundResource(R.color.white);
        } else {
            holder.gridPhoto.setBackgroundResource(R.mipmap.guan_li_null_image);
        }

        //删除点击事件
        holder.textDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                dialog = new Dialog(mActivity, R.style.DialogStyle);

                View menuContentLayout = View.inflate(mActivity, R.layout.neikan_dialog, null);
                TextView neikan_dialog_context = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_context);
                TextView neikan_dialog_l = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_l);
                TextView neikan_dialog_r = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_r);
                neikan_dialog_context.setVisibility(View.VISIBLE);
                neikan_dialog_context.setText("确定要删除吗");
                neikan_dialog_l.setText("取消");
                neikan_dialog_r.setText("删除");
                neikan_dialog_l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                neikan_dialog_r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tsqUpload.associatesDelete(mActivity, aDataDocsBeen, position, dialog, handler);
                        shapeLoadingDialog.setLoadingText("请求中,请等一小会");
                        shapeLoadingDialog.show();
                    }
                });

                dialog.setContentView(menuContentLayout);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();

            }
        });

        //举报跳转事件
        holder.textReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mActivity, CircleReportWebActivity.class);
                intent.putExtra("DynamicInfo", aDocsBean);
//                Comment comment = null;
//                intent.putExtra("Comment", comment);
                mActivity.startActivity(intent);
            }
        });

        //评论按钮 点击事件
//        holder.textComment.setOnClickListener(new NotifyView.OnClickListener() {
//            @Override
//            public void onClick(NotifyView view) {
////                dialog = new Dialog(mActivity, R.style.DialogStyle);
//
//                NotifyListView importContentLayout = NotifyListView.inflate(mActivity, R.layout.import_dialog, null);
//                final EditText import_edit = (EditText) importContentLayout.findViewById(R.id.import_edit);
//                TextView import_send = (TextView) importContentLayout.findViewById(R.id.import_send);
//                dialog.setContentView(importContentLayout);
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
//                dialog.getWindow().getAttributes().width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
//                dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
//                dialog.show();
//
//                import_send.setOnClickListener(new NotifyView.OnClickListener() {
//                    @Override
//                    public void onClick(NotifyView view) {
//                        if (!import_edit.getText().toString().equals(""))
//                            tsqUpload.associatesComment(mActivity, aDocsBean, -1, import_edit.getText().toString(), dialog, handler);
//                        else
//                            Toast.makeText(mActivity, "内容不能为空", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });

        return convertView;
    }

    class ViewHolder {
        TextView textCategory;
        TextView textContent;
        ImageView gridPhoto;
        TextView circle_main_photo_num;
        TextView textTime;
        TextView textLike;
        TextView textReport;
        TextView textComment;
        TextView textDelete;
    }
    //动态设置drawableRight大小
    private void setDrawLeft(Rect bounds, int resId, TextView... view) {
        for (TextView textView : view) {
            Drawable drawable = mActivity.getResources().getDrawable(resId);
            drawable.setBounds(bounds);
            textView.setCompoundDrawables(drawable, null, null, null);
        }
    }
}