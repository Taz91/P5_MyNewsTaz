package com.agilya.syc.tabbedactivity.Controllers.Activities;

import android.os.Bundle;

//import com.openclassrooms.netapp.Controllers.Fragments.MainFragment;
//import com.openclassrooms.netapp.R;

import com.agilya.syc.tabbedactivity.Controllers.Fragments.MainFragment;
import com.agilya.syc.tabbedactivity.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureAndShowMainFragment();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureAndShowMainFragment(){

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, mainFragment)
                    .commit();
        }
    }
}