package com.syc;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.syc.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static com.syc.utils.Utils.loadSharedPreferences;

/**
 * Activity Search with 2 options : Notification / manage Search
 * management of display and control differences, date / enable notification, common control.
 * root differences, back menu for notification validate choice, for search cancel choice.
 * use one datepicker for 2 date (root variable to differentiate),
 *
 *
 */
public class SearchActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private SharedPreferences sharedPref;
    //save if Notif or Search option status
    Boolean bNotifStatus = false;
    String sBuildFQ;
    Integer root;

    //@BindView(R.id.fab) FloatingActionButton fab;
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
    @BindView(R.id.searchactivity_search) Button searchactivity_gosearch;
    @BindView(R.id.searchactivity_go_notif) Switch searchactivity_GoNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        // load sharedPreferences and use for Default display
        sharedPref = loadSharedPreferences( this);
        // ========================================================== toolbar
        setSupportActionBar(searchactivity_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get putExtra of MainActivity, show Notif or Search switch with params
        Intent intent = getIntent();
        if(intent != null  ){
            if(intent.hasExtra("bNotif") && intent.getBooleanExtra("bNotif", true)){
                //Notification view
                searchactivity_gosearch.setVisibility(View.GONE);
                searchactivity_dateBegin.setVisibility(View.GONE);
                searchactivity_dateBeginLabel.setVisibility(View.GONE);
                searchactivity_dateEnd.setVisibility(View.GONE);
                searchactivity_dateEndLabel.setVisibility(View.GONE);
                searchactivity_GoNotif.setVisibility(View.VISIBLE);
                setTitle("Notifications");
                bNotifStatus = true;
                //apply custom view for Notif Option
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
                if(intent.hasExtra("qNotif")){
                    searchactivity_text.setText(intent.getStringExtra("qNotif"));
                }
            }else{
                //Search view
                searchactivity_gosearch.setVisibility(View.VISIBLE);
                searchactivity_dateBegin.setVisibility(View.VISIBLE);
                searchactivity_dateBeginLabel.setVisibility(View.VISIBLE);
                searchactivity_dateEnd.setVisibility(View.VISIBLE);
                searchactivity_dateEndLabel.setVisibility(View.VISIBLE);
                searchactivity_GoNotif.setVisibility(View.GONE);
                setTitle("Search Articles");
                bNotifStatus = false;
            }
        }

        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        /**
         * click on button Search, get params, start activity Search
         *
         */
        searchactivity_gosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to button search in form Search Option
                closeForm(true);
            }
        });

        // ========================================================== DatePicker - beginDate / endDate
        searchactivity_dateBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root = 1;
                showDatePickerDialog();
            }
        });
        searchactivity_dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root = 2;
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                            this,
                                            this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year ,month , dayOfMonth);
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        String setDate = formater.format(c.getTime());
        if(root==1){
            if(searchactivity_dateEnd.getText().toString().isEmpty()){
                searchactivity_dateBegin.setText(setDate);
            }else{
                if( Integer.parseInt(Utils.convertDate(searchactivity_dateEnd.getText().toString(),"dd/MM/yyyy", "yyyyMMdd")) >= Integer.parseInt(Utils.convertDate(setDate,"dd/MM/yyyy", "yyyyMMdd")) ){
                    searchactivity_dateBegin.setText(setDate);
                }else{
                    Toast.makeText(getBaseContext(), "start date must be smaller than end date.", Toast.LENGTH_LONG).show();
                }
                /*
                if(Integer.valueOf(searchactivity_dateEnd.getText().toString()) >= Integer.valueOf(setDate)){
                    searchactivity_dateBegin.setText(setDate);
                }else{
                    Toast.makeText(getBaseContext(), "start date must be smaller than end date.", Toast.LENGTH_LONG).show();
                }
                */
            }
        }

        if(root==2){
            if(searchactivity_dateBegin.getText().toString().isEmpty()){
                searchactivity_dateEnd.setText(setDate);
            }else{
                if( Integer.parseInt( Utils.convertDate(searchactivity_dateBegin.getText().toString(),"dd/MM/yyyy", "yyyyMMdd")) <= Integer.parseInt(Utils.convertDate(setDate,"dd/MM/yyyy", "yyyyMMdd")) ){
                    searchactivity_dateEnd.setText(setDate);
                }else{
                    Toast.makeText(getBaseContext(), "start date must be smaller than end date.", Toast.LENGTH_LONG).show();
                }
            }
        }
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
    public void onBackPressed(){
        // 2 root => 1) backSpace to Notif Option, 2) backSpace to Search Option.
        if(bNotifStatus){// Notif
            closeForm(true);
        }else{// Search
            closeForm(false);
        }
    }

    /**
     * verify information provided in form as correct, for Option Search and Notification
     * @param bBackSpace
     */
    private void closeForm(boolean bBackSpace){
        Integer nResultCode = bNotifStatus ? 1:2;
        boolean bOk = true;
        Intent back = new Intent();
        back.putExtra("bNotif", bNotifStatus);

        if (bBackSpace) {
            // get all section selected
            sBuildFQ = buildSectionSeleted();
            // bOk = true if : one or more section are selected and some worlds entered
            bOk = !sBuildFQ.isEmpty() && (!searchactivity_text.getText().toString().isEmpty());
            // Notif option : get Notif parameters
            if(bOk) {
                if (bNotifStatus) {
                    back.putExtra("switchNotif", searchactivity_GoNotif.isChecked());
                    back.putExtra("wordDefault", searchactivity_text.getText().toString());
                    back.putExtra("qNotif", searchactivity_text.getText().toString());
                    //to simplify code, concat is donne
                    back.putExtra("fqNotif", sBuildFQ);
                    //parameters for apply in form
                    back.putExtra("cbArts", cbArts.isChecked());
                    back.putExtra("cbBusiness", cbBusiness.isChecked());
                    back.putExtra("cbMovies", cbMovies.isChecked());
                    back.putExtra("cbPolitics", cbPolitics.isChecked());
                    back.putExtra("cbSport", cbSport.isChecked());
                    back.putExtra("cbTravel", cbTravel.isChecked());
                }else{
                    //to simplify code, concat is donne
                    back.putExtra("fqSearch", sBuildFQ);
                    back.putExtra("qSearch", searchactivity_text.getText().toString());
                    back.putExtra("sBeginDate", Utils.convertDate(searchactivity_dateBegin.getText().toString(),"dd/MM/yyyy","yyyyMMdd"));
                    back.putExtra("sEndDate", Utils.convertDate(searchactivity_dateEnd.getText().toString(),"dd/MM/yyyy","yyyyMMdd"));
                    back.putExtra("goSearch", true);
                }
            }else{//Verify is ko, return in form
                bOk = false;
                Toast.makeText(getBaseContext(), "Please select one categorie and write one word !", Toast.LENGTH_LONG).show();
                //TODO : possibility => color background in word and/or label category
            }
        }else{
            //BackSpace Search Option, not execute Search Result
            back.putExtra("goSearch", false);
        }
        if (bOk) {
            SearchActivity.this.setResult(nResultCode,back);
            SearchActivity.this.finish();
        }
    }

    /**
     * build string with all section checked
     * @return
     */
    private String buildSectionSeleted(){
        // get all section selected
        String sBuildFQ = "";
        sBuildFQ = (cbArts.isChecked())? "\"Arts\" " : "" ;
        sBuildFQ += (cbBusiness.isChecked())? "\"Business\" " : "" ;
        sBuildFQ += (cbMovies.isChecked())? "\"Movies\" " : "" ;
        sBuildFQ += (cbPolitics.isChecked())? "\"Politics\" " : "" ;
        sBuildFQ += (cbSport.isChecked())? "\"Sports\" " : "" ;
        sBuildFQ += (cbTravel.isChecked())? "\"Travel\" " : "" ;

        return sBuildFQ;
    }
}

