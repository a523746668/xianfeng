package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.adapter.mytongshiAdapter;
import com.qingyii.hxtz.bean.mytongshiInfo;

import java.util.ArrayList;

/**
 * 我的同事
 * @author Administrator
 *
 */
public class mytongshiActivity extends AbBaseActivity {
    private mytongshiAdapter adapter;
    private ImageView back_btn;
    private ArrayList<mytongshiInfo> list=new ArrayList<mytongshiInfo>();
    private ListView lv_mytongshi;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mytongshi);
    initUI();
    }
	private void initUI() {
		lv_mytongshi=(ListView) findViewById(R.id.lv_mytongshi);
		//加载静态数据
//		list.add(new mytongshiInfo("Eason", "男", "歌手", "啦啦啦啦啦"));
//		list.add(new mytongshiInfo("Jarvan", "女", "我也是歌手", "啦啦啦啦啦"));
		mytongshiInfo info1=new mytongshiInfo();
		info1.setTongshiName("Eason");
		info1.setTongshiUnit("歌手");
		info1.setTongshiZhiwu("lalalalalalalal");
		info1.setTongshiSex("男");
		info1.setTongshiImage("drawable://"+ R.mipmap.touxiang);
		mytongshiInfo info2=new mytongshiInfo();
		info2.setTongshiName("Jarvan");
		info2.setTongshiUnit("歌手");
		info2.setTongshiZhiwu("lalalalalalalal");
		info2.setTongshiSex("女");
		info2.setTongshiImage("drawable://"+ R.mipmap.haimianbb);
		mytongshiInfo info3=new mytongshiInfo();
		info3.setTongshiName("Jarvan");
		info3.setTongshiUnit("歌手");
		info3.setTongshiZhiwu("lalalalalalalal");
		info3.setTongshiSex("男");
		info3.setTongshiImage("drawable://"+ R.mipmap.dashitouxiang);
		mytongshiInfo info4=new mytongshiInfo();
		info4.setTongshiName("Jarvan");
		info4.setTongshiUnit("歌手");
		info4.setTongshiZhiwu("lalalalalalalal");
		info4.setTongshiSex("女");
		info4.setTongshiImage("drawable://"+ R.mipmap.mao);
		list.add(info1);
		list.add(info2);
		list.add(info3);
		list.add(info4);
		
		
		adapter=new mytongshiAdapter(this, list);
		lv_mytongshi.setAdapter(adapter);
		back_btn=(ImageView) findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				
			}
		});
		lv_mytongshi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		        Intent it=new Intent(mytongshiActivity.this,tongshitalkActivity.class);
		        
				startActivity(it);
			}
		});
		
	}
}
