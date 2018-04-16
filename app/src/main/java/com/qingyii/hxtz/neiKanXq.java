package com.qingyii.hxtz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxtz.adapter.neiKanXqAdapter;
import com.qingyii.hxtz.httpway.NKUpload;
import com.qingyii.hxtz.pojo.AxpectNK;
import com.qingyii.hxtz.pojo.SubscribedNK;

import java.util.ArrayList;
import java.util.List;

/**
 * 刊物期数列表
 *
 * @author Administrator
 */
public class neiKanXq extends AbBaseActivity {
    private AbPullToRefreshView mPullRefreshView;
    private ListView mListView;
    private LinearLayout emptyView;
    private LinearLayout iv_back;
    private TextView tv_title;
    private neiKanXqAdapter adapter;
    //    private List<Periods> list = new ArrayList<Periods>();
    private List<AxpectNK.DataBean> list = new ArrayList<AxpectNK.DataBean>();
//    private List<Magazine> list2 = new ArrayList<Magazine>();

    private ShapeLoadingDialog shapeLoadingDialog = null;

    private int page = 1;
    private int pagesize = 10;
    int type = 1;
    //    private Magazine magazine;
    private SubscribedNK.DataBean sDataBean;

    private Handler handler = new Handler(new Callback() {

        @SuppressLint("NewApi")
        @Override
        public boolean handleMessage(Message msg) {
//                if (mDialogFragment != null) {
//                    mDialogFragment.dismiss();
//                }
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }

            if (msg.what == 2) {
                Toast.makeText(neiKanXq.this, "已是最新数据", Toast.LENGTH_SHORT).show();
//                    mDialogFragment.dismiss();
                shapeLoadingDialog.dismiss();
            } else if (msg.what == 0) {
                Toast.makeText(neiKanXq.this, "获取数据异常", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                adapter.notifyDataSetChanged();
            }
            //刷新，加载更新提示隐藏
            mPullRefreshView.onHeaderRefreshFinish();
            mPullRefreshView.onFooterLoadFinish();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neikanxq);
//        magazine = (Magazine) getIntent().getSerializableExtra("Magazine");
        sDataBean = getIntent().getParcelableExtra("SubscribedNK");

        initUI();
        initData();
    }

    private void initData() {
//		list.add(new neikanInfo("2015.2.26期", "女主意外丢掉满身修为后，放弃剑修，踏上禅修之路。越来越凉薄寡情的性格，让一路扶持走来的傲娇相公非常糟心。道之所求，殊途同归。彼岸茫茫，唯心中梦想不灭，道心不灭，勇气不灭，天真不灭，爱不灭。", "Eason"));
//		list.add(new neikanInfo("2015.2.27期", "玄幻小说的巅峰之作，也是辰东的巅峰之作，原来吧里曾开玩笑说：东哥的是读这一本也就够了。死去万载的平凡青年一朝觉醒，只为寻找前生挚爱的红颜，却卷入天人之争，究竟是偶然还是必然？辰南，南，真男，真难，他除雨馨外从来别无所求，但奈何身负完美世界种子，又留着太古神族的血；七君王一战后，修为尽失，身躯老去，曾经天下无敌又有何用，","Eason"));
//		list.add(new neikanInfo("2015.2.28期", "巅峰之作，十分个性的书，我第一次见到一本书配角的光彩，完全遮蔽了主角的光辉；第一次见到头脑简单如日本热血漫画的主人公，居然能在中国网络小说里过得生龙活虎。此书读起来真是特别有味道，而且有特别的味道。快来吧，一起来领略后主李煜的绝世剑法，一起和公瑾、小乔守护埃菲尔铁塔，一起与成吉思汗横扫天下吧！","Eason"));
//		list.add(new neikanInfo("2015.2.29期", "玄幻小说的巅峰之作，要想看明白，至少看两遍。如果读者至今没看懂，我献丑分析一下。主角胖子罗格是头那夜明白了领域的才是真正的力量，但却长期苦于太过弱小形不成自己的域；","Eason"));
//		list.add(new neikanInfo("2015.2.30期", "女主意外丢掉满身修为后，放弃剑修，踏上禅修之路。越来越凉薄寡情的性格，让一路扶持走来的傲娇相公非常糟心。道之所求，殊途同归。彼岸茫茫，唯心中梦想不灭，道心不灭，勇气不灭，天真不灭，爱不灭。","Eason"));
        //显示进度框
        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */
//		mDialogFragment = AbDialogUtil.showLoadDialog(this, R.mipmap.ic_load, "查询中,请等一小会");
//		mDialogFragment
//		.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//
//			@Override
//			public void onLoad() {
//				// 下载网络数据
        refreshTask();
//			}
//
//		});
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");
//        mDialogFragment = AbDialogUtil.showProgressDialog(this, R.mipmap.ic_load, "查询中,请等一小会");
//		getNeikanQishuList(page,pagesize);
    }

    private void initUI() {
        tv_title = (TextView) findViewById(R.id.activity_tltle_name);
//        tv_title.setText(magazine.getMagazinename());
        tv_title.setText(sDataBean.getMagazinename());
        iv_back = (LinearLayout) findViewById(R.id.returns_arrow);
        mPullRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
        mListView = (ListView) findViewById(R.id.mListView);
        adapter = new neiKanXqAdapter(neiKanXq.this, list);
        mListView.setAdapter(adapter);


        //当listview没有内容时显示暂无内容
        emptyView = (LinearLayout) findViewById(R.id.empty_neikanxq);
        mListView.setEmptyView(emptyView);


        //下拉刷新
        mPullRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                refreshTask();

            }
        });
        //上拉加载
        mPullRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                loadMoreTask();

            }
        });
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent it = new Intent(neiKanXq.this, neiKanXq1.class);
//                it.putExtra("Periods", list.get(position));
//                it.putExtra("Magazine", magazine);
                it.putExtra("AxpectNK", list.get(position));
                //it.putExtra("SubscribedNK", sDataBean);
                startActivity(it);
            }
        });
        // 设置进度条的样式
        mPullRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mPullRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    protected void refreshTask() {
        type = 1;
//        getNeikanQishuList(1, pagesize);
        NKUpload nkUpload = NKUpload.getNKUpload();
        nkUpload.NKAxpect(this, adapter, sDataBean.getId(), 0, list, handler);
    }

    protected void loadMoreTask() {
        type = 2;
//        getNeikanQishuList(page, pagesize);
        NKUpload nkUpload = NKUpload.getNKUpload();
        nkUpload.NKAxpect(this, adapter, sDataBean.getId(), list.get(list.size() - 1).getId(), list, handler);
    }

    /**
     * 请求刊物期数列表数据
     *
     * @param mypage
     * @param mypagesize
     */
//    private void getNeikanQishuList(int mypage, int mypagesize) {
//        try {
//            JSONObject jsonObj = new JSONObject();
//            jsonObj.put("magazineid", magazine.getMagazineid() + "");
////		    jsonObj.put("subscribeid", magazine.getSubscribeid()+"");
//            jsonObj.put("userid", CacheUtil.userid + "");
//            jsonObj.put("page", mypage);
//            jsonObj.put("pagesize", mypagesize);
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            try {
//                bytes = jsonObj.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("NeiKan_xq_out", jsonObj.toString());
//
//                YzyHttpClient.post(neiKanXq.this, HttpUrlConfig.queryPeriodsList,
//                        entity, new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("NeiKan_xq_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(neiKanXq.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(neiKanXq.this, LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    Gson gson = new Gson();
//
//                                    try {
//                                        if (type == 1) {
//                                            page = 2;
//                                            list.clear();
//
//                                        }
//                                        JSONObject Obj = new JSONObject(content);
//                                        if (EmptyUtil.IsNotEmpty(Obj.getString("rows"))) {
//                                            JSONArray mlist = Obj.getJSONArray("rows");
//                                            if (mlist.length() <= 0) {
//                                                emptyView.setText("暂无内容");
//                                                handler.sendEmptyMessage(2);
//                                            } else {
//                                                //如果获取到新数据 页码加1
//                                                if (type == 2) {
//                                                    page += 1;
//                                                }
//                                                for (int i = 0; i < mlist.length(); i++) {
//                                                    Periods p = gson.fromJson(mlist.getString(i), Periods.class);
//                                                    list.add(p);
//
//                                                }
//                                                handler.sendEmptyMessage(1);
//                                            }
//                                        } else {
//                                            handler.sendEmptyMessage(2);
//                                        }
//
//                                    } catch (JSONException e) {
//                                        handler.sendEmptyMessage(0);
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(int statusCode,
//                                                  Throwable error, String content) {
//                                super.onFailure(statusCode, error, content);
//
//                            }
//                        });
//            } catch (UnsupportedEncodingException e) {
//                handler.sendEmptyMessage(0);
//                e.printStackTrace();
//            }
//
//
//        } catch (JSONException e) {
//            handler.sendEmptyMessage(0);
//            e.printStackTrace();
//        }
//    }
}
