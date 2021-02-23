package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Movie {
    String posterPath;



    String backdropPath;
    String title;
    String overview;



    float rating;
    int id; //needed to find the movie trailer through the api
    //empty constructor required by Parceler library
    ArrayList<Integer> genreIds = new ArrayList<>();
    public Movie(){}



    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = (float)jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");
        JSONArray genre_ids = jsonObject.getJSONArray("genre_ids");
        for(int i=0; i<genre_ids.length(); ++i){
            genreIds.add(genre_ids.getInt(i));
        }
    }
    public static List<Movie> parseJSON(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i=0; i<movieJsonArray.length(); ++i){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
    public float getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }
    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }
}
