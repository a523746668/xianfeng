package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.commonadapter.rv.mul.BaseMulTypeAdapter;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkitembean;
import com.qingyii.hxtz.wmcj.mvp.model.entity.FooterBean;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WorkParkDetailsActivity;
import com.zhf.Util.HintUtil;
import com.zhf.http.Urlutil;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by zhf on 2018/4/4.
 */
@ActivityScope
public class ReportingPresenter extends BasePresenter<WMCJContract.WorkParkModel,WMCJContract.WorkParkView> {
    private RxErrorHandler mErrorHandler;
    private ImageLoader mImageLoader;
    private Application mApplication;
    private AppManager mAppManager;

    HeaderFooterAdapter adapter;
    private ArrayList<WorkParkitembean.DataBean.AllactivityBean> list =new ArrayList<>();
    String time="null";
    @Inject
    public ReportingPresenter(WMCJContract.WorkParkModel model, WMCJContract.WorkParkView rootView, RxErrorHandler mErrorHandler, ImageLoader mImageLoader, Application mApplication, AppManager mAppManager) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mImageLoader = mImageLoader;
        this.mApplication = mApplication;
        this.mAppManager = mAppManager;
    }

    public void clearhead(){
        adapter.clearHeaderView();
        adapter.notifyDataSetChanged();
    }

    public void initadapter() {
        if(adapter==null){
            adapter=new HeaderFooterAdapter(new BaseMulTypeAdapter<WorkParkitembean.DataBean.AllactivityBean>(mApplication,list){
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
                            Intent intent=new Intent(mAppManager.getCurrentActivity(), WorkParkDetailsActivity.class);
                            intent.putExtra("actid",allactivityBean.getId());
                            mAppManager.startActivity(intent);
                        }
                    });
                    switch(allactivityBean.getItemLayoutId()){
                        case R.layout.workpark_content_layout1:
                            ImageView iv1=holder.getView(R.id.workpark_lv1_iv);
                            String url1= Urlutil.baseimgurl+ allactivityBean.getImgs().get(0);
                            Log.i("tmdurl",url1);
                            mImageLoader.loadImage(mApplication, GlideImageConfig.builder().url(url1).errorPic(R.mipmap.error_default)
                                    .imageView(iv1).build());

                            break;
                        case R.layout.workpark_content_layout2:
                            ImageView  imageView1=holder.getView(R.id.workpark_lv2_iv1);
                            ImageView  imageView2=holder.getView(R.id.workpark_lv2_iv2);
                            ImageView  imageView3=holder.getView(R.id.workpark_lv2_iv3);
                            mImageLoader.loadImage(mApplication,GlideImageConfig.builder().url(Urlutil.baseimgurl+allactivityBean.getImgs().get(0))
                                    .errorPic(R.mipmap.error_default).imageView(imageView1).build());
                            mImageLoader.loadImage(mApplication,GlideImageConfig.builder().url(Urlutil.baseimgurl+allactivityBean.getImgs().get(1))
                                    .errorPic(R.mipmap.error_default).imageView(imageView2).build());
                            mImageLoader.loadImage(mApplication,GlideImageConfig.builder().url(Urlutil.baseimgurl+allactivityBean.getImgs().get(2))
                                    .errorPic(R.mipmap.error_default).imageView(imageView3).build());

                            break;
                        case R.layout.alltext:

                            break;
                    }

                }

            }
            );

        }
        mRootView.setRecyclerviewAdapter(adapter);



    }

    public void  getReportdata(){
       initadapter();
        mModel.getAlreadyWork("null")
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1,1)) //重试次数
               .doOnSubscribe(new Consumer<Disposable>() {
                   @Override
                   public void accept(@NonNull Disposable disposable) throws Exception {
                           mRootView.doRefresh();
                   }
               })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                         mRootView.finishRefresh();
                    }
                })
                .subscribe(new ErrorHandleSubscriber<WorkParkitembean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull WorkParkitembean workParkitembean) {
                      if(workParkitembean.getData().getAllactivity().size()<=0){
                          mRootView.getdataerror(null);
                      }
                        list.clear();
                        list.addAll(workParkitembean.getData().getAllactivity());
                        time=workParkitembean.getData().getCreated_at();
                        mRootView.showMessage("ok");
                        adapter.notifyDataSetChanged();
                    }
                });
    }

     public void getReportMore(){
         mModel.getAlreadyWork(time)
                 .subscribeOn(Schedulers.io())
                 . retryWhen(new RetryWithDelay(1,1)) //重试次数
                 .doOnSubscribe(new Consumer<Disposable>() {
                     @Override
                     public void accept(@NonNull Disposable disposable) throws Exception {
                            mRootView.showLoading();
                         FooterBean footbean=new FooterBean();
                         adapter.addFooterView(footbean);
                         adapter.notifyDataSetChanged();
                     }
                 })
                 .subscribeOn(AndroidSchedulers.mainThread())
                 .observeOn(AndroidSchedulers.mainThread())
                 .doAfterTerminate(new Action() {
                     @Override
                     public void run() throws Exception {
                        mRootView.hideLoading();
                         adapter.clearFooterView();
                         adapter.notifyDataSetChanged();
                     }
                 })
                 .subscribe(new ErrorHandleSubscriber<WorkParkitembean>(mErrorHandler) {
                     @Override
                     public void onNext(@NonNull WorkParkitembean workParkitembean) {
                         if(workParkitembean.getData().getAllactivity()==null|workParkitembean.getData().getAllactivity().size()<=0){
                             HintUtil.showtoast(mApplication,"已经是最后一页");
                         }
                         else  {
                             int i = list.size();
                             list.addAll(workParkitembean.getData().getAllactivity());
                             time =workParkitembean.getData().getCreated_at();
                             adapter.notifyDataSetChanged();
                             //recyclerView.scrollToPosition(i);
                         }
                     }
                 });
     }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mImageLoader=null;
        mAppManager=null;
        mApplication=null;
        list.clear();
        list=null;

    }

    //动态设置drawableLeft大小
    private void setDrawLeft(Rect bond, int resid, TextView tv) {

        Drawable drawable = mApplication.getResources().getDrawable(resid);
        drawable.setBounds(bond);
        tv.setCompoundDrawables(drawable, null, null, null);

    }
}
