package com.qingyii.hxtz.circle;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.util.AbFileUtil;
import com.andbase.library.util.AbStrUtil;
import com.andbase.library.util.AbToastUtil;
import com.google.gson.Gson;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.CircleType;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.httpway.HandleParameterCallback;
import com.qingyii.hxtz.pojo.Category;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.pojo.UploadPictures;
import com.qingyii.hxtz.util.ImageCompressUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 创建新动态
 *
 * @author Lee
 */
public class CircleCreateActivity extends AbBaseActivity {

    public static final int TIMEOUT_CONNECTION = 300 * 1000;
    public static final int TIMEOUT_SOCKET = 450 * 1000;

    private static final String DEFAULT_ADDIMAGE = "" + R.mipmap.ic_addphoto;

    private ImageView mTextLeft;
    private TextView mTextRight;
    private ArrayList<String> mArrayImgs;
    private List<Integer> ImagesID;
    private ArrayList<CircleType> mArrayType;
    private EditText mEditContent;
    private TextView mTextNum;
    // private GridView mGridView;
    // private ImageShowAdapter mImageShowAdapter;
    private DialogFragment mAlertDialog = null;
    private String[] mStrUpload = new String[]{};
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CircleAlbumAdapter mAlbumAdapter;

    private TextView mTextType;
    private RelativeLayout circle_create_rl;

    private int mCircleType = -1;

    private int selectIndex = 0;
    private int camIndex = 0;
    private View mAvatarView = null;

    /* 用来标识请求照相功能的activity */
    private static final int CAMERA_WITH_DATA = 3023;
    /* 用来标识请求gallery的activity */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;
    /* 用来标识请求裁剪图片后的activity */
    private static final int CAMERA_CROP_DATA = 3022;
    /* 用来标识请求选择类型的activity */
    private static final int START_CHOOSE_TYPE = 3024;

    /* 拍照的照片存储位置 */
    private File PHOTO_DIR = null;
    // 照相机拍照得到的图片
    private File mCurrentPhotoFile;
    private String mFileName;

    private AbHttpUtil mAbHttpUtil = null;

    //弹窗
    private Dialog dialog;

    private Handler mHandler = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
            switch (msg.what) {
                case 0:
                    Toast.makeText(CircleCreateActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    Toast.makeText(CircleCreateActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(CircleCreateActivity.this,
//                                CircleMainActivity.class);
//                        setResult(RESULT_OK, intent);
//                    finish();
                    break;
                case 2:
                    CircleCreateActivity.this.uploadData();
                    break;
                case 3:
                    Toast.makeText(CircleCreateActivity.this, "图片上传失败，请重试", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });
    //    AbAlertDialogFragment;
//    AbSampleDialogFragment;
//    AbProgressDialogFragment;
    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
//    private AbProgressDialogFragment mDialogFragment = null;
    private ShapeLoadingDialog shapeLoadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_create);
//        Intent intent = getIntent();
//        Associates.DataBean.DocsBean aDocsBean = (Associates.DataBean.DocsBean) intent.getParcelableExtra("aDocsBean");
//        setResult(RESULT_OK, intent);

        findView();
        initData();
        setListener();
//        getTypeData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("生命周期执行：", " onPause");
        setResult(RESULT_OK);

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.e("生命周期执行：", " onStop");
        setResult(RESULT_OK);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("生命周期执行：", " onDestroy");
        setResult(RESULT_OK);

    }

    private void initData() {
        // 获取Http工具类
        mAbHttpUtil = AbHttpUtil.getInstance(this);
        resetArrayImgs();
        mArrayType = new ArrayList<CircleType>();
        // mImageShowAdapter = new ImageShowAdapter(this, mArrayImgs, 150,150);
        // mGridView.setAdapter(mImageShowAdapter);
        mAlbumAdapter = new CircleAlbumAdapter(this, ImagesID, mArrayImgs);
        mRecyclerView.setAdapter(mAlbumAdapter);

        // 初始化图片保存路径
        String photo_dir = AbFileUtil.getImageDownloadDir(this);
        if (AbStrUtil.isEmpty(photo_dir)) {
            AbToastUtil.showToast(CircleCreateActivity.this, "存储卡不存在");
        } else {
            PHOTO_DIR = new File(photo_dir);
        }
    }

    private void resetArrayImgs() {
        ImagesID = new ArrayList<Integer>();
        mArrayImgs = new ArrayList<String>();
        mArrayImgs.add(DEFAULT_ADDIMAGE);
    }

    private void findView() {
        //弹窗设置
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);

        mTextLeft = (ImageView) findViewById(R.id.circle_create_left);
        mTextRight = (TextView) findViewById(R.id.circle_create_right);
        mEditContent = (EditText) findViewById(R.id.circle_create_edit);
        mTextNum = (TextView) findViewById(R.id.circle_create_textnum);
        mRecyclerView = (RecyclerView) findViewById(R.id.circle_create_grid);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTextType = (TextView) findViewById(R.id.circle_create_type);
        circle_create_rl = (RelativeLayout) findViewById(R.id.circle_create_rl);
    }

    private void setListener() {
        mEditContent.addTextChangedListener(mTextWatcher);
        mTextLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CircleCreateActivity.this.finish();
            }
        });
        mTextRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (TextUtils.isEmpty(mEditContent.getText()) && mArrayImgs.size() <= 1) {
                    Toast.makeText(CircleCreateActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * 判断是否选择类型
                 */
                if (mCircleType == -1) {
                    Toast.makeText(CircleCreateActivity.this, "请选择类型", Toast.LENGTH_SHORT).show();
                    return;
                }

                CircleCreateActivity.this.uploadData();

                shapeLoadingDialog = new ShapeLoadingDialog(CircleCreateActivity.this);
                shapeLoadingDialog.setLoadingText("上传中,请等一小会");
                shapeLoadingDialog.show();

            }
        });
        circle_create_rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(CircleCreateActivity.this, CircleChooseTypeActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                startActivityForResult(intent, START_CHOOSE_TYPE);
                // Intent intent = new Intent(CircleCreateActivity.this, AddPhotoActivity.class);
            }
        });
        mAlbumAdapter.setCallback(new CircleAlbumAdapter.Callback() {

            @Override
            public void onItemClick(int position) {
                selectIndex = position;
                if (mArrayImgs.get(0).equals(DEFAULT_ADDIMAGE)) {
                    if (selectIndex == 0) {
                        if (mArrayImgs.size() > 9) {
                            Toast.makeText(CircleCreateActivity.this, "最多能选择9张相片", Toast.LENGTH_SHORT).show();
                            return;
                        }
//                        mAvatarView = mInflater.inflate(R.layout.choose_avatar, null);
                        mAvatarView = View.inflate(CircleCreateActivity.this, R.layout.choose_avatar, null);

                        Button albumButton = (Button) mAvatarView.findViewById(R.id.choose_album);
                        Button camButton = (Button) mAvatarView.findViewById(R.id.choose_cam);
                        Button cancelButton = (Button) mAvatarView.findViewById(R.id.choose_cancel);
                        albumButton.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
//                                AbDialogUtil.removeDialog(CircleCreateActivity.this);
                                dialog.dismiss();
                                // 从相册中去获取
                                try {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                                    intent.setType("image/*");
                                    startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                                } catch (ActivityNotFoundException e) {
                                    AbToastUtil.showToast(CircleCreateActivity.this, "没有找到照片");
                                }
                            }

                        });

                        camButton.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
//                                AbDialogUtil.removeDialog(CircleCreateActivity.this);
                                doTakePhoto();
                                dialog.dismiss();
                            }

                        });

                        cancelButton.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
//                                AbDialogUtil.removeDialog(CircleCreateActivity.this);
                                dialog.dismiss();
                            }
                        });
                        //将布局设置给Dialog
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        } else {
                            dialog.setContentView(mAvatarView);
                            dialog.getWindow().setGravity(Gravity.BOTTOM);
                            //获得屏幕看都，并传给dialog
                            dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                            dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                            dialog.show();
                        }
//                        AbDialogUtil.showDialog(mAvatarView, Gravity.BOTTOM);
                    } else {
                        ArrayList<String> array = new ArrayList<String>();
                        array.addAll(mArrayImgs);
                        array.remove(0);
                        BigPhotoLocalFragment frag = BigPhotoLocalFragment.getInstance(array, selectIndex - 1);
                        frag.show(getSupportFragmentManager(),
                                "BigPhotoLocalFragment");
                    }
                } else {
                    BigPhotoLocalFragment frag = BigPhotoLocalFragment.getInstance(mArrayImgs, position);
                    frag.show(getSupportFragmentManager(), "BigPhotoLocalFragment");
                }
            }
        });
    }

    /**
     * 从照相机获取
     */
    private void doPickPhotoAction() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            doTakePhoto();
        } else {
            AbToastUtil.showToast(CircleCreateActivity.this, "没有可用的存储卡");
        }
    }

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            mFileName = System.currentTimeMillis() + ".jpg";
            mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (Exception e) {
            AbToastUtil.showToast(CircleCreateActivity.this, "未找到系统相机程序");
        }
    }

    /**
     * 提交发布
     */
    private void uploadData() {

        Log.e("Category_URL", XrjHttpClient.getCirclePostUrl());
        String picture = "";
        for (int i = 0; i < ImagesID.size(); i++) {
            if ((i + 1) < ImagesID.size())
                picture = "," + ImagesID.get(i) + picture;
            else
                picture = ImagesID.get(i) + picture;
        }
        Log.e("Category_PictureID", picture);

        OkHttpUtils
                .post()
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .url(XrjHttpClient.getCirclePostUrl())
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doctype", mCircleType + "")//纪实类型
                .addParams("visible", "open")//可见范围 open 开放 department 仅部门 care 仅关注
                .addParams("content", mEditContent.getText().toString() + "")//纪实内容
                .addParams("picture", picture)//纪实图片 以 “,” (半角逗号)隔开的图片id
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("CirclePost_onError", e.toString());
//                                 Toast.makeText(CircleCreateActivity.this, "网络异常—发布工作圈", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("CirclePostCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
//                                         mHandler.sendEmptyMessage(1);
                                         Toast.makeText(CircleCreateActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                                         CircleCreateActivity.this.setResult(RESULT_OK, new Intent());
                                         finish();
                                         break;
                                     case 1:
//                                         mHandler.sendEmptyMessage(1);
                                         Toast.makeText(CircleCreateActivity.this, "暂时没有分类数据", Toast.LENGTH_SHORT).show();
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

//    private void uploadData() {
//        JSONObject jsonobject = new JSONObject();
//        try {
//            Gson gson = new Gson();
//            jsonobject.put("contenttxt", mEditContent.getText().toString());
//            JSONArray urlsJson = new JSONArray(gson.toJson(mStrUpload,
//                    String[].class));
//            jsonobject.put("contentimageurllist", urlsJson);
//            jsonobject.put("userid", CacheUtil.userid);
//            CircleLoc loc = new CircleLoc();
//            loc.setLat(0);
//            loc.setLng(0);
//            JSONObject locationJson = new JSONObject(gson.toJson(loc));
//            jsonobject.put("location", locationJson);
//            jsonobject.put("permission", 0);
//            jsonobject.put("typeid", mCircleType);
//            Log.e("tag", jsonobject.toString());
//            byte[] bytes = jsonobject.toString().getBytes("utf-8");
//            ByteArrayEntity entity = new ByteArrayEntity(bytes);
//            YzyHttpClient.post(this, HttpUrlConfig.reqCreateDynamicInfo,
//                    entity, new AsyncHttpResponseHandler() {
//
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            if (statusCode == 499) {
//                                Toast.makeText(CircleCreateActivity.this,
//                                        CacheUtil.logout, Toast.LENGTH_SHORT)
//                                        .show();
//                                Intent intent = new Intent(
//                                        CircleCreateActivity.this,
//                                        LoginActivity.class);
//                                startActivity(intent);
//                                onFinish();
//                            } else if (statusCode == 200) {
//                                try {
//                                    JSONObject js = new JSONObject(content);
//                                    int dynamicinfoid = js
//                                            .getInt("dynamicinfoid");
//                                    Message msg = Message.obtain();
//                                    msg.arg1 = dynamicinfoid;
//                                    msg.what = 1;
//                                    mHandler.sendMessage(msg);
//                                } catch (JSONException e) {
//                                    mHandler.sendEmptyMessage(0);
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
//
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
//                                Toast.makeText(CircleCreateActivity.this,
//                                        CacheUtil.logout, Toast.LENGTH_SHORT)
//                                        .show();
//                                Intent intent = new Intent(
//                                        CircleCreateActivity.this,
//                                        LoginActivity.class);
//                                startActivity(intent);
//                                onFinish();
//                            } else if (statusCode == 200) {
//                                Gson gson = new Gson();
//                                try {
//                                    JSONObject js = new JSONObject(content);
//                                    String a = js.getString("rows");
//                                    if (EmptyUtil.IsNotEmpty(js
//                                            .getString("rows"))) {
//
//                                        JSONArray lists = js
//                                                .getJSONArray("rows");
//
//                                        if (lists.length() <= 0) {
//                                            // handler.sendEmptyMessage(5);
//                                        } else {
//                                            for (int i = 0; i < lists.length(); i++) {
//                                                CircleType type = gson.fromJson(
//                                                        lists.getString(i),
//                                                        CircleType.class);
//                                                mArrayType.add(type);
//                                            }
//                                            // .sendEmptyMessage(1);
//                                        }
//
//                                    } else {
//                                        // handler.sendEmptyMessage(5);
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

    /**
     * 从相册得到的url转换为SD卡中图片路径
     */
    public String getPath(Uri uri) {
        if (AbStrUtil.isEmpty(uri.getAuthority())) {
            return null;
        }
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String[] names = cursor.getColumnNames();
                res = cursor.getString(column_index);
            }
            cursor.close();
        } else {
            res = uri.getPath();
        }
        return res;
    }

    /**
     * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况, 他们启动时是这样的startActivityForResult
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case START_CHOOSE_TYPE:
                if (resultCode == RESULT_OK && mIntent != null) {
//                    CircleType type = (CircleType) mIntent
//                            .getSerializableExtra("CircleType");
//                    mCircleType = type.getTypeid();
//                    this.mTextType.setText(type.getTypename());
                    Category.DataBean category = (Category.DataBean) mIntent.getParcelableExtra("Category");
                    mCircleType = category.getId();
                    this.mTextType.setText(category.getName());
                }
                break;
            case PHOTO_PICKED_WITH_DATA:
                Uri uri = mIntent.getData();
                // String currentFilePath = getPath(uri);
                String currentFilePath = GetPathFromUri4kitkat.getPath(this, uri);
                if (!AbStrUtil.isEmpty(currentFilePath)) {
                    /**
                     * 原本是将 图片路径直接给数组，现在更改为做网络请求
                     */
//                    refreshGrid(currentFilePath);
//                    uploadimges(currentFilePath);
                    ImageCompressUtil.compressImage(this, currentFilePath, new ImageCompressUtil.ProcessImgCallBack() {
                        @Override
                        public void compressSuccess(String imgPath) {
                            uploadimges(imgPath);
                        }
                    });
                    // Intent intent1 = new Intent(this, CropImageActivity.class);
                    // intent1.putExtra("PATH", currentFilePath);
                    // startActivityForResult(intent1, CAMERA_CROP_DATA);
                } else {
                    AbToastUtil
                            .showToast(CircleCreateActivity.this, "未在存储卡中找到这个文件");
                }
                break;
            case CAMERA_WITH_DATA:
                // AbLogUtil.d(AddPhotoActivity.class, "将要进行裁剪的图片的路径是 = " +
                // mCurrentPhotoFile.getPath());
                String currentFilePath2 = mCurrentPhotoFile.getPath();
                /**
                 * 原本是将 图片路径直接给数组，现在更改为做网络请求
                 */
//                refreshGrid(currentFilePath2);
//                uploadimges(currentFilePath2);
                ImageCompressUtil.compressImage(this, currentFilePath2, new ImageCompressUtil.ProcessImgCallBack() {
                    @Override
                    public void compressSuccess(String imgPath) {
                        uploadimges(imgPath);
                    }
                });
                // Intent intent2 = new Intent(this, CropImageActivity.class);
                // intent2.putExtra("PATH", currentFilePath2);
                // startActivityForResult(intent2, CAMERA_CROP_DATA);
                break;
            case CAMERA_CROP_DATA:
                String path = mIntent.getStringExtra("PATH");
//                AbLogUtil.d(AddPhotoActivity.class, "裁剪后得到的图片的路径是 = " + path);C
                /**
                 * 原本是将 图片路径直接给数组，现在更改为做网络请求
                 */
//                refreshGrid(path);
//                uploadimges(path);
                ImageCompressUtil.compressImage(this, path, new ImageCompressUtil.ProcessImgCallBack() {
                    @Override
                    public void compressSuccess(String imgPath) {
                        uploadimges(imgPath);
                    }
                });
                // mImageShowAdapter.addItem(mImageShowAdapter.getCount() - 1,
                // path);
                break;
        }
    }

    //获得图片 url并加入集合，刷新界面
    private void refreshGrid(String path) {
        if (mArrayImgs.size() == 9) {
            mArrayImgs.remove(0);
        } else {
            mArrayImgs.set(0, DEFAULT_ADDIMAGE);
        }
        mArrayImgs.add(path);
        mAlbumAdapter.notifyDataSetChanged();
    }

    public String getFileName(String pathandname) {
        Log.e("FilePath", pathandname);

        int start = pathandname.lastIndexOf("/");
//        int end=pathandname.lastIndexOf(".");
//        if(start!=-1 && end!=-1)
//            return pathandname.substring(start+1,end);
        if (start != -1)
            return pathandname.substring(start + 1);
        else
            return null;
    }

    private void uploadimges(final String path) {

        File imageFile = new File(path);
        Log.e("URL", XrjHttpClient.getUploadPicturesUrl());
        Log.e("ID", MyApplication.userUtil.getId() + "");
        Log.e("token", MyApplication.hxt_setting_config.getString("credentials", ""));
        Log.e("FileName", getFileName(path));

        shapeLoadingDialog = new ShapeLoadingDialog(CircleCreateActivity.this);
        shapeLoadingDialog.setLoadingText("上传中,请等一小会");
        shapeLoadingDialog.show();

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getUploadPicturesUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("user_id", MyApplication.userUtil.getId() + "")
                .addFile("picture", getFileName(path), imageFile)
                .build()
                .execute(new UploadPicturesCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("UploadPictures_onError", e.toString());
                                 Toast.makeText(CircleCreateActivity.this, "图片上传失败,重新选择", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(UploadPictures response, int id) {
                                 Log.e("UploadPicturesCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(CircleCreateActivity.this, "图片上传成功", Toast.LENGTH_LONG).show();
                                         refreshGrid(path);
                                         ImagesID.add(response.getData().getId());
                                         mHandler.sendEmptyMessage(-1);
                                         break;
                                     default:
                                         Toast.makeText(CircleCreateActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         mHandler.sendEmptyMessage(-1);
                                         break;
                                 }
                             }
                         }
                );

//        OkHttpUtils
//                .post()
//                .url("http://xf.ccketang.com/api/upload/avatar")
//                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
//                .addParams("user_id", UserParameterUtil.userUtil.getId() + "")
//                .addFile("picture", getFileName(path), imageFile)
//                .build()
//                .execute(new HandleParameterCallback() {
//                             @Override
//                             public void onError(Call call, Exception e, int id) {
//                                 Log.e("测试上传头像", e.toString());
//                                 Toast.makeText(CircleCreateActivity.this, "网络异常-上传", Toast.LENGTH_LONG).show();
//                             }
//
//                             @Override
//                             public void onResponse(HandleParameter response, int id) {
//                                 Log.e("测试上传头像", response.getError_msg());
//
//                                 switch (response.getError_code()) {
//                                     case 0:
//                                         Toast.makeText(CircleCreateActivity.this, "测试上传头像成功", Toast.LENGTH_LONG).show();
//                                         break;
//                                     default:
//                                         Toast.makeText(CircleCreateActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
//                                         break;
//                                 }
//                             }
//                         }
//                );
    }

    private abstract class UploadPicturesCallback extends com.zhy.http.okhttp.callback.Callback<UploadPictures> {

        @Override
        public UploadPictures parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("UploadPictures_String", result);
            UploadPictures uploadPictures = new Gson().fromJson(result, UploadPictures.class);
            return uploadPictures;
        }
    }

//    private void uploadimges(List<String> list) {
//        RequestParams requestParams = new RequestParams();
//        int start = 1;
//        if (list.size() >= 9) {
//            start = 0;
//        }
//        for (int i = start; i < list.size(); i++) {
//            String photoPath = list.get(i);
//            if (EmptyUtil.IsNotEmpty(photoPath)) {
//                File file = new File(photoPath);
//                if (file.exists() && file != null) {
//                    try {
//                        requestParams.put("uploads_" + i, new FileInputStream(
//                                file), "" + file.getName());
//                    } catch (FileNotFoundException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//            } else {
//                // 创建一个空流
//                byte[] empt = new byte[0];
//                InputStream emptIS = new ByteArrayInputStream(empt);
//                requestParams.put("uploads", emptIS);
//            }
//        }
//
//        // requestParams.put("file_type", "jpg");
//        // requestParams.put("resize", "250X250");
//        // requestParams.put("userid", CacheUtil.userid + "");
//
//        YzyHttpClient.postRequestParams(HttpUrlConfig.reqUploadFile,
//                requestParams, new AsyncHttpResponseHandler() {
//
//                    @Override
//                    public void onFailure(Throwable error, String content) {
//                        super.onFailure(error, content);
//                        mHandler.sendEmptyMessage(3);
//                    }
//
//                    @Override
//                    public void onSuccess(String response) {
//                        super.onSuccess(response);
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            String message = obj.getString("message");
//                            if ("upload_success".equals(message)) {
//                                String content = obj.getString("content");
//                                mStrUpload = content.split(",");
//                                mHandler.sendEmptyMessage(2);
//                            } else {
//                                mHandler.sendEmptyMessage(3);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            mHandler.sendEmptyMessage(3);
//                        }
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                    }
//
//                });
//    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub  
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub  
//          mTextView.setText(s);//将输入的内容实时显示  
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub  
            editStart = mEditContent.getSelectionStart();
            editEnd = mEditContent.getSelectionEnd();
            mTextNum.setText(temp.length() + "/1000");
            if (temp.length() > 1000) {
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                mEditContent.setText(s);
                mEditContent.setSelection(tempSelection);
            }
        }
    };

}
