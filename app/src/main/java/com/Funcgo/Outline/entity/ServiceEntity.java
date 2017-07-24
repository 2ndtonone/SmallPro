package com.Funcgo.Outline.entity;

import java.io.Serializable;

/**
 * Created by ydh on 2017/7/18.
 */

public class ServiceEntity implements Serializable {

    /**
     * ret : 200
     * msg : is Ok!
     * data : {"service":"member.jp02.bell.wiki","country_service":"日本","port":10129,"method":"rc4-md5","pass":"177438","protocol":"origin","obfs":"plain","t":0,"u":0,"d":0,"transfer_enable":5368709120000000,"reg_date":1497983271,"closing_date":1500575271,"user_order":{"id":14,"account_number":"13120362645","service_time":1500575271,"reg_date":1497983271,"sale_price":"25.99","level":"初级","info":"初级 - 25.99 元 - 30 天","seller_id":"2088421922928791"}}
     */

    public int ret;
    public String msg;
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * service : member.jp02.bell.wiki
         * country_service : 日本
         * port : 10129
         * method : rc4-md5
         * pass : 177438
         * protocol : origin
         * obfs : plain
         * t : 0
         * u : 0
         * d : 0
         * transfer_enable : 5368709120000000
         * reg_date : 1497983271
         * closing_date : 1500575271
         * user_order : {"id":14,"account_number":"13120362645","service_time":1500575271,"reg_date":1497983271,"sale_price":"25.99","level":"初级","info":"初级 - 25.99 元 - 30 天","seller_id":"2088421922928791"}
         */

        public String service;
        public String country_service;
        public int port;
        public String method;
        public String pass;
        public String protocol;
        public String obfs;
        public int t;
        public int u;
        public int d;
        public long transfer_enable;
        public int reg_date;
        public int closing_date;
        public UserOrderBean user_order;

        public static class UserOrderBean implements Serializable {
            /**
             * id : 14
             * account_number : 13120362645
             * service_time : 1500575271
             * reg_date : 1497983271
             * sale_price : 25.99
             * level : 初级
             * info : 初级 - 25.99 元 - 30 天
             * seller_id : 2088421922928791
             */

            public int id;
            public String account_number;
            public int service_time;
            public int reg_date;
            public String sale_price;
            public String level;
            public String info;
            public String seller_id;
        }
    }
}
