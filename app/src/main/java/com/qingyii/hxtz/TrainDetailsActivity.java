package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxtz.base.app.GlobalConsts;
import com.qingyii.hxtz.customview.MyGridView;
import com.qingyii.hxtz.pojo.TrainList;
import com.qingyii.hxtz.training.details.schedule.mvp.ui.activity.TrainNoticeActivity;
import com.qingyii.hxtz.util.DateUtils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qingyii.hxtz.QrCodeSignInActivity.REQUEST_CODE;

//培训详情
public class TrainDetailsActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;

    private ListView train_details_listview;
    private View litHeader;

    private MyGridView gridView;
    private List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
    private SimpleAdapter sim_adapter;
    private int[] gridViewImage = {R.mipmap.train_details_inform, R.mipmap.train_details_guide,
            R.mipmap.train_details_schedule, R.mipmap.train_details_student,
            R.mipmap.train_details_sign,
            R.mipmap.train_details_work,
            R.mipmap.train_details_exam, R.mipmap.train_details_download,
//            R.mipmap.train_details_remind
    };//, R.mipmap.train_details_weather, R.mipmap.train_details_report
    private String[] gridViewText =
            {"培训通知", "培训须知", "培训日程", "培训学员"
                    , "扫码签到"
                    , "出勤排行",
                    "培训测试", "资料下载",
                    //"上课提醒"
            };//, "天气预报", "培训报到"

    private RelativeLayout train_details_site_in;

    private List<Class<?>> classes = new ArrayList<>();

//    private RelativeLayout train_details_weather_in;

    private TextView train_details_site;
    private TextView train_details_time;
    private TextView train_title;
    private TextView train_details_lecturer;
    public int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_details);
        classes.add(TrainNoticeActivity.class);
        classes.add(TrainGuideActivity.class);
        classes.add(TrainScheduleActivity.class);
        classes.add(TrainStudentActivity.class);
        classes.add(CaptureActivity.class);
        classes.add(TrainWorkActivity.class);
        classes.add(KaoChangType02Activity.class);
        classes.add(TrainDownloadActivity.class);
//        classes.add(TrainRemindActivity.class);
        litHeader = View.inflate(this, R.layout.train_details_listview_header, null);

        intent = getIntent();
        tDataBean = intent.getParcelableExtra("training");
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("培训详情");
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
        train_title = (TextView) litHeader.findViewById(R.id.train_title);
        train_details_lecturer = (TextView) litHeader.findViewById(R.id.train_details_lecturer);

        this.loadUI(savedInstanceState);
        this.loadDatas(savedInstanceState);
        this.onClick();
    }

    /**
     * UI界面加载
     *
     * @param savedInstanceState
     */
    private void loadUI(Bundle savedInstanceState) {

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

    private void loadDatas(Bundle savedInstanceState) {
        train_details_site.setText("地址 : " + tDataBean.getAddress());
        train_details_time.setText("培训时间 : " + DateUtils.getDateToString(tDataBean.getBegintime()) +
                "至" + DateUtils.getDateToString(tDataBean.getEndtime()));
        train_details_lecturer.setText("主办方 : " + tDataBean.getOrganizer());
        train_title.setText(tDataBean.getTrainingtitle());
    }

    private void onClick() {
        train_details_site_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TrainDetailsActivity.this, TrainSiteActivity.class);
                intent.putExtra("training", tDataBean);
                intent.putExtra("tltle", tDataBean.getAddress());
                startActivity(intent);
            }
        });

//        train_details_weather_in.setOnClickListener(new NotifyListView.OnClickListener() {
//            @Override
//            public void onClick(NotifyListView v) {
//                intent = new Intent(TrainDetailsActivity.this, TrainWeatherActivity.class);
//                intent.putExtra("training", tDataBean);
//                intent.putExtra("tltle", "天气预报");
//                startActivity(intent);
//            }
//        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position < classes.size()) {

                    intent = new Intent(TrainDetailsActivity.this, classes.get(position));
                    if (classes.get(position).equals(TrainNoticeActivity.class)) {
                        intent.putExtra(GlobalConsts.TRAIN_ID, tDataBean.getId());
                    } else if(classes.get(position).equals(CaptureActivity.class)){
                        if(tDataBean.getIsend()==100){
                            UiUtils.snackbarText("不在签到时间内");
                                return;
                        }
                        startActivityForResult(intent, REQUEST_CODE);
                        return;
                    }else {
                        intent.putExtra("training", tDataBean);
                        intent.putExtra("tltle", gridViewText[position]);
                    }
                    startActivity(intent);
                }
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
                    intent.putExtra("training", tDataBean);
                    intent.putExtra(QrCodeSignInActivity.TITLE, gridViewText[currentPosition]);
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
