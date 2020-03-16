package com.syc;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.syc.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import static com.syc.utils.Utils.loadSharedPreferences;

public class SearchActivity extends AppCompatActivity {
    // =================================================================== shared_preferences :
    private SharedPreferences sharedPref;
    //save if Notif or Search option status
    Boolean bNotifStatus = false;

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.searchactivity_appbarlayout) AppBarLayout searchactivity_appbarlayout;
    @BindView(R.id.searchactivity_toolbar) Toolbar searchactivity_toolbar;
    @BindView(R.id.searchactivity_Text) EditText searchactivity_text;
    @BindView(R.id.searchactivity_datebegin) EditText searchactivity_dateBegin;
    @BindView(R.id.searchactivity_datebeginlabel) TextView searchactivity_dateBeginLabel;
    @BindView(R.id.searchactivity_dateend) EditText searchactivity_dateEnd;
    @BindView(R.id.searchactivity_dateendlabel ) TextView searchactivity_dateEndLabel;
    @BindView(R.id.cb_arts) CheckBox cbArts;
    @BindView(R.id.cb_business) CheckBox cbBusiness;
    @BindView(R.id.cb_movies) CheckBox cbMovies;
    @BindView(R.id.cb_politics) CheckBox cbPolitics;
    @BindView(R.id.cb_sports) CheckBox cbSport;
    @BindView(R.id.cb_travel) CheckBox cbTravel;
    @BindView(R.id.searchactivity_search) Button searchactivity_search;
    @BindView(R.id.searchactivity_go_notif) Switch searchactivity_GoNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        // ========================================================== sharedPreferences
        // load sharedPreferences and use for Default display
        sharedPref = loadSharedPreferences(this);

        // ========================================================== toolbar
        setSupportActionBar(searchactivity_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get putExtra of MainActivity, show Notif or Search switch with params
        Intent intent = getIntent();
        if(intent != null  ){
            if(intent.hasExtra("bNotif") && intent.getBooleanExtra("bNotif", true)){
                //Notification view
                searchactivity_search.setVisibility(View.GONE);
                searchactivity_dateBegin.setVisibility(View.GONE);
                searchactivity_dateBeginLabel.setVisibility(View.GONE);
                searchactivity_dateEnd.setVisibility(View.GONE);
                searchactivity_dateEndLabel.setVisibility(View.GONE);
                searchactivity_GoNotif.setVisibility(View.VISIBLE);
                setTitle("Notifications");
                bNotifStatus = true;
            }else{
                //Search view
                searchactivity_search.setVisibility(View.VISIBLE);
                searchactivity_dateBegin.setVisibility(View.VISIBLE);
                searchactivity_dateBeginLabel.setVisibility(View.VISIBLE);
                searchactivity_dateEnd.setVisibility(View.VISIBLE);
                searchactivity_dateEndLabel.setVisibility(View.VISIBLE);
                searchactivity_GoNotif.setVisibility(View.GONE);
                setTitle("Search Articles");
                bNotifStatus = false;
            }

            if(intent.hasExtra("wordDefault")){
                searchactivity_text.setText(intent.getStringExtra("wordDefault"));
            }
            if(intent.hasExtra("cbArts")){
                cbArts.setChecked(intent.getBooleanExtra("cbArts", false));
            }
            if(intent.hasExtra("cbBusiness")){
                cbBusiness.setChecked(intent.getBooleanExtra("cbBusiness", false));
            }
            if(intent.hasExtra("cbMovies")){
                cbMovies.setChecked(intent.getBooleanExtra("cbMovies", false));
            }
            if(intent.hasExtra("cbPolitics")){
                cbPolitics.setChecked(intent.getBooleanExtra("cbPolitics", false));
            }
            if(intent.hasExtra("cbSport")){
                cbSport.setChecked(intent.getBooleanExtra("cbSport", false));
            }
            if(intent.hasExtra("cbTravel")){
                cbTravel.setChecked(intent.getBooleanExtra("cbTravel", false));
            }
        }

        //TODO : delete under
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /**
         * click on button Search, get params, start activity Search
         *
         */
        searchactivity_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to button search in form Search Option
                verifyForm(bNotifStatus);
            }
        });
    }

    /**
     * react to the user tapping the back/up icon in the action bar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 2 possibility backPressed : 1) NotifView 2) SearchView
     * when NotifView, save params, go back with intent back
     * when SearchView, go back with intent intent
     * finaly close activity
     */
    @Override
    public void onBackPressed() {
        //back to button search in form Notif Option
        verifyForm(bNotifStatus);
    }

    /**
     * verify information provided in form as correct, for Option Search and Notification
     * @param bNotif
     */
    private void verifyForm(boolean bNotif){
        Integer nResultCode = bNotifStatus ? 1:2;
        boolean bOk = false;

        // get all section selected
        String sBuildFQ = "";
        sBuildFQ = (cbArts.isChecked())? "\"Arts\" " : "" ;
        sBuildFQ += (cbBusiness.isChecked())? "\"Business\" " : "" ;
        sBuildFQ += (cbMovies.isChecked())? "\"Movies\" " : "" ;
        sBuildFQ += (cbPolitics.isChecked())? "\"Politics\" " : "" ;
        sBuildFQ += (cbSport.isChecked())? "\"Sports\" " : "" ;
        sBuildFQ += (cbTravel.isChecked())? "\"Travel\" " : "" ;

        //bOk = true if : one or more section are selected and some worlds entered
        bOk = !sBuildFQ.isEmpty() && (searchactivity_text.getText().toString().isEmpty() ? false : true);
        if(bOk){
            //Utils.sharedPref.
            // je reviens de la notif ou de la search

            Intent back = new Intent();
            back.putExtra("cbArts", cbArts.isChecked());
            back.putExtra("cbBusiness", cbBusiness.isChecked());
            back.putExtra("cbMovies", cbMovies.isChecked());
            back.putExtra("cbPolitics", cbPolitics.isChecked());
            back.putExtra("cbSport", cbSport.isChecked());
            back.putExtra("cbTravel", cbTravel.isChecked());
            back.putExtra("wordDefault", searchactivity_text.getText().toString());
            back.putExtra("bNotif", bNotifStatus);
            //to simplify code
            back.putExtra("fq", sBuildFQ);

            //TODO : verify date
            //apply check with form Option environment
            if(bNotifStatus){
                // Notif option : verify Notif enable
                back.putExtra("switchNotif", searchactivity_GoNotif.isChecked());
            }else{
                // Search option : verify date value begin / end
                back.putExtra("sBeginDate", searchactivity_dateBegin.getText().toString());
                back.putExtra("sEndDate", searchactivity_dateEnd.getText().toString());
                Toast.makeText(getBaseContext(), "Verify date !", Toast.LENGTH_LONG).show();
            }

            SearchActivity.this.setResult(nResultCode,back);
            SearchActivity.this.finish();

        }else{
            //TODO : possibility => color background in word / and label category
            Toast.makeText(getBaseContext(), "Please select one categorie and write one word !", Toast.LENGTH_LONG).show();
        }

        //TODO : doit on tester s'il n'y a pas de retour de Response ? (car si vide le RV sera vide ... et donc prévenir avant d'y retourner de changer des choses .
        //TODO : DatePicker ou autre format ? spinner date ou list de date ?
    }
}

