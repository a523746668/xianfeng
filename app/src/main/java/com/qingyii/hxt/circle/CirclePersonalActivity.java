package com.qingyii.hxt.circle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.dialog.AbProgressDialogFragment;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.LoginActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.DepartmentInfo;
import com.qingyii.hxt.bean.DynamicInfo;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.pojo.User;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.util.ImageUtil;
import com.qingyii.hxt.view.RoundedImageView;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import com.ab.fragment.AbDialogFragment.AbDialogOnLoadListener;
//import com.ab.fragment.AbLoadDialogFragment;
//import com.ab.util.AbDialogUtil;
//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
//import com.qingyii.hxt.AbActivity;

//动态圈用户详情页

/**
 * @author Lee
 */
public class CirclePersonalActivity extends AbBaseActivity {

    private static final int TYPE_HEADER = 0x0001;
    private static final int TYPE_FOOTER = 0x0002;

    private TextView mTextTitle;
    private ImageView mImgLeft;
    private TextView mTextNum1, mTextNum2, mTextNum3, mTextNum4;
    private TextView mTextPersonalInfo;
    private ImageView mImgPersonalBg;
    private RoundedImageView mImgPersonalPhoto;

    private int mRefreshType;
    private int mPagePersonal = 1;
    private int mPageSize = 10;

    private AbPullToRefreshView mRefreshView;
    private ListView mListView;

    private ArrayList<DynamicInfo> mListDynamic = new ArrayList<DynamicInfo>();
    private CirclePersonalAdapter mPersonalAdapter;

    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
    private AbProgressDialogFragment mDialogFragment = null;
    private Handler mHandler;

    private User mDefaultUser;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM月dd日");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            mDefaultUser = (User) intent.getSerializableExtra("User");
        }
        setContentView(R.layout.activity_circle_personal);
        findView();
        initData();
        setListener();

        mHandler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (mDialogFragment != null) {
                    mDialogFragment.dismiss();
                }
                switch (msg.what) {
                    case 0:
                        Toast.makeText(CirclePersonalActivity.this, "获取数据异常", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case 1:
                        if (msg.arg1 == 0) {
                            mPersonalAdapter.notifyDataSetChanged();
                        } else if (msg.arg1 == 1) {
                            mPersonalAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 5:
                        Toast.makeText(CirclePersonalActivity.this, "已是最新数据", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case 6:
                        DepartmentInfo info = (DepartmentInfo) msg.obj;
                        mTextNum1.setText("" + info.getDynamicinfocount());
                        mTextNum2.setText("" + info.getCommentcount());
                        mTextNum3.setText("" + info.getReceivelovecount());
                        mTextNum4.setText("" + info.getSendlovecount());
                        String dmInfo = mDefaultUser.getUsername() + " "
                                + mDefaultUser.getDepname();
                        mTextPersonalInfo.setText(dmInfo);
                        ImageLoader.getInstance().displayImage(
                                HttpUrlConfig.photoDir
                                        + mDefaultUser.getPicaddress(),
                                mImgPersonalPhoto);
                        ImageLoader.getInstance().displayImage(
                                HttpUrlConfig.photoDir + info.getBackgroundurl(),
                                mImgPersonalBg, ImageUtil.newDisplayOptions(R.mipmap.bg_user_default));
                        break;
                }
                mRefreshView.onHeaderRefreshFinish();
                mRefreshView.onFooterLoadFinish();
                return false;
            }
        });
        reqData();
    }

    private void findView() {
        View viewHeader = LayoutInflater.from(CirclePersonalActivity.this).inflate(R.layout.item_circle_personal_head, null);
        WindowManager wm = this.getWindowManager();
        viewHeader.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, (int) (wm
                .getDefaultDisplay().getHeight() * 0.4f)));
        mImgPersonalPhoto = (RoundedImageView) viewHeader.findViewById(R.id.circle_personal_head_photo);
        mTextNum1 = (TextView) viewHeader.findViewById(R.id.circle_personal_head_num1);
        mTextNum2 = (TextView) viewHeader.findViewById(R.id.circle_personal_head_num2);
        mTextNum3 = (TextView) viewHeader.findViewById(R.id.circle_personal_head_num3);
        mTextNum4 = (TextView) viewHeader.findViewById(R.id.circle_personal_head_num4);
        mImgPersonalBg = (ImageView) viewHeader.findViewById(R.id.circle_personal_head_bg);
        mTextPersonalInfo = (TextView) viewHeader.findViewById(R.id.circle_personal_head_info);

        mTextTitle = (TextView) findViewById(R.id.circle_personal_title);
        if (mDefaultUser.getUserid().equals(CacheUtil.user.getUserid())) {
            mTextTitle.setText("我的时光轴");
        } else {
            mTextTitle.setText(mDefaultUser.getUsername() + "的时光轴");
        }
        mImgLeft = (ImageView) findViewById(R.id.circle_personal_left);
        mRefreshView = (AbPullToRefreshView) findViewById(R.id.circle_personal_refresh);
        mListView = (ListView) findViewById(R.id.circle_personal_list);
        mListView.addHeaderView(viewHeader);
        mPersonalAdapter = new CirclePersonalAdapter(mListDynamic);
        mListView.setAdapter(mPersonalAdapter);
    }

    private void initData() {
    }

    private void setListener() {
        mImgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        mRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                mPagePersonal = 0;
                getDataPersonal(mPagePersonal, mPageSize);
            }
        });
        mRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                getDataPersonal(mPagePersonal + 1, mPageSize);
            }
        });
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int index = arg2 - 1;
                if (index >= 0 && index < mListDynamic.size()) {
                    Intent intent = new Intent(CirclePersonalActivity.this,
                            CircleDetailActivity.class);
                    intent.putExtra("DynamicInfo", mListDynamic.get(index));
                    startActivity(intent);
                }

            }
        });
    }

    private void reqData() {
        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */
//        mDialogFragment = AbDialogUtil.showLoadDialog(this, R.mipmap.ic_load,
//                "查询中,请等一小会");
//        mDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//
//            @Override
//            public void onLoad() {
//                // 下载网络数据
//                getDataHead();
//                getDataPersonal(mPagePersonal, mPageSize);
//            }
//        });
        mDialogFragment = AbDialogUtil.showProgressDialog(this, R.mipmap.ic_load,
                "查询中,请等一小会");
    }

    public void getDataPersonal(int mypage, int mypagesize) {
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("page", mypage);
            jsonobject.put("pagesize", mypagesize);
            jsonobject.put("userid", mDefaultUser.getUserid());
            jsonobject.put("type", 3);
            byte[] bytes = jsonobject.toString().getBytes("utf-8");
            ByteArrayEntity entity = new ByteArrayEntity(bytes);
            YzyHttpClient.post(this, HttpUrlConfig.reqDynamicInfoList, entity,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String content) {
                            if (statusCode == 499) {
                                Toast.makeText(CirclePersonalActivity.this,
                                        CacheUtil.logout, Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(
                                        CirclePersonalActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                onFinish();
                            } else if (statusCode == 200) {
                                Gson gson = new Gson();
                                try {
                                    JSONObject js = new JSONObject(content);
                                    String a = js.getString("rows");
                                    System.out.println(a);
                                    if (EmptyUtil.IsNotEmpty(js
                                            .getString("rows"))) {
                                        JSONArray lists = js
                                                .getJSONArray("rows");

                                        if (lists.length() <= 0) {
                                            mHandler.sendEmptyMessage(5);
                                        } else {
                                            if (mRefreshType == TYPE_HEADER) {
                                                CirclePersonalActivity.this.mListDynamic
                                                        .clear();
                                            } else {
                                                mPagePersonal++;
                                            }
                                            for (int i = 0; i < lists.length(); i++) {
                                                DynamicInfo info = gson.fromJson(
                                                        lists.getString(i),
                                                        DynamicInfo.class);
                                                CirclePersonalActivity.this.mListDynamic
                                                        .add(info);
                                            }
                                            Message msg = Message.obtain();
                                            msg.what = 1;
                                            msg.arg1 = 0;
                                            mHandler.sendMessage(msg);
                                        }

                                    } else {
                                        mHandler.sendEmptyMessage(5);
                                    }

                                } catch (JSONException e) {
                                    mHandler.sendEmptyMessage(0);
                                    e.printStackTrace();
                                }
                            }
                            super.onSuccess(statusCode, content);
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            super.onFailure(error, content);
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDataHead() {
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("userid", mDefaultUser.getUserid());
            byte[] bytes = jsonobject.toString().getBytes("utf-8");
            ByteArrayEntity entity = new ByteArrayEntity(bytes);
            YzyHttpClient.post(this,
                    HttpUrlConfig.reqMyDynamicInfoStatistics, entity,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String content) {
                            if (statusCode == 499) {
                                Toast.makeText(CirclePersonalActivity.this,
                                        CacheUtil.logout, Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(
                                        CirclePersonalActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                onFinish();
                            } else if (statusCode == 200) {
                                Gson gson = new Gson();
                                try {
                                    JSONObject js = new JSONObject(content);
                                    DepartmentInfo info = gson.fromJson(
                                            js.toString(), DepartmentInfo.class);
                                    Message msg = Message.obtain();
                                    msg.obj = info;
                                    msg.what = 6;
                                    mHandler.sendMessage(msg);

                                } catch (JSONException e) {
                                    mHandler.sendEmptyMessage(0);
                                    e.printStackTrace();
                                }
                            }
                            super.onSuccess(statusCode, content);
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            super.onFailure(error, content);
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class CirclePersonalAdapter extends BaseAdapter {

        private ArrayList<DynamicInfo> mList;

        public CirclePersonalAdapter(ArrayList<DynamicInfo> list) {
            this.mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return mList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ViewHolder holder;
            if (arg1 == null) {
                arg1 = View.inflate(CirclePersonalActivity.this,
                        R.layout.item_circle_personal, null);
                holder = new ViewHolder();
                holder.textTime = (TextView) arg1
                        .findViewById(R.id.item_circle_personal_time);
                holder.textContent = (TextView) arg1
                        .findViewById(R.id.item_circle_personal_text);
                holder.textNum = (TextView) arg1
                        .findViewById(R.id.item_circle_personal_num);
                holder.gridView = (NonScrollGridView) arg1
                        .findViewById(R.id.item_circle_personal_photo);
                holder.photos = new ArrayList<String>();
                holder.photoAdapter = new PhotoAdapter(
                        CirclePersonalActivity.this, holder.photos);
                holder.gridView.setAdapter(holder.photoAdapter);
                arg1.setTag(holder);
            } else {
                holder = (ViewHolder) arg1.getTag();
            }

            holder.textNum.setText("");
            holder.photos.clear();
            holder.photoAdapter.notifyDataSetChanged();

            DynamicInfo info = mList.get(arg0);
            holder.textContent.setText("" + info.getContenttxt());
            holder.textTime.setText(""
                    + mDateFormat.format(new Date(info.getCreatetime())));
            String[] images = info.getContentimageurllist();
            if (images != null) {
                for (String str : images) {
                    holder.photos.add(str);
                    if (holder.photos.size() >= 4) {
                        break;
                    }
                }
                holder.textNum.setText("共" + holder.photos.size() + "张");
                if (holder.photos.size() > 0) {
                    holder.gridView.setVisibility(View.VISIBLE);
                    holder.photoAdapter.notifyDataSetChanged();
                } else {
                    holder.gridView.setVisibility(View.GONE);
                }
            } else {
                holder.gridView.setVisibility(View.GONE);
            }
            return arg1;
        }

        class ViewHolder {
            NonScrollGridView gridView;
            TextView textTime;
            TextView textContent;
            TextView textNum;
            PhotoAdapter photoAdapter;
            ArrayList<String> photos;
        }

    }
}
