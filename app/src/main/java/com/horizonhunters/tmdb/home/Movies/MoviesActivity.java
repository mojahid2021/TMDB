package com.horizonhunters.tmdb.home.Movies;

import static com.horizonhunters.tmdb.Connstant.API_KEY;
import static com.horizonhunters.tmdb.Connstant.BASE_URL;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.horizonhunters.tmdb.CustomProgressDialog;
import com.horizonhunters.tmdb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {
    private List<Content3> contentList3 = new ArrayList<>();
    private ContentAdapter3 contentAdapter3;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        RecyclerView recyclerView3 = findViewById(R.id.recyclerView3);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView3.setLayoutManager(gridLayoutManager);

        contentList3 = new ArrayList<>();  // Initialize contentList before passing it to adapter
        contentAdapter3 = new ContentAdapter3(contentList3, this);
        recyclerView3.setAdapter(contentAdapter3);

        progressDialog = new CustomProgressDialog(this);
        fetchMovies();

    }

    private void fetchMovies() {
        progressDialog.show();
        String URL = BASE_URL + "movie/popular?language=en-US&api_key="+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                contentList3.clear();  // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(this, "No content found", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < results.length(); i++) {
                    JSONObject contentObject = results.getJSONObject(i);

                    Content3 content3 = new Content3(
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
                            contentObject.optString("genres"),
                            contentObject.optBoolean("adult", false)  // Boolean value for adult
                    );

                    contentList3.add(content3);
                }

                progressDialog.dismiss();

                // Update adapter after data is fetched
                contentAdapter3.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error parsing content data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("HomeFragment", "Error: " + error.getMessage());
            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }

}