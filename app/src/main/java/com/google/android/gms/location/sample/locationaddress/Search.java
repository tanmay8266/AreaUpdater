package com.google.android.gms.location.sample.locationaddress;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Search extends AppCompatActivity {
    ArrayList<String> language = new ArrayList<String>();
    private Button button;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;

    private LinearLayout linlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("/files/");
        ValueEventListener eventListener =new ValueEventListener() {
            int i = 1;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    Datasnp datasnp = ds.getValue(Datasnp.class);
                    Log.i("nnnn", datasnp.loc);
                    language.add(datasnp.loc);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseRef.addListenerForSingleValueEvent(eventListener);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,language);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv =  (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("/files/");
                Log.i("tag","entered");
                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 600));
                lp.setMargins(0,50,0,20);
                ValueEventListener eventListener =new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                String key = ds.getKey();
                                ImageView imgv = new ImageView(Search.this);
                                Datasnp datasnp = ds.getValue(Datasnp.class);
                                Log.i("wooshh",datasnp.url);
                                Picasso.get().load(datasnp.url).placeholder(R.drawable.ae).fit().centerCrop().into(imgv);
                                imgv.setLayoutParams(lp);
                                linlay = findViewById(R.id.linlay);
                                linlay.addView(imgv);
                                TextView textView = new TextView(Search.this);
                                textView.setText(datasnp.loc);
                                textView.setGravity(Gravity.CENTER);
                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                                linlay.addView(textView);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    };
        mDatabaseRef.addListenerForSingleValueEvent(eventListener);

            }
        });
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);

    }
}
