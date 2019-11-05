package com.agilya.syc.tabbedactivity.network;

import com.agilya.syc.tabbedactivity.model.MoviePageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("ALL")
public interface GetMovieDataService {
    @GET("movie/popular")
    Call<MoviePageResult> getPopularMovies(@Query("page") int page, @Query("api_key") String userkey);

    @GET("movie/top_rated")
    Call<MoviePageResult> getTopRatedMovies(@Query("page") int page, @Query("api_key") String userkey);
}
//    ab5b518ddc23ac7d27ba63bf78ac1909