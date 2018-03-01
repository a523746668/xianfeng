package com.qingyii.hxt.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.qingyii.hxt.MoreCommentsActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.adapter.PingLunAdapter;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.httpway.SJIpload;
import com.qingyii.hxt.pojo.Book;
import com.qingyii.hxt.pojo.BooksParameter;
import com.qingyii.hxt.pojo.Comment;
import com.qingyii.hxt.pojo.Discuss;
import com.qingyii.hxt.util.DateUtils;
import com.qingyii.hxt.xiepinglunActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class PingLunFragment extends Fragment {
    private Activity mActivity = null;
    private ListView mListView = null;
    //    private Book bookInfo;
    private BooksParameter.DataBean bookInfo;
    //    private ArrayList<Discuss> list = new ArrayList<Discuss>();
    private List<Comment.DataBean> list = new ArrayList<Comment.DataBean>();
    private PingLunAdapter adapter;
    private Bundle b;
    private TextView bt_morecomment, bt_addcomment;
    private IntentFilter intentFilter;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
    SJIpload sjIpload = SJIpload.getSJIpload();

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRefreshBroadcastReceiver != null) {

            mActivity.unregisterReceiver(mRefreshBroadcastReceiver);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        bookInfo = (Book) b.getSerializable("bookInfo");
        bookInfo = (BooksParameter.DataBean) b.getParcelable("bookInfo");
        mActivity = this.getActivity();

        mActivity = this.getActivity();
        View mainView = inflater.inflate(R.layout.fragment_pinglun, null);
        //注册刷新数据广播
        intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshMyAddress");
        mActivity.registerReceiver(mRefreshBroadcastReceiver, intentFilter); //需要 unregisterReceiver

        mListView = (ListView) mainView.findViewById(R.id.mListView);

        View footerView = inflater.inflate(R.layout.shujixq_pinglun_footer, null);
        bt_morecomment = (TextView) footerView.findViewById(R.id.fragment_pinglun_footer_ck);
        bt_addcomment = (TextView) footerView.findViewById(R.id.fragment_pinglun_footer_pl);
        mListView.addFooterView(footerView);

        adapter = new PingLunAdapter(mActivity, list, bookInfo);
        mListView.setAdapter(adapter);

        bt_addcomment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mActivity, xiepinglunActivity.class);
                intent.putExtra("comingType", 1);
                intent.putExtra("book", bookInfo);
                intent.putExtra("parentid", 0);
                startActivity(intent);
            }
        });

        bt_morecomment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), MoreCommentsActivity.class);
                intent.putExtra("book", bookInfo);
                startActivity(intent);
            }
        });

        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        this.PingLunList();
        sjIpload.BooksCommentList(getActivity(), adapter, 0, bookInfo, list, handler);
//        adapter.notifyDataSetChanged();
    }

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshMyAddress")) {
                list.clear();
//                PingLunList();
                sjIpload.BooksCommentList(getActivity(), adapter, 0, bookInfo, list, handler);
            }
        }
    };

    private void PingLunList() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("bookid", bookInfo.getBookid());
//            jsonObject.put("page", 1);
//            jsonObject.put("pagesize", 4);
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            try {
//                bytes = jsonObject.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("书籍详情评论_out", jsonObject.toString());
//
//                YzyHttpClient.post(mActivity, HttpUrlConfig.queryDiscussList2,
//                        entity, new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("书籍详情评论_out", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(mActivity, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(mActivity, LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    Gson gson = new Gson();
//                                    try {
//                                        JSONObject Obj = new JSONObject(content);
//                                        JSONArray mylist = Obj.getJSONArray("rows");
//                                        for (int i = 0; i < mylist.length(); i++) {
//                                            Discuss m = gson.fromJson(mylist.getString(i), Discuss.class);
//                                            list.add(m);
//
//                                        }
//                                        adapter.notifyDataSetChanged();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                            @Override
//                            public void onFailure(int statusCode,
//                                                  Throwable error, String content) {
//                                super.onFailure(statusCode, error, content);
//                            }
//                        });
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
//        PingLunList();
        sjIpload.BooksCommentList(getActivity(), adapter, 0, bookInfo, list, handler);
    }

    /**
     * activity向fragment传值
     *
     * @param b
     */
    public void setBundle(Bundle b) {
        this.b = b;
    }

}
