package com.syc;
import com.google.android.material.tabs.TabLayout;
import android.content.Intent;
import android.os.Bundle;
import com.syc.ui.main.SectionsPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import androidx.viewpager.widget.ViewPager;
import butterknife.ButterKnife;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    RecyclerView content;
    private String typeNews;
    private static String API_KEY = "J0iJw0a8fdshubHztJsOJxEEg6hPstOG";
    @BindView(R.id.main_news) CoordinatorLayout mainNews;
    @BindView(R.id.appbarlayout) AppBarLayout appBarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabs;

    //@BindView(R.id.fab) FloatingActionButton fab;
    //Resources res = getResources();
    //private static final String API_KEY = Context.get  //.getResources().getString(R.string.news_api_key);
    //String API_KEY = res.getString(R.string.news_api_key);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        Intent searchActivityIntent = new Intent(MainActivity.this, SearchActivity.class);
        //TODO: ici mettre le code pour récupérer les préférences et mettre ces valeurs par défaut au niveau du form search
        //
        //
        String maString = "word1 word2 word3 etc";
        searchActivityIntent.putExtra("wordDefault", maString);
        searchActivityIntent.putExtra("cbArts", true);
        searchActivityIntent.putExtra("cbSport", true);
        searchActivityIntent.putExtra("bNotif", true);
        startActivity(searchActivityIntent);
    }

}
