package com.syc;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.searchactivity_appbarlayout) AppBarLayout searchactivity_appbarlayout;
    @BindView(R.id.searchactivity_toolbar) Toolbar searchactivity_toolbar;
    @BindView(R.id.searchactivity_go) Button searchactivity_go;
    @BindView(R.id.searchactivity_Text) EditText searchactivity_text;
    @BindView(R.id.cb_arts) CheckBox cbArts;
    @BindView(R.id.cb_sports) CheckBox cbSport;
    @BindView(R.id.notif_go) Switch notifGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        //get putExtra of MainActivity
        Intent intent = getIntent();
        if(intent != null  ){
            if(intent.hasExtra("bNotif") && intent.getBooleanExtra("bNotif", false)){
                //notifGo.setVisibility(View.INVISIBLE);
                searchactivity_text.setText(intent.getStringExtra("wordDefault"));
                searchactivity_go.setVisibility(View.INVISIBLE);
            }
            //


            if(intent.hasExtra("wordDefault")){
                searchactivity_text.setText(intent.getStringExtra("wordDefault"));
            }
            if(intent.hasExtra("cbArts")){
                cbArts.setChecked(intent.getBooleanExtra("cbArts", false));
            }
            if(intent.hasExtra("cbSport")){
                cbSport.setChecked(intent.getBooleanExtra("cbSport", false));
            }

        }
        //searchactivity_toolbar.setDisplayShowHomeEnabled(true);
        //searchactivity_toolbar.setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        searchactivity_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : faire code du retour search, tester si back ou search
                //TODO : si back, pas de vérif pas d'enregistrement, fin activity et retour mainActivity
                //TODO : si search, alors tester si valeur obligatoire sont ok
                //TODO : doit on tester s'il n'y a pas de retour de Response ? (car si vide le RV sera vide ... et donc prévenir avant d'y retourner de changer des choses .
                //TODO : DatePicker ou autre format ? spinner date ou list de date ?
                //
                // code pour récup les saisies
                Intent back = new Intent();
                back.putExtra("cbArts", cbArts.isChecked());
                back.putExtra("cbSport", cbSport.isChecked());
                back.putExtra("wordDefault", searchactivity_text.getText() );

            }
        });




    }
}