package com.qingyii.hxt.circle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.google.gson.Gson;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxt.R;
import com.qingyii.hxt.adapter.CircleChooseAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.Category;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 选择类型界面
 *
 * @author Lee
 */
public class CircleChooseTypeActivity extends AbBaseActivity {


    private ImageView mImgLeft;
    private ListView mListView;
    //    private ArrayList<CircleType> mArrayType;
//    private ArrayList<String> mArrayTypeStr;
    private List<Category.DataBean> cDataBeenList = new ArrayList<Category.DataBean>();

    private CircleChooseAdapter circleChooseAdapter;
//    private ArrayAdapter<String> mArrayAdapter;

    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
//    private AbProgressDialogFragment mDialogFragment = null;
    private ShapeLoadingDialog shapeLoadingDialog = null;

    private Handler mHandler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
            switch (msg.what) {
                case 0:
                    Toast.makeText(CircleChooseTypeActivity.this, "获取数据异常", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    circleChooseAdapter.notifyDataSetChanged();
                    break;
                case 5:
                    Toast.makeText(CircleChooseTypeActivity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    break;
            }
            return false;
        }
    });
    // 当你想实例化一个Handler,而又不实现自己的Hanlder的子类
//    private Handler handler = new Handler(new Callback() {
//        @SuppressLint("NewApi")
//        @Override
//        public boolean handleMessage(Message msg) {
//            if (shapeLoadingDialog != null) {
//                shapeLoadingDialog.dismiss();
//            }
//            if (msg.what == 1) {
//                mArrayAdapter.notifyDataSetChanged();
//                if (mArrayTypeStr.size() == 0) {
////                    txEmptyView.setText("暂无内容");
//                }
//            } else if (msg.what == 2) {
//                Toast.makeText(CircleChooseTypeActivity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
////                    mDialogFragment.dismiss();
//                shapeLoadingDialog.dismiss();
//            } else if (msg.what == 0) {
//                Toast.makeText(CircleChooseTypeActivity.this, "数据加载异常", Toast.LENGTH_SHORT).show();
//            }
//
//            return false;
//        }
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_typelist);
        findView();
        initData();
        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */
//        mDialogFragment = AbDialogUtil.showLoadDialog(
//                CircleChooseTypeActivity.this, R.mipmap.ic_load, "发送中,请等一小会");
//        mDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//            @Override
//            public void onLoad() {
        getTypeData();
//            }
//        });
//        mDialogFragment = AbDialogUtil.showProgressDialog(
//                CircleChooseTypeActivity.this, R.mipmap.ic_load,
//                "发送中,请等一小会");
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");
        shapeLoadingDialog.show();

        setListener();
    }

    private void findView() {
        mImgLeft = (ImageView) findViewById(R.id.circle_choosetype_left);
        mListView = (ListView) findViewById(R.id.circle_typelist_list);
    }

    private void initData() {
//        mArrayType = new ArrayList<CircleType>();
//        mArrayTypeStr = new ArrayList<String>();
//        mArrayAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, mArrayTypeStr);
        circleChooseAdapter = new CircleChooseAdapter(this, cDataBeenList);
        mListView.setAdapter(circleChooseAdapter);

    }

    private void setListener() {
        mImgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CircleChooseTypeActivity.this.finish();
            }
        });
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CircleChooseTypeActivity.this, CircleCreateActivity.class);
//                intent.putExtra("CircleType", mArrayType.get(arg2));
                intent.putExtra("Category", cDataBeenList.get(position));
                CircleChooseTypeActivity.this.setResult(RESULT_OK, intent);
                CircleChooseTypeActivity.this.finish();

            }
        });
    }

    private void getTypeData() {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getCategoryUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new CategoryCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Category_onError", e.toString());
                                 Toast.makeText(CircleChooseTypeActivity.this, "网络异常—纪实类型", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(Category response, int id) {
                                 Log.e("CategoryCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
//                                         for (int i = 0;i<response.getData().size();i++){
//                                             mArrayTypeStr.add(response.getData().get(i).getName());
//                                         }
//                                         mArrayAdapter.notifyDataSetChanged();
                                         cDataBeenList.clear();
                                         cDataBeenList.addAll(response.getData());
                                         mHandler.sendEmptyMessage(1);
                                         break;
                                     case 1:
                                         mHandler.sendEmptyMessage(1);
                                         Toast.makeText(CircleChooseTypeActivity.this, "暂时没有分类数据", Toast.LENGTH_SHORT).show();
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class CategoryCallback extends com.zhy.http.okhttp.callback.Callback<Category> {

        @Override
        public Category parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("CategoryString", result);
            Category category = new Gson().fromJson(result, Category.class);
            return category;
        }
    }

//    private void getTypeData() {
//        JSONObject jsonobject = new JSONObject();
//        try {
//            jsonobject.put("userid", CacheUtil.userid + "");
//            byte[] bytes = jsonobject.toString().getBytes("utf-8");
//            ByteArrayEntity entity = new ByteArrayEntity(bytes);
//            YzyHttpClient.post(this, HttpUrlConfig.reqDynamicInfoTypeList,
//                    entity, new AsyncHttpResponseHandler() {
//
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            if (statusCode == 499) {
//                                Toast.makeText(CircleChooseTypeActivity.this,
//                                        CacheUtil.logout, Toast.LENGTH_SHORT)
//                                        .show();
//                                Intent intent = new Intent(
//                                        CircleChooseTypeActivity.this,
//                                        LoginActivity.class);
//                                startActivity(intent);
//                                onFinish();
//                            } else if (statusCode == 200) {
//                                Gson gson = new Gson();
//                                try {
//                                    JSONObject js = new JSONObject(content);
//                                    if (EmptyUtil.IsNotEmpty(js
//                                            .getString("rows"))) {
//
//                                        JSONArray lists = js
//                                                .getJSONArray("rows");
//
//                                        if (lists.length() <= 0) {
//                                            mHandler.sendEmptyMessage(0);
//                                        } else {
//                                            for (int i = 0; i < lists.length(); i++) {
//                                                CircleType type = gson.fromJson(
//                                                        lists.getString(i),
//                                                        CircleType.class);
//                                                mArrayType.add(type);
//                                                mArrayTypeStr.add(type
//                                                        .getTypename());
//                                            }
//                                            mHandler.sendEmptyMessage(1);
//                                        }
//
//                                    } else {
//                                        mHandler.sendEmptyMessage(5);
//                                    }
//
//                                } catch (JSONException e) {
//                                    // handler.sendEmptyMessage(0);
//                                    e.printStackTrace();
//                                }
//                            }
//                            super.onSuccess(statusCode, content);
//                        }
//
//                        @SuppressWarnings("deprecation")
//                        @Override
//                        public void onFailure(Throwable error, String content) {
//                            super.onFailure(error, content);
//                            // System.out.println(content);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            super.onFinish();
//                            // System.out.println("finish");
//                        }
//
//                    });
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//    }

}
