package com.syc;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.TreeMap;
import static com.syc.utils.Utils.getSharedTopStoriesCategory;
import static com.syc.utils.Utils.getnArticlesMax;
import static com.syc.utils.Utils.loadSharedPreferences;
import static com.syc.utils.Utils.setSharedTopStoriesCategory;
//import static com.syc.utils.Utils.setbRemoveSharedPref;
import static com.syc.utils.Utils.setnArticlesMax;
import static com.syc.utils.Utils.sharedPrefLoadDefault;
import static com.syc.utils.Utils.sharedPrefRemove;

/**
 * Created by Chazette Sylvain
 * this activity manages Help Menu :
 * 2 spinner to choice : 1) section for TopStories Activity, 2) number of items read store in history
 * switch button for remove sharedPref, with confirm button.
 * method to manage remove sharedPref.
 *
 */
public class HelpActivity extends AppCompatActivity {
    @BindView(R.id.helpactivity_appbarlayout) AppBarLayout helpactivity_appbarlayout;
    @BindView(R.id.helpactivity_toolbar) Toolbar helpactivity_toolbar;
    @BindView(R.id.helpactivity_topstories_spinner_choice) Spinner helpTopStoriesSpinnerChoice;
    @BindView(R.id.helpactivity_remove_sharedpref) Switch buttonRemoveSharedPref;
    @BindView(R.id.helpactivity_remove_label) TextView helpactivity_removelabel;
    @BindView(R.id.helpactivity_remove_confirm_rg) RadioGroup helpactivity_confirm_rg;
    @BindView(R.id.helpactivity_remove_confirm_no) RadioButton helpactivity_confirm_no;
    @BindView(R.id.helpactivity_remove_confirm_yes) RadioButton helpactivity_confirm_yes;
    @BindView(R.id.helpactivity_articlesviewed_spinner) Spinner helpactivity_articlesviewed_spinner;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        sharedPref = Utils.loadSharedPreferences(getApplicationContext());

        setSupportActionBar(helpactivity_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TreeMap<String,Integer> spinnerCategory = helpBuildTreeMapForSpinnerList();
        List<String> spinnerList = helpBuildListForSpinnerSection(spinnerCategory);

        ArrayAdapter<String> spinnerListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerList);
        spinnerListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        helpTopStoriesSpinnerChoice.setAdapter(spinnerListAdapter);
        //select in spinner the category saved or default (home)


        String test = sharedPref.getString("sharedTopStoriesCategory", "home");
        helpTopStoriesSpinnerChoice.setSelection(spinnerCategory.get(sharedPref.getString("sharedTopStoriesCategory", "home")));
        helpTopStoriesSpinnerChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //helpactivity_choicespinner.setText(helpchoicemostpopularspinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //data spinner - index and value - to preselect choice in spinner
        TreeMap<String,Integer> spinnerNbArticles = new TreeMap<>();
        //List for spinner nb articles:
        List<String> articlesViewedSpinnerList = new ArrayList<>();
        //Construct list and data spinner to select in spinner the nb saved (of articles viewed) .
        for(int i = 0; i <= 5; i++)
        {
            spinnerNbArticles.put(String.valueOf(i*5+5),i); //+5 for index array begin at 0
            articlesViewedSpinnerList.add(String.valueOf(i*5+5));
        }

        ArrayAdapter<String> articlesViewedSpinnerListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articlesViewedSpinnerList );
        articlesViewedSpinnerListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        helpactivity_articlesviewed_spinner.setAdapter(articlesViewedSpinnerListAdapter);
        // select in spinner nb articles saved or default (30)
        helpactivity_articlesviewed_spinner.setSelection(spinnerNbArticles.get(String.valueOf(sharedPref.getInt("nbArticles",30))));

        helpactivity_articlesviewed_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        if (rgCheckedId == R.id.helpactivity_remove_confirm_yes) {
            //Confirmed Remove sharedPreferences !!
            //setbRemoveSharedPref(true);
            sharedPref = sharedPrefRemove();
            sharedPref = sharedPrefLoadDefault();
            sharedPref = loadSharedPreferences(getApplicationContext());
            Toast.makeText(getApplicationContext(), "Parameter are initialized", Toast.LENGTH_LONG).show();
            helpactivity_confirm_no.setChecked(true);
            buttonRemoveSharedPref.setChecked(false);
            helpactivity_removelabel.setVisibility(View.INVISIBLE);
            helpactivity_confirm_rg.setVisibility(View.INVISIBLE);
        }
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
            //save choice TopStoriesCategory in sharedPref
            if(helpTopStoriesSpinnerChoice.getSelectedItem().toString().isEmpty()){
                setSharedTopStoriesCategory("home"); }else {
                if (!getSharedTopStoriesCategory().equals(helpTopStoriesSpinnerChoice.getSelectedItem().toString())) {
                    setSharedTopStoriesCategory(helpTopStoriesSpinnerChoice.getSelectedItem().toString());
                    bModif = true;
                }
            }
            //save choice nb Arcticle viewed in sharedPref
            if(helpactivity_articlesviewed_spinner.getSelectedItem() == null){
                setnArticlesMax(30); }else {
                if (!getnArticlesMax().equals(Integer.parseInt(helpactivity_articlesviewed_spinner.getSelectedItem().toString()))) {
                    setnArticlesMax(Integer.parseInt(helpactivity_articlesviewed_spinner.getSelectedItem().toString()));
                    bModif = true;
                }
            }

            //Intent back = new Intent();
            intent.putExtra("helpbModif", bModif);
            HelpActivity.this.setResult(3,intent);
            HelpActivity.this.finish();
            return true;
        }
        return false;
    }

    /**
     * build treeMap of category
     * @return
     */
    private TreeMap<String,Integer> helpBuildTreeMapForSpinnerList(){
        //data spinner - index and value - to preselect choice in spinner
        TreeMap<String,Integer> spinnerCategory = new TreeMap<>();
        spinnerCategory.put("arts",0);
        spinnerCategory.put("business",1);
        spinnerCategory.put("home",2);
        spinnerCategory.put("movies",3);
        spinnerCategory.put("politics",4);
        spinnerCategory.put("sports",5);
        spinnerCategory.put("travel",6);
        return spinnerCategory;
    }

    /**
     * build list to spinner choice category
     * @param pspinnerCategory
     * @return
     */
    private List<String> helpBuildListForSpinnerSection(TreeMap<String,Integer> pspinnerCategory){

        //TODO:collection.AddAll
        //construct list for spinner:
        List<String> spinnerList = new ArrayList<>();
        for(String s : pspinnerCategory.keySet()){
            spinnerList.add(s);
        }
        return spinnerList;
    }

}