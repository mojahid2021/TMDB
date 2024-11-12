package com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.SeasonDetails;

import static com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.TvSeriesDetailsActivity.SERIESID;
import static com.horizonhunters.tmdb.Connstant.API_KEY;
import static com.horizonhunters.tmdb.Connstant.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.SeasonDetails.Episode.Episode;
import com.horizonhunters.tmdb.Activity.TvSeriesDetailsActivity.SeasonDetails.Episode.EpisodeAdapter;
import com.horizonhunters.tmdb.CustomProgressDialog;
import com.horizonhunters.tmdb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SeasonDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EpisodeAdapter episodeAdapter;
    private List<Episode> episodeList = new ArrayList<>();
    private String seasonId; // Assume seasonId is passed via Intent
    private String tvSeriesId;
    private CustomProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_details);

        Intent intent = getIntent();
        String value = intent.getStringExtra("season_id"); // Retrieve the data
        String value2 = intent.getStringExtra("movieId"); // Retrieve the data
        seasonId = value;
        tvSeriesId = value2;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        episodeAdapter = new EpisodeAdapter(episodeList);
        recyclerView.setAdapter(episodeAdapter);
        progressDialog = new CustomProgressDialog(this);

        parseEpisodeJson();
    }

    private void parseEpisodeJson() {
        progressDialog.show();
        String url = BASE_URL + "tv/"+ SERIESID +"/season/"+seasonId+"?language=en-US&api_key=" + API_KEY; // Modified endpoint for season details
        Log.d("SeasonDetailsActivity", "API URL: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray episodesArray = response.getJSONArray("episodes");

                        for (int i = 0; i < episodesArray.length(); i++) {
                            JSONObject episodeObj = episodesArray.getJSONObject(i);

                            String airDate = episodeObj.optString("air_date");
                            int episodeNumber = episodeObj.optInt("episode_number");
                            String name = episodeObj.optString("name");
                            String overview = episodeObj.optString("overview");
                            String stillPath = episodeObj.optString("still_path", "");
                            double voteAverage = episodeObj.optDouble("vote_average", 0.0);
                            int runtime = episodeObj.optInt("runtime", 0);

                            Episode episode = new Episode(airDate, episodeNumber, name, overview, stillPath, voteAverage, runtime);
                            episodeList.add(episode);
                        }
                        episodeAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("SeasonDetailsActivity", "Error fetching season details", error)
        );

        // Add request to Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
