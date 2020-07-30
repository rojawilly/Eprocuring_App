package com.example.eprocuring_app.Procure_Class;

import android.app.Activity;
import android.app.Dialog;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Admin_folder.Admin_Registration_Page;
import com.example.eprocuring_app.Admin_folder.Admin_form;
import com.example.eprocuring_app.R;
import com.example.eprocuring_app.Restaurant_folder.List_0f_Supliers;
import com.example.eprocuring_app.Restaurant_folder.Rastaurant_Registration_form;
import com.example.eprocuring_app.Restaurant_folder.Restaurant_Payment;
import com.example.eprocuring_app.Suplier_folder.Aploaded_Products;
import com.example.eprocuring_app.Suplier_folder.Supplier_Registration_form;
import com.example.eprocuring_app.Suplier_folder.Suppplier_Activity_page;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Procure_Dialogues {

    Dialog dialog,mydialogue;
    Activity activity;
    RadioButton restaurant_rbtn,supplier_rbtn,admin_rbtn;
    TextView register_ok,register_cancel,dialogue_text_ok,dialogue_title,dialogue_message,Dialogue_text_cancel;
    String restaurant;
    EditText pro_quantity;
    Button confirm;

public Procure_Dialogues(Activity activity) {
         this.activity = activity;
    }

public void register_dialogue(){
       dialog = new Dialog(activity);
       dialog.setContentView(R.layout.register_dialogue);
       dialog.setCancelable(false);
       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       restaurant_rbtn = dialog.findViewById(R.id.Restaurant_rbtn);
       supplier_rbtn = dialog.findViewById(R.id.Supplier_rbtn);
       admin_rbtn = dialog.findViewById(R.id.Admin_rbtn);
       register_ok = dialog.findViewById(R.id.Register_ok);
       register_cancel = dialog.findViewById(R.id.Register_concel);

    register_cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    });

       restaurant_rbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(restaurant_rbtn.isChecked()){
                   supplier_rbtn.setChecked(false);
                   admin_rbtn.setChecked(false);
               }
           }
       });


    supplier_rbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(supplier_rbtn.isChecked()){
                restaurant_rbtn.setChecked(false);
                admin_rbtn.setChecked(false);
            }
        }
    });

    admin_rbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(supplier_rbtn.isChecked()){
                restaurant_rbtn.setChecked(false);
                supplier_rbtn.setChecked(false);
            }
        }
    });

       register_ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (restaurant_rbtn.isChecked()){
                    restaurant = "Restaurant";
                   Intent intent = new Intent(activity, Rastaurant_Registration_form.class);
                   activity.startActivity(intent);
                   dialog.dismiss();
               }
               else if (supplier_rbtn.isChecked()){
                    restaurant = "Restaurant";
                   Intent intent = new Intent(activity, Supplier_Registration_form.class);
                   activity.startActivity(intent);
                   dialog.dismiss();
               }
               else if (admin_rbtn.isChecked()){
                   restaurant = "Admin";
                   Intent intent = new Intent(activity, Admin_Registration_Page.class);
                   activity.startActivity(intent);
                   dialog.dismiss();
               }
               else {
                   String title = "worning !";
                   String messagege = "Sorry dr costomer\n\n You should select Rastaurant or Supplier";
                   register_text(title,messagege);
                   dialog.dismiss();
               }
           }
       });


       dialog.show();
}

public void register_text(String title,String message){
      mydialogue = new Dialog(activity);
    mydialogue.setContentView(R.layout.register_text_dialogue);
    mydialogue.setCancelable(false);
    mydialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialogue_title = mydialogue.findViewById(R.id.Dialogue_title);
      dialogue_message = mydialogue.findViewById(R.id.Dialogue_message);
      dialogue_text_ok = mydialogue.findViewById(R.id.Dialogue_text_ok);

      dialogue_title.setText(title);
      dialogue_message.setText(message);

      dialogue_text_ok.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mydialogue.dismiss();
          }
      });

    mydialogue.show();
    }

public void deletedialogue(String title,String message,final int id,final String URL){
        mydialogue = new Dialog(activity);
        mydialogue.setContentView(R.layout.register_text_dialogue);
        mydialogue.setCancelable(false);
        mydialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogue_title = mydialogue.findViewById(R.id.Dialogue_title);
        dialogue_message = mydialogue.findViewById(R.id.Dialogue_message);
        dialogue_text_ok = mydialogue.findViewById(R.id.Dialogue_text_ok);
        Dialogue_text_cancel = mydialogue.findViewById(R.id.Dialogue_text_cancel);

        Dialogue_text_cancel.setText("Cancel");
        dialogue_title.setText(title);
        dialogue_message.setText(message);

        Dialogue_text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialogue.dismiss();
            }
        });

        dialogue_text_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    StringRequest request = new StringRequest(Request.Method.POST,URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                                    Toast.makeText(activity, response, Toast.LENGTH_LONG).show();

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
                            params.put("id",""+id+"");

                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(activity);
                    requestQueue.add(request);
                    Intent intent = new Intent(activity, Aploaded_Products.class);
                    activity.startActivity(intent);
                    activity.finish();
                    mydialogue.dismiss();
                }

        });
   mydialogue.show();
}

public void placeorder(final long logtime,final String time,final String deliver,final int rest_id, final String sup_status,
                       final String name, final String price, final String quantity, final String date, final String description,
                       final String status, final String id, final String empty, final String URL){
    mydialogue = new Dialog(activity);
    mydialogue.setContentView(R.layout.order_quantity);
    mydialogue.setCancelable(false);
    mydialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    pro_quantity = mydialogue.findViewById(R.id.pro_quantity);
    confirm = mydialogue.findViewById(R.id.confirm_order);

    confirm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!pro_quantity.getText().toString().isEmpty()){

                Procure_to_server server = new Procure_to_server(activity);
                server.request_infor_toserver(logtime,time,deliver,rest_id,sup_status,name,price,pro_quantity.getText().toString(),date,
                        description,status,id,empty,URL);
                mydialogue.dismiss();
            }
            else {
                register_text("Dear Customer ","Quantity field cant be Empty");
            }
        }
    });
mydialogue.show();
}

public void Buynow(final long longtime,final int proid,final String time,final String deliver,final int rest_id, final String sup_status,
                       final String name, final String price, final String date,
                       final String description, final String status, final int id, final String empty, final String URL){
        mydialogue = new Dialog(activity);
        mydialogue.setContentView(R.layout.order_quantity);
        mydialogue.setCancelable(false);
        mydialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pro_quantity = mydialogue.findViewById(R.id.pro_quantity);
        confirm = mydialogue.findViewById(R.id.confirm_order);
        confirm.setHint("Confirm to Buy");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pro_quantity.getText().toString().isEmpty()){
                    Double Quanty = Double.parseDouble(pro_quantity.getText().toString());
                    Double Total_Price = Quanty * Double.parseDouble(price);
                    Procure_to_server server = new Procure_to_server(activity);
                    server.buy_after_order(longtime,time,deliver,rest_id,sup_status,name,""+Total_Price+"",pro_quantity.getText().toString(),
                            date,description,status,id,empty,URL);
                    Intent intent1 = new Intent(activity,Restaurant_Payment.class);
                    intent1.putExtra("restid",rest_id);
                    intent1.putExtra("supid",id);
                    intent1.putExtra("price",Total_Price);
                    activity.startActivity(intent1);
                    mydialogue.dismiss();
                    Toast.makeText(activity,"supplier id = "+id,Toast.LENGTH_LONG).show();
                    activity.finish();
                }
                else {
                    register_text("Dear Customer ","Quantity field cant be Empty");
                }
            }
        });
        mydialogue.show();
    }

public void BuynowAfter_order(final long longtime,final int supid,final int restid,final String price,final int proid,final String time,final String date,
                              final String URL){
        mydialogue = new Dialog(activity);
        mydialogue.setContentView(R.layout.unpaid_order_adapter);
        mydialogue.setCancelable(false);
        mydialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        pro_quantity = mydialogue.findViewById(R.id.pro_quantity);
        confirm = mydialogue.findViewById(R.id.confirm_order);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Procure_to_server server = new Procure_to_server(activity);
                    server.update_ordered(longtime,time,date,proid,URL);
                Intent intent1 = new Intent(activity,Restaurant_Payment.class);
                intent1.putExtra("restid",restid);
                intent1.putExtra("supid",supid);
                intent1.putExtra("price",price);
                    activity.startActivity(intent1);
                    activity.finish();
                    mydialogue.dismiss();

            }
        });
        mydialogue.show();
    }
}