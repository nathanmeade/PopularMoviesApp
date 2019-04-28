package com.example.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key=";
        url = baseUrl + apiKey;
        new FetchTitleTask().execute(url);
        //textView.setText(url);
        //urlFromTextView = textView.getText().toString();

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
            try {
                jsonObject = new JSONObject(string);
                jsonArray = jsonObject.getJSONArray("results");
                jsonObject2 = jsonArray.getJSONObject(0);
                //title = jsonObject2.getString("poster_path");
                //title = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
                title = "http://image.tmdb.org/t/p/original//" + jsonObject2.getString("poster_path");
                        //textView.setText(title);
                //Picasso.get().load("http://image.tmdb.org/t/p/original//" + title).into(imageView);
                recyclerViewAdapter = new RecyclerViewAdapter(title);
                recyclerView.setAdapter(recyclerViewAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
