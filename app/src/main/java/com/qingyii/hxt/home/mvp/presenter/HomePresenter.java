package com.qingyii.hxt.home.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.utils.RxUtils;
import com.qingyii.hxt.home.mvp.model.entity.FakeData;
import com.qingyii.hxt.home.mvp.model.entity.HomeInfo;
import com.zhf.http.Urlutil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static com.qingyii.hxt.base.app.GlobalConsts.CIRCLE;


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

    public void requestHomeInfo(boolean is_first, Context context) {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<HomeInfo.AccountBean.ModulesBean>(homeInfoLists) {
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
        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
            }

            @Override
            public void onRequestPermissionFailure() {
            }
        }, mRootView.getRxPermissions(), mErrorHandler, mRootView.getPerMissions());
        mModel.getHomeInfo()
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<HomeInfo>(mErrorHandler) {
                    @Override
                    public void onNext(HomeInfo homeInfo) {
//                        mRootView.updateUI(homeInfo);
                        try {
                            if (is_first) homeInfoLists.clear();
                            final HomeInfo.AccountBean.ModulesBean[] module = {null};

                          /*  if(!BuildConfig.DEBUG) {

                                Observable.fromIterable(homeInfo.getAccount().getModules()).filter(
                                        modulesBean -> MyApplication.userUtil.getAccount_id() == 135).forEach(bean -> {
                                    if (bean.getMark().equals(MAGAZINE) && MyApplication.userUtil.getAccount_id() == 135)
                                        bean.setName("刊物");
                                    if (Arrays.asList(HIDE_MODULES).contains(bean.getMark())) {
                                        bean.setLevel(1);
                                    }
                                    if (Arrays.asList(SHOW_MODULES).contains(bean.getMark())) {
                                        bean.setLevel(0);
                                    }
                                });
                            }else{ */
                            //Observable.fromIterable(homeInfo.getAccount().getModules()).forEach(modulesBean -> modulesBean.setLevel(0));

                         /*   Observable.fromIterable(homeInfo.getAccount().getModules()).filter(
                                    modulesBean -> MyApplication.userUtil != null && MyApplication.userUtil.getJoin_doc() != 1).forEach(bean -> {
                                  if(bean.getMark().equalsIgnoreCase("documentary"))
                                      bean.setLevel(1);
                            });   */
                            if (module[0] != null)
                                homeInfo.getAccount().getModules().add(module[0]);

                            //使用rxjava过虑level不等于0的条目
                            if (homeInfo.getAccount().getModules().size() > 0)
                                Observable.fromIterable(homeInfo.getAccount().getModules())
                                        .filter(bean -> bean.getLevel() == 0 && is_first && !bean.getMark().equals("activity"))
                                        .subscribe(modulesBeen ->
                                        homeInfoLists.add(modulesBeen));
                           //是否显示同事圈
                         /*   if(MyApplication.hxt_setting_config.getInt(GlobalConsts.ACCOUNT_ID,0)!=135|MyApplication.hxt_setting_config.getInt(GlobalConsts.ACCOUNT_ID,0)!=137){
                                for(HomeInfo.AccountBean.ModulesBean bean:homeInfoLists){
                                    if (bean.getName().equalsIgnoreCase("同事圈")){
                                        homeInfoLists.remove(bean);
                                    }
                                }
                            }*/
                            mAdapter.notifyDataSetChanged();
                            mRootView.updateUI(homeInfo);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });
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




}