package com.qingyii.hxt;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.adapter.TabLayoutPagerAdapter;
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.base.app.GlobalConsts;
import com.qingyii.hxt.database.BookDB;
import com.qingyii.hxt.download.DownLoadHelper;
import com.qingyii.hxt.download.DownloadUtil;
import com.qingyii.hxt.download.DownloadUtil.OnDownloadListener;
import com.qingyii.hxt.fragment.PingLunFragment;
import com.qingyii.hxt.fragment.shujiInfoFragment;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.httpway.HandleParameterCallback;
import com.qingyii.hxt.pojo.BookDetails;
import com.qingyii.hxt.pojo.BooksParameter;
import com.qingyii.hxt.pojo.Discuss;
import com.qingyii.hxt.pojo.HandleParameter;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.util.ZipUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.Book;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.qingyii.hxt.pojo.Book.type_EPUB;
import static com.qingyii.hxt.pojo.Book.type_PDF;
import static com.qingyii.hxt.pojo.Book.type_TXT;

//import it.angrydroids.epub3reader.EpubReaderActivity;

public class shujixiangqingActivity extends BaseActivity {
    /**
     * 书籍详情界面
     */
    private Intent intent;
    private int max;
    DisplayImageOptions options; // 配置图片加载及显示选项
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DownloadUtil mDownloadUtil;
    private LinearLayout ll_layout_nrjj, ll_layout_pl;
    private RadioButton xiangqing_btn, pinglun_btn;
    private RadioGroup shujixiangqing_rgroup;
    private LinearLayout back_btn;
    private Button bt_addshujia, bt_read, bt_shoucan, bt_yishoucang;
    private RatingBar foodRatingBar;
    private TextView tv_sjxq_xiangqing, tv_sjxq_pinglun,
            tv_shujixiangqing_shuname, tv_zuozhe, tv_ppp,
            tv_shujixiangqing_shangchuan, shujixiangqing_readcishu,
            tv_gongkai, tv_numble, tv_filetype, tv_progress;
    private ImageView book_level_star1[] = new ImageView[5];

    // 后台返回BOOK对象
//    private Book bookInfo;
    private BooksParameter.DataBean bookInfo;
    private ImageView iv_shujixiangqing_Image;
    private ImageView tv_shujixiangqing_jurisdic;
    /**
     * AbSlidingTabView 失效，注释相关
     */
//  private AbSlidingTabView mAbSlidingTabView;
    public TabLayout tabLayout;
    public ViewPager viewPager;
    private TabLayoutPagerAdapter tabLayoutPagerAdapter;

    private ProgressBar pb_shujixiangqing;
    private DownLoadHelper dbHelper;
    // 数据库返回BOOK对象
    private boolean is_addShujia = false;
    private boolean is_downloaded = false;

    private String sdcard_url = null;

    //private Book bookSqlInfo = null;
    //private BooksParameter.DataBean bookSqlInfo = null;
    private Discuss discuss = new Discuss();
    private ScrollView scrollview;
    private int num;

    private BookCollectionShadow bs;
    private String progress = "暂未阅读";
    private int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shujixiangqing);
        bookInfo = getIntent().getParcelableExtra("Book"); //(BooksParameter.DataBean)
        if (MyApplication.hxt_setting_config.contains(GlobalConsts.BOOK_PROGRESS+bookInfo.getId()))
            progress = MyApplication.hxt_setting_config.getString(GlobalConsts.BOOK_PROGRESS+bookInfo.getId(), "暂未阅读");
        if (MyApplication.hxt_setting_config.contains(GlobalConsts.BOOK_CURRENT+bookInfo.getId()))
            current = MyApplication.hxt_setting_config.getInt(GlobalConsts.BOOK_CURRENT+bookInfo.getId(), 0);


        bs = new BookCollectionShadow();
        bs.bindToService(shujixiangqingActivity.this, null);

        if (bookInfo != null) {
            //判断书籍是否下载
            isDownLoadBook(bookInfo.getId() + "");
            //tltle设置
            TextView activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
            activity_tltle_name.setText(bookInfo.getBookname() + "");
            LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
            returns_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else
            System.out.println("book is null ---------------");
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bookInfo != null)
            bookDetails();
    }

    @SuppressLint("NewApi")
    private void initUI() {
        num = (int) (1 + Math.random() * 5); //1到5的随机数
//        foodRatingBar = (RatingBar) findViewById(R.id.foodRatingBar);
//        //禁止ratingBar改变
//        foodRatingBar.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(NotifyListView arg0, MotionEvent arg1) {
//                return true;
//            }
//        });
        /**
         * 书籍星级（待修改）
         */
//        if (EmptyUtil.IsNotEmpty(bookInfo.getXingji())) {
//            foodRatingBar.setRating(Float.parseFloat(bookInfo.getXingji()));
//        } else {
//            foodRatingBar.setRating(0);
//        }
        tv_ppp = (TextView) findViewById(R.id.tv_ppp);
        pb_shujixiangqing = (ProgressBar) findViewById(R.id.pb_shujixiangqing);
//      mAbSlidingTabView = (AbSlidingTabView) findViewById(mAbSlidingTabView);
//      mAbSlidingTabView.getViewPager().setOffscreenPageLimit(2);
        shujiInfoFragment page1 = new shujiInfoFragment();
        PingLunFragment page2 = new PingLunFragment();
        Bundle b2 = new Bundle();
        b2.putParcelable("bookInfo", bookInfo);
        page2.setBundle(b2);
        page1.setBundle(b2);
        List<Fragment> mFragments = new ArrayList<Fragment>();
        mFragments.add(page1);
        mFragments.add(page2);
        List<String> tabTexts = new ArrayList<String>();
        tabTexts.add("详情");
        tabTexts.add("评论");

        tabLayoutPagerAdapter = new TabLayoutPagerAdapter(getSupportFragmentManager(), mFragments, tabTexts);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_menu);
        viewPager = (ViewPager) findViewById(R.id.tablayout_viewpagaer);
        viewPager.setAdapter(tabLayoutPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        // 设置样式
//      mAbSlidingTabView.setTabTextColor(Color.BLACK);
//      mAbSlidingTabView.setTabSelectColor(Color.rgb(30, 168, 131));
//      mAbSlidingTabView.setTabBackgroundResource(R.drawable.tab_bg);
//      mAbSlidingTabView.setTabLayoutBackgroundResource(R.drawable.slide_top);
//      // 演示增加一组
//      mAbSlidingTabView.addItemViews(tabTexts, mFragments);
//      mAbSlidingTabView.setTabPadding(20, 8, 20, 8);
//      mAbSlidingTabView.setBackgroundColor(getResources().getColor(
//              R.color.white));

        bt_yishoucang = (Button) findViewById(R.id.bt_yishoucang);
        bt_shoucan = (Button) findViewById(R.id.bt_shoucan);
        /**
         * 是否公开控件
         */
//        tv_gongkai = (TextView) findViewById(R.id.tv_gongkai);
        shujixiangqing_readcishu = (TextView) findViewById(R.id.shujixiangqing_readcishu);
        iv_shujixiangqing_Image = (ImageView) findViewById(R.id.iv_shujixiangqing_Image);
        tv_shujixiangqing_jurisdic = (ImageView) findViewById(R.id.tv_shujixiangqing_jurisdic);
        tv_shujixiangqing_shuname = (TextView) findViewById(R.id.tv_shujixiangqing_shuname);
        book_level_star1[0] = (ImageView) findViewById(R.id.book_level_star5);
        book_level_star1[1] = (ImageView) findViewById(R.id.book_level_star4);
        book_level_star1[2] = (ImageView) findViewById(R.id.book_level_star3);
        book_level_star1[3] = (ImageView) findViewById(R.id.book_level_star2);
        book_level_star1[4] = (ImageView) findViewById(R.id.book_level_star1);
        tv_shujixiangqing_shangchuan = (TextView) findViewById(R.id.tv_shujixiangqing_shangchuan);
        tv_zuozhe = (TextView) findViewById(R.id.tv_zuozhe);
        tv_numble = (TextView) findViewById(R.id.tv_numble);
        tv_filetype = (TextView) findViewById(R.id.tv_filetype);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_progress.setText(progress);
        //tv_numble.setText(num + "M");

        /**
         * 书籍权限
         */
        if (EmptyUtil.IsNotEmpty(bookInfo.getPublishstatus())) {
            if ("public".equals(bookInfo.getPublishstatus())) {
                tv_shujixiangqing_jurisdic.setBackgroundResource(R.mipmap.shuwu_gongkai);
            } else if ("private".equals(bookInfo.getPublishstatus())) {
                tv_shujixiangqing_jurisdic.setBackgroundResource(R.mipmap.shuwu_zhuanyou);
            } else if ("share".equals(bookInfo.getPublishstatus())) {
                tv_shujixiangqing_jurisdic.setBackgroundResource(R.mipmap.shuwu_gongxiang);
            }
        }

        /**
         * 上传人（出版社）
         */
        if (EmptyUtil.IsNotEmpty(bookInfo.getPress())) {
            tv_shujixiangqing_shangchuan.setText(bookInfo.getPress());
        }
        /**
         * 作者
         */
        if (EmptyUtil.IsNotEmpty(bookInfo.getAuthor())) {
            tv_zuozhe.setText("作者：" + bookInfo.getAuthor());
        }
        /**
         * 书籍类型
         */
        if (EmptyUtil.IsNotEmpty(bookInfo.getFiletype() + "")) {
            if (bookInfo.getFiletype() == 1)
                tv_filetype.setText("epub");
            if (bookInfo.getFiletype() == 2)
                tv_filetype.setText("pdf");
        }
        /**
         * 书籍名字
         */
        if (EmptyUtil.IsNotEmpty(bookInfo.getBookname() + ""))
            tv_shujixiangqing_shuname.setText(bookInfo.getBookname());
        /**
         * 书籍大小
         */
        if (EmptyUtil.IsNotEmpty(bookInfo.getSize() + ""))
            if (bookInfo.getSize() > 1000000)
                tv_numble.setText("" + bookInfo.getSize() / 1000000 + "M");
            else
                tv_numble.setText("" + bookInfo.getSize() / 1000 + "K");

        /**
         * 书籍星级
         */
        int i = 0;
//        Log.e("列表星级：", rDataBean.getStars() + "");
        for (; i < bookInfo.getStars(); i++)
            book_level_star1[i].setVisibility(View.VISIBLE);

        for (; i < book_level_star1.length; i++)
            book_level_star1[i].setVisibility(View.GONE);

        /**
         * 书籍图片加载
         */
        ImageLoader.getInstance().displayImage(bookInfo.getBookcover(),
                iv_shujixiangqing_Image, MyApplication.options,
                animateFirstListener);
        bt_read = (Button) findViewById(R.id.bt_read);

        // 根据sqlite库是否保存，保存了则加入书架，反则没有
        System.out.println(is_addShujia + " -----isaddshujia-----------");

        if (!is_addShujia) {
            bt_shoucan.setVisibility(View.VISIBLE);
            bt_yishoucang.setVisibility(View.GONE);
        } else {
            bt_shoucan.setVisibility(View.GONE);
            bt_yishoucang.setVisibility(View.VISIBLE);
        }

        // 根据sqlite库是否保存，保存了直接阅读，反则下载
        if (is_downloaded) {
//                bt_shoucan.setVisibility(NotifyListView.GONE);
//                bt_yishoucang.setVisibility(NotifyListView.VISIBLE);
            bt_read.setText("立即阅读(已下载)");
        } else {
            bt_read.setText("立即阅读(未下载)");
        }

        /**
         * 阅读标志（无参数）
         */
//        if (EmptyUtil.IsNotEmpty(bookInfo.getReadflag())) {
//            if ("1".equals(bookInfo.getReadflag())) {
//                tv_gongkai.setText("公开");
//            } else if ("2".equals(bookInfo.getReadflag())) {
//                tv_gongkai.setText("专有");
//            }
//        }

//      scrollview = (ScrollView) findViewById(R.id.scrollview);

        back_btn = (LinearLayout) findViewById(R.id.returns_arrow);
        // String urlString =
        // "http://192.168.1.150/HXT/upload/1431311588750.epub";
        /**
         * 书籍下载地址
         */
//        String urlString = HttpUrlConfig.photoDir + bookInfo.getBookaddress();
        String urlString = bookInfo.getBookurl();
        // final String localPath =
        // Environment.getExternalStorageDirectory().getAbsolutePath() +
        // "/ADownLoadTest";
        /**
         * 图片
         */
        final String localPath = HttpUrlConfig.cacheDir;
        /**
         * 书籍保存
         */
        if (bookInfo != null && EmptyUtil.IsNotEmpty(bookInfo.getBookurl())) {
            // 文件名：类似（1431311588750.epub）
            final String filename = bookInfo.getBookurl().substring(
                    bookInfo.getBookurl().lastIndexOf("/") + 1,
                    bookInfo.getBookurl().length());

            System.out.println("filename ===   " + filename);

            mDownloadUtil = new DownloadUtil(2, localPath, filename, urlString, this);
            mDownloadUtil.setOnDownloadListener(new OnDownloadListener() {

                @Override
                public void downloadStart(int fileSize) {
                    max = fileSize;
                    pb_shujixiangqing.setMax(fileSize);
                }

                @Override
                public void downloadProgress(int downloadedSize) {
                    pb_shujixiangqing.setProgress(downloadedSize);
                    tv_ppp.setText((int) downloadedSize * 100 / max + "%");
                }

                @Override
                public void downloadEnd() {

//                    isDownLoadBook();
//                    is_addShujia

                    SQLiteDatabase database = MyApplication.helper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("shuji_title", bookInfo.getBookname());
                    cv.put("shuji_content", bookInfo.getDescription());
                    cv.put("shuji_author", bookInfo.getAuthor());
                    cv.put("shuji_photo", bookInfo.getBookcover());
                    cv.put("downLoadurl", bookInfo.getBookurl());
                    cv.put("read_nums", bookInfo.getReadnums());
                    cv.put("publishstatus", bookInfo.getPublishstatus());
                    cv.put("press", bookInfo.getPress());
                    cv.put("stars", bookInfo.getStars());
                    cv.put("size", bookInfo.getSize());
                    cv.put("shuji_id", bookInfo.getId() + "");
                    // 电子书名称
                    cv.put("shuji_sdcard_url", filename);
                    //保存书籍文件类型 epub or pdf
                    cv.put("file_type", bookInfo.getFiletype());

                    if (is_addShujia) {
                        BookDB.updateFilename(String.valueOf(bookInfo.getId()), filename);
                        bookInfo.setSdCardUrl(filename);
                        Log.e("书籍URL  加载时", bookInfo.getSdCardUrl());
                    } else {
                        long row = database.insert("shuji_info", null, cv);
                    }

                    bt_read.setVisibility(View.VISIBLE);
                    pb_shujixiangqing.setVisibility(View.GONE);
                    tv_ppp.setVisibility(View.GONE);
                    bt_shoucan.setVisibility(View.GONE);
                    bt_yishoucang.setVisibility(View.VISIBLE);
                    isDownLoadBook(bookInfo.getId() + "");

                    // 根据sqlite库是否保存，保存了直接阅读，反则下载
                    if (is_addShujia && !is_downloaded) {
                        bt_read.setText("立即阅读(未下载)");
                    } else if (is_downloaded) {
                        bt_read.setText("立即阅读(已下载)");
                        // 广播通知 :刷新前面已下载列表书籍
                        Intent intent = new Intent();
                        intent.setAction("action.shujiDownload");
                        sendBroadcast(intent);
                    }

                    int fileType = bookInfo.getFiletype();
                    System.out.println(fileType + "  file type --------");

                    if (fileType == type_EPUB) {
                        // 复制epub文件
                        if (ZipUtil.copyFile(HttpUrlConfig.cacheDir + "/" + filename, HttpUrlConfig.cacheDir + "/temp.zip")) {
                            if (ZipUtil.unZip(
                                    HttpUrlConfig.cacheDir + "/temp.zip",
                                    HttpUrlConfig.cacheDir + "/"
                                            + filename.substring(0,
                                            filename.lastIndexOf(".")))) {
                                // 删除临时zip文件
                                ZipUtil.deleteFile(HttpUrlConfig.cacheDir + "/temp.zip");
                            }
                        }
                    } else if (fileType == type_PDF) {

                    }
                }
            });
        } else {
            Toast.makeText(shujixiangqingActivity.this, "服务器电子书文件不存了！",
                    Toast.LENGTH_SHORT).show();
        }

        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        bt_read.setOnClickListener(new OnClickListener() {  //阅读
            @Override
            public void onClick(View v) {
                if (getSDCard()) {
                    if (bookInfo != null) {
                        if (EmptyUtil.IsNotEmpty(sdcard_url)) {
//                          if(checkRemoteFileIsExit(HttpUrlConfig.photoDir + bookInfo.getBookaddress())){
                            hitsAdd();

                            if (bookInfo.getFiletype() == type_EPUB || bookInfo.getFiletype() == type_TXT) { //epub
                                String epubUrl = HttpUrlConfig.cacheDir + "/" + sdcard_url;
                                System.out.println("bookSqlInfo = " + sdcard_url);

                                File file = new File(epubUrl);
                                Book book = bs.getBookByFile(file + "");

                                FBReader.openBookActivity(shujixiangqingActivity.this, book, null);

                            } else if (bookInfo.getFiletype() == type_PDF) {  //判断不同的书籍 pdf
                                Intent it = new Intent(shujixiangqingActivity.this, PDFBookActivity.class);
                                System.out.println("jinru  pdf read " + bookInfo.getSdCardUrl());
                                String filename = bookInfo.getBookurl().substring(
                                        bookInfo.getBookurl().lastIndexOf("/") + 1,
                                        bookInfo.getBookurl().length());
                                bookInfo.setSdCardUrl(filename);
                                Log.e("书籍URL", bookInfo.getSdCardUrl());
                                it.putExtra("book", bookInfo);
                                startActivity(it);
                            }
//                          }else{
//                              Toast.makeText(shujixiangqingActivity.this, "服务器书籍文件已不存在了！", Toast.LENGTH_SHORT).show();
//                          }
                        } else {
                            bt_read.setVisibility(View.GONE);
                            pb_shujixiangqing.setVisibility(View.VISIBLE);
                            tv_ppp.setVisibility(View.VISIBLE);
                            mDownloadUtil.start();
                        }
                    } else {
                        bt_read.setVisibility(View.GONE);
                        pb_shujixiangqing.setVisibility(View.VISIBLE);
                        tv_ppp.setVisibility(View.VISIBLE);
                        mDownloadUtil.start();
                    }
                } else {
                    Toast.makeText(shujixiangqingActivity.this, "sd卡不存在！请检查sd卡！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //加入书架
        bt_shoucan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    final String filename = bookInfo.getBookurl().substring(
                            bookInfo.getBookurl().lastIndexOf("/") + 1,
                            bookInfo.getBookurl().length());
//                  getShouCang();
                    SQLiteDatabase database = MyApplication.helper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("shuji_title", bookInfo.getBookname());
                    cv.put("shuji_content", bookInfo.getDescription());
                    cv.put("shuji_author", bookInfo.getAuthor());
                    cv.put("shuji_photo", bookInfo.getBookcover());
                    cv.put("downLoadurl", bookInfo.getBookurl());
                    cv.put("read_nums", bookInfo.getReadnums());
                    cv.put("publishstatus", bookInfo.getPublishstatus());
                    cv.put("press", bookInfo.getPress());
                    cv.put("stars", bookInfo.getStars());
                    cv.put("size", bookInfo.getSize());
                    cv.put("shuji_id", bookInfo.getId() + "");
                    // 电子书名称
                    cv.put("shuji_sdcard_url", "");

                    //file_type epub or pdf
                    int fileType = bookInfo.getFiletype();
                    cv.put("file_type", String.valueOf(fileType));
                    long row = database.insert("shuji_info", null, cv);

                    if (row > 0) {
                        bt_shoucan.setVisibility(View.GONE);
                        bt_yishoucang.setVisibility(View.VISIBLE);
                        is_addShujia = true;
                    } else {
                        //加入书架失败
                    }
                } catch (Exception e) {
                    Log.e("add shujia", e.toString());
                }
            }
        });

        //已加入书架
        bt_yishoucang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//              cancelShouCang();
            }
        });
    }

    /**
     * 查询数据库方法
     *
     * @param bookId
     * @return
     */
    public void isDownLoadBook(String bookId) {

        String sql = "select * from shuji_info where shuji_id =" + bookId;
        // DownLoadHelper dbHelper=new
        // DownLoadHelper(shujixiangqingActivity.this);
        // SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            SQLiteDatabase database = MyApplication.helper.getWritableDatabase();
            Cursor cursor = database.rawQuery(sql, null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                is_addShujia = true;
                sdcard_url = cursor.getString(cursor.getColumnIndex("shuji_sdcard_url"));
                System.out.println("已有书籍----------" + sdcard_url);

                if (sdcard_url != null && sdcard_url.length() > 0) {
                    is_downloaded = true;
                }

            } else {
                System.out.println("没有这个书籍----------" + sdcard_url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(String fileName, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(fileName, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 判断服务器文件是否存在
     *
     * @param fileUrl
     * @return
     */
    private boolean checkRemoteFileIsExit(String fileUrl) {
        boolean flag = true;
        try {
            URL url = new URL(fileUrl);
            // 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
            URLConnection uc = url.openConnection();
            // 打开的连接读取的输入流。
            InputStream in = uc.getInputStream();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * android判断SD卡是否存在以及获取SD卡的路径
     */
    public Boolean getSDCard() {
        Boolean flag = false;
        // 判断SDCard是否存在
        boolean sdExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdExist) {
            // 获取SDCard的路径
//                   File sdFile = Environment.getExternalStorageDirectory();
//                   Toast.makeText(FfffActivity.this, "" + sdFile, 0).show();
            flag = true;
        }
        return flag;
    }

    /**
     * 点击量
     */
    public void hitsAdd() {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.URL_PR + "/book/" + bookInfo.getId() + "/read")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("HandleParameter_onError", e.toString());
//                                 Toast.makeText(activity, "网络异常—未订阅内刊", Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("HandleParameterCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Log.e("HandleParameter_onError", "点击量+1");
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }


    /**
     * 书籍详情
     */
    public void bookDetails() {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.URL_PR + "/book/" + bookInfo.getId())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new BookDetailsCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("BookDetails_onError", e.toString());
//                                 Toast.makeText(activity, "网络异常—未订阅内刊", Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void onResponse(BookDetails response, int id) {
                                 Log.e("BookDetailsCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         /**
                                          * 阅读数
                                          */
                                         if (EmptyUtil.IsNotEmpty(response.getData().getReadnums() + ""))
                                             shujixiangqing_readcishu.setText(response.getData().getReadnums() + "次");
                                         else
                                             shujixiangqing_readcishu.setText("0 次");

                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
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

    private abstract class BookDetailsCallback extends com.zhy.http.okhttp.callback.Callback<BookDetails> {

        @Override
        public BookDetails parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("HandleParameter_String", result);
            BookDetails bookDetails = new Gson().fromJson(result, BookDetails.class);
            return bookDetails;
        }
    }

    @Subscriber(tag = EventBusTags.BOOK_PROGRESS,mode = ThreadMode.MAIN)
    public void onEvent(Message msg) {
        if (msg.what == EventBusTags.UPDATE_BOOK_READ_PROGRESS) {
            tv_progress.setText((CharSequence) msg.obj);
            MyApplication.hxt_setting_config.edit().putString(GlobalConsts.BOOK_PROGRESS+bookInfo.getId(), (String) msg.obj).apply();
            MyApplication.hxt_setting_config.edit().putInt(GlobalConsts.BOOK_CURRENT+bookInfo.getId(), msg.arg1).apply();

        }
    }

}
