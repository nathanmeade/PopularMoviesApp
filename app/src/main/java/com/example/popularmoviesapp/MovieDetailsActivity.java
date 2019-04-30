package com.example.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        textView = findViewById(R.id.text);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);
        imageView = findViewById(R.id.image);
        String newString;
        String newString2;
        String newString3;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
                newString2= null;
                newString3= null;
            } else {
                newString= extras.getString("string");
                newString2= extras.getString("string2");
                newString3= extras.getString("string3");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("string");
            newString2= (String) savedInstanceState.getSerializable("string2");
            newString3= (String) savedInstanceState.getSerializable("string3");
        }
        textView.setText(newString);
        textView2.setText(newString2);
        textView3.setText(newString3);
        //imageView.setImageResource(R.mipmap.ic_launcher);
        Picasso.get().load(newString).into(imageView);
    }
}
