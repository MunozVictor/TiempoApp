package com.example.vicapps.tiempoapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vicapps.tiempoapp.R;
import com.example.vicapps.tiempoapp.weather.Hour;

/**
 * Created by Victor on 22/01/2017.
 */

public class HourAdapter extends BaseAdapter {

    Context context;
    Hour[] hours;

    public HourAdapter(Context context,Hour[] hours) {
        this.hours = hours;
        this.context = context;
    }

    @Override
    public int getCount() {
        return hours.length;
    }

    @Override
    public Object getItem(int position) {
        return hours[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.hourly_list_item,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iconImageViewHour);
            holder.hourLabel= (TextView) convertView.findViewById(R.id.hourNameLabel);
            holder.temperature = (TextView) convertView.findViewById(R.id.temperatureHourLabel);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.temperature.setText(" "+hours[position].getTemperatureCelsius()+"º");
        holder.hourLabel.setText(hours[position].getFormattedTime());
        holder.imageView.setImageResource(hours[position].getIconId());

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView temperature,hourLabel;

    }
}
