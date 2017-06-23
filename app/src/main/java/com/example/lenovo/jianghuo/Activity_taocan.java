package com.example.lenovo.jianghuo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.lenovo.jianghuo.fragment.fiveFragment;
import com.example.lenovo.jianghuo.fragment.fourFragment;
import com.example.lenovo.jianghuo.fragment.fristFragment;
import com.example.lenovo.jianghuo.fragment.sixFragment;
import com.example.lenovo.jianghuo.fragment.threeFragment;
import com.example.lenovo.jianghuo.fragment.twoFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/6/22.
 */

public class Activity_taocan extends FragmentActivity {
    public fristFragment fristFragment;
    public twoFragment twoFragment;

    public threeFragment threeFragment;
    public fourFragment fourFragment;
    public fiveFragment fiveFragment;
    public sixFragment sixFragment;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<Fragment> fragmentList;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taocanshow);
        ButterKnife.bind(this);
        fragmentList=new ArrayList<>();
        fristFragment = new fristFragment();
        twoFragment = new twoFragment();
        threeFragment = new threeFragment();
        fourFragment = new fourFragment();
        fiveFragment = new fiveFragment();
        sixFragment = new sixFragment();
        fragmentList.add(fristFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(fourFragment);
        fragmentList.add(fiveFragment);
        fragmentList.add(sixFragment);
        viewpager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewpager.setPageMargin(80);
        viewpager.setOffscreenPageLimit(3);
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
          return null;
    }


    }

}




