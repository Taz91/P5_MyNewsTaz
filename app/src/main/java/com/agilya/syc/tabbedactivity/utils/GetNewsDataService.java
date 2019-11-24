package com.agilya.syc.tabbedactivity.utils;

import com.agilya.syc.tabbedactivity.models.NewResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Deyine Jiddou (deyine.jiddou@gmail.com)
 * Created at 2019-10-25
 */
public interface GetNewsDataService {
    /*
    TopStories : https://api.nytimes.com/svc/topstories/v2/science.json?api-key=yourkey
            2 param => la section (ci-dessous) , API_KEY
    arts, automobiles, books, business, fashion, food, health, home, insider, magazine, movies, national, nyregion, obituaries, opinion,
    politics, realestate, science, sports, sundayreview, technology, theater, tmagazine, travel, upshot, world
    */
    @GET("/svc/topstories/v2/{section}.json" )
    Call<NewResult> getTopStoriesNew(@Path("section") String section, @Query("api-key") String userkey );


    /*
    MostPopular : 3 types (emailed/viewed/shared), periode 1,7,30j
    https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=yourkey
    https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=yourkey

    Cas shared: shared type = email, facebook, twitter, periode = 1,7,30j

    https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=yourkey
    */

    @GET("/svc/news/mostpopular")
    Call<NewResult> getPopularNews(@Query("type") String type, @Query("periode") String periode, @Query("api_key") String userkey);
}
