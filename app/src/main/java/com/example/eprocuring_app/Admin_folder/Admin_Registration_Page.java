package com.example.eprocuring_app.Admin_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.eprocuring_app.MainActivity;
import com.example.eprocuring_app.Procure_Class.Procure_Dialogues;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.Procure_Class.Procure_to_server;
import com.example.eprocuring_app.R;

public class Admin_Registration_Page extends AppCompatActivity {
    private EditText name,email,phone_no,password,retypepassword;
    Procure_to_server procure_to_server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_registration_form);
        itemdefinitions();

    }

    public void itemdefinitions(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.Email);
        phone_no = findViewById(R.id.Phone_number);
        password = findViewById(R.id.Password);
        retypepassword = findViewById(R.id.Retypepassword);
        procure_to_server = new Procure_to_server(this);
    }

    public void getdata(){
        String u_name = name.getText().toString();
        String u_email = email.getText().toString();
        String phone = phone_no.getText().toString();
        String u_location = "";
        String u_password = password.getText().toString();
        String u_description = "";
        String status = "Disabled";
        String role = "Admin";

        if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !phone_no.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty()){

            if (password.getText().toString().equals(retypepassword.getText().toString())){

                if (email.getText().toString().contains("@")){
                    procure_to_server.request_infor_toserver(0,"","",0,"",u_name,u_password,u_email,u_location,phone,u_description,role,status,Procure_Url.Register_URL);
                    Intent intent = new Intent(Admin_Registration_Page.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    String title = "Warning!";
                    String message = "Invalid email\n Include '@' in your email";
                    Procure_Dialogues dialogues = new Procure_Dialogues(Admin_Registration_Page.this);
                    dialogues.register_text(title,message);
                }

            }
            else {
                String title = "Warning!";
                String message = "Password do not match";
                Procure_Dialogues dialogues = new Procure_Dialogues(Admin_Registration_Page.this);
                dialogues.register_text(title,message);
            }

        }else {
            String title = "Warning!";
            String message = "One or more field is empty";
            Procure_Dialogues dialogues = new Procure_Dialogues(Admin_Registration_Page.this);
            dialogues.register_text(title,message);
        }
    }

    public void Adimin_Signup_btn(View v){
        getdata();
    }

}
