package com.ksmt.Kismaat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ksmt.Kismaat.adapter.RecyclerViewAdapter2;

import java.util.ArrayList;

public class BookedTickets extends AppCompatActivity {

    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String presentTick;
    private ArrayList<String> lotterycode=new ArrayList<String>();
    private String bt;
    private int z;
    private int x;
    private RecyclerView rV;
    private String ticktotal;
    private String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_booked_tickets);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);


        Intent intent = getIntent();
       try {
           currentUpi = (String) intent.getStringExtra("currentPhoneNumber");
           currentUsername = (String) intent.getStringExtra("currentUserName");
           currentPassword = (String) intent.getStringExtra("currentPassword");
           currentName = (String) intent.getStringExtra("currentName");
           userId = (String) intent.getStringExtra("userId");
           lotterycode = (ArrayList<String>) getIntent().getSerializableExtra("arrayList");
           amount = (String) intent.getStringExtra("gpayamount");
           ticktotal = (String) intent.getStringExtra("tickettotal");
       }
       catch (Exception e)
       {
           e.getStackTrace();
           Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
       }

        rV = (RecyclerView) findViewById(R.id.recycle2);

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
              Log.d("kumar", "onCreate: 455");

              int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
              rV.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
              rV.setLayoutManager(new LinearLayoutManager(BookedTickets.this));
              rV.setAdapter(new RecyclerViewAdapter2(lotterycode, currentUpi, currentUsername, currentName, currentPassword, userId, ticktotal, amount));
          }
          catch (Exception e)
          {
              Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
          }
       }
       else {
           Intent noint=new Intent(BookedTickets.this,NoInternet.class);
           startActivity(noint);
       }
    }

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, @NonNull View view,
                                   RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    public boolean hasInternetConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
    }

    public void onBackPressed() {
        Intent intent6 = new Intent(BookedTickets.this, UserAccountScreen1.class);
        intent6.putExtra("currentName",currentName);
        intent6.putExtra("currentPhoneNumber", currentUpi);
        intent6.putExtra("currentUserName", currentUsername);
        intent6.putExtra("currentPassword", currentPassword);
        intent6.putExtra("userId",userId);
        startActivity(intent6);
    }
}