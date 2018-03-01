package com.qingyii.hxt.adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxt.R;
import com.qingyii.hxt.httpway.TSQUpload;
import com.qingyii.hxt.pojo.Associates;

import java.util.List;

/**
 * 评论适配器
 */
public class CommentAdapter extends BaseAdapter {

    private AppCompatActivity abBaseActivity;
    private ShapeLoadingDialog shapeLoadingDialog;
    private Associates.DataBean.DocsBean aDocsBean;
    private Handler handler;

    private Dialog dialog;
    private TSQUpload tsqUpload = TSQUpload.getTSQUpload();

    public CommentAdapter(AppCompatActivity abBaseActivity, ShapeLoadingDialog shapeLoadingDialog,
                          Associates.DataBean.DocsBean aDocsBean, Handler handler) {
        //Log.e("评论内容 适配器", "进入成功,列表长度：" + aDocsBean.getComments().size());
        this.abBaseActivity = abBaseActivity;
        this.shapeLoadingDialog = shapeLoadingDialog;
        this.aDocsBean = aDocsBean;
        this.handler = handler;
        this.dialog = new Dialog(abBaseActivity, R.style.DialogStyle);
    }

    @Override
    public int getCount() {
        return aDocsBean.getComments().size();
    }

    @Override
    public Object getItem(int position) {
        return aDocsBean.getComments().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(abBaseActivity, R.layout.circle_main_comment_listview_item, null);
            vh.circle_main_comment_context = (TextView) convertView.findViewById(R.id.circle_main_comment_context);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.circle_main_comment_context.setText(getCommentContent(aDocsBean.getComments().get(position)));
//        Log.e("评论内容", aDocsBean.getComments().get(position).getContent() + "");

        vh.circle_main_comment_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View importContentLayout = View.inflate(abBaseActivity, R.layout.import_dialog, null);
                final EditText import_edit = (EditText) importContentLayout.findViewById(R.id.import_edit);
                //显示 回复对象 name
                import_edit.setHint("回复 "+aDocsBean.getComments().get(position).getAuthor()+"：");
                TextView import_send = (TextView) importContentLayout.findViewById(R.id.import_send);
                dialog.setContentView(importContentLayout);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().getAttributes().width = abBaseActivity.getWindowManager().getDefaultDisplay().getWidth();
                dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                dialog.show();

                import_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!import_edit.getText().toString().equals(""))
                            tsqUpload.associatesComment(abBaseActivity, aDocsBean,
                                    position, import_edit.getText().toString(), dialog, handler);
                        else
                            Toast.makeText(abBaseActivity, "内容不能为空", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView circle_main_comment_context;
    }

    private Spannable getCommentContent(Associates.DataBean.DocsBean.CommentsBean aCommentsBean) {
        String author = aCommentsBean.getAuthor();//创建人
        String parent_author = aCommentsBean.getParent_author();//被回复人 可以为空
        String content = aCommentsBean.getContent();
//        if (ref != null && ref.getCreateuser() != null) {
//            author += " 回复 " + ref.getCreateuser().getUsername();
//        }
        if (!parent_author.equals("")) {
            author += " 回复 " + parent_author;
        }
        int end = author.length();
        author += "：" + content;
        return getColorSpannable(Color.parseColor("#5F84A9"), Color.BLACK,
                author, 0, end + 1/* 冒号 */);
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
