package com.qingyii.hxt.wmcj.mvp.model.entity

import com.mcxtzhang.commonadapter.rv.IHeaderHelper
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.qingyii.hxt.R
import com.qingyii.hxt.base.widget.PacManRefreshHead

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 17/01/08.
 */

 class FooterBean : IHeaderHelper {
    private var pcMan: PacManRefreshHead? = null

    override fun getItemLayoutId(): Int {
        return R.layout.item_footer
    }

    override fun onBind(holder: ViewHolder) {
        pcMan = holder.getView(R.id.pac_man)
        pcMan!!.performLoading()
    }

}