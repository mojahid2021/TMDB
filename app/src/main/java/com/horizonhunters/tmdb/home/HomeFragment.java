package com.horizonhunters.tmdb.home;

import static com.horizonhunters.tmdb.Connstant.API_KEY;
import static com.horizonhunters.tmdb.Connstant.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.horizonhunters.tmdb.CustomProgressDialog;
import com.horizonhunters.tmdb.R;
import com.horizonhunters.tmdb.home.Movies.Content3;
import com.horizonhunters.tmdb.home.Movies.ContentAdapter3;
import com.horizonhunters.tmdb.home.Movies.MoviesActivity;
import com.horizonhunters.tmdb.home.Popular.Content2;
import com.horizonhunters.tmdb.home.Popular.ContentAdapter2;
import com.horizonhunters.tmdb.home.Popular.PopularActivity;
import com.horizonhunters.tmdb.home.Trending.Today.Content;
import com.horizonhunters.tmdb.home.Trending.Today.ContentAdapter;
import com.horizonhunters.tmdb.home.TvSeries.Content4;
import com.horizonhunters.tmdb.home.TvSeries.ContentAdapter4;
import com.horizonhunters.tmdb.home.Upcoming.Content5;
import com.horizonhunters.tmdb.home.Upcoming.ContentAdapter5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5;
    private ContentAdapter contentAdapter;
    private ContentAdapter2 contentAdapter2;
    private ContentAdapter3 contentAdapter3;
    private ContentAdapter4 contentAdapter4;
    private ContentAdapter5 contentAdapter5;
    private List<Content> contentList;
    private List<Content2> contentList2;
    private List<Content3> contentList3;
    private List<Content4> contentList4;
    private List<Content5> contentList5;
    private Handler handler;
    private LinearLayoutManager layoutManager, layoutManager2, layoutManager3, layoutManager4, layoutManager5;
    private CustomProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog = new CustomProgressDialog(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView3 = view.findViewById(R.id.recyclerView3);
        recyclerView4 = view.findViewById(R.id.recyclerView4);
        recyclerView5 = view.findViewById(R.id.recyclerView5);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager5 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView5.setLayoutManager(layoutManager5);
        recyclerView4.setLayoutManager(layoutManager4);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView.setLayoutManager(layoutManager);

        contentList = new ArrayList<>();  // Initialize contentList before passing it to adapter
        contentAdapter = new ContentAdapter(contentList, getActivity());
        recyclerView.setAdapter(contentAdapter);
        contentList2 = new ArrayList<>();  // Initialize contentList before passing it to adapter
        contentAdapter2 = new ContentAdapter2(contentList2, getActivity());
        recyclerView2.setAdapter(contentAdapter2);
        contentList3 = new ArrayList<>();  // Initialize contentList before passing it to adapter
        contentAdapter3 = new ContentAdapter3(contentList3, getActivity());
        recyclerView3.setAdapter(contentAdapter3);
        contentList4 = new ArrayList<>();  // Initialize contentList before passing it to adapter
        contentAdapter4 = new ContentAdapter4(contentList4, getActivity());
        recyclerView4.setAdapter(contentAdapter4);
        contentList5 = new ArrayList<>();  // Initialize contentList before passing it to adapter
        contentAdapter5 = new ContentAdapter5(contentList5, getActivity());
        recyclerView5.setAdapter(contentAdapter5);

        fetchTrendingToday();
        fetchPopular();
        fetchMovies();
        fetchTvSeries();
        fetchUpcoming();

        // Create a handler to trigger smooth scrolling every 3 seconds
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothScrollRecyclerView();
                // Repeat the scrolling action every 3 seconds
                handler.postDelayed(this, 3000); // 3000ms = 3 seconds
            }
        }, 3000); // Start after 2 seconds

        ImageView showMoviesButton = view.findViewById(R.id.showMoviesButton);
        showMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoviesActivity.class);
                startActivity(intent);
            }
        });
        ImageView showPopularButton = view.findViewById(R.id.showPopularButton);
        showPopularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PopularActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchUpcoming() {
        progressDialog.show();
        String URL = BASE_URL + "movie/upcoming?language=en-US&api_key="+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                contentList5.clear();  // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(getActivity(), "No content found", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < results.length(); i++) {
                    JSONObject contentObject = results.getJSONObject(i);

                    Content5 content5 = new Content5(
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

                    contentList5.add(content5);
                }
                progressDialog.dismiss();
                // Update adapter after data is fetched
                contentAdapter5.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error parsing content data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("HomeFragment", "Error: " + error.getMessage());
            Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchTvSeries() {
        progressDialog.show();
        String URL = BASE_URL + "tv/popular?language=en-US&api_key="+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                contentList4.clear();  // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(getActivity(), "No content found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Error parsing content data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("HomeFragment", "Error: " + error.getMessage());
            Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchMovies() {
        progressDialog.show();
        String URL = BASE_URL + "movie/popular?language=en-US&api_key="+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                contentList3.clear();  // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(getActivity(), "No content found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Error parsing content data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("HomeFragment", "Error: " + error.getMessage());
            Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }

    // Fetch popular content
    private void fetchPopular() {
        progressDialog.show();
        String URL = BASE_URL + "trending/all/week?language=en-US&api_key="+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                contentList2.clear();  // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(getActivity(), "No content found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Error parsing content data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("HomeFragment", "Error: " + error.getMessage());
            Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void smoothScrollRecyclerView() {
        int currentPosition = layoutManager.findFirstVisibleItemPosition();
        int totalItems = contentAdapter.getItemCount();

        // Scroll to the next position, loop back to the first item if at the end
        int nextPosition = (currentPosition + 1) % totalItems;
        recyclerView.smoothScrollToPosition(nextPosition);
    }

    private void fetchTrendingToday() {
        progressDialog.show();
        String URL = BASE_URL + "trending/all/day?language=en-US&api_key="+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                contentList.clear();  // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(getActivity(), "No content found", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < results.length(); i++) {
                    JSONObject contentObject = results.getJSONObject(i);

                    Content content = new Content(
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

                    contentList.add(content);
                }
                progressDialog.dismiss();
                // Update adapter after data is fetched
                contentAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error parsing content data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("HomeFragment", "Error: " + error.getMessage());
            Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);  // Stop the scrolling loop when the fragment is paused
        }
    }
}
