package com.example.vicapps.tiempoapp.weather;

/**
 * Created by 21542295 on 19/01/2017.
 */
public class Forecast {
    private Current current;
    private Hour[] mHourlyForecast;
    private Day[] mDaylyForecast;

    public Forecast() {
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Day[] getmDaylyForecast() {
        return mDaylyForecast;
    }

    public void setmDaylyForecast(Day[] mDaylyForecast) {
        this.mDaylyForecast = mDaylyForecast;
    }

    public Hour[] getmHourlyForecast() {
        return mHourlyForecast;
    }

    public void setmHourlyForecast(Hour[] mHourlyForecast) {
        this.mHourlyForecast = mHourlyForecast;
    }
}
