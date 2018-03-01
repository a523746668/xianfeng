package com.zhf.present.Implview;

import com.zhf.base.BaseActivityview;
import com.zhf.bean.Headbean;
import com.zhf.bean.WorkParkitembean;

/**
 * Created by zhf on 2017/9/29.
 */

public interface WorkParkitemview extends BaseActivityview<WorkParkitembean> {
        void startloadmore();

        void endloadmore();

        void finishrefresh();

       void getmoredatasuccess(WorkParkitembean workParkitembean);

      void getimagessuccee(Headbean headbean);
}
