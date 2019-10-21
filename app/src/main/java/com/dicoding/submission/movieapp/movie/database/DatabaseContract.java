package com.dicoding.submission.movieapp.movie.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String TABLE_NAME_TV = "favorite_tv";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
        public static final String COLUMN_RELEASE = "release_date";
        public static final String COLUMN_LANGUANGE = "language";
        public static final String AUTHORITY = "com.dicoding.submission.movieapp.movie";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME_TV)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

}
