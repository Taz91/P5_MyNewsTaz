package com.agilya.syc.tabbedactivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
//libérer ButterKnife dans gradle
//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //@BindView()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);

        //setTitle("Mon Titre");

        //1 - Configuring Toolbar
        //this.configureToolbar();





        // ======================================================================================================================= Mip RV
        final RecyclerView rv = findViewById(R.id.rvList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter());
        // =======================================================================================================================

//        Thread t = new Thread(){

//            public void run(){
//                TextView tv = (TextView)findViewById(R.id.TVtest);
//                RelativeLayout l = (RelativeLayout)findViewById(R.id.firstPageId);
//                l.removeView(tv);
//                ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar);
//                pb.setProgress(1);
//                try {
//                    Thread.sleep(1000);
//                }catch (InterruptedException ie){
//                    ie.printStackTrace();
//                }
//
//                pb.setProgress(2);
//                try {
//                    Thread.sleep(1000);
//                }catch (InterruptedException ie){
//                    ie.printStackTrace();
//                }
//
//                pb.setProgress(3);
//                try {
//                    Thread.sleep(1000);
//                }catch (InterruptedException ie){
//                    ie.printStackTrace();
//                }
//                pb.setProgress(4);
//
//            }
//        };
//        t.start();


        //String myTestURL = "https://api.nytimes.com/svc/topstories/v2/books.json?api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG";
//        String myTestURL = "https://api.github.com/users/JakeWharton/following";

//        try { Thread.sleep(1000);
//        }catch(InterruptedException ie){ie.printStackTrace();}

//        ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar);
//        pb.setVisibility(View.VISIBLE);
//        pb.setProgress(1);


//        TextView tv = (TextView)findViewById(R.id.TVtest);
//        tv.setText(myTestURL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_main_toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }

}