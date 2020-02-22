package com.syc;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.syc.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import static com.syc.utils.Utils.loadSharedPreferences;
import static com.syc.utils.Utils.setSharedTopStoriesCategory;
import static com.syc.utils.Utils.setbRemoveSharedPref;
import static com.syc.utils.Utils.sharedPrefRemove;

public class HelpActivity extends AppCompatActivity {
    @BindView(R.id.helpactivity_choicemostpopularspinner) Spinner helpchoicemostpopularspinner;
    @BindView(R.id.helpactivity_appbarlayout) AppBarLayout helpactivity_appbarlayout;
    @BindView(R.id.helpactivity_toolbar) Toolbar helpactivity_toolbar;
    @BindView(R.id.helpactivity_choicespinner) TextView helpactivity_choicespinner;
    @BindView(R.id.helpactivity_removesharedpref) Switch buttonRemoveSharedPref;
    @BindView(R.id.helpactivity_confirm_rg) RadioGroup helpactivity_confirm_rg;
    @BindView(R.id.helpactivity_confirm_no) RadioButton helpactivity_confirm_no;
    @BindView(R.id.helpactivity_confirm_yes) RadioButton helpactivity_confirm_yes;
    @BindView(R.id.helpactivity_removelabel) TextView helpactivity_removelabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        setSupportActionBar(helpactivity_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO: mettre dans les ressources
        //construct liste for spinner:
        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("home");
        spinnerList.add("arts");
        spinnerList.add("business");
        spinnerList.add("entrepreneurs");
        spinnerList.add("politics");
        spinnerList.add("sports");
        spinnerList.add("travel");

        ArrayAdapter<String> spinnerListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerList);
        spinnerListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        helpchoicemostpopularspinner.setAdapter(spinnerListAdapter);
        //position default if necessary  :  helpchoicemostpopularspinner.setSelection(2);

        helpchoicemostpopularspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //helpactivity_choicespinner.setText(helpchoicemostpopularspinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * show Confirm Remove SharedPrefe
         */
        buttonRemoveSharedPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonRemoveSharedPref.isChecked()){
                    helpactivity_removelabel.setVisibility(View.VISIBLE);
                    helpactivity_confirm_rg.setVisibility(View.VISIBLE);
                }else{
                    helpactivity_removelabel.setVisibility(View.INVISIBLE);
                    helpactivity_confirm_rg.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * Remove SharedPreferences if confirm yes clicked
     */
    public void rbclick(View v) {
        int rgCheckedId = helpactivity_confirm_rg.getCheckedRadioButtonId();
        if (rgCheckedId == R.id.helpactivity_confirm_yes) {
            //Confirmed Remove sharedPreferences !!
            setbRemoveSharedPref(true);
            sharedPrefRemove();
            loadSharedPreferences(getBaseContext());
            Toast.makeText(getBaseContext(), "Parameter are initialized", Toast.LENGTH_LONG).show();
            helpactivity_confirm_no.setChecked(true);
            buttonRemoveSharedPref.setChecked(false);
            helpactivity_removelabel.setVisibility(View.INVISIBLE);
            helpactivity_confirm_rg.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            //save choice TopStoriesCategory in sharedPref
            if(helpchoicemostpopularspinner.getSelectedItem().toString().isEmpty()){
                setSharedTopStoriesCategory("home"); }else {
                setSharedTopStoriesCategory(helpchoicemostpopularspinner.getSelectedItem().toString());
            }
            finish();
            return true;
        }
        return false;
    }

}
