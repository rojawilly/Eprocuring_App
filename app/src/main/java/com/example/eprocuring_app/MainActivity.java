package com.example.eprocuring_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.eprocuring_app.Admin_folder.Admin_form;
import com.example.eprocuring_app.Procure_Class.Get_Supplier_id_forProduct;
import com.example.eprocuring_app.Procure_Class.Procure_Dialogues;
import com.example.eprocuring_app.Procure_Class.Procure_Url;
import com.example.eprocuring_app.Procure_Class.Procure_login;
import com.example.eprocuring_app.Procure_Class.SmsReceiver;
import com.example.eprocuring_app.Restaurant_folder.List_0f_Supliers;
import com.example.eprocuring_app.Suplier_folder.Suppplier_Activity_page;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    Procure_Dialogues procure_dialogues = new Procure_Dialogues(MainActivity.this);
    EditText username,password;
    private MaterialSearchView materialSearchView;
    private Toolbar toolbar;
    private Procure_login login;
    String TAG = SmsReceiver.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: SMS Received");
        initial_definitions();

    }

    public void initial_definitions(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.Password);
//        toolbar = findViewById(R.id.tool);
        login = new Procure_login(username,this,MainActivity.this);
    }

public void resta_supp_reg(View v){
   procure_dialogues.register_dialogue();
}

public void login_btn(View v){
        String u_name = username.getText().toString();
        String passcode = password.getText().toString();
        login.sendloginrequest(u_name,passcode, Procure_Url.Login_URL);
        username.setText("");
        password.setText("");
}


//    public void searchmaterial(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setActionBar(toolbar);
//            getActionBar().setTitle("search");
//        }
//        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                int count = 0;
//                if(newText != null || !newText.isEmpty()){
//                    List<String> found = new ArrayList<>();
//                    for(String item: list){
//                        if (item.contains(newText)){
//                            found.add(item);
//                            count++;
//                        }
//                    }
//
//                    itemfound.setText(""+count+""+" found");
//                    ArrayAdapter adapter = new ArrayAdapter(DataBaseConnectection.this,android.R.layout.simple_list_item_1,found);
//                    listView.setAdapter(adapter);
//                }
//
//                else {
//                    seachedarrylist();
//                }
//                return true;
//            }
//        });
//
//        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                seachedarrylist();
//            }
//        });
//    }

//    public void seachedarrylist(){
//        list = database.Datbasetostring();
//        ArrayAdapter adapter = new ArrayAdapter(DataBaseConnectection.this,android.R.layout.simple_list_item_1,list);
//        listView.setAdapter(adapter);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.roja_menu,menu);
//        MenuItem item = menu.findItem(R.id.item1);
//        materialSearchView.setMenuItem(item);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        switch (id){
//            case R.id.item1:
//
//                Toast.makeText(this,item+" is clicked",Toast.LENGTH_LONG).show();
//                break;
//
////            case R.id.item2:
////                Intent intent = new Intent(MainActivity.this, Admin_form.class);
////                startActivity(intent);
////                Toast.makeText(this,item+" is clicked",Toast.LENGTH_LONG).show();
////                break;
//
////            case R.id.item3:
////                Intent i = new Intent(MainActivity.this, Admin_Login_form.class);
////                startActivity(i);
////                break;
//            default:
//                Toast.makeText(this,"no item clicked",Toast.LENGTH_LONG).show();
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
