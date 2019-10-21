package com.dicoding.submission.movieapp.movie.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.submission.movieapp.movie.R;
import com.dicoding.submission.movieapp.movie.adapter.TvShowAdapter;
import com.dicoding.submission.movieapp.movie.model.Movie;

import java.util.List;


public class TvShowFragment extends Fragment {


    private RecyclerView recyclerView;

    ProgressBar pb;
    SwipeRefreshLayout swipeContainer;



    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        pb = view.findViewById(R.id.progressBar);
        swipeContainer = view.findViewById(R.id.main_content);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(getActivity(), "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TvShowViewModel viewModel = ViewModelProviders.of(getActivity())
                .get(TvShowViewModel.class);


        viewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading == true) {
                    pb.setVisibility(View.VISIBLE);

                }else {
                    pb.setVisibility(View.GONE);
                }
            }
        });

        viewModel.setData("1", getContext());
        viewModel.getMoviesData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesData) {
                if (moviesData != null) {

                    recyclerView.setAdapter(new TvShowAdapter(getContext(), moviesData));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }

                }
            }
        });
        pb.setVisibility(View.GONE);
    }


}

