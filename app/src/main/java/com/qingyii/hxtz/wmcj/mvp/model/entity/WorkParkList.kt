package com.qingyii.hxtz.wmcj.mvp.model.entity

import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.mcxtzhang.commonadapter.rv.mul.IMulTypeHelper
import com.qingyii.hxtz.R

/**
 * Created by xubo on 2017/9/9.
 */
class WorkParkList {


    var listId: String? = null
    var type: String? = null
    var expiredTime: Int = 0
    var currentPage: Int = 0
    var totalPage: Int = 0
    var item: List<ItemEntity>? = null

    class ItemEntity : IMulTypeHelper {
        /**
         * thumbnail : http://d.ifengimg.com/w132_h94_q80/p1.ifengimg.com/cmpp/2016/12/12/a7584438ada3e0bb858d6e7c07cb0041_size34_w168_h120.jpg
         * online : 1
         * title : 默克尔：坚持“一个中国” 不可能跟随特朗普的脚步
         * showType : 0
         * source : 环球时报
         * style : {"type":"slides","images":["http://d.ifengimg.com/w155_h107_q80/p1.ifengimg.com/cmpp/2016/12/13/4496f35045cb84410de094dbf2439512_size69_w206_h142.jpg","http://d.ifengimg.com/w155_h107_q80/p0.ifengimg.com/cmpp/2016/12/13/392cd435dc42f47a36c5f811fa26418e_size48_w206_h142.jpg","http://d.ifengimg.com/w155_h107_q80/p0.ifengimg.com/cmpp/2016/12/13/5372acad00d67b5530316d40f9f0ae3b_size41_w206_h142.jpg"],"slideCount":8}
         * phvideo : {"channelName":"网罗天下","length":42}
         * subscribe : {"cateid":"","type":"source","catename":"环球时报","logo":"","description":""}
         * updateTime : 2016/12/13 07:30:08
         * id : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_030180050405180&channelKey=Y21wcF8xNzAwN183MTlfNzU=
         * documentId : cmpp_030180050405180
         * type : doc
         * hasVideo : true
         * commentsUrl : http://news.ifeng.com/a/20161212/50405180_0.shtml
         * comments : 1811
         * commentsall : 5822
         * styleType : topic
         * link : {"type":"doc","url":"http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_030180050405180&channelKey=Y21wcF8xNzAwN183MTlfNzU=","weburl":"http://share.iclient.ifeng.com/sharenews.f?aid=cmpp_030180050405180"}
         * reftype : editor
         * simId : clusterId_3727335
         */

        var thumbnail: String? = null
        var online: String? = null
        var title: String? = null
        var showType: String? = null
        var source: String? = null
        var style: StyleEntity? = null
        var phvideo: PhvideoEntity? = null
        var subscribe: SubscribeEntity? = null
        var updateTime: String? = null
        var id: String? = null
        var documentId: String? = null
        var type: String? = null
        var hasVideo: Boolean = false
        var commentsUrl: String? = null
        var comments: String? = null
        var commentsall: String? = null
        var styleType: String? = null
        var link: LinkEntity? = null
        var reftype: String? = null
        var simId: String? = null
        var staticId: String? = null

        //自己加的viewType用于listview的adapter,默认取纯文字布局
        var viewType: Int = 2
        override fun getItemLayoutId(): Int {
            when(viewType){
                0-> return R.layout.workpark_content_layout1
                1-> return R.layout.workpark_content_layout2
                2-> return R.layout.workpark_content_layout3
                3-> return R.layout.workpark_content_layout4
            }
            return 0
        }

        override fun onBind(p0: ViewHolder?) {
        }
        class StyleEntity {
            /**
             * type : slides
             * images : ["http://d.ifengimg.com/w155_h107_q80/p1.ifengimg.com/cmpp/2016/12/13/4496f35045cb84410de094dbf2439512_size69_w206_h142.jpg","http://d.ifengimg.com/w155_h107_q80/p0.ifengimg.com/cmpp/2016/12/13/392cd435dc42f47a36c5f811fa26418e_size48_w206_h142.jpg","http://d.ifengimg.com/w155_h107_q80/p0.ifengimg.com/cmpp/2016/12/13/5372acad00d67b5530316d40f9f0ae3b_size41_w206_h142.jpg"]
             * slideCount : 8
             */

            var type: String? = null
            var slideCount: Int = 0
            var images: List<String>? = null
        }

        class PhvideoEntity {
            /**
             * channelName : 网罗天下
             * length : 42
             */

            var channelName: String? = null
            var length: String? = null
        }

        class SubscribeEntity {
            /**
             * cateid :
             * type : source
             * catename : 环球时报
             * logo :
             * description :
             */

            var cateid: String? = null
            var type: String? = null
            var catename: String? = null
            var logo: String? = null
            var description: String? = null
        }

        class LinkEntity {
            /**
             * type : doc
             * url : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_030180050405180&channelKey=Y21wcF8xNzAwN183MTlfNzU=
             * weburl : http://share.iclient.ifeng.com/sharenews.f?aid=cmpp_030180050405180
             */

            var type: String? = null
            var url: String? = null
            var weburl: String? = null
        }
    }
}
