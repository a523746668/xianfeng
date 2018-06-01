package com.qingyii.hxtz.wmcj.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.OnItemClickListener;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerWMCJcategoryComponent;
import com.qingyii.hxtz.wmcj.di.module.WMCJcategoryModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;
import com.qingyii.hxtz.wmcj.mvp.presenter.WMCJcatgoryPresenter;
import com.qingyii.hxtz.zhf.Util.HintUtil;

import java.util.ArrayList;

import butterknife.BindView;

//文明创建筛选，切换界面
public class WMCJcategoryActivity extends BaseActivity<WMCJcatgoryPresenter> implements WMCJContract.WMCJcategoryView {

       @BindView(R.id.category_tv1)
       TextView tv1;

       @BindView(R.id.category_tv2)
       TextView tv2;

       @BindView(R.id.category_tv3)
       TextView tv3;

       @BindView(R.id.category_tv4)
       TextView tv4;
       @BindView(R.id.toolbar_back)
       Button back;

       @BindView(R.id.toolbar_title)
       TextView title;

       @BindView(R.id.toolbar_right_tv)
       TextView barright;

       @BindView(R.id.category_recyclerView1)
       RecyclerView rec1;

       @BindView(R.id.category_recyclerView2)
       RecyclerView rec2;

       @BindView(R.id.category_recyclerView3)
       RecyclerView rec3;

       @BindView(R.id.category_recyclerView4)
       RecyclerView rec4;
       private String flag;

       CommonAdapter<ReportMenu.DataBean.AllsonIndustryBean> adapter;
       CommonAdapter<ReportMenu.DataBean.MenuItemBean> one;
       CommonAdapter<ReportMenu.DataBean.MenuItem1Bean> two;
       CommonAdapter<ReportMenu.DataBean.TagItemBean>  tag;
       CommonAdapter<ExamineMenu.DataBean.FirstSystemBean> adapter1;
       CommonAdapter<ExamineMenu.DataBean.TagListBean> adapter2;

       ArrayList<ReportMenu.DataBean.AllsonIndustryBean> alldanwei=new ArrayList<>();
       ArrayList<ReportMenu.DataBean.MenuItemBean> ones=new ArrayList<>();
       ArrayList<ReportMenu.DataBean.MenuItem1Bean> twos=new ArrayList<>();
       ArrayList<ReportMenu.DataBean.TagItemBean> tags=new ArrayList<>();
       ArrayList<ExamineMenu.DataBean.FirstSystemBean> list1=new ArrayList<>();
       ArrayList<ExamineMenu.DataBean.TagListBean> list2=new ArrayList<>();

    ChipsLayoutManager layoutManager1;
    ChipsLayoutManager layoutManager2;
    ChipsLayoutManager layoutManager3;
    ChipsLayoutManager layoutManager4;
    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerWMCJcategoryComponent.builder()
                .appComponent(appComponent)
                .wMCJcategoryModule(new WMCJcategoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_wmcjcategory;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        flag=getIntent().getStringExtra("wmcj");
        initlayoutmanager();
        if(flag.equalsIgnoreCase("Report")){
             title.setText("筛选");
            tv1.setText("按单位标签筛选");
            tv2.setText("按一级指标筛选");
            tv3.setText("按二级指标筛选");
            tv4.setText("按单位名称筛选");
            initrecycylerviewReport();
            mPresenter.getReportMenu();

        } else if(flag.equalsIgnoreCase("Result")){
            title.setText("切换排行榜");
            tv1.setText("按单位标签筛选");
            tv2.setText("按一级指标筛选");
            tv3.setText("按二级指标筛选");
            tv4.setText("按单位名称筛选");

        } else if(flag.equalsIgnoreCase("Examine")){
           title.setText("考核切换");
           tv1.setText("选择任务清单");
           tv2.setText("选择单位标签属性");
           tv3.setVisibility(View.GONE);
           tv4.setVisibility(View.GONE);
           rec3.setVisibility(View.GONE);
           rec4.setVisibility(View.GONE);
           initrecycylerviewExamine();
           mPresenter.getExmineMenu();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        barright.setText("确认");
        init();
    }

    private void initrecycylerviewExamine() {
          rec1.setLayoutManager(layoutManager1);
          rec2.setLayoutManager(layoutManager2);
          adapter1=new CommonAdapter<ExamineMenu.DataBean.FirstSystemBean>(this,list1,R.layout.category_recyclerview_item) {
              @Override
              public void convert(ViewHolder viewHolder, ExamineMenu.DataBean.FirstSystemBean firstSystemBean) {
                    TextView tv=viewHolder.getView(R.id.category_item_tv);
                    tv.setText(firstSystemBean.getTitle());
              }
          };
          rec1.setAdapter(adapter1);
         adapter2=new CommonAdapter<ExamineMenu.DataBean.TagListBean>(this,list2,R.layout.category_recyclerview_item) {
             @Override
             public void convert(ViewHolder viewHolder, ExamineMenu.DataBean.TagListBean tagListBean) {
                      TextView tv=viewHolder.getView(R.id.category_item_tv);
                      tv.setText(tagListBean.getTitle());
             }
         };
         rec2.setAdapter(adapter2);
    }

    private void initlayoutmanager() {
        layoutManager1= ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setScrollingEnabled(false)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
        layoutManager2= ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setScrollingEnabled(false)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
        layoutManager3= ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setScrollingEnabled(false)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
        layoutManager4= ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setScrollingEnabled(false)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
    }

    private void init() {
        rec1.setLayoutManager(layoutManager1);
        rec2.setLayoutManager(layoutManager2);
        rec3.setLayoutManager(layoutManager3);
        rec4.setLayoutManager(layoutManager4);
    }

    private void initrecycylerviewReport() {
        tag=new CommonAdapter<ReportMenu.DataBean.TagItemBean>(this,tags,R.layout.category_recyclerview_item) {
            @Override
            public void convert(ViewHolder viewHolder, ReportMenu.DataBean.TagItemBean tagItemBean) {
                      TextView textView= viewHolder.getView(R.id.category_item_tv);
                      textView.setText(tagItemBean.getTitle());
            }
        };
        rec1.setAdapter(tag);
       one=new CommonAdapter<ReportMenu.DataBean.MenuItemBean>(this,ones,R.layout.category_recyclerview_item) {
           @Override
           public void convert(ViewHolder viewHolder, ReportMenu.DataBean.MenuItemBean menuItemBean) {
               TextView textView= viewHolder.getView(R.id.category_item_tv);
               textView.setText(menuItemBean.getTitle());

           }
       };

       one.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                one.notifyDataSetChanged();
           }

           @Override
           public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
               return false;
           }
       });
       rec2.setAdapter(one);

      two=new CommonAdapter<ReportMenu.DataBean.MenuItem1Bean>(this,twos,R.layout.category_recyclerview_item) {
          @Override
          public void convert(ViewHolder viewHolder, ReportMenu.DataBean.MenuItem1Bean menuItem1Bean) {
              TextView textView= viewHolder.getView(R.id.category_item_tv);
              textView.setText(menuItem1Bean.getTitle());
          }
      } ;
      rec3.setAdapter(two);
      adapter=new CommonAdapter<ReportMenu.DataBean.AllsonIndustryBean>(this,alldanwei,R.layout.category_recyclerview_item) {
          @Override
          public void convert(ViewHolder viewHolder, ReportMenu.DataBean.AllsonIndustryBean allsonIndustryBean) {
              TextView textView= viewHolder.getView(R.id.category_item_tv);
              textView.setText(allsonIndustryBean.getTitle());
          }

      };
      rec4.setAdapter(adapter);
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


    @Override
    public void getReportMenuSuccess(ReportMenu bean) {
        ones.clear();
        ones.addAll(bean.getData().getMenu_item());
        twos.clear();
        twos.addAll(bean.getData().getMenu_item1());
        tags.clear();
        tags.addAll(bean.getData().getTag_item());
        alldanwei.clear();
        alldanwei.addAll(bean.getData().getAllsonIndustry());
        one.notifyDataSetChanged();
        two.notifyDataSetChanged();
        tag.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void getExmineMenuSuccess(ExamineMenu bean) {
          list1.clear();
          list1.addAll(bean.getData().getFirstSystem());
          adapter1.notifyDataSetChanged();
          list2.clear();
          list2.addAll(bean.getData().getTagList());
          adapter2.notifyDataSetChanged();
          hideLoading();
    }
}
