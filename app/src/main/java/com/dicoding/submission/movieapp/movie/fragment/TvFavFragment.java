package com.dicoding.submission.movieapp.movie.fragment;


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

import com.dicoding.submission.movieapp.movie.R;
import com.dicoding.submission.movieapp.movie.adapter.FavoriteTvAdapter;
import com.dicoding.submission.movieapp.movie.model.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.CONTENT_URI;
import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.FavoriteEntry.CONTENT_URI_TV;
import static com.dicoding.submission.movieapp.movie.database.MappingHelper.mapCursorToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFavFragment extends Fragment implements LoadMovieCallback{

    private RecyclerView recyclerView;
    private FavoriteTvAdapter adapter;
    ProgressBar progressBar;
    HandlerThread handlerThread;
    private TvFavFragment.DataObserver myObserver;

    public TvFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_fav, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBarFav);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, getContext());
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        adapter = new FavoriteTvAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        new TvFavFragment.LoadTvshowAsync(getContext(), this).execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new TvFavFragment.LoadTvshowAsync(getContext(), this).execute();
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void postExecute(Cursor movies) {
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<Movie> listTv =  mapCursorToArrayList(movies);
        if (listTv.size() > 0) {
            adapter.setListTvshow(listTv);
        } else {
            adapter.setListTvshow(listTv);
            Toast.makeText(getContext(), "Empty Tv Show Favorite", Toast.LENGTH_SHORT).show();
        }
        adapter.setListTvshow(listTv);
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


