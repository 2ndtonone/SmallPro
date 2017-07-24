package com.Funcgo.Outline.entity;

/**
 * Created by ydh on 2017/7/16.
 */

public class UserInfo {


    /**
     * id : 17
     * account_number : 13120362645
     * union_type : Own
     * union_id : null
     * name : bellchen
     * headimgurl : null
     * sex : null
     * api_token : 9617df80c52dd553a107e170166957e0772a7bd77db36b552ae1d94fda04cb00
     * province : null
     * city : null
     * country : null
     * reg_ip : 61.148.242.95
     * is_law : 1
     * login_at : 2017-07-16 20:30:24
     * created_at : 2017-05-18 03:18:53
     * updated_at : 2017-05-18 03:18:53
     * deleted_at : null
     * user_order : {"id":14,"account_number":"13120362645","service_time":1500575271,"reg_date":1497983271,"sale_price":"25.99","level":"初级","info":"初级 - 25.99 元 - 30 天","seller_id":"2088421922928791"}
     * service_conf : {"service":"member.jp02.bell.wiki","country_service":"日本","port":10119,"method":"rc4-md5","pass":"629834","protocol":"origin","obfs":"plain","t":0,"u":0,"d":0,"transfer_enable":5368709120000000,"reg_date":1497983271,"closing_date":1500575271}
     */

    public int id;
    public String account_number;
    public String union_type;
    public Object union_id;
    public String name;
    public String headimgurl;
    public Object sex;
    public String api_token;
    public Object province;
    public Object city;
    public Object country;
    public String reg_ip;
    public String is_law;
    public String login_at;
    public String created_at;
    public String updated_at;
    public Object deleted_at;
    public UserOrderBean user_order;
    public ServiceConfBean service_conf;

    public static class UserOrderBean {
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

    public static class ServiceConfBean {
        /**
         * service : member.jp02.bell.wiki
         * country_service : 日本
         * port : 10119
         * method : rc4-md5
         * pass : 629834
         * protocol : origin
         * obfs : plain
         * t : 0
         * u : 0
         * d : 0
         * transfer_enable : 5368709120000000
         * reg_date : 1497983271
         * closing_date : 1500575271
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
    }
}
