package com.dicoding.submission.movieapp.movie.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.submission.movieapp.movie.R;
import com.dicoding.submission.movieapp.movie.adapter.MoviesAdapter;
import com.dicoding.submission.movieapp.movie.model.Movie;

import java.util.List;


public class MovieSearchFragment extends Fragment {


    ProgressBar pb;
    SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    EditText etMovie;
    Button btnSearch;


    public MovieSearchFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);

        swipeContainer = view.findViewById(R.id.main_content);
        recyclerView = view.findViewById(R.id.recycler_view);
        pb = view.findViewById(R.id.progressBar);

        etMovie = view.findViewById(R.id.et_movie);
        btnSearch = view.findViewById(R.id.btn_search);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        pb.setVisibility(View.GONE);

        btnSearch.setOnClickListener(myListener);
        swipeContainer = view.findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(getActivity(), "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), etMovie.getText().toString(), Toast.LENGTH_SHORT).show();
            String key = etMovie.getText().toString();

            MovieSearchViewModel viewModel = ViewModelProviders.of(getActivity())
                    .get(MovieSearchViewModel.class);

            viewModel.getLoading().observe(getActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean isLoading) {
                    if (isLoading == true) {
                        pb.setVisibility(View.VISIBLE);

                    } else {
                        pb.setVisibility(View.GONE);
                    }
                }
            });

            if(TextUtils.isEmpty(key)) return;
            viewModel.setSearchData(key, getContext());

            viewModel.getMoviesData().observe(getActivity(), new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> moviesData) {
                    if (moviesData != null) {

                        recyclerView.setAdapter(new MoviesAdapter(getContext(), moviesData));
                        recyclerView.smoothScrollToPosition(0);
                        if (swipeContainer.isRefreshing()) {
                            swipeContainer.setRefreshing(false);
                        }


                    }
                }
            });
        }
    };
}