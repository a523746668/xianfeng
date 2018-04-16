package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.pojo.TrainList;

/**
 * 培训考试
 */
public class TrainExamActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_exam);
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
    }
}
