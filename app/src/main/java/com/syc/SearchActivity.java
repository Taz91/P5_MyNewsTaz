package com.syc;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
    @BindView(R.id.cb_entrepreneurs) CheckBox cbEntrepreneur;
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
            }else{
                //Search view
                searchactivity_search.setVisibility(View.VISIBLE);
                searchactivity_dateBegin.setVisibility(View.VISIBLE);
                searchactivity_dateBeginLabel.setVisibility(View.VISIBLE);
                searchactivity_dateEnd.setVisibility(View.VISIBLE);
                searchactivity_dateEndLabel.setVisibility(View.VISIBLE);
                searchactivity_GoNotif.setVisibility(View.GONE);
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
            if(intent.hasExtra("cbEntrepreneur")){
                cbEntrepreneur.setChecked(intent.getBooleanExtra("cbEntrepreneur", false));
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

                String sBuildFQ = "";
                sBuildFQ = (cbArts.isChecked())? "\"Arts\" " : "" ;
                sBuildFQ += (cbBusiness.isChecked())? "\"Business\" " : "" ;
                sBuildFQ += (cbEntrepreneur.isChecked())? "\"Entrepreneurs\" " : "" ;
                sBuildFQ += (cbPolitics.isChecked())? "\"Politics\" " : "" ;
                sBuildFQ += (cbSport.isChecked())? "\"Sports\" " : "" ;
                sBuildFQ += (cbTravel.isChecked())? "\"Travel\" " : "" ;

                //TODO : si search, alors tester si valeur obligatoire sont ok
                //else fields required
                if(!searchactivity_text.getText().toString().isEmpty() || !sBuildFQ.isEmpty() ){
                    Intent back = new Intent();
                    back.putExtra("cbArts", cbArts.isChecked());
                    back.putExtra("cbBusiness", cbBusiness.isChecked());
                    back.putExtra("cbEntrepreneur", cbEntrepreneur.isChecked());
                    back.putExtra("cbPolitics", cbPolitics.isChecked());
                    back.putExtra("cbSport", cbSport.isChecked());
                    back.putExtra("cbTravel", cbTravel.isChecked());
                    back.putExtra("sBeginDate", searchactivity_dateBegin.getText().toString());
                    back.putExtra("sEndDate", searchactivity_dateEnd.getText().toString());
                    back.putExtra("wordDefault", searchactivity_text.getText().toString() );
                    //String monTest = searchactivity_text.getText().toString() ;

                    SearchActivity.this.setResult(1,back);
                    SearchActivity.this.finish();
                }else{
                    Toast.makeText(getBaseContext(), "Champ obligatoire manquant !!", Toast.LENGTH_LONG).show();
                }

                //TODO : doit on tester s'il n'y a pas de retour de Response ? (car si vide le RV sera vide ... et donc prévenir avant d'y retourner de changer des choses .
                //TODO : DatePicker ou autre format ? spinner date ou list de date ?
                //
                // code pour récup les saisies

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
        //
        Intent intent = getIntent();
        if(intent != null  ){
            if(intent.hasExtra("bNotif") && intent.getBooleanExtra("bNotif", true)){
                //Notification view
                Intent back = new Intent();
                back.putExtra("wordDefault", searchactivity_text.getText().toString() );
                back.putExtra("cbArts", cbArts.isChecked());
                back.putExtra("cbBusiness", cbBusiness.isChecked());
                back.putExtra("cbEntrepreneur", cbEntrepreneur.isChecked());
                back.putExtra("cbPolitics", cbPolitics.isChecked());
                back.putExtra("cbSport", cbSport.isChecked());
                back.putExtra("cbTravel", cbTravel.isChecked());
                back.putExtra("switchNotif", searchactivity_GoNotif.isChecked());
                SearchActivity.this.setResult(1,back);
            }else{
                //Search view
                SearchActivity.this.setResult(2, intent);
            }
        }
        SearchActivity.this.finish();
    }
}

