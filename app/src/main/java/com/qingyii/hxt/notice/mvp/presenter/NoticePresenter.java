package com.qingyii.hxt.notice.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.utils.RxUtils;
import com.qingyii.hxt.notice.mvp.model.entity.NoticeList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class NoticePresenter extends BasePresenter<CommonContract.NoticeListModel, CommonContract.NotifyListView> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<NoticeList.DataBean> mNotices = new ArrayList<>();
    private int pagesize = 10;
    private boolean isFirst = true;
    private BaseRecyclerAdapter<NoticeList.DataBean> mAdapter;
    private int lastId = 0;
    private String direction = "lt";
    //判断当前是否已经在请求数据

    @Inject
    public NoticePresenter(CommonContract.NoticeListModel notifyListModel, CommonContract.NotifyListView rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(notifyListModel, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void requestNoticeLists(final boolean pullToRefresh) {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<NoticeList.DataBean>(mNotices) {


                @Override
                public int getItemLayoutId(int viewType) {
                    return R.layout.notice_recyclerview_item;
                }

                @Override
                public void bindData(BaseRecyclerViewHolder holder, int position, NoticeList.DataBean item) {
                    holder.setVisibility(R.id.meeting_item_mark, item.is_read() == 0 ? View.VISIBLE : View.GONE);
                    holder.setText(R.id.notice_item_title, item.getTitle());
                    holder.setText(R.id.notice_item_name, item.getAuthor());
                    holder.setText(R.id.notice_item_time, item.getCreated_at());
                }
            };
            mRootView.setAdapter(mAdapter);//设置Adapter18975
        }
        if (pullToRefresh) lastId = 0;//上拉刷新默认只请求第一页
        mModel.getNoticeList(lastId, direction)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh) mRootView.showLoading();
                    else mRootView.startLoadMore();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<NoticeList>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull NoticeList noticeList) {
                        if (noticeList.getData().size() < 10) {
                            mRootView.notifyAllLoad();
                        } else {
                            if (noticeList.getData().size() > 0)
                                lastId = noticeList.getData().get(noticeList.getData().size() - 1).getId();
                        }
                        if (pullToRefresh) mNotices.clear();
                        mNotices.addAll(noticeList.getData());
                        if (pullToRefresh) {
                            mAdapter.setData(noticeList.getData());
                        } else {
                            //解决数据重复
                            try {
                                if (mAdapter.getData().get(mAdapter.getData().size() - 1).getId() == noticeList.getData().get(noticeList.getData().size() - 1).getId()) {
                                    return;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mAdapter.addMoreData(noticeList.getData());
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

}