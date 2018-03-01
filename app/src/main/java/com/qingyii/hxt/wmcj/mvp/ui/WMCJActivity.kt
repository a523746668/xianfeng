package com.qingyii.hxt.wmcj.mvp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.qingyii.hxt.R
import com.qingyii.hxt.base.app.GlobalConsts
import com.qingyii.hxt.base.app.bindView
import com.qingyii.hxt.base.widget.MyFragmentTabHost
import com.zhf.zfragment.ExperienceFragment
import com.zhf.zfragment.ResultpositonFragment
import com.zhf.zfragment.TasklistFragment

class WMCJActivity : BaseActivity<BasePresenter<IModel, IView>>() {
    private val tabHost: MyFragmentTabHost by bindView(android.R.id.tabhost)

    companion object {
        private val tabTexts = arrayOf("工作动态", "任务清单", "结果排名")
        private val tabImgIds = intArrayOf(R.drawable.wmcj_workpark_selector, R.drawable.wmcj_tasklist_selector, R.drawable.wmcj_resultranking_selector)
        private var inflater: LayoutInflater? = null
        private val fragments = arrayOf<Class<*>>(com.zhf.zfragment.WorkParkFragment::class.java, TasklistFragment::class.java, ResultpositonFragment::class.java)
    }

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
        tabHost.setup(this, supportFragmentManager, R.id.wmcj_fragmentLayout)
        //初始化inflater
        inflater = LayoutInflater.from(this)
        //给tabHost添加Tab
        for (i in fragments.indices) {
            //创建新的Tab  参数作用：可通过该参数找到tabItem
            val tabItem = tabHost.newTabSpec(i.toString())
            //给tabItem设置内容view
            tabItem.setIndicator(getTabItemView(i))
            //tabItem添加到tabHost中
            /*
            * 参数1：tab标签
            *
            * 参数2：tab内容的Fragment类
            *
            * 参数3：Bundle    可以传值到Fragment
            *
            * */
            var b = Bundle()
            b.putString(GlobalConsts.TITLE,tabTexts[i])
            tabHost.addTab(tabItem, fragments[i], b)
            //tabHost去边线
            tabHost.tabWidget.setDividerDrawable(android.R.color.transparent)
        }
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_wmcj
    }

    /**
     * 加载底部导航的四个Tab
     *
     * @param index
     * @return
     */
    private fun getTabItemView(index: Int): View {

        val view = inflater!!.inflate(R.layout.wmcj_tabhost_item_layout, null)
        //找到控件
        val iv = view.findViewById(R.id.tab_img) as ImageView
        val tv = view.findViewById(R.id.tab_tv) as TextView
        //给控件设置相应内容
        iv.setImageResource(tabImgIds[index])
        tv.text = tabTexts[index]

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        tabHost.clearAllTabs()
        tabHost.removeAllViews()
    }

}
