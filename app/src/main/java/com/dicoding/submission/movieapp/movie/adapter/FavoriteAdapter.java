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
import com.dicoding.submission.movieapp.movie.activity.DetailActivity;
import com.dicoding.submission.movieapp.movie.model.Movie;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private Context context;
    private final ArrayList<Movie> listMovies = new ArrayList<>();


    public FavoriteAdapter(Context context) {
        this.context = context;

    }


    public ArrayList<Movie> getListMovies() {
        return listMovies;
    }

    public void setListMovies(ArrayList<Movie> listMovies) {
        this.listMovies.clear();
        this.listMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new FavoriteViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.tvTitle.setText(getListMovies().get(position).getTitle());
        holder.tvOverview.setText(getListMovies().get(position).getOverview());
        holder.tvRating.setText(getListMovies().get(position).getVoteAverage());
        String poster = "https://image.tmdb.org/t/p/w185" + getListMovies().get(position).getPosterPath();
        Glide.with(context)
                .load(poster)
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return listMovies.size();

    }



    public class FavoriteViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvTitle, tvOverview, tvRating;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.poster);
            tvTitle = itemView.findViewById(R.id.title);
            tvOverview = itemView.findViewById(R.id.overview);
            tvRating = itemView.findViewById(R.id.rating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        Movie movie = listMovies.get(pos);
                        Intent intent = new Intent(context, DetailActivity.class);

                        intent.putExtra(DetailActivity.DETAIL_MOVIE, movie);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "" + movie.getTitle(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}
