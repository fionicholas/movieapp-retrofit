package com.dicoding.submission.movieapp.movie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.submission.movieapp.movie.R;
import com.dicoding.submission.movieapp.movie.activity.DetailActivity;
import com.dicoding.submission.movieapp.movie.database.DatabaseContract;
import com.dicoding.submission.movieapp.movie.database.DatabaseHelper;
import com.dicoding.submission.movieapp.movie.model.Movie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    public static final String MOVIE_TITLE = "MOVIE_TITLE";
    public static final String MOVIE_ID = "MOVIE_ID";
    private final Context context;
    private final ArrayList<Movie> movieList = new ArrayList<>();
    private int appWidgetId;


    public StackRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        movieList.clear();
        loadWidgetData();
    }

    private void loadWidgetData () {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query(DatabaseContract.FavoriteEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie fav = new Movie(cursor);
                movieList.add(fav);
            } while (cursor.moveToNext());
            cursor.close();
        }

    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.item_widget);

        try {
            Movie movie = movieList.get(position);

            Bundle bundle = new Bundle();
            bundle.putString(MOVIE_ID, movie.getId());
            bundle.putString(MOVIE_TITLE, movie.getTitle());

            Intent intent = new Intent();
            intent.putExtras(bundle);
           // intent.putExtra(DetailActivity.DETAIL_MOVIE, movie);
            rv.setOnClickFillInIntent(R.id.iv_banner, intent);

            String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();

            try {
                Bitmap preview = Glide.with(context)
                        .asBitmap()
                        .load(posterUrl)
                        .apply(new RequestOptions().fitCenter())
                        .submit()
                        .get();
                rv.setImageViewBitmap(R.id.iv_banner, preview);
                rv.setTextViewText(R.id.tv_movie_title, movie.getTitle());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
