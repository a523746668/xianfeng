package com.zhf;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.commonadapter.rv.mul.BaseMulTypeAdapter;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxt.wmcj.mvp.model.entity.FooterBean;
import com.qingyii.hxt.wmcj.mvp.ui.WorkParkDetailsActivity;
import com.zhf.Util.HintUtil;
import com.zhf.bean.WorkParkitembean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implpresent.ReportingPresenter;
import com.zhf.present.Implview.ReportingView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


//已上报任务列表
public class ReportingMainActivity extends AppCompatActivity implements ReportingView {

     private Unbinder unbinder;
    @BindView(R.id.workpark_item_recyclerView)
    AutoLoadMoreRecyclerView recyclerView;

    @BindView(R.id.workpark_item_refresh)
    PtrClassicFrameLayout ptr;

    private ArrayList<WorkParkitembean.DataBean.AllactivityBean> list =new ArrayList<>();

    HeaderFooterAdapter adapter;

    ReportingPresenter presenter;

    @BindView(R.id.toolbar_back)
    Button back;

    @BindView(R.id.toolbar_title)
    TextView title;

    private String time;

    @BindView(R.id.empty_view)
    AutoLinearLayout emtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        unbinder= ButterKnife.bind(this);
         initactionbar();
         initview();
         initptr();
    }

    private void initactionbar() {
             back.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                      finish();
                 }
             });
           title.setText("已上报任务列表");
    }

    private void initptr() {
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                 presenter.getreportingdata();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, content, header);
            }
        });
        recyclerView.setOnLoadMoreListener(new AutoLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                if(time!=null){
                    presenter.getreportingdatamore(time);
                }
            }
        });
    }


    private void initview() {
        adapter=new HeaderFooterAdapter(new BaseMulTypeAdapter<WorkParkitembean.DataBean.AllactivityBean>(this,list){
            @Override
            public void convert(ViewHolder holder, WorkParkitembean.DataBean.AllactivityBean allactivityBean) {
                super.convert(holder, allactivityBean);
                setDrawLeft(new Rect(0, 0, 35, 36), R.drawable.workpark_time, holder.getView(R.id.workpark_include_tv_time));
                setDrawLeft(new Rect(0, 0, 27, 27), R.drawable.workpark_comment, holder.getView(R.id.workpark_include_tv_comment));
                setDrawLeft(new Rect(0, 0, 35, 23), R.drawable.workpark_read, holder.getView(R.id.workpark_include_tv_read));
                TextView times= holder.getView(R.id.workpark_include_tv_time);
                times.setText(allactivityBean.getCreated_at());
                TextView name=holder.getView(R.id.workpark_lv_title);
                name.setText(allactivityBean.getA_name());
                TextView readsun=holder.getView(R.id.workpark_include_tv_read);

                readsun.setText(String.valueOf(allactivityBean.getNum_show()));
                TextView comment=holder.getView(R.id.workpark_include_tv_comment);
                comment.setText(String.valueOf(allactivityBean.getNum_comment()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ReportingMainActivity.this, WorkParkDetailsActivity.class);
                        intent.putExtra("actid",allactivityBean.getId());
                        startActivity(intent);
                    }
                });
                switch(allactivityBean.getItemLayoutId()){
                    case R.layout.workpark_content_layout1:
                        ImageView iv1=holder.getView(R.id.workpark_lv1_iv);
                        String url1= Urlutil.baseimgurl+ allactivityBean.getImgs().get(0);
                        Log.i("tmdurl",url1);
                        Glide.with(ReportingMainActivity.this).load(url1).error(R.mipmap.error_default).into(iv1);
                        break;
                    case R.layout.workpark_content_layout2:
                        ImageView  imageView1=holder.getView(R.id.workpark_lv2_iv1);
                        ImageView  imageView2=holder.getView(R.id.workpark_lv2_iv2);
                        ImageView  imageView3=holder.getView(R.id.workpark_lv2_iv3);
                        Glide.with(ReportingMainActivity.this).load(Urlutil.baseimgurl+allactivityBean.getImgs().get(0))
                                .error(R.mipmap.error_default)
                                .into(imageView1);
                        Glide.with(ReportingMainActivity.this).load(Urlutil.baseimgurl+allactivityBean.getImgs().get(1))
                                .error(R.mipmap.error_default)
                                .into(imageView2);
                        Glide.with(ReportingMainActivity.this).load(Urlutil.baseimgurl+allactivityBean.getImgs().get(2))
                                .error(R.mipmap.error_default)
                                .into(imageView3);
                        break;
                    case R.layout.alltext:

                        break;
                }}}
        );
        recyclerView.setAutoLayoutManager(new LinearLayoutManager(this))
                .setAutoItemAnimator(new DefaultItemAnimator())
                .addAutoItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL))
                .setAutoAdapter(adapter);
     presenter=new ReportingPresenter(this,this);
        presenter.getreportingdata();


    }

    //动态设置drawableLeft大小
    private void setDrawLeft(Rect bond, int resid, TextView tv) {

        Drawable drawable = getResources().getDrawable(resid);
        drawable.setBounds(bond);
        tv.setCompoundDrawables(drawable, null, null, null);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
         unbinder.unbind();
         presenter.unbind();
        list.clear();
    }


    @Override
    public void getreportingsuccess(WorkParkitembean workParkitembean) {
           if(workParkitembean.getData().getAllactivity()!=null&&workParkitembean.getData().getAllactivity().size()>0) {
                list.clear();
                list.addAll(workParkitembean.getData().getAllactivity());
                 adapter.notifyDataSetChanged();
                 time=workParkitembean.getData().getCreated_at();
               recyclerView.setVisibility(View.VISIBLE);
               emtview.setVisibility(View.GONE);
           }
    }

    @Override
    public void getreportingmoresuccess(WorkParkitembean workParkitembean) {


        if(workParkitembean.getData().getAllactivity()==null|workParkitembean.getData().getAllactivity().size()<=0){
            HintUtil.showtoast(this,"已经是最后一页");
        }
        else  {
            int i = list.size();
            list.addAll(workParkitembean.getData().getAllactivity());
            time =workParkitembean.getData().getCreated_at();
            adapter.notifyDataSetChanged();
            //recyclerView.scrollToPosition(i);
        }
    }

    @Override
    public void startloadmore() {
        recyclerView.notifyMoreLoaded();
        FooterBean footbean=new FooterBean();

        adapter.addFooterView(footbean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void endloadmore() {
        adapter.clearFooterView();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finishrefresh() {
        ptr.refreshComplete();
    }

    @Override
    public void getdatano() {
        recyclerView.setVisibility(View.GONE);
        emtview.setVisibility(View.VISIBLE);
    }
}
