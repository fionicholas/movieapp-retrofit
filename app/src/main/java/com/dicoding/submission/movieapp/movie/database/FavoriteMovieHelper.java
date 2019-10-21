package com.dicoding.submission.movieapp.movie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.COLUMN_MOVIEID;
import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.TABLE_NAME;
import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.TABLE_NAME_TV;


public class FavoriteMovieHelper {

    private static final String TABLE_MOVIE = TABLE_NAME;
    private static final String TABLE_TV = TABLE_NAME_TV;
    private DatabaseHelper helper;
    private static FavoriteMovieHelper INSTANCE;
    private SQLiteDatabase database;

    private FavoriteMovieHelper(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static FavoriteMovieHelper getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavoriteMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }


    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryByIdProviderTV(String id) {
        return database.query(TABLE_TV, null
                , COLUMN_MOVIEID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProviderTV() {
        return database.query(TABLE_TV
                , null
                , null
                , null
                , null
                , null
                , COLUMN_MOVIEID + " ASC");
    }

    public long insertProviderTV(ContentValues values) {
        return database.insert(TABLE_TV, null, values);
    }

    public int updateProviderTV(String id, ContentValues values) {
        return database.update(TABLE_TV, values, COLUMN_MOVIEID + " = ?", new String[]{id});
    }

    public int deleteProviderTV(String id) {
        return database.delete(TABLE_TV, COLUMN_MOVIEID + " = ?", new String[]{id});
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(TABLE_MOVIE, null
                , COLUMN_MOVIEID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null
                , COLUMN_MOVIEID + " ASC");

    }

    public long insertProvider(ContentValues values) {
        return database.insert(TABLE_MOVIE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(TABLE_MOVIE, values, COLUMN_MOVIEID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(TABLE_MOVIE, COLUMN_MOVIEID + " = ?", new String[]{id});
    }
}


