package com.example.eprocuring_app.Suplier_folder;

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
import com.example.eprocuring_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Supplier_Payment extends AppCompatActivity {
    TextView title,cont1;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier__payment);

        title = findViewById(R.id.title);
        cont1 = findViewById(R.id.firstcont);
        layout = findViewById(R.id.relative);

        Bundle bundle = getIntent().getExtras();
        String admin_no = bundle.getString("admin_no");
        String sup_no = bundle.getString("sup_no");
        String price = bundle.getString("price");
        payment_instruction(price,admin_no,sup_no);
    }

    public void payment_instruction(String amount,String no,String rest_no) {
        // if restaurant number is voda
        if (rest_no.contains("075") | rest_no.contains("076") | rest_no.contains("074")) {
            layout.setBackgroundResource(R.drawable.vodacom2);
            title.setText("Karibu kwenye malipo ya vodacom\nNamba yako ni :" + rest_no);
            if (no.contains("075") | no.contains("076")) {
                cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 1 weka namba ya simu\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nChagua 1 kutuma");

            } else if (no.contains("071") | no.contains("075") | no.contains("065")) {
                cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 5 mitandao mingine\nChagua 2 TigoPesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nChagua 1 kutuma");

            } else if (no.contains("078") | no.contains("068")) {
                cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 5 mitandao mingine\nChagua 1 AirtelMoney\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nChagua 1 kutuma");

            } else if (no.contains("073")) {
                cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 5 mitandao mingine\nChagua 3 T-Pesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nChagua 1 kutuma");

            } else if (no.contains("062")) {
                cont1.setText("Piga : *150*00#\nChagua 1 kutuma pesa\nChagua 5 mitandao mingine\nChagua 4 HaloPesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nChagua 1 kutuma");

            }
            //if restaurant number is tigo
        } else if (rest_no.contains("071") | rest_no.contains("065") | rest_no.contains("067")) {
            layout.setBackgroundResource(R.drawable.tigo_p);
//                                title.setTextColor(R.color.black1);
            title.setText("Karibu kwenye malipo ya Tigo\nNamba yako ni :" + rest_no);
            if (no.contains("075") | no.contains("076")) {
                cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 3 M-Pesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");

            } else if (no.contains("071") | no.contains("075") | no.contains("065")) {
                cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 1 Kwa namba ya simu\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");

            } else if (no.contains("078") | no.contains("068")) {
                cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 1 AirtelMoney\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");

            } else if (no.contains("073")) {
                cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 4 T-Pesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");

            } else if (no.contains("062")) {
                cont1.setText("Piga : *150*01#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 5 HaloPesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");

            }
            //if restaurant number is Airtel
        } else if (rest_no.contains("068") | rest_no.contains("078")) {
            layout.setBackgroundResource(R.drawable.aitel);
//                                title.setTextColor(R.color.black1);
            title.setText("Karibu kwenye malipo ya Airtel\nNamba yako ni :" + rest_no);
            //voda
            if (no.contains("075") | no.contains("076")) {
                cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 3 M-Pesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");
                //tigo
            } else if (no.contains("071") | no.contains("075") | no.contains("065")) {
                cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 1 TigoPesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");
                //airtel
            } else if (no.contains("078") | no.contains("068")) {
                cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 1 Kwa namba ya simu\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");
                //ttcl
            } else if (no.contains("073")) {
                cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 4 T-Pesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");
                //halotel
            } else if (no.contains("062")) {
                cont1.setText("Piga : *150*60#\nChagua 1 Tuma pesa\nChagua 3 mitandao mingine\nChagua 5 HaloPesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri");

            }
            //if restaurant no is halotel
        } else if (rest_no.contains("062")) {
            layout.setBackgroundResource(R.drawable.halotel);
            title.setText("Karibu kwenye malipo ya Halotel\nNamba yako ni :" + rest_no);
            //voda
            if (no.contains("075") | no.contains("076")) {
                cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 2 kwenda mitandao mingine\nChagua 2 M-Pesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                //tigo
            } else if (no.contains("071") | no.contains("075") | no.contains("065")) {
                cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 2 kwenda mitandao mingine\nChagua 1 TigoPesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                //airtel
            } else if (no.contains("078") | no.contains("068")) {
                cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 2 kwenda mitandao mingine\nChagua 3 AirtelMoney\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                //ttcl
            } else if (no.contains("073")) {
                cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 2 kwenda mitandao mingine\nChagua 5 T-Pesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                //halotel
            } else if (no.contains("062")) {
                cont1.setText("Piga : *150*88#\nChagua 1 Tuma pesa\nChagua 1 Kwenda Halotel\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");

            }
            //if restaurant no is ttcl
        } else if (rest_no.contains("073")) {
            layout.setBackgroundResource(R.drawable.ttcl);
            title.setText("Karibu kwenye malipo ya Halotel\nNamba yako ni :" + rest_no);
            //voda
            if (no.contains("075") | no.contains("076")) {
                cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 4 kwenda mitandao mingine\nChagua 3 M-Pesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                //tigo
            } else if (no.contains("071") | no.contains("075") | no.contains("065")) {
                cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 4 kwenda mitandao mingine\nChagua 1 TigoPesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                //airtel
            } else if (no.contains("078") | no.contains("068")) {
                cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 4 kwenda mitandao mingine\nChagua 2 AirtelMoney\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                //ttcl
            } else if (no.contains("073")) {
                cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 1 Kwenda TTCL\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");
                //halotel
            } else if (no.contains("062")) {
                cont1.setText("Piga : *150*71#\nChagua 1 Tuma pesa\nChagua 4 kwenda mitandao mingine\nChagua 4 HaloPesa\nIngiza namba :" + no + "\nWeka kiasi:" + amount + "\nIngiza neno la siri\nchaguaa 1 kuthibitisha");

            }
        }
    }
}


