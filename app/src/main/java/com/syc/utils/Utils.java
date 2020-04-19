package com.syc.utils;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import com.syc.R;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Chazette Sylvain
 * contains :
 * sharedPreferences management, CRUD logical
 * method to convert format date
 * method to add articleViewed in sharedPref,
 * method to load setting of Notification Activity (search with notification option)
 * method to find if current article (in all RecyclerView) is viewed
 * method to remove and preload sharedPref
 */
public class Utils {
    public static SharedPreferences sharedPref;
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
    //nb Notif return
    private static Integer nNbNotif;

    /**
     * convert string date with old format and return the new format
     * @param pDate
     * @param pOldFormat example : "yyyy-MM-dd"
     * @param pNewFormat example : "dd/MM/yyyy"
     * @return
     */
    public static String convertDate(String pDate, String pOldFormat, String pNewFormat){
        SimpleDateFormat oldFormatDate = new SimpleDateFormat(pOldFormat);
        SimpleDateFormat newFormatDate = new SimpleDateFormat(pNewFormat);

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

    public static Integer getnNbNotif(){return nNbNotif;}
    public static void setnNbNotif(Integer pNotif){
        Utils.nNbNotif = pNotif;
        sharedPref
                .edit()
                .putInt("nbNotif", pNotif)
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
        //Initialize ArticlesViewed
        setSharedArticlesViewed(sharedPref.getString("articleViewed",""));
        //Initialize nbNotif
        setnNbNotif(sharedPref.getInt("nbNotif",0));
        //Initialize BeginDate
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -5);
        if(sharedPref.getString("NotifLastDate","").isEmpty()){
            setsBeginDate(new SimpleDateFormat("yyyyMMdd").format(cal.getTime()));
        }
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
        sharedArticlesViewed = pArticlesViewed;
        sharedPref
                .edit()
                .putString(PREFS_ARTICLESVIEWED, sharedArticlesViewed)
                .commit();
    }

    public static String addSharedArticlesViewed(String psharedArticlesViewed, String pArticleViewed, Integer nbArticlesSave) {
        if(psharedArticlesViewed.isEmpty()){
            psharedArticlesViewed += pArticleViewed;
        }else{
            //Get all artilces viewed
            psharedArticlesViewed = psharedArticlesViewed + ":" + pArticleViewed;
            //Explode in list, to delete over limit
            List<String> listArticlesViewed = Arrays.asList(psharedArticlesViewed.split(":"));

            if( listArticlesViewed.size() > nbArticlesSave ){
                //Delete element are not necessary, low index (first in, first out)
                Integer beginIndex = listArticlesViewed.size()-nbArticlesSave;;
                listArticlesViewed = listArticlesViewed.subList( beginIndex , listArticlesViewed.size() );
                //build new sharedPref article viewed
                //newSharedArticlesViewed = String.join(":",listArticlesViewed);
                String newSharedArticlesViewed = "";
                for (Iterator ite = listArticlesViewed.subList( 0 , listArticlesViewed.size() ).iterator(); ite.hasNext(); ){
                    if(newSharedArticlesViewed.isEmpty()){
                        newSharedArticlesViewed = ite.next().toString();  // ite.next().toString()
                    }else{
                        newSharedArticlesViewed += ":" + ite.next().toString();
                    }
                }
                psharedArticlesViewed = newSharedArticlesViewed;
            }
        }
        return psharedArticlesViewed;
    }

    /**
     * return true if Article is viewed
     * @param pArticle
     * @return
     */
    public static boolean isArticleViewed(String pArticle, String pArticles){
        return (pArticles.indexOf(pArticle) !=-1 ) ? true : false;
    }

    /**
     * Initiate sharedPreferences
     */
    public static SharedPreferences sharedPrefLoadDefault(){
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
            .putInt("nbNotif",0)
            .commit();
        return sharedPref;
    }

    /**
     * Remove sharedPreferences
     */
    public static SharedPreferences sharedPrefRemove(){
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
                .remove("nbNotif")
                .commit();
        return sharedPref;
        //setbRemoveSharedPref(false);
        //sharedPrefLoadDefault();
    }

    /**
     * to create a Periodic notification
     * @param pbGoNotif
     * @param pcontext
     */
    public static void creatNotification(boolean pbGoNotif, Context pcontext){
        //pcontext.getApplicationContext();
        WorkManager mWorkManager = WorkManager.getInstance(pcontext);

        if( pbGoNotif){
            Constraints contraintes = new Constraints.Builder ()
                    .setRequiresBatteryNotLow(true)
                    .build ();
                    //.setRequiresCharging (true)
            /*
            // ================================================= One time request !!
            OneTimeWorkRequest mRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                    .setInitialDelay(5,TimeUnit.MINUTES)
                    .build();
            //pull unique job in queue
            mWorkManager.enqueueUniqueWork("nyt_periodic", ExistingWorkPolicy.REPLACE, mRequest);
            */

            // ================================================= Periodic request !!
            //Get delay duration in minute
            Long delay = Utils.buildDelayDuration();

            PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(
                    NotificationWorker.class,
                    15,
                    TimeUnit.MINUTES,
                    PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
                    TimeUnit.MILLISECONDS)
                    .setInitialDelay(delay,TimeUnit.MINUTES)
                    .build();
            //pull periodic job in queue
            mWorkManager.enqueueUniquePeriodicWork("nyt_periodic", ExistingPeriodicWorkPolicy.REPLACE, mRequest);

        }else{
            //mWorkManager.cancelAllWorkByTag("nyt_channel");
            mWorkManager.cancelUniqueWork("nyt_periodic");
        }
    }

    /**
     * launch notification with options, message content number of hits
     * @param title : name of application
     * @param message : personnal message like xx actuality for you
     * @param pcontext : context of activity
     */
    public static void showNotification(String title, String message, Context pcontext) {
        //pcontext.getApplicationContext();
        NotificationManager manager = (NotificationManager) pcontext.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "nyt_channel";
        String channelName = "nyt_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            // Option of Notif :
            //channel.enableLights(true);
            //channel.setLightColor(Color.RED);
            //channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            //channel.enableVibration(true);
            //channel.setShowBadge( true );
            //channel.setLockscreenVisibility( Notification.VISIBILITY_PUBLIC );

            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(pcontext, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true) //when user tips on, notif is delete
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setSmallIcon(R.mipmap.nyt_21x21);
        manager.notify(1, builder.build());
    }

    /**
     * @return Calendar, target date launch notification
     */
    public static Long buildDelayDuration(){
        Calendar dCalDateDebut = Calendar.getInstance();
        Calendar dCalDateFin = Calendar.getInstance();
        dCalDateFin.set(Calendar.HOUR_OF_DAY, 9);
        dCalDateFin.set(Calendar.MINUTE, 0);
        dCalDateFin.set(Calendar.SECOND, 0);
        dCalDateFin.set(Calendar.DAY_OF_MONTH, dCalDateDebut.get(Calendar.DAY_OF_MONTH));

        long diffMillis = (dCalDateFin.getTimeInMillis() - dCalDateDebut.getTimeInMillis())/60/1000;

        //Calculate difference between now and target time
        if(diffMillis < 5){
            dCalDateFin.add(Calendar.DATE,1);
        }
        diffMillis = (dCalDateFin.getTimeInMillis() - dCalDateDebut.getTimeInMillis())/60/1000;

        return diffMillis;
    }

}
