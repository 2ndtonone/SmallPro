package com.Funcgo.Outline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.Funcgo.Outline.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lenovo on 2017/6/22.
 */

public class fiveFragment extends Fragment {
    @BindView(R.id.btn_buy)
    Button btnBuy;
    @BindView(R.id.ll_buy)
    LinearLayout llBuy;
    @BindView(R.id.ll_alipay)
    LinearLayout llAlipay;
    Unbinder unbinder;
    boolean isBuy = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.taocan_five, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                isBuy = !isBuy;
                if(isBuy){
                    llAlipay.setVisibility(View.VISIBLE);
                    llBuy.setVisibility(View.GONE);
                    btnBuy.setText("返回");
                }else {
                    llAlipay.setVisibility(View.GONE);
                    llBuy.setVisibility(View.VISIBLE);
                    btnBuy.setText("购买");
                }
                break;
        }
    }
}
