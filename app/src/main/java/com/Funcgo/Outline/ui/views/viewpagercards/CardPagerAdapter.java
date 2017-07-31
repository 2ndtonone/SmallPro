package com.Funcgo.Outline.ui.views.viewpagercards;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Funcgo.Outline.R;
import com.Funcgo.Outline.entity.ServiceList;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<ServiceList.DataBean> mData;
    private float mBaseElevation;
    private Context context;

    public void setListener(OnBuyClickListener listener) {
        this.listener = listener;
    }

    private OnBuyClickListener listener;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(ServiceList.DataBean item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        context = container.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(position,mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(int position, final ServiceList.DataBean item, View view) {
        int i = position % 3;

        final Button btn = (Button) view.findViewById(R.id.btn_buy);
        final LinearLayout ll_top = (LinearLayout) view.findViewById(R.id.ll_top);
        final LinearLayout ll_alipay = (LinearLayout) view.findViewById(R.id.ll_alipay);
        final LinearLayout ll_alipay2 = (LinearLayout) view.findViewById(R.id.ll_alipay2);
        final LinearLayout ll_buy = (LinearLayout) view.findViewById(R.id.ll_buy);
        final TextView tv_desc1 = (TextView) view.findViewById(R.id.tv_desc1);
        final TextView tv_desc2 = (TextView) view.findViewById(R.id.tv_desc2);
        final TextView tv_desc3 = (TextView) view.findViewById(R.id.tv_desc3);
        final TextView tv_desc4 = (TextView) view.findViewById(R.id.tv_desc4);
        final TextView tv_desc5 = (TextView) view.findViewById(R.id.tv_desc5);

        final ImageView iv_1 = (ImageView) view.findViewById(R.id.iv_1);
        final ImageView iv_2 = (ImageView) view.findViewById(R.id.iv_2);
        final ImageView iv_3 = (ImageView) view.findViewById(R.id.iv_3);
        final ImageView iv_4 = (ImageView) view.findViewById(R.id.iv_4);
        final ImageView iv_5 = (ImageView) view.findViewById(R.id.iv_5);

        final TextView tv_level = (TextView) view.findViewById(R.id.tv_level);
        final TextView tv_money = (TextView) view.findViewById(R.id.tv_money);

        checkItemSupport(item.description.get(0),tv_desc1,iv_1);
        checkItemSupport(item.description.get(1),tv_desc2,iv_2);
        checkItemSupport(item.description.get(2),tv_desc3,iv_3);
        checkItemSupport(item.description.get(3),tv_desc4,iv_4);
        checkItemSupport(item.description.get(4),tv_desc5,iv_5);
        tv_level.setText(item.level);
        tv_money.setText(item.sale_price);

        if (i == 0) {
            ll_top.setBackgroundColor(Color.parseColor("#FF16DD8D"));
            btn.setBackgroundColor(Color.parseColor("#FF16DD8D"));
        } else if (i == 1) {
            ll_top.setBackgroundColor(Color.parseColor("#FFFFCD00"));
            btn.setBackgroundColor(Color.parseColor("#FFFFCD00"));
        } else if (i == 2) {
            ll_top.setBackgroundColor(Color.parseColor("#FFEC407A"));
            btn.setBackgroundColor(Color.parseColor("#FFEC407A"));
        }
        btn.setTag(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = (boolean) v.getTag();
                btn.setTag(!flag);
                if (flag) {
                    btn.setText("返回");
                    ll_alipay.setVisibility(View.VISIBLE);
                    ll_buy.setVisibility(View.GONE);
                } else {
                    btn.setText("购买");
                    ll_alipay.setVisibility(View.GONE);
                    ll_buy.setVisibility(View.VISIBLE);
                }

            }
        });
        ll_alipay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onBuyClickListener(item.id);
                }
            }
        });

    }

    private void checkItemSupport(String desc, TextView textView, ImageView imageView) {
        if(desc.contains("-")){
            textView.setText(desc.substring(1));
            imageView.setImageResource(R.drawable.planlistnotcontain);
        }else {
            textView.setText(desc);
            imageView.setImageResource(R.drawable.planlistcontain);
        }
    }

    public interface OnBuyClickListener{
        public void onBuyClickListener(int id);
    }
}
