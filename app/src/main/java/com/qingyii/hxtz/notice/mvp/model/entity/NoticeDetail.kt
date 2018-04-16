package com.qingyii.hxtz.notice.mvp.model.entity

/**
 * Created by xubo on 2017/8/18.
 */

class NoticeDetail {

    /**
     * error_msg : success
     * error_code : 0
     * data : {"accounce":{"id":100,"title":"gg","content":"ghg","author":"易新煌","readnums":0,"attachment":"","published_at":1493275508,"created_at":"2017-04-27 14:45:01","super_id":1},"users":{"total":7,"per_page":10,"current_page":1,"last_page":1,"next_page_url":null,"prev_page_url":null,"from":1,"to":7,"data":[{"id":694,"email":"18670361875@139.com","truename":"易新煌","phone":"18670361875","nickname":"易新煌","gender":"secret","avatar":"http://admin.seeo.cn/upload/avatar/20170306/5487315b1286f907165907aa8fc96619.jpg","jobname":"产品经理","birthday":"2017-08-14","political":"群众","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":"2017-08-17 10:39:36","readtime":1493276268},{"id":1077,"email":"13327216391@139.com","truename":"王斌","phone":"13327216391","nickname":"解决了","gender":"male","avatar":"http://admin.seeo.cn/upload/avatar/20170626/062ddb6c727310e76b6200b7c71f63b5.jpg","jobname":"营销总监","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"121c83f76013aecf39b","doclasttime":"2017-08-15 16:01:50","readtime":1495352940},{"id":1107,"email":"15566663333@139.com","truename":"周广慧","phone":"15566663333","nickname":"党办一级","gender":"secret","avatar":"http://admin.seeo.cn/upload/avatar/20170307/e58cc5ca94270acaceed13bc82dfedf7.jpg","jobname":"客服专员","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":null,"readtime":1496563863},{"id":1720,"email":"18673898515@139.com","truename":"潘勇","phone":"18673898515","nickname":"潘勇","gender":"male","avatar":"http://admin.seeo.cn/upload/avatar/20170612/acab0116c354964a558e65bdd07ff047.jpg","jobname":"","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":"2017-08-17 10:33:33","readtime":1497259485},{"id":1719,"email":"18975875500@139.com","truename":"胥博","phone":"18975875500","nickname":"胥博","gender":"male","avatar":"http://admin.seeo.cn/upload/avatar/20170627/f3144cefe89a60d6a1afaf7859c5076b.jpg","jobname":"","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":"2017-08-17 14:26:09","readtime":1497582278},{"id":1079,"email":"13739096199@139.com","truename":"蒋连国","phone":"13739096199","nickname":"蒋经国","gender":"male","avatar":"","jobname":"总经理","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"190e35f7e049befd1fb","doclasttime":null,"readtime":1498380785},{"id":787,"email":"21592953@qq.com","truename":"陈慧亮","phone":"13007480998","nickname":"陈慧亮","gender":"female","avatar":"http://admin.seeo.cn/upload/avatar/20170706/3621f1454cacf995530ea53652ddf8fb.jpg","jobname":"站长","birthday":"1981-01-16","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":135,"is_leader":0,"device_id":"121c83f76013aecf39b","doclasttime":"2017-08-18 09:37:10","readtime":1502956172}]}}
     */

    var error_msg: String? = null
    var error_code: Int = 0
    var data: DataBeanX? = null

    class DataBeanX {
        /**
         * accounce : {"id":100,"title":"gg","content":"ghg","author":"易新煌","readnums":0,"attachment":"","published_at":1493275508,"created_at":"2017-04-27 14:45:01","super_id":1}
         * users : {"total":7,"per_page":10,"current_page":1,"last_page":1,"next_page_url":null,"prev_page_url":null,"from":1,"to":7,"data":[{"id":694,"email":"18670361875@139.com","truename":"易新煌","phone":"18670361875","nickname":"易新煌","gender":"secret","avatar":"http://admin.seeo.cn/upload/avatar/20170306/5487315b1286f907165907aa8fc96619.jpg","jobname":"产品经理","birthday":"2017-08-14","political":"群众","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":"2017-08-17 10:39:36","readtime":1493276268},{"id":1077,"email":"13327216391@139.com","truename":"王斌","phone":"13327216391","nickname":"解决了","gender":"male","avatar":"http://admin.seeo.cn/upload/avatar/20170626/062ddb6c727310e76b6200b7c71f63b5.jpg","jobname":"营销总监","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"121c83f76013aecf39b","doclasttime":"2017-08-15 16:01:50","readtime":1495352940},{"id":1107,"email":"15566663333@139.com","truename":"周广慧","phone":"15566663333","nickname":"党办一级","gender":"secret","avatar":"http://admin.seeo.cn/upload/avatar/20170307/e58cc5ca94270acaceed13bc82dfedf7.jpg","jobname":"客服专员","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":null,"readtime":1496563863},{"id":1720,"email":"18673898515@139.com","truename":"潘勇","phone":"18673898515","nickname":"潘勇","gender":"male","avatar":"http://admin.seeo.cn/upload/avatar/20170612/acab0116c354964a558e65bdd07ff047.jpg","jobname":"","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":"2017-08-17 10:33:33","readtime":1497259485},{"id":1719,"email":"18975875500@139.com","truename":"胥博","phone":"18975875500","nickname":"胥博","gender":"male","avatar":"http://admin.seeo.cn/upload/avatar/20170627/f3144cefe89a60d6a1afaf7859c5076b.jpg","jobname":"","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":"2017-08-17 14:26:09","readtime":1497582278},{"id":1079,"email":"13739096199@139.com","truename":"蒋连国","phone":"13739096199","nickname":"蒋经国","gender":"male","avatar":"","jobname":"总经理","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"190e35f7e049befd1fb","doclasttime":null,"readtime":1498380785},{"id":787,"email":"21592953@qq.com","truename":"陈慧亮","phone":"13007480998","nickname":"陈慧亮","gender":"female","avatar":"http://admin.seeo.cn/upload/avatar/20170706/3621f1454cacf995530ea53652ddf8fb.jpg","jobname":"站长","birthday":"1981-01-16","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":135,"is_leader":0,"device_id":"121c83f76013aecf39b","doclasttime":"2017-08-18 09:37:10","readtime":1502956172}]}
         */

        var accounce: AccounceBean? = null
        var users: UsersBean? = null

        class AccounceBean {
            /**
             * id : 100
             * title : gg
             * content : ghg
             * author : 易新煌
             * readnums : 0
             * attachment :
             * published_at : 1493275508
             * created_at : 2017-04-27 14:45:01
             * super_id : 1
             */

            var id: Int = 0
            var title: String? = null
            var content: String? = null
            var author: String? = null
            var readnums: Int = 0
            var attachment: String? = null
            var published_at: Int = 0
            var created_at: String? = null
            var super_id: Int = 0
        }

        class UsersBean {
            /**
             * total : 7
             * per_page : 10
             * current_page : 1
             * last_page : 1
             * next_page_url : null
             * prev_page_url : null
             * from : 1
             * to : 7
             * data : [{"id":694,"email":"18670361875@139.com","truename":"易新煌","phone":"18670361875","nickname":"易新煌","gender":"secret","avatar":"http://admin.seeo.cn/upload/avatar/20170306/5487315b1286f907165907aa8fc96619.jpg","jobname":"产品经理","birthday":"2017-08-14","political":"群众","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":"2017-08-17 10:39:36","readtime":1493276268},{"id":1077,"email":"13327216391@139.com","truename":"王斌","phone":"13327216391","nickname":"解决了","gender":"male","avatar":"http://admin.seeo.cn/upload/avatar/20170626/062ddb6c727310e76b6200b7c71f63b5.jpg","jobname":"营销总监","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"121c83f76013aecf39b","doclasttime":"2017-08-15 16:01:50","readtime":1495352940},{"id":1107,"email":"15566663333@139.com","truename":"周广慧","phone":"15566663333","nickname":"党办一级","gender":"secret","avatar":"http://admin.seeo.cn/upload/avatar/20170307/e58cc5ca94270acaceed13bc82dfedf7.jpg","jobname":"客服专员","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":null,"readtime":1496563863},{"id":1720,"email":"18673898515@139.com","truename":"潘勇","phone":"18673898515","nickname":"潘勇","gender":"male","avatar":"http://admin.seeo.cn/upload/avatar/20170612/acab0116c354964a558e65bdd07ff047.jpg","jobname":"","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":"2017-08-17 10:33:33","readtime":1497259485},{"id":1719,"email":"18975875500@139.com","truename":"胥博","phone":"18975875500","nickname":"胥博","gender":"male","avatar":"http://admin.seeo.cn/upload/avatar/20170627/f3144cefe89a60d6a1afaf7859c5076b.jpg","jobname":"","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"","doclasttime":"2017-08-17 14:26:09","readtime":1497582278},{"id":1079,"email":"13739096199@139.com","truename":"蒋连国","phone":"13739096199","nickname":"蒋经国","gender":"male","avatar":"","jobname":"总经理","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":137,"is_leader":0,"device_id":"190e35f7e049befd1fb","doclasttime":null,"readtime":1498380785},{"id":787,"email":"21592953@qq.com","truename":"陈慧亮","phone":"13007480998","nickname":"陈慧亮","gender":"female","avatar":"http://admin.seeo.cn/upload/avatar/20170706/3621f1454cacf995530ea53652ddf8fb.jpg","jobname":"站长","birthday":"1981-01-16","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","account_id":135,"is_leader":0,"device_id":"121c83f76013aecf39b","doclasttime":"2017-08-18 09:37:10","readtime":1502956172}]
             */

            var total: Int = 0
            var per_page: Int = 0
            var current_page: Int = 0
            var last_page: Int = 0
            var next_page_url: Any? = null
            var prev_page_url: Any? = null
            var from: Int = 0
            var to: Int = 0
            var data: List<DataBean>? = null

            class DataBean {
                /**
                 * id : 694
                 * email : 18670361875@139.com
                 * truename : 易新煌
                 * phone : 18670361875
                 * nickname : 易新煌
                 * gender : secret
                 * avatar : http://admin.seeo.cn/upload/avatar/20170306/5487315b1286f907165907aa8fc96619.jpg
                 * jobname : 产品经理
                 * birthday : 2017-08-14
                 * political : 群众
                 * joinparty_at : 0000-00-00
                 * idcode :
                 * account_id : 137
                 * is_leader : 0
                 * device_id :
                 * doclasttime : 2017-08-17 10:39:36
                 * readtime : 1493276268
                 */

                var id: Int = 0
                var email: String? = null
                var truename: String? = null
                var phone: String? = null
                var nickname: String? = null
                var gender: String? = null
                var avatar: String? = null
                var jobname: String? = null
                var birthday: String? = null
                var political: String? = null
                var joinparty_at: String? = null
                var idcode: String? = null
                var account_id: Int = 0
                var is_leader: Int = 0
                var device_id: String? = null
                var doclasttime: String? = null
                var readtime: Int = 0
            }
        }
    }
}
