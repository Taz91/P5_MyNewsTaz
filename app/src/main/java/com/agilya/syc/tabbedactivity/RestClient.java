package com.agilya.syc.tabbedactivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Deyine Jiddou (deyine.jiddou@gmail.com)
 * Created at 2019-10-25
 */
public class RestClient {


    public NewsService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demo1144374.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsService.class);

        //perso     : demo1144374.mockable.io
        //deyine    : http://demo2182278.mockable.io/
    }
}
