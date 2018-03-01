package com.qingyii.hxt.training.details.schedule.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.utils.RxUtils;
import com.qingyii.hxt.training.details.schedule.di.module.entity.TrainNoticeList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by xubo on 2017/8/9.
 */

public class TrainNoticePresenter extends BasePresenter<CommonContract.TrainingNoticeContractModel, CommonContract.TrainingNoticeContractView> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<TrainNoticeList.DataBean> mLists = new ArrayList<>();
    private BaseRecyclerAdapter<TrainNoticeList.DataBean> mAdapter;
    private String direction = "lt";
    //判断当前是否已经在请求数据

    @Inject
    public TrainNoticePresenter(CommonContract.TrainingNoticeContractModel model, CommonContract.TrainingNoticeContractView rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void requestTrainNoticeLists(int trainId) {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<TrainNoticeList.DataBean>(mLists) {
                @Override
                public int getItemLayoutId(int viewType) {
                    return R.layout.inform_listview_item;
                }

                @Override
                public void bindData(BaseRecyclerViewHolder holder, int position, TrainNoticeList.DataBean item) {
                    holder.setText(R.id.infoer_item_tltle, item.getTitle());
                    holder.setText(R.id.infoer_item_news, item.getAuthor() + "  " + item.getCreated_at());
                }
            };
            mRootView.setAdapter(mAdapter);//设置Adapter
        }


        mModel.getTrainNoticeList(trainId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示上拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    mRootView.hideLoading();//隐藏上拉刷新的进度条
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁

                .subscribe(
                        new ErrorHandleSubscriber<TrainNoticeList>(mErrorHandler) {
                            @Override
                            public void onNext(TrainNoticeList noticeList) {
                                mLists.clear();
                                mAdapter.setData(noticeList.getData());
                            }
                        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}