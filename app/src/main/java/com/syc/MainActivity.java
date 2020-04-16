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
import android.view.Menu;
import android.view.MenuItem;
import androidx.viewpager.widget.ViewPager;
import butterknife.ButterKnife;
import butterknife.BindView;
import static com.syc.utils.Utils.creatNotification;
import static com.syc.utils.Utils.loadSetting;
import static com.syc.utils.Utils.loadSharedPreferences;

/**
 * Created by Chazette Sylvain
 *
 *
 *
 *
 */
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
        // =================================================================================================== back searchActivity - option NOTIF
        if(resultCode==1){
            if( data != null ){
                // put in sharedPrefs
                loadSetting(data,false);

                if( data.getBooleanExtra("switchNotif", false )){
                    creatNotification(true,getApplicationContext());
                }else{
                    creatNotification(false,getApplicationContext());
                }
            }
        }
        // =================================================================================================== back searchActivity - option SEARCH
        if(resultCode==2){
            if( data != null ){
                if(data.getBooleanExtra("goSearch",false)){
                    // get Search params and launch ResultSearchActivity
                    Intent ResultSearchActivityIntent = new Intent(MainActivity.this, ResultSearchActivity.class);
                    //loadSetting(ResultSearchActivityIntent, true);
                    ResultSearchActivityIntent.putExtra("qSearch",data.getStringExtra("qSearch"));
                    ResultSearchActivityIntent.putExtra("fqSearch",data.getStringExtra("fqSearch"));
                    ResultSearchActivityIntent.putExtra("sBeginDate",data.getStringExtra("sBeginDate"));
                    ResultSearchActivityIntent.putExtra("sEndDate",data.getStringExtra("sEndDate"));

                    ResultSearchActivityIntent.putExtra("bNotif", false);
                    startActivityForResult(ResultSearchActivityIntent, 4);
                }
            }
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
        // =================================================================================================== back ResultSearchActivity
        if(resultCode==4){
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

}
