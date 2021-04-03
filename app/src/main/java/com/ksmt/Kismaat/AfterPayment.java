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
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class AfterPayment extends AppCompatActivity {

    private ImageView greentick;
    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String ticktotal;
    private String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_after_payment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        final Intent afterpay=getIntent();


        currentUpi=(String)afterpay.getStringExtra("currentPhoneNumber");
        currentUsername=(String)afterpay.getStringExtra("currentUserName");
        currentPassword=(String)afterpay.getStringExtra("currentPassword");
        currentName=(String)afterpay.getStringExtra("currentName");
        userId=(String)afterpay.getStringExtra("userId");
        ticktotal=(String)afterpay.getStringExtra("tickettotal");
        amount=(String)afterpay.getStringExtra("googlepayamount");

        greentick=(ImageView)findViewById(R.id.green12);
        greentick.setTranslationY(-1000f);
        greentick.setImageResource(R.drawable.done);

        greentick.animate().translationYBy(1000f).setDuration(1000);

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

            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {

                   try {

                       Intent afterpay2 = new Intent(AfterPayment.this, LoadingScreen3.class);
                       afterpay2.putExtra("currentName", currentName);
                       afterpay2.putExtra("currentPhoneNumber", currentUpi);
                       afterpay2.putExtra("currentUserName", currentUsername);
                       afterpay2.putExtra("currentPassword", currentPassword);
                       afterpay2.putExtra("userId", userId);
                       afterpay2.putExtra("tickettotal", ticktotal);
                       afterpay2.putExtra("gpayamount", amount);
                       startActivity(afterpay2);
                       finish();
                   }
                   catch (Exception e)
                   {
                       Toast.makeText(AfterPayment.this, "Something went wrong, please contact Kismaat", Toast.LENGTH_SHORT).show();
                       e.getStackTrace();
                   }
                }

            }, 5000L);
        }
        else
        {
            Intent noint=new Intent(AfterPayment.this,NoInternet.class);
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

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

}