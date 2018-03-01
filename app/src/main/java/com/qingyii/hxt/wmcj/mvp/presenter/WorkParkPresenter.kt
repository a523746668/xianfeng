package com.qingyii.hxt.wmcj.mvp.presenter

import android.app.Application
import android.content.Intent
import android.graphics.Rect
import android.widget.TextView
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.widget.imageloader.ImageLoader
import com.jess.arms.widget.imageloader.glide.GlideImageConfig
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.mcxtzhang.commonadapter.rv.mul.BaseMulTypeAdapter
import com.qingyii.hxt.R
import com.qingyii.hxt.base.app.GlobalConsts
import com.qingyii.hxt.base.mvp.contract.CommonContract
import com.qingyii.hxt.base.utils.RxUtils
import com.qingyii.hxt.wmcj.mvp.model.entity.HeaderBean
import com.qingyii.hxt.wmcj.mvp.model.entity.WorkParkList
import com.qingyii.hxt.wmcj.mvp.ui.WorkParkDetailsActivity
import com.youth.banner.Banner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import javax.inject.Inject


@ActivityScope
class WorkParkPresenter @Inject
constructor(model: CommonContract.WorkParkListModel, rootView: CommonContract.WorkParkListView, private var mErrorHandler: RxErrorHandler?, private var mApplication: Application?, private var mImageLoader: ImageLoader?, private var mAppManager: AppManager?) : BasePresenter<CommonContract.WorkParkListModel, CommonContract.WorkParkListView>(model, rootView) {
    private var mAdapter: HeaderFooterAdapter? = null
    //除轮播图所有的itemlist
    private var totalList: MutableList<WorkParkList.ItemEntity> = ArrayList()
    //放新闻轮播的itemlist
    private var bannerList: MutableList<WorkParkList.ItemEntity> = ArrayList()
    private var page: Int = 1
    override fun onDestroy() {
        super.onDestroy()
        this.mErrorHandler = null
        this.mAppManager = null
        this.mImageLoader = null
        this.mApplication = null
    }


    fun requestWorkParkList(pullToRefresh: Boolean) {
        if (pullToRefresh) page = 1

        if (mAdapter == null) {
            mAdapter = HeaderFooterAdapter(object : BaseMulTypeAdapter<WorkParkList.ItemEntity>(mAppManager!!.currentActivity, totalList) {
                override fun convert(holder: ViewHolder, item: WorkParkList.ItemEntity) {
                    super.convert(holder, item)
                    holder.itemView.setOnClickListener{
                        val webUrl = item.link!!.weburl
                        val intent = Intent(mApplication, WorkParkDetailsActivity::class.java)
                        intent.putExtra(GlobalConsts.ITEM_WEBURL, webUrl)
                        mAppManager!!.currentActivity.startActivity(intent)
                    }
                    if (item.updateTime != null) holder.setText(R.id.workpark_include_tv_time, getUpdateTime(item.updateTime))
                    if (item.commentsall != null) holder.setText(R.id.workpark_include_tv_comment, item.commentsall)
                    if (item.title != null) holder.setText(R.id.workpark_lv_title, item.title)
                    setDrawLeft(Rect(0, 0, 35, 36), R.drawable.workpark_time, holder.getView(R.id.workpark_include_tv_time))
                    setDrawLeft(Rect(0, 0, 27, 27), R.drawable.workpark_comment, holder.getView(R.id.workpark_include_tv_comment))
                    setDrawLeft(Rect(0, 0, 35, 23), R.drawable.workpark_read, holder.getView(R.id.workpark_include_tv_read))
                    when (item.itemLayoutId) {
                        R.layout.workpark_content_layout1 ->
                            mImageLoader?.loadImage(mAppManager!!.currentActivity,
                                    GlideImageConfig
                                            .builder()
                                            .url(item.thumbnail)
                                            .imageView(holder.getView(R.id.workpark_lv1_iv))
                                            .build())
                        R.layout.workpark_content_layout2 -> {
                            val arr = arrayOf(R.id.workpark_lv2_iv1, R.id.workpark_lv2_iv2, R.id.workpark_lv2_iv3)
                            arr.forEachIndexed { index, id ->
                                mImageLoader?.loadImage(mAppManager!!.currentActivity,
                                        GlideImageConfig
                                                .builder()
                                                .url(item.style!!.images!![index])
                                                .imageView(holder.getView(id))
                                                .build())

                            }
                        }
                        R.layout.workpark_content_layout3 -> {
                        }
                        R.layout.workpark_content_layout4 -> {
                            mImageLoader?.loadImage(mAppManager!!.currentActivity,
                                    GlideImageConfig
                                            .builder()
                                            .url(item.thumbnail)
                                            .imageView(holder.getView(R.id.workpark_lv4_iv_thumb))
                                            .build())
                        }
                    }

                }
            })
            mRootView.setAdapter(mAdapter)
        }
        mModel.getWorkParkList(page)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .retryWhen(RetryWithDelay(3, 2))
                .doOnSubscribe {
                    if (pullToRefresh)
                        mRootView.showLoading()//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore()//显示上拉加载更多的进度条
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    if (pullToRefresh)
                        mRootView.hideLoading()//隐藏上拉刷新的进度条
                    else
                        mRootView.endLoadMore()//隐藏下拉加载更多的进度条
                }.compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(
                        object : ErrorHandleSubscriber<List<WorkParkList>>(mErrorHandler) {
                            override fun onNext(entity: List<WorkParkList>) {
                                for (i in entity.indices) {
                                    when (entity[i].type) {
                                    //普通新闻
                                        "list" -> {
                                            for (j in 0 until entity[i].item!!.size) {
                                                //单图
                                                if (entity[i].item!![j].style!!.images == null && entity[i].item!![j].phvideo == null) {
                                                    entity[i].item!![j].viewType = 0
                                                    //多图
                                                } else if (entity[i].item!![j].style!!.images != null) {
                                                    entity[i].item!![j].viewType = 1
                                                    //视频
                                                } else {
                                                    entity[i].item!![j].viewType = 3
                                                }
                                            }
                                            if(pullToRefresh)totalList.clear()
                                            try {
                                                totalList
                                                        .filter { it.staticId==entity[i].item!!.last().staticId }
                                                        .forEach { return }
//                                                if (!entity[i].item!!.isEmpty()&&!totalList.isEmpty()&&totalList.last().staticId == entity[i].item!!.last().staticId) return
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                            if (entity[i].item!!.size < 20) mRootView.notifyAllLoad()
                                            page++
                                            totalList.addAll(entity[i].item!!)
                                        }
                                    //轮播新闻
                                        "focus" -> {
                                            if (pullToRefresh) {
                                                bannerList!!.clear()
                                                bannerList!!.addAll(entity[i].item!!)
                                                var hb = object : HeaderBean<WorkParkList.ItemEntity>(bannerList) {
                                                    override fun bindData(banner: Banner, list: List<WorkParkList.ItemEntity>) {

                                                        var imgUrls: MutableList<String> = ArrayList()
                                                        var titles: MutableList<String> = ArrayList()

                                                        for (i in list) {
                                                            imgUrls.add(i.thumbnail!!)
                                                            titles.add(i.title!!)
                                                        }
                                                        //设置banner的图片集合及标题集合
                                                        banner.setImages(imgUrls)
                                                        banner.setBannerTitles(titles)
                                                        //banner配置完成，开始轮播
                                                        banner.start()
                                                    }
                                                }
                                                mAdapter!!.setHeaderView(0, hb)
                                            }
                                        }

                                    }
                                }
                                mAdapter!!.notifyDataSetChanged()
                            }

                        })


    }

    //动态设置drawableLeft大小
    private fun setDrawLeft(bounds: Rect, resId: Int, vararg view: TextView) {
        for (textView in view) {
            val drawable = mAppManager!!.currentActivity.resources.getDrawable(resId)
            drawable.bounds = bounds
            textView.setCompoundDrawables(drawable, null, null, null)
        }
    }

    private fun getUpdateTime(sourceTime: String?): String? {
        if (sourceTime == null) {
            return null
        }
        return sourceTime.substring(0, 10)


    }

}