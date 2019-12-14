package com.example.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ReviewActivity extends AppCompatActivity {

    private String url;
    private String apiKey;
    private RecyclerView recyclerView1;
    private ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        recyclerView1 = findViewById(R.id.recycler_view1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setHasFixedSize(true);
        String movieId;
        apiKey = BuildConfig.ApiKey;
        Bundle extras = getIntent().getExtras();
        movieId = extras.getString("movieId");
        url = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key=" + apiKey;
        new FetchReviewTask().execute(url);
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public class FetchReviewTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String newJsonResponseVariable = new String();
            URL url = null;
            try {
                Uri uri = Uri.parse(strings[0]);
                url = new URL(uri.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                newJsonResponseVariable = getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newJsonResponseVariable;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject;
            JSONArray jsonArray;
            JSONObject jsonObject2;
            String reviewer;
            ArrayList<String> reviewers = new ArrayList<String>();
            String review;
            ArrayList<String> reviews = new ArrayList<String>();
            try {
                jsonObject = new JSONObject(s);
                jsonArray = jsonObject.getJSONArray(getString(R.string.results));
                for (int i=0; i<jsonArray.length(); i++){
                    jsonObject2 = jsonArray.getJSONObject(i);
                    reviewer = jsonObject2.getString("author");
                    review = jsonObject2.getString("content");
                    reviewers.add(reviewer);
                    reviews.add(review);
                }
                reviewsRecyclerViewAdapter = new ReviewsRecyclerViewAdapter(reviewers, reviews);
                recyclerView1.setAdapter(reviewsRecyclerViewAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
