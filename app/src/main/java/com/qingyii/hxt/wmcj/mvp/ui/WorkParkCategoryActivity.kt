package com.qingyii.hxt.wmcj.mvp.ui

import android.os.Bundle
import android.os.Message
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.OnItemClickListener
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.qingyii.hxt.R
import com.qingyii.hxt.base.app.EventBusTags
import com.qingyii.hxt.base.app.bindView
import com.qingyii.hxt.wmcj.mvp.model.entity.WorkParkData
import com.zhf.Util.Global
import com.zhf.bean.WorkParkbean
import org.simple.eventbus.EventBus

/**
 *专题，栏目分类
 */
class WorkParkCategoryActivity : BaseActivity<BasePresenter<IModel, IView>>() {
    val toolbarBackLayout: LinearLayout by bindView(R.id.toolbar_back_layout)
    val toolbarTitle: TextView by bindView(R.id.toolbar_title)
    val category: TextView by bindView(R.id.category_description)
    private val categoryRecyclerView: RecyclerView by bindView(R.id.category_recyclerView)
    private val mrecyclerview:RecyclerView by bindView(R.id.category_recyclerView1)
    private var mDatas: MutableList<WorkParkbean.DataBean.MenuItemBean> = ArrayList()
    private var mTags:MutableList<WorkParkbean.DataBean.TagItemBean> = ArrayList()
    private var mAdapter: CommonAdapter<WorkParkbean.DataBean.MenuItemBean>? = null
    private var mAdapter1: CommonAdapter<WorkParkbean.DataBean.TagItemBean>? = null
    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_work_park_category
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbarTitle.text = "专题分类"
        initAdapter()
        if(Global.workParkbean!=null){
           mDatas.clear()
           mDatas.addAll(Global.workParkbean!!.data.menu_item)
            mTags.clear();
            mTags.addAll(Global.workParkbean!!.data.tag_item)
        }

        toolbarBackLayout.setOnClickListener { finish() }
    }


    private fun initAdapter() {
        mAdapter = object : CommonAdapter<WorkParkbean.DataBean.MenuItemBean>(this@WorkParkCategoryActivity, mDatas, R.layout.category_recyclerview_item) {
            override fun convert(holder: ViewHolder?, item: WorkParkbean.DataBean.MenuItemBean) {
                holder!!.getView<TextView>(R.id.category_item_tv).text = item!!.title

            }
        }
        (mAdapter as CommonAdapter<WorkParkbean.DataBean.MenuItemBean>).setOnItemClickListener(object :OnItemClickListener<WorkParkbean.DataBean.MenuItemBean>{
            override fun onItemClick(p0: ViewGroup?, p1: View?, p2: WorkParkbean.DataBean.MenuItemBean?, p3: Int) {
                var msg = Message.obtain()
                msg.what = EventBusTags.WORKPARK_TOPIC
                msg.obj = mDatas
                msg.arg1 = p3
                EventBus.getDefault().post(msg,EventBusTags.WORKPARK)
                finish()
            }

            override fun onItemLongClick(p0: ViewGroup?, p1: View?, p2: WorkParkbean.DataBean.MenuItemBean?, p3: Int): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        mAdapter1 = object : CommonAdapter<WorkParkbean.DataBean.TagItemBean>(this@WorkParkCategoryActivity, mTags, R.layout.category_recyclerview_item) {
            override fun convert(holder: ViewHolder?, item: WorkParkbean.DataBean.TagItemBean) {
                holder!!.getView<TextView>(R.id.category_item_tv).text = item!!.title

            }
        }
        (mAdapter1 as CommonAdapter<WorkParkbean.DataBean.TagItemBean>).setOnItemClickListener(object :OnItemClickListener<WorkParkbean.DataBean.TagItemBean>{
            override fun onItemClick(p0: ViewGroup?, p1: View?, p2: WorkParkbean.DataBean.TagItemBean?, p3: Int) {
                var msg = Message.obtain()
                msg.what = EventBusTags.WORKPARK_TOPIC
                msg.obj = mDatas
                msg.arg1 = p3+mDatas.size
                EventBus.getDefault().post(msg,EventBusTags.WORKPARK)
                finish()
            }

            override fun onItemLongClick(p0: ViewGroup?, p1: View?, p2: WorkParkbean.DataBean.TagItemBean?, p3: Int): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        var layoutManager = ChipsLayoutManager.newBuilder(this@WorkParkCategoryActivity)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setScrollingEnabled(false)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build()
        var layoutManager1 = ChipsLayoutManager.newBuilder(this@WorkParkCategoryActivity)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setScrollingEnabled(false)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build()
        categoryRecyclerView.layoutManager=layoutManager
        categoryRecyclerView.adapter = mAdapter

        mrecyclerview.layoutManager=layoutManager1
        mrecyclerview.adapter=mAdapter1

    }

    override fun onDestroy() {
        super.onDestroy()
        mDatas.clear()
    }
}
