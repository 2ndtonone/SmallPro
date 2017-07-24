package com.Funcgo.Outline.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.Funcgo.Outline.R;
import com.Funcgo.Outline.ui.dialog.LoadingProgressDialog;
import com.Funcgo.Outline.utils.Debug;
import com.Funcgo.Outline.utils.SystemBarTintManager;
import com.Funcgo.Outline.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseActivity extends AppCompatActivity {

    protected static List<String> sForegroundActivityNameList = new ArrayList<>();
    protected SystemBarTintManager mBarTintManager;
    protected LocalBroadcastManager mLocalBroadcastManager;
    protected BroadcastReceiver mLocalReceiver;
    protected boolean isDestroyed = false;
    protected boolean isResumed = false;
    protected View btnBack;
    protected LoadingProgressDialog mLoadingProgressDialog;

    /**
     * <p>获取正在前台运行的activity名称 </p>
     * <p>由于ActivityManager.getRunningTasks()在5.0后被弃用，所以只能通过自定义的方式判断前台activity</p>
     *
     * @return
     */
    public static String getForegroundActivityName() {
        String activityName = null;
        if (!sForegroundActivityNameList.isEmpty()) {
            activityName = sForegroundActivityNameList.get(sForegroundActivityNameList.size() - 1);
            Debug.li(BaseActivity.class.getSimpleName(), "getForegroundActivityName:" + activityName);
        }
        return activityName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.i(getLogTag(), toString() + " | hasCode:" + hashCode());
        injectDependencies();
        initStatusBarColor();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Debug.i(getLogTag(), toString() + " | hasCode:" + hashCode());
        sForegroundActivityNameList.add(getClass().getName());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Debug.i(getLogTag(), toString() + " | hasCode:" + hashCode());
        setBtnBackClickListener();
        addStatusBarPadding();
        initLoadingDialogIfNeed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Debug.i(getLogTag(), toString() + " | hasCode:" + hashCode());
        isResumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Debug.i(getLogTag(), toString() + " | hasCode:" + hashCode());
        isResumed = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Debug.i(getLogTag(), toString() + " | hasCode:" + hashCode());
        sForegroundActivityNameList.remove(getClass().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Debug.i(getLogTag(), toString() + " | hasCode:" + hashCode());
        isDestroyed = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Debug.li(getLogTag(), "requestCode:" + requestCode + "\n" +
                "resultCode:" + resultCode + "\n" +
                "mCanTakeData:" + data + "\n");
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    protected <T extends View> T $(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    protected void setBtnBackClickListener() {
        btnBack = $(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }


    protected void initStatusBarColor() {
        mBarTintManager.setStatusBarTintEnabled(true);
        mBarTintManager.setStatusBarTintColor(getStatusBarColor());
        if (Utility.isMIUI()) {
            SystemBarTintManager.setMiuiStatusBarDarkMode(this, true);
        } else if (Utility.isFlyme()) {
            SystemBarTintManager.setMeizuStatusBarDarkMode(this, true);
        }
    }

    public int getStatusBarColor() {
        return getResources().getColor(R.color.main_red);
    }

    public int getStatusBarHeight() {
        return mBarTintManager.getConfig().getStatusBarHeight();
    }

    public void setTintResource(int resource) {
        mBarTintManager.setStatusBarTintResource(resource);
    }

    public void setTintColor(int color) {
        mBarTintManager.setStatusBarTintColor(color);
    }

    protected void addStatusBarPadding() {
        if (mBarTintManager != null
                && mBarTintManager.isStatusBarAvailable()
                && mBarTintManager.isStatusBarTintEnabled()) {
            View contentView = $(android.R.id.content);
            if (contentView != null) {
                int oldPaddingTop = contentView.getPaddingTop();
                int newPaddingTop = oldPaddingTop + getStatusBarHeight();
                contentView.setPadding(
                        contentView.getPaddingLeft(),
                        newPaddingTop,
                        contentView.getPaddingRight(),
                        contentView.getPaddingBottom()
                );
            }
        }
    }

    protected void initLoadingDialogIfNeed() {
        if (enableLoadingView()) {
            mLoadingProgressDialog = LoadingProgressDialog.createDialog(this);
        }
    }

    /** 需要登录才能进的页面，用于检测到登录冲突时判断是否要关闭本页面 */
    protected boolean isNeedLogin() {
        return false;
    }

    protected void injectDependencies() {
        mBarTintManager = new SystemBarTintManager(this);
    }

    public String getLogTag() {
        return getClass().getSimpleName();
    }

    protected boolean enableLoadingView() {
        return true;
    }

}