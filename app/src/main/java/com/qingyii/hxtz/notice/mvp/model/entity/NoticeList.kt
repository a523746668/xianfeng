package com.qingyii.hxtz.notice.mvp.model.entity

/**
 * Created by xubo on 2017/6/13.
 */

class NoticeList {

    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":5,"title":"111","content":"
     *
     *他强调，....<\/p>","readnums":0,"attachment":"","created_at":"2016-09-02 15:41:07","author":""}]
     */

    var error_msg: String? = null
    var error_code: Int = 0
    var data: List<DataBean>? = null

    class DataBean {
        /**
         * id : 5
         * title : 111
         * content :
         *
         *他强调，....
         * readnums : 0
         * attachment :
         * created_at : 2016-09-02 15:41:07
         * author :
         */

        var id: Int = 0
        var title: String? = null
        var content: String? = null
        var readnums: Int = 0
        var is_read: Int = 0
        var attachment: String? = null
        var created_at: String? = null
        var author: String? = null
    }
}
