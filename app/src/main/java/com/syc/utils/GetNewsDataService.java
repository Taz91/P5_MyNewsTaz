package com.syc.utils;
import com.syc.models.BusinessNYT;
import com.syc.models.MostPopularNYT;
import com.syc.models.NotificationLowData;
import com.syc.models.SearchNYT;
import com.syc.models.TopStoriesNYT;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Chazette Sylvain
 * Interface to define all root of consumption API NYT
 *
 */
public interface GetNewsDataService {
    /**
     * TopStories
     * @param section - home / arts / business / entrepreneurs / politics / sports / travel
     * @param userkey
     * @return
     */
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

    //TODO: if time, this fragment can be the result of notification, with all notification between last date notif and open MyNews
    //en prenant les params de la notification avec l'ensemble des datas
    /**
     *
     * @param fq
     * @param userkey
     * @return
     */
    @GET("search/v2/articlesearch.json")
    Call<BusinessNYT> getBusinessNews(@Query("fq") String fq, @Query("api-key") String userkey );


    @GET("search/v2/articlesearch.json")
    Call<SearchNYT> getSearchArticles(@QueryMap Map<String,String> parameters);
    //("begin_date") String begin_date, @Query("end_date") String end_date, @Query("fq") String fq, @Query("q") String q, @Query("api-key") String userkey

    @GET("search/v2/articlesearch.json")
    Call<SearchNYT> getSearchArticles(@Query("begin_date") String begin_date, @Query("end_date") String end_date, @Query("fq") String fq, @Query("q") String q, @Query("api-key") String userkey );
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


    //, @Query("begin_date") String beginDate
    //https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20200101&end_date=20200103&facet=false&fl=web_url,lead_paragraph,pub_date,section_name,subsection_name,multimedia&fq=Sports,Arts&q=federer&sort=newest&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG

    // Notification low data !!!
    @GET("search/v2/articlesearch.json")
    Call<NotificationLowData> getNotifLowData(@Query("begin_date") String begin_date, @Query("fl") String fl, @Query("fq") String fq, @Query("q") String q, @Query("api-key") String userkey );
    //https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20200301&fl=hits&fq=macron&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG
    //https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20200301&end_date=20200315&fl=hits&fq=Arts Business Movies Sports Travel Politcs&q=corona virus france&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG
}
