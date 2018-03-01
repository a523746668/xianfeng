package com.qingyii.hxt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.pojo.TrainCourseList;
import com.qingyii.hxt.pojo.TrainList;
import com.qingyii.hxt.util.DateUtils;
import com.qingyii.hxt.view.SwitchView;

import java.util.ArrayList;
import java.util.List;

/**
 * 上课提醒
 */
public class TrainRemindActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;
    private List<TrainCourseList.DataBean> tcDataBeanList = new ArrayList<TrainCourseList.DataBean>();

    private SharedPreferences.Editor editor = MyApplication.hxt_setting_config.edit();
    private SwitchView remind_switchview;
    private Spinner remind_duration;
    private Spinner remind_frequency;
    private List<String> listDurationSpinner = new ArrayList<>();
    private List<String> listFrequencySpinner = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_remind);
        intent = getIntent();
        tDataBean = intent.getParcelableExtra("training");

        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText(intent.getStringExtra("tltle"));
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        remind_switchview = (SwitchView) findViewById(R.id.remind_switchview);
        remind_duration = (Spinner) findViewById(R.id.remind_duration);
        remind_frequency = (Spinner) findViewById(R.id.remind_frequency);

        //保存开关
        Log.e("Remind_Switch", MyApplication.hxt_setting_config.getBoolean(tDataBean.getId() + "_remind_switch", false) + "");
        remind_switchview.setOpened(MyApplication.hxt_setting_config.getBoolean(tDataBean.getId() + "_remind_switch", false));

        //保存上课时间戳
        String timeDatas = "";
        for (int i = 0; i < tcDataBeanList.size(); i++) {
            timeDatas = timeDatas + DateUtils.getStringToLongDate(tcDataBeanList.get(i).getBegintime()) + ",";
        }
        Log.e("Remind_timeDatas", timeDatas);
        editor.putString(tDataBean.getId() + "_remind_timeDatas", timeDatas);
        editor.commit();

        this.loadUI(savedInstanceState);
        this.onClick();
    }

    /**
     * UI界面加载
     *
     * @param savedInstanceState
     */
    private void loadUI(Bundle savedInstanceState) {
        //持续时间
        listDurationSpinner.add("60");
        listDurationSpinner.add("50");
        listDurationSpinner.add("40");
        listDurationSpinner.add("30");
        listDurationSpinner.add("20");
        listDurationSpinner.add("10");
        // 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        final ArrayAdapter<String> adapterDuration = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listDurationSpinner);
        adapterDuration.setDropDownViewResource(R.layout.spinner_dropdown);
        remind_duration.setAdapter(adapterDuration);
        //设置初始值
        switch (MyApplication.hxt_setting_config.getInt(tDataBean.getId() + "_remind_duration", 0)) {
            case 60:
                remind_duration.setSelection(0);
                break;
            case 50:
                remind_duration.setSelection(1);
                break;
            case 40:
                remind_duration.setSelection(2);
                break;
            case 30:
                remind_duration.setSelection(3);
                break;
            case 20:
                remind_duration.setSelection(4);
                break;
            case 10:
                remind_duration.setSelection(5);
                break;
            default:
                remind_duration.setSelection(0);
                break;
        }

        //频率
        listFrequencySpinner.add("5");
        listFrequencySpinner.add("10");
        listFrequencySpinner.add("15");
        listFrequencySpinner.add("20");
        // 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        final ArrayAdapter<String> adapterFrequency = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listFrequencySpinner);
        adapterFrequency.setDropDownViewResource(R.layout.spinner_dropdown);
        remind_frequency.setAdapter(adapterFrequency);
        //设置初始值
        switch (MyApplication.hxt_setting_config.getInt(tDataBean.getId() + "_remind_frequency", 0)) {
            case 5:
                remind_frequency.setSelection(0);
                break;
            case 10:
                remind_frequency.setSelection(1);
                break;
            case 15:
                remind_frequency.setSelection(2);
                break;
            case 20:
                remind_frequency.setSelection(3);
                break;
            default:
                remind_frequency.setSelection(0);
                break;
        }
    }

    private void onClick() {
        //开启关闭切换
        remind_switchview.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                editor.putBoolean(tDataBean.getId() + "_remind_switch", true);
                editor.commit();
                Log.e("Remind_Switch", MyApplication.hxt_setting_config.getBoolean(tDataBean.getId() + "_remind_switch", false) + "");

                remind_switchview.setOpened(true);
            }

            @Override
            public void toggleToOff(View view) {
                editor.putBoolean(tDataBean.getId() + "_remind_switch", false);
                editor.commit();
                Log.e("Remind_Switch", MyApplication.hxt_setting_config.getBoolean(tDataBean.getId() + "_remind_switch", false) + "");

                remind_switchview.setOpened(false);
            }
        });

        //持续时间
        remind_duration.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                editor.putInt(tDataBean.getId() + "_remind_duration", Integer.parseInt(listDurationSpinner.get(position)));
                editor.commit();
                Log.e("Remind_Spinner", position + "");
//                arg0.setVisibility(NotifyListView.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
//                arg0.setVisibility(NotifyListView.VISIBLE);
            }
        });
        //频率
        remind_frequency.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                editor.putInt(tDataBean.getId() + "_remind_frequency", Integer.parseInt(listFrequencySpinner.get(position)));
                editor.commit();
//                arg0.setVisibility(NotifyListView.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
//                arg0.setVisibility(NotifyListView.VISIBLE);
            }
        });

    }

}
