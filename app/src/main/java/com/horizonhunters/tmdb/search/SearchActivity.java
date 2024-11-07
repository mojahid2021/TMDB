package com.horizonhunters.tmdb.search;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.horizonhunters.tmdb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList, this);
        recyclerView.setAdapter(movieAdapter);

        fetchMovies();

    }

    private void fetchMovies() {
        String URL = "https://api.themoviedb.org/3/search/movie?query=avatar&api_key=f3ec9ad1521b4eea8727f20fe9ef8ca4";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null,response -> {
            try {
                JSONArray results = response.getJSONArray("results");
                for (int i= 0;i<results.length();i++){
                    JSONObject movieObject = results.getJSONObject(i);
                    Movie movie = new Movie(
                            movieObject.getString("title"),
                            movieObject.getString("overview"),
                            movieObject.getString("poster_path"),
                            movieObject.getString("release_date"),
                            movieObject.getDouble("vote_average"),
                            movieObject.getInt("id"),
                            movieObject.getString("backdrop_path"),
                            movieObject.getString("original_language"),
                            movieObject.getString("original_title"),
                            movieObject.getBoolean("adult")

                    );
                    movieList.add(movie);

                }
                movieAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e("MainActivity", "Error: " + error.getMessage())
        );

        requestQueue.add(jsonObjectRequest);

    }
}