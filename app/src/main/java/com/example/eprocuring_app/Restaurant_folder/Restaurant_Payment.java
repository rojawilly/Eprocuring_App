package com.example.eprocuring_app.Restaurant_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Restaurant_Payment extends AppCompatActivity {
  TextView title,cont1;
  RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant__payment);

        title = findViewById(R.id.title);
        cont1 = findViewById(R.id.firstcont);
        layout = findViewById(R.id.relative);

        Bundle bundle = getIntent().getExtras();
        double amount = bundle.getDouble("price");
        int supid = bundle.getInt("supid");
        int restid = bundle.getInt("restid");
        Toast.makeText(getApplicationContext(),"price "+amount, Toast.LENGTH_LONG).show();
        getrest_no(""+amount+"",restid,supid,Procure_Url.Details_URL);

    }

    public void payment_instruction(final String amount,final int supid,final String rest_no, String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {

                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            String no = "";
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                no = object.getString("phone_no");
                            }
                            Toast.makeText(getApplicationContext(),"sup no "+no, Toast.LENGTH_LONG).show();
                           // if restaurant number is voda
                            if (rest_no.contains("075")|rest_no.contains("076")|rest_no.contains("074")){
                                layout.setBackgroundResource(R.drawable.vodacom2);
                                title.setText("Karibu kwenye malipo ya vodacom\nNamba yako ni :"+rest_no);
                                if (no.contains("075")|no.contains("076")|rest_no.contains("074")){
                                cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 1 weka namba ya simu\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nChagua 1 kutuma");

                                }else if (no.contains("071")|no.contains("067")|no.contains("065")){
                                    cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 5 mitandao mingine\nChagua 2 TigoPesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nChagua 1 kutuma");

                                }else if (no.contains("078")|no.contains("068")){
                                    cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 5 mitandao mingine\nChagua 1 AirtelMoney\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nChagua 1 kutuma");

                                }else if (no.contains("073")){
                                    cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 5 mitandao mingine\nChagua 3 T-Pesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nChagua 1 kutuma");

                                }else if (no.contains("062")){
                                    cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 5 mitandao mingine\nChagua 4 HaloPesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nChagua 1 kutuma");

                                }
                                //if restaurant number is tigo
                            }else if (rest_no.contains("071")|rest_no.contains("065")|rest_no.contains("067")){
                                layout.setBackgroundResource(R.drawable.tigo_p);
//                                title.setTextColor(R.color.black1);
                                title.setText("Karibu kwenye malipo ya Tigo\nNamba yako ni :"+rest_no);
                                if (no.contains("075")|no.contains("076")|rest_no.contains("074")){
                                    cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 3 M-Pesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");

                                }else if (no.contains("071")|no.contains("067")|no.contains("065")){
                                    cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 1 Kwa namba ya simu\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");

                                }else if (no.contains("078")|no.contains("068")){
                                    cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 1 AirtelMoney\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");

                                }else if (no.contains("073")){
                                    cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 4 T-Pesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");

                                }else if (no.contains("062")){
                                    cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 5 HaloPesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");

                                }
                                //if restaurant number is Airtel
                            }else if (rest_no.contains("068")|rest_no.contains("078")){
                                layout.setBackgroundResource(R.drawable.aitel);
//                                title.setTextColor(R.color.black1);
                                title.setText("Karibu kwenye malipo ya Airtel\nNamba yako ni :"+rest_no);
                                //voda
                                if (no.contains("075")|no.contains("076")|rest_no.contains("074")){
                                    cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 3 M-Pesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");
                                    //tigo
                                }else if (no.contains("071")|no.contains("067")|no.contains("065")){
                                    cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 1 TigoPesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");
                                    //airtel
                                }else if (no.contains("078")|no.contains("068")){
                                    cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 1 Kwa namba ya simu\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");
                                    //ttcl
                                }else if (no.contains("073")){
                                    cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 4 T-Pesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");
                                    //halotel
                                }else if (no.contains("062")){
                                    cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 5 HaloPesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri");

                                }
                                //if restaurant no is halotel
                            }else if (rest_no.contains("062")){
                                layout.setBackgroundResource(R.drawable.halotel);
                                title.setText("Karibu kwenye malipo ya Halotel\nNamba yako ni :"+rest_no);
                                //voda
                                if (no.contains("075")|no.contains("076")|rest_no.contains("074")){
                                    cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 2 kwenda mitandao mingine\nChagua 2 M-Pesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                                    //tigo
                                }else if (no.contains("071")|no.contains("067")|no.contains("065")){
                                    cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 2 kwenda mitandao mingine\nChagua 1 TigoPesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                                    //airtel
                                }else if (no.contains("078")|no.contains("068")){
                                    cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 2 kwenda mitandao mingine\nChagua 3 AirtelMoney\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                                    //ttcl
                                }else if (no.contains("073")){
                                    cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 2 kwenda mitandao mingine\nChagua 5 T-Pesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                                    //halotel
                                }else if (no.contains("062")){
                                    cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 1 Kwenda Halotel\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");

                                }
                                //if restaurant no is ttcl
                            }else if (rest_no.contains("073")){
                                layout.setBackgroundResource(R.drawable.ttcl);
                                title.setText("Karibu kwenye malipo ya Halotel\nNamba yako ni :"+rest_no);
                                //voda
                                if (no.contains("075")|no.contains("076")|rest_no.contains("074")){
                                    cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 4 kwenda mitandao mingine\nChagua 3 M-Pesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                                    //tigo
                                }else if (no.contains("071")|no.contains("067")|no.contains("065")){
                                    cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 4 kwenda mitandao mingine\nChagua 1 TigoPesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                                    //airtel
                                }else if (no.contains("078")|no.contains("068")){
                                    cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 4 kwenda mitandao mingine\nChagua 2 AirtelMoney\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                                    //ttcl
                                }else if (no.contains("073")){
                                    cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 1 Kwenda TTCL\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                                    //halotel
                                }else if (no.contains("062")){
                                    cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 4 kwenda mitandao mingine\nChagua 4 HaloPesa\nIngiza namba :"+no+"\nWeka kiasi:"+amount+"\nIngiza neno la siri\nchaguaa 1 kuthibitisha");

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "Aploaded error on catch "+e.toString(), Toast.LENGTH_LONG).show();
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
                params.put("id",""+supid+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void getrest_no(final String amount, final int restid, final int supid, final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {

                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            String rest_no = "";
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                rest_no = object.getString("phone_no");
                            }
                            Toast.makeText(getApplicationContext(),"rest no "+rest_no, Toast.LENGTH_LONG).show();
                            payment_instruction(amount,supid,rest_no,Procure_Url.Details_URL);

                        } catch (Exception e) {
                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "Aploaded error on catch "+response+e.toString(), Toast.LENGTH_LONG).show();
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
                params.put("id",""+restid+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
