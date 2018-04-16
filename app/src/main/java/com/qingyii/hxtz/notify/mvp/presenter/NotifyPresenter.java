package com.qingyii.hxtz.notify.mvp.presenter;

import android.app.Application;
import android.graphics.Typeface;
import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.notify.mvp.model.entity.NotifyList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class NotifyPresenter extends BasePresenter<CommonContract.NotifyListModel, CommonContract.NotifyListView> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<NotifyList.DataBean> mNotifys = new ArrayList<>();
    private int pagesize = 10;
    private boolean isFirst = true;
    private BaseRecyclerAdapter<NotifyList.DataBean> mAdapter;
    private int preEndIndex;
    private int lastId = 0;
    private String direction = "lt";
    //判断当前是否已经在请求数据

    @Inject
    public NotifyPresenter(CommonContract.NotifyListModel notifyListModel, CommonContract.NotifyListView rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(notifyListModel, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void requestNotifyLists(final boolean pullToRefresh, final String origin) {


        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<NotifyList.DataBean>(mNotifys) {
                @Override
                public int getItemLayoutId(int viewType) {
                    return R.layout.notify_recyclerview_item;
                }

                @Override
                public void bindData(BaseRecyclerViewHolder holder, int position, NotifyList.DataBean item) {
                    holder.setVisibility(R.id.notify_item_status_layout, origin.equals(EventBusTags.NOTIFY_SYSTEM) ? View.GONE : View.VISIBLE);
                    holder.setVisibility(R.id.notify_item_unit_layout, origin.equals(EventBusTags.NOTIFY_SYSTEM) ? View.GONE : View.VISIBLE);
                    holder.setText(R.id.notify_item_title, item.getTitle());
                    holder.setText(R.id.notify_item_unit, item.getAuthor());
                    holder.setText(R.id.notify_item_time, item.getCreated_at());
                    holder.setVisibility(R.id.notify_item_mark, item.getIs_receipt() == 0 ? View.VISIBLE : View.GONE);

                    switch (item.getReceipt_status_mark()) {
                        case NotifyList.DataBean.UNREAD:
                            holder.setText(R.id.notify_item_status, "未查阅");
                            holder.setText(R.id.notify_item_type, "查阅通知");
                            holder.setTextColorRes(R.id.notify_item_status, mApplication.getResources().getColor(R.color.notifyListUnreadColor));
                            break;
                        case NotifyList.DataBean.UNSIGN:
                            holder.setText(R.id.notify_item_status, "未签收");
                            holder.setText(R.id.notify_item_type, "签收通知");
                            holder.setTextColorRes(R.id.notify_item_status, mApplication.getResources().getColor(R.color.notifyListUnreadColor));
                            break;
                        case NotifyList.DataBean.UNRETURN:
                            holder.setText(R.id.notify_item_status, "未回执");
                            holder.setText(R.id.notify_item_type, "回执通知");
                            holder.setTextColorRes(R.id.notify_item_status, mApplication.getResources().getColor(R.color.notifyListUnreadColor));
                            break;
                        case NotifyList.DataBean.READ:
                            holder.setText(R.id.notify_item_status, "已查阅");
                            holder.setText(R.id.notify_item_type, "查阅通知");
                            holder.setTextColorRes(R.id.notify_item_status, mApplication.getResources().getColor(R.color.notifyListReadColor));
                            break;
                        case NotifyList.DataBean.SIGN:
                            holder.setText(R.id.notify_item_status, "已签收");
                            holder.setText(R.id.notify_item_type, "签收通知");
                            holder.setTextColorRes(R.id.notify_item_status, mApplication.getResources().getColor(R.color.notifyListReadColor));
                            break;
                        case NotifyList.DataBean.RETURN:
                            holder.setText(R.id.notify_item_status, "已回执");
                            holder.setText(R.id.notify_item_type, "回执通知");
                            holder.setTextColorRes(R.id.notify_item_status, mApplication.getResources().getColor(R.color.notifyListReadColor));
                            break;
                    }
                    switch (item.getReceipt_type()) {
                        case NotifyList.DataBean.FLAT:
                            holder.setText(R.id.notify_item_emergency, "普通");
                            holder.setTextColorRes(R.id.notify_item_emergency, mApplication.getResources().getColor(R.color.notifyListContentColor));
                            holder.setTextStyle(R.id.notify_item_emergency, Typeface.NORMAL);
                            break;
                        case NotifyList.DataBean.URGENT:
                            holder.setText(R.id.notify_item_emergency, "紧急");
                            holder.setTextColorRes(R.id.notify_item_emergency, mApplication.getResources().getColor(R.color.notifyListContentColor));
                            holder.setTextStyle(R.id.notify_item_emergency, Typeface.BOLD);
                            break;
                        case NotifyList.DataBean.EXTRAURGENT:
                            holder.setText(R.id.notify_item_emergency, "特急");
                            holder.setTextColorRes(R.id.notify_item_emergency, mApplication.getResources().getColor(R.color.notifyListEmergencyColor));
                            holder.setTextStyle(R.id.notify_item_emergency, Typeface.BOLD);
                            break;
                        default:
                            break;
                    }
                }
            };
            mRootView.setAdapter(mAdapter);//设置Adapter
        }

        if (pullToRefresh) lastId = 0;//上拉刷新默认只请求第一页

        mModel.getNotifyList(lastId, direction, origin.equals(EventBusTags.NOTIFY_SYSTEM))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示上拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示下拉加载更多的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁

                .subscribe(
                        new ErrorHandleSubscriber<NotifyList>(mErrorHandler) {
                            @Override
                            public void onNext(NotifyList noticeList) {
                                if (noticeList.getData().size() < pagesize) {
                                    mRootView.notifyAllLoad();
                                } else {
                                    if (noticeList.getData().size() > 0)
                                        lastId = noticeList.getData().get(noticeList.getData().size() - 1).getId();
                                }
                                if (pullToRefresh) mNotifys.clear();
//                                mNotifys.addAll(noticeList.getData());
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