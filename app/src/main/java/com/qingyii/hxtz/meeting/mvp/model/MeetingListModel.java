package com.qingyii.hxtz.meeting.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.base.mvp.api.cache.CommonCache;
import com.qingyii.hxtz.base.mvp.api.service.ApiService;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.meeting.di.module.entity.MeetingList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;


@ActivityScope
public class MeetingListModel extends BaseModel implements CommonContract.MeetingListModel {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MeetingListModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MeetingList> getMeetingList(int lastIdQueried, String direction, boolean update) {
        Observable<MeetingList> meetingLists = mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getMeetingLists(lastIdQueried, direction);
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return mRepositoryManager.obtainCacheService(CommonCache.class)
                .getMeetingLists(meetingLists
                        , new DynamicKey(lastIdQueried)
                        , new EvictDynamicKey(true))
                .flatMap(new Function<Reply<MeetingList>, ObservableSource<MeetingList>>() {
                    @Override
                    public ObservableSource<MeetingList> apply(@NonNull Reply<MeetingList> listReply) throws Exception {
                        return Observable.just(listReply.getData());
                    }
                });
    }

}