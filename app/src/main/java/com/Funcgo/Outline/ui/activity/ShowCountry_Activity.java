package com.Funcgo.Outline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.Funcgo.Outline.R;
import com.Funcgo.Outline.entity.Country;
import com.Funcgo.Outline.entity.ServiceEntity;
import com.Funcgo.Outline.ui.adapter.SelectCountryAdapter;
import com.Funcgo.Outline.utils.AggAsyncHttpResponseHandler;
import com.Funcgo.Outline.utils.SharePreUtil;
import com.Funcgo.Outline.web.WebAPI;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/6/22.
 */

public class ShowCountry_Activity extends BaseActivity {

    private Country country;
    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        ButterKnife.bind(this);
        getListData();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                http://api.funcgo.com/v1/checkservice
                getServiceData(country.data.get(position).country_service,country.data.get(position).country_name);
            }

        });
    }

    private void getServiceData(String country_service, String country_name) {
        WebAPI.getServiceDetail(SharePreUtil.getStringData(this,"token",""),country_service,country_name,new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
            @Override
            public void onSuccess(String data) {
                ServiceEntity serviceEntity = new Gson().fromJson(data, ServiceEntity.class);
                Intent intent = new Intent();
                intent.putExtra("entity",serviceEntity.data);
                setResult(RESULT_OK,intent);
                finish();
            }
        }));
    }

    private void getListData() {
        WebAPI.getServiceCountryList(SharePreUtil.getStringData(this,"token",""), new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {


            @Override
            public void onSuccess(String data) {
                country = new Gson().fromJson(data, Country.class);
                listview.setAdapter(new SelectCountryAdapter(country.data));
            }
        }));
    }

    @OnClick(R.id.iv_menus)
    public void onViewClicked() {
        finish();
    }
}
