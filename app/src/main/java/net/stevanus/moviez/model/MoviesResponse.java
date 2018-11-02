package net.stevanus.moviez.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MoviesResponse {

    @SerializedName("results")
    List<Movie> movieResults;


    public List<Movie> getMovieResults() {
        return movieResults;
    }
}
