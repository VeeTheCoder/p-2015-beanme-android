package com.beanmeapp.beanme.helpers;

import com.beanmeapp.beanme.R;
import com.beanmeapp.beanme.activities.DrinkDetails;
import com.beanmeapp.beanme.activities.MainMenu;
import com.beanmeapp.beanme.activities.ViewDrinksList;
import com.beanmeapp.beanme.databaseclasses.Drink;
import com.beanmeapp.beanme.parsehelper.ParseToolbox;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Receiver extends ParsePushBroadcastReceiver {

    public Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.e("RECEIVER", "received");
        
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(intent.getExtras().get("com.parse.Data").toString());
            String purpose = jsonObject.getString(ParseToolbox.PURPOSE);
            if (purpose.equals(ParseToolbox.NOTIFY_GROUP)) {
                
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("BeanMe")
                        .setContentText(jsonObject.getString("message"))
                        .setAutoCancel(true);
                Intent resultIntent = new Intent(context.getApplicationContext(),
                        DrinkDetails.class);
                resultIntent.putExtra("hostid", jsonObject.getString("hostid"));
                PendingIntent resultPendingIntent = PendingIntent
                        .getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) context
                                .getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
                
            } else if (purpose.equals(ParseToolbox.SEND_DRINK)) {
                
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("BeanMe")
                        .setContentText("You have received another drink order.")
                        .setAutoCancel(true);
                Intent resultIntent = new Intent(context.getApplicationContext(),
                        ViewDrinksList.class);
                PendingIntent resultPendingIntent = PendingIntent
                        .getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) context
                                .getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
                String name = jsonObject.getString("name");
                Drink drink = Drink.inflate((String) jsonObject.get("drink"));
                MainMenu.myRun.putDrink(name, drink);
                Log.e("DRINK DETAILS", drink.toString());
            } else if (purpose.equals(ParseToolbox.GROUP_INVITE)) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("BeanMe")
                        .setContentText("Group Invitation")
                        .setAutoCancel(true);
                NotificationManager mNotificationManager =
                        (NotificationManager) context
                                .getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
                ParseToolbox.subscribeToGroup(jsonObject.getString("groupId"), jsonObject.getString("groupName"));
            }
        } catch (JSONException e) {
            Log.e("Receiver", e.getMessage());
        }
    }

}
