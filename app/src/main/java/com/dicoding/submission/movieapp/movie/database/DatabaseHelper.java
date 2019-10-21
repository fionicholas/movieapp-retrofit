package com.dicoding.submission.movieapp.movie.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dicoding.submission.movieapp.movie.model.Movie;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE "+DatabaseContract.FavoriteEntry.TABLE_NAME+ " ("
                +DatabaseContract.FavoriteEntry.COLUMN_MOVIEID + " INTEGER PRIMARY KEY, " +
                DatabaseContract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_USERRATING + " REAL NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_RELEASE + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_LANGUANGE + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL" +
                "); ";

        final String SQL_CREATE_FAVORITE_TABLE_TV = "CREATE TABLE "+DatabaseContract.FavoriteEntry.TABLE_NAME_TV+ " ("
                +DatabaseContract.FavoriteEntry.COLUMN_MOVIEID + " INTEGER PRIMARY KEY, " +
                DatabaseContract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_USERRATING + " REAL NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_RELEASE + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_LANGUANGE + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL" + "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }


    // Movie
    public List<Movie> getAllFavoriteMovie(){
        String[] columns = {
                DatabaseContract.FavoriteEntry.COLUMN_MOVIEID,
                DatabaseContract.FavoriteEntry.COLUMN_TITLE,
                DatabaseContract.FavoriteEntry.COLUMN_USERRATING,
                DatabaseContract.FavoriteEntry.COLUMN_POSTER_PATH,
                DatabaseContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS,
                DatabaseContract.FavoriteEntry.COLUMN_RELEASE,
                DatabaseContract.FavoriteEntry.COLUMN_LANGUANGE
        };
        String sortOrder =
                DatabaseContract.FavoriteEntry.COLUMN_MOVIEID + " ASC";
        List<Movie> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DatabaseContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

        if(cursor.moveToFirst()){
            do{
                Movie movie = new Movie();
                movie.setId(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavoriteEntry.COLUMN_MOVIEID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavoriteEntry.COLUMN_TITLE)));
                movie.setVoteAverage(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavoriteEntry.COLUMN_USERRATING)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavoriteEntry.COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavoriteEntry.COLUMN_RELEASE)));
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavoriteEntry.COLUMN_LANGUANGE)));

                favoriteList.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }

}
