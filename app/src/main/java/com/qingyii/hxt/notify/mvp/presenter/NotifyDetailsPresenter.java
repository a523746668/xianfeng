package com.qingyii.hxt.notify.mvp.presenter;

import android.app.Application;

import com.github.barteksc.pdfviewer.util.FileUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.mvp.model.entity.CommonData;
import com.qingyii.hxt.base.utils.RxUtils;
import com.qingyii.hxt.base.utils.WindowUtils;
import com.qingyii.hxt.notify.mvp.model.entity.NotifyList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static com.qingyii.hxt.base.mvp.model.entity.CommonData.SUCCESS;


@ActivityScope
public class NotifyDetailsPresenter extends BasePresenter<CommonContract.NotifyDetailsModel, CommonContract.NotifyDetailsView> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<NotifyList.DataBean> mNotices = new ArrayList<>();

    @Inject
    public NotifyDetailsPresenter(CommonContract.NotifyDetailsModel model, CommonContract.NotifyDetailsView rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void requestReadMark(String id) {
        mModel.getReadMark(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<CommonData<String>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CommonData<String> data) {
                        if(data.getData().equals(SUCCESS)){
                            mRootView.UpdateReadStatus();
                        }
                    }
                });
    }
    public void requestSignMark(int id,String message) {
        mModel.getSignMark(id,message)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<CommonData<String>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CommonData<String> data) {
                        if(data.getData().toString().equals(SUCCESS)){
                            mRootView.UpdateSignStatus();
                        }
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

    public void downloadFile(String url, File file, String mimeType) {
        ProgressManager.getInstance().addResponseListener(url, WindowUtils.getDownloadListener());
        mModel.download(url).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(responseBody -> responseBody.byteStream())
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<InputStream>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull InputStream inputStream) {
                        try {
                            FileUtils.writeFile(inputStream, file);
                            FileUtils.openFile(mApplication, file.getPath(), mimeType);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}