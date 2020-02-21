package com.syc;
import com.google.android.material.tabs.TabLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.syc.ui.main.SectionsPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import butterknife.ButterKnife;
import butterknife.BindView;
import static com.syc.utils.Utils.loadSetting;
import static com.syc.utils.Utils.loadSharedPreferences;

public class MainActivity extends AppCompatActivity {
    RecyclerView content;
    private String typeNews;
    // =================================================================== shared_preferences :
    //private SharedPreferences sharedPref;
    //private static String apiKey;
    //====================================================================
    @BindView(R.id.main_news) CoordinatorLayout mainNews;
    @BindView(R.id.appbarlayout) AppBarLayout appBarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.backsearch) TextView backSearch;
    //@BindView(R.id.fab) FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // ========================================================== sharedPreferences
        // load sharedPreferences and use for Default display
        //sharedPref = loadSharedPreferences(this);

        loadSharedPreferences(this);


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
        if(resultCode==1){
            if(data != null  ){
                //test if value exist
                if(data.hasExtra("wordDefault")){
                    String monTest = data.getStringExtra("wordDefault");
                    backSearch.setText(data.getStringExtra("wordDefault"));
                /*
                    field-name:("value1" "value2" ... "value n")
                    field-name-1:("value1") AND field-name-2:("value2" "value3")
                    fq=source:("The New York Times")
                    fq=news_desk:("Sports" "Foreign")
                    fq=news_desk:("Sports") AND glocations:("NEW YORK CITY")
                    q=obama&facet_fields=source&facet=true&begin_date=20120101&end_date=20121231
                    https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=romney&facet_field=day_of_week&facet=true&begin_date=20120101&end_date=20120101&api-key=your-api-key
                    https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20191101&end_date=20191225&facet=false&fl=web_url,lead_paragraph,pub_date,section_name,subsection_name&fq=Sports,Arts&q=tennis,federer&sort=newest&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG
                */

                    String maVarTest = "";
                    if(!data.getStringExtra("wordDefault").isEmpty()){

                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                //TODO: faire mini activity avec param TopStories - choix section
                Intent searchActivityIntent = new Intent(MainActivity.this, HelpActivity.class);
                searchActivityIntent.putExtra("MenuHelp", "");
                startActivityForResult(searchActivityIntent, 3);
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

        loadSetting(searchActivityIntent);
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

    //TODO : use utils to nows if Artickle is viewed

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
