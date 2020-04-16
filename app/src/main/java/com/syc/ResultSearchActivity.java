package com.syc;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.syc.models.SearchDoc;
import com.syc.models.SearchNYT;
import com.syc.models.SearchResponse;
import com.syc.utils.GetNewsDataService;
import com.syc.utils.RetrofitInstance;
import com.syc.utils.SearchAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.syc.utils.Utils.getApiKey;
import static com.syc.utils.Utils.loadSharedPreferences;

/**
 * Created by Chazette Sylvain
 * Activity to show the result of personnal search
 * launch when click on magnifying glass in MainActivity.
 *
 */
public class ResultSearchActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    @BindView(R.id.resultsearchactivity_toolbar) Toolbar resultsearchactivity_toolbar;
    @BindView(R.id.rv_list) RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        ButterKnife.bind(this);

        // ========================================================== sharedPreferences
        // load sharedPreferences and use for Default display
        sharedPref = loadSharedPreferences(this);

        // ========================================================== toolbar
        setSupportActionBar(resultsearchactivity_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("My search result");

        //get putExtra of MainActivity, show Notif or Search switch with params
        Intent intent = getIntent();
        loadData(intent);
    }

    /**
     * initializes the consumption of the API NYT - search
     * service parameter : // q (words)  /  fq (sections)  /  beginDate  /  endDate
     *
     */
    private void loadData(Intent intent) {
        String beginDate = "";
        String endDate = "";

        Map<String,String> parameters = new HashMap<>();

        if(intent != null  ){
            if(intent.hasExtra("qSearch")){
                parameters.put("q", intent.getStringExtra("qSearch"));
            }
            if(intent.hasExtra("fqSearch")){
                parameters.put("fq", intent.getStringExtra("fqSearch"));
            }
            if(intent.hasExtra("sBeginDate")){
                beginDate = intent.getStringExtra("sBeginDate");
                if(!TextUtils.isEmpty(beginDate)){
                    parameters.put("beginDate", intent.getStringExtra("sBeginDate"));
                }
            }
            if(intent.hasExtra("sEndDate")){
                endDate = intent.getStringExtra("sEndDate");
                if(!TextUtils.isEmpty(endDate)){
                    parameters.put("endDate", intent.getStringExtra("sEndDate"));
                }
            }
        }

        parameters.put("api-key", getApiKey());

        GetNewsDataService newsDataService = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
        Call<SearchNYT> call = newsDataService.getSearchArticles( parameters );

        call.enqueue(new Callback<SearchNYT>(){

            @Override
            public void onResponse(Call<SearchNYT> call, Response<SearchNYT> response) {
                SearchResponse searchResponse = response.body().getResponse();
                List<SearchDoc> result = searchResponse.getDocs();

                SearchAdapter adapter = new SearchAdapter(result , Glide.with(getBaseContext()), getApplicationContext());

                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                rvList.setLayoutManager(verticalLayoutManager);
                rvList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<SearchNYT> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error consumption of API NYT - SearchResult", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Search Result : user dont launch search result, like escape activity
     * @param item, back activity
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Boolean bModif = false;
        Intent intent = getIntent();

        if(item.getItemId() == android.R.id.home){
            intent.putExtra("helpbModif", bModif);
            ResultSearchActivity.this.setResult(4,intent);
            ResultSearchActivity.this.finish();
            return true;
        }
        return false;
    }

}
