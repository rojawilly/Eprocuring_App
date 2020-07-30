package com.example.eprocuring_app.Procure_Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Admin_folder.Admin_form;
import com.example.eprocuring_app.MainActivity;
import com.example.eprocuring_app.R;
import com.example.eprocuring_app.Restaurant_folder.List_0f_Supliers;

import com.example.eprocuring_app.Suplier_folder.Suppplier_Activity_page;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Procure_login {
    Context context;
    Activity activity;
    EditText name;

    public Procure_login(EditText name,Context context,Activity activity) {
        this.context = context;
        this.activity = activity;
        this.name = name;
    }

    public void sendloginrequest(final String user_name, final String password, final String URL){

            StringRequest request = new StringRequest(Request.Method.POST,URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String success = object.getString("roja");
                                if (success.contains("Admin")){
                                    if (success.contains("Enabled")){

                                        Intent intent = new Intent(context, Admin_form.class);
                                        context.startActivity(intent);
                                    }else if (success.contains("Disabled")){

                                        String title = "Message";
                                        String message = "Dr. Costumer you are not admin\n contact Main admin to give access";
                                        Procure_Dialogues dialogues = new Procure_Dialogues(activity);
                                        dialogues.register_text(title,message);
                                    }else {

                                        String title = "Message";
                                        String message = "Dr. Costumer sorry\n   Error occurred ";
                                        Procure_Dialogues dialogues = new Procure_Dialogues(activity);
                                        dialogues.register_text(title,message);

                                    }

                                }else if (success.contains("Restaurant")){

                                    Get_Supplier_id_forProduct id_forProduct = new Get_Supplier_id_forProduct(name,context, activity,List_0f_Supliers.class);
                                    id_forProduct.sendProductIdrequest(user_name,password,Procure_Url.idForProduct_URL);

                                }else if (success.contains("Supplier")){
                                    if (success.contains("Enabled")){

                                        Get_Supplier_id_forProduct id_forProduct = new Get_Supplier_id_forProduct(name,context, activity,Suppplier_Activity_page.class);
                                        id_forProduct.sendProductIdrequest(user_name,password,Procure_Url.idForProduct_URL);

                                    }else if (success.contains("Disabled")){

                                        String title = "Message";
                                        String message = "Dr. Costumer you have not pay\n   Please pay before you Login";
                                        Procure_Dialogues dialogues = new Procure_Dialogues(activity);
                                        dialogues.register_text(title,message);
                                    }else {

                                        String title = "Message";
                                        String message = "Dr. Costumer sorry\n   Error occurred ";
                                        Procure_Dialogues dialogues = new Procure_Dialogues(activity);
                                        dialogues.register_text(title,message);

                                    }

                                }else {
                                    String title = "Warning!";
                                    String message = "Incorrect Username or Password\n Please Enter valid Username or Password";
                                    Procure_Dialogues dialogues = new Procure_Dialogues(activity);
                                    dialogues.register_text(title,message);
                                }
                            } catch (JSONException e) {
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
                    params.put("user_name",user_name);
                    params.put("password",password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        }


    }

