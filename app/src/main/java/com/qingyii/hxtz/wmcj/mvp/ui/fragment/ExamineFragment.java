package com.qingyii.hxtz.wmcj.mvp.ui.fragment;


import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerExamineComponent;
import com.qingyii.hxtz.wmcj.di.module.ExamineModule;
import com.qingyii.hxtz.wmcj.di.module.TaskModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineSelectMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.presenter.ExaminePresenter;
import com.qingyii.hxtz.wmcj.mvp.presenter.TaskPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCJcategoryActivity;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.zhy.autolayout.AutoLinearLayout;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

//考核首页
public class ExamineFragment extends BaseFragment<ExaminePresenter> implements WMCJContract.ExamineView,WMCJContract.TaskView {

    @BindView(R.id.toolbar_back_layout)
    AutoLinearLayout back;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.toolbar_right_tv)
    TextView barright;

    @BindView(R.id.toolbar_right_layout)
    AutoLinearLayout rightlayout;

    @BindView(R.id.examine_title)
    TextView examine_title;

    @BindView(R.id.examine_company)
    TextView examine_company;

    @BindView(R.id.examine_content)
    TextView examine_content;

    @BindView(R.id.examine_files)
    RecyclerView recyclerView;

    @ActivityScope
    @Inject
    TaskPresenter taskPresenter;

    @BindView(R.id.examine_finaly)
    TextView finaly;

    @BindView(R.id.visibleview)
    NestedScrollView visbleview;

    @BindView(R.id.empty_view)
    AutoLinearLayout emtview;

    @BindView(R.id.examine_duixiang)
    TextView obj;

    @BindView(R.id.paobu)
    ImageView paobu;

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerExamineComponent.builder()
        .appComponent(appComponent)
        .examineModule(new ExamineModule(this))
        .taskModule(new TaskModule(this))
        .build()
        .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wmcj_examine,null,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    inittoolbar();

    mPresenter.initadapter();
   // mPresenter.getExamineBean();
    taskPresenter.getTitle(true);
    }

    private void inittoolbar() {
      title.setText("考核首页");
      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              getActivity().finish();
          }
      });
      barright.setText("切换考核");
      barright.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(getActivity(), WMCJcategoryActivity.class);
              intent.putExtra("wmcj","Examine");
              startActivity(intent);
          }
      });
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showLoading() {
        HintUtil.showdialog(getContext());
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

    @BindView(R.id.examine_company1)
    TextView examine_company1;

    @BindView(R.id.pb)
    ZzHorizontalProgressBar bar;

    @BindView(R.id.examine_total)
    TextView examine_total;

    @BindView(R.id.examine_processnum)
    TextView examine_processnum;

    @BindView(R.id.examine_processnum100)
    TextView examine_processnum100;


    @Override
    public void getExamineBeanSuccess(ExamineBean bean) {
          emtview.setVisibility(View.GONE);
          visbleview.setVisibility(View.VISIBLE);
          examine_title.setText(bean.getData().getTitle());
          examine_company.setText(bean.getData().getSender());
          examine_content.setText(bean.getData().getContent());
          examine_company1.setText(bean.getData().getCompanyname());
          bar.setProgress((int) bean.getData().getPercent());
          examine_total.setText(String.valueOf(bean.getData().getTotal()));
          examine_processnum.setText(String.valueOf(bean.getData().getComplete()));
          examine_processnum100.setText(bean.getData().getPercent()+"%");
          finaly.setText("注：数据统计截至"+bean.getData().getEnddate()+",查看考核情况，请登录系统后台" );
          obj.setText(bean.getData().getCompleteobj());
          int left= 518*bean.getData().getPercent()/100>=518 ? 518 : (int) (518 * bean.getData().getPercent()/100);
          paobu.setPadding(left,0,0,0);
    }

    @Override
    public void setadapter(BaseRecyclerAdapter<ExamineBean.DataBean.FileBean> adapter) {
             recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
             recyclerView.setAdapter(adapter);
    }

    @Override
    public void getdataerror() {
        visbleview.setVisibility(View.GONE);
        emtview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.EXAMINE)
    public  void change(Message msg){
        if(msg.obj==null){
            mPresenter.getExamineBean(msg.arg1);
        } else {
            mPresenter.getExamineBean(msg.arg1,msg);
        }
    }

    @Override
    public void setRecyclerviewAdapter(BaseRecyclerAdapter adapter) {

    }

    @Override
    public void getdatano() {
    visbleview.setVisibility(View.GONE);
    emtview.setVisibility(View.VISIBLE);
    }

    @Override
    public void getdatasuccess(ArrayList<TaskListSonFragment> list) {

    }
     private int system_id;
    @Override
    public void gettitile(ArrayList<TaskTitlebean.DataBean.LibsystemBean> list) {
             system_id=list.get(list.size()-1).getId();
             mPresenter.getExamineBean(system_id);
    }
}
