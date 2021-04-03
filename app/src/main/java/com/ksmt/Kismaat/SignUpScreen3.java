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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class SignUpScreen3 extends AppCompatActivity {

    private ImageView greentick;
    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private FirebaseAuth auth;
    private String otpsent;
    private String enteredotp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_screen3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);


        final Intent intent5=getIntent();
        currentUpi=(String)intent5.getStringExtra("currentPhoneNumber");
        currentUsername=(String)intent5.getStringExtra("currentUserName");
        currentPassword=(String)intent5.getStringExtra("currentPassword");
        currentName=(String)intent5.getStringExtra("currentName");
        userId=(String)intent5.getStringExtra("userId");
        otpsent=(String)intent5.getStringExtra("otpsent");
        enteredotp=(String)intent5.getStringExtra("enteredotp");

       try {


           auth = FirebaseAuth.getInstance();

           HashMap<String, Object> fullname = new HashMap<>();
           fullname.put("Name", currentName);
           HashMap<String, Object> username = new HashMap<>();
           username.put("Username", currentUsername);
           HashMap<String, Object> passw = new HashMap<>();
           passw.put("Password", currentPassword);
           HashMap<String, Object> upi = new HashMap<>();
           upi.put("UPI PHONE", currentUpi);

           FirebaseFirestore clientinfo = FirebaseFirestore.getInstance();
           clientinfo.collection("REGISTERED NAME").document(userId).set(fullname);
           clientinfo.collection("REGISTERED NAME").document(userId).set(username, SetOptions.merge());
           clientinfo.collection("REGISTERED NAME").document(userId).set(passw, SetOptions.merge());
           clientinfo.collection("REGISTERED NAME").document(userId).set(upi, SetOptions.merge());

           greentick = (ImageView) findViewById(R.id.green);
           greentick.setTranslationY(-1000f);
           greentick.setImageResource(R.drawable.done);

           greentick.animate().translationYBy(1000f).setDuration(1000);

           if (hasInternetConnectivity()) {

               Handler mHandler = new Handler();
               mHandler.postDelayed(new Runnable() {

                   @Override
                   public void run() {
                       Intent intent6 = new Intent(SignUpScreen3.this, UserAccountScreen1.class);
                       intent6.putExtra("currentName", currentName);
                       intent6.putExtra("currentPhoneNumber", currentUpi);
                       intent6.putExtra("currentUserName", currentUsername);
                       intent6.putExtra("currentPassword", currentPassword);
                       intent6.putExtra("userId", userId);
                       intent6.putExtra("otpsent", otpsent);
                       intent6.putExtra("enteredotp", enteredotp);
                       startActivity(intent6);
                       finish();
                   }

               }, 4000L);
           } else {
               Intent noint = new Intent(SignUpScreen3.this, NoInternet.class);
               startActivity(noint);
           }
       }
       catch (Exception e)
       {
           Toast.makeText(this, "Something Went Wrong, please try again", Toast.LENGTH_SHORT).show();
           e.getStackTrace();
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