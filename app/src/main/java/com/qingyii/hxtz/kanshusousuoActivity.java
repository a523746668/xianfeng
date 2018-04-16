package com.qingyii.hxtz;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.util.EmptyUtil;

/**
 * 看书搜索界面
 *
 * @author Administrator
 */
public class kanshusousuoActivity extends AbBaseActivity {
    private ImageView sousuo;
    private EditText et_sousuoinfo;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanshusousuo);
        initUI();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }

    private void initUI() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        et_sousuoinfo = (EditText) findViewById(R.id.et_sousuoinfo);
        sousuo = (ImageView) findViewById(R.id.sousuo);
        sousuo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!EmptyUtil.IsNotEmpty(et_sousuoinfo.getText().toString())) {
                    Toast.makeText(kanshusousuoActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
