package com.example.favoritemovie.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.favoritemovie.R;
import com.example.favoritemovie.database.DatabaseContract;
import com.example.favoritemovie.model.Movie;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import static com.example.favoritemovie.database.DatabaseContract.CONTENT_URI_TV;
import static com.example.favoritemovie.database.DatabaseContract.FavoriteEntry.COLUMN_LANGUANGE;
import static com.example.favoritemovie.database.DatabaseContract.FavoriteEntry.COLUMN_MOVIEID;
import static com.example.favoritemovie.database.DatabaseContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS;
import static com.example.favoritemovie.database.DatabaseContract.FavoriteEntry.COLUMN_POSTER_PATH;
import static com.example.favoritemovie.database.DatabaseContract.FavoriteEntry.COLUMN_RELEASE;
import static com.example.favoritemovie.database.DatabaseContract.FavoriteEntry.COLUMN_TITLE;
import static com.example.favoritemovie.database.DatabaseContract.FavoriteEntry.COLUMN_USERRATING;

public class DetailTvActivity extends AppCompatActivity {
    TextView title, overview, rating, language, releaseDate;
    ImageView poster;

    public static final String DETAIL_TV = "detail_tv";
    ProgressDialog pd;

    String posterMovie, titleMovie, overviewMovie, ratingMovie, releaseMovie, languageMovie;
    int movie_id;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        }, 1000);


        title = findViewById(R.id.title);
        overview = findViewById(R.id.plotsynopsis);
        releaseDate = findViewById(R.id.releaseDate);

        rating = findViewById(R.id.userrating);
        language = findViewById(R.id.oriLanguage);
        poster = findViewById(R.id.poster);

        final Movie movie = getIntent().getParcelableExtra(DETAIL_TV);
        titleMovie = movie.getTitle();
        overviewMovie = movie.getOverview();
        posterMovie = movie.getPosterPath();
        movie_id = Integer.parseInt(movie.getId());
        ratingMovie = movie.getVoteAverage();
        releaseMovie = movie.getReleaseDate();
        languageMovie = movie.getOriginalLanguage();

        String posterMv = "https://image.tmdb.org/t/p/w185" + posterMovie;


        Glide.with(this)
                .load(posterMv)
                .apply(RequestOptions.circleCropTransform())
                .into(poster);

        title.setText(titleMovie);
        overview.setText(overviewMovie);
        releaseDate.setText(releaseMovie);
        rating.setText(ratingMovie);
        language.setText(languageMovie);

        // Favorite Button

        MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.favorite_button);


        if (Exists(titleMovie)) {
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                saveFavorite(movie);
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                removeFromFavorite(movie_id);
                                // dbHelper.deleteFavoriteMovie(movie_id);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });


        } else {
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite) {
                                saveFavorite(movie);
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                int movie_id = getIntent().getExtras().getInt("id");
                                removeFromFavorite(movie_id);
                                //dbHelper.deleteFavoriteMovie(movie_id);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });


        }


    }

    public boolean Exists(String searchItem) {

        String[] projection = {
                DatabaseContract.FavoriteEntry.COLUMN_MOVIEID,
                DatabaseContract.FavoriteEntry.COLUMN_TITLE,
                DatabaseContract.FavoriteEntry.COLUMN_USERRATING,
                DatabaseContract.FavoriteEntry.COLUMN_POSTER_PATH,
                DatabaseContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS,
                DatabaseContract.FavoriteEntry.COLUMN_RELEASE,
                DatabaseContract.FavoriteEntry.COLUMN_LANGUANGE

        };
        String selection = DatabaseContract.FavoriteEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = {searchItem};

        Cursor cursor = getContentResolver().query(CONTENT_URI_TV, projection, selection, selectionArgs, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    private void saveFavorite(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIEID, movie.getId());
        values.put(COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_PLOT_SYNOPSIS, movie.getOverview());
        values.put(COLUMN_RELEASE, movie.getReleaseDate());
        values.put(COLUMN_LANGUANGE, movie.getOriginalLanguage());
        values.put(COLUMN_USERRATING, movie.getVoteAverage());
        getContentResolver().insert(CONTENT_URI_TV, values);
    }

    private void removeFromFavorite(int movie_id) {
        if (getIntent().getData() != null) {
            getContentResolver().delete(getIntent().getData(), null, null);
        } else {
            getContentResolver().delete(Uri.parse(CONTENT_URI_TV+"/"+movie_id), COLUMN_MOVIEID, null);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
