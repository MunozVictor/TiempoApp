package com.example.vicapps.tiempoapp.UI;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vicapps.tiempoapp.Adapters.DayAdapter;
import com.example.vicapps.tiempoapp.R;
import com.example.vicapps.tiempoapp.weather.Day;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DailyForecastActivity extends AppCompatActivity{

    Day[] mDay;
    ListView lista;
    TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        mensaje = (TextView) findViewById(R.id.empty);
        lista = (ListView) findViewById(R.id.list);

        Intent i = getIntent();
        Parcelable[] parcelables = i.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDay = Arrays.copyOf(parcelables,parcelables.length,Day[].class);
        DayAdapter adapter = new DayAdapter(this , mDay);
        if(mDay.length==0){
            mensaje.setVisibility(View.VISIBLE);
        }else{
            mensaje.setVisibility(View.INVISIBLE);
        }
        lista.setAdapter(adapter);
    }
}
