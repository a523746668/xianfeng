package com.qingyii.hxt.circle;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbStrUtil;
import com.andbase.library.util.AbToastUtil;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxt.R;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.httpway.TSQUpload;
import com.qingyii.hxt.pojo.Associates;

import java.io.File;

/**
 * 举报
 *
 * @author Lee
 */
public class CircleReportActivity extends AbBaseActivity implements View.OnClickListener {

//    public static final int TIMEOUT_CONNECTION = 300 * 1000;
//    public static final int TIMEOUT_SOCKET = 450 * 1000;

    private static final String DEFAULT_ADDIMAGE = "" + R.mipmap.ic_addphoto;

    private ImageView mTextLeft;
    private TextView mTextRight;
    //    private ArrayList<String> mArrayImgs;
//    private ArrayList<CircleType> mArrayType;
    private EditText mEditContent;
    private TextView mTextNum;
    // private GridView mGridView;
    // private ImageShowAdapter mImageShowAdapter;
    private DialogFragment mAlertDialog = null;
    private String[] mStrUpload = new String[]{};
    //    private RecyclerView mRecyclerView;
//    private LinearLayoutManager mLayoutManager;
    private CircleAlbumAdapter mAlbumAdapter;

    //    private TextView mTextType;
    private ImageView circle_report_confirm;
    private RelativeLayout circle_report_rl;

    //    private int mCircleType = -1;
    private boolean mAnony = false;

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

//    private AbHttpUtil mAbHttpUtil = null;

    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
    private ShapeLoadingDialog shapeLoadingDialog = null;

    //private DynamicInfo mInfoReport;
//    private Comment mComment;
    private Associates.DataBean.DocsBean aDocsBean;


    private Handler mHandler = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
            switch (msg.what) {
                case 0:
                    Toast.makeText(CircleReportActivity.this, "举报失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    // Toast.makeText(CircleReportActivity.this, "举报成功",
                    // 0).show();
                    // Intent intent = new Intent(CircleReportActivity.this,
                    // CircleMainActivity.class);
                    // setResult(RESULT_OK, intent);
                    finish();
                    break;
                case 2:
                    CircleReportActivity.this.report();
                    break;
                case 3:
                    Toast.makeText(CircleReportActivity.this, "图片上传失败，请重试", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_report1);
        if (getIntent() != null)
//            mInfoReport = (DynamicInfo) getIntent().getSerializableExtra("DynamicInfo");
//            mComment = (Comment) getIntent().getSerializableExtra("Comment");
            aDocsBean = getIntent().getParcelableExtra("DynamicInfo");
        findView();
        initData();
    }

    private void initData() {
        // 获取Http工具类
//        mAbHttpUtil = AbHttpUtil.getInstance(this);
        // 重置图片数组
//        resetArrayImgs();
//        mArrayType = new ArrayList<CircleType>();
        // mImageShowAdapter = new ImageShowAdapter(this, mArrayImgs, 150,150);
        // mGridView.setAdapter(mImageShowAdapter);
//        mAlbumAdapter = new CircleAlbumAdapter(this, mArrayImgs);
//        mRecyclerView.setAdapter(mAlbumAdapter);

        // 初始化图片保存路径
//        String photo_dir = AbFileUtil.getImageDownloadDir(this);
//        if (AbStrUtil.isEmpty(photo_dir)) {
//            AbToastUtil.showToast(CircleReportActivity.this, "存储卡不存在");
//        } else {
//            PHOTO_DIR = new File(photo_dir);
//        }
    }

//    private void resetArrayImgs() {
//        mArrayImgs = new ArrayList<String>();
//        mArrayImgs.add(DEFAULT_ADDIMAGE);
//    }

    private void findView() {
        mTextLeft = (ImageView) findViewById(R.id.circle_report_left);
        mTextRight = (TextView) findViewById(R.id.circle_report_right);
        mEditContent = (EditText) findViewById(R.id.circle_report_edit);
        mTextNum = (TextView) findViewById(R.id.circle_report_textnum);
//        mRecyclerView = (RecyclerView) findViewById(R.id.circle_report_grid);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mTextType = (TextView) findViewById(R.id.circle_report_type);
        circle_report_rl = (RelativeLayout) findViewById(R.id.circle_report_rl);
        circle_report_confirm = (ImageView) findViewById(R.id.circle_report_confirm);


        mEditContent.addTextChangedListener(mTextWatcher);

        mTextLeft.setOnClickListener(this);
        mTextRight.setOnClickListener(this);
        circle_report_rl.setOnClickListener(this);

//        mAlbumAdapter.setCallback(new CircleAlbumAdapter.Callback() {
//
//            @Override
//            public void onItemClick(int position) {
//                selectIndex = position;
//                if (mArrayImgs.get(0).equals(DEFAULT_ADDIMAGE)) {
//                    if (selectIndex == 0) {
//                        if (mArrayImgs.size() > 9) {
//                            Toast.makeText(CircleReportActivity.this,
//                                    "最多能选择9张相片", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        mAvatarView = NotifyView.inflate(CircleReportActivity.this,
//                                R.layout.choose_avatar, null);
//                        Button albumButton = (Button) mAvatarView
//                                .findViewById(R.id.choose_album);
//                        Button camButton = (Button) mAvatarView
//                                .findViewById(R.id.choose_cam);
//                        Button cancelButton = (Button) mAvatarView
//                                .findViewById(R.id.choose_cancel);
//                        albumButton.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(NotifyView v) {
//                                AbDialogUtil
//                                        .removeDialog(CircleReportActivity.this);
//                                // 从相册中去获取
//                                try {
//                                    Intent intent = new Intent(
//                                            Intent.ACTION_GET_CONTENT, null);
//                                    intent.setType("image/*");
//                                    startActivityForResult(intent,
//                                            PHOTO_PICKED_WITH_DATA);
//                                } catch (ActivityNotFoundException e) {
//                                    AbToastUtil
//                                            .showToast(
//                                                    CircleReportActivity.this,
//                                                    "没有找到照片");
//                                }
//                            }
//
//                        });
//
//                        camButton.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(NotifyView v) {
//                                AbDialogUtil
//                                        .removeDialog(CircleReportActivity.this);
//                                doPickPhotoAction();
//                            }
//
//                        });
//
//                        cancelButton.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(NotifyView v) {
//                                AbDialogUtil
//                                        .removeDialog(CircleReportActivity.this);
//                            }
//                        });
//                        AbDialogUtil.showDialog(mAvatarView, Gravity.BOTTOM);
//                    } else {
//                        ArrayList<String> array = new ArrayList<String>();
//                        array.addAll(mArrayImgs);
//                        array.remove(0);
//                        BigPhotoLocalFragment frag = BigPhotoLocalFragment
//                                .getInstance(array, selectIndex - 1);
//                        frag.show(getSupportFragmentManager(),
//                                "BigPhotoLocalFragment");
//                    }
//                } else {
//                    BigPhotoLocalFragment frag = BigPhotoLocalFragment
//                            .getInstance(mArrayImgs, position);
//                    frag.show(getSupportFragmentManager(),
//                            "BigPhotoLocalFragment");
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circle_report_rl:
                if (mAnony) {
                    mAnony = false;
                    circle_report_confirm.setVisibility(View.GONE);
//                    mTextAnony.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);
                } else {
                    mAnony = true;
                    circle_report_confirm.setVisibility(View.VISIBLE);
//                    Drawable drawble = getResources().getDrawable(R.mipmap.ic_report_yes);
//                    mTextAnony.setCompoundDrawablesWithIntrinsicBounds(null,null, drawble, null);
                }
                break;
            case R.id.circle_report_left:
                CircleReportActivity.this.finish();
                break;
            case R.id.circle_report_right:
                if (TextUtils.isEmpty(mEditContent.getText())
                    //&& mArrayImgs.size() <= 1
                        ) {
                    Toast.makeText(CircleReportActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (mCircleType == -1) {
//                    Toast.makeText(CircleReportActivity.this, "请选择类型",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
                shapeLoadingDialog = new ShapeLoadingDialog(CircleReportActivity.this);
                shapeLoadingDialog.setLoadingText("上传中,请等一小会");
                shapeLoadingDialog.show();
                TSQUpload tsqUpload = TSQUpload.getTSQUpload();
                tsqUpload.associatesReport(CircleReportActivity.this,aDocsBean,mEditContent.getText().toString(),mAnony);
                break;
//            case R.id.circle_report_type:
//                Intent intent = new Intent(CircleReportActivity.this,
//                        CircleChooseReasonActivity.class);
//                startActivityForResult(intent, START_CHOOSE_TYPE);
//                break;
            default:
                break;
        }
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
            AbToastUtil.showToast(CircleReportActivity.this, "没有可用的存储卡");
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
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(mCurrentPhotoFile));
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (Exception e) {
            AbToastUtil.showToast(CircleReportActivity.this, "未找到系统相机程序");
        }
    }

    public void report() {
        if (MyApplication.userUtil == null) {
            return;
        }
        if (TextUtils.isEmpty(mEditContent.getText())) {
            Toast.makeText(CircleReportActivity.this, "请填写举报内容",
                    Toast.LENGTH_SHORT).show();
            return;
        }


//        if (mCircleType == -1) {
//            Toast.makeText(CircleReportActivity.this, "请选择举报原因",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }
//        JSONObject jsonobject = new JSONObject();
//        try {
//            jsonobject.put("userid", CacheUtil.userid + "");
//            jsonobject.put("dynamicinfoid", mInfoReport.getDynamicid());
//            if (mComment == null) {
//                jsonobject.put("commentid", 0);
//            } else {
//                jsonobject.put("commentid", mComment.getCommentid());
//            }
//            jsonobject.put("resonid", mCircleType);
//            jsonobject.put("content", "" + mEditContent.getText().toString());
//            Gson gson = new Gson();
//            JSONArray urlsJson = new JSONArray(gson.toJson(mStrUpload,
//                    String[].class));
//            jsonobject.put("imagelist", urlsJson);
//            jsonobject.put("isanonymous", mAnony ? 1 : 0);
//            byte[] bytes = jsonobject.toString().getBytes("utf-8");
//            ByteArrayEntity entity = new ByteArrayEntity(bytes);
//            YzyHttpClient.post(this, HttpUrlConfig.reqCreateReport, entity,
//                    new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            if (statusCode == 499) {
//                                Toast.makeText(CircleReportActivity.this,
//                                        CacheUtil.logout, Toast.LENGTH_SHORT)
//                                        .show();
//                                Intent intent = new Intent(
//                                        CircleReportActivity.this,
//                                        LoginActivity.class);
//                                startActivity(intent);
//                                onFinish();
//                            } else if (statusCode == 200) {
//                                mHandler.sendEmptyMessage(1);
//                            }
//                            super.onSuccess(statusCode, content);
//                        }
//
//                        @Override
//                        public void onFailure(Throwable error, String content) {
//                            super.onFailure(error, content);
//                            mHandler.sendEmptyMessage(0);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            super.onFinish();
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        switch (requestCode) {
//            case START_CHOOSE_TYPE:
//                if (resultCode == RESULT_OK && mIntent != null) {
//                    CircleReport type = (CircleReport) mIntent
//                            .getSerializableExtra("CircleReport");
//                    //mCircleType = type.getTypeid();
//                    //this.mTextType.setText(type.getTypename());
//                }
//                break;
//            case PHOTO_PICKED_WITH_DATA:
//                Uri uri = mIntent.getData();
//                // String currentFilePath = getPath(uri);
//                String currentFilePath = GetPathFromUri4kitkat.getPath(this, uri);
//                if (!AbStrUtil.isEmpty(currentFilePath)) {
//                    refreshGrid(currentFilePath);
//                    // Intent intent1 = new Intent(this, CropImageActivity.class);
//                    // intent1.putExtra("PATH", currentFilePath);
//                    // startActivityForResult(intent1, CAMERA_CROP_DATA);
//                } else {
//                    AbToastUtil
//                            .showToast(CircleReportActivity.this, "未在存储卡中找到这个文件");
//                }
//                break;
//            case CAMERA_WITH_DATA:
//                // AbLogUtil.d(AddPhotoActivity.class, "将要进行裁剪的图片的路径是 = " +
//                // mCurrentPhotoFile.getPath());
//                String currentFilePath2 = mCurrentPhotoFile.getPath();
//                refreshGrid(currentFilePath2);
//                // Intent intent2 = new Intent(this, CropImageActivity.class);
//                // intent2.putExtra("PATH", currentFilePath2);
//                // startActivityForResult(intent2, CAMERA_CROP_DATA);
//                break;
//            case CAMERA_CROP_DATA:
//                String path = mIntent.getStringExtra("PATH");
//                AbLogUtil.d(AddPhotoActivity.class, "裁剪后得到的图片的路径是 = " + path);
//                refreshGrid(path);
//                // mImageShowAdapter.addItem(mImageShowAdapter.getCount() - 1,
//                // path);
//                break;
//        }
    }

//    private void refreshGrid(String path) {
//        if (mArrayImgs.size() == 9) {
//            mArrayImgs.remove(0);
//        } else {
//            mArrayImgs.set(0, DEFAULT_ADDIMAGE);
//        }
//        mArrayImgs.add(path);
//        mAlbumAdapter.notifyDataSetChanged();
//    }

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
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            // mTextView.setText(s);//将输入的内容实时显示
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
