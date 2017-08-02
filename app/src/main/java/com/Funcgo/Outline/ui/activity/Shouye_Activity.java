package com.Funcgo.Outline.ui.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Funcgo.Outline.R;
import com.Funcgo.Outline.entity.ServiceEntity;
import com.Funcgo.Outline.entity.UserInfo;
import com.Funcgo.Outline.eventbus.AlipaySuccessEvent;
import com.Funcgo.Outline.ss.core.AppProxyManager;
import com.Funcgo.Outline.ss.core.LocalVpnService;
import com.Funcgo.Outline.ss.core.ProxyConfig;
import com.Funcgo.Outline.ui.views.CustomToast;
import com.Funcgo.Outline.ui.views.MyCircleProgressBar;
import com.Funcgo.Outline.utils.AggAsyncHttpResponseHandler;
import com.Funcgo.Outline.utils.Debug;
import com.Funcgo.Outline.utils.LogUtils;
import com.Funcgo.Outline.utils.SharePreUtil;
import com.Funcgo.Outline.utils.Utility;
import com.Funcgo.Outline.web.WebAPI;
import com.google.gson.Gson;
import com.kf5.sdk.im.ui.KF5ChatActivity;
import com.kf5.sdk.system.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

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
    @BindView(R.id.iv_out)
    ImageView iv_out;
    @BindView(R.id.iv_inner)
    ImageView iv_inner;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_current)
    TextView tvCurrent;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.activity_gif_giv)
    GifImageView mGifImageView;
    @BindView(R.id.fl_connect)
    FrameLayout fl_connect;

    ImageView iv_head;
    TextView tv_name;

    private static final String CONFIG_URL_KEY = "CONFIG_URL_KEY";
    private static final int START_VPN_SERVICE_REQUEST_CODE = 1985;
    private Calendar mCalendar;

    private boolean isConnect = false;
    private boolean isVip = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouye);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        getData();
//        initGif();

        mCalendar = Calendar.getInstance();
        LocalVpnService.addOnStatusChangedListener(this);
        ProxyConfig.Instance.globalMode = true;
        onLogReceived("Proxy global mode is on");
        if (AppProxyManager.isLollipopOrAbove) {
            new AppProxyManager(this);
            AppProxyManager.Instance.removeProxyApp("com.Funcgo.Outline");
        }
    }

    private void initGif() {
        try {
            GifDrawable gifDrawable = new GifDrawable(getAssets(), "connect_connecting1.gif");
            mGifImageView.setImageDrawable(gifDrawable);
//            final MediaController mediaController = new MediaController(this);
//            mediaController.setMediaPlayer((GifDrawable) mGifImageView.getDrawable());
//            /**
//             * 也许你会像我一样，当看到上面一行代码时会纳闷，为什么setMediaPalyer传入的参数会是一个
//             * GifDrawable对象呢，它需要的参数类型是MediaPlayerControl。。。
//             * 还永德我们前面提到GifDrawable实现了MediaPlayerControl接口吗？
//             * 嗯。。。哦，，，恍然大明白了
//             */
//            mediaController.setAnchorView(mGifImageView);
//            mGifImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mediaController.show();
//                }
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        WebAPI.getUserInfo(SharePreUtil.getStringData(this, "token", ""), new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
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
        LogUtils.i(getLogTag(), "onDestroy");
        LocalVpnService.removeOnStatusChangedListener(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEventMainThread(AlipaySuccessEvent event) {
        getData();
    }

    //    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        Intent intent=new Intent();
//        intent.setAction("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.HOME");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addCategory("android.intent.category.MONKEY");
//        startActivity(intent);
//    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo =
                pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0);
        if (keyCode == KeyEvent.KEYCODE_BACK){
            ActivityInfo ai = homeInfo.activityInfo;
            Intent startIntent = new Intent(Intent.ACTION_MAIN);
            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
            startActivitySafely(startIntent);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    private void startActivitySafely(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "null",
                    Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "null",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void setupView(UserInfo userInfo) {
        tv_name.setText(userInfo.name);
        Utility.loadImg(iv_head, userInfo.headimgurl);
        if (userInfo.user_order != null) {
            try {
                isVip = true;
                long currentTiem = new Date().getTime() / 1000;
                long less = userInfo.user_order.service_time - currentTiem;
                String lessDay = (less / 3600 / 24 + 1) + "";
                Debug.l(getLogTag(), "剩余天数：" + lessDay);
                progressBar.setLessDay(lessDay);

                long total = userInfo.user_order.service_time - userInfo.user_order.reg_date;


                float progress = less * 100 / total + 1;
                progressBar.setProgress((int) progress);
            } catch (Exception e) {
                isVip = false;
                e.printStackTrace();
                progressBar.setProgress(0);
                CustomToast.showToast("当前服务已到期，请购买新的套餐");
            }
            tvState.setText(userInfo.user_order.level);
        } else {
            isVip = false;
            progressBar.setProgress(0);
            tvState.setText("试用套餐");
        }
        tvCurrent.setText(userInfo.service_conf.country_service);

        if (userInfo.service_conf != null) {
            String url = "ss://" +
                    userInfo.service_conf.method + ":" + userInfo.service_conf.pass +
                    "@" + userInfo.service_conf.service + ":" + userInfo.service_conf.port;
            Debug.l(getLogTag(), "---vpn URL--------" + url);
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
                item.setCheckable(false);
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
                        LocalVpnService.IsRunning = false;
                        SharePreUtil.deleteStringData(Shouye_Activity.this, "token");
                        SharePreUtil.deleteStringData(Shouye_Activity.this, "account");
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

    @OnClick({R.id.ll_service, R.id.iv_menus, R.id.ll_country, R.id.fl_connect})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_country:
                startActivityForResult(new Intent(this, ShowCountry_Activity.class), 200);
                break;
            case R.id.ll_service:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.iv_menus:
                dl.openDrawer(GravityCompat.START);
                break;
            case R.id.fl_connect:
                LogUtils.i(getLogTag(), "点击按钮");
                if (isVip) {
                    if (isConnect) {
                        LocalVpnService.IsRunning = false;
                    } else {
                        Intent intent = LocalVpnService.prepare(this);
                        if (intent == null) {
                            startVPNService();
                        } else {
                            startActivityForResult(intent, START_VPN_SERVICE_REQUEST_CODE);
                        }
                    }
                } else {
                    CustomToast.showToast("请购买套餐");
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
        } else if (resultCode == RESULT_OK && requestCode == 200) {
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
//        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
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
        LogUtils.i(getLogTag(), logString);
        if (logString.contains("starting")) {
            isConnect = true;
            iv_out.setVisibility(View.GONE);
            iv_inner.setVisibility(View.GONE);
            mGifImageView.setVisibility(View.VISIBLE);
            try {
                GifDrawable gifDrawable = new GifDrawable(getAssets(), "connect_connecting2.gif");
                mGifImageView.setImageDrawable(gifDrawable);
            } catch (Exception e) {

            }
        } else if (logString.contains("已连接")) {
            isConnect = true;
            try {
                GifDrawable gifDrawable = new GifDrawable(getAssets(), "connect_connected.gif");
                mGifImageView.setImageDrawable(gifDrawable);
            } catch (Exception e) {

            }

        } else if (logString.contains("已断开")) {
            isConnect = false;
            iv_out.setVisibility(View.VISIBLE);
            iv_inner.setVisibility(View.VISIBLE);
            mGifImageView.setVisibility(View.GONE);
            try {
                GifDrawable gifDrawable = new GifDrawable(getAssets(), "connect_connecting1.gif");
                mGifImageView.setImageDrawable(gifDrawable);
            } catch (Exception e) {

            }

        }
    }
}
