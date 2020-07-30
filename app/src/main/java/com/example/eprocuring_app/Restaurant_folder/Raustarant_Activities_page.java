package com.example.eprocuring_app.Restaurant_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.eprocuring_app.Procure_Class.sup_details;
import com.example.eprocuring_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Raustarant_Activities_page extends AppCompatActivity {

    ListView listView;
    TextView Supdetails;
    String phone_no = "";
    String name = "";
    ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raustarant__activities_page);
        sup_details details = new sup_details(this,phone_no);
        listView = findViewById(R.id.restaurant_listview);
        Supdetails = findViewById(R.id.Supdetails);
        Supplier_list(Procure_Url.IMAGES_URL);
        details.Supplier_list(Supdetails,getid(),Procure_Url.sup_details_URL);
        Sup_no(getid(),Procure_Url.sup_details_URL);
//        Toast.makeText(getApplicationContext(), phone_no, Toast.LENGTH_LONG).show();

    }

    public void setListView(final ArrayList<Integer> list) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int setid = list.get(position);
                Intent intent = new Intent(Raustarant_Activities_page.this, Product_Details.class);
                intent.putExtra("setid",setid);
                intent.putExtra("id",getidrest());
                intent.putExtra("user",user());
                intent.putExtra("supid",getid());
                intent.putExtra("detail","");
                startActivity(intent);
            }
        });
    }

    public void calling(View v) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phone_no));
//        if (ActivityCompat.checkSelfPermission(Raustarant_Activities_page.this,
//                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        }

}

     public void sms(View v){
         Intent intent = new Intent(Raustarant_Activities_page.this,Send_SMS.class);
         intent.putExtra("phone_no",phone_no);
         intent.putExtra("name",name);
         startActivity(intent);
}

    public void Sup_no(final int id , final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        try {

                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++) {
                                object = array.getJSONObject(i);
                                phone_no = object.getString("phone_no");
                                name = "http://"+ Procure_Url.ip+"/ProcureApload/uploads/Image/" + object.getString("user_name")+".png";
                            }

                        }catch (Exception e){
                            e.printStackTrace();
//                            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "Aploaded error on responce "+error.toString(), Toast.LENGTH_LONG).show();
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

    public void Supplier_list(final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] image_name;
                        String image_url;
                        ArrayList<String> image = new ArrayList<>();
                        try {

                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            image_name = new String[array.length()];

                            for (int i = 0; i<array.length();i++){

                                object = array.getJSONObject(i);
                                image_name[i] = object.getString("description");
                                image_url = "http://"+Procure_Url.ip+"/ProcureApload/uploads/Image/" + object.getString("name")+".png";
                                image.add(image_url);
                            }
                            Procure_Array_Adapter array_adapter = new Procure_Array_Adapter(Raustarant_Activities_page.this,image_name,image);
                            listView.setAdapter(array_adapter);

                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                list.add(id);
                            }
                            setListView(list);

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
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

    public int getid(){
        Bundle bundle = getIntent().getExtras();
        int user_id = bundle.getInt("message");
        return user_id;
    }

    public int getidrest(){
        Bundle bundle = getIntent().getExtras();
        int user_id = bundle.getInt("id");
        return user_id;
    }

    public String user(){
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user");
        return user;
    }
}













