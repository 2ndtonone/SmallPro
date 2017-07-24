package com.Funcgo.Outline.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.Funcgo.Outline.R;
import com.Funcgo.Outline.entity.Country;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ydh on 2017/7/17.
 */

public class SelectCountryAdapter extends BaseAdapter {
    List<Country.DataBean> list;
    int[] imgRes = {R.drawable.changeserverjapan,R.drawable.changeserveramerica,R.drawable.changeserversingapore};

    public SelectCountryAdapter(List<Country.DataBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Country.DataBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_country, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
//        Utility.loadImg(holder.image,getItem(position).bak_img_url);
        holder.image.setImageResource(imgRes[position%3]);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
