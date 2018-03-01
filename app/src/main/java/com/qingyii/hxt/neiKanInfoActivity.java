package com.qingyii.hxt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxt.adapter.neiKanInfoAdapter;
import com.qingyii.hxt.adapter.neiKanInfoInfo;
import com.qingyii.hxt.adapter.neiKaninfoToupiaoAdapter;
import com.qingyii.hxt.bean.ontherPinglunInfo;
import com.qingyii.hxt.database.DianzanTubiao;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.httpway.NKUpload;
import com.qingyii.hxt.pojo.ArticleListNK;
import com.qingyii.hxt.pojo.Discuss;
import com.qingyii.hxt.pojo.Magazine;
import com.qingyii.hxt.pojo.Periods;
import com.qingyii.hxt.pojo.PeriodsArticleRela;
import com.qingyii.hxt.util.EmptyUtil;

import java.util.ArrayList;

/**
 * 文章详情界面
 *
 * @author Administrator
 */
public class neiKanInfoActivity extends BaseActivity {
    private ArrayList<Discuss> list = new ArrayList<Discuss>();
    private neiKanInfoAdapter adapter;
    private ListView mListView;
    private ImageView iv_fenxiang, iv_fenxianghong, iv_shoucang,
            neikaninfo_dianzanliang_iv;
    private LinearLayout ll_fenxiang, ll_toupiao, neikaninfo_pinglun_ll, llll;
    private RelativeLayout rl_neikaninfo_more;
    // private AbPullToRefreshView mPullRefreshView;
    private ImageView iv_back, neikaninfo_goback_iv, iv_qq, iv_weixin,
            iv_pengyouquan, iv_duanxin;
    private ArrayList<neiKanInfoInfo> list2 = new ArrayList<neiKanInfoInfo>();
    private neiKaninfoToupiaoAdapter adapter2;
    private ListView mListView_toupiao;
    private Button bt_neikaninfo_toupiao;
    private TextView tv_dianzanshu, tv_neikaninfo_title,
            neikaninfo_username_tv, neikaninfo_textsize_tv,/* , tv_readcishu, */
            tv_toupiao_title, neikaninfo_pinglushu_tv, neikaninfo_qishu_tv;
    private WebView wb_neikaninfo_content;
    private ImageView iv_textsize, neikaninfo_xiepinglun_iv,
            neikaninfo_dianzanblack_iv, iv_shoucangliang,
            neikaninfo_pinglunshu_iv;
//    private FrontiaSocialShare mSocialShare;
//    private FrontiaSocialShareContent mImageContent = new FrontiaSocialShareContent();
    private PeriodsArticleRela periodsrela;
    private ArticleListNK.DataBean aDataBean;
    // private neiKanInfoInfo info;

    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                // initUI();
                adapter.notifyDataSetChanged();
            } else if (msg.what == 0) {
                Toast.makeText(neiKanInfoActivity.this, "数据获取异常", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(neiKanInfoActivity.this, "子评论添加成功 ", Toast.LENGTH_SHORT)
                        .show();
            } else if (msg.what == 101) {
                iv_shoucang.setVisibility(View.GONE);
                iv_shoucangliang.setVisibility(View.VISIBLE);
            } else if (msg.what == 102) {
                iv_shoucang.setVisibility(View.GONE);
                iv_shoucangliang.setVisibility(View.VISIBLE);
            } else if (msg.what == 103) {
                iv_shoucang.setVisibility(View.VISIBLE);
                iv_shoucangliang.setVisibility(View.GONE);
            } else if (msg.what == 3) {
                neikaninfo_dianzanblack_iv.setVisibility(View.GONE);
                neikaninfo_dianzanliang_iv.setVisibility(View.VISIBLE);
                tv_dianzanshu.setText((Integer.parseInt(tv_dianzanshu
                        .getText().toString()) + 1) + "");
                /**
                 * 发送广播
                 */
                Intent intent = new Intent();
                intent.setAction("action.broadcast");
                sendBroadcast(intent);
                // periodsrela.setPraisecount((Integer.parseInt(periodsrela.getPraisecount())
                // + 1)+"");
            } else if (msg.what == 4) {
                Toast.makeText(neiKanInfoActivity.this, "点赞失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 5) {
                rl_neikaninfo_more.setVisibility(View.VISIBLE);
            } else if (msg.what == 6) {
                Toast.makeText(neiKanInfoActivity.this, "评论添加失败 ",
                        Toast.LENGTH_SHORT).show();
            }

            return false;
        }
    });

    private ArrayList<ontherPinglunInfo> ontherPinlunList = new ArrayList<ontherPinglunInfo>();
    private Discuss discuss;
// 当前选中的位置
// private int toupiaoPosition;
    private Magazine magazine;
    private Periods periods;

    private FrameLayout video_fullView; // 全屏时视频加载view
    private View xCustomView;
    private ProgressDialog waitdialog = null;
    private CustomViewCallback xCustomViewCallback;
    private myWebChromeClient xwebchromeclient;

    /**
     * 用来控制字体大小
     */
    int fontSize = 2;
    private WebSettings webSettings;
    int falg = 0;

    private PopupWindow pop;
    private View layout;

    public static int getPhoneAndroidSDK() {
        // TODO Auto-generated method stub
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;

    }

    private String contextArticle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neikaninfo);

        if (getPhoneAndroidSDK() > 14) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

        // 注册刷新数据广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshMyAddress");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        periodsrela = (PeriodsArticleRela) getIntent().getSerializableExtra(
                "periodsrela");
        aDataBean = (ArticleListNK.DataBean) getIntent().getParcelableExtra(
                "ArticleListNK");
        magazine = (Magazine) getIntent().getSerializableExtra("Magazine");
        periods = (Periods) getIntent().getSerializableExtra("Periods");

        if (DianzanTubiao.chaxun(Integer.parseInt(periodsrela.getArticleid()))) {
            periodsrela.setFlag(1);
        } else {
            periodsrela.setFlag(0);
        }

        initUI();
        initData();
        
        SQLiteDatabase dbRead = MyApplication.helper.getReadableDatabase();
        Cursor c = dbRead.query("article_info", null, "_id=?", new String[]{aDataBean.getId() + ""}, null, null, null);
        if (c.moveToFirst()) {
            contextArticle = c.getString(c.getColumnIndex("content"));
        } else {
            NKUpload nkUpload = NKUpload.getNKUpload();
            nkUpload.NKArticle(this,aDataBean,wb_neikaninfo_content);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (contextArticle != null) {
            webLoad();
        }
    }

    private void webLoad(){
        String strUrl = "";
//        final String str = periodsrela.getContent();
        final String str = contextArticle;

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

//        Log.e("neikaninfo_Url", periodsrela.getContent() + strUrl);
        Log.e("neikaninfo_Url", contextArticle + strUrl);
        //上一页请求到的网页。
//        wb_neikaninfo_content.loadDataWithBaseURL(null, periodsrela.getContent() + strUrl, "text/html", "utf-8", null);
        wb_neikaninfo_content.loadDataWithBaseURL(null,
                contextArticle + strUrl, "text/html", "utf-8", null);
    }

    /**
     * 增加阅读次数接口
     */
    private void addreadcount() {
//        JSONObject jsonObj = new JSONObject();
//        try {
//            // jsonObj.put("readcount", 1);
//            jsonObj.put("articleid", periodsrela.getArticleid());
//            byte[] bytes;
//            ByteArrayEntity emEntity = null;
//            try {
//                bytes = jsonObj.toString().getBytes("utf-8");
//                emEntity = new ByteArrayEntity(bytes);
//
//                Log.e("neikan_增加阅读次数接口_out", jsonObj.toString());
//
//                YzyHttpClient.post(neiKanInfoActivity.this,
//                        HttpUrlConfig.updateArticl, emEntity,
//                        new AsyncHttpResponseHandler() {
//                            @SuppressWarnings("deprecation")
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("neikan_增加阅读次数接口_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(neiKanInfoActivity.this,
//                                            CacheUtil.logout,
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(
//                                            neiKanInfoActivity.this,
//                                            LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(int statusCode,
//                                                  Throwable error, String content) {
//                                // TODO Auto-generated method stub
//                                super.onFailure(statusCode, error, content);
//                                System.out.println("fail");
//                            }
//                        });
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//
//            e.printStackTrace();
//        }
    }

    private void initData() {
        // 添加阅读数记录
        addreadcount();
        if (periodsrela.getIsVote() == 1) {
            gettoupiao();
        } else {
            ll_toupiao.setVisibility(View.GONE);
        }
        pinglunList();
        queryshoucang();
    }

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshMyAddress")) {
                list.clear();
                pinglunList();
            }
        }
    };

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.wb_neikaninfo_content.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.wb_neikaninfo_content.loadUrl("file:///android_asset/nonexistent.html");
        unregisterReceiver(mRefreshBroadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @SuppressLint({"ShowToast", "JavascriptInterface"})
    private void initUI() {
        /**
         * 初始化分享控件
         */
        initShareUi();
        /**
         * 初始化控件
         */
        neikaninfo_qishu_tv = (TextView) findViewById(R.id.neikaninfo_qishu_tv);
        neikaninfo_textsize_tv = (TextView) findViewById(R.id.neikaninfo_textsize_tv);
        neikaninfo_pinglushu_tv = (TextView) findViewById(R.id.neikaninfo_pinglushu_tv);
        neikaninfo_pinglunshu_iv = (ImageView) findViewById(R.id.neikaninfo_pinglunshu_iv);
        neikaninfo_pinglun_ll = (LinearLayout) findViewById(R.id.neikaninfo_pinglun_ll);
        neikaninfo_goback_iv = (ImageView) findViewById(R.id.neikaninfo_goback_iv);
        ll_toupiao = (LinearLayout) findViewById(R.id.ll_toupiao);
        tv_toupiao_title = (TextView) findViewById(R.id.tv_toupiao_title);
        neikaninfo_username_tv = (TextView) findViewById(R.id.neikaninfo_username_tv);
        // tv_readcishu = (TextView) findViewById(R.id.tv_readcishu);
        iv_shoucangliang = (ImageView) findViewById(R.id.iv_shoucangliang);
        neikaninfo_dianzanblack_iv = (ImageView) findViewById(R.id.neikaninfo_dianzanblack_iv);
        iv_shoucang = (ImageView) findViewById(R.id.iv_shoucang);
        tv_neikaninfo_title = (TextView) findViewById(R.id.tv_neikaninfo_title);
        iv_fenxianghong = (ImageView) findViewById(R.id.iv_fenxianghong);
        iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        ll_fenxiang = (LinearLayout) findViewById(R.id.ll_fenxiang);
        tv_dianzanshu = (TextView) findViewById(R.id.tneikaninfo_dianzanshu_tv);
        neikaninfo_xiepinglun_iv = (ImageView) findViewById(R.id.neikaninfo_xiepinglun_iv);
        neikaninfo_dianzanliang_iv = (ImageView) findViewById(R.id.neikaninfo_dianzanliang_iv);

        if (periodsrela.getFlag() == 1) {
            neikaninfo_dianzanblack_iv.setVisibility(View.GONE);
            neikaninfo_dianzanliang_iv.setVisibility(View.VISIBLE);
        } else if (periodsrela.getFlag() == 0) {
            neikaninfo_dianzanblack_iv.setVisibility(View.VISIBLE);
            neikaninfo_dianzanliang_iv.setVisibility(View.GONE);
        }

        // iv_textsize = (ImageView) findViewById(R.id.neikaninfo_textsize_iv);
        /**
         * 设置数据
         */
        if (EmptyUtil.IsNotEmpty(periodsrela.getPraisecount())) {
            tv_dianzanshu.setText(periodsrela.getPraisecount());
        } else {
            tv_dianzanshu.setText("0");
        }
        // if (EmptyUtil.IsNotEmpty(periodsrela.getReadcount())) {
        // tv_readcishu.setText("阅读" + periodsrela.getReadcount() + "次");
        // } else {
        // tv_readcishu.setText("阅读0次");
        // }

        // if (magazine != null) {
        // if (EmptyUtil.IsNotEmpty(magazine.getUsername())) {
        // neikaninfo_username_tv.setText("作者：" + magazine.getUsername());
        // } else {
        // neikaninfo_username_tv.setVisibility(NotifyListView.GONE);
        // }
        // } else {
        // neikaninfo_username_tv.setVisibility(NotifyListView.GONE);
        // }
        String subTitle = "";
        if (EmptyUtil.IsNotEmpty(periodsrela.getReadcount())) {
            subTitle = "阅读" + periodsrela.getReadcount() + "次 ";
        } else {
            subTitle = "阅读0次";
        }
        if (periodsrela != null) {
            if (EmptyUtil.IsNotEmpty(periodsrela.getAuthor())) {
                // neikaninfo_username_tv.setText(periodsrela.getAuthor());
                subTitle += ("   作者:" + periodsrela.getAuthor());
            } else {
                // neikaninfo_username_tv.setVisibility(NotifyListView.GONE);
            }
        } else {
            // neikaninfo_username_tv.setVisibility(NotifyListView.GONE);
        }

        neikaninfo_username_tv.setText(subTitle);

        // tv_neikaninfo_time.setText(TimeUtil.getStrTime3(periodsrela
        // .getCreatetime()));
        if (periods != null) {
            if (EmptyUtil.IsNotEmpty(periods.getPeriodsname())) {
                neikaninfo_qishu_tv.setText(periods.getPeriodsname());
            } else {
                neikaninfo_qishu_tv.setText("内刊内容");
            }
        } else {
            neikaninfo_qishu_tv.setText("内刊内容");
        }

        tv_neikaninfo_title.setText(periodsrela.getTitle());

        wb_neikaninfo_content = (WebView) findViewById(R.id.wb_neikaninfo_content);
        // 添加js交互接口类，并起别名 imagelistner
        if (android.os.Build.VERSION.SDK_INT >= 17) {
//            wb_neikaninfo_content.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
//            wb_neikaninfo_content.addJavascriptInterface(new JsInterface(this), "imageClick"); //JS交互
        }
        video_fullView = (FrameLayout) findViewById(R.id.video_fullView);

        // 设置WebView属性，能够执行Javascript脚本
        webSettings = wb_neikaninfo_content.getSettings();
        // webSettings
        // .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wb_neikaninfo_content.setVerticalScrollBarEnabled(false);
        wb_neikaninfo_content.setVerticalScrollbarOverlay(false);
        wb_neikaninfo_content.setHorizontalScrollBarEnabled(false);
        wb_neikaninfo_content.setHorizontalScrollbarOverlay(false);
        webSettings.setJavaScriptEnabled(true);
        // 适应屏幕，内容将自动缩放
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

        wb_neikaninfo_content.setWebChromeClient(new myWebChromeClient());
        wb_neikaninfo_content.setWebViewClient(new MyWebViewClient());

        webSettings.setTextSize(TextSize.NORMAL);

        // setupWebView();
        if (webSettings.getTextSize() == TextSize.SMALLER) {
            fontSize = 1;
        } else if (webSettings.getTextSize() == TextSize.NORMAL) {
            fontSize = 2;
        } else if (webSettings.getTextSize() == TextSize.LARGEST) {
            fontSize = 3;
        }
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rl_neikaninfo_more = (RelativeLayout) findViewById(R.id.rl_neikaninfo_more);
        bt_neikaninfo_toupiao = (Button) findViewById(R.id.bt_neikaninfo_toupiao);
        mListView_toupiao = (ListView) findViewById(R.id.mListView_toupiao);
        mListView = (ListView) findViewById(R.id.mListView);
        adapter2 = new neiKaninfoToupiaoAdapter(this, list2);
        mListView_toupiao.setAdapter(adapter2);
        // 去掉分割线
        mListView_toupiao.setDivider(null);
        adapter = new neiKanInfoAdapter(this, list);
        mListView.setAdapter(adapter);

        // if(CacheUtil.userid>0){
        // neikaninfo_dianzan_iv.setVisibility(NotifyListView.GONE);
        // iv_dianzanliang.setVisibility(NotifyListView.VISIBLE);
        // }

        iv_shoucang.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                shouchang();
                // iv_shoucang.setVisibility(NotifyListView.GONE);
                // iv_shoucangliang.setVisibility(NotifyListView.VISIBLE);
            }
        });

        iv_shoucangliang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(neiKanInfoActivity.this)
                        .setTitle("提示")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("确定取消此收藏？")
                        .setNegativeButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        cancelShouCang();

                                    }
                                })
                        .setPositiveButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                }).show();

            }
        });
        bt_neikaninfo_toupiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // for (int i = 0; i < list2.size(); i++) {
                // list2.get(i).setFlag(1);
                // }
                // adapter2.notifyDataSetChanged();
                // bt_neikaninfo_toupiao.setVisibility(NotifyListView.GONE);
                Vote();

            }
        });
        mListView_toupiao.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // toupiaoPosition = position;
                adapter2.addSelectIndex(position);
            }
        });

        /**
         * 点击评论按钮查看更多
         */
        neikaninfo_pinglunshu_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(neiKanInfoActivity.this,
                        allPinglunActivity.class);
                it.putExtra("PeriodsArticleRela", periodsrela);
                startActivity(it);

            }
        });

        /**
         * 查看更多
         */
        rl_neikaninfo_more.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent it = new Intent(neiKanInfoActivity.this,
                        allPinglunActivity.class);
                it.putExtra("PeriodsArticleRela", periodsrela);
                startActivity(it);
            }
        });

        neikaninfo_goback_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        /**
         * 字体
         */
        neikaninfo_textsize_tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 弹出字体选择dialog
                /**
                 * abApplication 失效 修改为 getApplication()
                 */
//                showPopWindow(abApplication, v);
                showPopWindow(neiKanInfoActivity.this.getApplication(), v);

            }
        });

        // /**
        // * 字体
        // */
        // iv_textsize.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(NotifyListView v) {
        // // 弹出字体选择dialog
        // showPopWindow(abApplication, v);
        //
        // }
        // });
        //
        /**
         * 写评论
         */
        neikaninfo_xiepinglun_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // 文章是否可以评论
                if (EmptyUtil.IsNotEmpty(periodsrela.getDiscussflag() + "")) {
                    if (periodsrela.getDiscussflag() == 1) {
                        popupwindow();
                    } else if (periodsrela.getDiscussflag() == 2) {
                        Toast.makeText(neiKanInfoActivity.this, "已关闭评论！",
                                Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        /**
         * 点赞
         */

        neikaninfo_dianzanblack_iv.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                if (periodsrela.getFlag() == 1) {
                    Toast.makeText(neiKanInfoActivity.this, "您已经点赞过该文章了！",
                            Toast.LENGTH_SHORT).show();
                } else if (periodsrela.getFlag() == 0) {
                    adddianzanshu();
                }

                // iv_dianzan.setVisibility(NotifyListView.GONE);
                // iv_dianzanliang.setVisibility(NotifyListView.VISIBLE);
                // tv_dianzanshu.setText((Integer.parseInt(tv_dianzanshu.getText()
                // .toString()) + 1) + "");

            }
        });

        neikaninfo_dianzanliang_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (periodsrela.getFlag() == 1) {
                    Toast.makeText(neiKanInfoActivity.this, "您已经点赞过该文章了！",
                            Toast.LENGTH_SHORT).show();
                } else if (periodsrela.getFlag() == 0) {
                    adddianzanshu();
                }

                // Toast.makeText(neiKanInfoActivity.this, "您已经点过赞了",
                // Toast.LENGTH_SHORT).show();
            }
        });

        iv_fenxiang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ll_fenxiang.setVisibility(View.VISIBLE);
                iv_fenxiang.setVisibility(View.GONE);
                iv_fenxianghong.setVisibility(View.VISIBLE);
            }
        });
        iv_fenxianghong.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ll_fenxiang.setVisibility(View.GONE);
                iv_fenxiang.setVisibility(View.VISIBLE);
                iv_fenxianghong.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    class JsInterface {
        Context context;

        public JsInterface(Context context) {
            this.context = context;
        }

        // 查看图片url
        public void click(String url) {
            // Intent intent = new Intent(context,ImgUrlActivity.class);
            // intent.putExtra("url", url);
            // startActivity(intent);
            // Toast.makeText(context, "33333", Toast.LENGTH_LONG).show();
        }
    }

    // float x,y;
    // @Override
    // public boolean onTouchEvent(MotionEvent event) {
    // // //通过wenview的touch来响应web上的图片点击事件
    // float density = getResources().getDisplayMetrics().density; //屏幕密度
    // float touchX = event.getX() / density; //必须除以屏幕密度
    // float touchY = event.getY() / density;
    // if(event.getAction() == MotionEvent.ACTION_DOWN){
    // x = touchX;
    // y = touchY;
    // }
    //
    // if(event.getAction() == MotionEvent.ACTION_UP){
    // float dx = Math.abs(touchX-x);
    // float dy = Math.abs(touchY-y);
    // if(dx<10.0/density&&dy<10.0/density){
    // clickImage(touchX,touchY);
    // }
    // }
    // return false;
    // }

    private void clickImage(float touchX, float touchY) {
        // 通过触控的位置来获取图片URL
        String js = "javascript:(function(){"
                + "var  obj=document.elementFromPoint(" + touchX + "," + touchY
                + ");" + "if(obj.src!=null){"
                + " window.imageClick.click(obj.src);}" + "})()";
        wb_neikaninfo_content.loadUrl(js);
    }

    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        public void openImage(String img) {
            //
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(img), "image/*");

            neiKanInfoActivity.this.startActivity(intent);
        }
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        wb_neikaninfo_content.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"video\"); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "alert (\"This document contains \")"
                + "    objs[i].onclick=function()  " + "    {  "
                + "        window.imagelistner.openImage(this.src);  "
                + "    }  " + "}" + "})()");
    }

    public class myWebChromeClient extends WebChromeClient {
        private View xprogressvideo;

        // 播放网络视频时全屏会被调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            wb_neikaninfo_content.setVisibility(View.INVISIBLE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            video_fullView.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            video_fullView.setVisibility(View.VISIBLE);
        }

        // 视频播放退出全屏会被调用的
        @Override
        public void onHideCustomView() {
            if (xCustomView == null)// 不是全屏播放状态
                return;
            setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);
            video_fullView.removeView(xCustomView);
            xCustomView = null;
            video_fullView.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
            wb_neikaninfo_content.setVisibility(View.VISIBLE);
        }

        // 视频加载时进程loading
        @Override
        public View getVideoLoadingProgressView() {
            // if (xprogressvideo == null) {
            // LayoutInflater inflater = LayoutInflater
            // .from(HomeActivity.this);
            // xprogressvideo = inflater.inflate(
            // R.layout.video_loading_progress, null);
            // }
            // return xprogressvideo;
            return super.getVideoLoadingProgressView();
        }

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".mp4") || url.endsWith(".mov")) {
                Intent intent = new Intent(neiKanInfoActivity.this, PlayVideoActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            view.getSettings().setJavaScriptEnabled(true);

            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
//            addImageClickListner();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    }

    /**
     * 投票接口
     */
    private void Vote() {
//        JSONObject Obj = new JSONObject();
//        try {
//            int size = adapter2.getSelectList().size();
//            if (size <= 0) {
//                return;
//            }
//            String infoidStr = "";
//            for (int i = 0; i < size; i++) {
//                int index = adapter2.getSelectList().get(i);
//                infoidStr += (list2.get(index).getInfoid() + ",");
//            }
//            Obj.put("infoidStr", infoidStr);
//            Obj.put("voteid", list2.get(0).getVoteid());
//            Obj.put("userid", CacheUtil.userid);
//            // Obj.put("infoid", list2.get(toupiaoPosition).getInfoid());
//            // Obj.put("voteid", list2.get(toupiaoPosition).getVoteid());
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            try {
//                bytes = Obj.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("neikan_投票接口_out", Obj.toString());
//
//                YzyHttpClient.post(neiKanInfoActivity.this, HttpUrlConfig.vote,
//                        entity, new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("neikan_投票接口_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(neiKanInfoActivity.this,
//                                            CacheUtil.logout,
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(
//                                            neiKanInfoActivity.this,
//                                            LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    try {
//                                        JSONObject Obj = new JSONObject(content);
//                                        if ("insert_success".equals(Obj
//                                                .getString("message"))) {
//                                            // 投票成功后
//                                            for (int i = 0; i < list2.size(); i++) {
//                                                list2.get(i).setFlag(1);
//                                            }
//                                            // 选择投票项加1
//                                            int selectVoteCount = 0;
//                                            ArrayList<Integer> list = adapter2
//                                                    .getSelectList();
//                                            for (Integer i : list) {
//                                                if (EmptyUtil.IsNotEmpty(list2
//                                                        .get(i).getVotecount())) {
//                                                    selectVoteCount = Integer
//                                                            .parseInt(list2
//                                                                    .get(i)
//                                                                    .getVotecount()) + 1;
//                                                    list2.get(i).setVotecount(
//                                                            selectVoteCount
//                                                                    + "");
//                                                } else {
//                                                    list2.get(i).setVotecount(
//                                                            "1");
//                                                }
//                                                // 投票总数加1
//                                                adapter2.voteCount += 1;
//                                            }
//                                            adapter2.notifyDataSetChanged();
//                                            bt_neikaninfo_toupiao
//                                                    .setVisibility(NotifyListView.GONE);
//                                            Toast.makeText(
//                                                    neiKanInfoActivity.this,
//                                                    "谢谢你的投票",
//                                                    Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(
//                                                    neiKanInfoActivity.this,
//                                                    "对不起你的投票失败，请重新投",
//                                                    Toast.LENGTH_SHORT).show();
//                                        }
//                                    } catch (JSONException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//                        });
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    /**
     * 获取投票信息接口
     */
    private void gettoupiao() {
//        JSONObject JSONobject = new JSONObject();
//        try {
//            JSONobject.put("articleid", periodsrela.getArticleid());
//            JSONobject.put("userid", CacheUtil.userid);
//
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            try {
//                bytes = JSONobject.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("neikan_投票信息_out", JSONobject.toString());
//
//                YzyHttpClient.post(neiKanInfoActivity.this,
//                        HttpUrlConfig.toupiaoinfo, entity,
//                        new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("neikan_投票信息_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(neiKanInfoActivity.this,
//                                            CacheUtil.logout,
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(
//                                            neiKanInfoActivity.this,
//                                            LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    Gson g = new Gson();
//                                    try {
//                                        JSONObject Obj = new JSONObject(content);
//                                        /**
//                                         * 1已投 2未投
//                                         */
//                                        String isvoted = Obj
//                                                .getString("isvoted");
//                                        JSONArray mlist = Obj
//                                                .getJSONArray("rows");
//                                        if (mlist.length() > 0) {
//                                            // for (int i = 0; i <
//                                            // mlist.length(); i++) {
//
//                                            neiKanInfoInfo info = g.fromJson(
//                                                    mlist.getString(0),
//                                                    neiKanInfoInfo.class);
//                                            ArrayList<neiKanInfoInfo> mylist = info
//                                                    .getVoteinfoList();
//                                            for (int j = 0; j < mylist.size(); j++) {
//                                                mylist.get(j).setIsvoted(
//                                                        isvoted);
//                                            }
//                                            adapter2.setMaxSelect(info
//                                                    .getSelectcount());
//                                            list2.clear();
//                                            list2.addAll(mylist);
//                                            tv_toupiao_title.setText(info
//                                                    .getVotedesc());
//                                            // }
//                                        }
//                                        /**
//                                         * voteCount投票总数变量
//                                         */
//                                        int voteCount = 0;
//
//                                        for (int i = 0; i < list2.size(); i++) {
//                                            if (EmptyUtil.IsNotEmpty(list2.get(
//                                                    i).getVotecount())) {
//                                                voteCount = voteCount
//                                                        + Integer
//                                                        .parseInt(list2
//                                                                .get(i)
//                                                                .getVotecount());
//
//                                            }
//
//                                        }
//                                        if ("1".equals(isvoted)) {
//                                            bt_neikaninfo_toupiao
//                                                    .setVisibility(NotifyListView.GONE);
//                                        }
//                                        if (list2.size() > 0) {
//                                            adapter2.voteCount = voteCount;
//                                            adapter2.notifyDataSetChanged();
//                                            // 显示投票UI
//                                            ll_toupiao
//                                                    .setVisibility(NotifyListView.VISIBLE);
//                                        }
//
//                                    } catch (JSONException e) {
//
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(int statusCode,
//                                                  Throwable error, String content) {
//                                super.onFailure(statusCode, error, content);
//                                System.out.println(content);
//                            }
//
//                            @Override
//                            public void onFinish() {
//                                // TODO Auto-generated method stub
//                                super.onFinish();
//                                System.out.println("完成");
//                            }
//                        });
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    /**
     * 增加文章点赞数
     */
    private void adddianzanshu() {
//        JSONObject JSOBJ = new JSONObject();
//        try {
//            JSOBJ.put("articleid", periodsrela.getArticleid());
//            JSOBJ.put("praisecount", 1);
//            JSOBJ.put("userid", CacheUtil.userid);
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            try {
//                bytes = JSOBJ.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("neikan_文章点赞_out", JSOBJ.toString());
//
//                YzyHttpClient.post(neiKanInfoActivity.this,
//                        HttpUrlConfig.adddianzanshu, entity,
//                        new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("neikan_文章点赞_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(neiKanInfoActivity.this,
//                                            CacheUtil.logout,
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(
//                                            neiKanInfoActivity.this,
//                                            LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    try {
//                                        JSONObject Obj = new JSONObject(content);
//                                        String message = Obj
//                                                .getString("message");
//
//                                        String msg = Obj.getString("msg");
//
//                                        if (("update_success").equals(message)) {
//                                            DianzanTubiao.add(periodsrela
//                                                    .getArticleid());
//                                            handler.sendEmptyMessage(3);
//                                        } else {
//                                            if (("您已经点赞过该文章了！").equals(msg)) {
//                                                Toast.makeText(
//                                                        neiKanInfoActivity.this,
//                                                        msg, Toast.LENGTH_SHORT)
//                                                        .show();
//                                                DianzanTubiao.add(periodsrela
//                                                        .getArticleid());
//                                                neikaninfo_dianzanblack_iv
//                                                        .setVisibility(NotifyListView.GONE);
//                                                neikaninfo_dianzanliang_iv
//                                                        .setVisibility(NotifyListView.VISIBLE);
//                                            } else {
//                                                Toast.makeText(
//                                                        neiKanInfoActivity.this,
//                                                        msg, Toast.LENGTH_SHORT)
//                                                        .show();
//                                            }
//                                        }
//
//                                    } catch (JSONException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Throwable error,
//                                                  String content) {
//                                // TODO Auto-generated method stub
//                                super.onFailure(error, content);
//                                handler.sendEmptyMessage(4);
//                            }
//
//                            @Override
//                            public void onFinish() {
//                                // TODO Auto-generated method stub
//                                super.onFinish();
//                            }
//                        });
//            } catch (UnsupportedEncodingException e) {
//
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//
//            e.printStackTrace();
//        }
    }

    /**
     * queryCollectionList.do ----传文章ID和用户ID，判断是否收藏过
     */
    private void queryshoucang() {
//        JSONObject JSOBJ = new JSONObject();
//        try {
//            JSOBJ.put("articleid", periodsrela.getArticleid());
//            JSOBJ.put("userid", CacheUtil.userid);
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            try {
//                bytes = JSOBJ.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("neikan_判断收藏_out", JSOBJ.toString());
//
//                YzyHttpClient.post(neiKanInfoActivity.this,
//                        HttpUrlConfig.queryshoucang, entity,
//                        new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("neikan_判断收藏_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(neiKanInfoActivity.this,
//                                            CacheUtil.logout,
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(
//                                            neiKanInfoActivity.this,
//                                            LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    try {
//                                        JSONObject Obj = new JSONObject(content);
//                                        JSONArray mlist = Obj
//                                                .getJSONArray("rows");
//
//                                        if (mlist.length() > 0) {
//                                            for (int i = 0; i < mlist.length(); i++) {
//                                                discuss = new Discuss();
//                                                JSONObject userObject = mlist
//                                                        .getJSONObject(i);
//                                                discuss.setCollectionid(userObject
//                                                        .getString("collectionid"));
//                                            }
//
//                                            handler.sendEmptyMessage(101);
//                                        } else {
//
//                                        }
//
//                                    } catch (JSONException e) {
//
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Throwable error,
//                                                  String content) {
//
//                                super.onFailure(error, content);
//                            }
//                        });
//            } catch (UnsupportedEncodingException e) {
//
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//
//            e.printStackTrace();
//        }
    }

    /**
     * 取消收藏接口
     */
    private void cancelShouCang() {
//        JSONObject Obj = new JSONObject();
//
//        try {
//            Obj.put("collectionid", discuss.getCollectionid());
//            Obj.put("userid", CacheUtil.userid);
//        } catch (JSONException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        byte[] bytes;
//        ByteArrayEntity entity = null;
//
//        try {
//            bytes = Obj.toString().getBytes("utf-8");
//            entity = new ByteArrayEntity(bytes);
//
//            Log.e("neikan_取消收藏_out", Obj.toString());
//
//            YzyHttpClient.post(neiKanInfoActivity.this, HttpUrlConfig.cancelshoucang, entity,
//                    new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//
//                            Log.e("neikan_取消收藏_out", content);
//
//                            super.onSuccess(statusCode, content);
//                            if (statusCode == 499) {
//                                Toast.makeText(neiKanInfoActivity.this,
//                                        CacheUtil.logout, Toast.LENGTH_SHORT)
//                                        .show();
//                                Intent intent = new Intent(
//                                        neiKanInfoActivity.this,
//                                        LoginActivity.class);
//                                startActivity(intent);
//                                onFinish();
//                            } else if (statusCode == 200) {
//                                try {
//                                    JSONObject UserObj = new JSONObject(content);
//                                    if ("delete_success".equals(UserObj
//                                            .getString("message"))) {
//                                        handler.sendEmptyMessage(103);
//                                        Toast.makeText(neiKanInfoActivity.this,
//                                                "操作成功", Toast.LENGTH_SHORT)
//                                                .show();
//                                    } else {
//                                        Toast.makeText(neiKanInfoActivity.this,
//                                                "操作失败，请重新操作",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (JSONException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                    Toast.makeText(neiKanInfoActivity.this,
//                                            "操作失败，请重新操作", Toast.LENGTH_SHORT)
//                                            .show();
//                                }
//                            }
//                        }
//
//                    });
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    /**
     * 收藏接口
     */
    private void shouchang() {
//        JSONObject JSOBJ = new JSONObject();
//        try {
//            JSOBJ.put("articleid", periodsrela.getArticleid());
//            JSOBJ.put("userid", CacheUtil.userid);
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            // 处理保存数据中文乱码问题
//            try {
//                bytes = JSOBJ.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("neikan_收藏接口_out", JSOBJ.toString());
//
//                YzyHttpClient.post(neiKanInfoActivity.this,
//                        HttpUrlConfig.shoucang, entity,
//                        new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("neikan_收藏接口_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(neiKanInfoActivity.this,
//                                            CacheUtil.logout,
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(
//                                            neiKanInfoActivity.this,
//                                            LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    try {
//                                        JSONObject jsObj = new JSONObject(
//                                                content);
//                                        if ("add_success".equals(jsObj
//                                                .getString("message"))) {
//                                            String collectionid = jsObj
//                                                    .getJSONObject("collection")
//                                                    .getString("collectionid");
//                                            if (discuss != null) {
//                                                discuss.setCollectionid(collectionid);
//                                            } else {
//                                                discuss = new Discuss();
//                                                discuss.setCollectionid(collectionid);
//                                            }
//                                            handler.sendEmptyMessage(102);
//                                            Toast.makeText(
//                                                    neiKanInfoActivity.this,
//                                                    "收藏成功", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(
//                                                    neiKanInfoActivity.this,
//                                                    "操作失败，请重新操作",
//                                                    Toast.LENGTH_SHORT).show();
//                                        }
//                                    } catch (JSONException e) {
//                                        Toast.makeText(neiKanInfoActivity.this,
//                                                "操作失败，请重新操作",
//                                                Toast.LENGTH_SHORT).show();
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        });
//            } catch (UnsupportedEncodingException e) {
//
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//
//            e.printStackTrace();
//        }
    }

    /**
     * 获取评论列表
     */
    private void pinglunList() {
//        JSONObject JSOBJ = new JSONObject();
//        try {
//            JSOBJ.put("articleid", periodsrela.getArticleid());
//            JSOBJ.put("page", 1);
//            JSOBJ.put("pagesize", 10);
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            // 处理保存数据中文乱码问题
//            try {
//                bytes = JSOBJ.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("neikan_获取评论_out", JSOBJ.toString());
//
//                YzyHttpClient.post(neiKanInfoActivity.this,
//                        HttpUrlConfig.pinglunshu, entity,
//                        new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("neikan_获取评论_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(neiKanInfoActivity.this,
//                                            CacheUtil.logout,
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(
//                                            neiKanInfoActivity.this,
//                                            LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    Gson gson = new Gson();
//                                    try {
//                                        JSONObject Obj = new JSONObject(content);
//                                        String total = Obj.getString("total");
//                                        JSONArray mlist = Obj
//                                                .getJSONArray("rows");
//
//                                        if (EmptyUtil.IsNotEmpty(total)) {
//                                            neikaninfo_pinglushu_tv
//                                                    .setBackgroundColor(getBaseContext()
//                                                            .getResources()
//                                                            .getColor(
//                                                                    R.color.red));
//                                            neikaninfo_pinglushu_tv
//                                                    .setText(total);
//
//                                        } else {
//                                            neikaninfo_pinglushu_tv
//                                                    .setVisibility(NotifyListView.GONE);
//
//                                        }
//                                        if (mlist.length() > 0) {
//                                            list.clear();
//                                            neikaninfo_pinglun_ll
//                                                    .setVisibility(NotifyListView.VISIBLE);
//                                        } else {
//                                            neikaninfo_pinglun_ll
//                                                    .setVisibility(NotifyListView.GONE);
//                                        }
//
//                                        for (int i = 0; i < mlist.length(); i++) {
//                                            if (i < 2) {
//                                                Discuss d = gson.fromJson(
//                                                        mlist.getString(i),
//                                                        Discuss.class);
//                                                list.add(d);
//                                            } else {
//                                                // 更多评论按钮显示
//                                                handler.sendEmptyMessage(5);
//
//                                                break;
//                                            }
//                                        }
//                                        handler.sendEmptyMessage(1);
//                                    } catch (JSONException e) {
//                                        handler.sendEmptyMessage(0);
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        });
//            } catch (UnsupportedEncodingException e) {
//                handler.sendEmptyMessage(0);
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//            handler.sendEmptyMessage(0);
//            e.printStackTrace();
//        }
    }

    private void initShareUi() {
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        iv_pengyouquan = (ImageView) findViewById(R.id.iv_pengyouquan);
        iv_duanxin = (ImageView) findViewById(R.id.iv_duanxin);
        iv_weixin = (ImageView) findViewById(R.id.iv_weixin);
//        // 百度分享
//        mSocialShare = Frontia.getSocialShare();
//        mSocialShare.setContext(this);
//        mSocialShare.setClientId(MediaType.SINAWEIBO.toString(),
//                HttpUrlConfig.sinaAppKey);
//        mSocialShare.setClientId(MediaType.QZONE.toString(), "100358052");
//        mSocialShare.setClientId(MediaType.QQFRIEND.toString(), "100358052");
//        mSocialShare.setClientName(MediaType.BAIDU.toString(), "百度");
//        mSocialShare.setClientId(MediaType.WEIXIN.toString(),
//                HttpUrlConfig.weixinAppKey);
//        mImageContent.setTitle("红信通分享");
//        mImageContent.setContent("红信通分享");
//        mImageContent.setLinkUrl("http://www.baidu.com");
//        iv_qq.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyListView v) {
//                qzoneShare();
//
//            }
//        });
//        iv_pengyouquan.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyListView v) {
//
//                smsShare();
//            }
//        });
//        iv_duanxin.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyListView v) {
//                SMSShare();
//
//            }
//        });
//        iv_weixin.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyListView v) {
//                weixinShare();
//
//            }
//        });
    }

//    /**
//     * 新浪微博分享
//     */
//    private void sinaWeiboShare() {
//        // TODO Auto-generated method stub
//        mSocialShare.share(mImageContent, MediaType.SINAWEIBO.toString(),
//                new ShareListener(), true);
//    }
//
//    /**
//     * 短信分享
//     */
//    private void SMSShare() {
//        // TODO Auto-generated method stub
//        mSocialShare.share(mImageContent, MediaType.SMS.toString(),
//                new ShareListener(), true);
//    }
//
//    private void qqWeiboShare() {
//        // TODO Auto-generated method stub
//        mSocialShare.share(mImageContent, MediaType.QQWEIBO.toString(),
//                new ShareListener(), true);
//    }
//
//    private void qzoneShare() {
//
//        mSocialShare.share(mImageContent, MediaType.QZONE.toString(),
//                new ShareListener(), true);
//    }
//
//    private void qqShare() {
//        // TODO Auto-generated method stub
//        mSocialShare.share(mImageContent, MediaType.QQFRIEND.toString(),
//                new ShareListener(), true);
//    }
//
//    /**
//     * 微信好友分享
//     */
//    private void weixinShare() {
//
//        mSocialShare.share(mImageContent, MediaType.WEIXIN_FRIEND.toString(),
//                new ShareListener(), true);
//    }
//
//    /**
//     * 微信朋友圈分享
//     */
//    private void smsShare() {
//        // TODO Auto-generated method stub
//        mSocialShare.share(mImageContent, MediaType.WEIXIN_TIMELINE.toString(),
//                new ShareListener(), true);
//        mImageContent.setTitle("豫头条分享");
//    }
//
//    private void renrenShare() {
//        // TODO Auto-generated method stub
//        mSocialShare.share(mImageContent, MediaType.RENREN.toString(),
//                new ShareListener(), true);
//    }
//
//    private void baiduShare() {
//        // TODO Auto-generated method stub
//        mSocialShare.share(mImageContent, MediaType.BAIDU.toString(),
//                new ShareListener(), true);
//    }
//
//    private class ShareListener implements FrontiaSocialShareListener {
//
//        @Override
//        public void onSuccess() {
//            Log.d("Test", "share success");
//
//            Toast.makeText(neiKanInfoActivity.this, "恭喜你分享成功！",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onFailure(int errCode, String errMsg) {
//            Log.d("Test", "share errCode " + errCode);
//
//            Toast.makeText(neiKanInfoActivity.this, "分享失败，请重新分享！",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancel() {
//            Log.d("Test", "cancel ");
//
//            Toast.makeText(neiKanInfoActivity.this, "分享取消！", Toast.LENGTH_SHORT)
//                    .show();
//        }
//    }

    /**
     * 字体大小DIAlog
     *
     * @param context
     * @param parent
     */
    @SuppressLint("NewApi")
    private void showPopWindow(Context context, View parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopWindow = inflater.inflate(R.layout.popwindow_font, null,
                false);
        // 宽300 高300
        final PopupWindow popWindow = new PopupWindow(vPopWindow, 300, 150,
                true);
        popWindow.setWidth(LayoutParams.MATCH_PARENT);
        final Button bt_font1 = (Button) vPopWindow.findViewById(R.id.bt_font1);
        final Button bt_font2 = (Button) vPopWindow.findViewById(R.id.bt_font2);
        final Button bt_font3 = (Button) vPopWindow.findViewById(R.id.bt_font3);

        if (falg == 0) {
            bt_font1.setBackground(getResources().getDrawable(
                    R.mipmap.huiseyuan));
            bt_font2.setBackground(getResources().getDrawable(
                    R.mipmap.huiseyuan));
            bt_font3.setBackground(getResources().getDrawable(
                    R.mipmap.hongseyuan));
        } else if (falg == 1) {
            bt_font1.setBackground(getResources().getDrawable(
                    R.mipmap.huiseyuan));
            bt_font2.setBackground(getResources().getDrawable(
                    R.mipmap.hongseyuan));
            bt_font3.setBackground(getResources().getDrawable(
                    R.mipmap.huiseyuan));
        } else if (falg == 2) {
            bt_font1.setBackground(getResources().getDrawable(
                    R.mipmap.hongseyuan));
            bt_font2.setBackground(getResources().getDrawable(
                    R.mipmap.huiseyuan));
            bt_font3.setBackground(getResources().getDrawable(
                    R.mipmap.huiseyuan));
        }

        // 设置popwindow区域外点击隐藏popwindow
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);

        bt_font1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                webSettings.setTextSize(TextSize.LARGEST);
                // wb_neikaninfo_content.loadUrl("javascript:MyApp.resize(periodsrela.getContent().height)");
//                 webSettings.setDefaultFontSize();
                falg = 2;
                popWindow.dismiss();

            }
        });

        bt_font2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                webSettings.setTextSize(TextSize.LARGER);
                // wb_neikaninfo_content.loadUrl("javascript:MyApp.resize(periodsrela.getContent().height)");
                falg = 1;
                popWindow.dismiss(); // Close the Pop Window

            }
        });

        bt_font3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                webSettings.setTextSize(TextSize.NORMAL);
                // wb_neikaninfo_content.loadUrl("javascript:MyApp.resize(periodsrela.getContent().height)");
                // System.out.println(wb_neikaninfo_content.getContentHeight());
                falg = 0;
                popWindow.dismiss(); // Close the Pop Window

            }
        });
        // popWindow.showAtLocation(parent, Gravity.TOP, 0, 100);
        // 从parent下面显示
        popWindow.showAsDropDown(parent);
    }

    /**
     * 弹出评论框
     */
    private void popupwindow() {

        // LayoutInflater inflater = (LayoutInflater)
        // getSystemService(LAYOUT_INFLATER_SERVICE);
        // LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        // final NotifyListView layout =
        // mLayoutInflater.inflate(R.layout.xiepinglun_neikan_chuangkou, null);
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        layout = mLayoutInflater.inflate(R.layout.xiepinglun_neikan_chuangkou,
                null);
        pop = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        // PopupWindow menuWindow = new
        // PopupWindow(layout,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        /** 设置点击其他地方消失 **/
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.update();
        pop.setBackgroundDrawable(new BitmapDrawable());

        // 让输入法框不遮挡弹出框
        pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // /**弹出效果**/
        // menuWindow.showAsDropDown(layout);
        // /** 设置在Activity中弹出位置**/
        // menuWindow.showAtLocation(menuWindow, Gravity.BOTTOM, 0, 0);

        ImageView xiepinglun_neikan_chuangkou_quxiao = (ImageView) layout
                .findViewById(R.id.xiepinglun_neikan_chuangkou_quxiao);
        /** X按钮 **/
        xiepinglun_neikan_chuangkou_quxiao
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        pop.dismiss();
                        // AbDialogUtil.removeDialog(layout);

                    }
                });

        TextView xiepinglun_neikan_chuangkou_fayan = (TextView) layout
                .findViewById(R.id.xiepinglun_neikan_chuangkou_fayan);
        xiepinglun_neikan_chuangkou_fayan
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        pop.dismiss();
                        // AbDialogUtil.removeDialog(layout);
                    }
                });

        ImageView xiepinglun_neikan_chuangkou_zhengque = (ImageView) layout
                .findViewById(R.id.xiepinglun_neikan_chuangkou_zhengque);
        final EditText xiepinglun_neikan_chuangkou_edittext = (EditText) layout
                .findViewById(R.id.xiepinglun_neikan_chuangkou_edittext);
        /** 正确按钮 **/
        xiepinglun_neikan_chuangkou_zhengque
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        String edit = xiepinglun_neikan_chuangkou_edittext
                                .getText().toString().trim();
                        if (!EmptyUtil.IsNotEmpty(edit)) {
                            Toast.makeText(neiKanInfoActivity.this, "评论内容不能为空",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            fbPinglun(edit);
                            pop.dismiss();
                            // AbDialogUtil.removeDialog(layout);
                        }
                    }
                });

        // AbDialogUtil.showDialog(layout, Gravity.BOTTOM);
        pop.showAtLocation(neiKanInfoActivity.this.findViewById(R.id.rl_1),
                Gravity.BOTTOM, 0, 0);

    }

    /**
     * 内刊添加评论接口
     *
     * @param content
     */
    private void fbPinglun(String content) {
//        JSONObject Obj = new JSONObject();
//        try {
//            Obj.put("content", content);
//            Obj.put("userid", CacheUtil.userid + "");
//            Obj.put("articleid", periodsrela.getArticleid());
//        } catch (JSONException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        byte[] bytes;
//        ByteArrayEntity entity = null;
//        try {
//            bytes = Obj.toString().getBytes("utf-8");
//            entity = new ByteArrayEntity(bytes);
//            YzyHttpClient.post(neiKanInfoActivity.this,
//                    HttpUrlConfig.addDiscuss, entity,
//                    new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            super.onSuccess(statusCode, content);
//                            if (statusCode == 499) {
//                                Toast.makeText(neiKanInfoActivity.this,
//                                        CacheUtil.logout, Toast.LENGTH_SHORT)
//                                        .show();
//                                Intent intent = new Intent(
//                                        neiKanInfoActivity.this,
//                                        LoginActivity.class);
//                                startActivity(intent);
//                                onFinish();
//                            } else if (statusCode == 200) {
//                                try {
//                                    JSONObject Obj = new JSONObject(content);
//                                    if ("add_success".equals(Obj
//                                            .getString("message"))) {
//                                        pinglunList();
//                                        Toast.makeText(neiKanInfoActivity.this,
//                                                "添加评论成功", Toast.LENGTH_SHORT)
//                                                .show();
//                                    } else {
//                                        handler.sendEmptyMessage(6);
//
//                                    }
//                                } catch (JSONException e) {
//                                    handler.sendEmptyMessage(6);
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                    });
//        } catch (UnsupportedEncodingException e) {
//            handler.sendEmptyMessage(0);
//            e.printStackTrace();
//        }

    }

    // @SuppressLint("JavascriptInterface")
    // private void setupWebView() {
    //
    // wb_neikaninfo_content.getSettings().setJavaScriptEnabled(true);
    //
    // wb_neikaninfo_content.setWebViewClient(new WebViewClient() {
    //
    // public void onPageFinished(WebView view, String url) {
    //
    // wb_neikaninfo_content.loadUrl("javascript:MyApp.resize(periodsrela.getContent().height)");
    //
    // super.onPageFinished(view, url);
    //
    // }
    //
    // });
    //
    // wb_neikaninfo_content.addJavascriptInterface(this, "MyApp");
    //
    // }
    //
    // private final class MyApp{
    // @JavascriptInterface
    // public void resize(final float height) {
    //
    // neiKanInfoActivity.this.runOnUiThread(new Runnable() {
    // @Override
    // public void run() {
    // wb_neikaninfo_content.setLayoutParams(new
    // LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels,
    // (int) (height * getResources().getDisplayMetrics().density)));
    //
    // }
    //
    // });
    //
    // }
    // }

}
