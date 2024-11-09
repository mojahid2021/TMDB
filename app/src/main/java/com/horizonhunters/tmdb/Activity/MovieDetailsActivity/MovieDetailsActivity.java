package com.horizonhunters.tmdb.Activity.MovieDetailsActivity;

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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.horizonhunters.tmdb.Activity.MovieDetailsActivity.Credits.Credit;
import com.horizonhunters.tmdb.Activity.MovieDetailsActivity.Credits.CreditAdapter;
import com.horizonhunters.tmdb.Activity.MovieDetailsActivity.SimilarMovies.Movie;
import com.horizonhunters.tmdb.Activity.MovieDetailsActivity.SimilarMovies.MovieAdapter;
import com.horizonhunters.tmdb.R;
import com.horizonhunters.tmdb.genres.GenresAdapter;
import com.horizonhunters.tmdb.home.Movies.Content3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView title;
    private TextView movieOverview;
    private ImageView backDrop;
    private List<String> genreList = new ArrayList<>();
    private List<Movie> movieList = new ArrayList<>();
    private List<Credit> creditList = new ArrayList<>();
    private GenresAdapter genresAdapter;
    private MovieAdapter movieAdapter;
    private CreditAdapter creditAdapter;
    private RecyclerView genresRecyclerView, recyclerView1, recyclerView3;

    private String id; // Default id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Initialize views
        backDrop = findViewById(R.id.backDrop);
        title = findViewById(R.id.title);
        movieOverview = findViewById(R.id.movieOverview);
        recyclerView1 = findViewById(R.id.recyclerView1);
        genresRecyclerView = findViewById(R.id.genresRecyclerView);
        recyclerView3 = findViewById(R.id.recyclerView3);

        // Initialize adapters
        genresAdapter = new GenresAdapter(genreList);
        genresRecyclerView.setAdapter(genresAdapter);
        movieAdapter = new MovieAdapter(movieList, this);
        recyclerView1.setAdapter(movieAdapter);
        creditAdapter = new CreditAdapter(creditList, this);
        recyclerView3.setAdapter(creditAdapter);

        // Set layout managers
        genresRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Get movie ID from Intent
        Intent intent = getIntent();
        id = intent.getStringExtra("movieId");

        Log.d("MovieDetailsActivity", "Received Movie ID: " + id);

        if (id == null) {
            Log.e("MovieDetailsActivity", "ID is missing or null");
            Toast.makeText(this, "Error: Movie ID is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        fetchMovieDetails();
        fetchSimilarMovies();
        fetchCredit();
    }

    private void fetchCredit() {
        String URL = BASE_URL + "movie/" + id + "/credits?language=en-US&api_key=" + API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                creditList.clear();
                JSONArray castArray = response.getJSONArray("cast");

                if (castArray.length() == 0) {
                    Toast.makeText(this, "No content found", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < castArray.length(); i++) {
                    JSONObject contentObject = castArray.getJSONObject(i);

                    Credit credit = new Credit(
                            contentObject.optString("name", ""),
                            contentObject.optString("known_for_department", ""),
                            contentObject.optString("profile_path", ""),
                            contentObject.optString("release_date", ""),
                            contentObject.optString("id", ""),
                            contentObject.optString("character", ""),
                            contentObject.optString("credit_id", ""),
                            contentObject.optString("cast_id", ""),
                            contentObject.optString("original_name", ""),
                            contentObject.optBoolean("adult", false)
                    );

                    creditList.add(credit);
                }

                creditAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error parsing content data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("MovieDetailsActivity", "Error: " + error.getMessage());
            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }


    private void fetchSimilarMovies() {
        String URL = BASE_URL + "movie/" + id + "/similar?language=en-US&api_key=" + API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                movieList.clear();
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(this, "No content found", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < results.length(); i++) {
                    JSONObject contentObject = results.getJSONObject(i);

                    Movie movie = new Movie(
                            contentObject.optString("title", ""),
                            contentObject.optString("overview", ""),
                            contentObject.optString("poster_path", ""),
                            contentObject.optString("release_date", ""),
                            contentObject.optDouble("vote_average", 0.0),
                            contentObject.optString("id"),
                            contentObject.optString("backdrop_path", ""),
                            contentObject.optString("original_language", ""),
                            contentObject.optString("original_title", ""),
                            contentObject.optString("media_type", ""),
                            contentObject.optBoolean("adult", false)
                    );

                    movieList.add(movie);
                }

                movieAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error parsing content data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("MovieDetailsActivity", "Error: " + error.getMessage());
            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchMovieDetails() {
        String Url = BASE_URL + "movie/" + id + "?language=en-US&api_key=" + API_KEY;
        Log.d("MovieDetailsActivity", "API URL: " + Url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Url,
                null,
                response -> {
                    try {
                        String getTitle = response.getString("title");
                        String overview = response.getString("overview");
                        String releaseDate = response.getString("release_date");
                        double rating = response.getDouble("vote_average");
                        String backdropPath = response.getString("backdrop_path");

                        JSONArray genresArray = response.getJSONArray("genres");
                        genreList.clear();
                        for (int i = 0; i < genresArray.length(); i++) {
                            JSONObject genreObject = genresArray.getJSONObject(i);
                            genreList.add(genreObject.getString("name"));
                        }

                        title.setText(getTitle);
                        movieOverview.setText(overview);
                        genresAdapter.notifyDataSetChanged();

                        if (backdropPath != null && !backdropPath.isEmpty()) {
                            String backdropUrl = "https://image.tmdb.org/t/p/w1280" + backdropPath;
                            Glide.with(MovieDetailsActivity.this)
                                    .load(backdropUrl)
                                    .into(backDrop);
                        } else {
                            Log.e("MovieDetailsActivity", "Backdrop path is empty");
                        }

                    } catch (JSONException e) {
                        Toast.makeText(MovieDetailsActivity.this, "Error parsing movie details", Toast.LENGTH_SHORT).show();
                        Log.e("MovieDetailsActivity", "JSON parsing error", e);
                    }
                },
                error -> {
                    Toast.makeText(MovieDetailsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    Log.e("MovieDetailsActivity", "Network error", error);
                }
        );

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
