package com.qingyii.hxtz.base.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.mvp.model.entity.CommonData;
import com.qingyii.hxtz.home.mvp.model.entity.FakeData;
import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxtz.meeting.di.module.entity.MeetingList;
import com.qingyii.hxtz.meeting.di.module.entity.MeetingSummary;
import com.qingyii.hxtz.notice.mvp.model.entity.NoticeDetail;
import com.qingyii.hxtz.notice.mvp.model.entity.NoticeList;
import com.qingyii.hxtz.notify.mvp.model.entity.NotifyList;
import com.qingyii.hxtz.training.details.schedule.di.module.entity.TrainNoticeList;
import com.qingyii.hxtz.wmcj.mvp.model.entity.ResultRankingData;
import com.qingyii.hxtz.wmcj.mvp.model.entity.WorkParkList;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;


public interface CommonContract {
     //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    /**
     * 通知列表view
     */
    interface NotifyListView extends IView {
        void setAdapter(BaseRecyclerAdapter adapter);

        void startLoadMore();

        void endLoadMore();

        void notifyAllLoad();
    }
    interface NoticeView extends IView {
        void updateUI(NoticeDetail detail);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存

    /**
     * 通知列表model
     */
    interface NotifyListModel extends IModel {
        Observable<NotifyList> getNotifyList(int lastId, String direction, boolean type);
    }
    /**
     * 通知详情view
     */
    interface NotifyDetailsView extends IView {
        void UpdateReadStatus();

        void UpdateSignStatus();
    }

    /**
     * 通知详情model
     */
    interface NotifyDetailsModel extends IModel {
        Observable<CommonData<String>> getReadMark(String id);

        Observable<CommonData<String>> getSignMark(int id,String message);

        Observable<ResponseBody> download(String url);
    }
    /**
     * 公告列表model
     */
    interface NoticeListModel extends IModel {
        Observable<NoticeList> getNoticeList(int lastId, String direction);
        Observable<NoticeDetail> getLastNotice(String id);
    }
    /**
     * 会议列表view
     */
    interface MeetingListView extends IView {
        void setAdapter(BaseRecyclerAdapter adapter);

        void startLoadMore();

        void endLoadMore();

        void notifyAllLoad();
    }
    /**
     * 会议列表model
     */
    interface MeetingListModel extends IModel {
        Observable<MeetingList> getMeetingList(int lastId, String direction, boolean update);

    }
    /**
     * 会议搜索列表view
     */
    interface MeetingSearchListView extends IView {
        void setAdapter(BaseRecyclerAdapter adapter);

        void startLoadMore();

        void endLoadMore();
    }
    /**
     * 会议搜索列表model
     */
    interface MeetingSearchListModel extends IModel {
        Observable<MeetingList> getMeetingSearchList(int lastIdQueried, String direction, boolean update);
    }
    /**
     * 会议搜索列表view
     */
    interface MeetingSummaryView extends IView {

        void uploadFinish(List<String> data);

        void submitFinish();
    }
    /**
     * 会议搜索列表model
     */
    interface MeetingSummaryModel extends IModel {
        Observable<CommonData<String>> requestMeetingSummary(MeetingSummary data);

        Observable<CommonData<String>> uploadPic(MultipartBody.Part part, String id);
    }

    /**
     * 会议我的发布view
     */
    interface MeetingPublishListView extends IView {
        void setAdapter(BaseRecyclerAdapter adapter);

        void startLoadMore();

        void endLoadMore();

        void notifyAllLoad();
    }
    /**
     * 会议我的发布model
     */
    interface MeetingPublishListModel extends IModel {
        Observable<MeetingList> getMeetingPublishList(int lastId, String direction, boolean update);
    }
    /**
     * 会议详情model
     */
    interface MeetingDetailsModel extends IModel {
        Observable<CommonData<String>> requestFeedback(int id,String status,String rejectresult);

        Observable<ResponseBody> download(String url);

    }
    /**
     * 会议详情view
     */
    interface MeetingDetailsView extends IView {

        void UpdateFeedbackStatus(String status);
    }
    /**
     * 主页model
     */
    interface HomeInfoModel extends IModel {
        Observable<HomeInfo> getHomeInfo();

        Observable<FakeData> getfakedata();
    }
    /**
     * 主页view
     */
    interface HomeInfoView extends IView {
        void setAdapter(BaseRecyclerAdapter adapter);
        void updateUI(HomeInfo homeInfo);
        //申请权限
        RxPermissions getRxPermissions();

       void getfakedatasuceess(FakeData fakeData);
        //void requestnewdo();
        String[] getPerMissions();
    }
    /**
     * 培训通知
     */
    interface TrainingNoticeContractView extends IView{
        void setAdapter(BaseRecyclerAdapter adapter);
    }
    /**
     * 培训通知model
     */
    interface TrainingNoticeContractModel extends IModel {
        Observable<TrainNoticeList> getTrainNoticeList(int trainId);
    }
    /**
     * 工作动态view
     */
    interface WorkParkListView extends IView {
        void setAdapter(RecyclerView.Adapter adapter);

        void startLoadMore();

        void endLoadMore();

        void notifyAllLoad();
    }
    /**
     * 工作动态model
     */
    interface WorkParkListModel extends IModel {
        //int lastId, String direction, int typeId
        Observable<List<WorkParkList>> getWorkParkList(int page);
    }

    /**
     * 结果排名view
     */
    interface ResultRankingListView extends IView {
        void setAdapter(RecyclerView.Adapter adapter);

        void startLoadMore();

        void endLoadMore();

        void notifyAllLoad();
    }
    /**
     * 结果排名model
     */
    interface ResultRankingModel extends IModel {
        //int lastId, String direction, int typeId
        Observable<ResultRankingData> getResultRankingData();
    }
}