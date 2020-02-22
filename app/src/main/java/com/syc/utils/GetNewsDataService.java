package com.syc.utils;

import com.syc.models.MostPopularNYT;
import com.syc.models.SearchNYT;
import com.syc.models.TopStoriesNYT;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Taz91
 * Created at 2019-10-25
 */
public interface GetNewsDataService {

    @GET("topstories/v2/{section}.json" )
    Call<TopStoriesNYT> getTopStoriesNew(@Path("section") String section, @Query("api-key") String userkey );

    /**
     * MostPopular = 2 types in this case ( emailed/viewed ), and 3 periode (1,7,30j)
     * examples :
     * https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=yourkey
     * https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=yourkey
     * @GET("mostpopular/v2/{type}/{periode}.json")
     * Call<MostPopularNYT> getPopularNews(@Path("type")String type, @Path("periode") String periode, @Query("api-key") String userkey);
     */
    @GET("mostpopular/v2/viewed/7.json")
    Call<MostPopularNYT> getPopularNews(@Query("api-key") String userkey);

    //TODO: MostPopular - possibility if time
    /**
     * MostPopular le type shared: type = email, facebook, twitter, periode = 1,7,30j
     * example :
     * https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=yourkey
     * @GET("mostpopular/v2/shared/{type}/{periode}.json")
     * Call<MostPopularNYT> getPopularNews(@Path("periode") String periode, @Path("typesocial") String typesocial, @Query("api_key") String userkey);
    */



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
    Call<SearchNYT> getSearchNews(@Query("fq") String fq,@Query("api-key") String userkey );

    //, @Query("begin_date") String beginDate
    //https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20200101&end_date=20200103&facet=false&fl=web_url,lead_paragraph,pub_date,section_name,subsection_name,multimedia&fq=Sports,Arts&q=federer&sort=newest&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG

}
