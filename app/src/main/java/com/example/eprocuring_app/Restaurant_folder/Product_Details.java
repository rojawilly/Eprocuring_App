package com.example.eprocuring_app.Restaurant_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
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
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.Procure_Class.Procure_to_server;
import com.example.eprocuring_app.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Product_Details extends AppCompatActivity {
public static int supid = 0;
public static int restid = 0;
public static int proid = 0;
    private ImageView imageView1;
    private Bitmap bitmap;
    TextView descriptiontxt,pricetxt,nametxt,quantity;
    String name1 = "";
    String name = "";
    String price = "";
    String brand = "";
    String description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details);

        imageView1 = findViewById(R.id.product_imageview);
        quantity = findViewById(R.id.quantity);
        descriptiontxt = findViewById(R.id.descriptiontxt);
        pricetxt = findViewById(R.id.pricetxt);
        nametxt = findViewById(R.id.nametxt);
        Toast.makeText(getApplicationContext(), user(), Toast.LENGTH_LONG).show();
        if (det().contains("placed")){
            send_order_request(Procure_Url.placed_order_by_id_URL);
        }else {
            sendproductIDrequest(Procure_Url.getImagebyId_URL);
        }
        Toast.makeText(this, ""+supid(), Toast.LENGTH_LONG).show();

    }

    public String user(){
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user");
        return user;
    }

    public void sendproductIDrequest(final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                 name = object.getString("name");
                                 price = object.getString("price");
                                description = object.getString("description");
                                brand = object.getString("Quantity");

                                name1 = "http://"+ Procure_Url.ip+"/ProcureApload/uploads/Image/" + object.getString("name")+".png";
                            }
                            new getimage(imageView1).execute(name1);
                            nametxt.setText("Name :"+name);
                            pricetxt.setText("Price :"+price);
                            quantity.setText("Quantity :"+brand);
                            descriptiontxt.setText(description);

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
                params.put("id",""+getprodid()+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void send_order_request(final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                name = object.getString("name");
                                brand = object.getString("quantity");
                                description = object.getString("status");
                               price = object.getString("amount");

                                name1 = "http://"+ Procure_Url.ip+"/ProcureApload/uploads/Image/" + object.getString("name")+".png";
                            }
                            new getimage(imageView1).execute(name1);
                            nametxt.setText("Name :"+name);
                            quantity.setText("Price :"+brand);
                            pricetxt.setText("Quantity :"+price);
                            descriptiontxt.setText(description);

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
                params.put("id",""+getprodid()+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public class getimage extends AsyncTask<String,Void, Bitmap>{

        ImageView imageView;
        public getimage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String geturl = url[0];
            bitmap = null;
            try {
                InputStream stream = new java.net.URL(geturl).openStream();
                bitmap = BitmapFactory.decodeStream(stream);
            }catch (Exception e){
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    public int getprodid(){
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("setid");
    }

    public String realtime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time = format.format(date);
        return time;
    }
    public String realdate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yy");
        String time = format.format(date);
        return time;
    }

    public void PlaceOder(View view){
        long longtime = new Date().getTime();
        String status = "Unpaid";
        String sup_status = "Unconfirmed";
        String delivered = "Undelivered";
        Procure_Dialogues dialogues = new Procure_Dialogues(this);
        dialogues.placeorder(longtime,realtime(),delivered,getidrest(),sup_status,name,price,brand,realdate(),description,status,
                ""+supid()+"", user(),Procure_Url.Order_details_URL);

    }

    public int getidrest(){
        Bundle bundle = getIntent().getExtras();
        int user_id = bundle.getInt("id");
        return user_id;
    }

    public int supid(){
        Bundle bundle = getIntent().getExtras();
        int user_id = bundle.getInt("supid");
        return user_id;
    }

    public String det(){
        Bundle bundle = getIntent().getExtras();
        return bundle.getString("detail");
    }

    public void Buy_now(View view){
        long longtime = new Date().getTime();
        if (det().contains("placed")){
            Procure_Dialogues dialogues = new Procure_Dialogues(this);
            dialogues.BuynowAfter_order(longtime,supid(),getidrest(),pricetxt.getText().toString(),getprodid(),realtime(),realdate(),
                    Procure_Url.update_order_date_URL);

        }else {
            String status = "Unpaid";
            String sup_status = "Unconfirmed";
            String delivered = "Undelivered";
            Procure_Dialogues dialogues = new Procure_Dialogues(this);
            dialogues.Buynow(longtime,getprodid(),realtime(),delivered,getidrest(),sup_status,name,price,realdate(),description,status,supid(),
                    user(),Procure_Url.Order_details_URL);
        }
    }

}
