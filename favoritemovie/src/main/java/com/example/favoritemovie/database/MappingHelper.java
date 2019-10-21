package com.example.favoritemovie.database;

import android.database.Cursor;

import com.example.favoritemovie.database.DatabaseContract;
import com.example.favoritemovie.model.Movie;

import java.util.ArrayList;

public class MappingHelper {


    public static ArrayList<Movie> mapCursorToArrayList(Cursor moviesListCursor) {
        ArrayList<Movie> moviesList = new ArrayList<>();
        while (moviesListCursor.moveToNext()) {
            String id = moviesListCursor.getString(moviesListCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteEntry.COLUMN_MOVIEID));
            String title = moviesListCursor.getString(moviesListCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteEntry.COLUMN_TITLE));
            String voteAverage = moviesListCursor.getString(moviesListCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteEntry.COLUMN_USERRATING));
            String poster_path = moviesListCursor.getString(moviesListCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteEntry.COLUMN_POSTER_PATH));
            String overview = moviesListCursor.getString(moviesListCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS));
            String releaseDate = moviesListCursor.getString(moviesListCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteEntry.COLUMN_RELEASE));
            String originalLanguage = moviesListCursor.getString(moviesListCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteEntry.COLUMN_LANGUANGE));
            String originalName = moviesListCursor.getString(moviesListCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteEntry.COLUMN_TITLE));
            String firstAirDate = moviesListCursor.getString(moviesListCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteEntry.COLUMN_RELEASE));

            moviesList.add(new Movie(id, title, voteAverage, poster_path, overview, releaseDate, originalLanguage, originalName, firstAirDate));
        }
        return moviesList;
    }
}

