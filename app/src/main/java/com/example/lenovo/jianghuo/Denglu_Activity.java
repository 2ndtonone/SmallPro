package com.example.lenovo.jianghuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Denglu_Activity extends AppCompatActivity {

    @BindView(R.id.bt_denglu)
    Button btDenglu;
    @BindView(R.id.tv_zhuce)
    TextView tvZhuce;
    public static final String TAG_EXIT = "exit";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_denglu, R.id.tv_zhuce})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_denglu:
                Intent intent=new Intent(this,Shouye_Activity.class);
                startActivity(intent);

                break;
            case R.id.tv_zhuce:
                Intent intent1=new Intent(this,Zhuce_Activity.class);
                startActivity(intent1);
                break;
        }
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
            if (isExit) {
                this.finish();
            }
        }
    }
}
