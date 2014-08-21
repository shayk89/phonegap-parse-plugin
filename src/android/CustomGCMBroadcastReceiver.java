package dk.ekstrabladet.gcm;

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

import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

public class CustomGCMBroadcastReceiver extends BroadcastReceiver {
private static final String TAG = "CustomGCMBroadcastReceiver";
 
  @Override
  public void onReceive(Context context, Intent intent) {
    try {
      String action = intent.getAction();
      String channel = intent.getExtras().getString("com.parse.Channel");
      JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
 
      Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
      Iterator itr = json.keys();
      while (itr.hasNext()) {
        String key = (String) itr.next();
        Log.d(TAG, "..." + key + " => " + json.getString(key));
      }
    } catch (JSONException e) {
      Log.d(TAG, "JSONException: " + e.getMessage());
    }
  }
}