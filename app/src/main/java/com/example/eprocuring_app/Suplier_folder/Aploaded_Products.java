package com.example.eprocuring_app.Suplier_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Delete_Aploaded_Product;
import com.example.eprocuring_app.Procure_Class.Procure_Array_Adapter;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.Restaurant_folder.Product_Details;
import com.example.eprocuring_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Aploaded_Products extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ListView listView;
    ArrayList<Integer> list = new ArrayList<>();
    int prodId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aploaded__products);
        listView = findViewById(R.id.Aploaded_prduct_listview);
        Supplier_list(Procure_Url.IMAGES_URL);
        setListView();
    }

    public void setListView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prodId = list.get(position);
                PopupMenu menu = new PopupMenu(Aploaded_Products.this,view);
                menu.setOnMenuItemClickListener(Aploaded_Products.this);
                menu.inflate(R.menu.roja_menu);
                menu.show();


            }
        });
    }

    public void Supplier_list(final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),""+getid()+"",Toast.LENGTH_LONG).show();
                        String[] image_name;
                        String image_url;
                        ArrayList<String> image = new ArrayList();
                        try {

                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            image_name = new String[array.length()];

                            for (int i = 0; i<array.length();i++){

                                object = array.getJSONObject(i);
                                image_name[i] = object.getString("description");
                                image_url = "http://"+ Procure_Url.ip+"/ProcureApload/uploads/Image/" + object.getString("name")+".png";
                                image.add(image_url);
                            }
                            Procure_Array_Adapter array_adapter = new Procure_Array_Adapter(Aploaded_Products.this,image_name,image);
                            listView.setAdapter(array_adapter);
//

                            for (int i = 0; i<array.length();i++){

                                object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                list.add(id);
                            }
//                            setListView(list);

                        }catch (Exception e){
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
    private int getid(){
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("id");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent = new Intent(Aploaded_Products.this, Aploaded_Praduct_Details.class);
                intent.putExtra("setid",prodId);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(Aploaded_Products.this, Update_aploaded_Product.class);
                intent2.putExtra("setid",prodId);
                startActivity(intent2);
                return true;
            case R.id.item3:
                Intent intent3 = new Intent(Aploaded_Products.this, Delete_Aploaded_Product.class);
                intent3.putExtra("setid",prodId);
                startActivity(intent3);
                finish();
                return true;

                default:
                    return false;

        }

    }
}
