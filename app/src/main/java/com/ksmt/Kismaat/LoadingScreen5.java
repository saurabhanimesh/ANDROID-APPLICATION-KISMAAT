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
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoadingScreen5 extends AppCompatActivity {

    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private ImageView loadGif;
    private String addressOffice;
    private String chairman;
    private String OfficeNumber;
    private String workingHour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_screen5);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Intent loading5=getIntent();
        currentUpi=(String)loading5.getStringExtra("currentPhoneNumber");
        currentUsername=(String)loading5.getStringExtra("currentUserName");
        currentPassword=(String)loading5.getStringExtra("currentPassword");
        currentName=(String)loading5.getStringExtra("currentName");
        userId=(String)loading5.getStringExtra("userId");

        loadGif=(ImageView)findViewById(R.id.imageView8);

        Glide.with(this)
                .load(R.drawable.gifload)
                .into(loadGif);

       if(hasInternetConnectivity()) {

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               NotificationChannel channel =
                       new NotificationChannel("KismaatNotification", "KismaatNotification", NotificationManager.IMPORTANCE_DEFAULT);

               NotificationManager manager = getSystemService(NotificationManager.class);
               manager.createNotificationChannel(channel);
           }

           FirebaseMessaging.getInstance().subscribeToTopic("general")
                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                       }
                   });

           try {

               DocumentReference doc = FirebaseFirestore.getInstance().collection("REGISTERED NAME").document("About Details");
               doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isSuccessful()) {
                           DocumentSnapshot dc = task.getResult();

                           assert dc != null;
                           addressOffice = (String) dc.get("AddressOffice");
                           chairman = (String) dc.get("ChairMan");
                           OfficeNumber = (String) dc.get("SupportNumber");
                           workingHour = (String) dc.get("OfficeHour");

                           Log.d("cat", "nextPage: 1 " + (String) dc.get("AddressOffice") + " " + (String) dc.get("ChairMan") + " " + workingHour + " " + OfficeNumber);
                           nextPage(addressOffice, chairman, OfficeNumber, workingHour);
                       } else {
                           Toast.makeText(LoadingScreen5.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                       }

                   }
               });
           } catch (Exception e)
           {
               Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
               e.getStackTrace();
           }
       }
       else
       {
           Intent noint=new Intent(LoadingScreen5.this,NoInternet.class);
           startActivity(noint);
       }
    }

    public void nextPage(final String addressOffice, final String chairman, final String OfficeNumber, final String workingHour)
    {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("cat", "nextPage: 2" + addressOffice + " "+ chairman + " "+ workingHour+ " "+ OfficeNumber);

                Intent profile=new Intent(LoadingScreen5.this,ProfileScreen.class);
                profile.putExtra("currentName",currentName);
                profile.putExtra("currentPhoneNumber", currentUpi);
                profile.putExtra("currentUserName", currentUsername);
                profile.putExtra("currentPassword", currentPassword);
                profile.putExtra("userId",userId);
                profile.putExtra("AddressOffice",addressOffice);
                profile.putExtra("ChairMan",chairman);
                profile.putExtra("SupportNumber",OfficeNumber);
                profile.putExtra("OfficeHour",workingHour);
                startActivity(profile);
            }
        }, 5000L);

    }

    public boolean hasInternetConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
    }

}