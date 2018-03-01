package com.qingyii.hxt.training.details.schedule.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.UiUtils
import com.qingyii.hxt.R
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter
import com.qingyii.hxt.base.app.GlobalConsts
import com.qingyii.hxt.base.app.bindView
import com.qingyii.hxt.base.mvp.contract.CommonContract
import com.qingyii.hxt.base.widget.AutoLoadMoreRecyclerView
import com.qingyii.hxt.training.details.schedule.di.component.DaggerTrainNoticeComponent
import com.qingyii.hxt.training.details.schedule.di.module.TrainNoticeModule
import com.qingyii.hxt.training.details.schedule.di.module.entity.TrainNoticeList
import com.qingyii.hxt.training.details.schedule.mvp.presenter.TrainNoticePresenter
import com.zhy.autolayout.AutoLinearLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class TrainNoticeActivity : BaseActivity<TrainNoticePresenter>(), CommonContract.TrainingNoticeContractView {
    val toolbarBackLayout: LinearLayout by bindView(R.id.toolbar_back_layout)
    private var trainId: Int = 0
    val toolbarTitle: TextView by bindView(R.id.toolbar_title)
    val mRecyclerView: AutoLoadMoreRecyclerView by bindView(R.id.notice_recyclerView)
    val emptyView: AutoLinearLayout by bindView(R.id.empty_view)
    val mRefreshLayout: SwipeRefreshLayout by bindView(R.id.notice_swipeRefreshLayout)

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerTrainNoticeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .trainNoticeModule(TrainNoticeModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        trainId = intent.getIntExtra(GlobalConsts.TRAIN_ID, 0)
        if (trainId <= 0) {
            showMessage("培训id出错")
            return 0
        }
        return R.layout.activity_notice
    }


    override fun initData(savedInstanceState: Bundle?) {
        toolbarTitle.text = "培训通知"
        mPresenter.requestTrainNoticeLists(trainId)
        mRefreshLayout.setOnRefreshListener {
            (mRecyclerView.adapter as BaseRecyclerAdapter<*>).data.clear()
            mRecyclerView.adapter.notifyDataSetChanged()
            mRecyclerView.notifyMoreLoaded()
            mPresenter.requestTrainNoticeLists(trainId)
        }
        toolbarBackLayout.setOnClickListener {
            finish()
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
        mRecyclerView.setOnLoadMoreListener { mPresenter.requestTrainNoticeLists(trainId) }
        adapter?.setOnItemClickListener(object : BaseRecyclerAdapter.OnItemClickListener<TrainNoticeList.DataBean> {
            override fun onItemClick(data: TrainNoticeList.DataBean, view: View, position: Int) {
                /**
                 * Intent intent = new Intent(TrainInformActivity.this, NotifyDetailsActivity.class);
                intent.putExtra("notify", iDataBeanlist.get(i));
                startActivity(intent);
                 */
                val intent = Intent(this@TrainNoticeActivity, TrainNoticeDetailsActivity::class.java)
                //修改缓存状态 为已阅读，数据库数据 在进入下一个页面后修改
                intent.putExtra(GlobalConsts.TRAIN_ID, data.id)
                launchActivity(intent)
            }

            override fun onItemLongClick(data: TrainNoticeList.DataBean, view: View, position: Int) {

            }
        })
    }

    override fun showMessage(message: String?) {
        checkNotNull(message)
        UiUtils.snackbarText(message)
    }
}
