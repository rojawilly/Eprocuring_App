package com.example.eprocuring_app.Restaurant_folder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eprocuring_app.R;
import com.example.eprocuring_app.Suplier_folder.Suppplier_Activity_page;

import java.io.InputStream;

public class Send_SMS extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
    private static final String TAG = Send_SMS.class.getName();
    ImageView sendBtn;
    TextView txtphoneNo;
    EditText txtMessage;
    ImageView imageView;
    String phoneNo;
    String message;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__sms);
        sendBtn = findViewById(R.id.btnSendSMS);
        txtphoneNo = findViewById(R.id.editText);
        txtMessage = findViewById(R.id.editText2);
        imageView = findViewById(R.id.imageButton);
        txtphoneNo.setText(phon_no());
        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("name");
        new getimage(imageView).execute(image);
        sendSMSMessage();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               send_sms();
               txtMessage.setText("");
            }
        });

    }

    protected void sendSMSMessage() {
        phoneNo = txtphoneNo.getText().toString();
        message = txtMessage.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            } else {
                Log.d(TAG, "sendSMSMessage: sms not sent");

            }
            Log.d(TAG, "sendSMSMessage: sms not sent after else");
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
//        Log.d(TAG, "onRequestPermissionsResult: Pot switch case");
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    Log.d(TAG, "onRequestPermissionsResult: sms manager");
//                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
//                    Toast.makeText(Send_SMS.this, "SMS sent.",Toast.LENGTH_LONG).show();
//                } else {
//                    Log.d(TAG, "onRequestPermissionsResult: on else");
//                    Toast.makeText(getApplicationContext(),"SMS faild, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//
//    }


    public class getimage extends AsyncTask<String,Void, Bitmap> {

        ImageView imageView;
        public getimage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String geturl = url[0];
            bitmap = null;
            try {
                InputStream stream = new java.net.URL(geturl).openStream();
                bitmap = BitmapFactory.decodeStream(stream);
            }catch (Exception e){
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    public void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , txtphoneNo.getText().toString());
        smsIntent.putExtra("sms_body"  , txtMessage.getText().toString());

        try {
            startActivity(smsIntent);
//            finish();
            Toast.makeText(Send_SMS.this,"SMS Sent ",Toast.LENGTH_SHORT).show();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Send_SMS.this,"SMS faild, please try again later. "+ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void send_sms(){
        PendingIntent pendingIntent = null, deliveryIntent = null;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(txtphoneNo.getText().toString(),null,txtMessage.getText().toString(),pendingIntent,deliveryIntent);
        Toast.makeText(Send_SMS.this, "SMS sent.",Toast.LENGTH_LONG).show();
    }

    public String phon_no(){
        Bundle bundle = getIntent().getExtras();
        return bundle.getString("phone_no");
    }

}
