package com.lunecode.tmdb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView movieTitle, moviewReleaseDate, movieDuration, moviewOverview;
    private ImageView movieBackdrop, moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        movieTitle = findViewById(R.id.mvTitle);
        moviewReleaseDate = findViewById(R.id.releaseDate);
        movieDuration = findViewById(R.id.mvDuration);
        moviewOverview = findViewById(R.id.mvOverview);
        movieBackdrop = findViewById(R.id.backdrop);
        moviePoster = findViewById(R.id.poster);
        String movieId = getIntent().getStringExtra("movieId");
        if (movieId != null){
            fetchMovieDetails(movieId);
        } else {
            try {
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
            } catch (Exception e){
                Log.d("Error", e.getMessage());
            }
        }
    }

    private void fetchMovieDetails(String movieId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.getMovileDetails(movieId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    if (response.isSuccessful() && response.body() != null){
                        try( ResponseBody responseBody = response.body()){
                            String responseString = responseBody.string();
                            Log.d("Response", responseString);
                            JSONObject mvObject = new JSONObject(responseString);
                            String title = mvObject.getString("title");
                            String releaseDate = mvObject.getString("release_date");
                            String duration = mvObject.getString("runtime");
                            String overview = mvObject.getString("overview");
                            String backdrop = mvObject.getString("backdrop_path");
                            String poster = mvObject.getString("poster_path");


                            movieTitle.setText(title);
                            moviewReleaseDate.setText(releaseDate);
                            movieDuration.setText(duration);
                            moviewOverview.setText(overview);
                            String fullBackdrop = "https://image.tmdb.org/t/p/w780" + backdrop;
                            String fullPoster = "https://image.tmdb.org/t/p/w342" + poster;
                            Glide.with(MovieDetailsActivity.this)
                                    .load(fullBackdrop)
                                    .centerCrop()
                                    .into(movieBackdrop);
                            Glide.with(MovieDetailsActivity.this)
                                    .load(fullPoster)
                                    .centerCrop()
                                    .into(moviePoster);



                        }   catch (Exception e){

                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }


}