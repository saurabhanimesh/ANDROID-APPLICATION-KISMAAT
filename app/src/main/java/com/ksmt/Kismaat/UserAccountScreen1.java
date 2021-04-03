package com.ksmt.Kismaat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class UserAccountScreen1 extends AppCompatActivity {

    private Button logout;
    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private Button booklottery;
    private Button bookedlottery;
    private Button yourprofile;
    boolean status=false;
    private String cu;
    private String otpsent;
    private String enteredotp;
    private String ticktotal;
    private String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_account_screen1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Intent intent6=getIntent();
        currentUpi=(String)intent6.getStringExtra("currentPhoneNumber");
        currentUsername=(String)intent6.getStringExtra("currentUserName");
        currentPassword=(String)intent6.getStringExtra("currentPassword");
        currentName=(String)intent6.getStringExtra("currentName");
        userId=(String)intent6.getStringExtra("userId");
        otpsent=(String)intent6.getStringExtra("otpsent");
        enteredotp=(String)intent6.getStringExtra("enteredotp");
        amount=(String)intent6.getStringExtra("gpayamount");
        ticktotal=(String)intent6.getStringExtra("tickettotal");



        Intent intentin=getIntent();
        currentUpi=(String)intentin.getStringExtra("currentPhoneNumber");
        currentUsername=(String)intentin.getStringExtra("currentUserName");
        currentPassword=(String)intentin.getStringExtra("currentPassword");
        currentName=(String)intentin.getStringExtra("currentName");
        userId=(String)intentin.getStringExtra("userId");
        otpsent=(String)intent6.getStringExtra("otpsent");
        enteredotp=(String)intent6.getStringExtra("enteredotp");



        Intent intentmain=getIntent();
        currentUpi=(String)intentmain.getStringExtra("currentPhoneNumber");
        currentUsername=(String)intentmain.getStringExtra("currentUserName");
        currentPassword=(String)intentmain.getStringExtra("currentPassword");
        currentName=(String)intentmain.getStringExtra("currentName");
        userId=(String)intentmain.getStringExtra("userId");

        logout = (Button) findViewById(R.id.logout);
        booklottery = (Button) findViewById(R.id.booklottery);
        bookedlottery = (Button) findViewById(R.id.bookedlottery);
        yourprofile = (Button) findViewById(R.id.yourprofile);


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

               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

               boolean status = true;

               SharedPreferences shrd = getSharedPreferences("check", MODE_PRIVATE);
               SharedPreferences.Editor edit = shrd.edit();

               SharedPreferences hrd = getSharedPreferences("currentUpi", MODE_PRIVATE);
               final SharedPreferences.Editor edit2 = hrd.edit();

               SharedPreferences hrda = getSharedPreferences("otpsent", MODE_PRIVATE);
               final SharedPreferences.Editor edit3 = hrda.edit();

               SharedPreferences hrdap = getSharedPreferences("enteredotp", MODE_PRIVATE);
               final SharedPreferences.Editor edit4 = hrdap.edit();


               Log.d("checkuser", "onCreate: " + user);
               Log.d("checkuser", "onCreate: " + currentUsername + " " + currentPassword + " " + currentName + " " + currentUpi + " " + otpsent + " " + enteredotp + " " + userId);

               edit.putBoolean("checkstatus", status);
               edit.apply();
               edit2.putString("currentUpi", currentUpi);
               edit2.apply();
               edit3.putString("otpsent", otpsent);
               edit3.apply();
               edit4.putString("enteredotp", enteredotp);
               edit4.apply();

               booklottery.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent loadingscreen = new Intent(UserAccountScreen1.this, LoadingScreen.class);
                       loadingscreen.putExtra("currentName", currentName);
                       loadingscreen.putExtra("currentPhoneNumber", currentUpi);
                       loadingscreen.putExtra("currentUserName", currentUsername);
                       loadingscreen.putExtra("currentPassword", currentPassword);
                       loadingscreen.putExtra("userId", userId);
                       startActivity(loadingscreen);
                   }
               });

               bookedlottery.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent user1 = new Intent(UserAccountScreen1.this, LoadingScreen2.class);
                       user1.putExtra("currentName", currentName);
                       user1.putExtra("currentPhoneNumber", currentUpi);
                       user1.putExtra("currentUserName", currentUsername);
                       user1.putExtra("currentPassword", currentPassword);
                       user1.putExtra("userId", userId);
                       user1.putExtra("gpayamount", amount);
                       user1.putExtra("tickettotal", ticktotal);
                       startActivity(user1);
                   }
               });

               yourprofile.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent profile = new Intent(UserAccountScreen1.this, LoadingScreen5.class);
                       profile.putExtra("currentName", currentName);
                       profile.putExtra("currentPhoneNumber", currentUpi);
                       profile.putExtra("currentUserName", currentUsername);
                       profile.putExtra("currentPassword", currentPassword);
                       profile.putExtra("userId", userId);
                       startActivity(profile);
                   }
               });


               logout.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       edit2.clear();
                       edit3.clear();
                       edit4.clear();

                       boolean status = false;
                       SharedPreferences shrd = getSharedPreferences("check", MODE_PRIVATE);
                       SharedPreferences.Editor edit = shrd.edit();

                       edit.putBoolean("checkstatus", status);
                       edit.apply();

                       FirebaseAuth.getInstance().signOut();

                       Intent signout = new Intent(UserAccountScreen1.this, SignInScreen.class);
                       startActivity(signout);
                   }
               });

           }
           catch (Exception e)
           {
               Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
               e.getStackTrace();
           }
        }
        else {
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(UserAccountScreen1.this,NoInternet.class);
                    startActivity(noint);
                }
            });

            booklottery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(UserAccountScreen1.this,NoInternet.class);
                    startActivity(noint);
                }
            });

            bookedlottery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(UserAccountScreen1.this,NoInternet.class);
                    startActivity(noint);
                }
            });

            yourprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(UserAccountScreen1.this,NoInternet.class);
                    startActivity(noint);
                }
            });
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