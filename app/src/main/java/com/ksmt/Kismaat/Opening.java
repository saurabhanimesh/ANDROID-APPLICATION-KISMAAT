package com.ksmt.Kismaat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Opening extends AppCompatActivity {
    private String currentUpi;
    public String currentUsername;
    public String currentPassword;
    private String currentName;
    private String userId;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_opening);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intentmain=getIntent();
        currentUpi=(String)intentmain.getStringExtra("currentPhoneNumber");
        currentUsername=(String)intentmain.getStringExtra("currentUserName");
        currentPassword=(String)intentmain.getStringExtra("currentPassword");
        currentName=(String)intentmain.getStringExtra("currentName");
        userId=(String)intentmain.getStringExtra("userId");


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {




            }

        }, 3000L);

    }
}