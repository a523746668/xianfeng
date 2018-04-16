package com.qingyii.hxtz.httpway;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.ArticleListNK;
import com.qingyii.hxtz.pojo.ArticleNK;
import com.qingyii.hxtz.pojo.AxpectNK;
import com.qingyii.hxtz.pojo.CommentNK;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.pojo.SubscribedNK;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/8/20.
 */
public class NKUpload {

    private static NKUpload nkUpload = new NKUpload();

    private NKUpload() {
    }

    public static NKUpload getNKUpload() {
        return nkUpload;
    }

    /**
     * 已订阅页面加载
     *
     * @param activity
     * @param baseAdapter
     * @param list
     * @param handler
     */
    public void NKSubscribed(final Activity activity, final BaseAdapter baseAdapter,
                             final List<SubscribedNK.DataBean> list, final Handler handler) {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getNKSubscribedUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new SubscribedNKCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("NKSubscribed_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                                 handler.sendEmptyMessage(1);

                             }

                             @Override
                             public void onResponse(SubscribedNK response, int id) {
                                 Log.e("SubscribedNKCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         list.clear();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
//                                         handler.sendEmptyMessage(2);
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 订阅页面加载
     *
     * @param activity
     * @param baseAdapter
     * @param list
     * @param handler
     */
    public void NKUnSubscribed(final Activity activity, final BaseAdapter baseAdapter, final int directionID,
                               final List<SubscribedNK.DataBean> list, final Handler handler) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getNKUnSubscribedUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("id", directionID + "")
                .addParams("direction", "lt")
//                .addParams("direction","it")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new SubscribedNKCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("NKSubscribed_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                                 handler.sendEmptyMessage(1);

                             }

                             @Override
                             public void onResponse(SubscribedNK response, int id) {
                                 Log.e("SubscribedNKCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (directionID == 0)
                                             list.clear();
                                         if (response.getData().size() == 0)
                                             Toast.makeText(activity, "没有更多了", Toast.LENGTH_SHORT).show();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         handler.sendEmptyMessage(2);
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class SubscribedNKCallback extends Callback<SubscribedNK> {

        @Override
        public SubscribedNK parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("Subscribed_String", result);
            SubscribedNK subscribedNK = new Gson().fromJson(result, SubscribedNK.class);
            return subscribedNK;
        }
    }

    /**
     * 订阅 置顶订阅接口
     *
     * @param context
     * @param dialog
     * @param posionId
     * @param nk
     * @param handler
     */
    public void SubscribedZD(final Context context, final Dialog dialog, final int posionId,
                             final List<SubscribedNK.DataBean> nk, final Handler handler) {

        Log.e("置顶刊物ID", nk.get(posionId).getId() + "");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getStickSubscribedUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("magazine_id", nk.get(posionId).getId() + "")
                .addParams("istop", "1")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("SubscribedZD_onError", e.toString());
                                 Toast.makeText(context, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("SubscribedZD_Callback", response.getData() + "");

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(context, "置顶成功", Toast.LENGTH_LONG).show();
                                         handler.sendEmptyMessage(2);
                                         dialog.dismiss();
                                         break;
                                     default:
                                         Toast.makeText(context, "请求失败", Toast.LENGTH_LONG).show();
                                         handler.sendEmptyMessage(1);
                                         break;
                                 }
                             }
                         }
                );
    }



    /**
     * 订阅 取消订阅接口
     *
     * @param context
     * @param parameter
     * @param dialog
     * @param posionId
     * @param nk
     * @param handler
     */
    public void SubscribedCZ(final Context context, final int parameter, final Dialog dialog,
                             final int posionId, final List<SubscribedNK.DataBean> nk, final Handler handler) {

        String url = null;

        if (parameter == 0)
            url = XrjHttpClient.getUnSubscribedUrl();
        else if (parameter == 1)
            url = XrjHttpClient.getSubscribedUrl();

        OkHttpUtils
                .post()
                .url(url)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("magazine_id", nk.get(posionId).getId() + "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("SubscribedCZ_onError", e.toString());
                                 Toast.makeText(context, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("SubscribedCZ_Callback", response.getData() + "");

                                 switch (response.getError_code()) {
                                     case 0:
                                         switch (parameter) {
                                             case 0:
                                                 Toast.makeText(context, "退订成功", Toast.LENGTH_LONG).show();
                                                 nk.remove(posionId);
                                                 handler.sendEmptyMessage(1);
                                                 break;
                                             case 1:
                                                 Toast.makeText(context, "订阅成功", Toast.LENGTH_LONG).show();
//                                                 nk.remove(posionId);
                                                 nk.get(posionId).setIs_subscribe(1);
                                                 handler.sendEmptyMessage(1);
                                                 break;
                                             default:
                                                 break;
                                         }
                                         dialog.dismiss();
                                         break;
                                     default:
                                         Toast.makeText(context, "请求失败", Toast.LENGTH_LONG).show();
                                         handler.sendEmptyMessage(1);
                                         break;
                                 }
                             }
                         }
                );
    }

    public void VoteWZ(final Context context, String vote_id, String voteitem_id) {

        String url = null;

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getNKVoteUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("vote_id", vote_id)
                .addParams("voteitem_id", voteitem_id)
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("VoteWZ_onError", e.toString());
//                                 Toast.makeText(context, "网络异常——请求失败", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("VoteWZ_Callback", response.getData() + "");
                                 Toast.makeText(context, "投票成功", Toast.LENGTH_LONG).show();
                             }

                         }

                );
    }

    /**
     * 期刊列表
     *
     * @param activity
     * @param baseAdapter
     * @param sDataBeanID
     * @param list
     * @param handler
     */
    public void NKAxpect(final Activity activity, final BaseAdapter baseAdapter, int sDataBeanID,
                         final int directionID, final List<AxpectNK.DataBean> list, final Handler handler) {
//        Log.e("AxpectNK_ID", sDataBean.getId() + "");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getNKAxpectUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
//                .addParams("magazine_id", sDataBean.getId() + "")
                .addParams("magazine_id", sDataBeanID + "")
                .addParams("id", directionID + "")
                .addParams("direction", "lt")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new AxpectNKCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("AxpectNK_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                                 handler.sendEmptyMessage(1);

                             }

                             @Override
                             public void onResponse(AxpectNK response, int id) {
                                 Log.e("AxpectNKCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (directionID == 0)
                                             list.clear();
                                         if (response.getData().size() == 0)
                                             Toast.makeText(activity, "没有更多了", Toast.LENGTH_SHORT).show();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
                                         break;
                                 }
                             }
                         }
                );

    }

    private abstract class AxpectNKCallback extends Callback<AxpectNK> {

        @Override
        public AxpectNK parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            AxpectNK axpectNK = new Gson().fromJson(result, AxpectNK.class);
            return axpectNK;
        }
    }

    /**
     * 文章列表
     *
     * @param activity
     * @param baseAdapter
     * @param aDataBeanID
     * @param list
     * @param handler
     */
    public void NKArticleList(final Activity activity, final BaseAdapter baseAdapter, int aDataBeanID,
                              final int directionID, final List<ArticleListNK.DataBean> list, final Handler handler) {

        Log.e("ArticleNK_ID", aDataBeanID + " issue_id" +aDataBeanID + " id"+directionID);

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getNKArticleListUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("issue_id", aDataBeanID + "")
                .addParams("id", directionID + "")
                .addParams("direction", "lt")
                .build()
                .execute(new ArticleListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("ArticleListNK_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                                 handler.sendEmptyMessage(1);

                             }

                             @Override
                             public void onResponse(ArticleListNK response, int id) {
                                 Log.e("ArticleListNKCallback", response.getError_msg());

                                 //Log.i("ArticalList",response.getData().get(0).getType()+"  ---type");

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (directionID == 0)
                                             list.clear();
                                         if (response.getData().size() == 0)
                                             Toast.makeText(activity, "没有更多了", Toast.LENGTH_SHORT).show();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);

                                         break;
                                     default:
                                         Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
                                         break;
                                 }
                             }
                         }
                );

    }

    private abstract class ArticleListCallback extends Callback<ArticleListNK> {

        @Override
        public ArticleListNK parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("ArticleListNK_onError", result);
            ArticleListNK articleListNK = new Gson().fromJson(result, ArticleListNK.class);
            return articleListNK;
        }
    }

    /**
     * 保存文章内容
     *
     * @param activity
     * @param aDataBean
     */
    public void NKSaveArticle(final Activity activity, ArticleListNK.DataBean aDataBean) {
//    public void NKArticle(final Activity activity) {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getNKArticleUrl() + "/" + aDataBean.getId())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new ArticleCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("SaveArticleNK_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(ArticleNK response, int id) {
                                 Log.e("SaveArticleNKCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         try {
                                             SQLiteDatabase dbWrite = MyApplication.helper.getWritableDatabase();
                                             ContentValues values = new ContentValues();
                                             values.put("_id", response.getData().getId());
                                             values.put("updated_at", response.getData().getUpdated_at());
                                             values.put("content", response.getData().getContent());
                                             dbWrite.insert("article_info", null, values);
                                             dbWrite.close();

                                             SQLiteDatabase dbRead = MyApplication.helper.getReadableDatabase();
                                             Cursor c = dbRead.query("article_info", null, null, null, null, null, null);
                                             while (c.moveToNext()) {
                                                 String content = c.getString(c.getColumnIndex("content"));
                                                 Log.e("onError-", content);
                                             }
                                             dbRead.close();
                                             Log.e("数据库", "载入成功");
                                         } catch (Exception e) {
                                             Log.e("SQLiteDatabase", e.toString());
                                         }

                                         break;
                                     default:
                                         Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
                                         break;
                                 }
                             }
                         }
                );

    }

    /**
     * 没有保存过的文章，网络加载
     *
     * @param activity
     * @param aDataBean
     */
    public void NKArticle(final Activity activity, ArticleListNK.DataBean aDataBean, final WebView webView) {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getNKArticleUrl() + "/" + aDataBean.getId())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new ArticleCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("ArticleNK_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(ArticleNK response, int id) {
                                 switch (response.getError_code()) {
                                     case 0:
                                         Log.e("SaveArticleNKCallback", response.getError_msg());
                                         String strUrl = "";
                                         final String str = response.getData().getContent();

                                         final int begin = str.indexOf("<video");
                                         int end = str.indexOf("</video>");
                                         if (begin != -1 && end != -1 && end < str.length()) {
                                             String strVideo = str.substring(begin, end);
                                             int beginV = strVideo.indexOf("src=\"");
                                             if (beginV != -1) {
                                                 String strScr = strVideo.substring(beginV + 5);
                                                 end = strScr.indexOf("\"");
                                                 if (end != -1) {
                                                     String strMp4 = strScr.substring(0, end);
                                                     strUrl = "<p><a href=\"" + strMp4 + "\">如果视频播放不佳，点击此处跳转播放</a></p>";
                                                 }
                                             }
                                         }

                                         Log.e("neikaninfo_Url", response.getData().getContent() + strUrl);
                                         webView.loadDataWithBaseURL(null,
                                                 response.getData().getContent() + strUrl, "text/html", "utf-8", null);
                                         break;
                                     default:
                                         Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
                                         break;
                                 }
                             }
                         }
                );

    }

    private abstract class ArticleCallback extends Callback<ArticleNK> {

        @Override
        public ArticleNK parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            ArticleNK articleNK = new Gson().fromJson(result, ArticleNK.class);
            return articleNK;
        }
    }

    /**
     * 评论列表
     *
     * @param activity
     * @param baseAdapter
     * @param aDataBean
     * @param list
     */
    public void NKComment(final Activity activity, final BaseAdapter baseAdapter, ArticleNK.DataBean aDataBean,
                          final List<CommentNK.DataBean> list) {
//    public void NKArticle(final Activity activity) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getNKCommentUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("article_id", aDataBean.getId() + "")
//                .addParams("id", )
//                .addParams("direction", )
                .build()
                .execute(new CommentCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("CommentNK_onError", e.toString() + MyApplication.hxt_setting_config.getString("credentials", ""));
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(CommentNK response, int id) {
                                 Log.e("CommentNKCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         list.clear();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
//                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
                                         break;
                                 }
                             }
                         }
                );

    }

    private abstract class CommentCallback extends Callback<CommentNK> {

        @Override
        public CommentNK parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            CommentNK commentNK = new Gson().fromJson(result, CommentNK.class);
            return commentNK;
        }
    }
}
