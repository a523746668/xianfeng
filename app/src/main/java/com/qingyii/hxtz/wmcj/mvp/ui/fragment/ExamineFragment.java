package com.qingyii.hxtz.wmcj.mvp.ui.fragment;


import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerExamineComponent;
import com.qingyii.hxtz.wmcj.di.module.ExamineModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineSelectMenu;
import com.qingyii.hxtz.wmcj.mvp.presenter.ExaminePresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCJcategoryActivity;
import com.qingyii.hxtz.zhf.Util.HintUtil;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;
import org.w3c.dom.Text;

import butterknife.BindView;
import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

//考核首页
public class ExamineFragment extends BaseFragment<ExaminePresenter> implements WMCJContract.ExamineView {

    @BindView(R.id.toolbar_back)
    Button back;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.toolbar_right_tv)
    TextView barright;

    @BindView(R.id.examine_title)
    TextView examine_title;

    @BindView(R.id.examine_company)
    TextView examine_company;

    @BindView(R.id.examine_content)
    TextView examine_content;

    @BindView(R.id.examine_files)
    RecyclerView recyclerView;

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerExamineComponent.builder()
        .appComponent(appComponent)
        .examineModule(new ExamineModule(this))
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
    mPresenter.getExamineBean();
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
          examine_title.setText(bean.getData().getTitle());
          examine_company.setText(bean.getData().getCompanyname());
          examine_content.setText(bean.getData().getContent());
          examine_company1.setText(bean.getData().getCompanyname());
          bar.setProgress((int) bean.getData().getPercent());
          examine_total.setText("总共完成"+bean.getData().getTotal()+"次");
          examine_processnum.setText("已经完成"+bean.getData().getComplete()+"次");
          examine_processnum100.setText("目标完成度"+bean.getData().getPercent()+"%");
    }

    @Override
    public void setadapter(BaseRecyclerAdapter<ExamineBean.DataBean.FileBean> adapter) {
             recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
             recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.EXAMINE)
    public  void change(ExamineSelectMenu select){

    }
}
