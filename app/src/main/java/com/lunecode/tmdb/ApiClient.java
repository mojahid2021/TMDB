package com.lunecode.tmdb;

import android.media.session.MediaSession;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

public class ApiClient {
    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmM2VjOWFkMTUyMWI0ZWVhODcyN2YyMGZlOWVmOGNhNCIsIm5iZiI6MTY3MDYxNTc0OS44NTMsInN1YiI6IjYzOTM5MmM1ZDdmYmRhMDA4OGJiZmI3YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.vjVp9EsZ96E1vbgiQhJO96lCJ37TbBF_e3ycCubzn8s";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(
                    chain -> {
                        Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token)
                                .addHeader("accept", "application/json").build();

                        return chain.proceed(request);
                    }


            ).build().newBuilder();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(httpClient.build()).build();
        }
        return retrofit;
    }
}
