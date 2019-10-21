package com.dicoding.submission.movieapp.movie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dicoding.submission.movieapp.movie.fragment.MovieSearchFragment;
import com.dicoding.submission.movieapp.movie.fragment.TvShowSearchFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {


    int tabCount;

    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MovieSearchFragment tab1 = new MovieSearchFragment();
                return tab1;
            case 1:
                TvShowSearchFragment tab2 = new TvShowSearchFragment();
                return tab2;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return tabCount;
    }
}