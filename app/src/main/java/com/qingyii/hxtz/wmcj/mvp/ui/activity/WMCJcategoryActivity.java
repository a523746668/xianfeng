package com.qingyii.hxtz.wmcj.mvp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerWMCJcategoryComponent;
import com.qingyii.hxtz.wmcj.di.module.WMCJcategoryModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineSelectMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.shuaixuan;
import com.qingyii.hxtz.wmcj.mvp.presenter.WMCJcatgoryPresenter;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.zhy.autolayout.AutoLinearLayout;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.http.POST;

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

      @BindView(R.id.toolbar_back_layout)
      AutoLinearLayout back;

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

       BaseRecyclerAdapter<ReportMenu.DataBean.AllsonIndustryBean> adaptera;

       private String flag;

       private int index=10;

       CommonAdapter<ReportMenu.DataBean.AllsonIndustryBean> adapter;
       CommonAdapter<ReportMenu.DataBean.MenuItemBean> one;
       CommonAdapter<ReportMenu.DataBean.MenuItem1Bean> two;
       CommonAdapter<ReportMenu.DataBean.TagItemBean>  tag;
       CommonAdapter<ExamineMenu.DataBean.FirstSystemBean> adapter1;
       CommonAdapter<ExamineMenu.DataBean.TagListBean> adapter2;

       //存储已经展示上去的单位
       ArrayList<ReportMenu.DataBean.AllsonIndustryBean> alldanwei=new ArrayList<>();
       //存储所有单位
       ArrayList<ReportMenu.DataBean.AllsonIndustryBean> alldanwei1=new ArrayList<>();
       ArrayList<ReportMenu.DataBean.MenuItemBean> ones=new ArrayList<>();
       ArrayList<ReportMenu.DataBean.MenuItem1Bean> twos=new ArrayList<>();
       ArrayList<ReportMenu.DataBean.TagItemBean> tags=new ArrayList<>();

       //存储筛选标签
       shuaixuan  sx=new shuaixuan();
       ArrayList<ExamineMenu.DataBean.FirstSystemBean> list1=new ArrayList<>();
       ArrayList<ExamineMenu.DataBean.TagListBean> list2=new ArrayList<>();

       //已选择标签
       ArrayList<ExamineMenu.DataBean.TagListBean> list3=new ArrayList<>();

       ExamineMenu.DataBean.FirstSystemBean firstSystemBean=null;
       ExamineMenu.DataBean.TagListBean tagListBean=null;

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
            barright.setText("确认筛选");
            barright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 EventBus.getDefault().post(sx,EventBusTags.WMCJ_REPORT);
                 finish();
                }
            });

        } else if(flag.equalsIgnoreCase("Result")){
            title.setText("切换排行榜");
            tv1.setText("选择任务清单");
            tv2.setText("选择单位标签属性");
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            rec3.setVisibility(View.GONE);
            rec4.setVisibility(View.GONE);

            initrecycylerviewExamine();
            mPresenter.getExmineMenu();
            barright.setText("确认");
            barright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg=Message.obtain();
                    if(firstSystemBean==null){
                        msg.arg1=list1.get(list1.size()-1).getId();
                        msg.arg2=-1;  //不改变2017/2018
                    }else {
                        msg.arg1=firstSystemBean.getId();
                        msg.arg2=list1.indexOf(firstSystemBean);
                    }
                    if(list3.size()>0){
                        msg.obj=list3;
                    }
                    EventBus.getDefault().post(msg, EventBusTags.WMCJ_RESULT);
                    finish();
                }
            });

        } else if(flag.equalsIgnoreCase("Examine")){
           title.setText("考核切换");
           tv1.setText("选择任务清单");
           tv2.setText("选择单位标签属性");
           tv2.setVisibility(View.GONE);
           tv3.setVisibility(View.GONE);
           tv4.setVisibility(View.GONE);
           rec2.setVisibility(View.GONE);
           rec3.setVisibility(View.GONE);
           rec4.setVisibility(View.GONE);

            initrecycylerviewExamine();
            mPresenter.getExmineMenu();
            barright.setText("切换");
            barright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg=Message.obtain();
                   try {
                       if(firstSystemBean==null){
                           msg.arg1=list1.get(list1.size()-1).getId();
                       }else {
                           msg.arg1=firstSystemBean.getId();
                       }
                       if(list3.size()>0){
                           msg.obj=list3;
                       }
                       EventBus.getDefault().post(msg, EventBusTags.EXAMINE);
                       finish();
                   }catch (Exception e){
                       e.printStackTrace();
                       finish();
                   }


                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                    ImageView iv=viewHolder.getView(R.id.itemtishi);
                    if(firstSystemBean.isIscheck()){
                          iv.setVisibility(View.VISIBLE);
                    } else {
                          iv.setVisibility(View.GONE);
                    }
              }
          };
          adapter1.setOnItemClickListener(new OnItemClickListener() {
              @Override
              public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                       if(firstSystemBean!=null){
                           firstSystemBean.setIscheck(false);
                       }
                        firstSystemBean=list1.get(i);
                        firstSystemBean.setIscheck(true);
                        adapter1.notifyDataSetChanged();
              }

              @Override
              public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                  return false;
              }
          });
          rec1.setAdapter(adapter1);
         adapter2=new CommonAdapter<ExamineMenu.DataBean.TagListBean>(this,list2,R.layout.category_recyclerview_item) {
             @Override
             public void convert(ViewHolder viewHolder, ExamineMenu.DataBean.TagListBean tagListBean) {
                      TextView tv=viewHolder.getView(R.id.category_item_tv);
                      tv.setText(tagListBean.getTitle());
                      ImageView iv=viewHolder.getView(R.id.itemtishi);
                     if(tagListBean.isIscheck()){
                         iv.setVisibility(View.VISIBLE);
                     } else {
                         iv.setVisibility(View.GONE);
                     }

             }
         };
        adapter2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                ImageView iv= (ImageView) view.findViewById(R.id.itemtishi);
                ExamineMenu.DataBean.TagListBean tagBean=list2.get(i);
                 if(tagBean.isIscheck()){
                     iv.setVisibility(View.GONE);
                     tagBean.setIscheck(false);
                     list3.remove(tagBean);
                 }else {
                     iv.setVisibility(View.VISIBLE);
                     tagBean.setIscheck(true);
                     list3.add(tagBean);
                 }

            }

            @Override
            public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                return false;
            }
        });
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
        rec4.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initrecycylerviewReport() {


        tag=new CommonAdapter<ReportMenu.DataBean.TagItemBean>(this,tags,R.layout.category_recyclerview_item) {
            @Override
            public void convert(ViewHolder viewHolder, ReportMenu.DataBean.TagItemBean tagItemBean) {
                      TextView textView= viewHolder.getView(R.id.category_item_tv);
                      textView.setText(tagItemBean.getTitle());
                      ImageView iv=viewHolder.getView(R.id.itemtishi);
            }
        };
        rec1.setAdapter(tag);
               tag.setOnItemClickListener(new OnItemClickListener() {
                   @Override
                   public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                       ImageView iv= (ImageView) view.findViewById(R.id.itemtishi);
                       ReportMenu.DataBean.TagItemBean bean=tags.get(i);
                           if(bean.isIscheck()){
                               iv.setVisibility(View.GONE);
                               bean.setIscheck(false);
                               sx.getTtags().remove(bean);
                            }else {
                               iv.setVisibility(View.VISIBLE);
                               bean.setIscheck(true);
                               sx.getTtags().add(bean);
                           }
                   }

                   @Override
                   public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                       return false;
                   }
               });

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
               ImageView iv= (ImageView) view.findViewById(R.id.itemtishi);
               ReportMenu.DataBean.MenuItemBean bean=ones.get(i);
               if(bean.isIscheck()){
                   iv.setVisibility(View.GONE);
                   bean.setIscheck(false);
                   sx.getOnetag().remove(bean);
               }else {
                   iv.setVisibility(View.VISIBLE);
                   bean.setIscheck(true);
                   sx.getOnetag().add(bean);
               }
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
      two.setOnItemClickListener(new OnItemClickListener() {
          @Override
          public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
              ImageView iv= (ImageView) view.findViewById(R.id.itemtishi);
              ReportMenu.DataBean.MenuItem1Bean bean=twos.get(i);
              if(bean.isIscheck()){
                  iv.setVisibility(View.GONE);
                  bean.setIscheck(false);
                  sx.getTwotag().remove(bean);
              }else {
                  iv.setVisibility(View.VISIBLE);
                  bean.setIscheck(true);
                  sx.getTwotag().add(bean);
              }
          }

          @Override
          public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
              return false;
          }
      });


      rec4.setAdapter(adapter);

      adaptera =new BaseRecyclerAdapter<ReportMenu.DataBean.AllsonIndustryBean>(alldanwei) {
          @Override
          public int getItemLayoutId(int viewType) {
              return R.layout.category_recyclerview_item;
          }

          @Override
          public void bindData(BaseRecyclerViewHolder holder, int position, ReportMenu.DataBean.AllsonIndustryBean item) {
              TextView textView=  holder.getTextView(R.id.category_item_tv);
              textView.setText(item.getTitle());
              ImageView iv=holder.getImageView(R.id.itemtishi);
              if(item.isIscheck()){
                  iv.setVisibility(View.VISIBLE);
              }else {
                  iv.setVisibility(View.GONE);
              }
          }
      };
      adaptera.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(Object Data, View view, int position) {

                    ReportMenu.DataBean.AllsonIndustryBean bean = alldanwei.get(position);

                    if (bean.isIscheck()) {
                        bean.setIscheck(false);
                        sx.getDanwei().remove(bean);
                    } else {
                        bean.setIscheck(true);
                        sx.getDanwei().add(bean);
                    }
                    adaptera.notifyItemChanged(position);
          }

          @Override
          public void onItemLongClick(Object Data, View view, int position) {

          }
      });
      rec4.setAdapter(adaptera);

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
        if(bean.getData().getMenu_item()!=null&&bean.getData().getMenu_item().size()>0){
            ones.clear();
            ones.addAll(bean.getData().getMenu_item());
            one.notifyDataSetChanged();
        }
        if(bean.getData().getMenu_item1()!=null&&bean.getData().getMenu_item1().size()>0){
            twos.clear();
            twos.addAll(bean.getData().getMenu_item1());
            two.notifyDataSetChanged();
        }
       if(bean.getData().getTag_item()!=null&&bean.getData().getTag_item().size()>0){
           tags.clear();
           tags.addAll(bean.getData().getTag_item());
           tag.notifyDataSetChanged();
       }
       if(isnull(bean.getData().getAllsonIndustry())){
           alldanwei.clear();
           alldanwei.addAll(bean.getData().getAllsonIndustry());
           adaptera.notifyDataSetChanged();
       }
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

    //检查列表，避免空指针
     public<T> boolean isnull(List<T> list){
        if(list!=null&&list.size()>0){
            return  true;
        }
         return  false;
     }
}
