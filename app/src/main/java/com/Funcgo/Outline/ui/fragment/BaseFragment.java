package com.Funcgo.Outline.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;

import com.Funcgo.Outline.ui.dialog.LoadingProgressDialog;
import com.Funcgo.Outline.utils.Debug;
import com.Funcgo.Outline.web.AhAsyncHttpClient;

public abstract class BaseFragment extends Fragment {

    protected boolean isActivityCreated = false;
    protected boolean isDetached = false;
    protected LoadingProgressDialog mLoadingProgressDialog;

    @Override
    public void onAttach(Context context) {
        isDetached = false;
        super.onAttach(context);
        initLoadingDialogIfNeed();
        Debug.fi(getLogTag(), toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.fi(getLogTag(), toString());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Debug.fi(getLogTag(), toString());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Debug.fi(getLogTag(), toString());
        isActivityCreated = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        Debug.fi(getLogTag(), toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        Debug.fi(getLogTag(), toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        Debug.fi(getLogTag(), toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        Debug.fi(getLogTag(), toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Debug.fi(getLogTag(), toString());
        AhAsyncHttpClient.getInstance().cancelRequestsByTAG(getClass().getName(), true);
    }

    @Override
    public void onDetach() {
        isDetached = true;
        super.onDetach();
        Debug.fi(getLogTag(), toString());
    }

    protected <T extends View> T $(@IdRes int id) {
        if (getView() == null) {
            return null;
        } else {
            return (T) getView().findViewById(id);
        }
    }

    protected <T extends View> T $(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    public String getLogTag() {
        return getClass().getSimpleName();
    }

    protected void initLoadingDialogIfNeed() {
        if (enableLoadingView() && getActivity() != null) {
            mLoadingProgressDialog =  LoadingProgressDialog.createDialog(getActivity());
        }
    }

    protected boolean isActivityCreated() {
        return isActivityCreated;
    }

    protected boolean enableLoadingView() {
        return true;
    }


    /**
     * 将final的getString封装一层，以便mockito来mock
     *
     * @param resId 字符串资源id
     * @return 获取的字符串
     */
    public String getStringById(@StringRes int resId) {
        Debug.li(getLogTag(), "isDetached : " + isDetached() + "\n"
                + "!isVisible() : " + !isVisible());
        if (isDetached) {
            return "";
        }
        return getString(resId);
    }

    /**
     * 将final的getString封装一层，以便mockito来mock
     *
     * @param resId  字符串资源id
     * @param params 格式化参数
     * @return 获取的字符串
     */
    public String getStringById(@StringRes int resId, Object... params) {
        Debug.li(getLogTag(), "isDetached : " + isDetached() + "\n"
                + "!isVisible() : " + !isVisible());
        if (isDetached) {
            return "";
        }
        return getString(resId, params);
    }

}