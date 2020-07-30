package com.example.eprocuring_app.Procure_Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Get_Supplier_id_forProduct {
    Context context;
    Activity activity;
    Class aclass;
    EditText name;

    public Get_Supplier_id_forProduct(EditText name ,Context context, Activity activity,Class aclass) {
        this.context = context;
        this.activity = activity;
        this.aclass = aclass;
        this.name = name;
    }

    public void sendProductIdrequest(final String user_name, final String password, final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String user = name.getText().toString();
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                Intent intent = new Intent(activity,aclass);
                                intent.putExtra("id",id);
                                intent.putExtra("user",user);
                                context.startActivity(intent);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
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
                params.put("user_name",user_name);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
