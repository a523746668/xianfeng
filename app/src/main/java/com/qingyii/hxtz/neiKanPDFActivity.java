package com.qingyii.hxtz;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxtz.download.DownloadUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.httpway.HandleParameterCallback;
import com.qingyii.hxtz.pojo.ArticleListNK;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.util.EmptyUtil;
import com.shockwave.pdfium.PdfDocument;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.IOError;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/9/24.
 */

public class neiKanPDFActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 221;
    public static final int READ_PHONE_STATE_REQUEST_CODE = 222;
    public static final int ACCESS_NETWORK_STATE_REQUEST_CODE = 223;
    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 224;
    public static final int ACCESS_COARSE_LOCATION = 225;
    public static final int ALL_REQUEST_CODE = 220;
    private final static int REQUEST_CODE = 42;

    private DownloadUtil mDownloadUtil;
    private String[] needPermission_list;

    private static final String TAG = neiKanPDFActivity.class.getSimpleName();

    private PDFView pdfView;
    private Uri uri;

    private ImageView is_praise;

    private ShapeLoadingDialog shapeLoadingDialog = null;
    private Integer pageNumber = 0;

    private ArticleListNK.DataBean article_pdf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
         setContentView(R.layout.nk_pdfviewer_activity);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pdfView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        is_praise = (ImageView) findViewById(R.id.is_praise);

        GetPermission();
        initData();

        if (article_pdf.getIs_upvote() == 1)
            is_praise.setSelected(true);
        else
            is_praise.setSelected(false);

        is_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (article_pdf.getIs_upvote() == 0) {
                    final Dialog dialog = new Dialog(neiKanPDFActivity.this, R.style.DialogStyle);

                    View menuContentLayout = View.inflate(neiKanPDFActivity.this, R.layout.neikan_dialog, null);
                    TextView neikan_dialog_context = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_context);
                    TextView neikan_dialog_l = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_l);
                    TextView neikan_dialog_r = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_r);

                    neikan_dialog_context.setVisibility(View.VISIBLE);
                    neikan_dialog_context.setText("确定要点赞吗");
                    neikan_dialog_l.setText("取消");
                    neikan_dialog_r.setText("点赞");
                    neikan_dialog_l.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    neikan_dialog_r.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isLike(dialog);
                        }
                    });
                    dialog.setContentView(menuContentLayout);
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show();
                } else {
                    Toast.makeText(neiKanPDFActivity.this, "您已经点过赞！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setTitle(article_pdf.getTitle());
    }

    public void isLike(final Dialog dialog) {

        //Log.e("article_id", article_pdf.getId() + "");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getUpvoteUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("article_id", article_pdf.getId() + "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Like_onError", e.toString());
//                                 Toast.makeText(activity, "网络异常—请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("LikeCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(neiKanPDFActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
//                                         textLike.setText(aDataDocsBeen.get(position).getUpvote()+1+"");
//                                         textLike.setSelected(true);
                                         article_pdf.setUpvote(article_pdf.getUpvote() + 1);
                                         article_pdf.setIs_upvote(1);
                                         Intent intent = new Intent();
                                         is_praise.setSelected(true);
                                         intent.putExtra("Article", article_pdf);
                                         setResult(RESULT_OK, intent);
                                         dialog.dismiss();
                                         break;
                                     default:
                                         Toast.makeText(neiKanPDFActivity.this, "点赞失败", Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    public void initData() {
        //book = (Book) getIntent().getSerializableExtra("book");
        //String  bookaddr = book.getBookaddress();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            article_pdf = b.getParcelable("Article");
            if (article_pdf != null) {
                String bookadr = article_pdf.getPdf_path();
                //NKSaveArticle(bookadr,article_pdf);
                //下载
                download();
                //uri = Uri.parse(bookadr);
                //if (uri != null) {
                //displayFromUri(uri);
                //}
            }
        }
        //System.out.println(bookaddr+"--------");
    }

    private void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void download() {

        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("下载中,请等一小会");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        /**
         * 书籍下载地址
         */
//        String urlString = HttpUrlConfig.photoDir + bookInfo.getBookaddress();
        String urlString = article_pdf.getPdf_path();
        // final String localPath =
        // Environment.getExternalStorageDirectory().getAbsolutePath() +
        // "/ADownLoadTest";
        System.out.println(urlString + "------url url--");
        /**
         * 图片
         */
        final String localPath = HttpUrlConfig.cacheDir;
        /**
         * 书籍保存
         */
//        if (bookInfo != null && EmptyUtil.IsNotEmpty(bookInfo.getBookaddress())) {
//            // 文件名：类似（1431311588750.epub）
//            final String filename = bookInfo.getBookaddress().substring(
//                    bookInfo.getBookaddress().lastIndexOf("/") + 1,
//                    bookInfo.getBookaddress().length());
        if (article_pdf != null && EmptyUtil.IsNotEmpty(urlString)) {
            // 文件名：类似（1431311588750.epub）
            final String filename = urlString.substring(
                    urlString.lastIndexOf("/") + 1,
                    urlString.length());
            mDownloadUtil = new DownloadUtil(2, localPath, filename, urlString,
                    this);
            mDownloadUtil.setOnDownloadListener(new DownloadUtil.OnDownloadListener() {
                @Override
                public void downloadStart(int fileSize) {
                    System.out.println("---start----");
                    shapeLoadingDialog.show();
                }

                @Override
                public void downloadProgress(int downloadedSize) {
                    //int percent = downloadedSize/
                    shapeLoadingDialog.setLoadingText("下载中,请等一小会");
                    System.out.println("---downloadsize----" + downloadedSize);
                }

                @Override
                public void downloadEnd() {
                    if (shapeLoadingDialog != null) {
                        shapeLoadingDialog.dismiss();
                    }
                    // Bitmap bitmap = decodeSampledBitmapFromResource(localPath
                    // + File.separator + "abc.jpg", 200, 200);
                    // image.setImageBitmap(bitmap);

                    // 数据库插入数据
                    // dbHelper=new DownLoadHelper(shujixiangqingActivity.this);
                    // SQLiteDatabase database = dbHelper.getWritableDatabase();
                    System.out.println("下载ok--------------");
                    String filepath = localPath + filename;
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                      uri= FileProvider.getUriForFile(neiKanPDFActivity.this,"com.qingyiiz.zhf1.fileprovider",new File(filepath));
                     }else {
                        uri= Uri.parse(filepath);
                    }

                    displayFromUri(uri);
                    //System.out.println("---已下载-111-开始读");

                    // 广播通知 :刷新前面已下载列表书籍
                    Intent intent = new Intent();
                    intent.setAction("action.shujiDownload");
                    sendBroadcast(intent);
                }
            });
            String filepath = localPath + "/" + filename;
            //System.out.println(filepath+" ------------filepathd");
            File file = new File(filepath);
            if (file.exists()) {
                Uri uri ;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri= FileProvider.getUriForFile(this,"com.qingyiiz.zhf1.fileprovider",file);
                }else {
                    uri= Uri.parse(filepath);
                }
                try {
                    displayFromUri(uri);
                    System.out.println("---已下载--开始读");
                } catch (IOError e) {
                    e.printStackTrace();
                    System.out.println("---读取失败--将删除--再下载");
                    {
                        file.delete();
                        mDownloadUtil.start();
                    }
                }
            } else {
                mDownloadUtil.start();
                System.out.println("不存在");
            }
        } else {
            Toast.makeText(neiKanPDFActivity.this, "服务器电子书文件不存了！", Toast.LENGTH_SHORT).show();
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                savePermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case READ_PHONE_STATE_REQUEST_CODE:
                savePermission(android.Manifest.permission.READ_PHONE_STATE);
                break;
            case READ_EXTERNAL_STORAGE_REQUEST_CODE:
                savePermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
            case ACCESS_NETWORK_STATE_REQUEST_CODE:
                savePermission(android.Manifest.permission.ACCESS_NETWORK_STATE);
                break;
            case ALL_REQUEST_CODE: {
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equals(android.Manifest.permission.READ_PHONE_STATE)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        //用户同意使用 获取 imei 这样的 设备 id
                        //JSession.getInstance().startPushWork();
                    }
                }
            }
            break;
            default:
                break;
        }
    }

    private void displayFromUri(Uri uri) {
        //pdfFileName = getFileName(uri);
        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    public void onActivityResult(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            uri = intent.getData();
            displayFromUri(uri);
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", article_pdf.getTitle(), page + 1, pageCount));
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    private void GetPermission() {
        System.out.println("------获取权限-------");
        //读取是否获取权限
        if (Build.VERSION.SDK_INT >= 23) {
            boolean has_WRITE_EXTERNAL_STORAGE = false;
            boolean has_READ_PHONE_STATE = false;
            boolean has_READ_EXTERNAL_STORAGE = false;
            boolean has_ACCESS_NETWORK_STATE = false;
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                has_READ_EXTERNAL_STORAGE = false;
                System.out.println("检查 READ_EXTERNAL_STORAGE");
            } else
                has_READ_EXTERNAL_STORAGE = true;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                has_WRITE_EXTERNAL_STORAGE = false;
                System.out.println("检查 WRITE_EXTERNAL_STORAGE");
            } else
                has_WRITE_EXTERNAL_STORAGE = true;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                has_READ_PHONE_STATE = false;
                System.out.println("检查 READ_PHONE_STATE");
            } else
                has_READ_PHONE_STATE = true;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                has_ACCESS_NETWORK_STATE = false;
                System.out.println("检查 ACCESS_NETWORK_STATE");
            } else
                has_ACCESS_NETWORK_STATE = true;

            //添加权限
            needPermission_list = new String[]{""};
            if (!has_READ_EXTERNAL_STORAGE)
                addString(android.Manifest.permission.READ_EXTERNAL_STORAGE);

            if (!has_WRITE_EXTERNAL_STORAGE)
                addString(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (!has_READ_PHONE_STATE)
                addString(android.Manifest.permission.READ_PHONE_STATE);

            if (!has_ACCESS_NETWORK_STATE)
                addString(android.Manifest.permission.ACCESS_NETWORK_STATE);

            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, needPermission_list, ALL_REQUEST_CODE);
        }
    }

    private void addString(String string) {
        if (needPermission_list.length == 1 && needPermission_list[0].length() == 0) {  //初始化时
            needPermission_list = new String[]{string};
        } else {
            needPermission_list = Arrays.copyOf(needPermission_list, needPermission_list.length + 1);
            needPermission_list[needPermission_list.length - 1] = string;
            for (String s : needPermission_list) {
                System.out.println(s + "---  lenght = " + needPermission_list.length);
            }
        }
    }

    public void savePermission(String permissionName) {
//        //写入权限
//        SharedPreferences mySharedPreferences= getSharedPreferences(permissionName,
//                Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mySharedPreferences.edit();
////用putString的方法保存数据
//        editor.putString("allow", "Karl");
////提交当前数据
//        editor.commit();
////使用toast信息提示框提示成功写入数据
//        Toast.makeText(this, "成功保存权限！"+permissionName , Toast.LENGTH_LONG).show();
    }
}
