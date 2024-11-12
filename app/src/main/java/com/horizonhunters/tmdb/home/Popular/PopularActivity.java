package com.horizonhunters.tmdb.home.Popular;

import static com.horizonhunters.tmdb.Connstant.API_KEY;
import static com.horizonhunters.tmdb.Connstant.BASE_URL;

import android.app.ProgressDialog;
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

public class PopularActivity extends AppCompatActivity {
    private ContentAdapter2 contentAdapter2;
    private List<Content2> contentList2;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        RecyclerView recyclerView3 = findViewById(R.id.recyclerView3);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView3.setLayoutManager(gridLayoutManager);

        progressDialog = new CustomProgressDialog(this);

        contentList2 = new ArrayList<>();  // Initialize contentList before passing it to adapter
        contentAdapter2 = new ContentAdapter2(contentList2, this);
        recyclerView3.setAdapter(contentAdapter2);


        fetchPopular();

    }

    private void fetchPopular() {
        progressDialog.show();
        String URL = BASE_URL + "trending/all/week?language=en-US&api_key="+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                contentList2.clear();  // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(this, "No content found", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < results.length(); i++) {
                    JSONObject contentObject = results.getJSONObject(i);

                    Content2 content2 = new Content2(
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

                    contentList2.add(content2);
                }
                progressDialog.dismiss();
                // Update adapter after data is fetched
                contentAdapter2.notifyDataSetChanged();

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