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
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LoadingScreen3 extends AppCompatActivity {

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
    private String amount;
    private String ticktotal;
    private ImageView loadGif;
    private String extra;
    private  String firstValueLottery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_screen3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);


        Intent user1 = getIntent();
        currentUpi = (String) user1.getStringExtra("currentPhoneNumber");
        currentUsername = (String) user1.getStringExtra("currentUserName");
        currentPassword = (String) user1.getStringExtra("currentPassword");
        currentName = (String) user1.getStringExtra("currentName");
        userId = (String) user1.getStringExtra("userId");
        amount=(String)user1.getStringExtra("gpayamount");
        ticktotal=(String)user1.getStringExtra("tickettotal");

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
                DocumentReference dof = FirebaseFirestore.getInstance().collection("REGISTERED NAME").document(userId);
                dof.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot dos = task.getResult();

                            assert dos != null;
                            bt = (String) dos.get("totalBuyed");

                            if (bt != null) {

                                z = Integer.parseInt(bt);

                                for (x = 0; x < z; x++) {
                                    lotterycode.add((String) dos.get("Contest"+x+"KSMT"));
                                }
                                Log.d("arraylist2", lotterycode.size() + " " + lotterycode + " " + bt);
                            }
                        } else {
                            Toast.makeText(LoadingScreen3.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                final DocumentReference doRef = FirebaseFirestore.getInstance().collection("REGISTERED NAME").document("Contest"+ticktotal+"A"+amount);
                doRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot dc = task.getResult();

                            Map<String, Object> hm;

                            assert dc != null;
                            hm = dc.getData();


                            assert hm != null;
                            for (Map.Entry<String, Object> firstValue : hm.entrySet()) {
                                Object fV = firstValue.getValue();
                                firstValueLottery = (String) fV;
                                break;
                            }
                            Set<String> ar=new LinkedHashSet<>();
                            ar = hm.keySet();

                            Iterator<String> itr = ar.iterator();

                            //get the first element
                            if (itr.hasNext()) {
                                extra = (String) itr.next();
                            }
                        }
                        else
                        {
                            Toast.makeText(LoadingScreen3.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                storedTickets();
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Something Went Wrong, Try again", Toast.LENGTH_SHORT).show();
                e.getStackTrace();
            }
        }
        else
        {
            Intent noint=new Intent(LoadingScreen3.this,NoInternet.class);
            startActivity(noint);
        }
    }
    public void storedTickets()
    {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent afterpay2 = new Intent(LoadingScreen3.this, DownloadTicket.class);
                afterpay2.putExtra("arrayList", lotterycode);
                afterpay2.putExtra("currentName",currentName);
                afterpay2.putExtra("currentPhoneNumber", currentUpi);
                afterpay2.putExtra("currentUserName", currentUsername);
                afterpay2.putExtra("currentPassword", currentPassword);
                afterpay2.putExtra("userId",userId);
                afterpay2.putExtra("gpayamount",amount);
                afterpay2.putExtra("tickettotal",ticktotal);
                afterpay2.putExtra("firstValueLottery",firstValueLottery);
                afterpay2.putExtra("extra",extra);
                startActivity(afterpay2);
            }

        }, 6000L);
    }

    public boolean hasInternetConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
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


}