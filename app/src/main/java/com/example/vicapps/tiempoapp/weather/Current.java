package com.example.vicapps.tiempoapp.weather;

import com.example.vicapps.tiempoapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 21542295 on 15/12/2016.
 */
public class Current {


    /*
    (Si no podemos abrir alguna web que nos parsee los archivos JSON online)
    Después de instalar este complemento vemos el archivo mucho más claro. Debemos fijarnos
    en la estructura del archivo y analizar cómo se crean los objetos, los pares clave-valor, los
    objetos JSON creados dentro de la raíz, los arrays, etc. Esta información será importante a la
    hora de recuperar la información. Vamos a fijarnos especialmente en el objeto Currently,
    porque es de donde vamos a obtener la información.
    Para poder almacenar los datos vamos a necesitar una nueva clase Java. Una clase que
    llamaremos Current y estos serán sus atributos:
     */
    private String icon;
    private long time;
    private double temperature;
    private double humidity;
    private double precipChance;
    private String summary;
    private String timeZone;

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getPrecipChance() {
        return precipChance;
    }

    public void setPrecipChance(double precipChance) {
        this.precipChance = precipChance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getFormattedTime (){
    // Seleccionaremos el format que queremos
            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
    //Determinamos la hora que será según la zona horario
            formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
    //Obtenemos la hora mediate la clase Date, el objeto está en milisegundos pore so lo multiplicamos por 1000
            Date dateTime = new Date(getTime()*1000);
    //Almacenamos en un String la hora generada con el format creado
            String timeString = formatter.format(dateTime);
            return timeString;
    }

    public int getTemperatureCelsius(){
        int grados;
        grados = (int)Math.round((temperature-32)/1.8);

        return grados;
    }

    public int getIconId(){
        int iconId = R.drawable.clear_day;
        //se comproueba por nombre que del icono cuando hacemos el get icon
        //y asignar de esta manera la imagen que queramos mostrar devolvera el id entero de la imagen que sea
        if (icon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (icon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (icon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (icon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (icon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (icon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (icon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (icon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (icon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (icon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }
        return iconId;
    }
}
