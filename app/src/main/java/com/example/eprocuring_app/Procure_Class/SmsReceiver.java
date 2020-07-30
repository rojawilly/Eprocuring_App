package com.example.eprocuring_app.Procure_Class;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.MainActivity;
import com.example.eprocuring_app.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";
    public static String smsNumber = "";
    public static String smsbody = "";

    Cursor cursor;
    Gson gson;

    {
        gson = new Gson();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
// Get the SMS message.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");

// Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);

//Sender number
       // String smsNumber = "";

        if (pdus != null) {
// Check the Android version.
            boolean isVersionM = (Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.M);

// Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
// Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
// If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
// If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
// Build the message to show.
                smsNumber = msgs[i].getOriginatingAddress();
                smsbody = msgs[i].getMessageBody();
                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                strMessage += " :" + msgs[i].getMessageBody() + "\n";
            }
// Log and display the SMS message.
            Log.d(TAG, "onReceive: " + strMessage);
            if(!strMessage.isEmpty()){
                Intent intent1 = new Intent(context,Background_Service.class);
                intent1.putExtra("sms",smsbody);
                intent1.putExtra("smn",smsNumber);
                context.startService(intent1);
//                notifications("roja obtained",context);
                Log.d(TAG, "onReceive: roja obtained from" + strMessage);
            }else {
                notifications("no roja",context);
                Log.d(TAG, "onReceive: " + smsNumber);
            }
        }
    }

    public static void notifications(String sms,Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentTitle("Eprocuring notification")
                .setContentText(sms)
                .setPriority(NotificationCompat.DEFAULT_SOUND);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
