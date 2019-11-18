package com.agilya.syc.tabbedactivity;

import android.os.Bundle;

import com.agilya.syc.tabbedactivity.archives.RestClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //retro compatibilté
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //btn en bas
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        content = findViewById(R.id.rv_list);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        content.setLayoutManager(verticalLayoutManager);

        loadData();
    }

    private void loadData() {
        NewsService client = RetrofitInstance.getRetrofitInstance().create(NewsService.class);

        client.getNewResult().enqueue(new Callback<NewResult>(){
            @Override
            public void onResponse(Call<NewResult> call, Response<NewResult> response) {
                Toast.makeText(MainActivity.this, "Yesss c'est ok", Toast.LENGTH_LONG).show();

                List<Result> result = response.body().getResults();

                MyAdapter adapter = new MyAdapter(result);
                content.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<NewResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Une erreur", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_notifications) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
