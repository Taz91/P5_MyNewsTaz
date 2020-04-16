package com.syc;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.webkit.WebView;
import com.syc.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import androidx.core.content.ContextCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.viewpager.widget.ViewPager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 19)
public class MainActivityUnitTest {
    private MainActivity activity;
    private SharedPreferences sharedPref;
    private ViewPager viewPager;

    private String url;
    private WebView webView;
    private DetailActivity detailActivity;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        activity = Robolectric.buildActivity(MainActivity.class).create().resume().get();
        viewPager = (ViewPager) activity.getWindow().findViewById(R.id.view_pager);
        sharedPref = Utils.sharedPref;
    }

    @Test
    public void activityShouldNotBeNull()throws Exception{
        assertNotNull(activity);
    }

    @Test
    public void menuSearchIsShown() throws Exception {
        //activity.onOptionsItemSelected(new RoboMenuItem(R.id.menu_main_activity_search));
        //activity.onOptionsItemSelected(new RoboMenuItem(R.menu.menu_main));
        //assertNotNull(activity.findViewById(R.id.menu_search).isEnabled());

        MenuItem menuItem = new RoboMenuItem(R.menu.menu_main);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        //assertEquals(true, shadowActivity.fini);

        //assert that `Settings` MenuItem is visible
        //assertEquals(shadowActivity.getOptionsMenu().findItem(R.id.menu_help).isVisible(), true);
        //assert that `Sort` MenuItem is visible

        shadowActivity.clickMenuItem(R.menu.menu_main);
        Boolean bOk =  activity.findViewById(R.id.menu_search).isEnabled();

        String titre = shadowActivity.getOptionsMenu().getItem(0).getTitle().toString();


        assertEquals(shadowActivity.getOptionsMenu().getItem(0).getTitle(), activity.getString(R.string.Notifications));
        //android:title="@string/Notifications"
        //android:title="@string/help"
        //android:title="@string/about"
    }

    @Test
    public void menuHelpIsShown() throws Exception {
        assertNotNull(activity.findViewById(R.id.menu_help));
    }


    // ====================================================================================== WebView
    @Test
    public void showWebView() {
        url = "http://google.com";
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("articleUrl", url);
        detailActivity = Robolectric.buildActivity(DetailActivity.class, intent).create().resume().get();
        ContextCompat.startActivity(detailActivity, intent, null);
        webView = detailActivity.findViewById(R.id.search_news);

        //
        assertNotNull(webView);
        //
        assertEquals(url, webView.getUrl());
    }

}
