package com.example.lenovo.jianghuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.shinelw.library.ColorArcProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/6/21.
 */

public class Shouye_Activity extends AppCompatActivity {
    @BindView(R.id.home_activity_toolbar)
    Toolbar homeActivityToolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.dl)
    DrawerLayout dl;
    @BindView(R.id.ll_country)
    LinearLayout llCountry;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouye);
        ButterKnife.bind(this);
        setSupportActionBar(homeActivityToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.homepagemenu);
        }
        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                dl.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.it_buy:

                        Intent intentTheme = new Intent(Shouye_Activity.this, Activity_taocan.class);
                        startActivity(intentTheme);
                        break;


                    case R.id.it_seriver:
                        Intent aboutTheme = new Intent(Shouye_Activity.this, Activity_service.class);
                        startActivity(aboutTheme);
                        break;

                    case R.id.it_callus:

                        break;
                    case R.id.it_exit:
                        Intent intent = new Intent(Shouye_Activity.this, Denglu_Activity.class);
                        intent.putExtra(Denglu_Activity.TAG_EXIT, true);
                        startActivity(intent);

                        break;

                }
                return true;
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.ll_country)
    public void onClick() {
        Intent intent = new Intent(this, ShowCountry_Activity.class);
        startActivity(intent);
    }

}
