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
            baseUrl = getString(R.string.top_rated_base_url);
        }
        else {
            baseUrl = getString(R.string.popular_base_url);
        }
        url = baseUrl + apiKey;
        new FetchTitleTask().execute(url);
    }

    @Override
    public void onClick(String posterUrl, String title, String voteAverage, String overview, String releaseDate, String movieId) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(getString(R.string.poster_url_variable_name), posterUrl);
        intent.putExtra(getString(R.string.title_variable_name), title);
        intent.putExtra(getString(R.string.rating_variable_name), voteAverage);
        intent.putExtra(getString(R.string.overview_variable_name), overview);
        intent.putExtra("movieId", movieId);
        intent.putExtra(getString(R.string.release_date_variable_name), releaseDate);
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
                baseUrl = getString(R.string.popular_base_url);
                url = baseUrl + apiKey;
                new FetchTitleTask().execute(url);
                return true;
            case R.id.top_rated:
                popular = false;
                baseUrl = getString(R.string.top_rated_base_url);
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
            String movieId;
            ArrayList<String> movieIds = new ArrayList<String>();
            try {
                jsonObject = new JSONObject(string);
                jsonArray = jsonObject.getJSONArray(getString(R.string.results));
                for (int i=0; i<jsonArray.length(); i++){
                    jsonObject2 = jsonArray.getJSONObject(i);
                    posterUrl = getString(R.string.poster_base_url) + jsonObject2.getString(getString(R.string.poster_path));
                    title = jsonObject2.getString(getString(R.string.original_title));
                    voteAverage = jsonObject2.get(getString(R.string.vote_average)) + getString(R.string.forward_slash_ten);
                    overview = jsonObject2.getString(getString(R.string.overview));
                    releaseDate = jsonObject2.getString(getString(R.string.release_date));
                    movieId = jsonObject2.get("id").toString();
                    posterUrls.add(posterUrl);
                    titles.add(title);
                    voteAverages.add(voteAverage);
                    overviews.add(overview);
                    releaseDates.add(releaseDate);
                    movieIds.add(movieId);
                }
                recyclerViewAdapter = new RecyclerViewAdapter(posterUrls, titles, voteAverages, overviews, releaseDates, movieIds, clickHandler);
                recyclerView.setAdapter(recyclerViewAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
