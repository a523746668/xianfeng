package com.qingyii.hxt.base.mvp.api.cache;


import com.qingyii.hxt.meeting.di.module.entity.MeetingList;
import com.qingyii.hxt.notify.mvp.model.entity.NotifyList;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Created by xubo on 8/30/16 13:53
 *
 */
public interface CommonCache {



    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<NotifyList>> getNotifyLists(Observable<NotifyList> notifyList, DynamicKey lastId, EvictProvider evictProvider);
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<MeetingList>> getMeetingLists(Observable<MeetingList> meetingList, DynamicKey page, EvictProvider evictProvider);

}
