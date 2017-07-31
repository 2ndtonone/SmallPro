package com.Funcgo.Outline.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.Funcgo.Outline.R;
import com.Funcgo.Outline.alipay.PayResult;
import com.Funcgo.Outline.entity.ServiceList;
import com.Funcgo.Outline.ui.views.viewpagercards.CardFragmentPagerAdapter;
import com.Funcgo.Outline.ui.views.viewpagercards.CardPagerAdapter;
import com.Funcgo.Outline.ui.views.viewpagercards.ShadowTransformer;
import com.Funcgo.Outline.utils.AggAsyncHttpResponseHandler;
import com.Funcgo.Outline.utils.SharePreUtil;
import com.Funcgo.Outline.web.WebAPI;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;
    private ServiceList serviceList;
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getData();
    }

    private void getData() {
        WebAPI.getServiceList(SharePreUtil.getStringData(this,"token",""), new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {
                serviceList = new Gson().fromJson(data, ServiceList.class);
                for (ServiceList.DataBean datum : serviceList.data) {
                    mCardAdapter.addCardItem(datum);
                }
                mCardAdapter.notifyDataSetChanged();
            }
        }));
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.setListener(new CardPagerAdapter.OnBuyClickListener() {
            @Override
            public void onBuyClickListener(int id) {
                pay(id);
            }
        });

        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, MainActivity.this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        findViewById(R.id.iv_menus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void pay(int id) {
        WebAPI.pay(SharePreUtil.getStringData(this,"token",""), id, new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject data1 = jsonObject.getJSONObject("data");
                    final String app_response = data1.getString("app_response");
//                    final String orderInfo = orderParam + "&" + sign;

                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(MainActivity.this);
                            Map<String, String> result = alipay.payV2(app_response, true);
                            Log.i("msp", result.toString());

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };

                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }));
    }

    @Override
    public void onClick(View view) {
        mShowingFragments = !mShowingFragments;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }
}
