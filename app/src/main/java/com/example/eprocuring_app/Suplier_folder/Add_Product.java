package com.example.eprocuring_app.Suplier_folder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eprocuring_app.Procure_Class.Procure_Dialogues;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.Procure_Class.Procure_to_server;
import com.example.eprocuring_app.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Add_Product extends AppCompatActivity {


    ImageView imageView;
   private EditText name,price,brand,desriptions;
   Procure_to_server procure_to_server;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    private int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__product);
        initialdefinition();
        requestStoragePermission();
    }

    public void initialdefinition(){

        imageView = findViewById(R.id.Add_imageview);
        name = findViewById(R.id.productname);
        brand = findViewById(R.id.productbrand);
        price = findViewById(R.id.productprice);
        desriptions = findViewById(R.id.product_descriptions);
        procure_to_server = new Procure_to_server(this);

    }

    public void choose_Photo_add(View view) {

    showFileChooser();

    }

    public void aploadproduct(View v){
        String image = convertBitmapToString(bitmap);
        String productname = name.getText().toString();
        String productamount = brand.getText().toString();
        String productprice = price.getText().toString();
        String productdescripton = desriptions.getText().toString();
        String status = "Available";

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");

        if (!name.getText().toString().isEmpty()&& !price.getText().toString().isEmpty()){
            procure_to_server.request_infor_toserver(0,"","",0,"",productname,productamount,
                    productprice,productdescripton,image,""+id+"","",status, Procure_Url.UPLOAD_URL);
            name.setText("");
            brand.setText("");
            price.setText("");
            desriptions.setText("");
        }
        else {
            String title = "Warning!";
            String message = "Product_name and Product_price cant be empty";
            Procure_Dialogues dialogues = new Procure_Dialogues(Add_Product.this);
            dialogues.register_text(title,message);
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST );
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                InputStream stream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(stream);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String convertBitmapToString(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream); //compress to which format you want.
        byte[] byte_arr = stream.toByteArray();
        String imageStr = Base64.encodeToString(byte_arr,Base64.DEFAULT);
        return imageStr;
    }
}

