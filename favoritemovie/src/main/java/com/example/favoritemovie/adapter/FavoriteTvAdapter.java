package com.example.favoritemovie.adapter;

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
import com.example.favoritemovie.R;
import com.example.favoritemovie.activity.DetailTvActivity;
import com.example.favoritemovie.model.Movie;

import java.util.ArrayList;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.FavoriteTvViewHolder> {
    private Context context;
    private final ArrayList<Movie> listMovies = new ArrayList<>();


    public FavoriteTvAdapter(Context context) {
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
    public FavoriteTvAdapter.FavoriteTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new FavoriteTvAdapter.FavoriteTvViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvAdapter.FavoriteTvViewHolder holder, int position) {
        holder.tvTitle.setText(getListMovies().get(position).getOriginalName());
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


    public class FavoriteTvViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvTitle, tvOverview, tvRating;
        public FavoriteTvViewHolder(@NonNull View itemView) {
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
