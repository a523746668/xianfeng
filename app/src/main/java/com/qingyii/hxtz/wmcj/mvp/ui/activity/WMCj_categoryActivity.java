package com.qingyii.hxtz.wmcj.mvp.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerWMCJ_categoryComponent;
import com.qingyii.hxtz.wmcj.di.module.WMCJcategoryModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Common;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.shuaixuan;
import com.qingyii.hxtz.wmcj.mvp.presenter.WMCJcatgoryPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.selectViewBinder;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.tagViewBinder;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.zhy.autolayout.AutoLinearLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import butterknife.BindView;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.MultiTypeAsserts;

public class WMCj_categoryActivity  extends BaseActivity<WMCJcatgoryPresenter> implements WMCJContract.WMCJcategoryView {

     @BindView(R.id.wmcj_cagegory_recyc)
     RecyclerView  recyclerView;

    MultiTypeAdapter adapter;

    @BindView(R.id.toolbar_back_layout)
    AutoLinearLayout back;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.toolbar_right_tv)
    TextView barright;

    private ArrayList<Common> list=new ArrayList<>();
    //存储已经展示上去的单位
    ArrayList<ReportMenu.DataBean.AllsonIndustryBean> alldanwei=new ArrayList<>();
    //存储所有单位
    ArrayList<ReportMenu.DataBean.AllsonIndustryBean> alldanwei1=new ArrayList<>();
    ArrayList<ReportMenu.DataBean.MenuItemBean> ones=new ArrayList<>();
    ArrayList<ReportMenu.DataBean.MenuItem1Bean> twos=new ArrayList<>();
    ArrayList<ReportMenu.DataBean.TagItemBean> tags=new ArrayList<>();
    shuaixuan sx;
    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerWMCJ_categoryComponent.builder()
                .appComponent(appComponent)
                .wMCJcategoryModule(new WMCJcategoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_wmcj_category;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        inittoolbar();
        initrecyclerview();
        mPresenter.getReportMenu();
    }

    private void inittoolbar() {
        sx=new shuaixuan();
        sx.setDanwei(alldanwei);
        sx.setOnetag(ones);
        sx.setTwotag(twos);
        sx.setTtags(tags);
         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
         title.setText("筛选内容");
        barright.setText("确认筛选");
        barright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(sx,EventBusTags.WMCJ_REPORT);
                finish();
            }
        });
    }

    private void initrecyclerview() {

        ChipsLayoutManager layoutManager =  ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setScrollingEnabled(true)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MultiTypeAdapter();
       adapter.register(Common.class).to(new selectViewBinder(),new tagViewBinder())
       .withClassLinker(new ClassLinker<Common>() {
           @NonNull
           @Override
           public Class<? extends ItemViewBinder<Common, ?>> index(@NonNull Common common) {
                if(common.type==Common.data_type){
                    return tagViewBinder.class;
                }else {
                    return  selectViewBinder.class;
                }
           }
       });
       recyclerView.setAdapter(adapter);

       MultiTypeAsserts.assertHasTheSameAdapter(recyclerView,adapter);
     }

    @Override
    public void getReportMenuSuccess(ReportMenu bean) {
                 list.add(new Common("按单位标签筛选",Common.data_type));
                 list.addAll(bean.getData().getTag_item());
                 list.add(new Common("按一级指标筛选",Common.data_type));
                //数据重复做一下处理
                 for(int i=0;i<bean.getData().getMenu_item().size()/2;i++){
                      list.add(bean.getData().getMenu_item().get(i));
                 }
                 list.add(new Common("按二级指标筛选",Common.data_type));
                 list.addAll(bean.getData().getMenu_item1());
                 list.add(new Common ("按单位名称筛选",Common.data_type));
                 list.addAll(bean.getData().getAllsonIndustry());
                 adapter.setItems(list);
                 MultiTypeAsserts.assertAllRegistered(adapter,list);

                 adapter.notifyDataSetChanged();
                 hideLoading();
    }

    @Override
    public void getExmineMenuSuccess(ExamineMenu bean) {

    }

    @Override
    public void showLoading() {
        HintUtil.showdialog(this);
    }

    @Override
    public void hideLoading() {
       HintUtil.stopdialog();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Subscriber(tag= EventBusTags.WMCJ_Adapter_Select)
    public void refrechdata(Message msg){
          if(msg.arg1==1){ //删除
             if(msg.arg2==1){
                 tags.remove(msg.obj);

             }else if(msg.arg2==2){
                 ones.remove(msg.obj);
             }else if(msg.arg2==3){
                 twos.remove(msg.obj);
             } else {
                 alldanwei.remove(msg.obj);
             }
          } else if(msg.arg1==2){ //增加
              if(msg.arg2==1){
                  tags.add((ReportMenu.DataBean.TagItemBean) msg.obj);

              }else if(msg.arg2==2){
                  ones.add((ReportMenu.DataBean.MenuItemBean) msg.obj);

              }else if(msg.arg2==3){
                  twos.add((ReportMenu.DataBean.MenuItem1Bean) msg.obj);
              } else {
                  alldanwei.add((ReportMenu.DataBean.AllsonIndustryBean) msg.obj);
              }
          }
    }

}
