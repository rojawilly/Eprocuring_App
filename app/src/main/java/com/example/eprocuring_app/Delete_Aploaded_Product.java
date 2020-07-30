package com.example.eprocuring_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.eprocuring_app.Suplier_folder.Aploaded_Praduct_Details;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Delete_Aploaded_Product extends AppCompatActivity {

    private ImageView imageView1;
    private Bitmap bitmap;
    TextView essentialdetails,descriptiontxt,pricetxt,nametxt,quantity,statustxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete__aploaded__product);


        imageView1 = findViewById(R.id.product_imageview);
        quantity = findViewById(R.id.quantity);
        descriptiontxt = findViewById(R.id.descriptiontxt);
        pricetxt = findViewById(R.id.pricetxt);
        nametxt = findViewById(R.id.nametxt);
        statustxt = findViewById(R.id.statustxt);

        sendproductIDrequest(Procure_Url.getImagebyId_URL);
//        Toast.makeText(this, ""+getid()+"", Toast.LENGTH_LONG).show();
    }


    public void sendproductIDrequest(final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                        String name1 = "";
                        String name = "";
                        String price = "";
                        String brand = "";
                        String status = "";
                        String description = "";
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
                                status = object.getString("status");

                                name1 = "http://"+ Procure_Url.ip+"/ProcureApload/uploads/Image/" + object.getString("name")+".png";
                            }
                            new getimage(imageView1).execute(name1);
                            nametxt.setText("Name :"+name);
                            pricetxt.setText("Price :"+price);
                            quantity.setText("Quantity :"+brand);
                            descriptiontxt.setText(description);
                            statustxt.setText(status);

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

    public int getid(){
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("setid");
    }

    public void deleteproduct(View v){
        Procure_Dialogues dialogues = new Procure_Dialogues(Delete_Aploaded_Product.this);
        String title = "Warning!";
        String measage = "This Product will be delete permanently";
        dialogues.deletedialogue(title,measage,getid(),Procure_Url.Delete_item_URL);
    }

}
