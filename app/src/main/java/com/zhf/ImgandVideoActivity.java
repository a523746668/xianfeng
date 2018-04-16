package com.zhf;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qingyii.hxtz.R;
import com.zhf.adapter.ImgandVideoAdapter;
import com.zhf.bean.SendTask;
import com.zhf.zfragment.ImgandVideoFragment;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

public class ImgandVideoActivity extends AppCompatActivity {
     private   ArrayList<SendTask> list=null;
     private int postion;
     private ViewPager viewPager;
      private ImgandVideoAdapter adapter;
    private ArrayList<ImgandVideoFragment> fragments=new ArrayList<>();
   private ImageView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgand_video);

        list= (ArrayList<SendTask>) getIntent().getSerializableExtra("tasks");
        postion=getIntent().getIntExtra("position",0);
        viewPager= (ViewPager) findViewById(R.id.imgvp);

        initviewpager();
        delete();
    }

    private void delete() {
     delete= (ImageView) findViewById(R.id.delete_iv);
      delete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


                SendTask task=list.get(viewPager.getCurrentItem());
                 int p=viewPager.getCurrentItem();
                  list.remove(p);
                fragments.clear();
              if(list.size()>0){
                  for(SendTask task1 :list){
                      ImgandVideoFragment fragment=new ImgandVideoFragment(task1);
                      fragments.add(fragment);
                  }
              }
                adapter.notifyDataSetChanged();
                EventBus.getDefault().post(list);
               if(fragments.size()<=0){
                   finish();
               }

          }
      });

    }

    private void initviewpager() {
          for(SendTask task :list){
               fragments.add(new ImgandVideoFragment(task));
          }


        adapter=new ImgandVideoAdapter(getSupportFragmentManager(),fragments,this);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(postion);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
