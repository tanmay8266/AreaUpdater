/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.location.sample.locationaddress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.squareup.picasso.Picasso;
public class MainActivity extends AppCompatActivity {
    private Button button;

    private LinearLayout linlay;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("/files/");
        Query query = mDatabaseRef.limitToLast(5);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 600));
        lp.setMargins(0,50,0,20);
//        ValueEventListener eventListener =new ValueEventListener() {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    ImageView imgv = new ImageView(MainActivity.this);
                    Datasnp datasnp = ds.getValue(Datasnp.class);
                    Log.i("wooshh",datasnp.url);
                    Picasso.get().load(datasnp.url).placeholder(R.drawable.ae).fit().centerCrop().into(imgv);
                    imgv.setLayoutParams(lp);
                    linlay = findViewById(R.id.linlay);
                    linlay.addView(imgv);
                    TextView textView = new TextView(MainActivity.this);
                    textView.setText(datasnp.loc);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                    linlay.addView(textView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
//        mDatabaseRef.addListenerForSingleValueEvent(eventListener);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button = findViewById(R.id.upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        button = findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch();
            }
        });

    }
    public void openActivity2(){
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
    public void openSearch(){
        Intent intent = new Intent(this,Search.class);
        startActivity(intent);
    }

    }
