package com.qingyii.hxt.base.mvp.api.service;

import com.qingyii.hxt.base.mvp.model.entity.CommonData;
import com.qingyii.hxt.home.mvp.model.entity.FakeData;
import com.qingyii.hxt.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxt.meeting.di.module.entity.MeetingList;
import com.qingyii.hxt.meeting.di.module.entity.MeetingSummary;
import com.qingyii.hxt.notice.mvp.model.entity.NoticeDetail;
import com.qingyii.hxt.notice.mvp.model.entity.NoticeList;
import com.qingyii.hxt.notify.mvp.model.entity.NotifyList;
import com.qingyii.hxt.training.details.schedule.di.module.entity.TrainNoticeList;
import com.qingyii.hxt.wmcj.mvp.model.entity.ResultRankingData;
import com.qingyii.hxt.wmcj.mvp.model.entity.WorkParkList;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 存放关于用户的一些api
 * Created by xubo on 8/5/16 12:05
 */
public interface ApiService {


    @FormUrlEncoded
    @POST("/notify")
    Observable<NotifyList> getNotifyLists(@Field("id") int lastId, @Field("direction") String direction);

    @GET("/notify/system")
    Observable<NotifyList> getNotifySystemLists(@Query("id") int lastId, @Query("direction") String direction);

    @GET("/notify/train/{trainid}")
    Observable<NoticeList> getNotifyTrainLists();

    @GET("/notify/{id}/read")
    Observable<CommonData<String>> getReadMark(@Path("id") String id);

    @FormUrlEncoded
    @POST("/notify/{id}/sign")
    Observable<CommonData<String>> getSignMark(@Path("id") int id, @Field("rejectreturn") String message);

    @GET("/meeting")
    Observable<MeetingList> getMeetingLists(@Query("id") int lastId, @Query("direction") String direction);

    @Streaming
    @GET
    Observable<Response> download1(@Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    @GET("/notify/common")
    Observable<MeetingList> getPublishLists(@Query("id") int lastId, @Query("direction") String direction);

    @GET("/notify/common")
    Observable<MeetingList> getMeetingPublishLists(@Query("id") int lastId, @Query("direction") String direction);

    @GET("/home/info")
    Observable<HomeInfo> getHomeInfo();

    @FormUrlEncoded
    @POST("/meeting/{id}/confirm")
    Observable<CommonData<String>> getFeedback(@Path("id") int id, @Field("status") String status, @Field("rejectresult") String rejectresult);

    @FormUrlEncoded
    @POST("/announcement")
    Observable<NoticeList> getNoticeLists(@Field("id") int lastId, @Field("direction") String direction);

    @GET("training/{training}/notify")
    Observable<TrainNoticeList> getTrainNoticeList(@Path("training") int trainId);

    @GET("announcement/{id}")
    Observable<NoticeDetail> getLastNotice(@Path("id") String id);

    @POST
    Observable<CommonData<String>> requestSummary(@Url String url, @Body MeetingSummary data);

    @Multipart
    @POST
    Observable<CommonData<String>> uploadPic(@Url String url,@Part() MultipartBody.Part part);

    @GET
    Observable<List<WorkParkList>> getWorkParkListData(@Url String url,@Query("page")int page);



    @GET
    Observable<ResultRankingData> getResultRankingData();


   @GET
    Observable<FakeData> getfakedata(@Url String url);
}
