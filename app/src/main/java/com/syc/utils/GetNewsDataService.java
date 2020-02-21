package com.syc.utils;

import com.syc.models.MostPopularNYT;
import com.syc.models.SearchNYT;
import com.syc.models.TopStoriesNYT;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Deyine Jiddou (deyine.jiddou@gmail.com)
 * Created at 2019-10-25
 */
public interface GetNewsDataService {

    //TODO: TopStories home.json pour tout sinon arts.json ou science.json ou us.json world.json etc
    /*
    TopStories  : https://api.nytimes.com/svc/topstories/v2/science.json?api-key=yourkey
                : parameter = section =>    arts, business, politics, sports, travel, technology
                                            automobiles, books,  fashion, food, health, home, insider, magazine, movies, national, tmagazine,
                                            nyregion, obituaries, opinion,  realestate, science, sundayreview, theater, upshot, world
    */
    @GET("topstories/v2/{section}.json" )
    Call<TopStoriesNYT> getTopStoriesNew(@Path("section") String section, @Query("api-key") String userkey );


    /*
    MostPopular : 3 types (emailed/viewed/shared), periode 1,7,30j
    https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=yourkey
    https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=yourkey

    Cas shared: shared type = email, facebook, twitter, periode = 1,7,30j

    https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=yourkey
    */
    @GET("mostpopular/v2/viewed/7.json")
    Call<MostPopularNYT> getPopularNews(@Query("api-key") String userkey);
    //TODO: soit 1 ou 7 jours
    //TODO: soit (emailed/viewed/shared)
    //Call<MostPopularNYT> getPopularNews(@Query("type") String type, @Query("periode") String periode, @Query("api_key") String userkey);


    /*
    Search : 3 type de paramêtres :
            - dates deb / fin
            - mots
            - catégories ( Arts / Politics / Business / Sports / Entrepreneurs / Travel

        https://api.nytimes.com/svc/search/v2/articlesearch.json?q=field-name-1:("soviet") AND field-name-2:("wedding")&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG
        https://api.nytimes.com/svc/search/v2/articlesearch.json?q=field-name:("decentralization" "businesses" "inevitably")&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG



        https://api.nytimes.com/svc/search/v2/articlesearch.json?q=election&api-key=yourkey
        /articlesearch.json?q={query}&fq={filter}
        field-name:("value1" "value2" ... "value n")
        field-name-1:("value1") AND field-name-2:("value2" "value3")
        fq=source:("The New York Times")
        fq=news_desk:("Sports" "Foreign")
        fq=news_desk:("Sports") AND glocations:("NEW YORK CITY")
        q=obama&facet_fields=source&facet=true&begin_date=20120101&end_date=20121231
        https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=romney&facet_field=day_of_week&facet=true&begin_date=20120101&end_date=20120101&api-key=your-api-key

        https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20191101&end_date=20191225&facet=false&fl=web_url,lead_paragraph,pub_date,section_name,subsection_name&fq=Sports,Arts&q=tennis,federer&sort=newest&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG



    https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=yourkey
    */
    @GET("search/v2/articlesearch.json")
    Call<SearchNYT> getSearchNews(@Query("api-key") String userkey );

    //, @Query("begin_date") String beginDate

    //Call<MostPopularNYT> getPopularNews(@Query("type") String type, @Query("periode") String periode, @Query("api_key") String userkey);
    //https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20200101&end_date=20200103&facet=false&fl=web_url,lead_paragraph,pub_date,section_name,subsection_name,multimedia&fq=Sports,Arts&q=federer&sort=newest&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG

}
