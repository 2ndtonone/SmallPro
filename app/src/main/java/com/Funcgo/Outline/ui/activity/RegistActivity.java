package com.Funcgo.Outline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Funcgo.Outline.R;
import com.Funcgo.Outline.ui.views.CustomToast;
import com.Funcgo.Outline.utils.AggAsyncHttpResponseHandler;
import com.Funcgo.Outline.utils.SharePreUtil;
import com.Funcgo.Outline.web.WebAPI;
import com.orhanobut.logger.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/6/21.
 */

public class RegistActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.ll_service_protocal)
    LinearLayout ll_service_protocal;
    private CountDownTimer timer;
    private boolean isForgetPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        ButterKnife.bind(this);
        isForgetPwd = getIntent().getBooleanExtra("isForgetPwd", false);
        if(isForgetPwd){
            etNickname.setVisibility(View.GONE);
            ll_service_protocal.setVisibility(View.GONE);
            btnRegister.setText("确定修改");
        }
    }

    @OnClick({R.id.image_back, R.id.btn_register, R.id.btn_send, R.id.tv_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.btn_register:
                if(isForgetPwd){
                    resetPwd();
                }else {
                    regist();
                }
                break;
            case R.id.btn_send:
                sendVerifyCode();
                break;
            case R.id.tv_service:
                startActivity(new Intent(this,ServiceActivity.class));
                break;
        }
    }

    private void resetPwd() {
        String account = etAccount.getText().toString();
        if (TextUtils.isEmpty(account)) {
            CustomToast.showToast("手机号或邮箱不能为空");
            return;
        }
        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            CustomToast.showToast("验证码不能为空");
            return;
        }
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            CustomToast.showToast("密码不能为空");
            return;
        }
        WebAPI.resetPwd(account, code, pwd, new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {
                login();
            }
        }));
    }

    private void regist() {
        String nickname = etNickname.getText().toString();
        if (TextUtils.isEmpty(nickname)) {
            CustomToast.showToast("昵称不能为空");
            return;
        }
        String account = etAccount.getText().toString();
        if (TextUtils.isEmpty(account)) {
            CustomToast.showToast("手机号或邮箱不能为空");
            return;
        }
        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            CustomToast.showToast("验证码不能为空");
            return;
        }
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            CustomToast.showToast("密码不能为空");
            return;
        }
        WebAPI.regist(nickname, account, code, pwd, new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {
                login();
            }
        }));
    }

    private void login() {
        WebAPI.login(etAccount.getText().toString(),etPwd.getText().toString(),new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data){
                try {
                    JSONObject object = new JSONObject(data);
                    JSONObject data1 = object.getJSONObject("data");
                    goHome(data1.getString("api_token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    private void goHome(String token) {
        SharePreUtil.saveStringData(this,"account",etAccount.getText().toString());
        SharePreUtil.saveStringData(this,"token",token);
        startActivity(new Intent(this,Shouye_Activity.class));
    }

    private void sendVerifyCode() {
        String account = etAccount.getText().toString();
        if (TextUtils.isEmpty(account)) {
            CustomToast.showToast("手机号或邮箱不能为空");
            return;
        }
        WebAPI.sendVerifyCode(account, new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {
                CustomToast.showToast("验证码已发送，请注意查收");
                startCountDown();
            }
        }));
    }

    private void startCountDown() {
        btnSend.setEnabled(false);
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnSend.setText(millisUntilFinished / 1000 + "S");
            }

            @Override
            public void onFinish() {
                btnSend.setText("发送验证码");
                btnSend.setEnabled(true);
            }
        };
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
