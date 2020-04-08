package com.syc;
import android.os.Build;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static junit.framework.TestCase.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.KITKAT, manifest=Config.NONE)
public class MainActivityRoboTest {
    private MainActivity activity;

    @Before
    public void setUp() throws Exception{
        activity = Robolectric.buildActivity( MainActivity.class )
                .create()
                .resume()
                .get();
    }

    @Test
    public void activityNotBeNull() throws Exception {
        //assertNotNull(activity);
    }


    @Test
    public void clickBackActivity_noSearchLaunch(){
        //ResultSearchActivity activity = Robolectric.setupActivity(ResultSearchActivity.class);
        //ResultSearchActivity activity = Robolectric.   //.setupActivity(ResultSearchActivity.class);

    }
}
