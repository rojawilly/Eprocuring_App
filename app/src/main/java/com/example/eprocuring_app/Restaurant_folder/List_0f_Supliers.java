package com.example.eprocuring_app.Restaurant_folder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.Procure_Class.Suppliers_data;
import com.example.eprocuring_app.R;
import com.example.eprocuring_app.Suplier_folder.Today_Order;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class List_0f_Supliers extends AppCompatActivity {

    ListView listView;
    TextView textView;
    ArrayList<String> list = new ArrayList<>();
    private MaterialSearchView materialSearchView;
    private Toolbar toolbar;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_0f__supliers);

//        Toast.makeText(this,getuser(Procure_Url.Rest_location_no_URL),Toast.LENGTH_LONG).show();

        listView = findViewById(R.id.supplier_listview);
        textView = findViewById(R.id.roja);
//        materialSearchView = findViewById(R.id.materialsearch);
//        toolbar = findViewById(R.id.tool);
//        this.getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getActionBar().setDisplayShowCustomEnabled(true);
//        getActionBar().setCustomView(R.layout.action_bar);
         getuser(Procure_Url.Rest_location_no_URL);
//         searchsup();

    }

    public void action_bar(){

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar2);
        //getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        TextView name = view.findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(List_0f_Supliers.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
            }
        });
    }

    public int getrestid(){
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("id");
    }


    public void getuser(final String URL){

        StringRequest request = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            String name = "";
                            for (int i = 0; i<array.length();i++){
                                object = array.getJSONObject(i);
                                name = object.getString("user_name");

                            }
                            Suppliers_data data = new Suppliers_data(list,name,getrestid(),List_0f_Supliers.this,listView, Procure_Url.Supplier_URL,Raustarant_Activities_page.class);
                            data.getsup_list(Procure_Url.Supplier_URL);
                            Toast.makeText(List_0f_Supliers.this,name,Toast.LENGTH_LONG).show();

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
                params.put("id",""+getrestid()+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void searchsup(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setActionBar(toolbar);
//            getActionBar().setTitle("search");
//        }
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                int count = 0;
                if(newText != null || !newText.isEmpty()){
                    List<String> found = new ArrayList<>();
                    for(String item: list){
                        if (item.contains(newText)){
                            found.add(item);
//                            count++;
                        }
                    }

//                    itemfound.setText(""+count+""+" found");
                    ArrayAdapter adapter = new ArrayAdapter(List_0f_Supliers.this,android.R.layout.simple_list_item_1,found);
                    listView.setAdapter(adapter);
                }

                else {
                    seachedarrylist();
                }
                return true;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                seachedarrylist();
            }
        });
    }

    public void seachedarrylist(){
        ArrayAdapter adapter = new ArrayAdapter(List_0f_Supliers.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.roja_menu,menu);
//        MenuItem item = menu.findItem(R.id.item1);
//        materialSearchView.setMenuItem(item);
//        return true;
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.item1);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null || !newText.isEmpty()){
                    List<String> found = new ArrayList<>();
                    for(String item: list){
                        if (item.contains(newText)){
                            found.add(item);
//                            count++;
                        }
                    }

//                    itemfound.setText(""+count+""+" found");
                    ArrayAdapter adapter = new ArrayAdapter(List_0f_Supliers.this,android.R.layout.simple_list_item_1,found);
                    listView.setAdapter(adapter);
                }

                else {
                    seachedarrylist();
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:

                Toast.makeText(this,item+" is clicked",Toast.LENGTH_LONG).show();
                break;

//            case R.id.item2:
//                Intent intent = new Intent(DataBaseConnectection.this,Cow_product.class);
//                startActivity(intent);
//                Toast.makeText(this,item+" is clicked",Toast.LENGTH_LONG).show();
//                break;

            case R.id.item3:
                Intent i = new Intent(List_0f_Supliers.this,Placed_order.class);
                i.putExtra("restid",getrestid());
                startActivity(i);
                break;

            default:
                Toast.makeText(this,"no item clicked",Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
