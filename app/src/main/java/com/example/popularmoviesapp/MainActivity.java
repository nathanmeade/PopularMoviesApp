package com.example.popularmoviesapp;

import android.arch.persistence.room.Room;
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
import java.util.List;
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
    public static MyAppDatabase myAppDatabase;
    private String movieName;
    private String movieId;
    private String posterUrl;
    private String voteAverage;
    private String overview;
    private String releaseDate;
    private Favorite favorite;

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
        //Database code:
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "favoritedb").allowMainThreadQueries().build();
        posterUrl = "http://image.tmdb.org/t/p/original/or06FN3Dka5tukK1e9sl16pB3iy.jpg";
        movieName = "Avengers: Endgame";
        voteAverage = "8.5/10";
        overview = "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.";
        releaseDate = "2019-04-24";
        movieId = "299534";
        favorite = new Favorite();
        favorite.setMovieId(movieId);
        favorite.setMovieName(movieName);
        favorite.setOverview(overview);
        favorite.setPosterUrl(posterUrl);
        favorite.setReleaseDate(releaseDate);
        favorite.setVoteAverage(voteAverage);
        //MainActivity.myAppDatabase.myDao().addFavorite(favorite);
        ///////////////
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
            case R.id.favorites:
                new FetchTitleTask().execute(getString(R.string.favorites));
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
        Boolean isFavorites;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            isFavorites = false;
            if (strings[0] == getString(R.string.favorites)){
                isFavorites = true;
                return getString(R.string.favorites);
            }
            else {
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

        }

        @Override
        protected void onPostExecute(String string) {
            if (isFavorites){
                //List<String> posterUrlsList = new List<String>();
                //posterUrlsList.add("http://image.tmdb.org/t/p/original/or06FN3Dka5tukK1e9sl16pB3iy.jpg");
                //ArrayList<String> posterUrls = new ArrayList<String>(posterUrlsList);
                ArrayList<String> posterUrls = new ArrayList<String>();
                //posterUrls.add("http://image.tmdb.org/t/p/original/or06FN3Dka5tukK1e9sl16pB3iy.jpg");
                ArrayList<String> titles = new ArrayList<String>();
                //titles.add("Avengers: Endgame");
                ArrayList<String> voteAverages = new ArrayList<String>();
                //voteAverages.add("8.5/10");
                ArrayList<String> overviews = new ArrayList<String>();
                //overviews.add("After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.");
                ArrayList<String> releaseDates = new ArrayList<String>();
                //releaseDates.add("2019-04-24");
                ArrayList<String> movieIds = new ArrayList<String>();
                //movieIds.add("299534");
                List<Favorite> favoritesList = MainActivity.myAppDatabase.myDao().getFavorites();
                //List<Favorite> favoritesList = MainActivity.myAppDatabase.myDao().getFavorite(movieId);
                for (Favorite fvt : favoritesList){
                    posterUrls.add(fvt.getPosterUrl());
                    titles.add(fvt.getMovieName());
                    voteAverages.add(fvt.getVoteAverage());
                    overviews.add(fvt.getOverview());
                    releaseDates.add(fvt.getReleaseDate());
                    movieIds.add(fvt.getMovieId());
                }
                recyclerViewAdapter = new RecyclerViewAdapter(posterUrls, titles, voteAverages, overviews, releaseDates, movieIds, clickHandler);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
            else {
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
}
