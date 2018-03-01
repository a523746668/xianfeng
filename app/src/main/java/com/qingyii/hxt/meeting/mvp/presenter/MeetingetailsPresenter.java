package com.qingyii.hxt.meeting.mvp.presenter;

import android.app.Application;
import android.os.Message;

import com.github.barteksc.pdfviewer.util.FileUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.mvp.model.entity.CommonData;
import com.qingyii.hxt.base.utils.RxUtils;
import com.qingyii.hxt.base.utils.WindowUtils;
import com.qingyii.hxt.notify.mvp.model.entity.NotifyList;
import com.squareup.okhttp.ResponseBody;
import com.zhf.Util.FileUtil;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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


@ActivityScope
public class MeetingetailsPresenter extends BasePresenter<CommonContract.MeetingDetailsModel, CommonContract.MeetingDetailsView> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<NotifyList.DataBean> mNotices = new ArrayList<>();

    @Inject
    public MeetingetailsPresenter(CommonContract.MeetingDetailsModel model, CommonContract.MeetingDetailsView rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }
    public void requestFeedback(int id, String status,String rejectresult) {
        mModel.requestFeedback(id,status,rejectresult)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<CommonData<String>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CommonData<String> data) {
                        if(data.getData().toString().equals(CommonData.SUCCESS)){
                            mRootView.UpdateFeedbackStatus(status);
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
                //.map(responseBody -> responseBody.byteStream())
                //.compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<okhttp3.ResponseBody>(mErrorHandler) {


                    @Override
                    public void onNext(@NonNull okhttp3.ResponseBody responseBody) {
                        try {
                            writetofile(file,responseBody);
                            FileUtils.openFile(mApplication,file.getPath(),FileUtils.getMIMEType(file.getPath()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

     public void writetofile(File file , okhttp3.ResponseBody responseBody) throws IOException {
         if (!file.getParentFile().exists())
             file.getParentFile().mkdirs();

         if (file != null && file.exists())
             file.delete();

         FileOutputStream out = null;
         InputStream in=null;
         try {
             in=responseBody.byteStream();
             out = new FileOutputStream(file);
             byte[] buffer = new byte[1024 * 128];
             int len = -1;
             while ((len = in.read(buffer)) != -1) {
                 out.write(buffer, 0, len);
             }
             out.flush();
             out.close();
             in.close();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }  finally {
             if(in!=null){
                 in.close();
             }
             if (out!=null){
                 out.close();
             }
         }


     }

}