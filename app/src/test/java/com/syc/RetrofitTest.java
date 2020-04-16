package com.syc;
import android.content.Context;
import com.syc.models.BusinessNYT;
import com.syc.models.MostPopularNYT;
import com.syc.models.NotificationLowData;
import com.syc.models.TopStoriesNYT;
import com.syc.utils.GetNewsDataService;
import com.syc.utils.RetrofitInstance;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Retrofit;
import static com.syc.utils.Utils.getApiKey;
import static com.syc.utils.Utils.setApiKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RetrofitTest {
    private final Context context = Mockito.mock(Context.class);
    private GetNewsDataService mNYTService;
    Retrofit mRetrofit;

    @Before
    public void setUp() {
        mRetrofit = RetrofitInstance.getRetrofitInstance();
        mNYTService = mRetrofit.create(GetNewsDataService.class);
    }

    /**
     * Test the Retrofit base call on the URL
     */
    @Test
    public void setRetrofitTest() {
        assertEquals("https://api.nytimes.com/svc/", mRetrofit.baseUrl().toString());
        assertNotNull(mRetrofit);
        assertTrue(mRetrofit.baseUrl().isHttps());
    }

    @Test
    public void getTopStoriesTest() {
        setApiKey("J0iJw0a8fdshubHztJsOJxEEg6hPstOG");;
        GetNewsDataService newsDataService = mRetrofit.create(GetNewsDataService.class);
        Call<TopStoriesNYT> call = newsDataService.getTopStoriesNew( "politics", getApiKey() );
        TopStoriesNYT result = null;
        try {
            result = call.execute().body();  //.body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestCase.assertEquals(result.getStatus(),"OK");
        TestCase.assertEquals(result.getSection(),"Politics");
    }

    @Test
    public void getMostPopularTest() {
        setApiKey("J0iJw0a8fdshubHztJsOJxEEg6hPstOG");;
        GetNewsDataService newsDataService = mRetrofit.create(GetNewsDataService.class);
        Call<MostPopularNYT> call = newsDataService.getPopularNews( getApiKey() );
        MostPopularNYT result = null;
        try {
            result = call.execute().body();  //.body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestCase.assertEquals(result.getStatus(),"OK");
        TestCase.assertEquals(result.getResults(),"Politics");
    }

    @Test
    public void getBusinessTest() {
        setApiKey("J0iJw0a8fdshubHztJsOJxEEg6hPstOG");;
        GetNewsDataService newsDataService = mRetrofit.create(GetNewsDataService.class);
        String fq = "Business";
        Call<BusinessNYT> call = newsDataService.getBusinessNews( fq ,getApiKey() );
        BusinessNYT result = null;
        try {
            result = call.execute().body();  //.body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestCase.assertEquals(result.getStatus(),"OK");
    }

    @Test
    public void getNotifLowDataTest() {
        //init param
        setApiKey("J0iJw0a8fdshubHztJsOJxEEg6hPstOG");;
        String beginDate ="20200201";
        String q = "corona virus";
        String fq = buildSectionSeleted();
        String fl = "hits";

        GetNewsDataService newsDataService = mRetrofit.create(GetNewsDataService.class);
        Call<NotificationLowData> call = newsDataService.getNotifLowData( beginDate, fl ,fq, q ,getApiKey() );

        NotificationLowData result = null;
        try {
            result = call.execute().body();  //.body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestCase.assertEquals(result.getStatus(),"OK");
        TestCase.assertNotNull(result.getResponse().getMeta().getHits());
    }

    /**
     * build string with all section checked
     * @return
     */
    private String buildSectionSeleted(){
        // get all section selected
        String sBuildFQ = "";
        sBuildFQ = "\"Arts\" ";
        sBuildFQ += "\"Business\" ";
        sBuildFQ += "\"Movies\" ";
        sBuildFQ += "\"Politics\" ";
        sBuildFQ += "\"Sports\" ";
        sBuildFQ += "\"Travel\" ";

        return sBuildFQ;
    }

}
