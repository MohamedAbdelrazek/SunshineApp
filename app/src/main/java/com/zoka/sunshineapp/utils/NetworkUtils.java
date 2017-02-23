package com.zoka.sunshineapp.utils;

import android.net.Uri;

import com.zoka.sunshineapp.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mohamed AbdelraZek on 2/19/2017.
 */

public class NetworkUtils {
    //Test comment

    //http://api.openweathermap.org/data/2.5/forecast/daily?q=Egypt&mode=json&units=metric&cnt=7&appid=898f65633246b1b5acdd60cb7250da84
    final static String format = "json";
    static final String units = "metric";
    static public final int numDays = 7;
    static final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    static final String QUERY_PARAM = "q";
    static final String FORMAT_PARAM = "mode";
    static final String UNITS_PARAM = "units";
    static final String DAYS_PARAM = "cnt";
    static final String APPID_PARAM = "APPID";

    public static URL buildQueryParam(String queryParam) throws MalformedURLException {
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryParam)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                .appendQueryParameter(APPID_PARAM, BuildConfig.API_KEY)
                .build();

        URL url = new URL(builtUri.toString());
        return url;
    }


}
