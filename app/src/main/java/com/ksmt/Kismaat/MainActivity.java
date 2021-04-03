package com.ksmt.Kismaat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity<getR> extends AppCompatActivity {

    private EditText tv;
    Button button;
    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String cu;
    private FirebaseAuth auth;
    private String otpsent;
    private String enteredotp;
    private String codesent;
    private String uc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


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


        auth=FirebaseAuth.getInstance();

        SharedPreferences getShared1=getSharedPreferences("currentUpi",MODE_PRIVATE);
        currentUpi=getShared1.getString("currentUpi","check2");

        SharedPreferences getShared2=getSharedPreferences("otpsent",MODE_PRIVATE);
        otpsent=getShared2.getString("otpsent","check3");

        SharedPreferences getShared3=getSharedPreferences("enteredotp",MODE_PRIVATE);
        enteredotp=getShared3.getString("enteredotp","check4");


        FirebaseFirestore fs=FirebaseFirestore.getInstance();
        fs.collection("REGISTERED NAME").whereEqualTo("UPI PHONE",currentUpi).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc: Objects.requireNonNull(task.getResult()))
                    {
                        currentUsername=(String)doc.get("Username");
                        currentName=(String)doc.get("Name");
                        currentPassword=(String)doc.get("Password");
                        userId=(String)doc.getId();
                    }
                }
            }
        });


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                SharedPreferences getShared=getSharedPreferences("check",MODE_PRIVATE);
                boolean value=getShared.getBoolean("checkstatus",false);
                if(user !=  null)
                {
                    Intent intentmain = new Intent(MainActivity.this, UserAccountScreen1.class);
                    intentmain.putExtra("currentName", currentName);
                    intentmain.putExtra("currentPhoneNumber", currentUpi);
                    intentmain.putExtra("currentUserName", currentUsername);
                    intentmain.putExtra("currentPassword", currentPassword);
                    intentmain.putExtra("userId", userId);
                    startActivity(intentmain);
                }
                else {
                    Intent intent1 = new Intent(MainActivity.this, registerScreen.class);
                    startActivity(intent1);
                }
            }

        }, 4000L);
    }

}