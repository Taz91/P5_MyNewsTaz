package com.syc.utils;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import com.syc.R;
import com.syc.models.NotificationResponse;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    public static SharedPreferences sharedPref;
    // =====================================================================
    //File Name
    private static final String PREFS_SETTING = "setting";
    //define key : apiKey
    private static final String PREFS_APIKEY = "apiKey";
    //ArticlesViewedKey
    private static final String PREFS_ARTICLESVIEWED = "articleViewed";
    //Access apiKey
    private static String apiKey;
    //Articles viewed
    private static String sharedArticlesViewed;
    //TopStories Choice Category
    private static String sharedTopStoriesCategory;
    //Remove SharedPref
    private static Boolean bRemoveSharedPref;
    //nb Articles Viewed
    private static Integer nArticlesMax;
    //nb Hits return
    private static Integer nNbHits;


    //TODO: SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //TODO: regarder quelle est la date qui arrive ... est ce long en millisecondes ?
    //TODO: si oui, prévoir la méthode en longToShortFR
    //TODO: si oui, prévoir la méthode en shortToLongUS
    //TODO:


    /**
     * format date "yyyy-MM-dd" in "dd/MM/yyyy"
     * @param pDate
     * @return
     */
    public static String convertDate(String pDate, String pOldFormat, String pNewFormat){
        SimpleDateFormat oldFormatDate = new SimpleDateFormat(pOldFormat);
        SimpleDateFormat newFormatDate = new SimpleDateFormat(pNewFormat);
        //SimpleDateFormat oldFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat newFormatDate = new SimpleDateFormat("dd/MM/yyyy");

        pDate = pDate.substring(0,10);
        try {
            pDate = newFormatDate.format(oldFormatDate.parse(pDate));
        }
        catch(Exception e){
            pDate = newFormatDate.format(new Date());
        }finally {
            return pDate;
        }
    }

    // ============================================================================ sharedPreferences utilities
    public static String getApiKey() { return apiKey; }
    public static void setApiKey(String apiKey) { Utils.apiKey = apiKey; }

    public static String getSharedTopStoriesCategory() { return sharedTopStoriesCategory; }
    public static void setSharedTopStoriesCategory(String p_sharedTopStoriesCategory) {
        Utils.sharedTopStoriesCategory = p_sharedTopStoriesCategory;
        sharedPref
            .edit()
            .putString("sharedTopStoriesCategory", sharedTopStoriesCategory)
            .commit();
    }

    public static Boolean getbRemoveSharedPref() { return bRemoveSharedPref; }
    public static void setbRemoveSharedPref(Boolean bRemoveSharedPref) { Utils.bRemoveSharedPref = bRemoveSharedPref; }

    public static Integer getnArticlesMax() { return nArticlesMax; }
    public static void setnArticlesMax(Integer nArticlesMax) {
        Utils.nArticlesMax = nArticlesMax;
        sharedPref
            .edit()
            .putInt("nbArticles", nArticlesMax)
            .commit();
    }

    public static void setsBeginDate(String pBeginDate) {
        sharedPref
            .edit()
            .putString("NotifLastDate", pBeginDate)
            .commit();
    }

    public static Integer getnNbHits(){return nNbHits;}
    public static void setnNbHits(Integer pHits){
        Utils.nNbHits = pHits;
        sharedPref
                .edit()
                .putInt("nNbHits", pHits)
                .commit();
    }


    /**
     * load sharedPreferences
     * @param context
     * @return
     */
    public static SharedPreferences loadSharedPreferences(final Context context){
        // load sharedPreferences and use for Default display
        sharedPref = context.getSharedPreferences(PREFS_SETTING, MODE_PRIVATE);
        if(sharedPref != null){
            setApiKey(sharedPref.getString( PREFS_APIKEY , "" ));
            if (getApiKey().isEmpty()) {
                sharedPrefLoadDefault();
                setApiKey(sharedPref.getString( PREFS_APIKEY , "" ));
            }
        }else {
            sharedPrefLoadDefault();
            setApiKey(sharedPref.getString( PREFS_APIKEY , "" ));
        }
        //Initialize TopStories Category
        setSharedTopStoriesCategory(sharedPref.getString("sharedTopStoriesCategory","home"));
        //Initialize nArticlesViewed
        setnArticlesMax(sharedPref.getInt("nbArticles", 30));
        // reload with sharedPreferences modified
        sharedPref = context.getSharedPreferences(PREFS_SETTING, MODE_PRIVATE);
        return sharedPref;
    }

    /**
     * load Setting Notif and Search for activity
     * @param psearchActivityIntent
     * @param pGet true to put sharedpref in intent, false to put intent in sharedpref
     */
    public static void loadSetting(Intent psearchActivityIntent, Boolean pGet ){
        apiKey = sharedPref.getString( PREFS_APIKEY , "" );
        if(sharedPref != null){
            if(pGet){
                psearchActivityIntent.putExtra("wordDefault", sharedPref.getString("wordDefault",""));
                psearchActivityIntent.putExtra("cbArts", sharedPref.getBoolean("cbArts", false));
                psearchActivityIntent.putExtra("cbBusiness", sharedPref.getBoolean("cbBusiness", false));
                psearchActivityIntent.putExtra("cbMovies", sharedPref.getBoolean("cbMovies", false));
                psearchActivityIntent.putExtra("cbPolitics", sharedPref.getBoolean("cbPolitics", false));
                psearchActivityIntent.putExtra("cbSport", sharedPref.getBoolean("cbSport", false));
                psearchActivityIntent.putExtra("cbTravel", sharedPref.getBoolean("cbTravel", false));
                psearchActivityIntent.putExtra("switchNotif", sharedPref.getBoolean("switchNotif", false));
                psearchActivityIntent.putExtra("articleViewed", sharedPref.getString("articleViewed", ""));
                psearchActivityIntent.putExtra("nbArticles", sharedPref.getInt("nbArticles", 30));
                psearchActivityIntent.putExtra("fqNotif", sharedPref.getString("fqNotif", ""));
                psearchActivityIntent.putExtra("qNotif", sharedPref.getString("qNotif", ""));
            }else {
                sharedPref
                    .edit()
                    .putString("wordDefault", psearchActivityIntent.getStringExtra("wordDefault"))
                    .putBoolean("cbArts",psearchActivityIntent.getBooleanExtra("cbArts",false))
                    .putBoolean("cbBusiness",psearchActivityIntent.getBooleanExtra("cbBusiness",false))
                    .putBoolean("cbMovies",psearchActivityIntent.getBooleanExtra("cbMovies",false))
                    .putBoolean("cbPolitics",psearchActivityIntent.getBooleanExtra("cbPolitics",false))
                    .putBoolean("cbSport",psearchActivityIntent.getBooleanExtra("cbSport",false))
                    .putBoolean("cbTravel",psearchActivityIntent.getBooleanExtra("cbTravel",false))
                    .putBoolean("switchNotif",psearchActivityIntent.getBooleanExtra("switchNotif",false))
                    .putString("fqNotif",psearchActivityIntent.getStringExtra("fqNotif"))
                    .putString("qNotif",psearchActivityIntent.getStringExtra("qNotif"))
                    .commit();
            }
        }else {
            sharedPrefLoadDefault();
        }
    }

    public static String getSharedArticlesViewed() { return sharedArticlesViewed; }
    public static void setSharedArticlesViewed(String pArticlesViewed) {
        if(sharedPref.getString(PREFS_ARTICLESVIEWED,"").isEmpty()){
            sharedArticlesViewed = sharedPref.getString(PREFS_ARTICLESVIEWED,"") + pArticlesViewed;
        }else{
            //Get all artilces viewed
            sharedArticlesViewed = sharedPref.getString(PREFS_ARTICLESVIEWED,"") + ":" + pArticlesViewed;
            //get limite of nb articles storage
            Integer nbArticlesSave = getnArticlesMax();//sharedPref.getInt("nbArticles",30) ;
            //Explode in list, to delete over limit
            List<String> listArticlesViewed = Arrays.asList(sharedArticlesViewed.split(":"));
            if( listArticlesViewed.size() > nbArticlesSave ){
                //Delete element are not necessary, low index (first in, first out)
                Integer beginIndex = listArticlesViewed.size()-nbArticlesSave;;
                listArticlesViewed = listArticlesViewed.subList( beginIndex , listArticlesViewed.size() );
                //build new sharedPref article viewed
                //newSharedArticlesViewed = String.join(":",listArticlesViewed);
                String newSharedArticlesViewed = "";
                for (Iterator ite = listArticlesViewed.subList( beginIndex , listArticlesViewed.size() ).iterator(); ite.hasNext(); ){
                    if(newSharedArticlesViewed.isEmpty()){
                        newSharedArticlesViewed = ite.next().toString();  // ite.next().toString()
                    }else{
                        newSharedArticlesViewed += ":" + ite.next().toString();
                    }
                }
                sharedArticlesViewed = newSharedArticlesViewed;
            }
        }
        sharedPref
                .edit()
                .putString(PREFS_ARTICLESVIEWED, sharedArticlesViewed)
                .commit();
    }

    /**
     * return true if Article is viewed
     * @param pArticle
     * @return
     */
    public static boolean isArticleViewed(String pArticle){

        return (getSharedArticlesViewed().indexOf(pArticle) !=-1 ) ? true : false;
    }

    /**
     * Initiate sharedPreferences
     *
     */
    private static void sharedPrefLoadDefault(){
        sharedPref
            .edit()
            .putString("apiKey","J0iJw0a8fdshubHztJsOJxEEg6hPstOG")
            .putString("articleViewed", "")
            .putString("wordDefault", "french paris")
            .putBoolean("cbArts",false)
            .putBoolean("cbBusiness",true)
            .putBoolean("cbMovies",false)
            .putBoolean("cbPolitics",false)
            .putBoolean("cbSport",true)
            .putBoolean("cbTravel",false)
            .putBoolean("switchNotif",false)
            .putInt("nbArticles", 30)
            .putString("sharedTopStoriesCategory", "home")
            .putBoolean("bRemoveSharedPref", false)
            .putString("NotifLastDate","")
            .putInt("NotifNbOf", 0)
            .putInt("nNbHits", 0)
            .putString("fqNotif","")
            .putString("qNotif","french paris")
            .commit();
    }

    /**
     * Remove sharedPreferences
     *
     */
    public static void sharedPrefRemove(){
        sharedPref
                .edit()
                .remove("apiKey")
                .remove("articleViewed")
                .remove("bRemoveSharedPref")
                .remove("wordDefault")
                .remove("cbArts")
                .remove("cbBusiness")
                .remove("cbMovies")
                .remove("cbPolitics")
                .remove("cbSport")
                .remove("cbTravel")
                .remove("switchNotif")
                .remove("nbArticles")
                .remove("sharedTopStoriesCategory")
                .remove("NotifLastDate")
                .remove("NotifNbOf")
                .remove("nNbHits")
                .remove("fqNotif")
                .remove("qNotif")
                .commit();
        setbRemoveSharedPref(false);
        sharedPrefLoadDefault();
    }

}
