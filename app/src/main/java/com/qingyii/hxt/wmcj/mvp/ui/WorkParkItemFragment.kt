package com.qingyii.hxt.wmcj.mvp.ui

import `in`.srain.cube.views.ptr.PtrClassicFrameLayout
import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.UiUtils
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter
import com.qingyii.hxt.R
import com.qingyii.hxt.base.app.bindView
import com.qingyii.hxt.base.mvp.contract.CommonContract
import com.qingyii.hxt.base.widget.AutoLoadMoreRecyclerView
import com.qingyii.hxt.wmcj.di.component.DaggerWorkParkComponent
import com.qingyii.hxt.wmcj.di.module.WorkParkModule
import com.qingyii.hxt.wmcj.mvp.model.entity.FooterBean
import com.qingyii.hxt.wmcj.mvp.presenter.WorkParkPresenter

/**
 * Created by xubo on 2017/9/9.
 * 工作动态子页面
 */
class WorkParkItemFragment : BaseFragment<WorkParkPresenter>(), CommonContract.WorkParkListView {
    val mRecyclerView: AutoLoadMoreRecyclerView by bindView(R.id.workpark_item_recyclerView)
    val mRefresh: PtrClassicFrameLayout by bindView(R.id.workpark_item_refresh)
    //标识：是否初次进入，针对刷新头
    private var flag = true

    override fun setupFragmentComponent(appComponent: AppComponent?) {
        DaggerWorkParkComponent.builder()
                .appComponent(appComponent)
                .workParkModule(WorkParkModule(this))
                .build()
                .injcet(this)
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_workpark_item, container, false)
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        mRecyclerView.setAutoLayoutManager(LinearLayoutManager(activity))
                .setAutoItemAnimator(DefaultItemAnimator()).setAutoAdapter(adapter)
                .addAutoItemDecoration(DividerItemDecoration(activity, LinearLayout.VERTICAL))
//        mRecyclerView.setEmptyView(emptyView)
        mRecyclerView.setOnLoadMoreListener {
            mPresenter.requestWorkParkList(false)
        }
    }

    override fun showMessage(message: String?) {
        checkNotNull(message)
        UiUtils.snackbarText(message)
    }

    override fun endLoadMore() {
        (mRecyclerView.adapter as HeaderFooterAdapter).clearFooterView()
        mRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun notifyAllLoad() {
        mRecyclerView.notifyAllLoaded()
    }

    override fun setData(data: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData(savedInstanceState: Bundle?) {
        mRefresh.setLastUpdateTimeRelateObject(this)
        //刷新头的监听
        mRefresh.setPtrHandler(object : PtrDefaultHandler() {
            override fun onRefreshBegin(frame: PtrFrameLayout) {
                flag = false
                //获取数据
                mPresenter.requestWorkParkList(true)
            }

            override fun checkCanDoRefresh(frame: PtrFrameLayout?, content: View, header: View?): Boolean {
                return super.checkCanDoRefresh(frame, mRecyclerView, header)
            }
        })
        mPresenter.requestWorkParkList(true)
    }


    override fun showLoading() {
        mRefresh.autoRefresh(true)
//        mRefresh.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                    mRefresh.autoRefresh(true)
//                    //api>=16
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                        mRefresh.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                }
//            }
//        })
    }

    override fun launchActivity(intent: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startLoadMore() {
        mRecyclerView.notifyMoreLoaded()
        var fb = FooterBean()
        (mRecyclerView.adapter as HeaderFooterAdapter).addFooterView(fb)
        mRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
        mRefresh.refreshComplete()
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}