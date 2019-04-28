package com.example.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TextView textView;
    private String apiKey;
    private String jsonResponse;
    private String baseUrl;
    private String url;
    private ImageView imageView;
    private String urlFromTextView;
    private boolean popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);


        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image_view);
        apiKey = BuildConfig.ApiKey;
        //textView.setText(apiKey);
        //popular = false;
        if (!popular){
            baseUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
        }
        else {
            baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key=";
        }
        url = baseUrl + apiKey;
        new FetchTitleTask().execute(url);
        //textView.setText(url);
        //urlFromTextView = textView.getText().toString();

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
                //Uri uri = Uri.parse("https://hoopla-ws-test.hoopladigital.com/kinds/5/titles/featured");
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
            String title;
            ArrayList<String> titles = new ArrayList<String>();
            try {
                jsonObject = new JSONObject(string);
                jsonArray = jsonObject.getJSONArray("results");
                for (int i=0; i<jsonArray.length(); i++){
                    jsonObject2 = jsonArray.getJSONObject(i);
                    title = "http://image.tmdb.org/t/p/original//" + jsonObject2.getString("poster_path");
                    titles.add(title);
                }

/*                jsonObject2 = jsonArray.getJSONObject(0);
                title = "http://image.tmdb.org/t/p/original//" + jsonObject2.getString("poster_path");*/

                recyclerViewAdapter = new RecyclerViewAdapter(titles);
                recyclerView.setAdapter(recyclerViewAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
