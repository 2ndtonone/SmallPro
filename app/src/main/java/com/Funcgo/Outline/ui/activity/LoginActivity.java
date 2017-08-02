package com.Funcgo.Outline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Funcgo.Outline.R;
import com.Funcgo.Outline.ui.views.CustomToast;
import com.Funcgo.Outline.utils.AggAsyncHttpResponseHandler;
import com.Funcgo.Outline.utils.Debug;
import com.Funcgo.Outline.utils.LogUtils;
import com.Funcgo.Outline.utils.SharePreUtil;
import com.Funcgo.Outline.utils.StringUtils;
import com.Funcgo.Outline.web.WebAPI;
import com.kf5.sdk.system.entity.Field;
import com.kf5.sdk.system.entity.ParamsKey;
import com.kf5.sdk.system.init.UserInfoAPI;
import com.kf5.sdk.system.internet.HttpRequestCallBack;
import com.kf5.sdk.system.utils.SPUtils;
import com.kf5.sdk.system.utils.SafeJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.bt_denglu)
    Button btDenglu;
    @BindView(R.id.tv_zhuce)
    TextView tvZhuce;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        ButterKnife.bind(this);
        if(!TextUtils.isEmpty(SharePreUtil.getStringData(this,"token",""))){
            LogUtils.i(getLogTag(),"---自动登录--");
            createOrLoginUser();
            goHome();
        }
    }

    @OnClick({R.id.bt_denglu, R.id.tv_zhuce, R.id.tv_forget_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_denglu:
                SPUtils.saveAppID("001593ac622469df4938f7e686f60d56ce76c8230c968606");
                SPUtils.saveHelpAddress("funcgo.kf5.com");
                login();
                break;
            case R.id.tv_zhuce:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.tv_forget_pwd:
                Intent intent1 = new Intent(this, RegistActivity.class);
                intent1.putExtra("isForgetPwd",true);
                startActivity(intent1);
                break;
        }
    }

    private void login() {
        final String account = etAccount.getText().toString();
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(etAccount.getText().toString())) {
            CustomToast.showToast("手机号或者邮箱不能为空");
            return;
        }
        if (TextUtils.isEmpty(etPwd.getText().toString())) {
            CustomToast.showToast("密码不能为空");
            return;
        }
        Debug.l("TAG", "pwd 加密前" + pwd);
//        try {
//            pwd = Base64Utils.encode(RSAUtils.encryptByPrivateKey(pwd.getBytes(),RSAUtils.DEFAULT_PUBLIC_KEY));
//            Debug.l("TAG","pwd 加密后"+pwd);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (account.equals("13120362645")) {
//            pwd = "rLIj5qbn4wDE6CVCvZKoWn0W43UsheDmmCH5PwIPwR8WU4qgO1KXirw20x+8h21kUYz+tRIrSvqD/s/CeB4uKdiJ0wIuqbIQkwWLHKgVQ63t0j6X33HEO7zvApI2GiIwaypJLokAiJJBvl/24gjQLW1cQJA+yGQHMT7VmzJKeng=";
//        }
//        if (account.equals("tyu880@sohu.com")) {
//            pwd = "nz2llKcoi9pFqALVcUYNCscB5QSf4kbsQvMG0EUC7x9sHFBdlBRjtmDZi3fnCy30CvzT49hScdMLkbXa6NSHvWfQKy6o0qoVC81WejNKzvyC54zMyzSxExOF0ovmB1IPz+HA0z3ryYdOR7EJ0moUAIpE/PNBOjbWL9EWyZIJNUg=";
//        }
        Debug.l("TAG", "pwd 后台的" + pwd);

        WebAPI.login(account, pwd, new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object = new JSONObject(data);
                    JSONObject data1 = object.getJSONObject("data");
                    SharePreUtil.saveStringData(LoginActivity.this,"account",account);
                    SharePreUtil.saveStringData(LoginActivity.this,"token",data1.getString("api_token"));
                    createOrLoginUser();
                    goHome();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    private void createOrLoginUser() {

        final Map<String, String> map = new ArrayMap<>();
        String account = SharePreUtil.getStringData(this,"account","");
        if (StringUtils.isMobileNO(account)) {
            map.put(ParamsKey.PHONE, account);
        } else {
            map.put(ParamsKey.EMAIL, account);
        }
        UserInfoAPI.getInstance().createUser(map, new HttpRequestCallBack() {
            @Override
            public void onSuccess(final String result) {
                Log.i("kf5测试", "登录成功" + result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final JSONObject jsonObject = SafeJson.parseObj(result);
                            int resultCode = SafeJson.safeInt(jsonObject, "error");
                            if (resultCode == 0) {
                                final JSONObject dataObj = SafeJson.safeObject(jsonObject, Field.DATA);
                                JSONObject userObj = SafeJson.safeObject(dataObj, Field.USER);
                                if (userObj != null) {
                                    String userToken = userObj.getString(Field.USERTOKEN);
                                    int id = userObj.getInt(Field.ID);
                                    SPUtils.saveUserToken(userToken);
                                    SPUtils.saveUserId(id);
                                    saveToken(map);

                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loginUser(map);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(String result) {
                Log.i("kf5测试", "登录失败" + result);
            }
        });

    }


    private void loginUser(final Map<String, String> map) {

        UserInfoAPI.getInstance().loginUser(map, new HttpRequestCallBack() {

            @Override
            public void onSuccess(final String result) {
                Log.i("kf5测试", "登录成功" + result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final JSONObject jsonObject = SafeJson.parseObj(result);
                            int resultCode = SafeJson.safeInt(jsonObject, "error");
                            if (resultCode == 0) {
                                final JSONObject dataObj = SafeJson.safeObject(jsonObject, Field.DATA);
                                JSONObject userObj = SafeJson.safeObject(dataObj, Field.USER);
                                if (userObj != null) {
                                    String userToken = userObj.getString(Field.USERTOKEN);
                                    int id = userObj.getInt(Field.ID);
                                    SPUtils.saveUserToken(userToken);
                                    SPUtils.saveUserId(id);
                                    saveToken(map);
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String message = jsonObject.getString("message");
                                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(String result) {
                Log.i("kf5测试", "登录失败" + result);
            }
        });
    }

    private void saveToken(Map<String, String> map) {
        map.put(ParamsKey.DEVICE_TOKEN, "123456");
        UserInfoAPI.getInstance().saveDeviceToken(map, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.i("kf5测试", "保存设备Token成功" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.i("kf5测试", "保存设备Token失败" + result);
            }
        });
    }

    private void goHome() {
        startActivity(new Intent(this, Shouye_Activity.class));
        finish();
    }
}
