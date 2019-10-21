package com.dicoding.submission.movieapp.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dicoding.submission.movieapp.movie.R;

public class SplashActivity extends AppCompatActivity {

    Animation fade;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);

        logo = findViewById(R.id.logo);

        logo.startAnimation(fade);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gotoLogin = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(gotoLogin);
                finish();
            }
        }, 2000);
    }
}
