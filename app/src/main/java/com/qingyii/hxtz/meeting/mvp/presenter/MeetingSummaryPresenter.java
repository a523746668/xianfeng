package com.qingyii.hxtz.meeting.mvp.presenter;

import android.app.Application;
import android.widget.Toast;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.base.mvp.model.entity.CommonData;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.meeting.di.module.entity.MeetingList;
import com.qingyii.hxtz.meeting.di.module.entity.MeetingSummary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MultipartBody;


@ActivityScope
public class MeetingSummaryPresenter extends BasePresenter<CommonContract.MeetingSummaryModel, CommonContract.MeetingSummaryView> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<MeetingList.DataBean> meetingLists = new ArrayList<>();
    private int lastMeetingListId;
    private boolean isFirst = true;
    private int preEndIndex;
    private BaseRecyclerAdapter<MeetingList.DataBean> mAdapter;
    private String direction = "lt";
    private List<String> imgUrl = new ArrayList<>();

    @Inject
    public MeetingSummaryPresenter(CommonContract.MeetingSummaryModel model, CommonContract.MeetingSummaryView rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void requestMeetingLists(MeetingSummary data) {

        mModel.requestMeetingSummary(data)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> mRootView.showLoading())//显示上拉刷新的进度条
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())//隐藏上拉刷新的进度条
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<CommonData<String>>(mErrorHandler) {
                    @Override
                    public void onNext(CommonData<String> commonData) {
                        if(commonData.getError_code()==0) {
                            mRootView.submitFinish();
                        }else{
                            Toast.makeText(mApplication, commonData.getError_msg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void uploadPic(List<MultipartBody.Part> parts, String id) {
        imgUrl.clear();
        singleUploadPic(parts, parts.get(0), id);
    }

    private void singleUploadPic(List<MultipartBody.Part> partList, MultipartBody.Part part, String id) {
        mModel.uploadPic(part, id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())//显示上拉刷新的进度条
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
//                    if (partList.size() == 1)
//                        mRootView.hideLoading();
                })//隐藏上拉刷新的进度条
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<CommonData<String>>(mErrorHandler) {
                    @Override
                    public void onNext(CommonData<String> commonData) {
                        imgUrl.add("\""+commonData.getData()+"\"");
                        partList.remove(0);
                        if (partList.size() > 0) {
                            singleUploadPic(partList, partList.get(0), id);
                        } else {
                            mRootView.uploadFinish(imgUrl);
                            mRootView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        mRootView.hideLoading();
                    }
                });
    }
}