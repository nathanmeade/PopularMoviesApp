package com.example.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("string");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("string");
        }
        textView.setText(newString);
        //imageView.setImageResource(R.mipmap.ic_launcher);
        Picasso.get().load(newString).into(imageView);
    }
}
