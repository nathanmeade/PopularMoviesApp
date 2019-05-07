package com.example.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String apiKey;
    private String jsonResponse;
    private String baseUrl;
    private String url;
    private boolean popular;
    private RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler clickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        clickHandler = this;
        apiKey = BuildConfig.ApiKey;
        if (!popular){
            baseUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
        }
        else {
            baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key=";
        }
        url = baseUrl + apiKey;
        new FetchTitleTask().execute(url);
    }

    @Override
    public void onClick(String posterUrl, String title, String voteAverage, String overview, String releaseDate) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("posterUrl", posterUrl);
        intent.putExtra("title", title);
        intent.putExtra("rating", voteAverage);
        intent.putExtra("overview", overview);
        intent.putExtra("releaseDate", releaseDate);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                popular = true;
                baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key=";
                url = baseUrl + apiKey;
                new FetchTitleTask().execute(url);
                return true;
            case R.id.top_rated:
                popular = false;
                baseUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
                url = baseUrl + apiKey;
                new FetchTitleTask().execute(url);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    public class FetchTitleTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                Uri uri = Uri.parse(strings[0]);
                url = new URL(uri.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                jsonResponse = getResponseFromHttpUrl(url);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String string) {
            JSONObject jsonObject;
            JSONArray jsonArray;
            JSONObject jsonObject2;
            String posterUrl;
            ArrayList<String> posterUrls = new ArrayList<String>();
            String title;
            ArrayList<String> titles = new ArrayList<String>();
            String voteAverage;
            ArrayList<String> voteAverages = new ArrayList<String>();
            String overview;
            ArrayList<String> overviews = new ArrayList<String>();
            String releaseDate;
            ArrayList<String> releaseDates = new ArrayList<String>();
            try {
                jsonObject = new JSONObject(string);
                jsonArray = jsonObject.getJSONArray("results");
                for (int i=0; i<jsonArray.length(); i++){
                    jsonObject2 = jsonArray.getJSONObject(i);
                    posterUrl = "http://image.tmdb.org/t/p/original//" + jsonObject2.getString("poster_path");
                    title = jsonObject2.getString("original_title");
                    voteAverage = jsonObject2.get("vote_average") + "/10";
                    overview = jsonObject2.getString("overview");
                    releaseDate = jsonObject2.getString("release_date");
                    posterUrls.add(posterUrl);
                    titles.add(title);
                    voteAverages.add(voteAverage);
                    overviews.add(overview);
                    releaseDates.add(releaseDate);
                }
                recyclerViewAdapter = new RecyclerViewAdapter(posterUrls, titles, voteAverages, overviews, releaseDates, clickHandler);
                recyclerView.setAdapter(recyclerViewAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
