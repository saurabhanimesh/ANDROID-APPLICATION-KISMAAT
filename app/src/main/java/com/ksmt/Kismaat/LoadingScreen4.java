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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;
import java.util.Random;

public class LoadingScreen4 extends AppCompatActivity {

    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String ticktotal;
    private  String firstValueLottery;
    private String amount;
    private int index1;
    private int index2;
    private int index3;
    private int index4;
    private String contestnamevalue;
    private String contestnamevalue2;
    private int x;
    private String winningNumber;
    private String winningNumber2;
    private String winningNumber3;
    private String winningNumber4;
    private String nameFind;
    private String nameFind2;
    private String nameFind3;
    private String nameFind4;
    private String phoneFind;
    private String phoneFind2;
    private String phoneFind3;
    private String phoneFind4;
    private String timeDisplay;
    private String liveLink;
    private int i;
    private TextView paymentmessage;
    private  int[] ex ;
    private ImageView loadGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_screen3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Intent result=getIntent();
        currentUpi=(String)result.getStringExtra("currentPhoneNumber");
        currentUsername=(String)result.getStringExtra("currentUserName");
        currentPassword=(String)result.getStringExtra("currentPassword");
        currentName=(String)result.getStringExtra("currentName");
        userId=(String)result.getStringExtra("userId");
        ticktotal=(String)result.getStringExtra("tickettotal");
        firstValueLottery=(String)result.getStringExtra("lotterycode");
        amount=(String)result.getStringExtra("gpayamount");

        loadGif=(ImageView)findViewById(R.id.imageView8);

        Glide.with(this)
                .load(R.drawable.gifload)
                .into(loadGif);


        index1=firstValueLottery.indexOf("c");
        index2=firstValueLottery.indexOf("A");
        contestnamevalue=firstValueLottery.substring(index1+1,index2);


        index3=firstValueLottery.indexOf("A");
        index4=firstValueLottery.indexOf("N");
        contestnamevalue2=firstValueLottery.substring(index3+1,index4);

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

            DocumentReference doc = FirebaseFirestore.getInstance().collection("REGISTERED NAME").document("Winners" + contestnamevalue + "A" + contestnamevalue2);
            doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot dc = task.getResult();

                        assert dc != null;
                        winningNumber = (String) dc.get("Winner1");
                        winningNumber2 = (String) dc.get("Winner2");
                        winningNumber3 = (String) dc.get("Winner3");
                        winningNumber4 = (String) dc.get("Winner4");
                        timeDisplay = (String) dc.get("TimeLive");
                        liveLink = (String) dc.get("LinkLive");

                        Log.d("nko", "onComplete: 2" + " " + winningNumber + " " + winningNumber2 + " " + winningNumber3 + " " + winningNumber4 + " " + liveLink + " " + timeDisplay);

                        if (winningNumber != null || winningNumber2 != null || winningNumber3 != null || winningNumber4 != null) {
                            FirebaseFirestore fc = FirebaseFirestore.getInstance();
                            fc.collection("REGISTERED NAME").whereEqualTo("kc" + contestnamevalue + "A" + contestnamevalue2 + "N" + winningNumber, "kc" + contestnamevalue + "A" + contestnamevalue2 + "N" + winningNumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                            nameFind = (String) doc.get("Name");
                                            phoneFind = (String) doc.get("UPI PHONE");

                                        }
                                        Log.d("nko", "onComplete: 3" + " " + nameFind + " " + phoneFind);

                                    } else {
                                        Toast.makeText(LoadingScreen4.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            fc.collection("REGISTERED NAME").whereEqualTo("kc" + contestnamevalue + "A" + contestnamevalue2 + "N" + winningNumber2, "kc" + contestnamevalue + "A" + contestnamevalue2 + "N" + winningNumber2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                            nameFind2 = (String) doc.get("Name");
                                            phoneFind2 = (String) doc.get("UPI PHONE");
                                        }

                                    } else {
                                        Toast.makeText(LoadingScreen4.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            fc.collection("REGISTERED NAME").whereEqualTo("kc" + contestnamevalue + "A" + contestnamevalue2 + "N" + winningNumber3, "kc" + contestnamevalue + "A" + contestnamevalue2 + "N" + winningNumber3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                            nameFind3 = (String) doc.get("Name");
                                            phoneFind3 = (String) doc.get("UPI PHONE");
                                        }
                                    } else {
                                        Toast.makeText(LoadingScreen4.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            fc.collection("REGISTERED NAME").whereEqualTo("kc" + contestnamevalue + "A" + contestnamevalue2 + "N" + winningNumber4, "kc" + contestnamevalue + "A" + contestnamevalue2 + "N" + winningNumber4).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                            nameFind4 = (String) doc.get("Name");
                                            phoneFind4 = (String) doc.get("UPI PHONE");
                                            nextPage();
                                        }
                                    } else {
                                        Toast.makeText(LoadingScreen4.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            nextPage();
                        }
                    } else {
                        Toast.makeText(LoadingScreen4.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
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
          Intent noint=new Intent(LoadingScreen4.this,NoInternet.class);
          startActivity(noint);
      }
    }

    public void nextPage()
    {

        Log.d("nko", "onCreate:1 "+ nameFind+ " "+ nameFind2 + " "+ nameFind3+ " "+ nameFind4 + " "+ contestnamevalue+ " "+ " "+contestnamevalue2 + " "+ phoneFind+ " "+ phoneFind2 + " "+phoneFind3 + " "+ phoneFind4+ " "+ currentName+" "+currentPassword+ " "+currentUpi+ " "+currentUsername+" "+ firstValueLottery+ " "+ticktotal+ " "+ amount);



        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent result=new Intent(LoadingScreen4.this,ResultLottery.class);
                result.putExtra("currentName",currentName);
                result.putExtra("currentPhoneNumber", currentUpi);
                result.putExtra("currentUserName", currentUsername);
                result.putExtra("currentPassword", currentPassword);
                result.putExtra("userId",userId);
                result.putExtra("tickettotal",ticktotal);
                result.putExtra("lotterycode",firstValueLottery);
                result.putExtra("gpayamount",amount);
                result.putExtra("Winner1",winningNumber);
                result.putExtra("Winner2",winningNumber2);
                result.putExtra("Winner3",winningNumber3);
                result.putExtra("Winner4",winningNumber4);
                result.putExtra("nameFind",nameFind);
                result.putExtra("nameFind2",nameFind2);
                result.putExtra("nameFind3",nameFind3);
                result.putExtra("nameFind4",nameFind4);
                result.putExtra("LinkLive",liveLink);
                result.putExtra("TimeLive",timeDisplay);
                result.putExtra("phoneFind",phoneFind);
                result.putExtra("phoneFind2",phoneFind2);
                result.putExtra("phoneFind3",phoneFind3);
                result.putExtra("phoneFind4",phoneFind4);


                startActivity(result);
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

    public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }
}