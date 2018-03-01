package com.qingyii.hxt.wmcj.mvp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.jess.arms.widget.autolayout.AutoTabLayout
import com.qingyii.hxt.R
import com.qingyii.hxt.base.app.EventBusTags
import com.qingyii.hxt.base.app.GlobalConsts
import com.qingyii.hxt.base.app.bindView
import com.qingyii.hxt.meeting.mvp.ui.fragment.MeetingFragment
import com.qingyii.hxt.wmcj.mvp.model.entity.WorkParkData
import com.qingyii.hxt.wmcj.mvp.ui.adapter.MYViewPagerAdapter
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode
import java.util.*

/**
 * 工作动态，workpark为易新煌命名
 * Created by xubo on 2017/9/7.
 */
class WorkParkFragment : BaseFragment<BasePresenter<IModel, IView>>() {
    val toolbarBack: Button by bindView(R.id.toolbar_back)
    val toolbarBackLayout: LinearLayout by bindView(R.id.toolbar_back_layout)
    val toolbarTitle: TextView by bindView(R.id.toolbar_title)
    val toolbarRightTv: TextView by bindView(R.id.toolbar_right_tv)
    val toolbarRight: Button by bindView(R.id.toolbar_right)
    val toolbarRightLayout: LinearLayout by bindView(R.id.toolbar_right_layout)
    val tabLayout: AutoTabLayout by bindView(R.id.workpark_tablayout)
    val viewPager: ViewPager by bindView(R.id.workpark_viewpager)

    companion object {
        var title: String? = null
        var titlesList = ArrayList<WorkParkData>()
        private var inflater: LayoutInflater? = null
    }

    private var adapter: MYViewPagerAdapter? = null

    private var fragmentList: MutableList<Fragment> = ArrayList()

    override fun initData(savedInstanceState: Bundle?) {
        //初始化inflater
        inflater = LayoutInflater.from(activity)
        toolbarRightTv.visibility = View.VISIBLE
        toolbarRightTv.text = "专题"
        //0栏目，1专题
        toolbarRightLayout.setTag(TAG.hashCode(), 0)
        toolbarTitle.text = title
        initColumn()
        tabLayout.setupWithViewPager(viewPager)
        toolbarRightLayout.setOnClickListener {
            when (toolbarRightLayout.getTag(TAG.hashCode())) {
            //当前页面为栏目,按钮显示为专题
                0 -> {
                    activity.startActivity(Intent(activity, WorkParkCategoryActivity::class.java))
                }
            //当前页面为专题，按钮显示为返回栏目
                1 -> {
                    initColumn()
                    toolbarRightTv.text = "专题"
                    toolbarRightLayout.setTag(TAG.hashCode(), 0)
                }
            }
        }
        toolbarBackLayout.setOnClickListener { activity.finish() }
    }

    private fun initColumn() {
        titlesList.clear()
        titlesList.add(WorkParkData(0, "栏目一级"))
        titlesList.add(WorkParkData(1, "栏目二"))
        titlesList.add(WorkParkData(2, "栏目三"))
        fragmentList.clear()
        viewPager.removeAllViews()
        for (i in titlesList.indices) {
            fragmentList.add(WorkParkItemFragment())
        }
        if (adapter == null) {
            adapter = MYViewPagerAdapter(context, childFragmentManager, fragmentList, titlesList)
            viewPager.adapter = adapter
        }
        adapter!!.notifyDataSetChanged()
        viewPager.offscreenPageLimit = titlesList.size
        viewPager.currentItem = 0
    }

    override fun setData(data: Any?) {
    }

    override fun setupFragmentComponent(appComponent: AppComponent?) {
        title = arguments.getString(GlobalConsts.TITLE)
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_workpark, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.removeAllViews()
    }


    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.WORKPARK)
    fun onEventBus(msg: Message) {
        when (msg.what) {
            EventBusTags.WORKPARK_TOPIC -> {
                titlesList.clear()
                titlesList.addAll(msg.obj as List<WorkParkData>)
                fragmentList.clear()
                viewPager.removeAllViews()
//                viewPager.offscreenPageLimit = titlesList.size
                titlesList.forEach {
                    fragmentList.add(MeetingFragment())
                }
                adapter!!.notifyDataSetChanged()
                viewPager.offscreenPageLimit = titlesList.size
                viewPager!!.currentItem = msg.arg1
                toolbarRightTv.text = "返回栏目"
                toolbarRightLayout.setTag(TAG.hashCode(), 1)
            }
        }
    }

}