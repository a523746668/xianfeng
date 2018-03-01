package com.zhf.present.Implview;

import com.zhf.bean.WorkParkitembean;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/12/26.
 */

public interface ReportingView {
     void getreportingsuccess(WorkParkitembean workParkitembean);
     void getreportingmoresuccess(WorkParkitembean workParkitembean);
     void startloadmore();
     void endloadmore();
     void finishrefresh();
     void getdatano();
}
