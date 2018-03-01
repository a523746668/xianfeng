package com.qingyii.hxt.notice.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.UiUtils
import com.qingyii.hxt.R
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter
import com.qingyii.hxt.base.app.bindView
import com.qingyii.hxt.base.mvp.contract.CommonContract
import com.qingyii.hxt.base.widget.AutoLoadMoreRecyclerView
import com.qingyii.hxt.notice.di.component.DaggerNoticeComponent
import com.qingyii.hxt.notice.di.module.NoticeModule
import com.qingyii.hxt.notice.mvp.model.entity.NoticeList
import com.qingyii.hxt.notice.mvp.presenter.NoticePresenter
import com.zhy.autolayout.AutoLinearLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class NoticeActivity : BaseActivity<NoticePresenter>(), CommonContract.NotifyListView {
    val toolbarTitle: TextView by bindView(R.id.toolbar_title)
    val mRecyclerView: AutoLoadMoreRecyclerView by bindView(R.id.notice_recyclerView)
    val emptyView: AutoLinearLayout by bindView(R.id.empty_view)
    val mRefreshLayout: SwipeRefreshLayout by bindView(R.id.notice_swipeRefreshLayout)

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerNoticeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .noticeModule(NoticeModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_notice
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbarTitle.text = "往前公告"
        mPresenter.requestNoticeLists(true)
        mRefreshLayout.setOnRefreshListener {
            (mRecyclerView.adapter as BaseRecyclerAdapter<*>).data.clear()
            mRecyclerView.adapter.notifyDataSetChanged()
            mRecyclerView.notifyMoreLoaded()
            mPresenter.requestNoticeLists(true)
        }
    }

    override fun showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { mRefreshLayout.isRefreshing = true }
    }

    override fun launchActivity(intent: Intent?) {
        checkNotNull(intent)
        UiUtils.startActivity(intent)
    }

    override fun startLoadMore() {
        mRecyclerView.notifyMoreLoaded()
        (mRecyclerView.adapter as BaseRecyclerAdapter<*>).showFooter(true)
    }

    override fun hideLoading() {
        mRefreshLayout.isRefreshing = false
    }

    override fun killMyself() {
       finish()
    }

    override fun setAdapter(adapter: BaseRecyclerAdapter<*>?) {
        mRecyclerView.setAutoLayoutManager(LinearLayoutManager(this)).setAutoHasFixedSize(true)
                .setAutoItemAnimator(DefaultItemAnimator()).setAutoAdapter(adapter)
        mRecyclerView.setEmptyView(emptyView)
        mRecyclerView.setOnLoadMoreListener { mPresenter.requestNoticeLists(false) }
        adapter?.setOnItemClickListener(object : BaseRecyclerAdapter.OnItemClickListener<NoticeList.DataBean> {
            override fun onItemClick(data: NoticeList.DataBean, view: View, position: Int) {
                val intent = Intent(this@NoticeActivity, NoticeDetailsActivity::class.java)
                //修改缓存状态 为已阅读，数据库数据 在进入下一个页面后修改
                if (data.is_read == 0) {
                    data.is_read = 1
                    adapter.notifyDataSetChanged()
                }
                intent.putExtra(NoticeDetailsActivity.PARAMS, data.id.toString())
                launchActivity(intent)
                killMyself()
            }

            override fun onItemLongClick(data: NoticeList.DataBean, view: View, position: Int) {

            }
        })
    }

    override fun showMessage(message: String?) {
        checkNotNull(message)
        UiUtils.snackbarText(message)
    }

    override fun endLoadMore() {
        (mRecyclerView.adapter as BaseRecyclerAdapter<*>).hideFooter()
    }

    override fun notifyAllLoad() {
        mRecyclerView.notifyAllLoaded()
    }
}

