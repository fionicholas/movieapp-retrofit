package com.dicoding.submission.movieapp.movie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dicoding.submission.movieapp.movie.database.FavoriteMovieHelper;

import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.AUTHORITY;
import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.CONTENT_URI;
import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.CONTENT_URI_TV;
import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.TABLE_NAME;
import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.TABLE_NAME_TV;

public class FavoriteProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int ID = 2;

    private static final int TVSHOW = 3;
    private static final int ID_TV = 4;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", ID);

        uriMatcher.addURI(AUTHORITY, TABLE_NAME_TV, TVSHOW);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME_TV + "/#", ID_TV);
    }

    private FavoriteMovieHelper favoriteMovieHelper;

    @Override
    public boolean onCreate() {
        favoriteMovieHelper = FavoriteMovieHelper.getInstance(getContext());
        favoriteMovieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        favoriteMovieHelper.open();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = favoriteMovieHelper.queryProvider();
                break;
            case ID:
                cursor = favoriteMovieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TVSHOW:
                cursor = favoriteMovieHelper.queryProviderTV();
                break;
            case ID_TV:
                cursor = favoriteMovieHelper.queryByIdProviderTV(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }



        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        favoriteMovieHelper.open();
        long added;

        switch (uriMatcher.match(uri)) {
            case MOVIE:
                added = favoriteMovieHelper.insertProvider(values);
                break;
            case TVSHOW:
                added = favoriteMovieHelper.insertProviderTV(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Nullable
    public Uri insertTVshow(@NonNull Uri uri, @Nullable ContentValues values) {
        favoriteMovieHelper.open();
        long added;

        switch (uriMatcher.match(uri)) {
            case TVSHOW:
                added = favoriteMovieHelper.insertProviderTV(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI_TV + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        favoriteMovieHelper.open();
        int deleted;
        switch (uriMatcher.match(uri)) {
            case ID:
                deleted = favoriteMovieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case ID_TV:
                deleted = favoriteMovieHelper.deleteProviderTV(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        favoriteMovieHelper.open();
        int updated;
        switch (uriMatcher.match(uri)) {
            case ID:
                updated = favoriteMovieHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            case ID_TV:
                updated = favoriteMovieHelper.updateProviderTV(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }


        return updated;
    }
}


