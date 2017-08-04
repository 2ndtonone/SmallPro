package com.Funcgo.Outline.entity;

import com.Funcgo.Outline.utils.ConstantUtils;

import java.io.Serializable;

/**
 *
 * Created by ydh on 6/17/15.
 * {"code":200,"message":"确认成功"}
 */
public class SimpleResult1 implements Serializable {
    public static final int STATUS_AUTH_FAIL = ConstantUtils.STATUS_AUTH_FAIL;
    private static final int STATUS_OK = 200;
    public int ret;
    public String msg;

    public SimpleResult1() {
    }

    public boolean isOk() {
        return ret == STATUS_OK;
    }

    public String getMessage() {
        return msg;
    }

    public int getCode() {
        return ret;
    }

    @Override
    public String toString() {
        return "SimpleResult1{" +
                "code=" + ret +
                ", message='" + msg + '\'' +
                '}';
    }

    public void copy(SimpleResult1 result1) {
        if (result1 == null) {
            return;
        }

        ret = result1.ret;
        msg = result1.msg;
    }
}
