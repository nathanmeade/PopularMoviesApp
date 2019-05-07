package com.example.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView ratingTextView;
    private TextView overviewTextView;
    private TextView releaseDateTextView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        titleTextView = findViewById(R.id.title);
        ratingTextView = findViewById(R.id.rating);
        overviewTextView = findViewById(R.id.overview);
        releaseDateTextView = findViewById(R.id.release_date);
        imageView = findViewById(R.id.image);
        String posterUrl;
        String title;
        String rating;
        String overview;
        String releaseDate;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            posterUrl= null;
            title= null;
            rating= null;
            overview= null;
            releaseDate = null;
        } else {
            posterUrl= extras.getString(getString(R.string.poster_url_variable_name));
            title= extras.getString(getString(R.string.title_variable_name));
            rating= extras.getString(getString(R.string.rating_variable_name));
            overview= extras.getString(getString(R.string.overview_variable_name));
            releaseDate= extras.getString(getString(R.string.release_date_variable_name));
        }
        titleTextView.setText(title);
        ratingTextView.setText(rating);
        overviewTextView.setText(overview);
        releaseDateTextView.setText(releaseDate);
        Picasso.get().load(posterUrl).into(imageView);
    }
}
