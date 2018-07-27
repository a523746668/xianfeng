package com.qingyii.hxtz.zhf;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.zhf.Sqlitehelper.Drafitbean;
import com.qingyii.hxtz.zhf.Sqlitehelper.DraftHelper;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.adapter.DraftAdapter;
import com.qingyii.hxtz.zhf.wight.DefaultItem;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//草稿箱页面
public class
DraftActivity extends AppCompatActivity {
    private Unbinder unbinder;
    @BindView(R.id.toolbar_back)
    Button back
            ;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.draftrecyc)
    RecyclerView recyc;

    @BindView(R.id.toolbar_right_tv)
    TextView clear;

    private ArrayList<Drafitbean> list=new ArrayList<>();

    private DraftAdapter adapter;

    DraftHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        unbinder= ButterKnife.bind(this);
        helper=new DraftHelper(this);
        inittoolbar();
        initrecyc();
        getdatafromsqlite();


    }

    private void initrecyc() {
      recyc.setLayoutManager(new LinearLayoutManager(this));
        recyc.addItemDecoration(new DefaultItem());
        adapter=new DraftAdapter(list);
       recyc.setAdapter(adapter);
       adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(Object Data, View view, int position) {
               EventBus.getDefault().post(list.get(position));
               finish();
           }

           @Override
           public void onItemLongClick(Object Data, View view, int position) {
               AlertDialog.Builder ab = new AlertDialog.Builder(DraftActivity.this);
               //2. 设置各种信息
               ab.setIcon(R.mipmap.social_message_bind_icon);  //设置图标
               ab.setTitle("提示");
               ab.setMessage("您确定要删除此条草稿记录吗");
               ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       helper.deleteBySql(Global.taskdetailbean.getData().getTask().getId(),list.get(position).getName());
                       list.remove(position);
                       adapter.notifyDataSetChanged();
                       dialog.dismiss();
                   }
               });
              ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                  }
              });
                ab.show();

           }
       });
    }

    private void inittoolbar() {
         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
         title.setText("草稿箱");
         clear.setText("清空");
         clear.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v)
             {
                 AlertDialog.Builder ab = new AlertDialog.Builder(DraftActivity.this);
                 //2. 设置各种信息
                 ab.setIcon(R.mipmap.social_message_bind_icon);  //设置图标
                 ab.setTitle("提示");
                 ab.setMessage("您确定要清空次任务对应的所有草稿吗");
                 ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         helper.deleteBySql(Global.taskdetailbean.getData().getTask().getId());
                         list.clear();
                         adapter.notifyDataSetChanged();
                         dialog.dismiss();
                     }
                 });
                 ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     }
                 });
                 ab.show();
             }
         });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       unbinder.unbind();
    }

    public void getdatafromsqlite() {
         helper=new DraftHelper(this);
         list.clear();
         ArrayList<Drafitbean> ll= helper.getdata(Global.taskdetailbean.getData().getTask().getId());
         list.addAll(ll);
         adapter.notifyDataSetChanged();

    }
}
