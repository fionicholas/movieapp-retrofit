package com.dicoding.submission.movieapp.movie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dicoding.submission.movieapp.movie.database.DatabaseContract;
import com.google.gson.annotations.SerializedName;

import static com.dicoding.submission.movieapp.movie.database.DatabaseContract.getColumnString;

public class Movie implements Parcelable {
    @SerializedName("original_name")
    public String originalName;
    @SerializedName("id")
    public String id;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("overview")
    public String overview;
    @SerializedName("original_language")
    public String originalLanguage;
    @SerializedName("title")
    public String title;
    @SerializedName("vote_average")
    public String voteAverage;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("first_air_date")
    public String firstAirDate;

    public Movie(String id, String title, String voteAverage, String poster_path, String overview, String releaseDate, String originalLanguage, String originalName, String firstAirDate) {
            this.id = id;
            this.title = title;
            this.voteAverage = voteAverage;
            this.posterPath = poster_path;
            this.overview = overview;
            this.releaseDate = releaseDate;
            this.originalLanguage = originalLanguage;
            this.originalName = originalName;
            this.firstAirDate = firstAirDate;

    }



    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }



    public Movie() {

    }

    public Movie(Cursor cursor) {
        this.id = getColumnString(cursor, DatabaseContract.FavoriteEntry.COLUMN_MOVIEID);
        this.posterPath = getColumnString(cursor, DatabaseContract.FavoriteEntry.COLUMN_POSTER_PATH);
        this.title = getColumnString(cursor, DatabaseContract.FavoriteEntry.COLUMN_TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS);
        this.releaseDate = getColumnString(cursor, DatabaseContract.FavoriteEntry.COLUMN_RELEASE);
        this.originalLanguage = getColumnString(cursor, DatabaseContract.FavoriteEntry.COLUMN_LANGUANGE);
        this.voteAverage = getColumnString(cursor, DatabaseContract.FavoriteEntry.COLUMN_USERRATING);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalName);
        dest.writeString(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.voteAverage);
        dest.writeString(this.releaseDate);
        dest.writeString(this.firstAirDate);
    }

    protected Movie(Parcel in) {
        this.originalName = in.readString();
        this.id = in.readString();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.voteAverage = in.readString();
        this.releaseDate = in.readString();
        this.firstAirDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

