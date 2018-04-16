package com.qingyii.hxtz;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.util.AbFileUtil;
import com.andbase.library.util.AbStrUtil;
import com.andbase.library.util.AbToastUtil;
import com.andbase.library.view.wheel.AbStringWheelAdapter;
import com.andbase.library.view.wheel.AbWheelView;
import com.baoyz.actionsheet.ActionSheet;
import com.mingle.widget.ShapeLoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.circle.GetPathFromUri4kitkat;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.httpway.HandleParameterCallback;
import com.qingyii.hxtz.pojo.Company;
import com.qingyii.hxtz.pojo.Department;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.pojo.Job;
import com.qingyii.hxtz.pojo.User;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;
import com.qingyii.hxtz.util.DateUtils;
import com.qingyii.hxtz.util.ImageCompressUtil;
import com.qingyii.hxtz.view.RoundedImageView;
import com.qingyii.hxtz.wheelpicker.picker.DatePicker;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;

public class myActivity1 extends AbBaseActivity {
    private int mMyear;
    private int mMouth;
    private int mDay;

    /* 用来标识请求照相功能的activity */
    private static final int CAMERA_WITH_DATA = 3023;
    /* 用来标识请求gallery的activity */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;
    /* 用来标识请求裁剪图片后的activity */
    private static final int CAMERA_CROP_DATA = 3022;
    private View mAvatarView = null;
    /* 拍照的照片存储位置 */
    private File PHOTO_DIR = null;
    // 照相机拍照得到的图片
    private File mCurrentPhotoFile;
    private String mFileName;
    private User user = new User();

    private ShapeLoadingDialog shapeLoadingDialog = null;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    /**
     * 选择裁剪后的图片
     */
    private String photoPath = "";

    private static final int DATE_DIALOG_ID = 1;
    public static final int SHOW_DATAPICK = 0;
    private LinearLayout back_btn;
    private TextView tv_my_nicheng;// 昵称
    private TextView tv_my_name;// 姓名
    private TextView tv_my_phone;// 电话
    private TextView tv_my_sex;// 性别
    private TextView tv_my_birthday;// 生日
    private TextView tv_my_danwei;// 单位
    private TextView tv_my_bumen;// 部门
    private TextView tv_my_zhiwu;// 职务
    //    private TextView tv_update;// 修改
//    private Button bt_sure;
    public RelativeLayout rl_rizhi;
    private RelativeLayout rl_chushengdate;// 出生日期的RativeLayout
    private RelativeLayout xuznzexingbie;// 选择性别
    private RelativeLayout rl_danwei;// 选择单位
    private RelativeLayout rl_bumen;// 部门
    private RelativeLayout rl_zhiwu;// 职务
    private RelativeLayout rl_touxiang;// 头像
    private Handler sHandler;

    private View mTimeView = null;

    private View mTimeView_sex = null;
    private TextView timeTextView1 = null;
    /**
     * 性别显示数据源
     */
    ArrayList<String> sexlist = new ArrayList<String>();
    /**
     * 单位显示数据源
     */
    ArrayList<String> wheedanweilist = new ArrayList<String>();
    /**
     * 部门显示数据源
     */
    ArrayList<String> wheedepartmentlist = new ArrayList<String>();
    /**
     * 工作显示数据源
     */
    ArrayList<String> wheejoblist = new ArrayList<String>();
    /**
     * 单位数据源
     */
    private ArrayList<Company> Companylists = new ArrayList<Company>();
    /**
     * 工作显示数据源
     */
    private ArrayList<Job> Joblists = new ArrayList<Job>();
    /**
     * 部门数据源
     */
    private ArrayList<Department> departmentlists = new ArrayList<Department>();
    /**
     * 单位选中索引
     */
    private int selectCompayIndex = -1;
    /**
     * 部门选中索引
     */
    private int selectdepartmentIndex = -1;
    /**
     * 工作选中索引
     */
    private int selectjobIndex = -1;
    /**
     * 性别选择索引
     */
    private int selectSexIndex = -1;
    private RoundedImageView iv_touxiang;

    //年月日选择器
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my1);
        initUI();
        initData();

    }

    @Override
    protected void onResume() {

        super.onResume();
        AbDialogUtil.removeDialog(myActivity1.this);
    }

    private void initData() {
        sexlist.add("男");
        sexlist.add("女");
        //初始化图片保存路径
        String photo_dir = AbFileUtil.getImageDownloadDir(this);
        if (AbStrUtil.isEmpty(photo_dir)) {
            AbToastUtil.showToast(myActivity1.this, "存储卡不存在");
        } else {
            PHOTO_DIR = new File(photo_dir);
        }
//      initqueryAlliDate();

    }

//    private void getDate() {
//
//        JSONObject jsonObject = new JSONObject();
//        try {
////            jsonObject.put("name", tv_my_name.getText().toString());
//            jsonObject.put("username", tv_my_nicheng.getText().toString());
////            if ("男".equals(tv_my_sex.getText().toString())) {
////                jsonObject.put("sex", 1);
////            } else if ("女".equals(tv_my_sex.getText().toString())) {
////                jsonObject.put("sex", 2);
////            }
//            jsonObject.put("birthday", tv_my_birthday.getText().toString());
////            if (selectCompayIndex >= 0) {
////                jsonObject.put("companyid", Companylists.get(selectCompayIndex).getCompanyid());
////            }
////            if (selectdepartmentIndex >= 0) {
////                jsonObject.put("depid", departmentlists.get(selectdepartmentIndex).getDepid());
////            }
////            if (selectjobIndex >= 0) {
////                jsonObject.put("jobid", Joblists.get(selectjobIndex).getJobid());
////            }
//            jsonObject.put("userid", CacheUtil.userid);
//            jsonObject.put("phone", CacheUtil.user.getPhone());
//        } catch (JSONException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//
//        byte[] bytes;
//        try {
//            bytes = jsonObject.toString().getBytes("utf-8");
//            ByteArrayEntity entity = new ByteArrayEntity(bytes);
//            YzyHttpClient.post(myActivity1.this, HttpUrlConfig.changepassWord,
//                    entity, new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            super.onSuccess(statusCode, content);
//                            try {
//                                JSONObject obj = new JSONObject(content);
//                                if ("update_success".equals(obj.getString("message"))) {
//                                    Gson gson = new Gson();
//                                    CacheUtil.user = gson.fromJson(obj.getString("user"), User.class);
//                                    user.setXiuflag(0);
//                                    tv_my_nicheng.setEnabled(false);
//                                    tv_my_name.setEnabled(false);
////                                    bt_sure.setVisibility(NotifyListView.GONE);
//                                    Toast.makeText(myActivity1.this, "恭喜你修改信息成功！", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(myActivity1.this, "对不起您修改信息失败，请重新提交！", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Throwable error, String content) {
//
//                            super.onFailure(error, content);
//                            System.out.println(content);
//                        }
//
//                        @Override
//                        public void onFinish() {
//
//                            super.onFinish();
//                            System.out.println("finish");
//                        }
//                    });
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 刷新UI数据方法
     */
    private void F5UI() {
//        if (CacheUtil.user != null) {
//            tv_my_nicheng.setText(CacheUtil.user.getUsername());
//            tv_my_name.setText(CacheUtil.user.getName());
//            tv_my_phone.setText(CacheUtil.user.getPhone());
        if (MyApplication.userUtil != null) {
            Log.e("刷新界面进入：", MyApplication.userUtil.getAvatar());
            ImageLoader.getInstance().displayImage(MyApplication.userUtil.getAvatar(),
                    iv_touxiang, MyApplication.options, animateFirstListener);
            tv_my_nicheng.setText(MyApplication.userUtil.getNickname());
            tv_my_name.setText(MyApplication.userUtil.getTruename());
            tv_my_phone.setText(MyApplication.userUtil.getPhone());
//            if ("1".equals(CacheUtil.user.getSex()))
//                tv_my_sex.setText("男");
//             else
//                tv_my_sex.setText("女");

            if ("male".equals(MyApplication.userUtil.getGender()))
                tv_my_sex.setText("男");
            else if ("female".equals(MyApplication.userUtil.getGender()))
                tv_my_sex.setText("女");
            else if ("secret".equals(MyApplication.userUtil.getGender()))
                tv_my_sex.setText("保密");

//            tv_my_birthday.setText(CacheUtil.user.getBirthdayStr());
//            tv_my_danwei.setText(CacheUtil.user.getCompanyid());
//            tv_my_bumen.setText(CacheUtil.user.getDepid());
//            tv_my_zhiwu.setText(CacheUtil.user.getJobid());
            tv_my_birthday.setText(MyApplication.userUtil.getBirthday());
            tv_my_danwei.setText(MyApplication.userUtil.getCompany().getName());
            tv_my_bumen.setText(MyApplication.userUtil.getDepartment().getName());
            tv_my_zhiwu.setText(MyApplication.userUtil.getJobname());

//            if (EmptyUtil.IsNotEmpty(CacheUtil.user.getPicaddress())) {
//                ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + CacheUtil.user.getPicaddress(), iv_touxiang);
//            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                return true;
//        }
        AbDialogUtil.removeDialog(myActivity1.this);
        finish();

        return super.onKeyDown(keyCode, event);
    }

    private void initUI() {
        iv_touxiang = (RoundedImageView) findViewById(R.id.iv_touxiang);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
//        bt_sure = (Button) findViewById(R.id.bt_sure);

        tv_my_nicheng = (TextView) findViewById(R.id.tv_my_nicheng);
        tv_my_name = (TextView) findViewById(R.id.tv_my_name);
        tv_my_phone = (TextView) findViewById(R.id.tv_my_phone);
        tv_my_sex = (TextView) findViewById(R.id.tv_my_sex);
        tv_my_birthday = (TextView) findViewById(R.id.tv_my_birthday);
        tv_my_danwei = (TextView) findViewById(R.id.tv_my_danwei);
        tv_my_bumen = (TextView) findViewById(R.id.tv_my_bumen);
        tv_my_zhiwu = (TextView) findViewById(R.id.tv_my_zhiwu);
//        tv_update = (TextView) findViewById(R.id.tv_update);

        rl_rizhi = (RelativeLayout) findViewById(R.id.rl_rizhi);
        rl_chushengdate = (RelativeLayout) findViewById(R.id.rl_chushengdate);
        rl_bumen = (RelativeLayout) findViewById(R.id.rl_bumen);
        rl_zhiwu = (RelativeLayout) findViewById(R.id.rl_zhiwu);
        xuznzexingbie = (RelativeLayout) findViewById(R.id.xuznzexingbie);
        rl_touxiang = (RelativeLayout) findViewById(R.id.rl_touxiang);
        rl_danwei = (RelativeLayout) findViewById(R.id.rl_danwei);

//        if (EmptyUtil.IsNotEmpty(CacheUtil.user.getCompanyname())) {
//            tv_my_danwei.setText(CacheUtil.user.getCompanyname());
//        }
//        if (EmptyUtil.IsNotEmpty(CacheUtil.user.getDepname())) {
//            tv_my_bumen.setText(CacheUtil.user.getDepname());
//        }
//        if (EmptyUtil.IsNotEmpty(CacheUtil.user.getJobname())) {
//            tv_my_zhiwu.setText(CacheUtil.user.getJobname());
//        }
//        if (EmptyUtil.IsNotEmpty(CacheUtil.user.getPicaddress())) {
//            ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + CacheUtil.user.getPicaddress(), iv_touxiang);
//        }


        // 头像


        rl_touxiang.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {

                ActionSheet.createBuilder(myActivity1.this, getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles("本地相册", "相机拍照")
                        .setCancelableOnTouchOutside(true)
                        .setListener(new ActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                            }

                            @Override
                            public void onOtherButtonClick(ActionSheet actionSheet, int index) {

                                switch (index) {

                                    case 0:

                                        try {
                                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                                            intent.setType("image/*");
                                            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                                        } catch (ActivityNotFoundException e) {
                                            AbToastUtil.showToast(myActivity1.this, "没有找到照片");
                                        }

                                        break;
                                    case 1:
                                        doPickPhotoAction();
                                        break;
                                    default:

                                        break;

                                }

                            }
                        }).show();


//
//                mAvatarView = NotifyListView.inflate(myActivity1.this, R.layout.choose_avatar, null);
//                Button albumButton = (Button) mAvatarView.findViewById(R.id.choose_album);
//                Button camButton = (Button) mAvatarView.findViewById(R.id.choose_cam);
//                Button cancelButton = (Button) mAvatarView.findViewById(R.id.choose_cancel);
//                albumButton.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(NotifyListView v) {
//                        AbDialogUtil.removeDialog(myActivity1.this);
//                        // 从相册中去获取
//                        try {
//                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//                            intent.setType("image/*");
//                            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
//                        } catch (ActivityNotFoundException e) {
//                            AbToastUtil.showToast(myActivity1.this, "没有找到照片");
//                        }
//                    }
//
//                });
//
//                camButton.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(NotifyListView v) {
//                        AbDialogUtil.removeDialog(myActivity1.this);
//                        doPickPhotoAction();
//                    }
//
//                });
//
//                cancelButton.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(NotifyListView v) {
//                        AbDialogUtil.removeDialog(myActivity1.this);
//                    }
//
//                });
//
//
//
//                AbDialogUtil.showDialog(mAvatarView, Gravity.BOTTOM);

            }
        });

        // 出生日期
        rl_chushengdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePicker picker = new DatePicker(myActivity1.this, DatePicker.YEAR_MONTH_DAY);
                final int YEAR = calendar.get(Calendar.YEAR), MONTH = calendar.get(Calendar.MONTH) + 1, DAY = calendar.get(Calendar.DAY_OF_MONTH);
                picker.setRangeStart(1970, 1, 1);
                picker.setRangeEnd(YEAR, MONTH, DAY);
                picker.setSelectedItem(YEAR, MONTH, DAY);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        changeBirthday(year + "-" + month + "-" + day);
                    }
                });
                picker.show();
////                if (user.getXiuflag() == 1) {
//                mTimeView = NotifyListView.inflate(myActivity1.this, R.layout.choose_three, null);
////                initWheeDate(mTimeView, tv_my_birthday);
//                AbDialogUtil.showDialog(mTimeView, Gravity.BOTTOM);
////                }
            }
        });

//        xuznzexingbie.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(NotifyListView view) {
//                OptionPicker picker = new OptionPicker(myActivity1.this, new String[]{"男", "女", "保密"});
//                picker.setOffset(1);
//                picker.setSelectedIndex(1);
//                picker.setTextSize(16);
//                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
//                    @Override
//                    public void onOptionPicked(int position, String option) {
//                        changeSex(option);
//                    }
//                });
//                picker.show();
//            }
//        });

        /**
         * 单位
         */
        AbStringWheelAdapter dwabw = new AbStringWheelAdapter(sexlist);
        rl_rizhi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(myActivity1.this, NameAlterActivity.class);
                startActivity(it);
            }
        });

//		rl_danwei.setOnClickListener(new OnClickListener() {
//			//
//			@Override
//			public void onClick(NotifyListView v) {
//				mTimeView_sex = mInflater.inflate(R.layout.choose_one, null);
//				initWheelData1(mTimeView_sex, tv_my_danwei, wheedanweilist,2);
//				AbDialogUtil.showDialog(mTimeView_sex, Gravity.BOTTOM);
//			}
//		});
//		/**
//		 * 部门
//		 */
//		rl_bumen.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(NotifyListView v) {
//				mTimeView_sex = mInflater.inflate(R.layout.choose_one, null);
//				initWheelData1(mTimeView_sex, tv_my_bumen, wheedepartmentlist,3);
//				AbDialogUtil.showDialog(mTimeView_sex, Gravity.BOTTOM);
//			}
//		});
//
//		/**
//		 * 职务
//		 */
//		rl_zhiwu.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(NotifyListView v) {
//				mTimeView_sex = mInflater.inflate(R.layout.choose_one, null);
//				initWheelData1(mTimeView_sex, tv_my_zhiwu, wheejoblist,4);
//				AbDialogUtil.showDialog(mTimeView_sex, Gravity.BOTTOM);
//			}
//		});

//        bt_sure.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(NotifyListView v) {
//                getDate();
//            }
//        });

//        tv_update.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(NotifyListView v) {
//                // 设置EditText可编辑
//                if (user.getXiuflag() == 0) {
//                    user.setXiuflag(1);
//                    tv_my_nicheng.setEnabled(true);
////                    tv_my_name.setEnabled(true);
////                    bt_sure.setVisibility(NotifyListView.VISIBLE);
//                } else if (user.getXiuflag() == 1) {
//                    user.setXiuflag(0);
//                    tv_my_nicheng.setEnabled(false);
//                    tv_my_name.setEnabled(false);
////                    bt_sure.setVisibility(NotifyListView.GONE);
//                }
//            }
//        });

//		// 选择性别
//		xuznzexingbie.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(NotifyListView v) {
//				mTimeView_sex = mInflater.inflate(R.layout.choose_one, null);
//				initWheelData1(mTimeView_sex, tv_my_sex, sexlist,1);
//				AbDialogUtil.showDialog(mTimeView_sex, Gravity.BOTTOM);
//			}
//		});

        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        //刷新UI数据
        F5UI();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
    }

    /**
     * 初始化wheelview弹出UI
     *
     * @param mDataView1 弹出布局UI
     * @param textView   赋值对像
     * @param strings    数据源
     * @param flag       标志：1性别，2单位，3部门，4工作
     */
    public void initWheelData1(View mDataView1, final TextView textView,
                               ArrayList<String> strings, final int flag) {
        final AbWheelView mWheelView1 = (AbWheelView) mDataView1
                .findViewById(R.id.wheelView1);
        mWheelView1.setAdapter(new AbStringWheelAdapter(strings));
        // 可循环滚动
        mWheelView1.setCyclic(false);
        // 添加文字
        // mWheelView1.setLabel(getResources().getString(R.string.data1_unit));

        mWheelView1.setCenterSelectDrawable(this.getResources().getDrawable(
                R.drawable.wheel_select));

        Button okBtn = (Button) mDataView1.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) mDataView1.findViewById(R.id.cancelBtn);
        okBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(v.getContext());
                int index = mWheelView1.getCurrentItem();
                switch (flag) {
                    case 1:
                        //性别
                        selectSexIndex = index;
                        break;

                    case 2:
                        //单位
                        selectCompayIndex = index;
                        break;
                    case 3:
                        //部门
                        selectdepartmentIndex = index;
                        break;
                    case 4:
                        //工作
                        selectjobIndex = index;
                        break;
                }
                String val = mWheelView1.getAdapter().getItem(index);
                textView.setText(val);
            }

        });

        cancelBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(v.getContext());
            }

        });
    }

    protected void initWheeDate(View mDateView, TextView mText) {
        // 年月日时间选择器
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DATE);
//        final AbWheelView mWheelViewY = (AbWheelView) mDateView
//                .findViewById(R.id.wheelView1);
//        final AbWheelView mWheelViewM = (AbWheelView) mDateView
//                .findViewById(R.id.wheelView2);
//        final AbWheelView mWheelViewD = (AbWheelView) mDateView
//                .findViewById(R.id.wheelView3);
//        Button okBtn = (Button) mDateView.findViewById(R.id.okBtn);
//        Button cancelBtn = (Button) mDateView.findViewById(R.id.cancelBtn);
//        mWheelViewY.setCenterSelectDrawable(this.getResources().getDrawable(
//                R.drawable.wheel_select));
//        mWheelViewM.setCenterSelectDrawable(this.getResources().getDrawable(
//                R.drawable.wheel_select));
//        mWheelViewD.setCenterSelectDrawable(this.getResources().getDrawable(
//                R.drawable.wheel_select));
////        AbWheelUtil.initWheelDatePicker(myActivity1.this, mText, mWheelViewY,
////                mWheelViewM, mWheelViewD, okBtn, cancelBtn, year, month, day,
////                year, 120, false);
//        /**
//         * initWheelDatePicker 失效 修改为 initWheelPickerMDHM
//         */
////        AbWheelUtil.initWheelDatePicker(this, mText, mWheelViewY, mWheelViewM,
////                mWheelViewD, okBtn, cancelBtn, year, month, day, 1940, year - 1940 + 10,
////                false);
//        AbWheelUtil.initWheelPickerMDHM(this, mText, mWheelViewY, mWheelViewM,
//                mWheelViewD, okBtn, cancelBtn, year, month, day, 1940, year - 1940 + 10,
//                false);
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
            AbToastUtil.showToast(myActivity1.this, "没有可用的存储卡");
        }
    }

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            mFileName = System.currentTimeMillis() + ".jpg";
            mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
            Log.i("tmdphoto",PHOTO_DIR.getAbsolutePath());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                uri= FileProvider.getUriForFile(this, "com.qingyii.hxt.fileprovider", mCurrentPhotoFile);

            }   else {
                uri=Uri.fromFile(mCurrentPhotoFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    uri);
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (Exception e) {
            AbToastUtil.showToast(myActivity1.this, "未找到系统相机程序");
        }
    }

    /**
     * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况, 他们启动时是这样的startActivityForResult
     */
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent mIntent) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA:
                Uri uri = mIntent.getData();
                // String currentFilePath = getPath(uri);
                String currentFilePath = GetPathFromUri4kitkat.getPath(this, uri);
                if (!AbStrUtil.isEmpty(currentFilePath)) {
                    /**
                     * 原本是将 图片路径直接给数组，现在更改为做网络请求
                     */
//                    refreshGrid(currentFilePath);
//                    changeHead(currentFilePath);
                    ImageCompressUtil.compressImage(this, currentFilePath, new ImageCompressUtil.ProcessImgCallBack() {
                        @Override
                        public void compressSuccess(String imgPath) {
                            changeHead(imgPath);
                        }
                    });
                    // Intent intent1 = new Intent(this, CropImageActivity.class);
                    // intent1.putExtra("PATH", currentFilePath);
                    // startActivityForResult(intent1, CAMERA_CROP_DATA);
                } else {
                    AbToastUtil
                            .showToast(myActivity1.this, "未在存储卡中找到这个文件");
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
//                changeHead(currentFilePath2);
                ImageCompressUtil.compressImage(this, currentFilePath2, new ImageCompressUtil.ProcessImgCallBack() {
                    @Override
                    public void compressSuccess(String imgPath) {
                        changeHead(imgPath);
                    }
                });
                // Intent intent2 = new Intent(this, CropImageActivity.class);
                // intent2.putExtra("PATH", currentFilePath2);
                // startActivityForResult(intent2, CAMERA_CROP_DATA);
                break;
            case CAMERA_CROP_DATA:
                String path = mIntent.getStringExtra("PATH");
//                AbLogUtil.d(AddPhotoActivity.class, "裁剪后得到的图片的路径是 = " + path);
                /**
                 * 原本是将 图片路径直接给数组，现在更改为做网络请求
                 */
//                refreshGrid(path);
//                changeHead(path);
                ImageCompressUtil.compressImage(this, path, new ImageCompressUtil.ProcessImgCallBack() {
                    @Override
                    public void compressSuccess(String imgPath) {
                        changeHead(imgPath);
                    }
                });
                // mImageShowAdapter.addItem(mImageShowAdapter.getCount() - 1,
                // path);
                break;
//            case PHOTO_PICKED_WITH_DATA:
//                Uri uri = mIntent.getData();
////			String currentFilePath = getPath(uri);
//                String currentFilePath = GetPathFromUri4kitkat.getPath(this, uri);
//                if (!AbStrUtil.isEmpty(currentFilePath)) {
//                    Intent intent1 = new Intent(this, CropImageActivity.class);
//                    intent1.putExtra("PATH", currentFilePath);
//                    startActivityForResult(intent1, CAMERA_CROP_DATA);
//                } else {
//                    AbToastUtil.showToast(myActivity1.this, "未在存储卡中找到这个文件");
//                }
//                break;
//            case CAMERA_WITH_DATA:
//                AbLogUtil.d(myActivity1.class, "将要进行裁剪的图片的路径是 = "
//                        + mCurrentPhotoFile.getPath());
//                String currentFilePath2 = mCurrentPhotoFile.getPath();
//                Intent intent2 = new Intent(this, CropImageActivity.class);
//                intent2.putExtra("PATH", currentFilePath2);
//                startActivityForResult(intent2, CAMERA_CROP_DATA);
//                break;
//            case CAMERA_CROP_DATA:
//                photoPath = mIntent.getStringExtra("PATH");
//                if (EmptyUtil.IsNotEmpty(photoPath)) {
//                    // 选择图片返回地址立即修改头像信息
//                    changeHead(photoPath);
//                }
//                AbLogUtil.d(myActivity1.class, "裁剪后得到的图片的路径是 = " + photoPath);
//                break;
        }
    }

    /**
     * 从相册得到的url转换为SD卡中图片路径
     */
    public String getPath(Uri uri) {
        if (AbStrUtil.isEmpty(uri.getAuthority())) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
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

    /**
     * 修改头像接口
     */
    private void changeHead(final String path) {

        File imageFile = new File(path);
        Log.e("URL", XrjHttpClient.getUploadPicturesUrl());
        Log.e("ID", MyApplication.userUtil.getId() + "");
        Log.e("token", MyApplication.hxt_setting_config.getString("credentials", ""));
        Log.e("FileName", getFileName(path));

        shapeLoadingDialog = new ShapeLoadingDialog(myActivity1.this);
        shapeLoadingDialog.setLoadingText("上传中,请等一小会");
        shapeLoadingDialog.show();

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getAvatarUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("user_id", MyApplication.userUtil.getId() + "")
                .addFile("picture", getFileName(path), imageFile)
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("上传头像", e.toString());
                                 Toast.makeText(myActivity1.this, "图片上传失败,重新选择", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("上传头像", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(myActivity1.this, "上传头像成功", Toast.LENGTH_LONG).show();
                                         MyApplication.userUtil.setAvatar(response.getData() + "?r=" + DateUtils.getDateLong());

                                         Log.e("返回的头像：", response.getData());
                                         Log.e("返回的头像：", MyApplication.userUtil.getAvatar());

                                         F5UI();
                                         if (shapeLoadingDialog != null) {
                                             shapeLoadingDialog.dismiss();
                                         }
                                         break;
                                     default:
                                         Toast.makeText(myActivity1.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         if (shapeLoadingDialog != null) {
                                             shapeLoadingDialog.dismiss();
                                         }
                                         break;
                                 }
                             }
                         }
                );


//        RequestParams requestParams = new RequestParams();
//
//        if (EmptyUtil.IsNotEmpty(photoPath)) {
//            File file = new File(photoPath);
//            if (file.exists() && file != null) {
//                try {
//                    requestParams.put("uploads", file);
//                    requestParams.put("file_type", "jpg");
//                    requestParams.put("resize", "250X250");
//
//                } catch (FileNotFoundException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//            }
//        } else {
//            // 创建一个空流
//            byte[] empt = new byte[0];
//            InputStream emptIS = new ByteArrayInputStream(empt);
//            requestParams.put("uploads", emptIS);
//
//        }
//        requestParams.put("userid", CacheUtil.userid + "");
//
//        YzyHttpClient.postRequestParams(HttpUrlConfig.uploadUserImage,
//                requestParams, new AsyncHttpResponseHandler() {
//
//                    @Override
//                    public void onFailure(Throwable error, String content) {
//                        super.onFailure(error, content);
//                        // Toast.makeText(RegiestActivity.this,
//                        // "图片上传失败："+error.toString()+content,
//                        // Toast.LENGTH_SHORT).show();
//                        // handler.sendEmptyMessage(0);
//                        Toast.makeText(myActivity1.this, "头像设置失败",
//                                Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess(String response) {
//                        super.onSuccess(response);
//
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            String message = obj.getString("message");
//                            if ("success".equals(message)) {
//                                JSONObject userObj = obj.getJSONObject("user");
//                                if (EmptyUtil.IsNotEmpty(userObj.getString("picaddress"))) {
//                                    CacheUtil.user.setPicaddress(userObj.getString("picaddress"));
//                                    ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + userObj.getString("picaddress"), iv_touxiang);
//                                }
//                                user.setXiuflag(0);
//                                tv_my_nicheng.setEnabled(false);
//                                tv_my_name.setEnabled(false);
////                                bt_sure.setVisibility(NotifyListView.GONE);
//                                Toast.makeText(getBaseContext(), "头像设置成功！",
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getBaseContext(), "头像设置失败，请重试！",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                            Toast.makeText(getBaseContext(), "注册异常，服务器返回数据异常!",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                    }
//                });
    }

    /**
     * 修改生日接口
     */
    private void changeBirthday(final String birthday) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getUserEditUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("birthday", birthday)
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("修改生日", e.toString());
                                 Toast.makeText(myActivity1.this, "网络异常-修改生日", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("修改生日", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(myActivity1.this, "修改生日成功", Toast.LENGTH_LONG).show();
                                         MyApplication.userUtil.setBirthday(birthday);
                                         F5UI();
                                         break;
                                     default:
                                         Toast.makeText(myActivity1.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 修改生日接口
     */
    String sxeMark = "";
    private void changeSex(String sxe) {

        if (sxe.equals("男"))
            sxeMark = "male";
        if (sxe.equals("女"))
            sxeMark = "female";
        if (sxe.equals("保密"))
            sxeMark = "secret";

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getUserEditUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("gender", sxeMark)
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("修改生日", e.toString());
                                 Toast.makeText(myActivity1.this, "修改失败", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("修改生日", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(myActivity1.this, "修改生日成功", Toast.LENGTH_LONG).show();
                                         MyApplication.userUtil.setGender(sxeMark);
                                         F5UI();
                                         break;
                                     default:
                                         Toast.makeText(myActivity1.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 获取单位职务部门的数据
     */
//    protected void initqueryAlliDate() {
//        JSONObject object = new JSONObject();
//        byte[] bytes;
//        try {
//            bytes = object.toString().getBytes("utf-8");
//            ByteArrayEntity entity = new ByteArrayEntity(bytes);
//
//            YzyHttpClient.post(myActivity1.this, HttpUrlConfig.queryAllgsibmzw,
//                    entity, new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            if (statusCode == 499) {
//                                Toast.makeText(myActivity1.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(myActivity1.this, LoginActivity.class);
//                                startActivity(intent);
//                                onFinish();
//                            } else if (statusCode == 200) {
//                                Gson gson = new Gson();
//                                try {
//                                    JSONObject object = new JSONObject(content);
//                                    JSONArray companyArray = object
//                                            .getJSONArray("companyList");
//                                    JSONArray departmentList = object
//                                            .getJSONArray("departmentList");
//                                    JSONArray jobArray = object
//                                            .getJSONArray("jobList");
//
//                                    for (int i = 0; i < companyArray.length(); i++) {
//                                        Company copanyinfo = gson.fromJson(
//                                                companyArray.getString(i),
//                                                Company.class);
//                                        Companylists.add(copanyinfo);
//                                        if ((copanyinfo.getCompanyid() + "").equals(CacheUtil.user.getCompanyid())) {
//                                            tv_my_danwei.setText(copanyinfo.getCompanyname());
//                                        }
//                                        wheedanweilist.add(copanyinfo
//                                                .getCompanyname());
//
//                                    }
//
//                                    for (int i = 0; i < departmentList.length(); i++) {
//                                        Department departmentinfo = gson
//                                                .fromJson(departmentList
//                                                                .getString(i),
//                                                        Department.class);
//                                        departmentlists.add(departmentinfo);
//                                        if (departmentinfo.getDepid().equals(
//                                                CacheUtil.user.getDepid())) {
//                                            tv_my_bumen.setText(departmentinfo
//                                                    .getDepname());
//                                        }
//
//                                        wheedepartmentlist.add(departmentinfo
//                                                .getDepname());
//                                    }
//
//                                    for (int i = 0; i < jobArray.length(); i++) {
//                                        Job jobinfo = gson.fromJson(
//                                                jobArray.getString(i),
//                                                Job.class);
//                                        if (jobinfo.getJobid().equals(
//                                                CacheUtil.user.getJobid())) {
//                                            tv_my_zhiwu.setText(jobinfo.getJobname());
//                                        }
//                                        Joblists.add(jobinfo);
//                                        wheejoblist.add(jobinfo.getJobname());
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                            super.onSuccess(statusCode, content);
//                        }
//
//                        @Override
//                        public void onFailure(Throwable error, String content) {
////							System.out.println(content);
//                            super.onFailure(error, content);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            // TODO Auto-generated method stub
//                            super.onFinish();
//                        }
//
//                    });
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
}
