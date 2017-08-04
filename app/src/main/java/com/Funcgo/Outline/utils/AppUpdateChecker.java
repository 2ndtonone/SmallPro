package com.Funcgo.Outline.utils;

import android.content.Context;

import com.Funcgo.Outline.LocationApplication;
import com.Funcgo.Outline.entity.UpdateInfo;
import com.Funcgo.Outline.web.WebAPI;
import com.hk.lib.appupdate.AppUpdateManager;

public class AppUpdateChecker {
    public interface Callback {
        void onSuccess();

        void onFinish();
    }

    private static final int REQUEST_INTERVAL = 1000 * 60 * 10;

    private static boolean isNeedRequest() {
        Context context = LocationApplication.getInstance();
        long lastRequestTime = AppUpdateManager.getLastRequestTime(context);
        long now = System.currentTimeMillis();
        return now - lastRequestTime > REQUEST_INTERVAL;
    }

    /**
     * 检查应用是否有更新
     *
     * @param manual   true表示手动点击按钮检查更新，false表示非用户操作的检查更新
     * @param callback
     */
    public static void checkUpdate(Context context, final boolean manual, final Callback callback) {
        if (!manual && !isNeedRequest()) {
            Debug.li(getLogTag(), "使用缓存的应用更新信息");
            if (callback != null) {
                callback.onSuccess();
                callback.onFinish();
            }
            return;
        }
        WebAPI.getCheckUpdatePost(new AggAsyncHttpResponseHandler(context, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {
                Context context = LocationApplication.getInstance();
                final UpdateInfo updateInfo = JsonParser.getInstance().fromJson(data, UpdateInfo.class);
                AppUpdateManager.setLastRequestTime(context, System.currentTimeMillis());
                AppUpdateManager.saveAppUpdateInfo(
                        context,
                        "www.funcgo.com/v1/Android/download/OutLine.apk",
                        Float.parseFloat(updateInfo.data.version),
                        Float.parseFloat(updateInfo.data.version)
                );
                if (callback != null) {
                    callback.onSuccess();
                }
            }
        }) {
            @Override
            public void onFinish() {
                super.onFinish();
                if (callback != null) {
                    callback.onFinish();
                }
            }
        });
    }

    private static String getLogTag() {
        return AppUpdateChecker.class.getSimpleName();
    }
}