package com.qingyii.hxt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.dialog.AbProgressDialogFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxt.adapter.BookMarkListAdapter;
import com.qingyii.hxt.adapter.muluAdapter;
import com.qingyii.hxt.database.BookDB;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.pojo.BookMark;
import com.qingyii.hxt.pojo.BooksParameter;
import com.qingyii.hxt.pojo.EpubBookChapter;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.util.XmlUtil;
import com.qingyii.hxt.util.ZipUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;

//import com.ab.fragment.AbLoadDialogFragment;

public class WebViewReader extends AbBaseActivity {
    /**
     * epubBook电子书对像
     */
    private Book epubBook = null;
    /**
     * 电子书章节资源列表
     */
//	private List<Resource> chapterList = new ArrayList<Resource>();
    private List<TOCReference> chapterList = new ArrayList<TOCReference>();
    /**
     * epub电子书章节实体信息列表
     */
    private ArrayList<EpubBookChapter> myChapterList = new ArrayList<EpubBookChapter>();
//	private ArrayList<Chapter> list = new ArrayList<Chapter>();
    /**
     * 目录适配器
     */
    private muluAdapter adapter;
    /**
     * 书签适配器
     */
    private BookMarkListAdapter bookMarkAdapter;
    /**
     * 书签列表数据源
     */
    private ArrayList<BookMark> bookMarkList = new ArrayList<BookMark>();
    /**
     * 批注适配器
     */
    private BookMarkListAdapter bookRemarkAdapter;
    /**
     * 批注列表数据源
     */
    private ArrayList<BookMark> bookRemarkList = new ArrayList<BookMark>();
    /**
     * 电子书大小：只包括章节内容大小(单位：bytes)
     */
    private long bookSize = 0;
    /**
     * 字号大小 ：大1.5，中1，小0.8
     */
    private double fontSize = 1.0;
    private String htmlContent;
    private String baseUrl = "file:///android_asset/";
    private WebView my_webview;
    private String contentStr = "数据加载中。。。";
    /**
     * 书籍json格式字符串
     */
    private String bookData;
    private ImageView iv_dengguang, iv_yueliang, kanshuziti, iv_kanshumulu,
            iv_kanshujindu, iv_kanshujinduliang, iv_kanshu_sousuo, iv_discuss, iv_book_mark;
    private LinearLayout ll_tab_bottom, ll_kanshujindu;
    private RelativeLayout rl_tab_top;
    private ImageView back_btn;
    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//	private AbLoadDialogFragment mDialogFragment = null;
    private AbProgressDialogFragment mDialogFragment = null;
    /**
     * 书籍pojo对像信息
     */
    //private com.qingyii.hxt.pojo.Book book = null;
    private BooksParameter.DataBean book = null;
    private TextView tv_kanshu_shuming;
    /**
     * 解压书籍存放目录名
     */
    private String fileDir = "";
    /**
     * epub文件解压html文件存放根目录
     */
//    private String epubDir="OPS";
    private String epubDir = "";

    private ProgressDialog pd = null;
    /**
     * seekbar拖动值，书籍拖动阅读
     */
    private int seekNum = 0;
    /**
     * 当前html路径
     */
    private String componentId = "";
    private SeekBar read_seekbar;
    private TextView read_present;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_reader);
        //book = (BooksParameter.DataBean) getIntent().getSerializableExtra("book");
        book = (BooksParameter.DataBean) getIntent().getParcelableExtra("book");
        //增加阅读次数
//        if (book != null) {
//            addreadcount(book.getBookid());
//        }
        // 加载书籍epub数据
        initData();
        initUI();
        // mDialogFragment = AbDialogUtil.showLoadDialog(this,
        // R.drawable.ic_load, "书籍加载中,请等一小会");
        // mDialogFragment
        // .setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
        //
        // @Override
        // public void onLoad() {
        // //加载书籍epub数据
        // initData();
        // }
        //
        // });
    }

    @SuppressLint("NewApi")
    private void initData() {
//		epubBook = getEpubBook("books/168816.epub");
        if (!new File(HttpUrlConfig.cacheDir + "/monoclelib").exists()) {
            //如果此文件不存需要把assest目录下的monoclelib.rar文件解压到HXT目录下
//			if(ZipUtil.copyFile("file:///android_asset/monoclelib.rar", HttpUrlConfig.cacheDir+"/monoclelib.rar")){
            try {
                if (ZipUtil.assetsDataToSD(this, "monoclelib.zip", HttpUrlConfig.cacheDir + "/monoclelib.zip")) {
                    //解压
                    if (ZipUtil.unZip(HttpUrlConfig.cacheDir + "/monoclelib.zip", HttpUrlConfig.cacheDir)) {
                        //解压成功删除rar文件
                        ZipUtil.deleteFile(HttpUrlConfig.cacheDir + "/monoclelib.zip");
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(this, "monoclelib复制失败", Toast.LENGTH_SHORT).show();
            }
        }
        // if(book!=null){
        //文件名：类似（1431311588750.epub）
        //String filename = book.getBookaddress().substring(book.getBookaddress().lastIndexOf("/") + 1, book.getBookaddress().length());
        String filename = book.getSdCardUrl();
        fileDir = filename.substring(0, filename.lastIndexOf("."));
        //获取epub文件解压后html存放的根路径
        String temStr = XmlUtil.getRootDir(HttpUrlConfig.cacheDir + "/" + fileDir + "/META-INF/container.xml");
        if (EmptyUtil.IsNotEmpty(temStr)) {
            epubDir = temStr;
        }
        //epubBook = getEpubBookFromSD(HttpUrlConfig.cacheDir + "/" + book.getSdaddress());
        epubBook = getEpubBookFromSD(HttpUrlConfig.cacheDir + "/" + book.getSdCardUrl());
        // }else{
        // epubBook=getEpubBookFromSD(HttpUrlConfig.cacheDir+"/168816.epub");
        // }
        if (epubBook != null) {
            // 获取所有章节html文件
//			chapterList = epubBook.getContents();
            TableOfContents tableOfContents = epubBook.getTableOfContents();
            chapterList = tableOfContents.getTocReferences();
            if (chapterList.size() > 0) {
                // 赋值章节List
                for (int i = 0; i < chapterList.size(); i++) {
                    if (i == 0) {
                        componentId = "file://" + HttpUrlConfig.cacheDir + "/" + fileDir + "/" + epubDir + "/" + chapterList.get(i).getCompleteHref();
                    }
//					Chapter c = new Chapter();
////					c.setTitle(chapterList.get(i).getTitle());
////					c.setContent(chapterList.get(i).getHref());
//					c.setTitle(chapterList.get(i).getTitle());
//					c.setContent(chapterList.get(i).getCompleteHref());
//					list.add(c);
                    EpubBookChapter ebc = new EpubBookChapter();
                    ebc.setComponentId(chapterList.get(i).getCompleteHref());
                    ebc.setFileDir(fileDir);
                    ebc.setEpubDir(epubDir);
                    ebc.setTitle(chapterList.get(i).getTitle());
                    ebc.setChapterSize(chapterList.get(i).getResource().getSize());
                    ebc.setStartSizeInBook(bookSize);
                    myChapterList.add(ebc);
                    //迭代获取各章节大小来获取书籍大小
                    bookSize += chapterList.get(i).getResource().getSize();
                }
            }
            // 组装bookdata数据（json）
            JSONObject obj = new JSONObject();
            JSONArray componentsJa = new JSONArray();
            JSONArray chaptersJa = new JSONArray();
            try {
                JSONObject metadataObj = new JSONObject();
                metadataObj.put("title", epubBook.getTitle());
                metadataObj.put("creator", "wmh");
//				obj.put("chapters", chaptersJa);
                obj.put("components", componentsJa);
                obj.put("metadata", metadataObj);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            for (int i = 0; i < chapterList.size(); i++) {
//				bookSize += chapterList.get(i).getSize();
//				componentsJa.put("file://" + HttpUrlConfig.cacheDir + "/"
//						+ chapterList.get(i).getHref());
//				bookSize += chapterList.get(i).getResource().getSize();
                componentsJa.put("file://" + HttpUrlConfig.cacheDir + "/" + fileDir + "/" + epubDir + "/"
                        + chapterList.get(i).getCompleteHref());
                //测试
                xpathSearch(HttpUrlConfig.cacheDir + "/" + fileDir + "/" + epubDir + "/"
                        + chapterList.get(i).getCompleteHref(), "战", chapterList.get(i).getTitle());

                JSONObject chapterObj = new JSONObject();
                try {
                    if (EmptyUtil.IsNotEmpty(chapterList.get(i).getTitle())) {
                        chapterObj.put("title", chapterList.get(i).getTitle());
                    } else {
                        chapterObj.put("title", "");
                    }
//					chapterObj.put("src", "file://" + HttpUrlConfig.cacheDir
//							+ "/" + chapterList.get(i).getHref());
                    chapterObj.put("src", "file://" + HttpUrlConfig.cacheDir
                            + "/" + fileDir + "/" + epubDir + "/" + chapterList.get(i).getCompleteHref());
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                chaptersJa.put(chapterObj);
                if (i == 1) {
                    byte[] b;
                    try {
//						b = chapterList.get(i).getData();
//						String str = new String(b, chapterList.get(i)
//								.getInputEncoding());
                        b = chapterList.get(i).getResource().getData();
                        String str = new String(b, chapterList.get(i)
                                .getResource().getInputEncoding());
                        contentStr = str;
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
            // 赋值
            bookData = obj.toString();
            bookData = "Monocle.bookData(" + bookData + ")";
            System.out.println("bookData：" + bookData);
            System.out.println("电子书大小：" + bookSize);
        } else {
            Toast.makeText(WebViewReader.this, "电子书加载异常！", Toast.LENGTH_SHORT)
                    .show();
        }
        //sqlite获取书签列表数据
        //ArrayList<BookMark> tempBMList = BookDB.getBookMarkList(book.getBookid());
        ArrayList<BookMark> tempBMList = BookDB.getBookMarkList(book.getId() + "");
        if ((tempBMList != null) && tempBMList.size() > 0) {
            bookMarkList.clear();
            bookMarkList.addAll(tempBMList);
        }
        //sqlite获取批注列表数据
        //ArrayList<BookMark> tempBRMList = BookDB.getBookRemarkList(book.getBookid());
        ArrayList<BookMark> tempBRMList = BookDB.getBookRemarkList(book.getId() + "");
        if ((tempBRMList != null) && tempBRMList.size() > 0) {
            bookRemarkList.clear();
            bookRemarkList.addAll(tempBRMList);
        }
    }

    /**
     * 获取 epub电子书(assets目录下)
     *
     * @param bookPath
     */
    private Book getEpubBook(String bookPath) {
        Book book = null;
        AssetManager assetManager = getAssets();
        InputStream epubInputStream;
        try {
            epubInputStream = assetManager.open(bookPath);
            // Load Book from inputStream

            book = (new EpubReader()).readEpub(epubInputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return book;
    }

    /**
     * 获取SD卡缓存目录epub电子书
     *
     * @param bookPath
     * @return
     */
    private Book getEpubBookFromSD(String bookPath) {
        Book book = null;
        InputStream epubInputStream = null;
        try {
            if (new File(bookPath).exists()) {
                epubInputStream = new FileInputStream(bookPath);
                book = (new EpubReader()).readEpub(epubInputStream);
            } else {
                Toast.makeText(WebViewReader.this, "电子书找不到了！",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(WebViewReader.this, "电子书加载异常！", Toast.LENGTH_SHORT)
                    .show();
        }
        return book;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
//		super.onBackPressed();
//		this.finish();
        my_webview.loadUrl("javascript:back()");
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

    @SuppressLint({"SetJavaScriptEnabled", "NewApi", "JavascriptInterface"})
    private void initUI() {
        iv_discuss = (ImageView) findViewById(R.id.iv_discuss);
        iv_discuss.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(WebViewReader.this,
                        xiepinglunActivity.class);
                intent.putExtra("comingType", 1);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });
        tv_kanshu_shuming = (TextView) findViewById(R.id.tv_kanshu_shuming);
        if (book != null) {
            tv_kanshu_shuming.setText(book.getBookname());
        }
        read_present = (TextView) findViewById(R.id.read_present);
        read_seekbar = (SeekBar) findViewById(R.id.read_seekbar);
        read_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
                long sizeprogress = bookSize * seekNum / 100;
                for (int i = 0; i < myChapterList.size(); i++) {
                    EpubBookChapter ebc = myChapterList.get(i);
                    if (sizeprogress > ebc.getStartSizeInBook() && sizeprogress <= (ebc.getStartSizeInBook() + ebc.getChapterSize())) {
                        float progressInChapter = (sizeprogress - ebc.getStartSizeInBook()) / (float) ebc.getChapterSize();
//						DecimalFormat decimalFormat=new DecimalFormat("#.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//						String progressInChapterStr=decimalFormat.format(progressInChapter);//format 返回的是字符串
                        my_webview.loadUrl("javascript:movePercent('file://" + HttpUrlConfig.cacheDir + File.separator + fileDir + File.separator + epubDir + File.separator + ebc.getComponentId() + "','" + progressInChapter + "')");
                        break;
                    }
                }
                ll_kanshujindu.setVisibility(View.GONE);
                iv_kanshujindu.setVisibility(View.VISIBLE);
                iv_kanshujinduliang.setVisibility(View.GONE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                // TODO Auto-generated method stub
                read_present.setText(arg1 + "%");
                seekNum = arg1;
            }
        });
        iv_kanshujindu = (ImageView) findViewById(R.id.iv_kanshujindu);
        iv_kanshujindu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ll_kanshujindu.setVisibility(View.VISIBLE);
                iv_kanshujindu.setVisibility(View.GONE);
                iv_kanshujinduliang.setVisibility(View.VISIBLE);
//				Toast.makeText(WebViewReader.this, "即将上线，敬请期待.....", Toast.LENGTH_SHORT).show();
//				my_webview.loadUrl("javascript:movePercent('"+componentId+"','"+0.1+"')");
            }
        });
        iv_kanshujinduliang = (ImageView) findViewById(R.id.iv_kanshujinduliang);
        iv_kanshujinduliang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ll_kanshujindu.setVisibility(View.GONE);
                iv_kanshujindu.setVisibility(View.VISIBLE);
                iv_kanshujinduliang.setVisibility(View.GONE);
//				Toast.makeText(WebViewReader.this, "即将上线，敬请期待.....", Toast.LENGTH_SHORT).show();
            }
        });
        iv_kanshu_sousuo = (ImageView) findViewById(R.id.iv_kanshu_sousuo);
        iv_kanshu_sousuo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				Intent it = new Intent(WebViewReader.this,
//						kanshusousuoActivity.class);
//				startActivity(it);
//				my_webview.loadUrl("javascript:goToXPath('"+componentId+"','//*[contains(text(),\'书\')]','书')");
                findALertUI();
            }
        });
        iv_kanshumulu = (ImageView) findViewById(R.id.iv_kanshumulu);
        iv_kanshumulu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (myChapterList.size() > 0) {
                    /**
                     * abApplication 失效 修改为 gatApplication()
                     */
//					showmulupopWindow(abApplication, v);
                    showmulupopWindow(WebViewReader.this.getApplication(), v);
                } else {
                    Toast.makeText(WebViewReader.this, "此书暂无目录",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        iv_book_mark = (ImageView) findViewById(R.id.iv_book_mark);
        iv_book_mark.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                my_webview.loadUrl("javascript:saveBookMark()");
            }
        });
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        kanshuziti = (ImageView) findViewById(R.id.kanshuziti);
        kanshuziti.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 * abApplication 失效 修改为 gatApplication()
                 */
//                showPopWindow(abApplication, v);
                showPopWindow(WebViewReader.this.getApplication(), v);

            }
        });
        iv_yueliang = (ImageView) findViewById(R.id.iv_yueliang);
        iv_yueliang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                my_webview.loadUrl("javascript:changeDay()");
                iv_yueliang.setVisibility(View.GONE);
                iv_dengguang.setVisibility(View.VISIBLE);
            }
        });
        iv_dengguang = (ImageView) findViewById(R.id.iv_dengguang);
        iv_dengguang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                my_webview.loadUrl("javascript:changeNight()");
                iv_yueliang.setVisibility(View.VISIBLE);
                iv_dengguang.setVisibility(View.GONE);
//				Toast.makeText(WebViewReader.this, "即将上线，敬请期待.....", Toast.LENGTH_SHORT).show();
            }
        });
        back_btn = (ImageView) findViewById(R.id.back_btn);
        ll_tab_bottom = (LinearLayout) findViewById(R.id.ll_tab_bottom);
        rl_tab_top = (RelativeLayout) findViewById(R.id.rl_tab_top);
        iv_kanshu_sousuo = (ImageView) findViewById(R.id.iv_kanshu_sousuo);
        iv_kanshujinduliang = (ImageView) findViewById(R.id.iv_kanshujinduliang);
        ll_kanshujindu = (LinearLayout) findViewById(R.id.ll_kanshujindu);
        iv_kanshujindu = (ImageView) findViewById(R.id.iv_kanshujindu);
        iv_kanshujinduliang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ll_kanshujindu.setVisibility(View.GONE);
                iv_kanshujindu.setVisibility(View.VISIBLE);
                iv_kanshujinduliang.setVisibility(View.GONE);
            }
        });

        my_webview = (WebView) findViewById(R.id.my_webview);
        // 允许JavaScript执行
        my_webview.getSettings().setJavaScriptEnabled(true);
        my_webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        my_webview.getSettings().setEnableSmoothTransition(true);
        my_webview.getSettings().setAllowFileAccess(true);

        my_webview.getSettings().setAllowContentAccess(true);
        my_webview.getSettings().setAllowFileAccessFromFileURLs(true);

        my_webview.getSettings().setBlockNetworkImage(false);
        my_webview.getSettings().setBlockNetworkLoads(false);
        my_webview.getSettings().setLightTouchEnabled(true);
        my_webview.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        my_webview.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        my_webview.addJavascriptInterface(new Contact(), "contact");
        my_webview.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {
                //返回false不触发点击事件
                return false;
            }
        });
        my_webview.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

            }
        });
        my_webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                if (pd != null) {
                    pd.dismiss();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                pd = ProgressDialog.show(WebViewReader.this, "", "内容加载中，请稍等...");
                pd.setCancelable(true);
            }
        });
        //控制JS是否可以alert:重写WebChromeClient可弹出，否则不弹出
        my_webview.setWebChromeClient(new WebChromeClient());
        // 找到Html文件，也可以用网络上的文件
        // 这是本地assets目录的html模板

//		 htmlContent = getStringFromAssets("index3.html");
//		 htmlContent=htmlContent.replace("#content#", contentStr);
//		my_webview.loadDataWithBaseURL(baseUrl, htmlContent, "text/html",
//		"utf-8", null);

//		String bookData3 = "{components:['file:///android_asset/components/1.html','file:///android_asset/components/2.html','file:///android_asset/components/3.html','file:///android_asset/components/toc.html'],metadata: {title: 'Three Ghost Stories',  creator: 'Charles Dickens'}}";
//		String bookData4 = "Monocle.bookData({components:['file:///android_asset/components/1.html','file:///android_asset/components/2.html','file:///android_asset/components/3.html','file:///android_asset/components/toc.html'],metadata: {title: 'Three Ghost Stories',  creator: 'Charles Dickens'}})";
        htmlContent = getStringFromAssets("index3.html");
        htmlContent = htmlContent.replace("#bookData#", bookData);
        if (new File(HttpUrlConfig.cacheDir + "/bookView.html").exists()) {
            ZipUtil.deleteFile(HttpUrlConfig.cacheDir + "/bookView.html");
        }
        if (ZipUtil.writeFileSdcardFile(HttpUrlConfig.cacheDir + "/bookView.html", htmlContent)) {
            my_webview.loadUrl("file://" + HttpUrlConfig.cacheDir + "/bookView.html");
        } else {
            Toast.makeText(WebViewReader.this, "书籍模板文件加载失败，请重新加载", Toast.LENGTH_SHORT).show();
        }

//		 my_webview.loadUrl("file:///android_asset/index3.html");
//		 my_webview.loadUrl("javascript:setBookData('"+bookData3+"')");
//		 my_webview.loadUrl("javascript:initReader2()");

        // my_webview.loadData(contentStr, "text/html; charset=UTF-8",
        // null);//这种写法可以正确解码
    }

    private String getStringFromAssets(String path) {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inputStream2String(inputStream);
    }

    public String inputStream2String(InputStream in) {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        try {
            for (int n; (n = in.read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out.toString();
    }

    /**
     * 刷新书籍章节内容(暂时没用)
     */
    private void updateWebview() {
        if (my_webview != null) {
            htmlContent = getStringFromAssets("index3.html");
            htmlContent = htmlContent.replace("#content#", contentStr);
            // htmlContent = getStringFromAssets("monocle/test/index.html");
            // htmlContent=htmlContent.replace("{$bookdata}", bookData);

            my_webview.loadDataWithBaseURL(baseUrl, htmlContent, "text/html",
                    "utf-8", null);
            my_webview.loadUrl("javascript:onChangeFontSize('" + fontSize
                    + "')");
        }
    }

    /**
     * 设置字号弹出UI
     *
     * @param context
     * @param parent
     */
    private void showPopWindow(Context context, View parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopWindow = inflater.inflate(R.layout.popwindow_font, null,
                false);
        // 宽300 高300
//		final PopupWindow popWindow = new PopupWindow(vPopWindow, 800, 150,
//				true);
        final PopupWindow popWindow = new PopupWindow(vPopWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
                true);
//		popWindow.setWidth(LayoutParams.MATCH_PARENT);
        Button bt_font1 = (Button) vPopWindow.findViewById(R.id.bt_font1);
        // 设置popwindow区域外点击隐藏popwindow
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);
        bt_font1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // my_webview.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
                fontSize = 1.5;
                my_webview.loadUrl("javascript:onChangeFontSize('" + fontSize
                        + "')");
                popWindow.dismiss();
            }
        });

        Button bt_font2 = (Button) vPopWindow.findViewById(R.id.bt_font2);
        bt_font2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // my_webview.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
                fontSize = 1.0;
                my_webview.loadUrl("javascript:onChangeFontSize('" + fontSize
                        + "')");
                popWindow.dismiss(); // Close the Pop Window
            }
        });
        Button bt_font3 = (Button) vPopWindow.findViewById(R.id.bt_font3);
        bt_font3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // my_webview.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
                fontSize = 0.8;
                my_webview.loadUrl("javascript:onChangeFontSize('" + fontSize
                        + "')");
                popWindow.dismiss(); // Close the Pop Window
            }
        });
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 100);
    }

    private final class Contact {
        // JavaScript调用此方法拨打电话
        @JavascriptInterface
        public void call(String phone) {
            // startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +
            // phone)));
            Toast.makeText(WebViewReader.this, phone, Toast.LENGTH_LONG).show();
        }

        // Html调用此方法传递数据
        @JavascriptInterface
        public void showcontacts() {
            String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
            // 调用JS中的方法
//			my_webview.loadUrl("javascript:setBookData('" + bookData + "')");
        }

        /**
         * 当退出阅读器时记录当前阅读进度信息
         *
         * @param locusStr    js加载对像串
         * @param componentId 章节路径
         * @param percent     阅读当前章节进度百分比
         */
        @JavascriptInterface
        public void saveLocus(String locusStr, String componentId, String percent) {
            //locusStr={"page":1,"componentId":"file:///mnt/sdcard/HXT/1432274176647/OEBPS/Text/copyright.html","percent":1}
            //if (BookDB.updateBookRead(book.getBookid(), componentId, percent, locusStr)) {
            if (BookDB.updateBookRead(book.getId() + "", componentId, percent, locusStr)) {
//				Toast.makeText(WebViewReader.this, "记录阅读进度信息成功", Toast.LENGTH_SHORT).show();
                //广播通知 :刷新前面已下载列表书籍
                Intent intent = new Intent();
                intent.setAction("action.shujiDownload");
                sendBroadcast(intent);
            } else {
//				Toast.makeText(WebViewReader.this, "记录阅读进度信息失败", Toast.LENGTH_SHORT).show();
            }
            WebViewReader.this.finish();
        }

        /**
         * 书签保存
         *
         * @param locusStr
         */
        @JavascriptInterface
        public void saveBookMark(String locusStr) {
            //取当前章节名称
            String bookMarkName = "书签名";
            try {
                JSONObject obj = new JSONObject(locusStr);
                String componentId = obj.getString("componentId");
                for (int i = 0; i < myChapterList.size(); i++) {
                    if (componentId.contains(myChapterList.get(i).getComponentId())) {
                        bookMarkName = myChapterList.get(i).getTitle();
                        break;
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //if (BookDB.addBookMark(book.getBookid(), locusStr, bookMarkName)) {
            if (BookDB.addBookMark(book.getId() + "", locusStr, bookMarkName)) {
                Toast.makeText(WebViewReader.this, "书签保存成功", Toast.LENGTH_SHORT).show();
                //向当前书签列表添加数据
                BookMark bm = new BookMark();
                //bm.setBookId(book.getBookid());
                bm.setBookId(book.getId() + "");
                bm.setBookMarkLoucs(locusStr);
                bm.setBookMarkName(bookMarkName);
                bookMarkList.add(bm);
                bookMarkAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(WebViewReader.this, "书签保存失败，请重新操作", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 根据阅读记录进度跳转到指定位置
         */
        @JavascriptInterface
        public void loadLocus() {
            if (EmptyUtil.IsNotEmpty(book.getRead_locus())) {
//				my_webview.loadUrl("javascript:movePercent('"+book.getRead_componentId()+"','"+book.getRead_percent()+"')");
                my_webview.loadUrl("javascript:historyRead(" + book.getRead_locus() + ")");
            }
        }

        /**
         * 长按文字选择,保存批注
         *
         * @param wordStr
         */
        @JavascriptInterface
        public void userselection(String wordStr) {
            //{"cmpid":"file:///mnt/sdcard/HXT/1432274176647/OEBPS/Text/chapter0_1.html","anchorOffset":170,"focusOffset":170,"selectionstring":"","jqueryselector":"html>body>p:nth-child(4)"}
            String selectionstring = "";
            String cmpid = "";
            try {
                JSONObject obj = new JSONObject(wordStr);
                selectionstring = obj.getString("selectionstring");
                cmpid = obj.getString("cmpid");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (EmptyUtil.IsNotEmpty(selectionstring)) {
//				if(selectionstring.length()>1){
//					findALertUI();
                //if (BookDB.addBookRemark(book.getBookid(), wordStr, selectionstring)) {
                if (BookDB.addBookRemark(book.getId() + "", wordStr, selectionstring)) {
                    //向当前批注列表添加数据
                    BookMark bm = new BookMark();
                    //bm.setBookId(book.getBookid());
                    bm.setBookId(book.getId() + "");
                    bm.setBookMarkLoucs(wordStr);
                    bm.setBookMarkName(selectionstring);
                    bookRemarkList.add(bm);
                    bookRemarkAdapter.notifyDataSetChanged();
                    Toast.makeText(WebViewReader.this, "批注保存成功", Toast.LENGTH_SHORT).show();
                    my_webview.loadUrl("javascript:saveBookRemark('" + cmpid + "','" + book.getRead_locus() + "')");
                } else {
                    Toast.makeText(WebViewReader.this, "批注保存失败", Toast.LENGTH_SHORT).show();
                }
//				}
            } else {
//				Toast.makeText(WebViewReader.this, "未选择文字", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 查看目录弹出UI
     *
     * @param context
     * @param parent
     */
    private void showmulupopWindow(Context context, View parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View muluPopWindow = inflater.inflate(R.layout.popwindow_mulu,
                null, false);
        // 宽300 高300
        final PopupWindow popWindow = new PopupWindow(muluPopWindow, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                true);
        //目录listview
        final ListView mListView_mulu = (ListView) muluPopWindow
                .findViewById(R.id.mListView_mulu);
        adapter = new muluAdapter(this, myChapterList);
        mListView_mulu.setAdapter(adapter);
        //书签listview
        final ListView mListView_book_mark = (ListView) muluPopWindow.findViewById(R.id.mListView_book_mark);
        bookMarkAdapter = new BookMarkListAdapter(context, bookMarkList);
        mListView_book_mark.setAdapter(bookMarkAdapter);
        //批注listview
        final ListView mListView_book_remark = (ListView) muluPopWindow.findViewById(R.id.mListView_book_remark);
        bookRemarkAdapter = new BookMarkListAdapter(context, bookRemarkList);
        mListView_book_remark.setAdapter(bookRemarkAdapter);
        final TextView tv_popwindow_mulu = (TextView) muluPopWindow
                .findViewById(R.id.tv_popwindow_mulu);
        final TextView tv_popwindow_pizhu = (TextView) muluPopWindow
                .findViewById(R.id.tv_popwindow_pizhu);
        final TextView tv_popwindow_shuqian = (TextView) muluPopWindow
                .findViewById(R.id.tv_popwindow_shuqian);
        tv_popwindow_mulu.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                tv_popwindow_mulu.setBackground(getResources().getDrawable(
                        R.drawable.shape_all_corners));
                tv_popwindow_mulu.setTextColor(getResources().getColor(
                        R.color.white));
                tv_popwindow_pizhu.setBackground(null);
                tv_popwindow_pizhu.setTextColor(getResources().getColor(
                        R.color.black));
                tv_popwindow_shuqian.setBackground(null);
                tv_popwindow_shuqian.setTextColor(getResources().getColor(
                        R.color.black));
                mListView_book_mark.setVisibility(View.GONE);
                mListView_mulu.setVisibility(View.VISIBLE);
                mListView_book_remark.setVisibility(View.GONE);
            }
        });
        tv_popwindow_pizhu.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                tv_popwindow_pizhu.setBackground(getResources().getDrawable(
                        R.drawable.shape_all_corners));
                tv_popwindow_pizhu.setTextColor(getResources().getColor(
                        R.color.white));
                tv_popwindow_mulu.setBackground(null);
                tv_popwindow_mulu.setTextColor(getResources().getColor(
                        R.color.black));
                tv_popwindow_shuqian.setBackground(null);
                tv_popwindow_shuqian.setTextColor(getResources().getColor(
                        R.color.black));
                mListView_book_mark.setVisibility(View.GONE);
                mListView_mulu.setVisibility(View.GONE);
                mListView_book_remark.setVisibility(View.VISIBLE);
            }
        });
        tv_popwindow_shuqian.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                tv_popwindow_shuqian.setBackground(getResources().getDrawable(
                        R.drawable.shape_all_corners));
                tv_popwindow_shuqian.setTextColor(getResources().getColor(
                        R.color.white));
                tv_popwindow_pizhu.setBackground(null);
                tv_popwindow_pizhu.setTextColor(getResources().getColor(
                        R.color.black));
                tv_popwindow_mulu.setBackground(null);
                tv_popwindow_mulu.setTextColor(getResources().getColor(
                        R.color.black));
                mListView_book_mark.setVisibility(View.VISIBLE);
                mListView_mulu.setVisibility(View.GONE);
                mListView_book_remark.setVisibility(View.GONE);
            }
        });
        // 设置popwindow区域外点击隐藏popwindow
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);

        popWindow.showAtLocation(parent, Gravity.TOP, 0, 200);
        mListView_mulu.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//				byte[] b;
//				try {
////					b = chapterList.get(position).getData();
////					String str = new String(b, chapterList.get(position)
////							.getInputEncoding());
//					b = chapterList.get(position).getResource().getData();
//					String str = new String(b, chapterList.get(position)
//							.getResource().getInputEncoding());
//					contentStr = str;
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				updateWebview();
                componentId = "file://" + HttpUrlConfig.cacheDir + "/" + fileDir + "/" + epubDir + "/" + chapterList.get(position).getCompleteHref();
                String jsStr = "javascript:jumpChapter('" + componentId + "')";
                my_webview.loadUrl(jsStr);
                popWindow.dismiss();

            }
        });
        mListView_book_mark.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                my_webview.loadUrl("javascript:historyRead(" + bookMarkList.get(arg2).getBookMarkLoucs() + ")");
                popWindow.dismiss();
            }
        });
        mListView_book_remark.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                my_webview.loadUrl("javascript:historyRead(" + bookRemarkList.get(arg2).getBookMarkLoucs() + ")");
                popWindow.dismiss();
            }
        });
    }

//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		if(EmptyUtil.IsNotEmpty(book.getRead_componentId())){
//			my_webview.loadUrl("javascript:movePercent('"+book.getRead_componentId()+"','"+book.getRead_percent()+"')");
//		}
//	}

    /**
     * 搜索关键字弹出UI
     */
    private void findALertUI() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        //方式一：处理不能弹出输入键盘
        dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //方式二：处理不能弹出输入键盘
//		final AlertDialog dlg = new AlertDialog.Builder(this).create();
//		dlg.setView(LayoutInflater.from(this).inflate(R.layout.keyword_find, null));
//		dlg.show();

        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,keyword_find.xml文件中定义view内容
        window.setContentView(R.layout.keyword_find);
        final EditText et_keyword = (EditText) window.findViewById(R.id.et_keyword);
        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (EmptyUtil.IsNotEmpty(et_keyword.getText().toString())) {
                    String keyWord = et_keyword.getText().toString();
//					String jsStr="javascript:goToXPath('"+componentId+"','//*[contains(text(),'"+keyWord+"')]','"+keyWord+"')";
//					my_webview.loadUrl(jsStr);
                    String xpathStr = "//*[contains(text(),\"" + keyWord + "\")]";
                    System.out.println(xpathStr);
                    my_webview.loadUrl("javascript:goToXPath('" + componentId + "','" + xpathStr + "','" + keyWord + "')");
                    dlg.dismiss();
                } else {
                    Toast.makeText(WebViewReader.this, "请输入你要搜索的关键字！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView btn_cancel = (TextView) window.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dlg.dismiss();
            }
        });
    }

    private static Document doc;
    private static XPath xpath;

    /**
     * xpath处理
     *
     * @param htmlUrl 章节ID
     * @param keyWord 搜索关键字
     * @param cTitle  章节名称
     */
    private void xpathSearch(String htmlUrl, String keyWord, String cTitle) {
        //章节ID全路径
        String cid = "file://" + htmlUrl;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(new FileInputStream(new File(htmlUrl)));

            // 创建XPath对象
            XPathFactory factory = XPathFactory.newInstance();
            xpath = factory.newXPath();
            NodeList nodeList;
            try {
                nodeList = (NodeList) xpath.evaluate("//*[contains(text(),'" + keyWord + "')]", doc,
                        XPathConstants.NODESET);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    System.out.println(htmlUrl + "获取节点列表项目：" + nodeList.item(i).getNodeName() + " ");
                    //获取节点属性
                    if (nodeList.item(i).getAttributes().getLength() > 0) {

                    } else {

                    }
                    System.out.println("章节ID：" + cid);
                    System.out.println("章节名称：" + cTitle);
                }
            } catch (XPathExpressionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 增加阅读次数接口
     */
//    private void addreadcount(String bookid) {
//        JSONObject jsonObj = new JSONObject();
//        try {
////			jsonObj.put("readcount", 1);
//            jsonObj.put("bookid", bookid);
//            byte[] bytes;
//            ByteArrayEntity emEntity = null;
//            try {
//                bytes = jsonObj.toString().getBytes("utf-8");
//                emEntity = new ByteArrayEntity(bytes);
//
//                Log.e("网络阅读增加次数_out", jsonObj.toString());
//
//                YzyHttpClient.post(WebViewReader.this, HttpUrlConfig.addReadCount, emEntity,
//                        new AsyncHttpResponseHandler() {
//                            @SuppressWarnings("deprecation")
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("网络阅读增加次数_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(WebViewReader.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(WebViewReader.this, LoginActivity.class);
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
//    }
}
