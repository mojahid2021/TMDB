package com.horizonhunters.tmdb.search;

import static com.horizonhunters.tmdb.Connstant.API_KEY;
import static com.horizonhunters.tmdb.Connstant.BASE_URL;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private EditText editTextSearch;
    private ImageView imageViewSearch;
    private ProgressBar progressBar;  // Add a progress bar
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList, this);
        recyclerView.setAdapter(movieAdapter);
        editTextSearch = findViewById(R.id.editTextSearch);
        imageViewSearch = findViewById(R.id.imageViewSearch);

        // Set up search button listener with debounce
        imageViewSearch.setOnClickListener(view -> {
            searchQuery = editTextSearch.getText().toString().trim();
            if (!searchQuery.isEmpty()) {
                fetchMovies();
            } else {
                Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMovies() {
        // Show the progress bar while loading data

        String URL = BASE_URL+"search/movie?query=" + searchQuery + "&api_key="+API_KEY;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                movieList.clear();  // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(this, "No movies found", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < results.length(); i++) {
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

                // Hide progress bar after data is fetched
                movieAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error parsing movie data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("SearchActivity", "Error: " + error.getMessage());
            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }
}
