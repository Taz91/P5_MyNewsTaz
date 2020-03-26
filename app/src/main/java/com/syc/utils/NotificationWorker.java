package com.syc.utils;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;
import com.syc.R;
import com.syc.models.NotificationLowData;
import com.syc.models.NotificationResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.syc.utils.Utils.getApiKey;
import static com.syc.utils.Utils.getnNbHits;
import static com.syc.utils.Utils.loadSharedPreferences;
import static com.syc.utils.Utils.setnNbHits;
import static com.syc.utils.Utils.setsBeginDate;

public class NotificationWorker extends Worker {
    private static final String WORK_RESULT = "work_result";
    //public static final String MESSAGE_STATUS = "message_status";
    // =================================================================== shared_preferences :
    private SharedPreferences sharedPref;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        //TODO : à ce moment que contient taskData ????? et comment il est alimenté

        Data taskData = getInputData();
        String taskDataString = taskData.getString(WORK_RESULT);
        //get data from API NYT
        loadData(taskDataString);

        Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();

        //TODO : comment relancer si c KO ????
        //return Result.retry();
        return Result.success(outputData);
    }

    private void loadData(String ptaskDataString){
        // reload sharedPreferences and use for Default display
        sharedPref = loadSharedPreferences(getApplicationContext());
        //TODO : pourquoi cela fonctionne pas ????????????
        //String beginDate = sharedPref.getString("NotifLastDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));;
        String beginDate ="";
        beginDate = sharedPref.getString("NotifLastDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        if(TextUtils.isEmpty(beginDate)){
            beginDate  = new SimpleDateFormat("yyyyMMdd").format(new Date());
        }
        Utils.setsBeginDate(beginDate);

        //String endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String q = sharedPref.getString("qNotif", "");
        String fq = sharedPref.getString("fqNotif", "");
        String fl = "hits";

        //https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20200301&end_date=20200315&fl=hits&fq=Arts Business Movies Sports Travel Politcs&q=corona virus france&api-key=J0iJw0a8fdshubHztJsOJxEEg6hPstOG
        GetNewsDataService newsDataService = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
        Call<NotificationLowData> call = newsDataService.getNotifLowData( beginDate, fl ,fq, q ,getApiKey() );

        call.enqueue(new Callback<NotificationLowData>(){
            @Override
            public void onResponse(Call<NotificationLowData> call, Response<NotificationLowData> response) {
                if(response.isSuccessful()){
                    NotificationResponse result = response.body().getResponse();
                    //get nb of hits
                    Integer nHits = result.getMeta().getHits();
                    //put in sharedPref
                    setnNbHits(nHits);
                    // reload sharedPreferences and use for Default display
                    sharedPref = loadSharedPreferences(getApplicationContext());

                    if (getnNbHits()>0) {
                        //showNotification("New York Times", ptaskDataString != null ? ptaskDataString : "New actuality for you !!");
                        showNotification("New York Times",  getnNbHits().toString() + " New actuality for you !!");
                        //store new begin date for the next time
                        setsBeginDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                        //put message in taskDataString
                        //taskData. = "Notification launch !!";
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationLowData> call, Throwable t) {
                //TODO: gestion message de retour : soit le site est inaccessible
                Toast.makeText( getApplicationContext() , "Error : notification response", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showNotification(String title, String message) {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "nyt_channel";
        String channelName = "nyt_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            // Option of Notif :
            //channel.enableLights(true);
            //channel.enableVibration(true);
            //channel.setLockscreenVisibility( Notification.VISIBILITY_PUBLIC );

            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true) //when user tips on, notif is delete
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setSmallIcon(R.drawable.nyt_21x21); //ic_launcher
        manager.notify(1, builder.build());
    }
}
