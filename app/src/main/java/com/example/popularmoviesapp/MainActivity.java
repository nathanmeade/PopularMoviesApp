package com.example.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.popularmoviesapp.Database.JsonResponse;
import com.example.popularmoviesapp.Database.Movie;
import com.example.popularmoviesapp.Database.MyAppDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private String apiKey;
    private String jsonResponse;
    private String baseUrl;
    private String url;
    private boolean popular;
    private MovieAdapter.MovieAdapterOnClickHandler movieAdapterOnClickHandler;
    public static MyAppDatabase myAppDatabase;
    private RequestManager requestManager;
    private Retrofit retrofit;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private Boolean isTopRated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapterOnClickHandler = this;
        apiKey = BuildConfig.ApiKey;
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "favoritedb").allowMainThreadQueries().build();
        if (!popular){
            baseUrl = getString(R.string.top_rated_base_url);
        }
        else {
            baseUrl = getString(R.string.popular_base_url);
        }

        requestManager = Glide.with(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        isTopRated = true;
        retrofitFunction(isTopRated);
    }

    public void retrofitFunction(Boolean isTopRated){
        Call<JsonResponse> call;
        if (isTopRated){
            call = jsonPlaceholderApi.getTopRated(apiKey);
        }
        else {
            call = jsonPlaceholderApi.getPopular(apiKey);
        }

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.isSuccessful()){
                    JsonResponse jsonResponse = response.body();
                    ArrayList<Movie> movies = jsonResponse.getResults();
                    MovieAdapter movieAdapter = new MovieAdapter(movies, movieAdapterOnClickHandler, requestManager, getString(R.string.poster_base_url));
                    recyclerView.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MovieAdapter movieAdapter = (MovieAdapter) recyclerView.getAdapter();
        assert movieAdapter != null;
/*        outState.putParcelableArrayList("movies", movieAdapter.getmMovies());*/
/*        outState.putStringArrayList("posterUrls", movieAdapter.getPosterUrls());
        outState.putStringArrayList("titles", movieAdapter.getTitles());
        outState.putStringArrayList("voteAverages", movieAdapter.getVoteAverages());
        outState.putStringArrayList("overviews", movieAdapter.getOverviews());
        outState.putStringArrayList("releaseDate", movieAdapter.getReleaseDates());
        outState.putStringArrayList("movieId", movieAdapter.getMovieIds());*/
    }

    public void ObserveMethod(){
        MainViewModel mainViewModel;
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getFavorites().observe(this, favorites -> {
            ArrayList<Movie> favoriteArrayList = new ArrayList<Movie>();
            assert favorites != null;
            for (Favorite fvt : favorites) {
                Movie movie = new Movie(fvt.getMovieId(), fvt.getMovieName(), fvt.getPosterUrl(), fvt.getVoteAverage(), fvt.getOverview(), fvt.getReleaseDate());
                favoriteArrayList.add(movie);
            }
            MovieAdapter movieAdapter = new MovieAdapter(favoriteArrayList, movieAdapterOnClickHandler, requestManager, getString(R.string.poster_base_url));
            recyclerView.setAdapter(movieAdapter);
        });
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        intent.putExtras(bundle);
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
                isTopRated = false;
                baseUrl = getString(R.string.popular_base_url);
                url = baseUrl + apiKey;
                retrofitFunction(isTopRated);
                return true;
            case R.id.top_rated:
                popular = false;
                baseUrl = getString(R.string.top_rated_base_url);
                url = baseUrl + apiKey;
                isTopRated = true;
                retrofitFunction(isTopRated);
                return true;
            case R.id.favorites:
                ObserveMethod();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
