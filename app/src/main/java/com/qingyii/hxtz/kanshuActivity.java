package com.qingyii.hxtz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.wheel.AbNumericWheelAdapter;
import com.andbase.library.view.wheel.AbWheelView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxtz.adapter.ReaderPageListAdapter;
import com.qingyii.hxtz.adapter.muluAdapter;
import com.qingyii.hxtz.customview.ReadView;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.pojo.Book;
import com.qingyii.hxtz.pojo.Chapter;
import com.qingyii.hxtz.util.EmptyUtil;
import com.zhf.android_viewflow.ViewFlow;
import com.zhf.android_viewflow.ViewFlow.ViewSwitchListener;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//import com.ab.util.AbDialogUtil;
//import com.ab.view.wheel.AbNumericWheelAdapter;
//import com.ab.view.wheel.AbWheelView;

/**
 * 看书详情(暂时无用，由WebViewReader.java代替)
 *
 * @author Administrator
 */
public class kanshuActivity extends AbBaseActivity {
    //	private WebView wb_kanshu;
//	private WebSettings mWebSettings;
    private RelativeLayout rl_tab_top;
    private LinearLayout ll_tab_bottom, ll_kanshujindu;
    private ImageView back_btn;
    private TextView tv_kanshu_shuming;
    private ImageView iv_dengguang, iv_yueliang, kanshuziti, iv_kanshumulu, iv_kanshujindu,
            iv_kanshujinduliang, iv_kanshu_sousuo;
    private ListView mListView_mulu;
    private ReadView main_context;
    // private muluAdapter adapter;
    private ArrayList<Chapter> list = new ArrayList<Chapter>();
    private muluAdapter adapter;
    private Book bookinfo;
    private Handler handler;
    private TextView page_num;
    String contentStr = "此书暂无内容信息";
    /**
     * 每页字符数
     */
    private int pagesize = 0;
    /**
     * 当前页面
     */
    private int page = 1;
    /**
     * 总共多少页
     */
    private int pagenum = 0;
    /**
     * 分页内容数组
     */
    private ArrayList<SpannableString> contentList = new ArrayList<SpannableString>();
    /**
     * 弹出字号选择view
     */
    private View mDataView1 = null;
    /**
     * 字号大小:默认18sp
     */
    public String fontsize = "18";
    private ViewFlow main_list_viewflow;
    private ReaderPageListAdapter myAdapter;
    /**
     * 手机宽
     */
    float width;
    /**
     * 手机高
     */
    float height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanshu);
        handler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == 1) {
                    // adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        bookinfo = (Book) getIntent().getSerializableExtra("Book");

        //获取手机分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        //计算全屏一屏显示的字个数:(手机宽度/18)*(手机高度/18)
//        pagesize=(DisplayUtil.px2sp(width, dm.density)/Integer.parseInt(fontsize))*(DisplayUtil.px2sp(height, dm.density)/Integer.parseInt(fontsize));

        initUI();
        initData();
    }

    private void initData() {
        getmuluList();
    }

    /**
     * 字号选择UI
     *
     * @param mDataView1
     */
    public void initWheelData1(View mDataView1) {
        final AbWheelView mWheelView1 = (AbWheelView) mDataView1.findViewById(R.id.wheelView1);
        mWheelView1.setAdapter(new AbNumericWheelAdapter(10, 30));
        // 可循环滚动
        mWheelView1.setCyclic(true);
        // 添加文字
        mWheelView1.setLabel("号");
        // 初始化时显示的数据
        mWheelView1.setCurrentItem(Integer.parseInt(fontsize) - 10);
        mWheelView1.setValueTextSize(35);
        mWheelView1.setLabelTextSize(35);
        mWheelView1.setLabelTextColor(0x80000000);
        mWheelView1.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

        Button okBtn = (Button) mDataView1.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) mDataView1.findViewById(R.id.cancelBtn);
        okBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(v.getContext());
                int index = mWheelView1.getCurrentItem();
                String val = mWheelView1.getAdapter().getItem(index);
//				mDataTextView1.setText(val);
                //选择字号赋值
                fontsize = val;
                doContent();
            }

        });

        cancelBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(v.getContext());
            }

        });
    }

    private void initUI() {
        iv_kanshu_sousuo = (ImageView) findViewById(R.id.iv_kanshu_sousuo);
        iv_kanshujinduliang = (ImageView) findViewById(R.id.iv_kanshujinduliang);
        ll_kanshujindu = (LinearLayout) findViewById(R.id.ll_kanshujindu);
        iv_kanshujindu = (ImageView) findViewById(R.id.iv_kanshujindu);
        main_list_viewflow = (ViewFlow) findViewById(R.id.main_list_viewflow);
        myAdapter = new ReaderPageListAdapter(this, contentList);
        main_list_viewflow.setAdapter(myAdapter, 0);
        main_list_viewflow.setOnViewSwitchListener(new ViewSwitchListener() {

            @Override
            public void onSwitched(View view, int position) {
                // TODO Auto-generated method stub
                if (position > 0) {
                    page = position + 1;
                    page_num.setText("当前第" + page + "页 ，总共" + pagenum + "页");
                }/*else if(position==0){
                    main_context.setVisibility(NotifyListView.VISIBLE);
					main_list_viewflow.setVisibility(NotifyListView.GONE);
				}*/
            }
        });
        page_num = (TextView) findViewById(R.id.page_num);
        main_context = (ReadView) findViewById(R.id.main_context);
        main_context.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        main_context.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (contentList.size() > 0 && page <= pagenum) {
                    main_context.setText(contentList.get(page - 1));
                    page_num.setText("当前第" + page + "页 ，总共" + pagenum + "页");
                    page++;
                } else {
                    Toast.makeText(kanshuActivity.this, "已是最后一页",
                            Toast.LENGTH_SHORT).show();
                }
//				main_context.setVisibility(NotifyListView.GONE);
//				main_list_viewflow.setVisibility(NotifyListView.VISIBLE);
            }
        });
//		contentStr = "太阳初升，万物初始，生之气最盛，虽不能如传说中那般餐霞食气，但这样迎霞锻体自也有莫大好处，可充盈人体生机。一天之计在于晨，每日早起多用功，强筋壮骨，活血炼筋，将来才能在这苍莽山脉中有活命的本钱。”站在前方、指点一群孩子的中年男子一脸严肃，认真告诫，而后又喝道：“你们明白吗？”太阳初升，万物初始，生之气最盛，虽不能如传说中那般餐霞食气，但这样迎霞锻体自也有莫大好处，可充盈人体生机。一天之计在于晨，每日早起多用功，强筋壮骨，活血炼筋，将来才能在这苍莽山脉中有活命的本钱。”站在前方、指点一群孩子的中年男子一脸严肃，认真告诫，而后又喝道：“你们明白吗？”太阳初升，万物初始，生之气最盛，虽不能如传说中那般餐霞食气，但这样迎霞锻体自也有莫大好处，可充盈人体生机。一天之计在于晨，每日早起多用功，强筋壮骨，活血炼筋，将来才能在这苍莽山脉中有活命的本钱。”站在前方、指点一群孩子的中年男子一脸严肃，认真告诫，而后又喝道：“你们明白吗？”太阳初升，万物初始，生之气最盛，虽不能如传说中那般餐霞食气，但这样迎霞锻体自也有莫大好处，可充盈人体生机。一天之计在于晨，每日早起多用功，强筋壮骨，活血炼筋，将来才能在这苍莽山脉中有活命的本钱。”站在前方、指点一群孩子的中年男子一脸严肃，认真告诫，而后又喝道：“你们明白吗？”";
        main_context.setText(contentStr);
        tv_kanshu_shuming = (TextView) findViewById(R.id.tv_kanshu_shuming);
        mListView_mulu = (ListView) findViewById(R.id.mListView_mulu);
        iv_kanshumulu = (ImageView) findViewById(R.id.iv_kanshumulu);
        kanshuziti = (ImageView) findViewById(R.id.kanshuziti);
        iv_yueliang = (ImageView) findViewById(R.id.iv_yueliang);
        iv_dengguang = (ImageView) findViewById(R.id.iv_dengguang);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        ll_tab_bottom = (LinearLayout) findViewById(R.id.ll_tab_bottom);
        rl_tab_top = (RelativeLayout) findViewById(R.id.rl_tab_top);
//		wb_kanshu = (WebView) findViewById(R.id.wb_kanshu);
        tv_kanshu_shuming.setText(bookinfo.getBookname());

        iv_kanshumulu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (list.size() > 0) {
                    showmulupopWindow(getApplication(), v);
//                    showmulupopWindow(abApplication, v);
                } else {
                    Toast.makeText(kanshuActivity.this, "此书暂无目录",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        kanshuziti.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // showPopWindow(abApplication, v);
                mDataView1 = View.inflate(kanshuActivity.this, R.layout.choose_one, null);
                initWheelData1(mDataView1);
                AbDialogUtil.showDialog(mDataView1, Gravity.BOTTOM);
            }
        });
        iv_kanshu_sousuo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(kanshuActivity.this, kanshusousuoActivity.class);
                startActivity(it);

            }
        });
        iv_dengguang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (iv_dengguang.getVisibility() == View.VISIBLE) {
                    iv_dengguang.setVisibility(View.GONE);
                    iv_yueliang.setVisibility(View.VISIBLE);
                    main_context.setBackgroundColor(getResources().getColor(
                            R.color.black));
                    main_context.setTextColor(getResources().getColor(
                            R.color.white));
                } else if (iv_dengguang.getVisibility() == View.GONE) {
                    iv_dengguang.setVisibility(View.VISIBLE);
                    iv_yueliang.setVisibility(View.GONE);
                    main_context.setBackgroundColor(getResources().getColor(
                            R.color.white));
                    main_context.setTextColor(getResources().getColor(
                            R.color.black));
                }

            }
        });
        iv_kanshujindu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ll_kanshujindu.setVisibility(View.VISIBLE);
                iv_kanshujindu.setVisibility(View.GONE);
                iv_kanshujinduliang.setVisibility(View.VISIBLE);
            }
        });
        iv_kanshujinduliang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ll_kanshujindu.setVisibility(View.GONE);
                iv_kanshujindu.setVisibility(View.VISIBLE);
                iv_kanshujinduliang.setVisibility(View.GONE);

            }
        });
        iv_yueliang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (iv_dengguang.getVisibility() == View.VISIBLE) {
                    iv_dengguang.setVisibility(View.GONE);
                    iv_yueliang.setVisibility(View.VISIBLE);
                    main_context.setBackgroundColor(getResources().getColor(
                            R.color.black));
                    main_context.setTextColor(getResources().getColor(
                            R.color.white));
                } else if (iv_dengguang.getVisibility() == View.GONE) {
                    iv_dengguang.setVisibility(View.VISIBLE);
                    iv_yueliang.setVisibility(View.GONE);
                    main_context.setBackgroundColor(getResources().getColor(
                            R.color.white));
                    main_context.setTextColor(getResources().getColor(
                            R.color.black));
                }

            }
        });

        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

//		mWebSettings = wb_kanshu.getSettings();
//		mWebSettings.setJavaScriptEnabled(true);
//		mWebSettings.setBuiltInZoomControls(true);
//		mWebSettings.setLightTouchEnabled(true);
//		mWebSettings.setSupportZoom(true);
//		// mWebSettings.setAllowContentAccess(true);
//
//		wb_kanshu.setHapticFeedbackEnabled(false);
//		// wb_kanshu.loadUrl("file:///android_asset/kanshu.html");
//		wb_kanshu
//				.loadData(
//						"飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟飞啊飞违法违规乌龟乌龟",
//						"text/html; charset=UTF-8", null);

    }

//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		// TODO Auto-generated method stub
//		super.onWindowFocusChanged(hasFocus);
//		if (hasFocus) {
//			doContent();
//		}
//	}

    /**
     * 处理内容分页数据
     */
    public void doContent() {
        //清空内容分页数组
        contentList.clear();
        pagesize = main_context.getCharNum();
        //设置textview字号大小
        main_context.setTextSize(TypedValue.COMPLEX_UNIT_SP, Integer.parseInt(fontsize));
        if (EmptyUtil.IsNotEmpty(contentStr)) {
            if (contentStr.length() > pagesize) {
                if (pagesize != 0) {
                    pagenum = contentStr.length() / pagesize;
                }
            } else {
                pagenum = 1;
            }
        } else {
            pagenum = 1;
        }
        if (page > pagenum) {
            page = pagenum;
        }
        page_num.setText("当前第" + page + "页 ，总共" + pagenum + "页");
        for (int i = 0; i < pagenum; i++) {
            CharSequence ssStr = "";
            if (i < pagenum - 1) {
                ssStr = contentStr
                        .subSequence(i * pagesize, (i + 1) * pagesize);
            } else {
                if (EmptyUtil.IsNotEmpty(contentStr)) {
                    if (contentStr.length() > pagesize) {
                        ssStr = contentStr.subSequence(i * pagesize,
                                contentStr.length());
                    } else {
                        ssStr = contentStr;
                    }
                }
            }
            SpannableString ss = new SpannableString(ssStr);
            contentList.add(ss);
        }
        if (contentList.size() > 0) {
            main_context.setText(contentList.get(0));
        }
        //刷新数据
        myAdapter.notifyDataSetChanged();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            super.openOptionsMenu(); // 调用这个，就可以弹出菜单
            if (ll_tab_bottom.getVisibility() == View.VISIBLE) {
                ll_tab_bottom.setVisibility(View.GONE);
                rl_tab_top.setVisibility(View.GONE);
            } else {
                ll_tab_bottom.setVisibility(View.VISIBLE);
                rl_tab_top.setVisibility(View.VISIBLE);
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return true; // 最后，一定要做完以后返回 true，或者在弹出菜单后返回true，其他键返回super，让其他键默认

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        // 关闭Menu菜单时你想做的事情
        rl_tab_top.setVisibility(View.VISIBLE);
        ll_tab_bottom.setVisibility(View.VISIBLE);
        super.onOptionsMenuClosed(menu);
    }

    // 显示目录列表框
    private void showmulupopWindow(Context context, View parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View muluPopWindow = inflater.inflate(R.layout.popwindow_mulu,
                null, false);
        // 宽300 高300
        final PopupWindow popWindow = new PopupWindow(muluPopWindow, 600, 900,
                true);
        ListView mListView_mulu = (ListView) muluPopWindow.findViewById(R.id.mListView_mulu);
//        adapter = new muluAdapter(this, list);
        mListView_mulu.setAdapter(adapter);
        final TextView tv_popwindow_mulu = (TextView) muluPopWindow.findViewById(R.id.tv_popwindow_mulu);
        final TextView tv_popwindow_pizhu = (TextView) muluPopWindow.findViewById(R.id.tv_popwindow_pizhu);
        final TextView tv_popwindow_shuqian = (TextView) muluPopWindow.findViewById(R.id.tv_popwindow_shuqian);
        tv_popwindow_mulu.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                tv_popwindow_mulu.setBackground(getResources().getDrawable(R.drawable.shape_all_corners));
                tv_popwindow_mulu.setTextColor(getResources().getColor(R.color.white));
                tv_popwindow_pizhu.setBackground(null);
                tv_popwindow_pizhu.setTextColor(getResources().getColor(R.color.black));
                tv_popwindow_shuqian.setBackground(null);
                tv_popwindow_shuqian.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tv_popwindow_pizhu.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                tv_popwindow_pizhu.setBackground(getResources().getDrawable(R.drawable.shape_all_corners));
                tv_popwindow_pizhu.setTextColor(getResources().getColor(R.color.white));
                tv_popwindow_mulu.setBackground(null);
                tv_popwindow_mulu.setTextColor(getResources().getColor(R.color.black));
                tv_popwindow_shuqian.setBackground(null);
                tv_popwindow_shuqian.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tv_popwindow_shuqian.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                tv_popwindow_shuqian.setBackground(getResources().getDrawable(R.drawable.shape_all_corners));
                tv_popwindow_shuqian.setTextColor(getResources().getColor(R.color.white));
                tv_popwindow_pizhu.setBackground(null);
                tv_popwindow_pizhu.setTextColor(getResources().getColor(R.color.black));
                tv_popwindow_mulu.setBackground(null);
                tv_popwindow_mulu.setTextColor(getResources().getColor(R.color.black));
            }
        });
        //设置popwindow区域外点击隐藏popwindow
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);

        popWindow.showAtLocation(parent, Gravity.TOP, 0, 200);
        mListView_mulu.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // popWindow.dismiss();
                queryChapterList(list.get(position).getChapterid());
            }


            /**
             * 根据章节ID查询内容
             *
             * @param chapterid
             */
            private void queryChapterList(String chapterid) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("chapterid", chapterid);
                    byte[] bytes;
                    ByteArrayEntity entity = null;
                    try {
                        bytes = obj.toString().getBytes("utf-8");
                        entity = new ByteArrayEntity(bytes);
                        YzyHttpClient.post(kanshuActivity.this,
                                HttpUrlConfig.queryChapterList, entity,
                                new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode,
                                                          String content) {
                                        super.onSuccess(statusCode, content);
                                        if (statusCode == 499) {
                                            Toast.makeText(kanshuActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(kanshuActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            onFinish();
                                        } else if (statusCode == 200) {
                                            Gson gson = new Gson();
                                            try {
                                                JSONObject Obj = new JSONObject(
                                                        content);
                                                JSONArray mlist = Obj
                                                        .getJSONArray("rows");
                                                if (mlist.length() > 0) {
                                                    Chapter cinfo = gson.fromJson(
                                                            mlist.getString(0),
                                                            Chapter.class);
                                                    if (EmptyUtil.IsNotEmpty(cinfo.getContent())) {
                                                        contentStr = cinfo.getContent();
                                                        doContent();
                                                    } else {
                                                        Toast.makeText(kanshuActivity.this, "此章节无内容信息", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                                popWindow.dismiss();
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch
                                                // block
                                                e.printStackTrace();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(int statusCode,
                                                          Throwable error, String content) {
                                        // TODO Auto-generated method stub
                                        super.onFailure(statusCode, error,
                                                content);

                                        System.out.print("f");
                                    }

                                    @Override
                                    public void onFinish() {
                                        System.out.print("o");
                                    }

                                    ;
                                });
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }

    // 获取目录列表
    private void getmuluList() {

        JSONObject obj = new JSONObject();
        try {
            obj.put("bookid", bookinfo.getBookid() + "");
            byte[] bytes;
            ByteArrayEntity entity = null;
            try {
                bytes = obj.toString().getBytes("utf-8");
                entity = new ByteArrayEntity(bytes);
                YzyHttpClient.post(kanshuActivity.this,
                        HttpUrlConfig.shujiMulu, entity,
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, String content) {
                                super.onSuccess(statusCode, content);
                                if (statusCode == 499) {
                                    Toast.makeText(kanshuActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(kanshuActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    onFinish();
                                } else if (statusCode == 200) {
                                    Gson gson = new Gson();
                                    try {
                                        JSONObject Obj = new JSONObject(content);
                                        JSONArray mlist = Obj
                                                .getJSONArray("rows");
                                        for (int i = 0; i < mlist.length(); i++) {
                                            Chapter cinfo = gson.fromJson(
                                                    mlist.getString(i),
                                                    Chapter.class);
                                            //进入时：默认加载第一章节内容
                                            if (i == 0) {
                                                contentStr = cinfo.getContent();
//												contentStr = "太阳初升，万物初始，生之气最盛，虽不能如传说中那般餐霞食气，但这样迎霞锻体自也有莫大好处，可充盈人体生机。一天之计在于晨，每日早起多用功，强筋壮骨，活血炼筋，将来才能在这苍莽山脉中有活命的本钱。”站在前方、指点一群孩子的中年男子一脸严肃，认真告诫，而后又喝道：“你们明白吗？”太阳初升，万物初始，生之气最盛，虽不能如传说中那般餐霞食气，但这样迎霞锻体自也有莫大好处，可充盈人体生机。一天之计在于晨，每日早起多用功，强筋壮骨，活血炼筋，将来才能在这苍莽山脉中有活命的本钱。”站在前方、指点一群孩子的中年男子一脸严肃，认真告诫，而后又喝道：“你们明白吗？”太阳初升，万物初始，生之气最盛，虽不能如传说中那般餐霞食气，但这样迎霞锻体自也有莫大好处，可充盈人体生机。一天之计在于晨，每日早起多用功，强筋壮骨，活血炼筋，将来才能在这苍莽山脉中有活命的本钱。”站在前方、指点一群孩子的中年男子一脸严肃，认真告诫，而后又喝道：“你们明白吗？”太阳初升，万物初始，生之气最盛，虽不能如传说中那般餐霞食气，但这样迎霞锻体自也有莫大好处，可充盈人体生机。一天之计在于晨，每日早起多用功，强筋壮骨，活血炼筋，将来才能在这苍莽山脉中有活命的本钱。”站在前方、指点一群孩子的中年男子一脸严肃，认真告诫，而后又喝道：“你们明白吗？”";
                                                doContent();
                                            }
                                            list.add(cinfo);
                                        }
                                        handler.sendEmptyMessage(1);
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(int statusCode,
                                                  Throwable error, String content) {
                                // TODO Auto-generated method stub
                                super.onFailure(statusCode, error, content);
                            }
                        });
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
