package com.dicoding.submission.movieapp.movie.fragment;

import android.database.Cursor;

public interface LoadMovieCallback {

        void preExecute();
        void postExecute(Cursor notes);
    }

