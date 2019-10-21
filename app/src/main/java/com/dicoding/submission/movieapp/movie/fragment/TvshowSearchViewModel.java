package com.dicoding.submission.movieapp.movie.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.dicoding.submission.movieapp.movie.BuildConfig;
import com.dicoding.submission.movieapp.movie.api.Utils;
import com.dicoding.submission.movieapp.movie.model.Movie;
import com.dicoding.submission.movieapp.movie.model.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvshowSearchViewModel extends ViewModel {

    public final static String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void setSearchData(String key, final Context context) {
        isLoading.postValue(true);
        Utils.geClient().getTvshowSearch(API_KEY , "en-US" ,key)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        isLoading.postValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getMovies() != null) {
                                movieList.setValue(response.body().getMovies());
                            } else {
                                Toast.makeText(context, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        isLoading.postValue(false);
                    }
                });
    }

    LiveData<Boolean> getLoading() {
        return isLoading;
    }
    LiveData<List<Movie>> getMoviesData() {
        return movieList;
    }
}
