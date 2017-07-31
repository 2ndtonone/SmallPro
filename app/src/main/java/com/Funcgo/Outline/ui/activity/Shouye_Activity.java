package com.Funcgo.Outline.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Funcgo.Outline.LocationApplication;
import com.Funcgo.Outline.R;
import com.Funcgo.Outline.entity.ServiceEntity;
import com.Funcgo.Outline.entity.UserInfo;
import com.Funcgo.Outline.ss.core.AppProxyManager;
import com.Funcgo.Outline.ss.core.LocalVpnService;
import com.Funcgo.Outline.ss.core.ProxyConfig;
import com.Funcgo.Outline.ui.views.CustomToast;
import com.Funcgo.Outline.ui.views.MyCircleProgressBar;
import com.Funcgo.Outline.utils.AggAsyncHttpResponseHandler;
import com.Funcgo.Outline.utils.Debug;
import com.Funcgo.Outline.utils.Utility;
import com.Funcgo.Outline.web.WebAPI;
import com.google.gson.Gson;
import com.kf5.sdk.im.ui.KF5ChatActivity;
import com.kf5.sdk.system.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/6/21.
 */

public class Shouye_Activity extends BaseActivity implements LocalVpnService.onStatusChangedListener {
    @BindView(R.id.dl)
    DrawerLayout dl;
    @BindView(R.id.ll_country)
    LinearLayout llCountry;
    @BindView(R.id.progress)
    MyCircleProgressBar progressBar;
    @BindView(R.id.iv_menus)
    ImageView ivMenus;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_current)
    TextView tvCurrent;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.nav_view)
    NavigationView navView;

    ImageView iv_head;
    TextView tv_name;

    private static final String CONFIG_URL_KEY = "CONFIG_URL_KEY";
    private static final int START_VPN_SERVICE_REQUEST_CODE = 1985;
    private Calendar mCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouye);
        ButterKnife.bind(this);
        initView();
        getData();


        mCalendar = Calendar.getInstance();
        LocalVpnService.addOnStatusChangedListener(this);
        ProxyConfig.Instance.globalMode = true;
        onLogReceived("Proxy global mode is on");
        if (AppProxyManager.isLollipopOrAbove){
            new AppProxyManager(this);
        }
    }

    private void getData() {
        WebAPI.getUserInfo(LocationApplication.getInstance().getToken(), new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {

                try {
                    JSONObject object = new JSONObject(data);
                    JSONObject data1 = object.getJSONObject("data");
                    UserInfo userInfo = new Gson().fromJson(data1.toString(), UserInfo.class);
                    setupView(userInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    private void startVPNService() {
        String ProxyUrl = readProxyUrl();
        onLogReceived("starting...");
        LocalVpnService.ProxyUrl = ProxyUrl;
        startService(new Intent(this, LocalVpnService.class));
    }

    @Override
    protected void onDestroy() {
        LocalVpnService.removeOnStatusChangedListener(this);
        super.onDestroy();
    }



    private void setupView(UserInfo userInfo) {
        tv_name.setText(userInfo.name);
        Utility.loadImg(iv_head, userInfo.headimgurl);
        if (userInfo.user_order != null) {
            try {
                long currentTiem = new Date().getTime() / 1000;
                long less = userInfo.user_order.service_time - currentTiem;
                String lessDay = (less / 3600 / 24 + 1) + "";
                Debug.l(getLogTag(), "剩余天数：" + lessDay);
                progressBar.setLessDay(lessDay);

                long total = userInfo.user_order.service_time - userInfo.user_order.reg_date;


                float progress = less * 100 / total + 1;
                progressBar.setProgress((int) progress);
            } catch (Exception e) {
                e.printStackTrace();
                progressBar.setProgress(0);
                CustomToast.showToast("当前服务已到期，请购买新的套餐");
            }
            tvState.setText(userInfo.user_order.level);
        } else {
            progressBar.setProgress(0);
            tvState.setText("试用套餐");
        }
        tvCurrent.setText(userInfo.service_conf.country_service);

        if (userInfo.service_conf != null) {
            String url = "ss://" +
                    userInfo.service_conf.method + ":" + userInfo.service_conf.pass +
                    "@" + userInfo.service_conf.service + ":" + userInfo.service_conf.port;
            Debug.l(getLogTag(),"---vpn URL--------"+url);
            url = "ss://aes-256-cfb:Zxc950320@50.117.38.29:8831";
            setProxyUrl(url);
        }
    }

    private void initView() {
        View headerView = navView.getHeaderView(0);
        iv_head = (ImageView) headerView.findViewById(R.id.iv_head);
        tv_name = (TextView) headerView.findViewById(R.id.tv_name);

        Resources resource = (Resources) getBaseContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.navigation_menu_item_color);
        navView.setItemTextColor(csl);
        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                dl.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.it_buy:

                        Intent intentTheme = new Intent(Shouye_Activity.this, MainActivity.class);
                        startActivity(intentTheme);
                        break;


                    case R.id.it_seriver:
                        Intent aboutTheme = new Intent(Shouye_Activity.this, ServiceActivity.class);
                        startActivity(aboutTheme);
                        break;

                    case R.id.it_callus:
                        startActivity(new Intent(Shouye_Activity.this, KF5ChatActivity.class));
                        break;
                    case R.id.it_exit:
                        finish();
                        SPUtils.clearSP();
                        Intent intent = new Intent(Shouye_Activity.this, LoginActivity.class);
                        startActivity(intent);

                        break;

                }
                return true;
            }
        });
    }


    String readProxyUrl() {
        SharedPreferences preferences = getSharedPreferences("shadowsocksProxyUrl", MODE_PRIVATE);
        return preferences.getString(CONFIG_URL_KEY, "");
    }

    void setProxyUrl(String ProxyUrl) {
        SharedPreferences preferences = getSharedPreferences("shadowsocksProxyUrl", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CONFIG_URL_KEY, ProxyUrl);
        editor.apply();
    }

    String getVersionName() {
        PackageManager packageManager = getPackageManager();
        if (packageManager == null) {
            Log.e(getLogTag(), "null package manager is impossible");
            return null;
        }

        try {
            return packageManager.getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(getLogTag(), "package not found is impossible", e);
            return null;
        }
    }

    boolean isValidUrl(String url) {
        try {
            if (url == null || url.isEmpty())
                return false;

            if (url.startsWith("ss://")) {//file path
                return true;
            } else { //url
                Uri uri = Uri.parse(url);
                if (!"http".equals(uri.getScheme()) && !"https".equals(uri.getScheme()))
                    return false;
                if (uri.getHost() == null)
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @OnClick({R.id.ll_service, R.id.iv_menus, R.id.ll_country, R.id.iv_connect})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_country:
                startActivityForResult( new Intent(this, ShowCountry_Activity.class), 200);
                break;
            case R.id.ll_service:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.iv_menus:
                dl.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_connect:
                Intent intent = LocalVpnService.prepare(this);
                if (intent == null) {
                    startVPNService();
                } else {
                    startActivityForResult(intent, START_VPN_SERVICE_REQUEST_CODE);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == START_VPN_SERVICE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startVPNService();
            } else {
                onLogReceived("canceled.");
            }
            return;
        }else if (resultCode == RESULT_OK && requestCode == 200) {
            ServiceEntity.DataBean entity = (ServiceEntity.DataBean) intent.getSerializableExtra("entity");
            tvCurrent.setText(entity.country_service);
            // TODO 配置SS

//            String url = "ss://" + entity.method + ":" + entity.pass +
//                    "@" + entity.service + ":" + entity.port;
//            setProxyUrl(url);
//            Debug.l(getLogTag(),"---vpn URL--------"+url);
//            startVPNService();
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }



    @Override
    public void onStatusChanged(String status, Boolean isRunning) {
        onLogReceived(status);
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onLogReceived(String logString) {
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        logString = String.format("[%1$02d:%2$02d:%3$02d] %4$s\n",
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                mCalendar.get(Calendar.SECOND),
                logString);
        System.out.println(logString);
    }
}
