package com.lunecode.tmdb;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/3/movie/{movie_id}")
    Call<ResponseBody> getMovileDetails(@Path("movie_id") Integer movie_id);
}

