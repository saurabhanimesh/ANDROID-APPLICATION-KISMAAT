package com.ksmt.Kismaat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class UserAccountScreen3 extends AppCompatActivity {

    private Button participate;
    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String gpayamount;
    private String ticktotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_account_screen3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Intent userscreen3=getIntent();
        currentUpi=(String)userscreen3.getStringExtra("currentPhoneNumber");
        currentUsername=(String)userscreen3.getStringExtra("currentUserName");
        currentPassword=(String)userscreen3.getStringExtra("currentPassword");
        currentName=(String)userscreen3.getStringExtra("currentName");
        userId=(String)userscreen3.getStringExtra("userId");
        gpayamount=(String)userscreen3.getStringExtra("googlepayamount");
        ticktotal=(String)userscreen3.getStringExtra("tickettotal");



        participate=(Button)findViewById(R.id.participate);

        if(hasInternetConnectivity()) {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                NotificationChannel channel =
                        new NotificationChannel("KismaatNotification","KismaatNotification", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager manager=getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            FirebaseMessaging.getInstance().subscribeToTopic("general")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });

           try {

               participate.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent payg = new Intent(UserAccountScreen3.this, GooglePayActivity.class);
                       payg.putExtra("currentName", currentName);
                       payg.putExtra("currentPhoneNumber", currentUpi);
                       payg.putExtra("currentUserName", currentUsername);
                       payg.putExtra("currentPassword", currentPassword);
                       payg.putExtra("userId", userId);
                       payg.putExtra("googlepayamount", gpayamount);
                       payg.putExtra("tickettotal", ticktotal);
                       startActivity(payg);
                   }
               });
           }
           catch (Exception e)
           {
               Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
           }
        }
        else
        {
            Intent noint=new Intent(UserAccountScreen3.this,NoInternet.class);
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