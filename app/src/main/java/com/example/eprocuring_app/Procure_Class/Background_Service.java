package com.example.eprocuring_app.Procure_Class;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Background_Service extends IntentService {

    public Background_Service() {
        super("name");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

//
//        if (intent == null){
//            String sms = intent.getStringExtra("sms");
//                try {
//                    Thread.sleep(300);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
////            if (sms.contains("roja")){
//                SmsReceiver.notifications("roja found",this);
////            }
//
//            return;
//        }

            String sms = intent.getStringExtra("sms");
          String smn = intent.getStringExtra("smn");
            if (sms.contains("roja")) {
                SmsReceiver.notifications("roja pay at :"+realtime(), this);
                getrestidrequest("0620522562",this,Procure_Url.phone_no_comparison_URL);
//                autopayrequest(proid,realtime(),realdate(),this,Procure_Url.automatic_payment_URL);
                try {
                    Thread.sleep(100000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    public void getrestidrequest(final String phone, final Context context , final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                id = object.getInt("id");
                            }
                            autopayrequest(id,realtime(),realdate(),context,Procure_Url.automatic_payment_URL);
                            Toast.makeText(context, ""+id+"", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Aploaded error on catch order "+response+""+id+""+e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Aploaded error on responce "+error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",phone);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void autopayrequest(final int id, final String time, final String date, final Context context , final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
//                            JSONArray array = new JSONArray(response);
//                            JSONObject object = null;
//                            for (int i = 0; i<array.length();i++){
//                                object = array.getJSONObject(i);
//                            }
                            Toast.makeText(context,response, Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Aploaded error on autopay catch order "+e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Aploaded error on responce "+error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",""+id+"");
                params.put("time",time);
                params.put("date",date);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public String realtime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time = format.format(date);
        return time;
    }
    public String realdate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd:MMM:yy");
        String time = format.format(date);
        return time;
    }
}
