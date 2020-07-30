package com.example.eprocuring_app.Procure_Class;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Restaurant_folder.Raustarant_Activities_page;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class sup_details {
private Context context;
 String phone_no;

    public sup_details(Context context,String phone_no) {
        this.context = context;
        this.phone_no = phone_no;
    }

    public void Supplier_list(final TextView textView, final int id , final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        try {

                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            String result = "";
                            for (int i = 0; i<array.length();i++) {
                                object = array.getJSONObject(i);
                                String user_name = object.getString("user_name");
                                String emails = object.getString("email");
                                phone_no = object.getString("phone_no");
                                String location = object.getString("location");
//                                description = object.getString("description");
                                result = "Name :"+ user_name + "\nLocation :" + location + "\nPhone no. "+phone_no + "\nEmail :"+emails;
                            }
                            textView.setText(result);
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
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
