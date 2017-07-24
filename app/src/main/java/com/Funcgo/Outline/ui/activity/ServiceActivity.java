package com.Funcgo.Outline.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.ImageView;

import com.Funcgo.Outline.R;
import com.Funcgo.Outline.utils.AggAsyncHttpResponseHandler;
import com.Funcgo.Outline.web.WebAPI;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/6/22.
 */

public class ServiceActivity extends BaseActivity {
    @BindView(R.id.iv_menus)
    ImageView ivMenus;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceshow);
        ButterKnife.bind(this);
//        webview.loadUrl("http://www.baidu.com");
        WebAPI.getProtocalData(new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject data1 = jsonObject.getJSONObject("data");
                    String protocol_content = data1.getString("protocol_content");
                    webview.loadData(protocol_content,"text/html; charset=UTF-8",null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }));


    }

    @OnClick(R.id.iv_menus)
    public void onViewClicked() {
        finish();
    }
}
