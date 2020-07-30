package com.example.eprocuring_app.Suplier_folder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.R;
import com.example.eprocuring_app.Restaurant_folder.Product_Details;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Suppplier_Activity_page extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    private int PICK_IMAGE_REQUEST = 100;
    private ImageView imageView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppplier__page);
        imageView1 = findViewById(R.id.pasportimgv);
        requestStoragePermission();
        pasport(Procure_Url.sup_details_URL);

    }

    public void add_product(View view){
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        Intent intent = new Intent(Suppplier_Activity_page.this, Add_Product.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void today_order(View v){
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        Intent intent = new Intent(Suppplier_Activity_page.this, Today_Order.class);
        intent.putExtra("supid",id);
        startActivity(intent);
    }

    public void aploaded_btn(View v){
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        Intent intent = new Intent(Suppplier_Activity_page.this, Aploaded_Products.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void derivered_btn(View view){
        Bundle bundle = getIntent().getExtras();
        int supid = bundle.getInt("id");
        Intent intent = new Intent(Suppplier_Activity_page.this,Derivered_order.class);
        intent.putExtra("supid",supid);
        startActivity(intent);
    }

    public void confirmed_order(View v){
        Bundle bundle = getIntent().getExtras();
        int supid = bundle.getInt("id");
        Intent intent = new Intent(Suppplier_Activity_page.this,Confirmed_Order.class);
        intent.putExtra("supid",supid);
        startActivity(intent);
    }

    public void hostory_btn(View v){
        Bundle bundle = getIntent().getExtras();
        int supid = bundle.getInt("id");
        Intent intent = new Intent(Suppplier_Activity_page.this,History_page.class);
        intent.putExtra("supid",supid);
        startActivity(intent);
    }

    public void pasport(String URL){

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
                                name1 = "http://"+ Procure_Url.ip+"/ProcureApload/uploads/Image/" + object.getString("user_name")+".png";

                            }
                            new getimage(imageView1).execute(name1);
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
                params.put("id",""+supid()+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void change_pasport(View view){
        showFileChooser();
    }

    public void upload_pasport(View view){
        Bundle bundle = getIntent().getExtras();
        int supid = bundle.getInt("id");
        String image = convertBitmapToString(bitmap);
        upload_pasport(supid,image,Procure_Url.upload_pasport_URL);
        pasport(Procure_Url.sup_details_URL);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST );
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                InputStream stream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(stream);
                imageView1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String convertBitmapToString(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream); //compress to which format you want.
        byte[] byte_arr = stream.toByteArray();
        String imageStr = Base64.encodeToString(byte_arr,Base64.DEFAULT);
        return imageStr;
    }

    public class getimage extends AsyncTask<String,Void, Bitmap> {

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

    public void upload_pasport(final int id,final String image ,final String URL){

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
                params.put("image",image);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public int supid(){
        Bundle bundle = getIntent().getExtras();
        int supid = bundle.getInt("id");
        return supid;
    }
}
