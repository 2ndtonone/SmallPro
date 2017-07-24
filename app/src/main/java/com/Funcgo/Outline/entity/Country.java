package com.Funcgo.Outline.entity;

import java.util.List;

/**
 * Created by ydh on 2017/7/17.
 */

public class Country {


    /**
     * ret : 200
     * msg : null
     * data : [{"id":1,"bak_img_url":null,"country_name":"日本","country_service":"member.jp02.bell.wiki","method":"rc4-md5","info":null},{"id":2,"bak_img_url":null,"country_name":"美国","country_service":"member.ca01.bell.wiki","method":"rc4-md5","info":null},{"id":3,"bak_img_url":null,"country_name":"新加坡","country_service":"member.sg01.bell.wiki","method":"rc4-md5","info":null}]
     */

    public int ret;
    public Object msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * bak_img_url : null
         * country_name : 日本
         * country_service : member.jp02.bell.wiki
         * method : rc4-md5
         * info : null
         */

        public int id;
        public String bak_img_url;
        public String country_name;
        public String country_service;
        public String method;
        public Object info;
    }
}
