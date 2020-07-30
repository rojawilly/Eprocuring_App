package com.example.eprocuring_app.Admin_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Procure_Class.Procure_Dialogues;
import com.example.eprocuring_app.Procure_Class.Procure_Disable_Enable;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.R;
import com.example.eprocuring_app.Restaurant_folder.List_0f_Supliers;
import com.example.eprocuring_app.Suplier_folder.Suppplier_Activity_page;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Enable_Disable_Dupplier extends AppCompatActivity {
    Button status;
    BufferedInputStream bufferedInputStream;
    TextView storesupplier,suppliertitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable__disable__dupplier);

        sendloginrequest(Procure_Url.Details_URL);
//        new Backgroundruning().execute();


        Toast.makeText(this,""+getid()+"", Toast.LENGTH_LONG).show();
        status = findViewById(R.id.status_btn);
        storesupplier = findViewById(R.id.storesupplier);
        suppliertitle = findViewById(R.id.suppliertitle);

    }

    public void status_btn(View view){
        Procure_Disable_Enable disable_enable = new Procure_Disable_Enable(this,Enable_Disable_Dupplier.this,getid());
        disable_enable.sendStatusrequest(status,Procure_Url.Status_URL);
    }

    public int getid(){
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("message");
        return id;
    }

    public void sendloginrequest(final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String details = "";

                        String statusdata = "";
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                String name1 = object.getString("user_name");
                                String email = object.getString("email");
                                String phone  = object.getString("phone_no");
                                statusdata = object.getString("status");
                                String location = object.getString("location");

                                details = "Name :" + name1 + "\n" + "Email :" + email + "\n" + "Phone no. " + location + "\n" + "Location :" + phone + "\n" + "Status :" + statusdata;
                            }
                            if (statusdata.equals("Enabled")){
                                status.setText(statusdata);
                                status.setBackgroundResource(R.color.green);
                            }else {
                                status.setText(statusdata);
                                status.setBackgroundResource(R.color.red);
                            }

                            storesupplier.setText(details);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Aploaded error on responce "+error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",""+getid()+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}

