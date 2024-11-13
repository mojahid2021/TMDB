package com.horizonhunters.tmdb.home.TvSeries;

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

public class TvSeriesActivity extends AppCompatActivity {
    private List<Content4> contentList4;
    private ContentAdapter4 contentAdapter4;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_series);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new CustomProgressDialog(this);

        contentList4 = new ArrayList<>();  // Initialize contentList before passing it to adapter
        contentAdapter4 = new ContentAdapter4(contentList4, this);
        recyclerView.setAdapter(contentAdapter4);

        fetchTvSeries();

    }

    private void fetchTvSeries() {
        progressDialog.show();
        String URL = BASE_URL + "tv/popular?language=en-US&api_key=" + API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                contentList4.clear();  // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(this, "No content found", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < results.length(); i++) {
                    JSONObject contentObject = results.getJSONObject(i);

                    Content4 content4 = new Content4(
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
                            contentObject.optBoolean("adult", false)  // Boolean value for adult
                    );

                    contentList4.add(content4);
                }

                progressDialog.dismiss();

                // Update adapter after data is fetched
                contentAdapter4.notifyDataSetChanged();

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