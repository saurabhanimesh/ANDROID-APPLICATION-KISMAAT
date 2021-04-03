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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ksmt.Kismaat.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class UserAccountScreen2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Integer i;
    private TextView check;
    public ArrayList<String> entryfee=new ArrayList<String>();
    public ArrayList<String> googlepayamount=new ArrayList<String>();
    public ArrayList<String> winnamount=new ArrayList<String>();
    public ArrayList<String> ticketava=new ArrayList<String>();
    private RecyclerView recycleView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_account_screen2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Intent  userscreen2=getIntent();
        currentUpi=(String)userscreen2.getStringExtra("currentPhoneNumber");
        currentUsername=(String)userscreen2.getStringExtra("currentUserName");
        currentPassword=(String)userscreen2.getStringExtra("currentPassword");
        currentName=(String)userscreen2.getStringExtra("currentName");
        userId=(String)userscreen2.getStringExtra("userId");
        contest1entry=(String)userscreen2.getStringExtra("contest1entry");
        contest1win1=(String)userscreen2.getStringExtra("contest1win1");
        contest1win2=(String)userscreen2.getStringExtra("contest1win2");
        contest1win3=(String)userscreen2.getStringExtra("contest1win3");
        contest1win4=(String)userscreen2.getStringExtra("contest1win4");
        contest1ticket=(String)userscreen2.getStringExtra("contest1ticket");
        contest2entry=(String)userscreen2.getStringExtra("contest2entry");
        contest2win1=(String)userscreen2.getStringExtra("contest2win1");
        contest2win2=(String)userscreen2.getStringExtra("contest2win2");
        contest2win3=(String)userscreen2.getStringExtra("contest2win3");
        contest2win4=(String)userscreen2.getStringExtra("contest2win4");
        contest2ticket=(String)userscreen2.getStringExtra("contest2ticket");
        contest3entry=(String)userscreen2.getStringExtra("contest3entry");
        contest3win1=(String)userscreen2.getStringExtra("contest3win1");
        contest3win2=(String)userscreen2.getStringExtra("contest3win2");
        contest3win3=(String)userscreen2.getStringExtra("contest3win3");
        contest3win4=(String)userscreen2.getStringExtra("contest3win4");
        contest3ticket=(String)userscreen2.getStringExtra("contest3ticket");
        contest4entry=(String)userscreen2.getStringExtra("contest4entry");
        contest4win1=(String)userscreen2.getStringExtra("contest4win1");
        contest4win2=(String)userscreen2.getStringExtra("contest4win2");
        contest4win3=(String)userscreen2.getStringExtra("contest4win3");
        contest4win4=(String)userscreen2.getStringExtra("contest4win4");
        contest4ticket=(String)userscreen2.getStringExtra("contest4ticket");
        contest5entry=(String)userscreen2.getStringExtra("contest5entry");
        contest5win1=(String)userscreen2.getStringExtra("contest5win1");
        contest5win2=(String)userscreen2.getStringExtra("contest5win2");
        contest5win3=(String)userscreen2.getStringExtra("contest5win3");
        contest5win4=(String)userscreen2.getStringExtra("contest5win4");
        contest5ticket=(String)userscreen2.getStringExtra("contest5ticket");


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

               if (contest1entry != null) {
                   entryfee.add("Rs " + contest1entry);
                   googlepayamount.add(contest1entry);
                   winnamount.add("1st Rs " + contest1win1);
                   winnamount.add("2nd Rs " + contest1win2);
                   winnamount.add("3rd Rs " + contest1win3);
                   winnamount.add("4th Rs " + contest1win4);
                   ticketava.add(contest1ticket);
               }


               if (contest2entry != null) {
                   entryfee.add("Rs " + contest2entry);
                   googlepayamount.add(contest2entry);
                   winnamount.add("1st Rs " + contest2win1);
                   winnamount.add("2nd Rs " + contest2win2);
                   winnamount.add("3rd Rs " + contest2win3);
                   winnamount.add("4th Rs " + contest2win4);
                   ticketava.add(contest2ticket);
               }

               if (contest3entry != null) {
                   entryfee.add("Rs " + contest3entry);
                   googlepayamount.add(contest3entry);
                   winnamount.add("1st Rs " + contest3win1);
                   winnamount.add("2nd Rs " + contest3win2);
                   winnamount.add("3rd Rs " + contest3win3);
                   winnamount.add("4th Rs " + contest3win4);
                   ticketava.add(contest3ticket);
               }

               if (contest4entry != null) {
                   entryfee.add("Rs " + contest4entry);
                   googlepayamount.add(contest4entry);
                   winnamount.add("1st Rs " + contest4win1);
                   winnamount.add("2nd Rs " + contest4win2);
                   winnamount.add("3rd Rs " + contest4win3);
                   winnamount.add("4th Rs " + contest4win4);
                   ticketava.add(contest4ticket);
               }

               if (contest5entry != null) {
                   entryfee.add("Rs " + contest5entry);
                   googlepayamount.add(contest5entry);
                   winnamount.add("1st Rs " + contest5win1);
                   winnamount.add("2nd Rs " + contest5win2);
                   winnamount.add("3rd Rs " + contest5win3);
                   winnamount.add("4th Rs " + contest5win4);
                   ticketava.add(contest5ticket);
               }

               recycleView = (RecyclerView) findViewById(R.id.recycleview);
               int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
               recycleView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
               recycleView.setLayoutManager(new LinearLayoutManager(this));
               recycleView.setAdapter(new RecyclerViewAdapter(entryfee, winnamount, ticketava, currentUpi, currentUsername, currentName, currentPassword, userId, googlepayamount));
           } catch (Exception e)
           {
               Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
           }
       }
       else
       {
           Intent noint=new Intent(UserAccountScreen2.this,NoInternet.class);
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
}