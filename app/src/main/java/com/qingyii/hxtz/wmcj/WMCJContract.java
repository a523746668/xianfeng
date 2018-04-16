package com.qingyii.hxtz.wmcj;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Headbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Resultbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkitembean;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.ResultSonFragment;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.TaskListSonFragment;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.TaskLineAdaper;


import java.util.ArrayList;

import io.reactivex.Observable;
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
         Observable<WorkParkitembean>  getAlreadyWork(String time);


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
        Observable<TaskTitlebean>  getTaskTitle();
    }

    interface  ResultSonView extends IView{
          void getResultDataSuccess(Resultbean resultbean);
    }

    interface  IssuseTaskView extends IView{

    }

    interface  IssuseTaskModel extends  IModel{

    }
}
