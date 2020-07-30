package com.example.eprocuring_app.Restaurant_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Procure_Class.Order_Adapter;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.Procure_Class.Rest_placed_order;
import com.example.eprocuring_app.R;
import com.example.eprocuring_app.Suplier_folder.Today_order_details;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Placed_order extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);
        listView = findViewById(R.id.orddet);

        getprod_det_requst(Procure_Url.get_placed_order_URL);
    }

    public void getprod_det_requst(final String URL) {

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String details = "";
                        String[] name, user, quantity, status, amount, date, descriptions, image_url;
                        int[] id,rest_id,supid;

                        try {

                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            name = new String[array.length()];
                            quantity = new String[array.length()];
                            status = new String[array.length()];
                            amount = new String[array.length()];
                            date = new String[array.length()];
                            descriptions = new String[array.length()];
                            image_url = new String[array.length()];
                            id = new int[array.length()];
                            rest_id = new int[array.length()];
                            user = new String[array.length()];
                            supid = new int[array.length()];

                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                supid[i] = object.getInt("user_id");
                                rest_id[i] = object.getInt("rest_id");
                                id[i] = object.getInt("id");
                                name[i] = object.getString("name");
                                user[i] = object.getString("user");
                                quantity[i] = object.getString("quantity");
                                status[i] = object.getString("status");
                                amount[i] = object.getString("amount");
                                date[i] = object.getString("date");
                                descriptions[i] = object.getString("descriptions");
                                image_url[i] = "http://" + Procure_Url.ip + "/ProcureApload/uploads/Image/" + object.getString("name") + ".png";

                            }
                            Rest_placed_order adapter = new Rest_placed_order(Placed_order.this,getrestid(),name);
                            listView.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Aploaded error on responce on getprod" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", "" + getrestid() + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

//    public void getsup_det_requst(final int[] restid,final String[] user,final int[] supid,final int[] id,final String[] name,final String[] quantity,final String[] status,final String[] amount,final String[] date,final String[] descriptions,final String[] image_url,final String URL) {
//
//        StringRequest request = new StringRequest(Request.Method.POST, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        String sup_name;
//                        String locat = "",sup_no = "";
//
//                        try {
//
//                            JSONArray array = new JSONArray(response);
//                            JSONObject object = null;
//                            sup_name = "";
//                            int orderNo = 0;
//                            for (int i = 0; i < array.length(); i++) {
//                                object = array.getJSONObject(i);
//                                locat = object.getString("location");
//                                sup_no = object.getString("phone_no");
//                                sup_name = object.getString("user_name");
//                                orderNo++;
//                            }
//
//                            Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_LONG).show();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(),"catch on getsup "+response+ e.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Aploaded error on responce " + error.toString(), Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("id",  "64");
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }

    public int getsupid() {
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("supid");
        return id;
    }

    public int getrestid() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("restid");
    }
}
