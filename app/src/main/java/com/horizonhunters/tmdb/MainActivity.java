package com.horizonhunters.tmdb;

import static com.horizonhunters.tmdb.Connstant.API_KEY;
import static com.horizonhunters.tmdb.Connstant.BASE_URL;

import android.content.SharedPreferences;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.horizonhunters.tmdb.discover.DiscoverFragment;
import com.horizonhunters.tmdb.home.HomeFragment;
import com.horizonhunters.tmdb.profile.ProfileFragment;
import com.horizonhunters.tmdb.search.Movie;
import com.horizonhunters.tmdb.search.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private SearchView searchView;
    private PopupWindow popupWindow;
    private List<Movie> movieList; // Sample data for testing
    private MovieAdapter movieAdapter;
    private CustomProgressDialog progressDialog;
    private Handler handler = new Handler();
    private long oneHourInMillis = 60 * 60 * 1000; // 1 hour in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_nav);

        // Set default fragment on launch
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Start the periodic cache check
        handler.postDelayed(runnable, oneHourInMillis);

        progressDialog = new CustomProgressDialog(this);

        // Initialize the movie list and adapter
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList, this);

        // Bottom navigation setup
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    fragment = new HomeFragment();
                } else if (itemId == R.id.nav_discover) {
                    fragment = new DiscoverFragment();
                } else if (itemId == R.id.nav_profile) {
                    fragment = new ProfileFragment();
                } else {
                    return false;
                }
                if (fragment != null) {
                    loadFragment(fragment);
                }
                return true;
            }
        });


        // Toolbar setup
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer layout setup
        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Navigation view setup (side drawer)
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                if (id == R.id.nav_home) {
                    fragment = new HomeFragment();
                } else if (id == R.id.nav_discover) {
                    fragment = new DiscoverFragment();
                } else if (id == R.id.nav_profile) {
                    fragment = new ProfileFragment();
                }
                if (fragment != null) {
                    loadFragment(fragment);
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        // SearchView setup
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Sample data, replace with actual search logic
                fetchSearchResults(query);
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text changes if needed
                return false;
            }
        });

        // Add animations to SearchView
        addSearchViewAnimations();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            checkAndClearCache();
            handler.postDelayed(this, oneHourInMillis); // Repeat every hour
        }
    };

    private void checkAndClearCache() {
        SharedPreferences sharedPreferences = getSharedPreferences("CachePrefs", MODE_PRIVATE);
        long lastCacheClearTime = sharedPreferences.getLong("lastCacheClearTime", 0);
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastCacheClearTime >= oneHourInMillis) {
            clearAppCache();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("lastCacheClearTime", currentTime);
            editor.apply();
        }
    }

    private void clearAppCache() {
        try {
            File cacheDir = getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                deleteDir(cacheDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void addSearchViewAnimations() {
        final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchView.startAnimation(fadeIn);
                } else {
                    searchView.startAnimation(fadeOut);
                }
            }
        });
    }

    private void fetchSearchResults(String searchQuery) {
        progressDialog.show();
        // Show the progress bar while loading data
        String URL = BASE_URL + "search/multi?query=" + searchQuery + "&include_adult=true&language=en-US&api_key=" + API_KEY;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                movieList.clear(); // Clear previous results
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    Toast.makeText(this, "No movies found", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieObject = results.getJSONObject(i);

                    String title = movieObject.optString("title", "Unknown Title");
                    String overview = movieObject.optString("overview", "No Overview Available");
                    String posterPath = movieObject.optString("poster_path", null);
                    String releaseDate = movieObject.optString("release_date", "Unknown Date");
                    double voteAverage = movieObject.optDouble("vote_average", 0.0);
                    String id = movieObject.getString("id");
                    String backdropPath = movieObject.optString("backdrop_path", null);
                    String mediaType = movieObject.optString("media_type", "Unknown");
                    String originalLanguage = movieObject.optString("original_language", "Unknown");
                    String originalTitle = movieObject.optString("original_title", "Unknown Title");
                    boolean adult = movieObject.optBoolean("adult", false);

                    Movie movie = new Movie(title, overview, posterPath, releaseDate, voteAverage, mediaType, id, backdropPath, originalLanguage, originalTitle, adult);
                    movieList.add(movie);
                }

                progressDialog.dismiss();
                // Show the search results in a pop-up window
                showPopupWindow();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error parsing movie data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("MainActivity", "Error: " + error.getMessage());
            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void showPopupWindow() {
        // Inflate the pop-up layout
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_search_results, null);

        // Initialize the pop-up window
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Setup RecyclerView in the pop-up window
        RecyclerView recyclerView = popupView.findViewById(R.id.popup_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

        // Show the pop-up window
        popupWindow.showAtLocation(findViewById(android.R.id.content), android.view.Gravity.CENTER, 0, 0);
    }

}
