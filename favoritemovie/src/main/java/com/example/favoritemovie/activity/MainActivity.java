package com.example.favoritemovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.favoritemovie.R;
import com.example.favoritemovie.fragment.MovieFavFragment;
import com.example.favoritemovie.fragment.TvshowFavFragment;

public class MainActivity extends AppCompatActivity  {

   private Fragment pageContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        pageContent = new MovieFavFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();


    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment pageContent = null;

                    switch (item.getItemId()) {
                        case R.id.nav_movie:
                            pageContent = new MovieFavFragment();
                            break;
                        case R.id.nav_tvshow:
                            pageContent = new TvshowFavFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();

                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menusetting) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
        }
        return super.onOptionsItemSelected(item);
    }


}

