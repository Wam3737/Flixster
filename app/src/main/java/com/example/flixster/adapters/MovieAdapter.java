package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.DetailActivity;
import com.example.flixster.models.Movie;
import com.example.flixster_fix.R;
import com.example.flixster_fix.*;

import org.parceler.Parcels;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }
    //inflate a layout from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);

    }

    //populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    //Get the movie at position
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView synopsis;
        ImageView poster;
        RelativeLayout container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            synopsis = itemView.findViewById(R.id.tvSynopsis);
            poster = itemView.findViewById(R.id.ivposter);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            title.setText(movie.getTitle());
            synopsis.setText(movie.getOverview());
            String imageUrl;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            }else{
                imageUrl = movie.getPosterPath();
            }
            Glide.with(context).load(imageUrl).into(poster);
            //register click listener on whole container
            //navigate to new activity on click
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Intent i = new Intent(context, DetailActivity.class);
                  i.putExtra("title", movie.getTitle());
                  i.putExtra("movie", Parcels.wrap(movie));
                  context.startActivity(i);
                }
            });
        }

    }
}
