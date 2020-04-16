package com.syc.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;
import com.syc.models.NotificationLowData;
import com.syc.models.NotificationResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.annotation.NonNull;
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
import static com.syc.utils.Utils.showNotification;

/**
 * Created by Chazette Sylvain
 * worker for sending notification by day if number of result greater than 0
 * notification content a title and a message with numbers of hits
 * notification is launched at 9 p.m.
 */
public class NotificationWorker extends Worker {
    private static final String WORK_RESULT = "work_result";
    private SharedPreferences sharedPref;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data taskData = getInputData();
        String taskDataString = taskData.getString(WORK_RESULT);
        //get data from API NYT
        loadData(taskDataString);

        Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();
        //return Result.retry();
        return Result.success(outputData);
    }

    /**
     * initializes the consumption of the API
     * service parameter : q (words), fq(sections), fl(API return only hits), begindate(today or the last day of notification)
     * if hits greater than 0 then notification is sending
     * @param ptaskDataString
     */
    private void loadData(String ptaskDataString){
        // reload sharedPreferences and use for Default display
        sharedPref = loadSharedPreferences(getApplicationContext());
        String beginDate ="";
        beginDate = sharedPref.getString("NotifLastDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        if(TextUtils.isEmpty(beginDate)){
            beginDate  = new SimpleDateFormat("yyyyMMdd").format(new Date());
        }
        Utils.setsBeginDate(beginDate);
        //TODO: uncomment for test
        //Utils.setnNbNotif(getnNbNotif()+1);

        String q = sharedPref.getString("qNotif", "");
        String fq = sharedPref.getString("fqNotif", "");
        String fl = "hits";

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
                    sharedPref = loadSharedPreferences( getApplicationContext());

                    if (getnNbHits()>0) {
                        //TODO: uncomment for test
                        //showNotification("New York Times",  getnNbHits().toString() + " New actuality for you !!  : " + getnNbNotif().toString() );
                        //TODO: comment for test
                        showNotification("New York Times",  getnNbHits().toString() + " New actuality for you !!", getApplicationContext() );
                        //store new begin date for the next time
                        setsBeginDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                        //put message in taskDataString
                        //taskData. = "Notification launch !!";
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationLowData> call, Throwable t) {
                Toast.makeText( getApplicationContext() , "Error : notification response", Toast.LENGTH_LONG).show();
            }
        });
    }

}
