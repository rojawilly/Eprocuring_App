package com.example.eprocuring_app.Admin_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.Procure_Class.Suppliers_data;
import com.example.eprocuring_app.R;

import java.util.ArrayList;

public class List_Supplier_Added extends AppCompatActivity {

    ListView listView;
    private ImageView homeimgv,addimgv,suppllierimgv;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__supplier__added);

        listView = findViewById(R.id.supplier_listview);
        Suppliers_data data = new Suppliers_data(list,"",0,this,listView, Procure_Url.Supplier_admin_URL,Enable_Disable_Dupplier.class);
        data.getsup_list(Procure_Url.Supplier_URL);

        homeimgv = findViewById(R.id.homeimgv);
        addimgv = findViewById(R.id.addimgview);
        suppllierimgv = findViewById(R.id.supplierimgview);
        suppllierimgv.setImageResource(R.drawable.people_blue_icon);
    }

    public void home_btn(View v){
        Intent intent = new Intent(this,Admin_form.class);
        startActivity(intent);
        finish();
    }

    public void list_of_supplier(View v){
    }

    public void add_supplier_btn(View v){
        Intent intent = new Intent(this,Add_Supplier_page.class);
        startActivity(intent);
        finish();
    }
}
