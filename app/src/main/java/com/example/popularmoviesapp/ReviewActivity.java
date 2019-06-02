package com.example.popularmoviesapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ReviewActivity extends AppCompatActivity implements TrailersRecyclerViewAdapter.TrailersRecyclerViewAdapterOnClickHandler {

    private String[] jsonResponse;
    private String url;
    private String url2;
    private String[] urlArray;
    private String apiKey;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter;
    private TrailersRecyclerViewAdapter trailersRecyclerViewAdapter;
    private TrailersRecyclerViewAdapter.TrailersRecyclerViewAdapterOnClickHandler clickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        //////////////////
        clickHandler = this;
        recyclerView1 = findViewById(R.id.recycler_view1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setHasFixedSize(true);
        recyclerView2 = findViewById(R.id.recycler_view2);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setHasFixedSize(true);
        /////////////////
        String movieId;
        apiKey = BuildConfig.ApiKey;
        Bundle extras = getIntent().getExtras();
        movieId = extras.getString("movieId");
        url = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key=" + apiKey;
        url2 = "http://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=" + apiKey;
        urlArray = new String[2];
        urlArray[0] = url;
        urlArray[1] = url2;
        new FetchReviewTask().execute(urlArray);
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
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

    @Override
    public void onClick(String movieId) {
        watchYoutubeVideo(this, movieId);
    }

    public class FetchReviewTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... strings) {
            jsonResponse = new String[2];
            URL url = null;
            URL url2 = null;
            try {
                Uri uri = Uri.parse(strings[0]);
                url = new URL(uri.toString());
                Uri uri2 = Uri.parse(strings[1]);
                url2 = new URL(uri2.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                jsonResponse[0] = getResponseFromHttpUrl(url);
                jsonResponse[1] = getResponseFromHttpUrl(url2);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String[] s) {
            JSONObject jsonObject;
            JSONArray jsonArray;
            JSONObject jsonObject2;
            String reviewer;
            ArrayList<String> reviewers = new ArrayList<String>();
            String review;
            ArrayList<String> reviews = new ArrayList<String>();
            //videos:
            JSONObject jsonObjectTrailer;
            JSONArray jsonArrayTrailer;
            JSONObject jsonObjectTrailer2;
            String key;
            ArrayList<String> keys = new ArrayList<String>();
            try {
                jsonObject = new JSONObject(s[0]);
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
                //videos:
                jsonObjectTrailer = new JSONObject(s[1]);
                jsonArrayTrailer = jsonObjectTrailer.getJSONArray(getString(R.string.results));
                for (int i=0; i<jsonArrayTrailer.length(); i++){
                    jsonObjectTrailer2 = jsonArrayTrailer.getJSONObject(i);
                    key = jsonObjectTrailer2.getString("key");
                    keys.add(key);
                }
                trailersRecyclerViewAdapter = new TrailersRecyclerViewAdapter(keys, clickHandler);
                recyclerView2.setAdapter(trailersRecyclerViewAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
