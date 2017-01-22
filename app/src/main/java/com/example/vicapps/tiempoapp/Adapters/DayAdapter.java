package com.example.vicapps.tiempoapp.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vicapps.tiempoapp.R;
import com.example.vicapps.tiempoapp.UI.MainActivity;
import com.example.vicapps.tiempoapp.weather.Day;

/**
 * Created by Victor on 22/01/2017.
 */

public class DayAdapter extends BaseAdapter {
    Context context;
    Day[] arrayDay;

    public DayAdapter(Context context, Day[] arrayDay) {
        this.context = context;
        this.arrayDay = arrayDay;
    }

    @Override
    public int getCount() {
        return arrayDay.length;
    }

    @Override
    public Object getItem(int position) {
        return arrayDay[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.daily_list_item,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.dayLabel= (TextView) convertView.findViewById(R.id.dayNameLabel);
            holder.temperature= (TextView) convertView.findViewById(R.id.temperatureLabel);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.temperature.setText(arrayDay[position].getTemperatureCelsius()+"");
        Log.i(MainActivity.TAG, "Obtenido desde ADAPTER temperatura en celsius: "+ arrayDay[position].getTemperatureCelsius());

        holder.imageView.setImageResource(arrayDay[position].getIconId());

        if(position==0){
            holder.dayLabel.setText("HOY");
        }else{
            holder.dayLabel.setText((arrayDay[position].getDayofWeek()).toUpperCase());

        }

        return convertView;
    }
    private class ViewHolder {
        ImageView imageView;
        TextView temperature,dayLabel;

    }
}