package com.example.eprocuring_app.Procure_Class;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Suplier_folder.Add_Product;

import java.util.HashMap;
import java.util.Map;


public class Procure_to_server {
    private Activity activity;
    private Context context;

    public Procure_to_server(Context context){
        this.context = context;
    }

    public  void request_infor_toserver(final long longtime,final String time,final String deliver, final int rest_id, final String sup_status, final String name,
                                        final String password,final String email, final String location, final String phone,
                                        final String description, final String role, final String status, String URL){
        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Uploaded error on response "+error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("password",password);
                params.put("email",email);
                params.put("location",location);
                params.put("phone",phone);
                params.put("description",description);
                params.put("role",role);
                params.put("status",status);
                params.put("rest_id",""+rest_id+"");
                params.put("sup_status",sup_status);
                params.put("deliver",deliver);
                params.put("time",time);
                params.put("longtime",""+longtime+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public  void update_ordered(final long longtime,final String time,final String date, final int prodid, String URL){
        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();

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
                params.put("id",""+prodid+"");
                params.put("date",date);
                params.put("time",time);
                params.put("longtime",""+longtime+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public  void buy_after_order(final long longtime,final String time,final String deliver, final int rest_id, final String sup_status, final String name,
                                 final String password, final String email, final String location, final String phone,
                                 final String description, final int role, final String status, String URL){
        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();

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
                params.put("name",name);
                params.put("password",password);
                params.put("email",email);
                params.put("location",location);
                params.put("phone",phone);
                params.put("description",description);
                params.put("role",""+role+"");
                params.put("status",status);
                params.put("rest_id",""+rest_id+"");
                params.put("sup_status",sup_status);
                params.put("deliver",deliver);
                params.put("time",time);
                params.put("longtime",""+longtime+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
