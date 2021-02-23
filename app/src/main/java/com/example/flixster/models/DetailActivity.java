package com.example.flixster.models;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster_fix.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
    private static final String googleAPI = "insert_private_key_here"; // private google api key redacted
   private static final String VID_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
   private static final String GENRE_URL = "https://api.themoviedb.org/3/genre/movie/list?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US";
    YouTubePlayerView playerView;

    TextView tvTitle;
    TextView overview;
    RatingBar ratingBar;
    TextView genres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        overview = findViewById(R.id.tvSynopsis);
        ratingBar = findViewById(R.id.ratingBar3);
        playerView = findViewById(R.id.player);
        // genres = findViewById(R.id.genres);
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        ratingBar.setRating(movie.getRating());
        overview.setText(movie.getOverview());
        AsyncHttpClient client = new AsyncHttpClient();


        client.get(String.format(VID_URL, movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }
                    String ytKey = results.getJSONObject(0).getString("key");
                    initializeYT(ytKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });
    }
    /* AsyncHttpClient client2 = new AsyncHttpClient();
        client2.get(GENRE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("genres");
                    String genreText = "";
                    if(results.length()>0){
                        if(results.length()>1){
                            genreText += "Genre: ";
                        } else{
                            genreText+= "Genres: ";
                        }
                        int j=0;
                        while(j<results.length() && j<3){
                            genreText += results.getJSONObject(movie.getGenreIds().get(j)).getString("name");
                            if(j!=2){
                                genreText += ", ";
                            }
                            ++j;
                        }
                        genres.setText(genreText);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
            System.out.println("WILL YOURE STUPID");
            }
        });
    }*/

    private void initializeYT(String ytKey) {
        playerView.initialize(googleAPI, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(ytKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
