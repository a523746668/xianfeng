package com.qingyii.hxtz.wmcj;

import com.qingyii.hxtz.bean.ReportBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Headbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Resultbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkitembean;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by zhf on 2018/3/13. 文明创建相关API
 */

public interface WMCJApi {

    @POST
    Observable<WorkParkbean> getWorkMenu(@Url String url);

    //获得数据 刷新数据
    @FormUrlEncoded
    @POST
    Observable<WorkParkitembean> getWorkParkItem(@Url String url , @FieldMap Map<String,String> map);

    //加载更多
    @FormUrlEncoded
    @POST
    Observable<WorkParkitembean> getWorkParkItemMore(@Url String url , @FieldMap Map<String, String> map);

    //获得数据 刷新数据
    @FormUrlEncoded
    @POST
    Observable<WorkParkitembean> getWorkParkItem(@Url String url , @Field("tag_id") String  tag_id);

    //获得工作动态首页面轮播图
    @POST
    Observable<Headbean> getWorkParkItemSlider(@Url String url);

    //获得任务清单标题
    @POST
    Observable<TaskTitlebean> getTaskTitle(@Url String mUrl);

    @POST
    Observable<ResponseBody> getTaskListData(@Url String mUrl);

    @POST
    @FormUrlEncoded
    Observable<Taskdetailbean> getTaskDetailData(@Url String url,@Field("taskid") String taskid);

    @POST
    Observable<Resultbean>  getResultData(@Url String mUrl);

    @POST
    @FormUrlEncoded
    Observable<Resultbean>  getResultData(@Url String mUrl,@Field("industryid") String industryid);

    @POST
    Observable<ReportBean> getReportBean(@Url String mUrl);

    @POST
    @FormUrlEncoded
    Observable<ReportBean> getReportMore(@Url String murl,@Field("time") String time);

    @POST
    Observable<ReportMenu> getReportMenu(@Url String murl);

    @GET
    Observable<ExamineBean> getExamine(@Url String murl);

    @GET
    Observable<ExamineMenu> getExamineMenu(@Url String murl);

    @GET
    Observable<ResponseBody> download(@Url String url);
}
