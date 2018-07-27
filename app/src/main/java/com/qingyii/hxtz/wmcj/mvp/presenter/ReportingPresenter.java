package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.mcxtzhang.commonadapter.rv.HeaderRecyclerAndFooterWrapperAdapter;
import com.mcxtzhang.commonadapter.rv.IHeaderHelper;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.commonadapter.rv.mul.BaseMulTypeAdapter;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.ReportBean;
import com.qingyii.hxtz.my.My_StudyActivity;
import com.qingyii.hxtz.view.RoundedImageView;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Common;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportDelete;
import com.qingyii.hxtz.wmcj.mvp.model.bean.shuaixuan;
import com.qingyii.hxtz.wmcj.mvp.model.entity.FooterBean;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WorkParkDetailsActivity;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.qingyii.hxtz.zhf.http.Urlutil;

import org.androidannotations.annotations.Bean;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private boolean isadmine=false;


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

                    TextView fbdw=holder.getView(R.id.workpark_fbdw);
                    fbdw.setText(allactivityBean.getIndname());

                    Button delete=holder.getView(R.id.report_delete);
                    delete.setText("删除");
                    delete.setTextColor(delete.getContext().getResources().getColor(R.color.green1));
                    if(allactivityBean.getA_org_id()== Global.userid&&isadmine){
                       delete.setVisibility(View.VISIBLE);
                    } else {
                        delete.setVisibility(View.GONE);
                    }

                   delete.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           AlertDialog.Builder builder = new AlertDialog.Builder(mAppManager.getCurrentActivity());
                           builder.setTitle("提示")
                                   .setMessage("你确定要执行删除操作吗？删除之后不可恢复。")
                                   .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog,
                                                           int which) {

                                           DeleteOrback(String.valueOf(allactivityBean.getId()),String.valueOf(allactivityBean.getA_org_id()),getPosition(holder));
                                           dialog.dismiss();
                                       }
                                   })
                                   .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog,
                                                           int which) {
                                           dialog.dismiss();
                                       }
                                   })
                                   .show();

                       }
                   });
                    if(allactivityBean.getIs_approve()==0){
                        issh.setText("未审核");
                        issh.setTextColor(mApplication.getResources().getColor(R.color.allStyles ));
                    } else if(allactivityBean.getIs_approve()==1){
                        issh.setText("已审核");
                        issh.setTextColor(mApplication.getResources().getColor(R.color.notifyListReadColor));
                    }
                   TextView issc=holder.getView(R.id.istj);
                    if(allactivityBean.getIs_article()==0){
                      issc.setText("未推荐至首页");
                      issc.setTextColor(mApplication.getResources().getColor(R.color.workparktext));
                    } else if(allactivityBean.getIs_article()==1){
                        issc.setText("已推荐至首页");
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
                    mImageLoader.loadImage(mApplication, GlideImageConfig.builder().imageView(iv).errorPic(R.mipmap.error_default).placeholder(R.mipmap.error_default).url(murl).build());
                    iv.setCornerRadius(12f);
                   }

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(mAppManager.getCurrentActivity(), My_StudyActivity.class);
                            intent.putExtra("actid",allactivityBean.getId());
                            mAppManager.startActivity(intent);
                        }
                    });
                }});
        }
        mRootView.setRecyclerviewAdapter(adapter);
    }
     ArrayList<ReportBean.DataBean.AllactivityBean> list =new ArrayList<>();
    public void  getReportdata(){
       initadapter();
        mModel.getAlreadyWork("null")
                .subscribeOn(Schedulers.io())
              //  .retryWhen(new RetryWithDelay(1,1)) //重试次数
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
                      if(workParkitembean.getData().getAllactivity()==null||workParkitembean.getData().getAllactivity().size()<=0){
                          mRootView.getdataerror(null);
                          return;
                      }
                        isadmine=workParkitembean.getData().isIsadmin();
                        list.clear();
                        list.addAll(workParkitembean.getData().getAllactivity());
                        Iterator<ReportBean.DataBean.AllactivityBean> stuIter = list.iterator();
                        while(stuIter.hasNext()){
                           ReportBean.DataBean.AllactivityBean bean=stuIter.next();
                           if(Global.userid!=bean.getA_org_id()&&bean.getIs_delete()==1){
                               stuIter.remove();
                           }
                       }
                        Log.i("tmditerator",list.size()+"----");
                        time=workParkitembean.getData().getCreated_at();
                        mRootView.showMessage("ok");
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                       mRootView.getdataerror(null);
                    }
                });
    }

     public void getReportMore(){
         mRootView.setflag(false);
         mModel.getAlreadyWork(time)
                 .subscribeOn(Schedulers.io())
                 . retryWhen(new RetryWithDelay(1,1)) //重试次数
                 .doOnSubscribe(new Consumer<Disposable>() {
                     @Override
                     public void accept(@NonNull Disposable disposable) throws Exception {
                         // mRootView.showLoading();
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
                             List<ReportBean.DataBean.AllactivityBean> datas=workParkitembean.getData().getAllactivity();
                             Iterator<ReportBean.DataBean.AllactivityBean> stuIter = datas.iterator();
                             while(stuIter.hasNext()){
                                 ReportBean.DataBean.AllactivityBean bean=stuIter.next();
                                 if(Global.userid!=bean.getA_org_id()&&bean.getIs_delete()==1){
                                     stuIter.remove();
                                 }
                             }
                             Log.i("tmditerator",datas.size()+"----");
                             list.addAll(datas);
                             time =workParkitembean.getData().getCreated_at();
                             adapter.notifyDataSetChanged();
                             //recyclerView.scrollToPosition(i);
                         }
                     }
                     @Override
                     public void onError(Throwable e) {
                         super.onError(e);

                     }
                 });
     }
     public void getReportSX(shuaixuan SX){
        if(SX==null){
            return;
        }
        String indtagid=chuli(SX.getTtags());
        String onetask=chuli(SX.getOnetag());
        String twotask=chuli(SX.getTwotag());
        String industryarray=chuli(SX.getDanwei());
        mModel.getReportSX(indtagid,onetask,twotask,industryarray)
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
                     Iterator<ReportBean.DataBean.AllactivityBean> stuIter = list.iterator();
                     while(stuIter.hasNext()){
                         ReportBean.DataBean.AllactivityBean bean=stuIter.next();
                         if(Global.userid!=bean.getA_org_id()&&bean.getIs_delete()==1){
                             stuIter.remove();
                         }
                     }
                     Log.i("tmditerator",list.size()+"----");
                     time=workParkitembean.getData().getCreated_at();
                     mRootView.showMessage("ok");
                     adapter.notifyDataSetChanged();
                 }

                 @Override
                 public void onError(Throwable e) {
                     super.onError(e);
                     HintUtil.stopdialog();
                 }
             });

     }
      public void getReportSXMore(shuaixuan sx){
          String indtagid=chuli(sx.getTtags());
          String onetask=chuli(sx.getOnetag());
          String twotask=chuli(sx.getTwotag());
          String industryarray=chuli(sx.getDanwei());
          Map<String ,String> map=new HashMap<>();
          map.put("indtagid",indtagid);
          map.put("onetask",onetask);
          map.put("twotask",twotask);
          map.put("industryarray",industryarray);
          map.put("created_at", time);
          map.put("direction", "lt");
          map.put("time",time);
          map.put("system",Global.system);
          Log.i("tmdtime",time);
          mModel.getReportSXMore(map)
                  .subscribeOn(Schedulers.io())
                  .retryWhen(new RetryWithDelay(1,1)) //重试次数
                  .doOnSubscribe(new Consumer<Disposable>() {
                      @Override
                      public void accept(@NonNull Disposable disposable) throws Exception {
                          // mRootView.showLoading();
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
                              List<ReportBean.DataBean.AllactivityBean> datas=workParkitembean.getData().getAllactivity();
                              Iterator<ReportBean.DataBean.AllactivityBean> stuIter = datas.iterator();
                              while(stuIter.hasNext()){
                                  ReportBean.DataBean.AllactivityBean bean=stuIter.next();
                                  if(Global.userid!=bean.getA_org_id()&&bean.getIs_delete()==1){
                                      stuIter.remove();
                                  }
                              }
                              Log.i("tmditerator",datas.size()+"----");
                              list.addAll(datas);
                              time =workParkitembean.getData().getCreated_at();
                              adapter.notifyDataSetChanged();
                              //recyclerView.scrollToPosition(i);
                              }
                      }
                      @Override
                      public void onError(Throwable e) {
                          super.onError(e);
                      }
                  });
      }


     public <T extends Common> String chuli(List<T> commons){
         StringBuffer sb=new StringBuffer();
        for(int i=0;i<commons.size();i++){
              if(i!=commons.size()-1){
                  sb.append(commons.get(i).getId()+",");
              }else {
                  sb.append(commons.get(i).getId());
              }
         }
         Log.i("tmdchuli",sb.toString()+"-----");
         return  sb.toString();
     }

     //行业组ID为6
    public void getWorkItem(String system_id){
        initadapter();
         mModel.getallReport(system_id)
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

    public void getworkitemmore(String system_id){
        mModel.getallReportMore(system_id,time)
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
                        mRootView.setflag(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.setflag(true);
                    }
                });
    }

    //删除或者恢复
    public void DeleteOrback(String actid,String a_org_id,int postion){
        mModel.deleteReport(actid,a_org_id)
                .subscribeOn(Schedulers.io())
                . retryWhen(new RetryWithDelay(1,1)) //重试次数
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                      HintUtil.showdialog(mAppManager.getCurrentActivity());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                       HintUtil.stopdialog();
                    }
                })
                .subscribe(new ErrorHandleSubscriber<ReportDelete>(mErrorHandler) {
                    @Override
                    public void onNext(ReportDelete reportDelete) {
                           if (reportDelete.getError_code()==0){
                              list.remove(postion);
                              adapter.notifyDataSetChanged();
                           } else {
                               UiUtils.snackbarText(reportDelete.getError_msg()) ;
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

    public boolean islistnull(List<Object> list){
         if(list==null&&list.size()<=0){
             return  true;
         }
         return  false;
    }

}
