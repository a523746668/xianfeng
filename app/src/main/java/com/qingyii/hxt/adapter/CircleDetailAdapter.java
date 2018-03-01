package com.qingyii.hxt.adapter;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.R;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.pojo.Associates;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;

import java.util.List;

/**
 * 朋友圈详情评论适配器
 */
public class CircleDetailAdapter extends BaseAdapter {

    private AbBaseActivity abBaseActivity;
    private List<Associates.DataBean.DocsBean.CommentsBean> mList;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public CircleDetailAdapter(AbBaseActivity abBaseActivity, List<Associates.DataBean.DocsBean.CommentsBean> list) {
        this.abBaseActivity = abBaseActivity;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(abBaseActivity, R.layout.item_circle_detail, null);
            holder = new ViewHolder();
            //holder.imgHead = (ImageView) convertView.findViewById(R.id.item_circle_detail_header);
            holder.textName = (TextView) convertView.findViewById(R.id.item_circle_detail_name);
            holder.textContent = (TextView) convertView.findViewById(R.id.item_circle_detail_content);
//            holder.textDepartment = (TextView) convertView.findViewById(R.id.item_circle_detail_department);
//            holder.textDelete = (TextView) convertView.findViewById(R.id.item_circle_detail_delete);
//            holder.textReport = (TextView) convertView.findViewById(R.id.item_circle_detail_report);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        final Associates.DataBean.DocsBean.CommentsBean aCommentsBean = mList.get(position);
//        User createUser = info.getCreateuser();
//        if (createUser.getUserid().equals(CacheUtil.user.getUserid())) {
//            holder.textDelete.setVisibility(NotifyView.VISIBLE);
//        } else {
//            holder.textDelete.setVisibility(NotifyView.GONE);
//        }

//        ImageLoader.getInstance().displayImage(aCommentsBean.getAvatar(), imgHead, MyApplication.options, animateFirstListener);
//        ImageLoader.getInstance().displayImage(
//                HttpUrlConfig.photoDir + createUser.getPicaddress(),
//                holder.imgHead);
        holder.textName.setText("" + aCommentsBean.getAuthor());
        holder.textContent.setText(getCommentContent(aCommentsBean));
//        holder.textDepartment.setText("" + createUser.getDepname());
//        holder.textReport.setOnClickListener(new NotifyView.OnClickListener() {
//            @Override
//            public void onClick(NotifyView arg0) {
//                Intent intent = new Intent(abBaseActivity, CircleReportActivity.class);
////                intent.putExtra("DynamicInfo", mInfo);
//                intent.putExtra("Comment", aCommentsBean);
//                abBaseActivity.startActivity(intent);
//            }
//        });
//        holder.textDelete.setOnClickListener(new NotifyView.OnClickListener() {
//            @Override
//            public void onClick(NotifyView arg0) {
//                AlertDialog dialog = new AlertDialog.Builder(
//                        abBaseActivity)
//                        .setMessage("确定要删除？")
//                        .setPositiveButton("确定",
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        /**
//                                         * Load失效修改为Progress
//                                         *
//                                         * 监听失效
//                                         */
////                                            mDialogFragment = AbDialogUtil
////                                                    .showLoadDialog(
////                                                            CircleDetailActivity.this,
////                                                            R.mipmap.ic_load,
////                                                            "删除中,请等一小会");
////                                            mDialogFragment
////                                                    .setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
////                                                        @Override
////                                                        public void onLoad() {
////                                                            reqDelComment(info);
////                                                        }
////                                                    });
////                                            mDialogFragment = AbDialogUtil
////                                                    .showProgressDialog(
////                                                            CircleDetailActivity.this,
////                                                            R.mipmap.ic_load,
////                                                            "删除中,请等一小会");
//                                        shapeLoadingDialog.setLoadingText("删除中,请等一小会");
//                                        shapeLoadingDialog.show();
//                                    }
//                                })
//                        .setNegativeButton("取消",
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(
//                                            DialogInterface arg0, int arg1) {
//
//                                    }
//                                }).show();
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        //ImageView imgHead;
        TextView textName;
        TextView textContent;
//        TextView textDepartment;
//        TextView textDelete;
//        TextView textReport;
    }

    private Spannable getCommentContent(Associates.DataBean.DocsBean.CommentsBean aCommentsBean) {
        String author = "";//创建人
        String parent_author = aCommentsBean.getParent_author();//被回复人 可以为空
        String content = aCommentsBean.getContent();
//        if (ref != null && ref.getCreateuser() != null) {
//            author += " 回复 " + ref.getCreateuser().getUsername();
//        }
        if (!parent_author.equals("")) {
            author += "回复 " + parent_author + "：";
        }
        int end = author.length();
        author += content;
        return getColorSpannable(Color.parseColor("#5F84A9"), Color.BLACK,
                author, 0, end);
    }

    /**
     * 设置textview不同的颜色
     *
     * @param str
     */
    public Spannable getColorSpannable(int color, int colorDefault, String str, int begin, int end) {
        Spannable wordToSpan = new SpannableString(str);
        wordToSpan.setSpan(new ForegroundColorSpan(colorDefault), 0, begin, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordToSpan.setSpan(new ForegroundColorSpan(color), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (end < wordToSpan.length()) {
            wordToSpan.setSpan(new ForegroundColorSpan(colorDefault), end,
                    wordToSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return wordToSpan;
    }

}