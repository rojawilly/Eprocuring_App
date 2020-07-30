package com.example.eprocuring_app.Procure_Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.eprocuring_app.Suplier_folder.Suppplier_Activity_page;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Suppliers_data {

    Context context;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Integer> listid = new ArrayList<>();
    ListView listView;
    String URL;
    BufferedInputStream bufferedInputStream;
    Activity activity;
    Class aClass;
    int idrest;
    String user;

    public Suppliers_data(ArrayList<String> list, String user,int idrest,Context context,ListView listView,String URL,Class aclass) {
        this.context = context;
        this.listView = listView;
        this.URL = URL;
        this.aClass = aclass;
        this.idrest = idrest;
        this.user = user;
        this.list = list;
    }

//    public void excutelist(){
//        new Backgroundruning().execute();
//    }

    public void getlistname(ListView listView, final List<Integer> listi, final Class roja){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             int message = listi.get(position);
                Intent intent = new Intent(context, roja);
                intent.putExtra("message",message);
                intent.putExtra("id",idrest);
                intent.putExtra("user",user);
                context.startActivity(intent);
            }
        });
    }


    public void getsup_list(String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            String name1 = "";
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                 name1 = object.getString("user_name");

                                list.add(name1);
                                listid.add(id);
                            }
                            ArrayAdapter adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,list);
                            listView.setAdapter(adapter);
                            getlistname(listView,listid,aClass);

                        } catch (Exception e) {
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
//                params.put("id",""+supid+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public class Backgroundruning extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String line = "";
            try {
                java.net.URL url = new URL(Procure_Url.Supplier_URL);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null){
                    builder.append(line+"\n");
                }
                bufferedInputStream.close();
                result = builder.toString();

            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
//                Toast.makeText(context,result, Toast.LENGTH_LONG).show();
//                String name = "";
            try{
                JSONArray array = new JSONArray(result);
                JSONObject object = null;
                for (int i = 0; i<array.length();i++){
                    object = array.getJSONObject(i);
                    int id = object.getInt("id");
                    String name1 = object.getString("user_name");
//                    String description = object.getString("description");
//                    name = "Name :"+ name1 +"\n" + "description :"+description;

                    list.add(name1);
                        listid.add(id);
                }
                ArrayAdapter adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);
                    getlistname(listView,listid,aClass);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
