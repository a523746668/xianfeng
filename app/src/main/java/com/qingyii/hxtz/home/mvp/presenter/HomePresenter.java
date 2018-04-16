package com.qingyii.hxtz.home.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.home.mvp.model.entity.FakeData;
import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.zhf.Util.Global;
import com.zhf.http.Urlutil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.qingyii.hxtz.base.app.GlobalConsts.CIRCLE;


@ActivityScope
public class HomePresenter extends BasePresenter<CommonContract.HomeInfoModel, CommonContract.HomeInfoView> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private BaseRecyclerAdapter<HomeInfo.AccountBean.ModulesBean> mAdapter;
    private List<HomeInfo.AccountBean.ModulesBean> homeInfoLists = new ArrayList<>();

    @Inject
    public HomePresenter(CommonContract.HomeInfoModel model, CommonContract.HomeInfoView rootView
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

    public void requestHomeInfo( Context context) {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<HomeInfo.AccountBean.ModulesBean>(Global.list) {
                @Override
                public int getItemLayoutId(int viewType) {
                    return R.layout.home_recyclerview_item;
                }

                @Override
                public void bindData(BaseRecyclerViewHolder holder, int position, HomeInfo.AccountBean.ModulesBean item) {
                    try {
                        holder.itemView.setTag(item.getMark());
                        //int resId = mApplication.getResources().getIdentifier(item.getMark(), "drawable", mApplication.getPackageName());
                       // holder.setImage(R.id.home_item_icon, resId);
                        Glide.with(context).load(Urlutil.baseimgurl+item.getIcon()).into(holder.getImageView(R.id.home_item_icon));


                        if (item.getMark().equals(CIRCLE)) {
                            holder.setVisibility(R.id.home_item_tv_tip, View.GONE);
                            holder.setVisibility(R.id.home_item_mark, item.getCount() > 0 ? View.VISIBLE : View.GONE);
                        } else {
                            holder.setVisibility(R.id.home_item_mark, View.GONE);
                            holder.setVisibility(R.id.home_item_tv_tip, item.getCount() > 0 ? View.VISIBLE : View.GONE);
                        }
                        holder.setText(R.id.home_item_tv_tip, item.getCount() + "");
                        holder.setText(R.id.home_item_name, item.getName());
                        if (position != 0 && position == homeInfoLists.size() - 1) {
                            holder.itemView.setPadding(0,0,0,40);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            mRootView.setAdapter(mAdapter);//设置Adapter18975
        }

    }

     public void requesrfakedata(){
         mModel.getfakedata()
                 .subscribeOn(Schedulers.io())
                 .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                 .subscribeOn(AndroidSchedulers.mainThread())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new ErrorHandleSubscriber<FakeData>(mErrorHandler) {

                     @Override
                     public void onNext(@NonNull FakeData fakeData) {
                               mRootView.getfakedatasuceess(fakeData);
                     }
                 });

     }

     public void getpersion(){
         PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {
             @Override
             public void onRequestPermissionSuccess() {
             }

             @Override
             public void onRequestPermissionFailure() {
             }
         }, mRootView.getRxPermissions(), mErrorHandler, mRootView.getPerMissions());
     }

     public void gethomeinfo(){


         mModel.getHomeInfo()
                 .subscribeOn(Schedulers.io())
                 .doOnSubscribe(new Consumer<Disposable>() {
                     @Override
                     public void accept(@NonNull Disposable disposable) throws Exception {
                         mRootView.showLoading();
                     }
                 })
                 .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                 .subscribeOn(AndroidSchedulers.mainThread())
                 .observeOn(AndroidSchedulers.mainThread())
                 .doAfterTerminate(new Action() {
                     @Override
                     public void run() throws Exception {
                         mRootView.hideLoading();
                     }
                 })
                 .subscribe(new ErrorHandleSubscriber<HomeInfo>(mErrorHandler) {
                     @Override
                     public void onNext(@NonNull HomeInfo homeInfo) {
                         mRootView.updateUI(homeInfo);
                     }

                     @Override
                     public void onError(@NonNull Throwable e) {
                         UiUtils.snackbarText(e.getMessage());
                     }
                 });


     }


}