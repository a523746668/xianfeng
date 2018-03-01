package com.qingyii.hxt.httpway;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxt.pojo.BooksClassify;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.BooksParameter;
import com.qingyii.hxt.pojo.Comment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/8/22.
 */

public class SJIpload {

    private static SJIpload sjIpload = new SJIpload();

    private SJIpload() {
    }

    public static SJIpload getSJIpload() {
        return sjIpload;
    }

    /**
     * 书籍推荐，最新，排行列表
     *
     * @param activity
     * @param baseAdapter
     * @param parameter
     * @param bookID
     * @param list
     * @param handler
     */
    public void BooksList(final Activity activity, final BaseAdapter baseAdapter, String parameter,
                          final int bookID, final List<BooksParameter.DataBean> list, final Handler handler) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getBooksUrl() + "/" + parameter)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("id", bookID + "")
                .addParams("direction", "lt")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new BooksParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("BooksParameter_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(BooksParameter response, int id) {
                                 Log.e("BooksParameterCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (bookID == 0)
                                             list.clear();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 分类书籍列表
     *
     * @param activity
     * @param baseAdapter
     * @param parameter
     * @param list
     * @param handler
     */
    public void BooksCategoryList(final Activity activity, final BaseAdapter baseAdapter, String parameter,
                                  final List<BooksParameter.DataBean> list, final Handler handler) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getBooksCategoryUrl() + "/" + parameter)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("id", "0")
                .addParams("direction", "it")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new BooksParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("BooksParameter_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(BooksParameter response, int id) {
                                 Log.e("BooksParameterCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         list.clear();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 专题书籍列表
     *
     * @param activity
     * @param baseAdapter
     * @param parameter
     * @param bookID
     * @param list
     * @param handler
     */
    public void BooksSubjectList(final Activity activity, final BaseAdapter baseAdapter, String parameter,
                                 final int bookID, final List<BooksParameter.DataBean> list, final Handler handler) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getBooksSubjectUrl() + "/" + parameter)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("id", bookID + "")
                .addParams("direction", "lt")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new BooksParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("BooksParameter_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(BooksParameter response, int id) {
                                 Log.e("BooksParameterCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (bookID == 0)
                                             list.clear();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class BooksParameterCallback extends Callback<BooksParameter> {

        @Override
        public BooksParameter parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("BooksParameter_String", result);
            BooksParameter booksParameter = new Gson().fromJson(result, BooksParameter.class);
            return booksParameter;
        }
    }

    /**
     * 书籍分类列表
     *
     * @param activity
     * @param baseAdapter
     * @param parameter
     * @param list
     * @param handler
     */
    public void BooksClassifyList(final Activity activity, final BaseAdapter baseAdapter, String parameter,
                                  final List<BooksClassify.DataBean> list, final Handler handler) {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getBooksUrl() + "/" + parameter)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
//                .addParams("id","0")
//                .addParams("direction","it")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new BooksClassifyCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("BooksParameter_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(BooksClassify response, int id) {
                                 Log.e("BooksParameterCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         list.clear();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class BooksClassifyCallback extends Callback<BooksClassify> {

        @Override
        public BooksClassify parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("BooksParameter_String", result);
            BooksClassify booksClassify = new Gson().fromJson(result, BooksClassify.class);
            return booksClassify;
        }
    }

    /**
     * 评论详情
     *
     * @param activity
     * @param baseAdapter
     * @param ID
     * @param bDataBean
     * @param cDataBeanList
     * @param handler
     */
    public void BooksCommentList(final Activity activity, final BaseAdapter baseAdapter, final int ID,
                                 final BooksParameter.DataBean bDataBean, final List<Comment.DataBean> cDataBeanList, final Handler handler) {

        Log.e("book_id", bDataBean.getId() + "");
        Log.e("id", ID + "");
        Log.e("direction", "lt");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getNKCommentUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("book_id", bDataBean.getId() + "")
                .addParams("id", ID + "")
                .addParams("direction", "lt")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new CommentCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("CommentCallback_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(Comment response, int id) {
                                 Log.e("Comment_circle", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (ID == 0)
                                             cDataBeanList.clear();
//                                         if (response.getData().size() == 0)
//                                             Toast.makeText(activity, "没有更多了", Toast.LENGTH_SHORT).show();
                                         cDataBeanList.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );

    }

    private abstract class CommentCallback extends Callback<Comment> {

        @Override
        public Comment parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("Comment_String", result);
            Comment comment = new Gson().fromJson(result, Comment.class);
            return comment;
        }
    }
}
