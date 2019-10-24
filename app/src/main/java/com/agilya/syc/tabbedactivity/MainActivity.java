package com.agilya.syc.tabbedactivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setTitle("Mon Titre");
        // ======================================================================================================================= Mip RV
        final RecyclerView rv = findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter());

        // =======================================================================================================================

        //String myTestURL = "https://api.nytimes.com/svc/topstories/v2/books.json?api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG";
        String myTestURL = "https://api.github.com/users/JakeWharton/following";


    }
}