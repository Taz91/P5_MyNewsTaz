package com.syc;
import android.content.Context;
import android.content.SharedPreferences;
import com.syc.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilsTest {
    private final Context context = Mockito.mock(Context.class);
    private SharedPreferences sharedPref;
    //private SharedPreferences.Editor editor;

    @Before
    public void before() throws Exception {
        sharedPref = Mockito.mock(SharedPreferences.class);
        //Mockito.when(sharedPreferences.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
        //sharedPref = context.getSharedPreferences("sessionTest", Context.MODE_PRIVATE);
        //mockNotificationBuilderProvider = Mockito.mock(NotificationBuilderProvider.class);
        //mockNotificationBuilder = Mockito.mock(NotificationCompat.Builder.class, RETURNS_SELF);
        //mockNotifyManager = Mockito.mock(NotificationManager.class);
    }
    @Test
    public void utilsTopStoriesSection() throws Exception{

        String topSection = sharedPref.getString("sharedTopStoriesCategory","home");

        //data spinner - index and value - to preselect choice in spinner
        TreeMap<String,Integer> spinnerCategory = new TreeMap<>();
        spinnerCategory.put("arts",0);
        spinnerCategory.put("business",1);
        spinnerCategory.put("home",2);
        spinnerCategory.put("movies",3);
        spinnerCategory.put("politics",4);
        spinnerCategory.put("sports",5);
        spinnerCategory.put("travel",6);

        //construct list for spinner:
        List<String> spinnerList = new ArrayList<>();
        for(String s : spinnerCategory.keySet()){
            spinnerList.add(s);
        }
        assertTrue(spinnerList.contains(topSection));
        //assertTrue(spinnerList.contains("badPosibilty"));
    }

    @Test
    public void utilsConvertDate() throws Exception {
        String dateToConvert = Utils.convertDate("2020-05-15", "yyyy-MM-dd", "yyyy/MM/dd");
        assertEquals("2020/05/15",dateToConvert);
    }
}
