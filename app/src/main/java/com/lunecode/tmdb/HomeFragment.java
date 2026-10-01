package com.lunecode.tmdb;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private List<Movie> movies = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView, recyclerViewTwo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerViewTwo = view.findViewById(R.id.recyclerViewTwo);
        movieAdapter = new MovieAdapter(movies, getContext());
        recyclerView.setAdapter(movieAdapter);
        recyclerViewTwo.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTwo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fetchUpcomingMovies();
        fetchMovies();
        return view;
    }

    private void fetchUpcomingMovies() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.getUpcomingMovies();

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null){
                    try( ResponseBody responseBody = response.body()){
                        String responseString = responseBody.string();
                        JSONObject mvObject = new JSONObject(responseString);
                        JSONArray mvArray = mvObject.getJSONArray("results");
                        Log.d("Response", responseString);
                        for (int i = 0; i < mvArray.length(); i++) {
                            JSONObject movieObject = mvArray.getJSONObject(i);
                            String id = movieObject.optString("id");
                            String title = movieObject.optString("title");
                            String posterPath = movieObject.optString("poster_path");
                            String releaseDate = movieObject.optString("release_date");

                            String poster = posterPath.isEmpty() ? "": "https://image.tmdb.org/t/p/w342" + posterPath;

                            Movie movie = new Movie(id, title, poster, releaseDate);
                            movies.add(movie);

                            Log.d("Movies" , movies.toString());
                        }
                        movieAdapter.notifyDataSetChanged();

                    } catch (Exception e){
                        Log.d("Error", e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }

    private void fetchMovies() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.getMovies();

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null){
                    try( ResponseBody responseBody = response.body()){
                        String responseString = responseBody.string();
                        JSONObject mvObject = new JSONObject(responseString);
                        JSONArray mvArray = mvObject.getJSONArray("results");
                        Log.d("Response", responseString);
                        for (int i = 0; i < mvArray.length(); i++) {
                            JSONObject movieObject = mvArray.getJSONObject(i);
                            String id = movieObject.optString("id");
                            String title = movieObject.optString("title");
                            String posterPath = movieObject.optString("poster_path");
                            String releaseDate = movieObject.optString("release_date");

                            String poster = posterPath.isEmpty() ? "": "https://image.tmdb.org/t/p/w342" + posterPath;

                            Movie movie = new Movie(id, title, poster, releaseDate);
                            movies.add(movie);

                            Log.d("Movies" , movies.toString());
                        }
                        movieAdapter.notifyDataSetChanged();

                    } catch (Exception e){
                        Log.d("Error", e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}