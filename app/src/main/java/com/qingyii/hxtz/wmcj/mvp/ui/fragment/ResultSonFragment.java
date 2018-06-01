package com.qingyii.hxtz.wmcj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerResultSonComponent;
import com.qingyii.hxtz.wmcj.di.module.ResultSonModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Resultbean;
import com.qingyii.hxtz.wmcj.mvp.presenter.ResultSonPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.ResultsonAdapter;
import com.qingyii.hxtz.zhf.Util.HintUtil;

import java.util.ArrayList;

/**
 * Created by zhf on 2018/3/26.
 */

public class ResultSonFragment  extends BaseFragment<ResultSonPresenter> implements WMCJContract.ResultSonView{
    Resultbean resultbean;
    TextView title,getscore,score,postion1,allpostion1,postion2,allpostion2;
    TextView name1,name2;
    Button back;
    RecyclerView recyclerView;


    ArrayList<Resultbean.DataBean.BrothindustryBean> list=new ArrayList<>();
    private ResultsonAdapter adapter;

    int  librarySystem;
    int industryid=-999;

    public void setIndustryid(int industryid) {
        this.industryid = industryid;
    }

    public void setLibrarySystem(int librarySystem) {
        this.librarySystem = librarySystem;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerResultSonComponent.builder()
                .appComponent(appComponent)
                .resultSonModule(new ResultSonModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.resultsonfragment,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
            mPresenter.getResultData(librarySystem,industryid);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        title= (TextView) view.findViewById(R.id.resultsonname);
        getscore= (TextView) view.findViewById(R.id.getscore);

        score= (TextView) view.findViewById(R.id.score);

        postion1= (TextView) view.findViewById(R.id.getadnwei);
        postion2= (TextView) view.findViewById(R.id.danwei);
        recyclerView= (RecyclerView) view.findViewById(R.id.resultsonrecyc);
        adapter=new ResultsonAdapter(list,getActivity() );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
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

    @Override
    public void getResultDataSuccess(Resultbean resultbean) {
        title.setText(resultbean.getData().getMy().getName());
        getscore.setText(String.valueOf(resultbean.getData().getMy().getMyscore()));
        score.setText("/"+resultbean.getData().getMy().getScore());
      /*  name1.setText(resultbean.getData().getParentindustry().getName());
        postion1.setText(String.valueOf(resultbean.getData().getParentindustry().getOrder()));
        allpostion1.setText("/"+resultbean.getData().getParentindustry().getTotle());
      */
      // name1.setText("在"+resultbean.getData().getTopIndustry().getName()+"的排名");
        postion1.setText(String.valueOf(resultbean.getData().getTopIndustry().getOrder()));
        postion2.setText("/"+resultbean.getData().getTopIndustry().getTotle());
        list.clear();
        list.addAll(resultbean.getData().getBrothindustry());
        adapter.notifyDataSetChanged();
    }
}
