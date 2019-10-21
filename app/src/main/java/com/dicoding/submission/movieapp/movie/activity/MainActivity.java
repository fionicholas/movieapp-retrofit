package com.dicoding.submission.movieapp.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dicoding.submission.movieapp.movie.R;
import com.dicoding.submission.movieapp.movie.fragment.MovieFavFragment;
import com.dicoding.submission.movieapp.movie.fragment.MovieFragment;
import com.dicoding.submission.movieapp.movie.fragment.TvFavFragment;
import com.dicoding.submission.movieapp.movie.fragment.TvShowFragment;


public class MainActivity extends AppCompatActivity {


    private Fragment pageContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabsearch = findViewById(R.id.fab);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        pageContent = new MovieFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();

        fabsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment pageContent = null;

                    switch (item.getItemId()) {
                        case R.id.nav_movie:
                            pageContent = new MovieFragment();
                            break;
                        case R.id.nav_tvshow:
                            pageContent = new TvShowFragment();
                            break;
                        case R.id.nav_favmovie:
                            pageContent = new MovieFavFragment();
                            break;
                        case R.id.nav_favtv:
                            pageContent = new TvFavFragment();
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
            Intent mIntent = new Intent(this, SettingActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }


}

