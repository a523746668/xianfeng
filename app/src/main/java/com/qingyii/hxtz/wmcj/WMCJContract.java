package com.qingyii.hxtz.wmcj;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.bean.ReportBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Headbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportDelete;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Resultbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkitembean;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.ResultSonFragment;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.TaskListSonFragment;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.TaskLineAdaper;


import org.geometerplus.zlibrary.core.util.ZLSearchUtil;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import okhttp3.ResponseBody;

/**
 * Created by zhf on 2018/3/13.
 */
/*  文明创建模块
* */
public interface WMCJContract  {

    interface  WorkParkView extends IView {
        void getdatasuccess(WorkParkbean workParkbean);
        void getdataerror(Exception e);
        void setRecyclerviewAdapter(HeaderFooterAdapter adapter);
        void doRefresh();
        void finishRefresh();
        void setflag(boolean flag);
    }

    /*复用model*/
    interface  WorkParkModel extends IModel{
         Observable<WorkParkbean>  getWorkMenu();

        /* 获得数据*/
         Observable<WorkParkitembean>  getWorkParkItem(int tag_id);
         Observable<WorkParkitembean>  getWorkParkItem(int library_id,int system_id);

     //获得更多
        Observable<WorkParkitembean> getWorkParkItemMore(String time  ,int library_id,int system_id);
        Observable<WorkParkitembean> getWorkParkItemMore(String time  ,int tag_id);
        //轮播图
         Observable<Headbean>  getWorkParkItemHead();

       //获得已上报任务
         Observable<ReportBean> getAlreadyWork(String time);
      //6
        Observable<ReportBean> getallReport(String system_id);
        Observable<ReportBean> getallReportMore(String system_id,String time);
        Observable<ReportBean> getReportSX(String indtagid,String onetask,String twotask,String industryarray);
        Observable<ReportDelete> deleteReport(String actid,String a_org_id);
        Observable<ReportBean> getReportSXMore(Map<String,String > map);
    }

   interface  WorkParkItemView extends  IView{
         void setRecyclerviewAdapter(HeaderFooterAdapter adapter);
         void doRefresh();
         void finishRefresh();
   }

  interface TaskView extends  IView{
         void setRecyclerviewAdapter(BaseRecyclerAdapter adapter);
         void getdatano();
         void getdatasuccess(ArrayList<TaskListSonFragment> list);
         void gettitile(ArrayList<TaskTitlebean.DataBean.LibsystemBean> list);
  }

  interface TaskModel extends IModel{
     Observable<TaskTitlebean> getTaskTitle();
     Observable<ResponseBody> getTaskListData(int id);
     Observable<Taskdetailbean> getTaskDetail(int taskid);
  }

   interface  TaskSonView extends  IView{
     void setRecyclerviewAdapter(TaskLineAdaper adapter);

   }

   interface TaskDetaileView extends  IView{
       void  getdatasuccess(Taskdetailbean bean);
   }

    interface ResultView extends  IView{
       void getdatano();
        void getdatasuccess(ArrayList<ResultSonFragment> list) ;

        void  gettitlesuccess(ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles);

    }

    interface  ResultModel extends  IModel{
        Observable<Resultbean>  getResultBean(int librarySystem);
        Observable<Resultbean>  getResultBean(int librarySystem,int  industryid);
        Observable<Resultbean>  getResultSX(int librarySystem,int industryid,String tagid);
        Observable<TaskTitlebean>  getTaskTitle();
    }

    interface  ResultSonView extends IView{
          void getResultDataSuccess(Resultbean resultbean);
    }

    interface  IssuseTaskView extends IView{

    }

    interface  IssuseTaskModel extends  IModel{

    }

   interface WMCJcategoryView extends  IView{
       void getReportMenuSuccess(ReportMenu bean);
       void getExmineMenuSuccess(ExamineMenu bean);
   }

   interface WMCJcategoryModel extends IModel{
      Observable<ReportMenu> getReportMenu();
     Observable<ExamineMenu> getExamineMenu();
    }

    interface ExamineView extends  IView{
     void getExamineBeanSuccess(ExamineBean bean);
     void setadapter(BaseRecyclerAdapter<ExamineBean.DataBean.FileBean>  adapter);
     void getdataerror();
    }

    interface ExamineModel extends IModel{
        Observable<ExamineBean> getExamineBean(int id);
        Observable<ResponseBody> download(String url);
        Observable<ExamineBean> getExamineBean(int id,String system_id);
    }
}
