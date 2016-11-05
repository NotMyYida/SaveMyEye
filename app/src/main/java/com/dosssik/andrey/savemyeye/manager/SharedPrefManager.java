package com.dosssik.andrey.savemyeye.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String MAIN_KEY = "PREF_FILE";

    private static final String CUSTOM_WORK_PERIOD = "CUSTOM_WORK_PERIOD";
    private static final String CUSTOM_REST_PERIOD = "CUSTOM_REST_PERIOD";

    private static final long TWENTY_SECONDS = 20 * 1000;
    private static final long TWENTY_MINUTES = 20 * 60 * 1000;
    private static final long FIFTY_MINUTES = 50 * 60 * 1000;
    private static final long FIVE_MINUTES = 5 * 60 * 1000;


    public static final long DEFAULT_WORK_PERIOD = TWENTY_MINUTES;
    public static final long DEFAULT_REST_PERIOD = TWENTY_SECONDS;

    public static final long DEFAULT_LONG_WORK_PERIOD = FIFTY_MINUTES;
    public static final long DEFAULT_LONG_REST_PERIOD = FIVE_MINUTES;


    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(MAIN_KEY, Context.MODE_PRIVATE);
    }

    public static long getCustomWorkPeriod(Context context) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getLong(CUSTOM_WORK_PERIOD, TWENTY_MINUTES);
    }

    public static void setCustomWorkPeriod(Context context, long workPeriod) {
        SharedPreferences prefs = getPrefs(context);
        prefs.edit().putLong(CUSTOM_WORK_PERIOD, workPeriod).apply();
    }

    public static long getCustomRestPeriod(Context context) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getLong(CUSTOM_REST_PERIOD, TWENTY_SECONDS);
    }

    public static void setCustomRestPeriod(Context context, long workPeriod) {
        SharedPreferences prefs = getPrefs(context);
        prefs.edit().putLong(CUSTOM_REST_PERIOD, workPeriod).apply();
    }


}
