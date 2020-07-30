package com.example.eprocuring_app.Procure_Class;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eprocuring_app.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Procure_Array_Adapter extends ArrayAdapter<String> {

    String[] image_name;
    ArrayList<String> getImage_url;
    Activity activity;
    Bitmap bitmap;

    public Procure_Array_Adapter(Activity activity ,String[] image_name,ArrayList<String> getImage_url) {
        super(activity , R.layout.array_adapter,image_name);
        this.image_name = image_name;
        this.getImage_url = getImage_url;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        Viewholder viewholder = null;
        if (v == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.array_adapter, parent, false);
             viewholder = new Viewholder(v);
            v.setTag(viewholder);
        }
        else {
            viewholder = (Viewholder)v.getTag();
        }

        viewholder.textView.setText(image_name[position]);
        new getimage(viewholder.imageView).execute(getImage_url.get(position));


        return v;
    }

    class Viewholder{
        ImageView imageView;
        TextView textView;

        Viewholder(View v){

            imageView = v.findViewById(R.id.adaptaimgview);
            textView = v.findViewById(R.id.present_data);

        }

    }

    public class getimage extends AsyncTask<String,Void, Bitmap>{

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
}
