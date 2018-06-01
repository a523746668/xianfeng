package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineBean;
import com.qingyii.hxtz.wmcj.mvp.model.entity.FooterBean;

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

@ActivityScope
public class ExaminePresenter extends BasePresenter<WMCJContract.ExamineModel,WMCJContract.ExamineView> {

    private RxErrorHandler mErrorHandler;
    private ImageLoader mImageLoader;
    private Application mApplication;
    private AppManager mAppManager;

    private BaseRecyclerAdapter<ExamineBean.DataBean.FileBean> adapter=null;

    private ArrayList<ExamineBean.DataBean.FileBean> list =new ArrayList<>();
    @Inject
    public ExaminePresenter(WMCJContract.ExamineModel model, WMCJContract.ExamineView rootView, RxErrorHandler mErrorHandler, ImageLoader mImageLoader, Application mApplication, AppManager mAppManager) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mImageLoader = mImageLoader;
        this.mApplication = mApplication;
        this.mAppManager = mAppManager;
    }

    public void initadapter(){
          if(adapter==null){
            adapter=new BaseRecyclerAdapter<ExamineBean.DataBean.FileBean>(list) {
                @Override
                public int getItemLayoutId(int viewType) {
                    return R.layout.draftlist;
                }

                @Override
                public void bindData(BaseRecyclerViewHolder holder, int position, ExamineBean.DataBean.FileBean item) {
                    holder.getTextView(R.id.draftrecycname).setText(item.getFilename());
                    holder.getTextView(R.id.draftrecyctime).setVisibility(View.GONE);
                }
            };
            adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Object Data, View view, int position) {

                }

                @Override
                public void onItemLongClick(Object Data, View view, int position) {

                }
            });}
            mRootView.setadapter(adapter);

    }


   public void getExamineBean(){
        mModel.getExamineBean()
                .subscribeOn(Schedulers.io())
                . retryWhen(new RetryWithDelay(1,1)) //重试次数
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mRootView.showLoading();


                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mRootView.hideLoading();

                    }
                })
                .subscribe(new ErrorHandleSubscriber<ExamineBean>(mErrorHandler) {
                    @Override
                    public void onNext(ExamineBean examineBean) {
                           mRootView.getExamineBeanSuccess(examineBean);
                           list.clear();
                           list.addAll(examineBean.getData().getDoclist());
                           adapter.notifyDataSetChanged();
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
    }
}
