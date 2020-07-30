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
import com.example.eprocuring_app.Procure_Class.Procure_Array_Adapter;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Today_Order extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today__order);
        listView = findViewById(R.id.today_order_listview);

        getorderlisrequst(Procure_Url.get_user_id_URL);
//        Toast.makeText(this,""+restid+"",Toast.LENGTH_LONG).show();
//        setListView();
    }
    public void setListView(final int restid, final String no, final String location){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Today_Order.this, Today_order_details.class);
                intent.putExtra("supid",getsupid());
                intent.putExtra("no",no);
                intent.putExtra("location",location);
                intent.putExtra("restid",restid);

//                Toast.makeText(getApplicationContext(), ""+restid+"", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
    }

    public void getorderlisrequst(final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int restid = 0;
                        ArrayList<String> list = new ArrayList<>();
                        ArrayList<String> listfilt = new ArrayList<>();
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                               restid = object.getInt("rest_id");
                                String name1 = object.getString("user");

                                list.add(name1);
                            }

                            for (String name: list){
                                if (listfilt.isEmpty()){
                                    listfilt.add(name);
                                }else {
                                    for (String filtname: listfilt){
                                        if (filtname.equals(name)){

                                        }else {
                                            listfilt.add(name);
                                            break;
                                        }
                                    }
                                }
                            }
                            ArrayAdapter adapter = new ArrayAdapter(Today_Order.this,android.R.layout.simple_list_item_1,listfilt);
                            listView.setAdapter(adapter);
                            getrestdetailsrequst(restid,Procure_Url.Rest_location_no_URL);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Aploaded error on catch "+e.toString(), Toast.LENGTH_LONG).show();
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
                params.put("id",""+getsupid()+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public int getsupid(){
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("supid");
        return id;
    }

    public void getrestdetailsrequst(final int id ,final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String location = "";
                        String no = "";
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                no = object.getString("phone_no");
                                location = object.getString("location");
                            }
                            setListView(id,no,location);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Aploaded error on catch order "+e.toString(), Toast.LENGTH_LONG).show();
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
                params.put("id",""+id+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}



