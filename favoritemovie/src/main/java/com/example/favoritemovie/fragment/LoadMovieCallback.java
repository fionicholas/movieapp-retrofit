package com.example.favoritemovie.fragment;

import android.database.Cursor;

public interface LoadMovieCallback {
    void preExecute();
    void postExecute(Cursor notes);
}

