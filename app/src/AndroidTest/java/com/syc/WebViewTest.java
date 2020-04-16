package com.syc;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.core.content.ContextCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.runner.AndroidJUnitRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class WebViewTest {
    private String url;
    private WebView webView;

    @Test
    public void mainActivityClickOnImage(){
        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // test statement
        onView(withId(R.id.rvItemImg))          // withId(R.id.my_view) is a ViewMatcher
                .perform(click())               // click() is a ViewAction
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion

        // new test
        /*        onView(withId(R.id.greet_button))
                .perform(click())
                .check(matches(not(isEnabled()));
        */

    }

    // ====================================================================================== WebView
    @Test
    public void showWebView() {
        /*
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
        */
    }

    @Test
    public void webView_isVisible() throws Exception {
        assertNotNull(webView);
    }

    @Test
    public void webViewUrl_correctUrl() throws Exception {
        assertEquals(url, webView.getUrl());
    }

}
