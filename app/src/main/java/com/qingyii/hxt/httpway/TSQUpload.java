package com.qingyii.hxt.httpway;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxt.bean.Comment_circle;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.Associates;
import com.qingyii.hxt.pojo.HandleParameter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by XRJ on 16/9/7.
 */

public class TSQUpload {

    private static TSQUpload tsqUpload = new TSQUpload();

    private TSQUpload() {
    }

    public static TSQUpload getTSQUpload() {
        return tsqUpload;
    }


    public abstract class CommentCallback extends Callback<Comment_circle> {

        @Override
        public Comment_circle parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("Comment_circle", result);
            Comment_circle mComment_circle = new Gson().fromJson(result, Comment_circle.class);
            return mComment_circle;
        }


    }

    /**
     * 同事圈评论
     *
     * @param activity
     * @param aDocsBean
     * @param context
     * @param dialog
     * @param handler
     */
    public void associatesComment(final Activity activity, final Associates.DataBean.DocsBean aDocsBean,
                                  final int position, final String context, final Dialog dialog, final Handler handler) {

        Log.e("工作圈ID", aDocsBean.getId() + "");
        Log.e("工作圈评论context", context);
        String parent_id = "";
        if (position > -1)
            parent_id = aDocsBean.getComments().get(position).getId() + "";
        Log.e("工作圈父评论ID", parent_id);

        OkHttpUtils
                .post()
//                .url(XrjHttpClient.getAssociatesCommentUrl())
                .url(XrjHttpClient.getCommentUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doc_id", aDocsBean.getId() + "")
                .addParams("content", context)
                .addParams("parent_id", parent_id)
                .build()
                .execute(new CommentCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Comment_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(Comment_circle response, int id) {
                                 Log.e("Comment_circle", response.getError_msg());

                                 Comment_circle.DataBean cDataBean = response.getData();
                                 switch (response.getError_code()) {
                                     case 0:
                                         List<Associates.DataBean.DocsBean.CommentsBean> aCommentsBean = aDocsBean.getComments();
                                         //如果评论为空，则新建评论类
                                         if (aCommentsBean == null)
                                             aCommentsBean = new ArrayList<Associates.DataBean.DocsBean.CommentsBean>();
                                         //将评论内容添加到缓存 以便展示
                                         Associates.DataBean.DocsBean.CommentsBean commentsBean = new Associates.DataBean.DocsBean.CommentsBean();
                                         commentsBean.setId(cDataBean.getId());
                                         commentsBean.setAuthor(cDataBean.getAuthor());
                                         commentsBean.setContent(cDataBean.getContent());
                                         commentsBean.setUpvote(0);
                                         commentsBean.setDownvote(0);
                                         commentsBean.setParent_id(cDataBean.getParent_id());
                                         commentsBean.setParent_author(cDataBean.getParent_author());
                                         commentsBean.setCreated_at(cDataBean.getCreated_at());
                                         if (position > -1) {
                                             commentsBean.setParent_author(aDocsBean.getComments().get(position).getAuthor());
                                             aCommentsBean.add(position + 1, commentsBean);
                                         } else
                                             aCommentsBean.add(commentsBean);

                                         handler.sendEmptyMessage(1);
                                         Toast.makeText(activity, "评论成功", Toast.LENGTH_LONG).show();
                                         if (dialog != null)
                                             dialog.dismiss();
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         handler.sendEmptyMessage(1);
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 同事圈删除
     *
     * @param activity
     * @param aDataDocsBeen
     * @param position
     * @param dialog
     * @param handler
     */
    public void associatesDelete(final Activity activity, final List<Associates.DataBean.DocsBean> aDataDocsBeen,
                                 final int position, final Dialog dialog, final Handler handler) {


        Log.e("Delete_URL", XrjHttpClient.getAssociatesUrl() + "/" + aDataDocsBeen.get(position).getId() + "/delete");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getAssociatesUrl() + "/" + aDataDocsBeen.get(position).getId() + "/delete")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Delete_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请检查后重试！", Toast.LENGTH_LONG).show();
                                 handler.sendEmptyMessage(3);
                                 dialog.dismiss();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("DeleteCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "删除成功", Toast.LENGTH_LONG).show();
                                         aDataDocsBeen.remove(position);
                                         handler.sendEmptyMessage(3);
                                         dialog.dismiss();
                                         break;
                                     default:
                                         Toast.makeText(activity, "删除失败", Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 同事圈点赞
     *
     * @param activity
     * @param aDocsBeen
     * @param dialog
     * @param handler
     */
    public void associatesLike(final Activity activity, final Associates.DataBean.DocsBean aDocsBeen,
                               final Dialog dialog, final TextView textLike, final Handler handler) {


        OkHttpUtils
                .post()
                .url(XrjHttpClient.getUpvoteUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doc_id", aDocsBeen.getId() + "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Like_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请检查后重试！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("LikeCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "点赞成功", Toast.LENGTH_LONG).show();
//                                         textLike.setText(aDataDocsBeen.get(position).getUpvote()+1+"");
//                                         textLike.setSelected(true);
                                         aDocsBeen.setUpvote(aDocsBeen.getUpvote() + 1);
                                         aDocsBeen.setIs_upvote(1);
                                         textLike.setSelected(true);
                                         dialog.dismiss();
                                         handler.sendEmptyMessage(2);
                                         break;
                                     default:
                                         Toast.makeText(activity, "点赞失败", Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 同事圈举报
     *
     * @param activity
     * @param aDocsBeen
     * @param content
     * @param mAnony
     */
    public void associatesReport(final Activity activity, final Associates.DataBean.DocsBean aDocsBeen, String content, boolean mAnony) {
        int anonymous = 0;
        if (mAnony)
            anonymous = 1;

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getUploadReportUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doc_id", aDocsBeen.getId() + "")
                .addParams("content", content + "")
                .addParams("anonymous", anonymous + "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Report_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请检查后重试！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("ReportCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "已举报", Toast.LENGTH_LONG).show();
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }
}
