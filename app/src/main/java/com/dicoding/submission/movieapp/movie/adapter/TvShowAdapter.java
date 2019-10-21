package com.dicoding.submission.movieapp.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.submission.movieapp.movie.R;
import com.dicoding.submission.movieapp.movie.activity.DetailTvActivity;
import com.dicoding.submission.movieapp.movie.model.Movie;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {
    private Context context;
    private List<Movie> listMovie;

    public TvShowAdapter(Context context, List<Movie> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
    }



    @NonNull
    @Override
    public TvShowAdapter.TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card, viewGroup, false);
        return new TvShowAdapter.TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.TvShowViewHolder tvShowViewHolder, int i) {
        Movie p = listMovie.get(i);
        String poster = "https://image.tmdb.org/t/p/w185" + listMovie.get(i).getPosterPath();
        Glide.with(context)
                .load(poster)
                .apply(new RequestOptions().override(350, 550))
                .into(tvShowViewHolder.imgPhoto);

        tvShowViewHolder.tvTitle.setText(p.getOriginalName());
        tvShowViewHolder.tvOverview.setText(p.getOverview());
        tvShowViewHolder.tvRating.setText(p.getVoteAverage());

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvTitle, tvOverview, tvRating;
        TvShowViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.poster);
            tvTitle = itemView.findViewById(R.id.title);
            tvOverview = itemView.findViewById(R.id.overview);
            tvRating = itemView.findViewById(R.id.rating);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){

                        Movie movie = listMovie.get(pos);
                        Intent intent = new Intent(context, DetailTvActivity.class);

                        intent.putExtra(DetailTvActivity.DETAIL_TV, movie);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "" + movie.getOriginalName(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }


}
