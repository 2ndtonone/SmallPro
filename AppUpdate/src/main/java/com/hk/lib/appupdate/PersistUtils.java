package com.hk.lib.appupdate;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AvatarQing on 2016/5/27.
 */
class PersistUtils {

    private static final String PREF_NAME_APP_UPDATE = "PREF_NAME_APP_UPDATE";
    private static final String PREF_KEY_LAST_REQUEST_TIME = "PREF_KEY_LAST_REQUEST_TIME";
    private static final String PREF_KEY_APK_DOWNLOAD_URL = "mobile_apk";
    private static final String PREF_KEY_FORCE_UPDATE_VERSION = "mobile_apk_force_version";
    private static final String PREF_KEY_NORMAL_UPDATE_VERSION = "mobile_apk_version";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME_APP_UPDATE, Context.MODE_PRIVATE);
    }

    /** 获取Apk下载地址 */
    public static String getApkDownloadUrl(Context context) {
        return getPrefs(context).getString(PREF_KEY_APK_DOWNLOAD_URL, "");
    }

    /** 获取强制更新的版本号 */
    public static float getForceUpdateVersion(Context context) {
        return getPrefs(context).getFloat(PREF_KEY_FORCE_UPDATE_VERSION, 0);
    }

    /** 获取普通更新的版本号 */
    public static float getNormalUpdateVersion(Context context) {
        return getPrefs(context).getFloat(PREF_KEY_NORMAL_UPDATE_VERSION, 0);
    }

    /** 获取上次请求更新的时间点 */
    public static long getLastRequestTime(Context context) {
        return getPrefs(context).getLong(PREF_KEY_LAST_REQUEST_TIME, 0);
    }

    /** 设置Apk下载地址 */
    public static void setApkDownloadUrl(Context context, String url) {
        getPrefs(context).edit().putString(PREF_KEY_APK_DOWNLOAD_URL, url).apply();
    }

    /** 设置强制更新的版本号 */
    public static void setForceUpdateVersion(Context context, float forceVersion) {
        getPrefs(context).edit().putFloat(PREF_KEY_FORCE_UPDATE_VERSION, forceVersion).apply();
    }

    /** 设置普通更新的版本号 */
    public static void setNormalUpdateVersion(Context context, float normalVersion) {
        getPrefs(context).edit().putFloat(PREF_KEY_NORMAL_UPDATE_VERSION, normalVersion).apply();
    }

    /** 设置上次请求更新的时间点 */
    public static void setLastRequestTime(Context context, long time) {
        getPrefs(context).edit().putLong(PREF_KEY_LAST_REQUEST_TIME, time).apply();
    }
}