package com.qingyii.hxt.http;

import android.content.Context;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbHttpResponseListener;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.andbase.library.util.AbFileUtil;
import com.andbase.library.util.AbJsonUtil;
import com.qingyii.hxt.bean.Article;
import com.qingyii.hxt.bean.ArticleListResult;

import java.util.List;

//import com.ab.http.AbHttpListener;
//import com.ab.http.AbHttpUtil;
//import com.ab.http.AbRequestParams;
//import com.ab.http.AbStringHttpResponseListener;
//import com.ab.model.AbResult;
//import com.ab.util.AbFileUtil;
//import com.ab.util.AbJsonUtil;

public class NetworkWeb {

    private AbHttpUtil mAbHttpUtil = null;
    private Context mContext = null;

    public NetworkWeb(Context context) {
        // 创建Http工具类
        mContext = context;
        mAbHttpUtil = AbHttpUtil.getInstance(context);
    }

    /**
     * Create a new instance of SettingWeb.
     */
    public static NetworkWeb newInstance(Context context) {
        NetworkWeb web = new NetworkWeb(context);
        return web;
    }

    /**
     * 调用请求的模版
     *
     * @param param1         参数1
     * @param param2         参数2
     * @param abHttpListener 请求的监听器
     */
    public void testHttpGet(String param1, String param2, final AbHttpResponseListener abHttpResponseListener) {

        // 一个url地址
        String urlString = "http://www.amsoft.cn/rss.php";
        mAbHttpUtil.get(urlString, new AbStringHttpResponseListener() {

            @Override
            public void onSuccess(int statusCode, String content) {
                //将结果传递回去
                abHttpResponseListener.onSuccess(statusCode, content);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int statusCode, String content,
                                  Throwable error) {
                //将失败错误信息传递回去
                abHttpResponseListener.onFailure(statusCode, content, error);
            }

        });
    }

    /**
     * 调用一个列表请求
     *
     * @param AbRequestParams 参数列表
     * @param abHttpListener  请求的监听器
     */
    public void findLogList(AbRequestParams params, final AbHttpResponseListener abHttpResponseListener) {

        final String result = AbFileUtil.readAssetsByName(mContext, "article_list.json", "UTF-8");
        // 一个url地址
        String urlString = "http://www.amsoft.cn/rss.php?";
        mAbHttpUtil.get(urlString, params, new AbStringHttpResponseListener() {

            @Override
            public void onSuccess(int statusCode, String content) {
                try {
                    //模拟数据
                    content = result;

                    AbResult result = new AbResult(content);
                    if (result.getResultCode() > 0) {
                        //成功
                        ArticleListResult mArticleListResult = (ArticleListResult) AbJsonUtil.fromJson(content, ArticleListResult.class);
                        List<Article> articleList = mArticleListResult.getItems();
                        //将结果传递回去
                        abHttpResponseListener.onSuccess(statusCode, articleList);
                    } else {
                        //将错误信息传递回去
                        /**
                         * result 没有 Throwable参数方法
                         */
                        abHttpResponseListener.onFailure(statusCode, content, null);
//                        abHttpResponseListener.onFailure(statusCode, content, result.getResultMessage());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    abHttpResponseListener.onFailure(statusCode, content, e);
                }
            }

            @Override
            public void onStart() {
                //开始的状态传递回去
            }

            @Override
            public void onFinish() {
                //完成的状态传递回去
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                //将失败错误信息传递回去
                abHttpResponseListener.onFailure(statusCode, content, error);
            }

        });

    }

}
