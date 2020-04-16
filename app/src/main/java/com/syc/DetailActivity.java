package com.syc;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
//import android.view.View;
import com.google.android.material.appbar.AppBarLayout;

/**
 * show article in webView
 * add articles in sharedPreferences like ArticlesViewed
 */
public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.detailactivity_appbarlayout) AppBarLayout detailactivity_appbarlayout;
    @BindView(R.id.detailactivity_toolbar) Toolbar detailactivity_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(detailactivity_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String url = getIntent().getStringExtra("articleUrl");
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }

}
