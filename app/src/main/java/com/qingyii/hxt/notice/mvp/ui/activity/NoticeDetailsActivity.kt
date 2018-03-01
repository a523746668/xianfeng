package com.qingyii.hxt.notice.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.qingyii.hxt.R
import com.qingyii.hxt.base.app.bindView
import com.qingyii.hxt.base.mvp.contract.CommonContract
import com.qingyii.hxt.notice.di.component.DaggerNoticeDetailsComponent
import com.qingyii.hxt.notice.di.module.NoticeDetailsModule
import com.qingyii.hxt.notice.mvp.model.entity.NoticeDetail
import com.qingyii.hxt.notice.mvp.model.entity.NoticeList
import com.qingyii.hxt.notice.mvp.presenter.NoticeDetailsPresenter

class NoticeDetailsActivity : BaseActivity<NoticeDetailsPresenter>(), CommonContract.NoticeView {

    val noticeDetailsTitle: TextView by bindView(R.id.notice_details_title)
    val noticeDetailsAuthorTime: TextView by bindView(R.id.notice_details_author_time)
    val noticeDetailsContent: TextView by bindView(R.id.notice_details_content)
    val noticeAlreadyTv: TextView by bindView(R.id.notice_already_tv)
    val oldNoticeTv: TextView by bindView(R.id.old_notice_tv)
    val mBackLayout: LinearLayout by bindView(R.id.toolbar_back_layout)
    val mContentLayout: LinearLayout by bindView(R.id.notice_details_content_layout)
    val toolbarTitle: TextView by bindView(R.id.toolbar_title)
//    val mRefreshLayout: SwipeRefreshLayout by bindView(R.id.notice_details_swipeRefreshLayout)

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerNoticeDetailsComponent.builder()
                .appComponent(appComponent)
                .noticeDetailsModule(NoticeDetailsModule(this))
                .build()
                .inject(this)
        if (intent.hasExtra(PARAMS) && intent.getStringExtra(PARAMS) != null) {
            id = intent.getStringExtra(PARAMS)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null && intent.hasExtra(PARAMS) && intent.getStringExtra(PARAMS) != null) {
            id = intent.getStringExtra(PARAMS)
        } else {
            id = "last"
        }
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_notice_details
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbarTitle.text = "公告"
        setListener()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.requestNoticeListsForDetails(id)

    }
    private fun setListener() {
        mBackLayout.setOnClickListener {
            killMyself()
        }
        /**
         * 往期公告
         */
        oldNoticeTv.setOnClickListener {
            launchActivity(Intent(NoticeDetailsActivity@ this, NoticeActivity::class.java))
            mContentLayout.visibility = View.GONE
        }
    }

    override fun updateUI(detail: NoticeDetail?) {
//        mRefreshLayout.isEnabled = false
        mContentLayout.visibility = View.VISIBLE
        noticeDetailsContent.text = detail?.data?.accounce?.content
        noticeDetailsTitle.text = detail?.data?.accounce?.title
        noticeDetailsAuthorTime.text = detail?.data?.accounce?.author + " " + detail?.data?.accounce?.created_at
    }

    companion object {
        private var id = "last"
        val PARAMS = "ID"
        var data: NoticeList.DataBean? = null
    }

    override fun showLoading() {
//        Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { mRefreshLayout.isRefreshing = true }
    }

    override fun launchActivity(intent: Intent?) {
        startActivity(intent)
    }


    override fun hideLoading() {
//        mRefreshLayout.isRefreshing = false
    }

    override fun killMyself() {
        finish()
    }


    override fun showMessage(message: String?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        id = "last"
    }

}
