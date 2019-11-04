package com.agilya.syc.tabbedactivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Deyine Jiddou (deyine.jiddou@gmail.com)
 * Created at 2019-10-25
 */
public interface NewsService {

    //@GET("/news_get")
    //@GET("/news_get_deyine")
    @GET("/news_get")
    Call<List<New>> getNews();
}
