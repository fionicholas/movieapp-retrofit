package com.dicoding.submission.movieapp.movie.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.dicoding.submission.movieapp.movie.R;
import com.dicoding.submission.movieapp.movie.activity.DetailActivity;
import com.dicoding.submission.movieapp.movie.model.Movie;

import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.COLUMN_MOVIEID;
import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.CONTENT_URI;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteMovieWidget extends AppWidgetProvider {
    public static final String MOVIE_ID = "MOVIE_ID";
    private static final String CLICK_ACTION = "com.dicoding.submission.movieapp.movie.CLICK_ACTION";
    public static final String UPDATE_ACTION = "com.dicoding.submission.movieapp.movie.UPDATE_ACTION";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_movie_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.tv_no_data);

        Intent clickIntent = new Intent(context, FavoriteMovieWidget.class);
        clickIntent.setAction(FavoriteMovieWidget.CLICK_ACTION);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, clickPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(CLICK_ACTION)) {

                Intent i = new Intent(context, DetailActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Uri uri = Uri.parse(CONTENT_URI + "/" + intent.getStringExtra(MOVIE_ID));
                i.setData(uri);
                context.startActivity(i);


            }


            if (intent.getAction().equals(UPDATE_ACTION)){
                int widgetIDs[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, FavoriteMovieWidget.class));
                AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(widgetIDs, R.id.stack_view);
            }
        }
        super.onReceive(context, intent);
    }
}

