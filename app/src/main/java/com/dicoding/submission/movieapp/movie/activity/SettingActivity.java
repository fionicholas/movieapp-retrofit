package com.dicoding.submission.movieapp.movie.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.dicoding.submission.movieapp.movie.R;
import com.dicoding.submission.movieapp.movie.database.MoviePreference;
import com.dicoding.submission.movieapp.movie.reminder.DailyReminder;
import com.dicoding.submission.movieapp.movie.reminder.ReleaseTodayReminder;

public class SettingActivity extends AppCompatActivity {

    ToggleButton tbDailyReminder;
    ToggleButton tbReleaseTodayReminder;
    Button  btn_language;
    private DailyReminder dailyReminder;
    private ReleaseTodayReminder releaseTodayReminder;
    private MoviePreference moviePreference;
    public static final String TIME_DAILY = "07:00";
    public static final String TIME_RELEASE = "08:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tbDailyReminder = findViewById(R.id.tb_daily_reminder);
        tbReleaseTodayReminder = findViewById(R.id.tb_release_today_reminder);
        btn_language = findViewById(R.id.btn_change_language);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dailyReminder = new DailyReminder();
        releaseTodayReminder = new ReleaseTodayReminder();

        moviePreference = new MoviePreference(this);
        loadPreference();


        btn_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }
        });


        tbDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moviePreference.setDailyReminder(isChecked, TIME_DAILY);
                if (isChecked) {
                    tbDailyReminder.setBackground(getDrawable(R.drawable.back_daily_light));
                    dailyReminder.startReminder(SettingActivity.this, TIME_DAILY);
                } else {
                    tbDailyReminder.setBackground(getDrawable(R.drawable.back_daily_dark));
                    dailyReminder.stopReminder(SettingActivity.this);
                }
            }
        });

        tbReleaseTodayReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moviePreference.setReleaseTodayReminder(isChecked, TIME_RELEASE);
                if (isChecked) {
                    tbReleaseTodayReminder.setBackground(getDrawable(R.drawable.back_daily_light));
                    releaseTodayReminder.startReminder(SettingActivity.this, TIME_RELEASE);
                } else {
                    tbReleaseTodayReminder.setBackground(getDrawable(R.drawable.back_daily_dark));
                    releaseTodayReminder.stopReminder(SettingActivity.this);
                }
            }
        });
    }

    private void loadPreference() {
        tbDailyReminder.setChecked(moviePreference.getDailyReminder());
        if (moviePreference.getDailyReminder()) {
            tbDailyReminder.setBackground(getDrawable(R.drawable.back_daily_light));
        } else {
            tbDailyReminder.setBackground(getDrawable(R.drawable.back_daily_dark));
        }
        tbReleaseTodayReminder.setChecked(moviePreference.getReleaseTodayReminder());
        if (moviePreference.getReleaseTodayReminder()) {
            tbReleaseTodayReminder.setBackground(getDrawable(R.drawable.back_daily_light));
        } else {
            tbReleaseTodayReminder.setBackground(getDrawable(R.drawable.back_daily_dark));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}