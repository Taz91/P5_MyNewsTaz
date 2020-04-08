package com.syc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ResultSearchActivityTest {
    private ResultSearchActivity activity;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.buildActivity( ResultSearchActivity.class )
                .create()
                .resume()
                .get();
    }

    @Test
    public void clickBackActivity_noSearchLaunch(){
        //ResultSearchActivity activity = Robolectric.setupActivity(ResultSearchActivity.class);
        //ResultSearchActivity activity = Robolectric.   //.setupActivity(ResultSearchActivity.class);

    }

}
