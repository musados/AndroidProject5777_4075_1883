package com.siduron.java.iTravel.Model.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Controller.MainActivity;
import com.siduron.java.iTravel.Model.Backend.BackendFactor;
import com.siduron.java.iTravel.Model.Backend.IBackEnd;
import com.siduron.java.iTravel.Model.Entities.DBType;

import java.util.Date;
import java.util.logging.Level;

/**
 * Created by musad on 29/01/2017.
 */

public class TravelService extends Service {

    //Last checked for update - checks if one of list type was changed
    //and need to notify user or service (Broadcast receiver)
    private Date lastActivitiesChecked=new Date();
    private Date lastBussinessChecked=new Date();

    private boolean serviceRunning=false;

    private IBackEnd dbManager = BackendFactor.getIBackend(DBType.LIST);

    private final String TAG="iTravel service";

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.w(TAG,"Service was created!!!");

        serviceRunning=true;

        Toast.makeText(this,"Service was created!",Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "onStartCommand. sum is:" + 1, Toast.LENGTH_LONG).show();
        Log.w(TAG,"Service was started!!!");

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    if (dbManager.isActivitiesChanged(lastActivitiesChecked)) {

                        Log.w(TAG, "Activities changed!");
                    }
                    if (dbManager.isBussinessChanged(lastBussinessChecked)) {
                        Log.w(TAG, "Bussinesses changed!");

                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Log.e(TAG,e.getMessage());
                    }

                    notifyUser("i-Travel Service","Service is running: "+serviceRunning);
                        Log.w(TAG, "is running: " + serviceRunning);
                }

            }
        }).start();

        return START_STICKY;
    }

    private void notifyUser(String title,String message) {
        // prepare intent which is triggered if the
        // notification is selected

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "The Service was destroyed ...", Toast.LENGTH_LONG).show();
       Log.d(TAG," onDestroy service");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
