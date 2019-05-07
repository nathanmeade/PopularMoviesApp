package com.example.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    //private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView overviewTextView;
    private TextView releaseDateTextView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //textView = findViewById(R.id.text);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);
        overviewTextView = findViewById(R.id.overview);
        releaseDateTextView = findViewById(R.id.release_date);
        imageView = findViewById(R.id.image);
        String newString;
        String newString2;
        String newString3;
        String overview;
        String releaseDate;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
                newString2= null;
                newString3= null;
                overview= null;
                releaseDate = null;
            } else {
                newString= extras.getString("string");
                newString2= extras.getString("string2");
                newString3= extras.getString("string3");
                overview= extras.getString("overview");
                releaseDate= extras.getString("releaseDate");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("string");
            newString2= (String) savedInstanceState.getSerializable("string2");
            newString3= (String) savedInstanceState.getSerializable("string3");
            overview = (String) savedInstanceState.getSerializable("overview");
            releaseDate = (String) savedInstanceState.getSerializable("releaseDate");
        }
        //textView.setText(newString);
        textView2.setText(newString2);
        textView3.setText(newString3);
        overviewTextView.setText(overview);
        releaseDateTextView.setText(releaseDate);
        Picasso.get().load(newString).into(imageView);
    }
}
