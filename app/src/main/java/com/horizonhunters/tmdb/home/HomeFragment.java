package com.horizonhunters.tmdb.home;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.horizonhunters.tmdb.R;
import com.horizonhunters.tmdb.home.Trending.Today.Content;
import com.horizonhunters.tmdb.home.Trending.Today.ContentAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ContentAdapter contentAdapter;
    private List<Content> contentList;
    private Handler handler;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        contentList = new ArrayList<>();  // Initialize contentList before passing it to adapter
        contentAdapter = new ContentAdapter(contentList, getActivity());
        recyclerView.setAdapter(contentAdapter);

        fetchTrendingToday();

        // Create a handler to trigger smooth scrolling every 2 seconds
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothScrollRecyclerView();
                // Repeat the scrolling action every 2 seconds
                handler.postDelayed(this, 3000); // 2000ms = 2 seconds
            }
        }, 3000); // Start after 2 seconds

        return view;
    }

    private void smoothScrollRecyclerView() {
        int currentPosition = layoutManager.findFirstVisibleItemPosition();
        int totalItems = contentAdapter.getItemCount();

        // Scroll to the next position, loop back to the first item if at the end
        int nextPosition = (currentPosition + 1) % totalItems;
        recyclerView.smoothScrollToPosition(nextPosition);
    }

    private void fetchTrendingToday() {
        String URL = "https://api.themoviedb.org/3/trending/all/day?language=en-US&api_key=f3ec9ad1521b4eea8727f20fe9ef8ca4";
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
                            contentObject.optInt("id", 0),
                            contentObject.optString("backdrop_path", ""),
                            contentObject.optString("original_language", ""),
                            contentObject.optString("original_title", ""),
                            contentObject.optString("media_type", ""),
                            contentObject.optBoolean("adult", false)  // Boolean value for adult
                    );

                    contentList.add(content);
                }

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
