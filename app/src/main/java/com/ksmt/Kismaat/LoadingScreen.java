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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoadingScreen extends AppCompatActivity {

    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String contest1entry;
    private String contest1win1;
    private String contest1win2;
    private String contest1win3;
    private String contest1win4;
    private String contest1ticket;


    private String contest2entry;
    private String contest2win1;
    private String contest2win2;
    private String contest2win3;
    private String contest2win4;
    private String contest2ticket;


    private String contest3entry;
    private String contest3win1;
    private String contest3win2;
    private String contest3win3;
    private String contest3win4;
    private String contest3ticket;

    private String contest4entry;
    private String contest4win1;
    private String contest4win2;
    private String contest4win3;
    private String contest4win4;
    private String contest4ticket;

    private String contest5entry;
    private String contest5win1;
    private String contest5win2;
    private String contest5win3;
    private String contest5win4;
    private String contest5ticket;
    private String docvalue;

    private ImageView  loadGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent loadingscreen=getIntent();
        currentUpi=(String)loadingscreen.getStringExtra("currentPhoneNumber");
        currentUsername=(String)loadingscreen.getStringExtra("currentUserName");
        currentPassword=(String)loadingscreen.getStringExtra("currentPassword");
        currentName=(String)loadingscreen.getStringExtra("currentName");
        userId=(String)loadingscreen.getStringExtra("userId");

        loadGif=(ImageView)findViewById(R.id.imageView8);

        Glide.with(this)
                .load(R.drawable.gifload)
                .into(loadGif);



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
               DocumentReference doRef = FirebaseFirestore.getInstance().collection("REGISTERED NAME").document("NewContests");
               doRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isSuccessful()) {
                           DocumentSnapshot doc = task.getResult();

                           assert doc != null;
                           contest1ticket = (String) doc.get("contest1tickettotal");
                           contest1entry = (String) doc.get("contestentryfee" + contest1ticket);
                           contest1win1 = (String) doc.get("contest1win1fee");
                           contest1win2 = (String) doc.get("contest1win2fee");
                           contest1win3 = (String) doc.get("contest1win3fee");
                           contest1win4 = (String) doc.get("contest1win4fee");


                           contest2ticket = (String) doc.get("contest2tickettotal");
                           contest2entry = (String) doc.get("contestentryfee" + contest2ticket);
                           contest2win1 = (String) doc.get("contest2win1fee");
                           contest2win2 = (String) doc.get("contest2win2fee");
                           contest2win3 = (String) doc.get("contest2win3fee");
                           contest2win4 = (String) doc.get("contest2win4fee");

                           contest3ticket = (String) doc.get("contest3tickettotal");
                           contest3entry = (String) doc.get("contestentryfee" + contest3ticket);
                           contest3win1 = (String) doc.get("contest3win1fee");
                           contest3win2 = (String) doc.get("contest3win2fee");
                           contest3win3 = (String) doc.get("contest3win3fee");
                           contest3win4 = (String) doc.get("contest3win4fee");

                           contest4ticket = (String) doc.get("contest4tickettotal");
                           contest4entry = (String) doc.get("contestentryfee" + contest4ticket);
                           contest4win1 = (String) doc.get("contest4win1fee");
                           contest4win2 = (String) doc.get("contest4win2fee");
                           contest4win3 = (String) doc.get("contest4win3fee");
                           contest4win4 = (String) doc.get("contest4win4fee");

                           contest5ticket = (String) doc.get("contest5tickettotal");
                           contest5entry = (String) doc.get("contestentryfee" + contest5ticket);
                           contest5win1 = (String) doc.get("contest5win1fee");
                           contest5win2 = (String) doc.get("contest5win2fee");
                           contest5win3 = (String) doc.get("contest5win3fee");
                           contest5win4 = (String) doc.get("contest5win4fee");

                           nextPage();

                       } else {
                           Toast.makeText(LoadingScreen.this, "Something went wrong,please try again", Toast.LENGTH_SHORT).show();
                       }
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
            Intent noint=new Intent(LoadingScreen.this,NoInternet.class);
            startActivity(noint);
        }
    }
    private void nextPage()
    {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent userscreen2 = new Intent(LoadingScreen.this, UserAccountScreen2.class);
                userscreen2.putExtra("currentName",currentName);
                userscreen2.putExtra("currentPhoneNumber", currentUpi);
                userscreen2.putExtra("currentUserName", currentUsername);
                userscreen2.putExtra("currentPassword", currentPassword);
                userscreen2.putExtra("userId",userId);
                userscreen2.putExtra("contest1entry",contest1entry);
                userscreen2.putExtra("contest1win1",contest1win1);
                userscreen2.putExtra("contest1win2",contest1win2);
                userscreen2.putExtra("contest1win3",contest1win3);
                userscreen2.putExtra("contest1win4",contest1win4);
                userscreen2.putExtra("contest1ticket",contest1ticket);
                userscreen2.putExtra("contest2entry",contest2entry);
                userscreen2.putExtra("contest2win1",contest2win1);
                userscreen2.putExtra("contest2win2",contest2win2);
                userscreen2.putExtra("contest2win3",contest2win3);
                userscreen2.putExtra("contest2win4",contest2win4);
                userscreen2.putExtra("contest2ticket",contest2ticket);
                userscreen2.putExtra("contest3entry",contest3entry);
                userscreen2.putExtra("contest3win1",contest3win1);
                userscreen2.putExtra("contest3win2",contest3win2);
                userscreen2.putExtra("contest3win3",contest3win3);
                userscreen2.putExtra("contest3win4",contest3win4);
                userscreen2.putExtra("contest3ticket",contest3ticket);
                userscreen2.putExtra("contest4entry",contest4entry);
                userscreen2.putExtra("contest4win1",contest4win1);
                userscreen2.putExtra("contest4win2",contest4win2);
                userscreen2.putExtra("contest4win3",contest4win3);
                userscreen2.putExtra("contest4win4",contest4win4);
                userscreen2.putExtra("contest4ticket",contest4ticket);
                userscreen2.putExtra("contest5entry",contest5entry);
                userscreen2.putExtra("contest5win1",contest5win1);
                userscreen2.putExtra("contest5win2",contest5win2);
                userscreen2.putExtra("contest5win3",contest5win3);
                userscreen2.putExtra("contest5win4",contest5win4);
                userscreen2.putExtra("contest5ticket",contest5ticket);
                startActivity(userscreen2);
                finish();
            }

        }, 4000L);

    }

    public boolean hasInternetConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
    }
}