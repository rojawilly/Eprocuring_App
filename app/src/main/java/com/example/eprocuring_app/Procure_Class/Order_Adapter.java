package com.example.eprocuring_app.Procure_Class;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Order_Adapter extends ArrayAdapter {

    String[] prodname,prodprice,prodquant,prodstatus,proddescrpt,image_url;
    String[] restname,date,time;
    String restlocation,restno;
    int[] id;
    Activity activity;
    Bitmap bitmap;

    public Order_Adapter(Activity activity, int[] id ,String[] prodname,String[] image_url,String[] prodstatus,String[] prodquant,String[] prodprice,String[] proddescrpt,
                         String restlocation,String[] restname,String restno,String[] date,String[] time) {
        super(activity , R.layout.order_details_adapter,prodname);
        this.prodname = prodname;
        this.image_url = image_url;
        this.prodprice = prodprice;
        this.prodquant = prodquant;
        this.prodstatus = prodstatus;
        this.proddescrpt = proddescrpt;
        this.restname = restname;
        this.restlocation = restlocation;
        this.restno = restno;
        this.date = date;
        this.time = time;
        this.id = id;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        Viewholder viewholder = null;
        if (v == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.order_details_adapter, parent, false);
            viewholder = new Viewholder(v);
            v.setTag(viewholder);
        }
        else {
            viewholder = (Viewholder)v.getTag();
        }

        viewholder.proddet.setText("Product name :"+prodname[position]+"\n"+"Quantity :"+prodquant[position]+"\n"+"Price :"+prodprice[position]+
                "\n"+"Status :"+prodstatus[position]+"\n"+"Description :"+proddescrpt[position]);

        viewholder.restdet.setText("Restaurant name :"+restname[position]+"\n"+"Location :"+restlocation+"\n"+"Phone no. "+restno);

        viewholder.date.setText(date[position]+"  "+time[position]);

        new getimage(viewholder.imageView).execute(image_url[position]);

        viewholder.confimbtn.setHint("Confirm Order "+id[position]);

        viewholder.confimbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int ida = id[position];
               orderIdrequst(ida,Procure_Url.confirm_order_URL);
            }
        });

        return v;
    }


    public void orderIdrequst(final int orderid ,final String URL){

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
//                                no = object.getString("phone_no");
//                                location = object.getString("location");
                            }
//                            setListView(id,no,location);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Aploaded error on catch order "+e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Aploaded error on responce "+error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",""+orderid+"");
                params.put("status","");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    class Viewholder{
        ImageView imageView;
        TextView proddet;
        TextView restdet;
        TextView date;
        Button confimbtn;

        Viewholder(View v){
            imageView = v.findViewById(R.id.orderimgvw);
            proddet = v.findViewById(R.id.productdet);
            restdet = v.findViewById(R.id.restdetails);
            date = v.findViewById(R.id.date);
            confimbtn = v.findViewById(R.id.confirm_btn);

        }
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
}
