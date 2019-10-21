package com.dicoding.submission.movieapp.movie.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MoviePreference {

    SharedPreferences prefs;
    Context context;
    String PREFERENCES_REMINDER_DAILY = "pref_reminder_daily";
    String PREFERENCES_REMINDER_DAILY_TIME ="pref_reminder_daily_time";
    String PREFERENCES_REMINDER_RELEASE  ="pref_reminder_release";

    public MoviePreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setDailyReminder(Boolean input, String time){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREFERENCES_REMINDER_DAILY, input);
        editor.putString(PREFERENCES_REMINDER_DAILY_TIME, time);
        editor.apply();
    }

    public Boolean getDailyReminder(){
        return prefs.getBoolean(PREFERENCES_REMINDER_DAILY, true);
    }

    public void setReleaseTodayReminder(Boolean input, String time){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREFERENCES_REMINDER_RELEASE, input);
        editor.putString(PREFERENCES_REMINDER_DAILY_TIME, time);
        editor.apply();
    }

    public Boolean getReleaseTodayReminder(){
        return prefs.getBoolean(PREFERENCES_REMINDER_RELEASE, true);
    }
}


