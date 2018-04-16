package com.qingyii.hxtz.wmcj.mvp.model.entity

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mcxtzhang.commonadapter.rv.IHeaderHelper
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.qingyii.hxtz.R
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 17/01/08.
 */

abstract class HeaderBean<T>(private var list: List<T>) : IHeaderHelper {
    private var banner: Banner? = null

    override fun getItemLayoutId(): Int {
        return R.layout.banner_layout
    }

    override fun onBind(holder: ViewHolder) {
        //初始化控件
        banner= holder.getView(R.id.news_banner)
        //设置banner样式
        banner!!.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
        //设置图片加载器
        banner!!.setImageLoader(GlideImageLoader())
        //设置图片集合
//        banner.setImages(images);
        //设置banner动画效果
        banner!!.setBannerAnimation(Transformer.Default)
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner!!.isAutoPlay(true)
        //设置轮播时间
        banner!!.setDelayTime(3000)
        //设置指示器位置（当banner模式中有指示器时）
        banner!!.setIndicatorGravity(BannerConfig.CENTER)
//        holder.setText(R.id.tv, text)
//        holder.setOnClickListener(R.id.tv) {
//            //点击跳转到多ItemType
//            holder.itemView.context.startActivity(Intent(holder.itemView.context, RvMulTypeMulBeanActivity::class.java))
//        }
        setBannerContent()
    }

    private fun setBannerContent() {


        bindData(this!!.banner!!, this!!.list!!)

        //        //设置banner的图片集合及标题集合
        //        banner.setImages(imgUrls);
        //        banner.setBannerTitles(titles);
        //        //banner配置完成，开始轮播
        //        banner.start();

    }

    /**
     * 设置轮播图片地址集合和标题集合
     * 最后，banner.start();即可
     * @param banner
     * @param list
     */
    abstract fun bindData(banner: Banner, list: List<T>)

    internal inner class GlideImageLoader : ImageLoader() {

        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            //把path转换成ImageView
            Glide.with(context).load(path).into(imageView)

        }
    }
}
