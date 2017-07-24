package com.Funcgo.Outline.entity;

import java.util.List;

/**
 * Created by ydh on 2017/7/17.
 */

public class ServiceList {

    /**
     * ret : 200
     * msg : null
     * data : [{"id":1,"level":"初级","original_price":"25.99","sale_price":"25.99","discount":"1.00","service_time":30,"description":["一月 不限流量","标清视频（480P）","24小时人工客服","邮件收发，20M共享宽带","-服务器可选，全平台支持"]},{"id":2,"level":"初级","original_price":"69.99","sale_price":"69.99","discount":"1.00","service_time":90,"description":["一季度 不限流量","标清视频（480p）","24小时人工客服 不限流量","邮件收发、20M 共享宽带","-服务器可选，全平台支持"]},{"id":3,"level":"初级","original_price":"139.99","sale_price":"139.99","discount":"1.00","service_time":365,"description":["一年 不限流量","标清视频（480p）","24小时人工客服 不限流量","邮件收发、20M 共享宽带","-服务器可选，全平台支持"]},{"id":4,"level":"高级","original_price":"149.99","sale_price":"149.99","discount":"1.00","service_time":183,"description":["半年 不限流量","超清视频体验（1080P）","24小时人工客服 不限流量","邮件收发、100M 共享宽带","服务器可选，全平台支持"]},{"id":5,"level":"高级","original_price":"269.99","sale_price":"269.99","discount":"1.00","service_time":365,"description":["一年 不限流量","超清视频体验（1080P）","24小时人工客服 不限流量","邮件收发、100M 共享宽带","服务器可选，全平台支持"]},{"id":6,"level":"高级","original_price":"389.99","sale_price":"389.99","discount":"1.00","service_time":730,"description":["二年 不限流量","超清视频体验（1080P）","24小时人工客服 不限流量","邮件收发、100M 共享宽带","服务器可选，全平台支持"]}]
     */

    public int ret;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * level : 初级
         * original_price : 25.99
         * sale_price : 25.99
         * discount : 1.00
         * service_time : 30
         * description : ["一月 不限流量","标清视频（480P）","24小时人工客服","邮件收发，20M共享宽带","-服务器可选，全平台支持"]
         */

        public int id;
        public String level;
        public String original_price;
        public String sale_price;
        public String discount;
        public int service_time;
        public List<String> description;
    }
}
