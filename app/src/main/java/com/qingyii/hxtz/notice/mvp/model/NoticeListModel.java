package com.qingyii.hxtz.notice.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.base.mvp.api.service.ApiService;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.notice.mvp.model.entity.NoticeDetail;
import com.qingyii.hxtz.notice.mvp.model.entity.NoticeList;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class NoticeListModel extends BaseModel implements CommonContract.NoticeListModel {

    @Inject
    public NoticeListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<NoticeList> getNoticeList(int lastId, String direction) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getNoticeLists(lastId, direction);
    }

    @Override
    public Observable<NoticeDetail> getLastNotice(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getLastNotice(id);
    }
}