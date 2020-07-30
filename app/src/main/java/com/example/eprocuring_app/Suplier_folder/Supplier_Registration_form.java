package com.example.eprocuring_app.Suplier_folder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.eprocuring_app.Admin_folder.Admin_Registration_Page;
import com.example.eprocuring_app.MainActivity;
import com.example.eprocuring_app.Procure_Class.Procure_Dialogues;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.Procure_Class.Procure_to_server;
import com.example.eprocuring_app.R;
import com.example.eprocuring_app.Restaurant_folder.Restaurant_Payment;

public class Supplier_Registration_form extends AppCompatActivity {

    private EditText name,email,phone_no,location,password,retypepassword,desriptions;
    private Procure_to_server procure_to_server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier__registration_form);
        itemdefinitions();

    }
    public void itemdefinitions(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone_no = findViewById(R.id.phone_number);
        location = findViewById(R.id.location);
        password = findViewById(R.id.Password);
        retypepassword = findViewById(R.id.Retypepassword);
        desriptions = findViewById(R.id.Description);
        procure_to_server = new Procure_to_server(this);
    }

    public void getdata(){
        String u_name = name.getText().toString();
        String u_email = email.getText().toString();
        String phone = phone_no.getText().toString();
        String u_location = location.getText().toString();
        String u_password = password.getText().toString();
        String u_description = desriptions.getText().toString();
        String status = "Disabled";
        String role = "Supplier";

        if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !phone_no.getText().toString().isEmpty() &&
                !location.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !desriptions.getText().toString().isEmpty()){
            if (password.getText().toString().equals(retypepassword.getText().toString())){

                if (email.getText().toString().contains("@")){
                    procure_to_server.request_infor_toserver(0,"","",0,"",u_name,u_password,u_email,u_location,phone,u_description,role,status,Procure_Url.Register_URL);
                    Intent intent = new Intent(Supplier_Registration_form.this, Supplier_Payment.class);
                    intent.putExtra("admin_no","0734073281");
                    intent.putExtra("sup_no",phone_no.getText().toString());
                    intent.putExtra("price","2000");
                    startActivity(intent);
                    finish();
                }
                else {
                    String title = "Warning!";
                    String message = "Invalid email\n Include '@' in your email";
                    Procure_Dialogues dialogues = new Procure_Dialogues(Supplier_Registration_form.this);
                    dialogues.register_text(title,message);
                }
            }
            else {
                String title = "Warning!";
                String message = "Password do not match";
                Procure_Dialogues dialogues = new Procure_Dialogues(Supplier_Registration_form.this);
                dialogues.register_text(title,message);
            }
        }else {
            String title = "Warning!";
            String message = "One or more field is empty";
            Procure_Dialogues dialogues = new Procure_Dialogues(Supplier_Registration_form.this);
            dialogues.register_text(title,message);
        }
    }

    public void Supplier_Signup_btn(View v){
        getdata();
    }
}
