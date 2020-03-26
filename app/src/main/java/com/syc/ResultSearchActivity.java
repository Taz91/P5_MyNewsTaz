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
import android.view.MenuItem;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.syc.models.TopResult;
import com.syc.models.TopStoriesNYT;
import com.syc.utils.GetNewsDataService;
import com.syc.utils.ResultAdapter;
import com.syc.utils.RetrofitInstance;
import java.util.List;
import static com.syc.utils.Utils.getApiKey;
import static com.syc.utils.Utils.getSharedTopStoriesCategory;
import static com.syc.utils.Utils.loadSharedPreferences;

public class ResultSearchActivity extends AppCompatActivity {
    // =================================================================== shared_preferences :
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

        loadData();

    }


    private void loadData() {
        //@GET("search/v2/articlesearch.json")
        //Call<NotificationLowData> getSearchArticles(@Query("begin_date") String begin_date, @Query("end_date") String end_date, @Query("fq") String fq, @Query("q") String q, @Query("api-key") String userkey );

        GetNewsDataService newsDataService = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
        Call<TopStoriesNYT> call = newsDataService.getTopStoriesNew( getSharedTopStoriesCategory(), getApiKey() );

        call.enqueue(new Callback<TopStoriesNYT>(){

            @Override
            public void onResponse(Call<TopStoriesNYT> call, Response<TopStoriesNYT> response) {
                List<TopResult> result = response.body().getResults();

                ResultAdapter adapter = new ResultAdapter(result , Glide.with(getApplicationContext()), getApplicationContext());

                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rvList.setLayoutManager(verticalLayoutManager);
                rvList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TopStoriesNYT> call, Throwable t) {
                // TODO : gestion message de retour : soit le site est inaccessible
                // y mettre une image ou un fragment spécifique ?

                Toast.makeText(getApplicationContext(), "Une erreur", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     *
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
