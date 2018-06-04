package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.util.FileUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.base.utils.WindowUtils;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineBean;
import com.qingyii.hxtz.wmcj.mvp.model.entity.FooterBean;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressManager;
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
                   TextView tv= holder.getTextView(R.id.draftrecycname);
                   tv.setText(item.getFilename());
                   tv.setTextColor(mApplication.getResources().getColor(R.color.examne_text ));
                   holder.getTextView(R.id.draftrecyctime).setVisibility(View.GONE);

                }
            };
            adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Object Data, View view, int position) {
                    String murl=list.get(position).getFileurl();
                    File file=new File(HttpUrlConfig.cacheDir,murl);
                    downloadandopen(murl,file);
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


   //下载并且打开文件
    public void downloadandopen(String murl,File file){
        ProgressManager.getInstance().addResponseListener(murl, WindowUtils.getDownloadListener());
        mModel.download(murl).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(responseBody -> responseBody.byteStream())
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<InputStream>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull InputStream inputStream) {
                        try {

                            String[] urls=murl.split(".");
                            FileUtils.writeFile(inputStream, file);
                            FileUtils.openFile(mApplication, file.getPath(),"."+urls[1]);
                        } catch (IOException e) {
                            e.printStackTrace();
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
    }
}
