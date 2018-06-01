package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
import com.qingyii.hxtz.bean.ReportBean;
import com.qingyii.hxtz.view.RoundedImageView;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.entity.FooterBean;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WorkParkDetailsActivity;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.qingyii.hxtz.zhf.http.Urlutil;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    String time="null";



     private long  dotime(String time){
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
         Date date ;
         try {
              date=sdf.parse(time);
             return  date.getTime();
         } catch (ParseException e) {
             e.printStackTrace();
         }

           return -1;
     }

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
        // fiveOne=dotime(five_one);
         if(adapter==null){
            adapter=new HeaderFooterAdapter(new BaseMulTypeAdapter<ReportBean.DataBean.AllactivityBean>(mApplication,list){
                @Override
                public void convert(ViewHolder holder, ReportBean.DataBean.AllactivityBean allactivityBean) {
                    super.convert(holder, allactivityBean);

                    TextView tv1=holder.getView(R.id.tv_name);
                    tv1.setText(allactivityBean.getA_name());
                    TextView tv2=holder.getView(R.id.workpark_fbtime);
                    tv2.setText(allactivityBean.getCreated_at());
                    TextView  issh=holder.getView(R.id.workpark_issh);
                    if(allactivityBean.getIs_approve()==0){
                        issh.setText("未审核");
                        issh.setTextColor(mApplication.getResources().getColor(R.color.allStyles ));
                    } else if(allactivityBean.getIs_approve()==1){
                        issh.setText("已审核");
                        issh.setTextColor(mApplication.getResources().getColor(R.color.notifyListReadColor));
                    }
                   TextView issc=holder.getView(R.id.istj);
                    if(allactivityBean.getIs_article()==0){
                      issc.setText("未推荐");
                      issc.setTextColor(mApplication.getResources().getColor(R.color.workparktext));
                    } else if(allactivityBean.getIs_article()==1){
                        issc.setText("已推荐");
                        issc.setTextColor(mApplication.getResources().getColor(R.color.allStyles));
                    }else if(allactivityBean.getIs_article()==2){
                        issc.setText("已收录");
                        issc.setTextColor(mApplication.getResources().getColor(R.color.notifyListReadColor));
                    }

                   if (allactivityBean.getItemLayoutId()==R.layout.workpark_newlayout1){
                       RoundedImageView iv=holder.getView(R.id.iv);
                       String murl;
                       if(allactivityBean.getImgs().get(0).contains("http")){
                           murl=allactivityBean.getImgs().get(0);
                       } else {
                           murl= Urlutil.baseimgurl+ allactivityBean.getImgs().get(0);
                       }
                    mImageLoader.loadImage(mApplication, GlideImageConfig.builder().imageView(iv).errorPic(R.mipmap.error_default).url(murl).build());
                   iv.setCornerRadius(12f);

                   }

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(mAppManager.getCurrentActivity(), WorkParkDetailsActivity.class);
                            intent.putExtra("actid",allactivityBean.getId());
                            mAppManager.startActivity(intent);
                        }
                    });

                }

            }
            );

        }
        mRootView.setRecyclerviewAdapter(adapter);



    }
     ArrayList<ReportBean.DataBean.AllactivityBean> list =new ArrayList<>();
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
                .subscribe(new ErrorHandleSubscriber<ReportBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ReportBean workParkitembean) {
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
                 .subscribe(new ErrorHandleSubscriber<ReportBean>(mErrorHandler) {
                     @Override
                     public void onNext(@NonNull ReportBean workParkitembean) {
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
