package com.qingyii.hxtz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxtz.adapter.shujiaAdapter;
import com.qingyii.hxtz.bean.shujiaInfo;
import com.qingyii.hxtz.database.BookDB;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.pojo.BooksParameter;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * 书架界面
 *
 * @author Administrator
 */
public class shuJiaActivity extends BaseActivity {
    private AutoLinearLayout returns_arrow;
    private AutoRelativeLayout shujia_add;
    private AbPullToRefreshView mAbPullToRefreshView = null;
    private ArrayList<shujiaInfo> list = new ArrayList<shujiaInfo>();
    private shujiaAdapter adapter;
    private ListView mListView = null;
    private int page = 1;
    private int pagesize = 10;
    // 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    int type = 1;
    private Handler handler = new Handler(new Callback() {

        @SuppressLint("NewApi")
        @Override
        public boolean handleMessage(Message arg0) {

            switch (arg0.what) {
                case 0:
                    Toast.makeText(shuJiaActivity.this, "数据加载异常！",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    shapeLoadingDialog.dismiss();
                    adapter.notifyDataSetChanged();
                    mListView.deferNotifyDataSetChanged();
                    break;
                case 5:
                    Toast.makeText(shuJiaActivity.this, "已是最新数据！",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            if (mAbPullToRefreshView != null) {
                // 刷新，加载更新提示隐藏
                mAbPullToRefreshView.onHeaderRefreshFinish();
                mAbPullToRefreshView.onFooterLoadFinish();
            }
            if (shapeLoadingDialog != null)
                shapeLoadingDialog.dismiss();
            return true;
        }
    });
    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
//    private AbProgressDialogFragment mDialogFragment = null;
    private ShapeLoadingDialog shapeLoadingDialog = null;
    private ArrayList<BooksParameter.DataBean> myList = new ArrayList<BooksParameter.DataBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // 注册刷新数据广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.shujiDownload");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        setContentView(R.layout.activity_shujia);

        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("书架");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initData();
        initUI();
    }

    // 广播 在那个类接收广播就在哪个类注册
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.shujiDownload")) {
                myList.clear();
                myList.addAll(QueryshujiaList(0, pagesize));
                handler.sendEmptyMessage(1);

            }
        }
    };

    private void initData() {
        // 显示进度框
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
        refreshTask();
//            }
//
//        });
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");
        shapeLoadingDialog.show();
//        mDialogFragment = AbDialogUtil.showProgressDialog(this, R.mipmap.ic_load,
//                "查询中,请等一小会");
        // getshujiaList(page,pagesize);
    }

    private void initUI() {
        mListView = (ListView) this.findViewById(R.id.mListView);
        mAbPullToRefreshView = (AbPullToRefreshView) this
                .findViewById(R.id.mPullRefreshView);
        adapter = new shujiaAdapter(myList, this);
        mListView.setAdapter(adapter);
        if (myList.size() <= 0) {
            LinearLayout emptyView = (LinearLayout) findViewById(R.id.empty);
            mListView.setEmptyView(emptyView);
        }

        // 设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                refreshTask();
            }
        });
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                loadMoreTask();
            }
        });

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));

        shujia_add = (AutoRelativeLayout) findViewById(R.id.shujia_add);
        shujia_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(shuJiaActivity.this, shuWuActivity.class);
                startActivity(intent);
            }
        });
        returns_arrow = (AutoLinearLayout) findViewById(R.id.returns_arrow);
        //短按进入阅读
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Intent it=new
                // Intent(shuJiaActivity.this,kanshuActivity.class);
                BooksParameter.DataBean book = myList.get(position);
                if (book != null) {
                    Intent it = new Intent(shuJiaActivity.this, shujixiangqingActivity.class);

                    it.putExtra("Book", book);
                    startActivity(it);
                }
            }
        });

        //长按删除
        mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub

                final Dialog builder = new Dialog(shuJiaActivity.this);
                final View v = View.inflate(shuJiaActivity.this, R.layout.shujia_dialog_layout, null);
                final View tltle = View.inflate(shuJiaActivity.this, R.layout.null_tltle, null);
                builder.setContentView(v);
                Button quxiao = (Button) v.findViewById(R.id.shujia_shanchuquxiao);
                Button shanchu = (Button) v.findViewById(R.id.shujia_shanchu);
                quxiao.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                shanchu.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BookDB.deleteBookRead(myList.get(position).getId() + "");
                        refreshTask();
                        builder.dismiss();
                    }
                });
                builder.show();

                return true;
            }
        });

        returns_arrow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

    }

    protected void refreshTask() {
        type = 1;
        myList.clear();
        myList.addAll(QueryshujiaList(0, pagesize));
        // QueryshujiaList(1,pagesize);
        page = 2;
        handler.sendEmptyMessage(1);
    }

    protected void loadMoreTask() {
        type = 2;
        myList.addAll(QueryshujiaList((page - 1) * pagesize, pagesize));
        page += 1;
        handler.sendEmptyMessage(1);
        // QueryshujiaList((page-1)*pagesize,pagesize);
    }

    /*
     * 从数据库获取列表数据
     */
    public ArrayList<BooksParameter.DataBean> QueryshujiaList(int startNum, int pagesize) {
        ArrayList<BooksParameter.DataBean> lists = new ArrayList<>();
        String sql = "select * from shuji_info order by id desc Limit "
                + pagesize + " Offset " + startNum;
        SQLiteDatabase database = MyApplication.helper.getWritableDatabase();

        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                BooksParameter.DataBean book = new BooksParameter.DataBean();
                book.setBookname(cursor.getString(cursor.getColumnIndex("shuji_title")));
//                book.setAuthor(cursor.getString(cursor.getColumnIndex("shuji_name")));
                book.setDescription(cursor.getString(cursor.getColumnIndex("shuji_content")));
                book.setBookcover(cursor.getString(cursor.getColumnIndex("shuji_photo")));
                book.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("shuji_id"))));
                book.setBookurl(cursor.getString(cursor.getColumnIndex("downLoadurl")));
                book.setAuthor(cursor.getString(cursor.getColumnIndex("shuji_author")));
                book.setReadnums((cursor.getInt(cursor.getColumnIndex("read_nums"))));
                book.setPress((cursor.getString(cursor.getColumnIndex("press"))));
                book.setFiletype(cursor.getInt(cursor.getColumnIndex("file_type")));
                book.setPublishstatus((cursor.getString(cursor.getColumnIndex("publishstatus"))));
                book.setStars((cursor.getInt(cursor.getColumnIndex("stars"))));
                book.setSize((cursor.getInt(cursor.getColumnIndex("size"))));
                //book.setFilename(cursor.getString(cursor.getColumnIndex("file_name")));

                book.setSdCardUrl(cursor.getString(cursor.getColumnIndex("shuji_sdcard_url")));

                //计算type

//                Log.e("ManageList_onError", "Bookname:" + book.getBookname() + "  Description:" + book.getDescription()
//                        + "  Bookcover:" + book.getBookcover() + "  Id:" + book.getId() + "  Bookurl:" + book.getBookurl()
//                        + "  Author:" + book.getAuthor() + "  Readnums:" + book.getReadnums() + "  Filetype:" + book.getFiletype()
//                        + "  SdCardUrl:" + book.getSdCardUrl());
                int fileType = Integer.parseInt(cursor.getString(cursor.getColumnIndex("file_type")));
                System.out.println("filetype -----------" + fileType);
                book.setFiletype(fileType);

                //计算type
                lists.add(book);
                cursor.moveToNext();
            }
        }
        return lists;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        refreshTask();
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //注销广播
        unregisterReceiver(mRefreshBroadcastReceiver);
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
}
