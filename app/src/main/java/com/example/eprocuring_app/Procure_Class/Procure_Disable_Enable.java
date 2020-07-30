package com.example.eprocuring_app.Procure_Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Admin_folder.Admin_form;
import com.example.eprocuring_app.R;
import com.example.eprocuring_app.Restaurant_folder.List_0f_Supliers;
import com.example.eprocuring_app.Suplier_folder.Suppplier_Activity_page;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Procure_Disable_Enable {

private Context context;
    String status;
    Activity activity;
    int id;

    public Procure_Disable_Enable(Context context,Activity activity,int id) {
        this.context = context;
        this.id = id;
        this.activity = activity;
    }

    public void sendStatusrequest(final Button button, final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String success = object.getString("roja");
                            Toast.makeText(context, success, Toast.LENGTH_LONG).show();

                            if (success.contains("Enabled")){
                                status = "Disabled";
                                button.setText(status);
                                button.setBackgroundResource(R.color.red);
                            }else if (success.contains("Disabled")){
                                status = "Enabled";
                                button.setText(status);
                                button.setBackgroundResource(R.color.green);
                            }else {
                                Procure_Dialogues dialogues = new Procure_Dialogues(activity);
                                dialogues.register_text("Error","Do not contain Enabled or Disabled");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Aploaded error on catch "+e.toString(), Toast.LENGTH_LONG).show();
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
