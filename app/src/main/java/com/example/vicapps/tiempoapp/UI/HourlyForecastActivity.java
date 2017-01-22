package com.example.vicapps.tiempoapp.UI;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vicapps.tiempoapp.Adapters.HourAdapter;
import com.example.vicapps.tiempoapp.R;
import com.example.vicapps.tiempoapp.weather.Hour;

import java.util.Arrays;

public class HourlyForecastActivity extends AppCompatActivity {
    Hour[]hours;
    ListView lista;
    TextView tvMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        lista = (ListView) findViewById(R.id.list_hours);
        tvMensaje= (TextView) findViewById(R.id.tv_mensaje);

        Intent i = getIntent();
        Parcelable[] parcelables = i.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        Log.i(MainActivity.TAG, "Enviando desde hourly longitud parcelables: "+ parcelables.length);
        hours= Arrays.copyOf(parcelables,parcelables.length,Hour[].class);
        Log.i(MainActivity.TAG, "Enviando desde hourly longitud: "+ hours.length);
        HourAdapter adapter = new HourAdapter(this,hours);

        if(hours.length==0){
            tvMensaje.setVisibility(View.VISIBLE);
        }else{
            tvMensaje.setVisibility(View.INVISIBLE);
        }
        lista.setAdapter(adapter);


    }
}
