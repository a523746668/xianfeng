package com.qingyii.hxt.jpush;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.google.gson.Gson;
import com.qingyii.hxt.QrCodeSignInActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.customview.MyGridView;
import com.qingyii.hxt.home.mvp.ui.HomeActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.TrainList;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.qingyii.hxt.QrCodeSignInActivity.REQUEST_CODE;

public class TrainDetailsJPush extends AbBaseActivity {
    private TextView tltle;
    private Intent intent;
    private TrainList.DataBean tDataBean;
    private int trainID;

    private ListView train_details_listview;
    private View litHeader;

    private MyGridView gridView;
    private List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
    private SimpleAdapter sim_adapter;
    private int[] gridViewImage = {R.mipmap.train_details_inform, R.mipmap.train_details_report, R.mipmap.train_details_schedule,
            R.mipmap.train_details_remind, R.mipmap.train_details_sign, R.mipmap.train_details_work,
            R.mipmap.train_details_exam, R.mipmap.train_details_download, R.mipmap.train_details_student,
            R.mipmap.train_details_guide, R.mipmap.train_details_weather};
    private String[] gridViewText =
            {"培训通知", "培训报到", "培训日程",
                    "上课提醒", "课堂签到", "出勤排行",
                    "培训考试", "资料下载", "培训学员",
                    "培训须知", "天气预报"};

    private RelativeLayout train_details_site_in;
//    private RelativeLayout train_details_weather_in;

    private TextView train_details_site;
    private TextView train_details_time;
    private TextView train_details_lecturer;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_details);

        litHeader = View.inflate(this, R.layout.train_details_listview_header, null);

        intent = getIntent();
        trainID = intent.getIntExtra("ID", 0);
        trainList(trainID);
        //tltle设置
        tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("培训");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gridView = (MyGridView) litHeader.findViewById(R.id.train_details_gridview);
        train_details_listview = (ListView) findViewById(R.id.train_details_listview);
        train_details_site_in = (RelativeLayout) litHeader.findViewById(R.id.train_details_site_in);
//        train_details_weather_in = (RelativeLayout) litHeader.findViewById(R.id.train_details_weather_in);


        train_details_site = (TextView) litHeader.findViewById(R.id.train_details_site);
        train_details_time = (TextView) litHeader.findViewById(R.id.train_details_time);
        train_details_lecturer = (TextView) litHeader.findViewById(R.id.train_details_lecturer);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startActivity(new Intent(this, HomeActivity.class));
    }

    /**
     * UI界面加载
     */
    private void loadUI() {

        for (int i = 0; i < gridViewImage.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", gridViewImage[i]);
            map.put("text", gridViewText[i]);
            data_list.add(map);
        }
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.train_details_gridview_item_image, R.id.train_details_gridview_item_text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.train_details_gridview_item, from, to);

        //配置适配器
        gridView.setAdapter(sim_adapter);

        train_details_listview.addHeaderView(litHeader);
        train_details_listview.setAdapter(null);

    }

    private void loadDatas() {
//        train_details_site.setText("地址 : " + tDataBean.getAddress());
//        train_details_time.setText("培训时间 : " + DateUtils.getDateToString(tDataBean.getBegintime()) +
//                "至" + DateUtils.getDateToString(tDataBean.getEndtime()));
//        train_details_lecturer.setText("主办方 : " + tDataBean.getOrganizer());
    }

    private void onClick() {
        train_details_site_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intent = new Intent(TrainDetailsJPush.this, TrainSiteActivity.class);
//                intent.putExtra("training", tDataBean);
//                intent.putExtra("tltle", tDataBean.getAddress());
//                startActivity(intent);
            }
        });

//        train_details_weather_in.setOnClickListener(new NotifyView.OnClickListener() {
//            @Override
//            public void onClick(NotifyView v) {
//                intent = new Intent(TrainDetailsActivity.this, TrainWeatherActivity.class);
//                intent.putExtra("training", tDataBean);
//                intent.putExtra("tltle", "天气预报");
//                startActivity(intent);
//            }
//        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*switch (position) {
                    case 0:
                        intent = new Intent(TrainDetailsJPush.this, TrainInformActivity.class);
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", gridViewText[position]);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(TrainDetailsJPush.this, CaptureActivity.class);
//                        intent = new Intent(TrainDetailsActivity.this, QrCodeSignInActivity.class);
                        currentPosition = position;
                        startActivityForResult(intent,REQUEST_CODE);
                        break;
                    case 2:
                        intent = new Intent(TrainDetailsJPush.this, TrainScheduleActivity.class);
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", gridViewText[position]);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(TrainDetailsJPush.this, TrainRemindActivity.class);
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", gridViewText[position]);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(TrainDetailsJPush.this, CaptureActivity.class);
//                        intent = new Intent(TrainDetailsActivity.this, QrCodeSignInActivity.class);
                        currentPosition = position;
                        startActivityForResult(intent,REQUEST_CODE);
                        break;
                    case 5:
                        intent = new Intent(TrainDetailsJPush.this, TrainWorkActivity.class);
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", gridViewText[position]);
                        startActivity(intent);
                        break;
                    case 6:
//                        intent = new Intent(TrainDetailsActivity.this, TrainExamActivity.class);
                        intent = new Intent(TrainDetailsJPush.this, KaoChangType02Activity.class);
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", gridViewText[position]);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(TrainDetailsJPush.this, TrainDownloadActivity.class);
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", gridViewText[position]);
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(TrainDetailsJPush.this, TrainStudentActivity.class);
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", gridViewText[position]);
                        startActivity(intent);
                        break;
                    case 9:
                        intent = new Intent(TrainDetailsJPush.this, TrainGuideActivity.class);
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", gridViewText[position]);
                        startActivity(intent);
                        break;
                    case 10:
                        intent = new Intent(TrainDetailsJPush.this, TrainWeatherActivity.class);
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", "天气预报");
                        startActivity(intent);
                        break;
                    default:
                        break;
                }*/
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Intent intent = new Intent(this, QrCodeSignInActivity.class);
                    if (getIntent().hasExtra(QrCodeSignInActivity.TITLE))
                        intent.putExtra(QrCodeSignInActivity.TITLE, getIntent().getStringExtra(QrCodeSignInActivity.TITLE));
                    intent.putExtra("webUrl", result.toString());
//                    intent.putExtra("training", tDataBean);
                    intent.putExtra(QrCodeSignInActivity.TITLE, gridViewText[currentPosition]);
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public void trainList(int trainID) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getTrainListUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("only", trainID + "")
                .build()
                .execute(new TrainListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("TrainList_onError", e.toString());
                                 Toast.makeText(TrainDetailsJPush.this, "网络已断开，请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(TrainList response, int id) {
                                 Log.e("TrainListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         tDataBean = response.getData().get(0);
//                                         tltle.setText(tDataBean.getTrainingtitle());
                                         loadUI();
                                         loadDatas();
                                         onClick();
                                         break;
                                     default:
                                         Toast.makeText(TrainDetailsJPush.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class TrainListCallback extends Callback<TrainList> {

        @Override
        public TrainList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            TrainList trainList = new Gson().fromJson(result, TrainList.class);
            return trainList;
        }
    }
}
