package com.example.popularmoviesapp.Database;

import com.google.gson.annotations.SerializedName;

public class Movie {
    private int id;

    @SerializedName("original_title")
    private String name;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private Float voteAverage;

    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
