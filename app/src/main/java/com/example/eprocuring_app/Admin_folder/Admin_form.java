package com.example.eprocuring_app.Admin_folder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eprocuring_app.R;

public class Admin_form extends AppCompatActivity {

    private ImageView homeimgv,addimgv,suppllierimgv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_form);

        homeimgv = findViewById(R.id.homeimgv);
        addimgv = findViewById(R.id.addimgview);
        suppllierimgv = findViewById(R.id.supplierimgview);
        homeimgv.setImageResource(R.drawable.blue_home_icon);
    }

    public void home_btn(View v){
    }

    public void list_of_supplier(View v){
        Intent intent = new Intent(this,List_Supplier_Added.class);
        startActivity(intent);
    }

    public void add_supplier_btn(View v){
        Intent intent = new Intent(this,Add_Supplier_page.class);
        startActivity(intent);
    }

    public void get_password(View v){

    }
}
