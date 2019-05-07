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
            posterUrl= extras.getString("posterUrl");
            title= extras.getString("title");
            rating= extras.getString("rating");
            overview= extras.getString("overview");
            releaseDate= extras.getString("releaseDate");
        }
        titleTextView.setText(title);
        ratingTextView.setText(rating);
        overviewTextView.setText(overview);
        releaseDateTextView.setText(releaseDate);
        Picasso.get().load(posterUrl).into(imageView);
    }
}
