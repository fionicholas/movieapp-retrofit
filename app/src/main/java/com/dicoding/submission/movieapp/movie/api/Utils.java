package com.dicoding.submission.movieapp.movie.api;

import com.dicoding.submission.movieapp.movie.api.Client;
import com.dicoding.submission.movieapp.movie.api.Service;

public class Utils {

    public static Service geClient() {
        return Client.getClient().create(Service.class);
    }

}