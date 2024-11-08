package com.horizonhunters.tmdb.Activity;

import static com.horizonhunters.tmdb.Connstant.API_KEY;
import static com.horizonhunters.tmdb.Connstant.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.horizonhunters.tmdb.R;
import com.horizonhunters.tmdb.genres.GenresAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView title;
    private TextView movieOverview;
    private TextView movieRating;
    private TextView movieReleaseDate;
    private ImageView backDrop;
    private List<String> genreList = new ArrayList<>();
    private GenresAdapter genresAdapter;
    private RecyclerView genresRecyclerView;

    private String id; // Default id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Initialize the views
        backDrop = findViewById(R.id.backDrop);
        title = findViewById(R.id.title);
        movieOverview = findViewById(R.id.movieOverview);
        // movieRating = findViewById(R.id.movieRating);  // Uncomment if needed
        // movieReleaseDate = findViewById(R.id.movieReleaseDate);  // Uncomment if needed
        genresRecyclerView = findViewById(R.id.genresRecyclerView);  // RecyclerView for genres

        genresRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Get movie ID from Intent
        Intent intent = getIntent();
        id = intent.getStringExtra("movieId");  // Initialize id here

        Log.d("MovieDetailsActivity", "Received Movie ID: " + id);  // Ensure it's logged

        // If ID is missing, log an error
        if (id == null) {
            Log.e("MovieDetailsActivity", "ID is missing or null");
            Toast.makeText(this, "Error: Movie ID is missing", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Log.d("MovieDetailsActivity", "Received ID: " + id);
        }

        // Fetch movie details
        fetchMovieDetails();
    }

    private void fetchMovieDetails() {
        // Prepare the API URL with the movie ID
        String Url = BASE_URL + "movie/" + id + "?language=en-US&api_key=" + API_KEY;
        Log.d("MovieDetailsActivity", "API URL: " + Url);

        // Make the network request using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract details from the response JSON
                            String getTitle = response.getString("title");
                            String overview = response.getString("overview");
                            String releaseDate = response.getString("release_date");
                            double rating = response.getDouble("vote_average");
                            String backdropPath = response.getString("backdrop_path");
                            String posterPath = response.getString("poster_path");
                            // Extract genres
                            JSONArray genresArray = response.getJSONArray("genres");
                            genreList.clear();  // Clear the existing list
                            for (int i = 0; i < genresArray.length(); i++) {
                                JSONObject genreObject = genresArray.getJSONObject(i);
                                String genreName = genreObject.getString("name");
                                genreList.add(genreName);  // Add genre to the list
                            }
                            // Set the extracted data to the UI elements
                            title.setText(getTitle);
                            movieOverview.setText(overview);
                            // movieRating.setText("Rating: " + rating);  // Display the rating
                            // movieReleaseDate.setText("Release Date: " + releaseDate);  // Display the release date
                            // Set up the genres RecyclerView
                            genresAdapter = new GenresAdapter(genreList);
                            genresRecyclerView.setAdapter(genresAdapter);
                            // Load images using Glide
                            if (backdropPath != null && !backdropPath.isEmpty()) {
                                String backdropUrl = "https://image.tmdb.org/t/p/w1280" + backdropPath;
                                Glide.with(MovieDetailsActivity.this)
                                        .load(backdropUrl)
                                        .into(backDrop);
                            } else {
                                Log.e("MovieDetailsActivity", "Backdrop path is empty");
                            }

                        } catch (JSONException e) {
                            // Handle JSON parsing error
                            Toast.makeText(MovieDetailsActivity.this, "Error parsing movie details", Toast.LENGTH_SHORT).show();
                            Log.e("MovieDetailsActivity", "JSON parsing error", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        // Handle error in the network request
                        Toast.makeText(MovieDetailsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        Log.e("MovieDetailsActivity", "Network error", error);
                    }
                }
        );

        // Add the request to the request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
