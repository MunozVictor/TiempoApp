package com.example.vicapps.tiempoapp.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.vicapps.tiempoapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 21542295 on 19/01/2017.
 */
public class Hour implements Parcelable{
    private long mTime;
    private String mSummary;
    private double mTemperature;
    private String mIcon;
    //aunque no es parte de hourly lo incluimos para mostrar la hora local
    private String mTimeZone;
    public static Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel source) {
            return new Hour(source);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };


    public Hour() {
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }

    public double getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public String getmTimeZone() {
        return mTimeZone;
    }

    public void setmTimeZone(String mTimeZone) {
        this.mTimeZone = mTimeZone;
    }

    public String getFormattedTime (){
        // Seleccionaremos el format que queremos
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        //Determinamos la hora que será según la zona horario
        formatter.setTimeZone(TimeZone.getTimeZone(getmTimeZone()));
        //Obtenemos la hora mediate la clase Date, el objeto está en milisegundos pore so lo multiplicamos por 1000
        Date dateTime = new Date(getmTime()*1000);
        //Almacenamos en un String la hora generada con el format creado
        String timeString = formatter.format(dateTime);
        return timeString;
    }
    public int getTemperatureCelsius(){
        int grados;
        grados = (int)Math.round((mTemperature-32)/1.8);
        return grados;

    }
    public int getIconId(){
        int iconId = R.drawable.clear_day;
        //se comproueba por nombre que del icono cuando hacemos el get icon
        //y asignar de esta manera la imagen que queramos mostrar devolvera el id entero de la imagen que sea
        if (mIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }
        return iconId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeDouble(mTemperature);
        dest.writeString(mIcon);
        dest.writeString(mTimeZone);
    }
    private Hour(Parcel in){
        mTime = in.readLong();
        mSummary = in.readString();
        mTemperature=in.readDouble();
        mIcon=in.readString();
        mTimeZone=in.readString();



    }
}
