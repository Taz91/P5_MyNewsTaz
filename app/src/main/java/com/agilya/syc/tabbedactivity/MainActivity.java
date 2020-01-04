package com.agilya.syc.tabbedactivity;
import com.agilya.syc.models.NewResult;
import com.agilya.syc.models.Result;

import android.content.Intent;
import android.os.Bundle;

import com.agilya.syc.utils.GetNewsDataService;
import com.agilya.syc.utils.MyAdapter;
import com.agilya.syc.utils.RetrofitInstance;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import butterknife.ButterKnife;
import butterknife.BindView;


public class MainActivity extends AppCompatActivity {
    RecyclerView content;
    private static int tabChoice = 1;
    private Call<NewResult> call;
    private String typeNews;
    private static String API_KEY = "J0iJw0a8fdshubHztJsOJxEEg6hPstOG";

    //Resources res = getResources();
    //private static final String API_KEY = Context.get  //.getResources().getString(R.string.news_api_key);
    //String API_KEY = res.getString(R.string.news_api_key);

//test du soir !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    @BindView(R.id.main_news) CoordinatorLayout mainNews;
    @BindView(R.id.appbarlayout) AppBarLayout appBarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);




        //retro compatibilté
        //Knife //
        //Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //btn en bas
        //Knife //
        ///FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        content = findViewById(R.id.rv_list);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        content.setLayoutManager(verticalLayoutManager);

        //loadData();
    }

    private void loadData() {

        // A traiter switch sur 2 cas
        //          == 1) MostPopular / TopStories
        //          == 2) Search

        GetNewsDataService newsDataService = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
        switch (tabChoice){
            case 1:
                call = newsDataService.getTopStoriesNew("science", API_KEY);
                break;
            case 2:
                /*
                MostPopular : 3 types (emailed/viewed/shared), periode 1,7,30j
                https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=yourkey
                https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=yourkey

                Cas shared: shared type = email, facebook, twitter, periode = 1,7,30j
                https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=yourkey
                https://api.nytimes.com/svc/mostpopular/v2/shared/1.json?api-key=yourkey
                */

                if (typeNews.equals("facebook") || typeNews.equals("email") || typeNews.equals("twitter") ) {
                    //shared -- partie de periode --
                    call = newsDataService.getPopularNews("shared/1/", "7.json", API_KEY);
                }
                else if (typeNews.equals("shared")){

                }
                call = newsDataService.getPopularNews("shared", "7.json", API_KEY);
                break;
        }

            //newsDataService.getTopStoriesNew("science.json", API_KEY).enqueue(new Callback<NewResult>(){
            call.enqueue(new Callback<NewResult>(){
            @Override
            public void onResponse(Call<NewResult> call, Response<NewResult> response) {
                Toast.makeText(MainActivity.this, "Yesss c'est ok", Toast.LENGTH_LONG).show();

                List<Result> result = response.body().getResults();

                MyAdapter adapter = new MyAdapter(result);
                content.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<NewResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Une erreur", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.menu_notifications:
                callNotificationParams();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //optionMenu Notification
    private void callNotificationParams(){
        Intent notifParams = new Intent(MainActivity.this,Fragment_category.class);
        startActivity(notifParams);
    }

}
