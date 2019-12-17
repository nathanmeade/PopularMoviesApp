package com.example.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.popularmoviesapp.Database.Favorite;
import com.example.popularmoviesapp.Database.MyAppDatabase;

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
    private String apiKey;
    private String jsonResponse;
    private String baseUrl;
    private String url;
    private boolean popular;
    private RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler clickHandler;
    public static MyAppDatabase myAppDatabase;
    private RequestManager requestManager;

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
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "favoritedb").allowMainThreadQueries().build();
        String posterUrl = "http://image.tmdb.org/t/p/original/or06FN3Dka5tukK1e9sl16pB3iy.jpg";
        String movieName = "Avengers: Endgame";
        String voteAverage = "8.5/10";
        String overview = "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.";
        String releaseDate = "2019-04-24";
        String movieId = "299534";
        Favorite favorite = new Favorite();
        favorite.setMovieId(movieId);
        favorite.setMovieName(movieName);
        favorite.setOverview(overview);
        favorite.setPosterUrl(posterUrl);
        favorite.setReleaseDate(releaseDate);
        favorite.setVoteAverage(voteAverage);
        if (!popular){
            baseUrl = getString(R.string.top_rated_base_url);
        }
        else {
            baseUrl = getString(R.string.popular_base_url);
        }
        url = baseUrl + apiKey;
        if (savedInstanceState != null) {
            ArrayList<String> posterUrls;
            posterUrls = savedInstanceState.getStringArrayList("posterUrls");
            ArrayList<String> titles;
            titles = savedInstanceState.getStringArrayList("titles");
            ArrayList<String> voteAverages;
            voteAverages = savedInstanceState.getStringArrayList("voteAverages");
            ArrayList<String> overviews;
            overviews = savedInstanceState.getStringArrayList("overviews");
            ArrayList<String> releaseDates;
            releaseDates = savedInstanceState.getStringArrayList("releaseDate");
            ArrayList<String> movieIds;
            movieIds = savedInstanceState.getStringArrayList("movieId");
            RecyclerViewAdapter recyclerViewAdapter2 = new RecyclerViewAdapter(Glide.with(this), posterUrls, titles, voteAverages, overviews, releaseDates, movieIds, clickHandler);
            recyclerView.setAdapter(recyclerViewAdapter2);
        }
        else {
            new FetchTitleTask().execute(url);
        }
        requestManager = Glide.with(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        RecyclerViewAdapter recyclerViewAdapter = (RecyclerViewAdapter) recyclerView.getAdapter();
        assert recyclerViewAdapter != null;
        outState.putStringArrayList("posterUrls", recyclerViewAdapter.getPosterUrls());
        outState.putStringArrayList("titles", recyclerViewAdapter.getTitles());
        outState.putStringArrayList("voteAverages", recyclerViewAdapter.getVoteAverages());
        outState.putStringArrayList("overviews", recyclerViewAdapter.getOverviews());
        outState.putStringArrayList("releaseDate", recyclerViewAdapter.getReleaseDates());
        outState.putStringArrayList("movieId", recyclerViewAdapter.getMovieIds());
    }

    public void ObserveMethod(){
        MainViewModel mainViewModel;
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getFavorites().observe(this, favorites -> {
            ArrayList<String> posterUrls = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<String> voteAverages = new ArrayList<>();
            ArrayList<String> overviews = new ArrayList<>();
            ArrayList<String> releaseDates = new ArrayList<>();
            ArrayList<String> movieIds = new ArrayList<>();
            assert favorites != null;
            for (Favorite fvt : favorites) {
                posterUrls.add(fvt.getPosterUrl());
                titles.add(fvt.getMovieName());
                voteAverages.add(fvt.getVoteAverage());
                overviews.add(fvt.getOverview());
                releaseDates.add(fvt.getReleaseDate());
                movieIds.add(fvt.getMovieId());
            }
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(requestManager, posterUrls, titles, voteAverages, overviews, releaseDates, movieIds, clickHandler);
            recyclerView.setAdapter(recyclerViewAdapter);
        });
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
                //new FetchTitleTask().execute(getString(R.string.favorites));
                ObserveMethod();
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
            Log.d("nathanTest", "doinbackground");
            isFavorites = false;

            if (strings[0].equals(getString(R.string.favorites))){
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
                    assert url != null;
                    jsonResponse = getResponseFromHttpUrl(url);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return jsonResponse;
            }

        }

        @Override
        protected void onPostExecute(String string) {
            Log.d("nathanTest", "start of onpostexectue");
            if (!isFavorites){
                JSONObject jsonObject;
                JSONArray jsonArray;
                JSONObject jsonObject2;
                String posterUrl;
                ArrayList<String> posterUrls = new ArrayList<>();
                String title;
                ArrayList<String> titles = new ArrayList<>();
                String voteAverage;
                ArrayList<String> voteAverages = new ArrayList<>();
                String overview;
                ArrayList<String> overviews = new ArrayList<>();
                String releaseDate;
                ArrayList<String> releaseDates = new ArrayList<>();
                String movieId;
                ArrayList<String> movieIds = new ArrayList<>();
                Log.d("nathanTest", "onpostexectue variables initialized");
                try {
                    Log.d("nathanTest", "start of try block");
                    Log.d("nathanTest", "string is empty?: " + string.isEmpty());
                    jsonObject = new JSONObject(string);
                    Log.d("nathanTest", "onpostexectue jsonobject initialized");
                    jsonArray = jsonObject.getJSONArray(getString(R.string.results));
                    Log.d("nathanTest", "before jsonarray.lenght call");
                    int arrayLength = jsonArray.length();
                    for (int i=0; i<arrayLength; i++){
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
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(requestManager, posterUrls, titles, voteAverages, overviews, releaseDates, movieIds, clickHandler);
                    recyclerView.setAdapter(recyclerViewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
