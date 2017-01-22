package com.example.vicapps.tiempoapp.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vicapps.tiempoapp.R;
import com.example.vicapps.tiempoapp.weather.Current;
import com.example.vicapps.tiempoapp.weather.Day;
import com.example.vicapps.tiempoapp.weather.Forecast;
import com.example.vicapps.tiempoapp.weather.Hour;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";

    //String forecasturl = "https://api.darksky.net/forecast/4e289f42073b483d96acba37e28e601a/37.8267,-122.4233";
                                                            // key   de la pag darksky          coordenadas
    //solo permite 1000 clicks

    String key ="4e289f42073b483d96acba37e28e601a";
    String lat="40.5358";
    String lon = "-3.61661";
    String forecastURL = "https://api.darksky.net/forecast/"+key+"/"+lat+","+lon;
    ImageView iconImageView , imageRefresh ;
    TextView timeLabel,temperatureLabel,precipValue,humidityValue,summary,locationLabel;
    ProgressBar progresBar;
    Forecast forecast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconImageView = (ImageView) findViewById(R.id.iconImageView);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        temperatureLabel = (TextView) findViewById(R.id.temperatureLabel);
        precipValue= (TextView) findViewById(R.id.precipValue);
        humidityValue = (TextView) findViewById(R.id.humidityValue);
        summary = (TextView) findViewById(R.id.summaryLabel);
        locationLabel = (TextView) findViewById(R.id.locationLabel);
        imageRefresh = (ImageView) findViewById(R.id.imageRefresh);
        progresBar = (ProgressBar) findViewById(R.id.progressBar);
        progresBar.setVisibility(View.INVISIBLE);
        obtenerForcast();
    }

    private void obtenerForcast() {
        if(isNetworkAvailable()) {
            imageRefresh.setVisibility(View.INVISIBLE);
            progresBar.setVisibility(View.VISIBLE);


            OkHttpClient client = new OkHttpClient();//se importa la libreria que hemos añadido en el gradle moduleapp
            Request request = new Request.Builder().url(forecastURL).build();
            //se crea la consulta a la que pasamos la url
            Call call = client.newCall(request);

        /*¿Qué hacer cuando la red no funciona? Es posible que nos encontremos en un lugar sin acceso
        a la red, por lo que en ese caso es mejor no realizar el intento de petición de datos a la web y
        mostrar un mensaje avisando de la falta de red. Para eso, en nuestro proyecto meteremos
        tod o el código de conexión a la red en un if (desde antes de crear el objeto OkHttpClient) hasta
        después terminar los métodos del Callback.
         */


            call.enqueue(new Callback() {//enqueue se pone en cola
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageRefresh.setVisibility(View.VISIBLE);
                            progresBar.setVisibility(View.INVISIBLE);

                        }
                    });


                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageRefresh.setVisibility(View.VISIBLE);
                                progresBar.setVisibility(View.INVISIBLE);
                            }
                        });

                        //los lopgs a la hora de querer subir la apk no permite se sustituyen , no esta permitido
                        //Response response = call.execute();//este proceso debe esperar a que el layout se carga por lo que hay que ponerlo en cola
                        String jsonData=response.body().string();//solo se puede hacer un response
                        Log.v(TAG, jsonData);//devuelve los daros del archivo json con los datos del api que estamos usando

                        if (response.isSuccessful()) {
                            forecast = parseForecastDetail(jsonData);
                            cargarDatos();





                        } else {
                            //SALTARA CUANDO HAYA UN NOT FOUND
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Excepcion: Entrada / Salida", e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }else{
            Toast.makeText(this,"Error de conexion",Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatos() {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Current current = forecast.getCurrent();
                Drawable img = getResources().getDrawable(current.getIconId());
                iconImageView.setImageDrawable(img);
                timeLabel.setText(current.getFormattedTime());
                temperatureLabel.setText(current.getTemperatureCelsius()+"");
                precipValue.setText(Math.round((current.getPrecipChance())*100)+"%");
                humidityValue.setText(Math.round((current.getHumidity())*100)+"%");
                summary.setText(current.getSummary());
                locationLabel.setText(current.getTimeZone());

            }
        });





    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
       Current c = new Current();
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");//accedemos al valor del timezone del json
        Log.i(TAG, "Obtenido desde JSON: "+ timezone);
        //creación del objeto JSON que almacene el objeto currently definido dentro de la raíz.
        JSONObject currently = forecast.getJSONObject("currently");
        //Creación de un objeto Current donde vayamos almacendo uno por uno los valores que nos interesan
        c.setHumidity(currently.getDouble("humidity"));
        Log.i(TAG, "Obtenido desde JSON: "+ c.getHumidity());
        c.setTime(currently.getLong("time"));
        Log.i(TAG, "Obtenido desde JSON: "+ c.getTime());
        c.setPrecipChance(currently.getDouble("precipProbability"));
        c.setTemperature(currently.getDouble("temperature"));
        c.setIcon(currently.getString("icon"));
        c.setSummary(currently.getString("summary"));
        c.setTimeZone(timezone);

        Log.i(TAG, c.getFormattedTime());

        return c;
    }
    private Hour [] getHourly(String jsonData) throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "Obtenido desde JSON: "+ timezone);
        JSONObject hourlyJSON = forecast.getJSONObject("hourly");
        //como data no tiene nombres en el array traemos todos y lo inicializamos con su longitud
        JSONArray data = hourlyJSON.getJSONArray("data");
        Hour hourly[] = new Hour[data.length()];
        JSONObject hourJSON;
        for (int i = 0 ; i<hourly.length;i++){
            hourJSON = data.getJSONObject(i);
            hourly[i]=new Hour();

            hourly[i].setmTime(hourJSON.getLong("time"));
            hourly[i].setmIcon(hourJSON.getString("icon"));
            hourly[i].setmTemperature(hourJSON.getDouble("temperature"));
            hourly[i].setmSummary(hourJSON.getString("summary"));
            hourly[i].setmTimeZone(timezone);

        }

        return hourly;
    }
    private Day[] getDaily(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "Obtenido desde JSON: "+ timezone);

        JSONObject dayJSON = forecast.getJSONObject("daily");
        //como data no tiene nombres en el array traemos todos y lo inicializamos con su longitud
        JSONArray data = dayJSON.getJSONArray("data");
        Day daily[] = new Day[data.length()];
        JSONObject dailyJSON;
        for (int i = 0 ; i<daily.length;i++){
            dailyJSON = data.getJSONObject(i);
            daily[i]=new Day();
            daily[i].setmTime(dailyJSON.getLong("time"));
            daily[i].setmIcon(dailyJSON.getString("icon"));
            daily[i].setmTemperature(dailyJSON.getDouble("temperatureMax"));
            Log.i(TAG, "Obtenido desde JSON temperatura en celsius: "+ dailyJSON.getDouble("temperatureMax"));
            daily[i].setmSummary(dailyJSON.getString("summary"));
            daily[i].setmTimeZone(timezone);

        }

        return daily;

    }

    private Forecast parseForecastDetail(String jsonData)throws JSONException{
        Forecast f = new Forecast();
        f.setCurrent(getCurrentDetails(jsonData));
        f.setmDaylyForecast(getDaily(jsonData));
        f.setmHourlyForecast(getHourly(jsonData));
        return f;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "Error dialog:");
    }
    private boolean isNetworkAvailable() {
        /*
        Como condición del if pondremos haremos la llamada a un método isNetworkAvailable() que
        implementaremos a continuación. Es posible que no se entienda bien el código de primeras, ya
        que nos varias clases nuevas de golpe, pero se trata de comprobar desde nuestra aplicación
        que nuestro terminal está conectada a la red (para esto deberemos añadir un permiso nuevo
        en el manifest). Devuelve true si está conectado y false si no lo está:

         */
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();//hay que habilitar permisos en el manifest
        boolean isAvailable=false;
        if(networkInfo!=null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }
    public void onClickRefresh(View v){
        obtenerForcast();
    }

    public void startDailyActivty (View v ){
        Intent i = new Intent(MainActivity.this, DailyForecastActivity.class);

        i.putExtra(DAILY_FORECAST,forecast.getmDaylyForecast());


        startActivity(i);
    }



}
