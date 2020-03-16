package com.syc;
import com.google.android.material.tabs.TabLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.syc.models.NotificationLowData;
import com.syc.models.NotificationResponse;
import com.syc.ui.main.SectionsPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.syc.utils.GetNewsDataService;
import com.syc.utils.NotificationWorker;
import com.syc.utils.RetrofitInstance;
import com.syc.utils.Utils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.viewpager.widget.ViewPager;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import butterknife.ButterKnife;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.syc.utils.Utils.getApiKey;
import static com.syc.utils.Utils.loadSetting;
import static com.syc.utils.Utils.loadSharedPreferences;
import static com.syc.utils.Utils.setnNbHits;

public class MainActivity extends AppCompatActivity {
    // =================================================================== shared_preferences :
    private SharedPreferences sharedPref;
    //==================================================================== view
    @BindView(R.id.main_news) CoordinatorLayout mainNews;
    @BindView(R.id.appbarlayout) AppBarLayout appBarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabs;
    //@BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // ========================================================== sharedPreferences
        // load sharedPreferences and use for Default display
        sharedPref = loadSharedPreferences(this);

        setSupportActionBar(toolbar);
        //Tabs :
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO : back searchActivity with NOTIF form option
        // =================================================================================================== back searchActivity - option NOTIF
        if(resultCode==1){
            if( data != null ){
                // ========================================================== sharedPreferences
                // put in sharedPrefs
                loadSetting(data,false);

                if( data.getBooleanExtra("switchNotif", false )){
                    creatNotification(true);
                }else{
                    creatNotification(false);
                }

                /*
                // reload sharedPreferences and use for Default display
                sharedPref = loadSharedPreferences(getApplicationContext());
                //TODO : pourquoi cela fonctionne pas ????????????
                //String beginDate = sharedPref.getString("NotifLastDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));;
                String beginDate ="";
                try {
                    beginDate = sharedPref.getString("NotifLastDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
                }
                catch(Exception e){
                    beginDate  = new SimpleDateFormat("yyyyMMdd").format(new Date());
                }finally {
                    Utils.setsBeginDate(beginDate);
                }
                //String endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                String q = sharedPref.getString("wordDefault", "");
                String fq = sharedPref.getString("fq", "");
                String fl = "hits";

                //https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20200301&end_date=20200315&fl=hits&fq=Arts Business Movies Sports Travel Politcs&q=corona virus france&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG
                GetNewsDataService newsDataService = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
                Call<NotificationLowData> call = newsDataService.getNotifLowData( beginDate, fl ,fq, q ,getApiKey() );

                call.enqueue(new Callback<NotificationLowData>(){
                    @Override
                    public void onResponse(Call<NotificationLowData> call, Response<NotificationLowData> response) {
                        NotificationResponse result = response.body().getResponse();

                        Integer nHits = result.getMeta().getHits().intValue();
                        setnNbHits(nHits);
                    }
                    @Override
                    public void onFailure(Call<NotificationLowData> call, Throwable t) {
                        //TODO: gestion message de retour : soit le site est inaccessible
                        Toast.makeText( getApplicationContext() , "Error : notification response", Toast.LENGTH_LONG).show();
                    }
                });
                */
            }
        }
        // =================================================================================================== back searchActivity - option SEARCH
        if(resultCode==2){
            // récupérer les params de la recherche et lancer une activity recherche !!
            // faire l'activity recherche
            //
        }
        // =================================================================================================== back helpActivity
        if(resultCode==3){
            if(data != null  ){
                if(data.getBooleanExtra("helpbModif", false)){
                    // reload sharedPreferences if necessary
                    sharedPref = loadSharedPreferences(this);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.menu_notifications:
                callNotificationParams(1);
                return true;
            case R.id.menu_search:
                callNotificationParams(2);
                return true;
            case R.id.menu_help:
                Intent helpIntent = new Intent(MainActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpbModif", false);
                startActivityForResult(helpIntent, 3);
                return true;
            case R.id.menu_about:
                //TODO: faire mini activity avec mode emploi appli
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //optionMenu Notification
    private void callNotificationParams(Integer pRoot){
        Intent searchActivityIntent = new Intent(MainActivity.this, SearchActivity.class);

        loadSetting(searchActivityIntent, true);
        switch(pRoot){
            case 1: // Notification view
                searchActivityIntent.putExtra("bNotif", true);
                startActivityForResult(searchActivityIntent, 1);
                break;
            case 2: // Search view
                searchActivityIntent.putExtra("bNotif", false);
                startActivityForResult(searchActivityIntent, 2);
                break;
            default: // Notification view
                searchActivityIntent.putExtra("bNotif", true);
                startActivityForResult(searchActivityIntent, 1);
        }
    }

    public void creatNotification(boolean pbGoNotif){
        WorkManager mWorkManager = WorkManager.getInstance();

        if( pbGoNotif){
            // ================================================= One time request !!
            OneTimeWorkRequest mRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class).build();
            //pull unique job in queue
            mWorkManager.enqueueUniqueWork("nyt_periodic", ExistingWorkPolicy.REPLACE, mRequest);

            //TODO: go periodic
            // ================================================= Periodic request !!
            //PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder( NotificationWorker.class,20, TimeUnit.MINUTES ).build();
            //pull periodic job in queue
            //mWorkManager.enqueueUniquePeriodicWork("nyt_periodic", ExistingPeriodicWorkPolicy.REPLACE, mRequest);
        }else{
            //TODO: Stop the periodic Notification
            //mWorkManager.cancelAllWorkByTag("nyt_channel");
            mWorkManager.cancelUniqueWork("nyt_periodic");
        }
    }
    //TODO : use utils to nows if Article is viewed

    /**
     *
     *
     */
    /*
    private void eloadSetting(Intent psearchActivityIntent ){
        apiKey = sharedPref.getString( PREFS_APIKEY , "" );
        if(sharedPref != null){
            psearchActivityIntent.putExtra("wordDefault", sharedPref.getString("wordDefault",""));
            psearchActivityIntent.putExtra("cbArts", sharedPref.getBoolean("cbArts", false));
            psearchActivityIntent.putExtra("cbBusiness", sharedPref.getBoolean("cbBusiness", false));
            psearchActivityIntent.putExtra("cbEntrepreneur", sharedPref.getBoolean("cbEntrepreneur", false));
            psearchActivityIntent.putExtra("cbPolitics", sharedPref.getBoolean("cbPolitics", false));
            psearchActivityIntent.putExtra("cbSport", sharedPref.getBoolean("cbSport", false));
            psearchActivityIntent.putExtra("cbTravel", sharedPref.getBoolean("cbTravel", false));
            psearchActivityIntent.putExtra("switchNotif", sharedPref.getBoolean("switchNotif", false));
            psearchActivityIntent.putExtra("articleViewed", sharedPref.getString("articleViewed", ""));
            psearchActivityIntent.putExtra("nbArticle", sharedPref.getInt("nbArticle", 30));
        }else {
            //sharedPrefLoadDefault();
        }

        //Build
        List<Item> listDayTemp ;
        listDayTemp = new ArrayList<>();
        List<Item> listDay ;
        listDay = new ArrayList<>();
        int countItem = 0;

        //Get all mood
        sharedPreferencesArticleViewed = getBaseContext().getSharedPreferences(PREFS_ARTICLEVIEWED, MODE_PRIVATE);
        Map<String, ?> prefsMap = sharedPreferencesArticleViewed.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {

            //Log.d("montest", entry.getKey() +" != " + buildKey());

            //dont show current mood
            if (!entry.getKey().equals(buildKey()) ){
                listDayTemp.add(new Item(entry.getKey(), getComment( entry.getKey() ), tbliColor[iMood], iMood ));
            }
        }
        //Collections.sort(listDayTemp, (Item p1, Item p2) -> p1.itemColor > p2.itemColor );

        //listDayTemp full mood list, listDay contruct with full oy max 7 item
        Collections.sort(listDayTemp, Collections.reverseOrder());
        if (bFull){
            listDay = listDayTemp;
        }
        else {
            //if list is less then 7 item, error ibound
            countItem = (listDayTemp.size()<7) ? listDayTemp.size(): 7 ;
            for(int i = 0; i <countItem; i++){
                listDay.add(listDayTemp.get(i));
            }
        }

    }
    */
}
