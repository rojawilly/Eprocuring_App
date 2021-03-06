package com.example.eprocuring_app.Suplier_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.eprocuring_app.Procure_Class.Procure_Array_Adapter;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Today_order_details extends AppCompatActivity {

    ListView listView;
//    String loc = no.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_order_details);
        listView = findViewById(R.id.orddet);

        getorderlisrequst(Procure_Url.getorder_URL);
    }

    public void getorderlisrequst(final String URL) {

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String details = "";
                        String[] name, user, quantity, status, amount, date, descriptions, image_url;
                        int[] id;
                        Bundle bundle = getIntent().getExtras();
                        String no = bundle.getString("no");
                        String locat = bundle.getString("location");

                        try {

                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            name = new String[array.length()];
                            user = new String[array.length()];
                            quantity = new String[array.length()];
                            status = new String[array.length()];
                            amount = new String[array.length()];
                            date = new String[array.length()];
                            descriptions = new String[array.length()];
                            image_url = new String[array.length()];
                            id = new int[array.length()];

                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                id[i] = object.getInt("id");
                                user[i] = object.getString("user");
                                name[i] = object.getString("name");
                                quantity[i] = object.getString("quantity");
                                status[i] = object.getString("status");
                                amount[i] = object.getString("amount");
                                date[i] = object.getString("date");
                                descriptions[i] = object.getString("descriptions");
                                image_url[i] = "http://" + Procure_Url.ip + "/ProcureApload/uploads/Image/" + object.getString("name") + ".png";

                            }
                            Order_Adapter adapter = new Order_Adapter(Today_order_details.this,id, name, image_url, status, quantity, amount, descriptions, locat, user, no, date, date);
                            listView.setAdapter(adapter);

                            Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Aploaded error on responce " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", "" + getrestid() + "");
                params.put("supid", "" + getsupid() + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

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
