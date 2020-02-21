package com.syc.utils;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Utils {
    public static SharedPreferences sharedPref;
    // =====================================================================
    //File Name
    private static final String PREFS_SETTING = "setting";
    //define key : apiKey
    private static final String PREFS_APIKEY = "apiKey";
    //ArticlesViewedKey
    private static final String PREFS_ARTICLESVIEWED = "articlesviewedKey";
    //Access apiKey
    private static String apiKey;
    //Articles viewed
    private static String sharedArticlesViewed;
    //TopStories Choice Category
    private static String sharedTopStoriesCategory;
    //Remove SharedPref
    private static Boolean bRemoveSharedPref;


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
    public static String convertDate(String pDate){
        SimpleDateFormat oldFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newFormatDate = new SimpleDateFormat("dd/MM/yyyy");

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

/*
        if(getSharedTopStoriesCategory() == null || getSharedTopStoriesCategory().isEmpty()){
            setSharedTopStoriesCategory("home");
        }
*/
        // reload with sharedPreferences modified
        sharedPref = context.getSharedPreferences(PREFS_SETTING, MODE_PRIVATE);
        return sharedPref;
    }

    /**
     * load Setting Notif and Search for activity
     * @param psearchActivityIntent
     */
    public static void loadSetting(Intent psearchActivityIntent ){
        apiKey = sharedPref.getString( PREFS_APIKEY , "" );
        if(sharedPref != null){
            psearchActivityIntent.putExtra("wordDefault", sharedPref.getString("wordDefault",""));
            psearchActivityIntent.putExtra("cbArts", sharedPref.getBoolean("cbArts", false));
            psearchActivityIntent.putExtra("cbBusiness", sharedPref.getBoolean("cbBusiness", false));
            psearchActivityIntent.putExtra("cbEntrepreneur", sharedPref.getBoolean("cbEntrepreneur", false));
            psearchActivityIntent.putExtra("cbPolitics", sharedPref.getBoolean("cbPolitics", false));
            psearchActivityIntent.putExtra("cbSport", sharedPref.getBoolean("cbSport", false));
            psearchActivityIntent.putExtra("cbTravel", sharedPref.getBoolean("cbTravel", false));
            psearchActivityIntent.putExtra("switchNotif", sharedPref.getBoolean("switchNotif", false));
            psearchActivityIntent.putExtra("articleViewed", sharedPref.getString("articleViewed", ""));
            psearchActivityIntent.putExtra("nbArticle", sharedPref.getInt("nbArticle", 30));
        }else {
            sharedPrefLoadDefault();
        }
    }

    public static String getSharedArticlesViewed() { return sharedArticlesViewed; }
    public static void setSharedArticlesViewed(String pArticlesViewed) {
        sharedArticlesViewed = sharedPref.getString(PREFS_ARTICLESVIEWED,"") + ":" + pArticlesViewed;

        sharedPref
                .edit()
                .putString("articleViewed", sharedArticlesViewed)
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
            .remove("articleViewed")
            .putString("apiKey","J0iJw0a8fdshubHztJsOJxEEg6hPstOG")
            .putString("articleViewed", "")
            .putString("wordDefault", "trump macron")
            .putBoolean("cbArts",false)
            .putBoolean("cbBusiness",true)
            .putBoolean("cbEntrepreneur",false)
            .putBoolean("cbPolitics",false)
            .putBoolean("cbSport",true)
            .putBoolean("cbTravel",false)
            .putBoolean("switchNotif",false)
            .putInt("nbArticle", 30)
            .putString("sharedTopStoriesCategory", "home")
            .putBoolean("bRemoveSharedPref", false)
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
                .remove("cbEntrepreneur")
                .remove("cbPolitics")
                .remove("cbSport")
                .remove("cbTravel")
                .remove("switchNotif")
                .remove("nbArticle")
                .remove("sharedTopStoriesCategory")
                .remove("sharedTopStoriesChoiceCategory")
                .remove("sTopStoriesCategory")
                .commit();
        setbRemoveSharedPref(false);
        sharedPrefLoadDefault();
    }
    // ============================================================================ Notifications utilities

    private final void createNotiifcation(){
        //Get NotificationManager:
        //final NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //NotificationManager notificationManagerNew = mContext.getSystemService(NotificationManager.class);
    }


}
