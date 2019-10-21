package com.example.favoritemovie.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.favoritemovie.R;
import com.example.favoritemovie.adapter.FavoriteTvAdapter;
import com.example.favoritemovie.model.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.favoritemovie.database.MappingHelper.mapCursorToArrayList;
import static com.example.favoritemovie.database.DatabaseContract.CONTENT_URI;
import static com.example.favoritemovie.database.DatabaseContract.CONTENT_URI_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFavFragment extends Fragment implements LoadMovieCallback{

    private RecyclerView recyclerView;
    ProgressBar progressBar;

    HandlerThread handlerThread;
    private DataObserver myObserver;
    private FavoriteTvAdapter adapter;
    public TvshowFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tvshow_fav, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, getContext());
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        adapter = new FavoriteTvAdapter(getContext());
        recyclerView.setAdapter(adapter);

        new LoadTvshowAsync(getContext(), this).execute();

        return  view;
    }
    @Override
    public void onResume() {
        super.onResume();
        new LoadTvshowAsync(getContext(), this).execute();

    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void postExecute(Cursor movies) {
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<Movie> listMovies =  mapCursorToArrayList(movies);
        if (listMovies.size() > 0) {
            adapter.setListMovies(listMovies);
        } else {
            adapter.setListMovies(listMovies);
            Toast.makeText(getContext(), "Empty Movie Favorite", Toast.LENGTH_SHORT).show();
        }
        adapter.setListMovies(listMovies);
    }


    private static class LoadTvshowAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadTvshowAsync(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }
        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI_TV, null, null,null, null);
        }
        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);

        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
    }


}
