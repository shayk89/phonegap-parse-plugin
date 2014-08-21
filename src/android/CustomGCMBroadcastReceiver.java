package dk.ekstrabladet;

import android.content.Context;

/*
import com.google.android.gcm.GCMBroadcastReceiver;
import static com.google.android.gcm.GCMConstants.DEFAULT_INTENT_SERVICE_CLASS_NAME;

public class CustomGCMBroadcastReceiver extends GCMBroadcastReceiver {
	
	@Override
	protected String getGCMIntentServiceClassName(Context context) {
    	return "dk.ekstrabladet.gcm" + DEFAULT_INTENT_SERVICE_CLASS_NAME;
    }
	
}
*/
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat.Builder;

import android.content.Intent;
import android.content.Context;
import java.util.Iterator;
import org.json.JSONObject;
import org.json.JSONException;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.os.Bundle;

public class CustomGCMBroadcastReceiver extends BroadcastReceiver {
private static final String TAG = "CustomGCMBroadcastReceiver";
 
  @Override
  public void onReceive(Context context, Intent intent) {
    try {
      String action = intent.getAction();
      String channel = intent.getExtras().getString("com.parse.Channel");
      JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
      Bundle extras = intent.getExtras();
 
      Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
      Iterator itr = json.keys();
      while (itr.hasNext()) {
        String key = (String) itr.next();
        Log.d(TAG, "..." + key + " => " + json.getString(key));
      }
      createNotification(context, extras);
    } catch (JSONException e) {
      Log.d(TAG, "JSONException: " + e.getMessage());
    }
  }

  public void createNotification(Context context, Bundle extras){
	NotificationCompat.Builder mBuilder =
	        new NotificationCompat.Builder(this)
	        .setSmallIcon(R.drawable.notification_icon)
	        .setContentTitle("My notification")
	        .setContentText("Hello World!");
	// Creates an explicit intent for an Activity in your app
	Intent resultIntent = new Intent(this, ResultActivity.class);

	// The stack builder object will contain an artificial back stack for the
	// started Activity.
	// This ensures that navigating backward from the Activity leads out of
	// your application to the Home screen.
	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	// Adds the back stack for the Intent (but not the Intent itself)
	stackBuilder.addParentStack(ResultActivity.class);
	// Adds the Intent that starts the Activity to the top of the stack
	stackBuilder.addNextIntent(resultIntent);
	PendingIntent resultPendingIntent =
	        stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_UPDATE_CURRENT
	        );
	mBuilder.setContentIntent(resultPendingIntent);
	NotificationManager mNotificationManager =
	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	// mId allows you to update the notification later on.
	mNotificationManager.notify(mId, mBuilder.build());  	
  }
}