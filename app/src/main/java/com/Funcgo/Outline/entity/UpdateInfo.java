package com.Funcgo.Outline.entity;

/**
 * Created by Administrator on 2015/8/25.
 */
public class UpdateInfo extends SimpleResult1 {

    public DataEntity data;

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "data=" + data +
                '}';
    }

    public static class DataEntity {
        public String type;
        public String version;

        @Override
        public String toString() {
            return "DataEntity{" +
                    "type='" + type + '\'' +
                    "version='" + version + '\'' +
                    '}';
        }
    }
}
