package com.Funcgo.Outline.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.Funcgo.Outline.R;

public class SplashActivity extends BaseActivity {

    private static final long SPLASH_DELAY_MILLIS = 2500;

    private FrameLayout mGifViewContainer;
    private WebView mGifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppUpdateChecker.checkUpdate(true, null);
        setContentView(R.layout.activity_splash);
        loadGif();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseGif();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    private void loadGif() {
        mGifViewContainer = (FrameLayout) findViewById(R.id.gif_view_container);
        mGifView = new WebView(getApplicationContext());
        mGifViewContainer.addView(mGifView);
        mGifView.setBackgroundColor(Color.TRANSPARENT);
        mGifView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            mGifView.setScrollBarSize(0);
        }
        mGifView.clearCache(false);
        String data = "<html><body><head><style>*{ margin:0; padding: 0;}</style></head><img src=\""
                + "logo_anim.gif" +
                "\" width=\"100%\" loop=\"false\"/></body></html>";
        mGifView.loadDataWithBaseURL("file:///android_asset/", data, "text/html", "utf-8", null);
        mGifView.getSettings().setBuiltInZoomControls(false);
        mGifView.getSettings().setDisplayZoomControls(false);
        mGifView.getSettings().setSupportZoom(false);
        mGifView.getSettings().setDomStorageEnabled(false);
    }

    private void releaseGif() {
        mGifView.getSettings().setBuiltInZoomControls(true);
        mGifView.getSettings().setDisplayZoomControls(true);
        mGifView.getSettings().setSupportZoom(true);
        mGifViewContainer.removeAllViews();
        mGifView.removeAllViews();
        mGifView.destroy();
    }

}