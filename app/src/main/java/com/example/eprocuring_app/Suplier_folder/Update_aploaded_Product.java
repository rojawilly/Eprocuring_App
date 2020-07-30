package com.example.eprocuring_app.Suplier_folder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.eprocuring_app.Restaurant_folder.Product_Details;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Update_aploaded_Product extends AppCompatActivity {

    private ImageView imageView;

    private EditText name,price,brand,desriptions;
    Procure_to_server procure_to_server;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    private int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_aploaded__product);
        initialdefinition();
        sendproductIDrequest(Procure_Url.getImagebyId_URL);

       Long date =  new Date().getTime();

    }

    public void initialdefinition(){
        requestStoragePermission();
        imageView = findViewById(R.id.update_image);
        name = findViewById(R.id.productname);
        brand = findViewById(R.id.productbrand);
        price = findViewById(R.id.productprice);
        desriptions = findViewById(R.id.product_descriptions);
        procure_to_server = new Procure_to_server(this);

    }

    public void sendproductIDrequest(final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                        String name1 = "";
                        String brand1 = "";
                        String description1 = "";
                        String price1 = "";
                        String image = "";
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                 name1 = object.getString("name");
                                 price1 = object.getString("price");
                                description1 = object.getString("description");
                                 brand1 = object.getString("Quantity");
                                image = "http://"+ Procure_Url.ip+"/ProcureApload/uploads/Image/" + object.getString("name")+".png";

                            }
                            new getimage(imageView).execute(image);
                            name.setText(name1);
                            brand.setText(brand1);
                            price.setText(price1);
                            desriptions.setText(description1);


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
                params.put("id",""+getid()+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public int getid(){
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("setid");
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

    public void choose_Photo(View view){
        showFileChooser();
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
                imageView.setImageBitmap(bitmap);
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

    public void updateproduct(View v){
        String image = convertBitmapToString(bitmap);
        String productname = name.getText().toString();
        String productamount = brand.getText().toString();
        String productprice = price.getText().toString();
        String productdescripton = desriptions.getText().toString();

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("setid");

        if (!name.getText().toString().isEmpty()&& !price.getText().toString().isEmpty()
                &&!brand.getText().toString().isEmpty()&& !desriptions.getText().toString().isEmpty()){
            procure_to_server.request_infor_toserver(0,"","",0,"",productname,productamount,
                    productprice,productdescripton,image,""+id+"","","", Procure_Url.UpdateImage_URL);
            name.setText("");
            brand.setText("");
            price.setText("");
            desriptions.setText("");
        }
        else {
            String title = "Warning!";
            String message = "Product_name and Product_price cant be empty";
            Procure_Dialogues dialogues = new Procure_Dialogues(Update_aploaded_Product.this);
            dialogues.register_text(title,message);
        }
    }
}
