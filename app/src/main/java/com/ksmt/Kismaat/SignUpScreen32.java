package com.ksmt.Kismaat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpScreen32 extends AppCompatActivity {

    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_screen32);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Intent intent4=getIntent();
        currentUpi=(String)intent4.getStringExtra("currentPhoneNumber");
        currentUsername=(String)intent4.getStringExtra("currentUserName");
        currentPassword=(String)intent4.getStringExtra("currentPassword");
        currentName=(String)intent4.getStringExtra("currentName");

        auth= FirebaseAuth.getInstance();

      if(hasInternetConnectivity()) {
          Handler mHandler = new Handler();
          mHandler.postDelayed(new Runnable() {

              @Override
              public void run() {
                  auth.createUserWithEmailAndPassword(currentUsername, currentPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {

                              userId = auth.getCurrentUser().getUid().toString();
                              Intent intent5 = new Intent(SignUpScreen32.this, SignUpScreen3.class);
                              intent5.putExtra("currentPhoneNumber", currentUpi);
                              intent5.putExtra("currentUserName", currentUsername);
                              intent5.putExtra("currentPassword", currentPassword);
                              intent5.putExtra("currentName", currentName);
                              intent5.putExtra("userId", userId);
                              startActivity(intent5);
                          }
                      }
                  });
              }
          }, 1000L);
      }
      else
      {
          Intent noint=new Intent(SignUpScreen32.this,NoInternet.class);
          startActivity(noint);
      }
    }

    public boolean hasInternetConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
    }

}