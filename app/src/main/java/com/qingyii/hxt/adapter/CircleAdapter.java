package com.qingyii.hxt.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingle.widget.ShapeLoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.CircleReportWebActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.circle.BigPhotoFragment;
import com.qingyii.hxt.circle.NonScrollGridView;
import com.qingyii.hxt.circle.PhotoAdapter;
import com.qingyii.hxt.circle.ShiGuangZhou;
import com.qingyii.hxt.customview.MyListView;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.httpway.TSQUpload;
import com.qingyii.hxt.pojo.Associates;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.util.DateUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by XRJ on 16/9/19.
 */
public class CircleAdapter extends BaseAdapter {

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

    public CircleAdapter(AppCompatActivity mActivity, ShapeLoadingDialog shapeLoadingDialog,
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
            convertView = View.inflate(mActivity, R.layout.item_circle_main, null);
            holder = new ViewHolder();
            holder.imgHead = (ImageView) convertView.findViewById(R.id.circle_main_header);
            holder.textCategory = (TextView) convertView.findViewById(R.id.circle_main_category);
            holder.textName = (TextView) convertView.findViewById(R.id.circle_main_name);
            holder.textDepartment = (TextView) convertView.findViewById(R.id.circle_main_department);
            holder.textContent = (TextView) convertView.findViewById(R.id.circle_main_content);
            holder.textTime = (TextView) convertView.findViewById(R.id.circle_main_time);
            holder.textLike = (TextView) convertView.findViewById(R.id.circle_main_like);


            holder.textDelete = (TextView) convertView.findViewById(R.id.circle_main_delete);
            holder.textReport = (TextView) convertView.findViewById(R.id.circle_main_report);
            holder.textComment = (TextView) convertView.findViewById(R.id.circle_main_comment);
            setDrawLeft(new Rect(0, 0, 60, 60), R.drawable.circle_love_selector_style, holder.textLike);
            setDrawLeft(new Rect(0, 0, 60, 60), R.mipmap.ic_circle_comment, holder.textComment);
//            holder.textComment1 = (TextView) convertView.findViewById(R.id.circle_main_comment1);
//            holder.textComment2 = (TextView) convertView.findViewById(R.id.circle_main_comment2);
//            holder.textComment3 = (TextView) convertView.findViewById(R.id.circle_main_comment3);
//            holder.textMore = (TextView) convertView.findViewById(R.id.circle_main_more);
            holder.textReply = (MyListView) convertView.findViewById(R.id.circle_main_comment_listview);
            //这是，避免遮挡ListView Item
            holder.textReply.setPressed(false);
            holder.textReply.setClickable(false);
            holder.textReply.setEnabled(false);


            holder.gridPhoto = (NonScrollGridView) convertView.findViewById(R.id.circle_main_photo);
            holder.photos = new ArrayList<String>();
            holder.photoAdapter = new PhotoAdapter(mActivity, holder.photos);
            //这是，避免遮挡ListView Item
            holder.gridPhoto.setPressed(false);
            holder.gridPhoto.setClickable(false);
            holder.gridPhoto.setEnabled(false);

            holder.gridPhoto.setAdapter(holder.photoAdapter);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else
            holder = (ViewHolder) convertView.getTag();

//        holder.textComment1.setVisibility(NotifyView.GONE);
//        holder.textComment2.setVisibility(NotifyView.GONE);
//        holder.textComment3.setVisibility(NotifyView.GONE);
//        holder.textMore.setVisibility(NotifyView.GONE);
        holder.photos.clear();
        holder.photoAdapter.notifyDataSetChanged();

        final Associates.DataBean.DocsBean aDocsBean = aDataDocsBeen.get(position);
//        final User createUser = info.getCreateuser();
        //评论区域 数据载入
        holder.commentAdapter = new CommentAdapter(mActivity, shapeLoadingDialog, aDataDocsBeen.get(position), handler);
        holder.textReply.setAdapter(holder.commentAdapter);

        //判断是否打开删除按钮
        if (aDocsBean.getUser_id() == MyApplication.userUtil.getId() && (DateUtils.getStringToLong2Date(aDocsBean.getCreated_at()) > DateUtils.getStringToShortDate(YEAR + "-" + MONTH))) {
            holder.textDelete.setVisibility(View.VISIBLE);
            holder.textReport.setVisibility(View.GONE);
        } else {
            holder.textDelete.setVisibility(View.GONE);
            holder.textReport.setVisibility(View.VISIBLE);
        }

        ImageLoader.getInstance().displayImage(aDocsBean.getAvatar(), holder.imgHead, MyApplication.options, animateFirstListener);
//        ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + createUser.getPicaddress(), holder.imgHead);

        holder.textName.setText("" + aDocsBean.getUsername());
        holder.textDepartment.setText("" + aDocsBean.getDepartment());
        holder.textContent.setText("" + aDocsBean.getContent());
//        holder.textTime.setText("" + AbDateUtil.getStringByFormat(info.getCreatetime(), AbDateUtil.dateFormatMD + " " + AbDateUtil.dateFormatHMS));
        holder.textTime.setText(aDocsBean.getCreated_at());
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

        //评论按钮 点击事件
        holder.textComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog = new Dialog(mActivity, R.style.DialogStyle);

                View importContentLayout = View.inflate(mActivity, R.layout.import_dialog, null);
                final EditText import_edit = (EditText) importContentLayout.findViewById(R.id.import_edit);
                TextView import_send = (TextView) importContentLayout.findViewById(R.id.import_send);
                dialog.setContentView(importContentLayout);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().getAttributes().width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
                dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                dialog.show();

                import_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!import_edit.getText().toString().equals(""))
                            tsqUpload.associatesComment(mActivity, aDocsBean, -1, import_edit.getText().toString(), dialog, handler);
                        else
                            Toast.makeText(mActivity, "内容不能为空", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //评论
//        List<Associates.DataBean.DocsBean.CommentsBean> comments = aDocsBean.getComments();
//        Log.e("评论内容 列表", "评论列表长度：" + comments.size());
//        holder.listComments.clear();
//        holder.listComments.addAll(aDocsBean.getComments());
//        holder.commentAdapter.notifyDataSetChanged();

//        if (comments.size() > 3) {
//            holder.textMore.setVisibility(NotifyView.VISIBLE);
//        } else {
//            holder.textMore.setVisibility(NotifyView.GONE);
//        }
//        if (aDocsBean.getComments() != null && aDocsBean.getComments().size() > 0) {
//            int commentLength = comments.size();
//            if (commentLength > 2) {
//                holder.textComment1.setVisibility(NotifyView.VISIBLE);
//                holder.textComment2.setVisibility(NotifyView.VISIBLE);
//                holder.textComment3.setVisibility(NotifyView.VISIBLE);
////                holder.textComment1.setText(getCommentContent(comments[0]));
//                holder.textComment1.setText(comments.get(0).getContent());
//                holder.textComment2.setText(comments.get(1).getContent());
//                holder.textComment3.setText(comments.get(2).getContent());
//            } else if (commentLength > 1) {
//                holder.textComment1.setVisibility(NotifyView.VISIBLE);
//                holder.textComment2.setVisibility(NotifyView.VISIBLE);
//                holder.textComment1.setText(comments.get(0).getContent());
//                holder.textComment2.setText(comments.get(1).getContent());
//            } else {
//                holder.textComment1.setVisibility(NotifyView.VISIBLE);
//                holder.textComment1.setText(comments.get(0).getContent());
//            }
//        }
        //内容图片（暂时未出接口）
        List<Associates.DataBean.DocsBean.PictureBean> picture = aDocsBean.getPicture();
        if (picture != null) {
//            for (String str : images) {
            for (int i = 0; i < picture.size(); i++) {
                holder.photos.add(picture.get(i).getUri());
                if (holder.photos.size() >= 9) {
                    break;
                }
            }
            holder.photoAdapter.notifyDataSetChanged();
        }
        holder.imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mActivity, ShiGuangZhou.class);
                //传用户资料
//                intent.putExtra("User", createUser);
                intent.putExtra("UserId", aDocsBean.getUser_id());
                //System.out.println(aDocsBean.getUser_id()+"-----------");

                intent.putExtra("ShiGuangType", 0);

                mActivity.startActivity(intent);
            }
        });

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
                        shapeLoadingDialog.setLoadingText("上传中,请等一小会");
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
        //图片点击时间
        holder.gridPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                BigPhotoFragment frag = BigPhotoFragment.getInstance(holder.photos, arg2);
                frag.show(mActivity.getSupportFragmentManager(), "BigPhotoFragment");
            }
        });
        return convertView;
    }
    //动态设置drawableRight大小
    private void setDrawLeft(Rect bounds, int resId, TextView... view) {
        for (TextView textView : view) {
            Drawable drawable = mActivity.getResources().getDrawable(resId);
            drawable.setBounds(bounds);
            textView.setCompoundDrawables(drawable, null, null, null);
        }
    }
    class ViewHolder {
        ImageView imgHead;
        TextView textName;
        TextView textDepartment;
        TextView textCategory;
        TextView textContent;
        NonScrollGridView gridPhoto;
        ArrayList<String> photos;
        PhotoAdapter photoAdapter;
        TextView textTime;
        TextView textLike;
        TextView textReport;
        TextView textComment;
        //TextView textComment1;
//        TextView textComment2;
//        TextView textComment3;
//        TextView textMore;
        MyListView textReply;
        CommentAdapter commentAdapter;
        TextView textDelete;
    }
}