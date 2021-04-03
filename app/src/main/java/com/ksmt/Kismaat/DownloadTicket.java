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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ksmt.Kismaat.adapter.RecyclerViewAdapter2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class DownloadTicket extends AppCompatActivity {

    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private RecyclerView rV;
    private String lottery;
    private Integer lotteryI;
    private String ticktotal;
    private  String firstValueLottery;
    private  int indexarray=0;
    private ArrayList<String> lotterycode=new ArrayList<String>();
    private String extra;
    private String bt;
    private String arraysize;
    //Set<String> set=new LinkedHashSet<String>();
    //Set<String> setextra=new LinkedHashSet<String>();
    private int x;
    private int z;
    private String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_download_ticket);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        //SharedPreferences getShared1 = getSharedPreferences("lotteryValue", MODE_PRIVATE);
        //indexarray = getShared1.getInt("lotteryCode", 0);


        /*if(indexarray >0) {
            SharedPreferences getShared2 = getSharedPreferences("lotteryString", MODE_PRIVATE);
            set = getShared2.getStringSet("lotteryStringValue",setextra);

            Log.d("arraylist",   " "+set + " "+ indexarray);

        }*/


        rV = (RecyclerView) findViewById(R.id.recycle);


        Intent afterpay2 = getIntent();

       try {
           currentUpi = (String) afterpay2.getStringExtra("currentPhoneNumber");
           currentUsername = (String) afterpay2.getStringExtra("currentUserName");
           currentPassword = (String) afterpay2.getStringExtra("currentPassword");
           currentName = (String) afterpay2.getStringExtra("currentName");
           userId = (String) afterpay2.getStringExtra("userId");
           ticktotal = (String) afterpay2.getStringExtra("tickettotal");
           amount = (String) afterpay2.getStringExtra("gpayamount");
           lotterycode = (ArrayList<String>) getIntent().getSerializableExtra("arrayList");
           extra = (String) afterpay2.getStringExtra("extra");
           firstValueLottery = (String) afterpay2.getStringExtra("firstValueLottery");
       }
       catch (Exception e)
       {
           Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
           e.getStackTrace();
       }


        Intent user1 = getIntent();
        currentUpi = (String) user1.getStringExtra("currentPhoneNumber");
        currentUsername = (String) user1.getStringExtra("currentUserName");
        currentPassword = (String) user1.getStringExtra("currentPassword");
        currentName = (String) user1.getStringExtra("currentName");
        userId = (String) user1.getStringExtra("userId");


        if (hasInternetConnectivity()) {


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
                    /*Log.d("arraylist8", bt + "bu");
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
                                processing(firstValueLottery);
                            }
                            else
                            {
                                Toast.makeText(DownloadTicket.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/

                    processing(firstValueLottery);


                } catch (Exception e) {
                    Toast.makeText(DownloadTicket.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                    e.getStackTrace();
                }
    }
       else {
           Intent noint=new Intent(DownloadTicket.this,NoInternet.class);
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


    private void processing(String v1) {

      try{

        lotterycode.add(v1);
        Log.d("arraylist8", lotterycode + " " + firstValueLottery + " " + extra);

        Log.d("arraylist8", lotterycode + "");


        HashMap<String, Object> listsave = new HashMap<>();
        HashMap<String, Object> listsave2 = new HashMap<>();

        HashMap<String, Object> buyedticket = new HashMap<>();

        for (x = 0; x < lotterycode.size(); x++) {
            listsave.put("Contest"+x+"KSMT", (String) lotterycode.get(x));
            listsave.put(firstValueLottery, (String) lotterycode.get(x));
        }

        arraysize = String.valueOf(lotterycode.size());

        Log.d("arraylist8", lotterycode.size() + " thu");
        buyedticket.put("totalBuyed", arraysize);

        FirebaseFirestore saveset = FirebaseFirestore.getInstance();
        saveset.collection("REGISTERED NAME").document(userId).set(listsave, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                    Toast.makeText(DownloadTicket.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveset.collection("REGISTERED NAME").document(userId).set(listsave2, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    Toast.makeText(DownloadTicket.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveset.collection("REGISTERED NAME").document(userId).set(buyedticket, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    Toast.makeText(DownloadTicket.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //SharedPreferences shrd=getSharedPreferences("lotteryValue",MODE_PRIVATE);
        //SharedPreferences.Editor edit = shrd.edit();

        //indexarray++;

        //edit.putInt("lotteryCode",indexarray);
        //edit.apply();

        //SharedPreferences shrd2=getSharedPreferences("lotteryString",MODE_PRIVATE);
        //SharedPreferences.Editor edit2 = shrd2.edit();

        //edit2.putStringSet("lotteryStringValue",set);
        //edit2.apply();


        final DocumentReference doRef2 = FirebaseFirestore.getInstance().collection("REGISTERED NAME").document("Contest"+ticktotal+"A" + amount);
        doRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("arraylist5", ticktotal + " " + amount);
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    if (documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        assert map != null;
                        Log.d("arraylist3", map + " " + map.size());
                        if (map.size() == 1) {

                            Map<String, Object> updates = new HashMap<>();
                            updates.put(extra, FieldValue.delete());

                            Log.d("arraylist5", lotterycode + "" + extra);


                            doRef2.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("firstvaluy", "onComplete: " + "DELETE SUCCESSFULL");
                                }
                            });

                            FirebaseFirestore contestremove = FirebaseFirestore.getInstance();

                            contestremove.collection("REGISTERED NAME").whereEqualTo("contestentryfee" + ticktotal, amount).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (queryDocumentSnapshots.isEmpty()) {
                                    } else {
                                        DocumentReference doRef3 = FirebaseFirestore.getInstance().collection("REGISTERED NAME").document("NewContests");
                                        Map<String, Object> updates = new HashMap<>();
                                        updates.put("contestentryfee" + ticktotal, FieldValue.delete());

                                        doRef3.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                                                    rV.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
                                                    rV.setLayoutManager(new LinearLayoutManager(DownloadTicket.this));
                                                    rV.setAdapter(new RecyclerViewAdapter2(lotterycode, currentUpi, currentUsername, currentName, currentPassword, userId, ticktotal, amount));
                                                } else {
                                                    Toast.makeText(DownloadTicket.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                        } else {

                            Map<String, Object> updates = new HashMap<>();
                            updates.put(extra, FieldValue.delete());

                            Log.d("arraylist5", lotterycode + "" + extra);


                            doRef2.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("firstvaluy", "onComplete: " + "DELETE SUCCESSFULL");
                                    } else {
                                        Toast.makeText(DownloadTicket.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                            rV.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
                            rV.setLayoutManager(new LinearLayoutManager(DownloadTicket.this));
                            rV.setAdapter(new RecyclerViewAdapter2(lotterycode, currentUpi, currentUsername, currentName, currentPassword, userId, ticktotal, amount));
                        }
                    } else {
                        Toast.makeText(DownloadTicket.this, "Something went wrong ,try again later", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DownloadTicket.this, "Something went wrong ,try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
     }
      catch (Exception e)
      {
          Toast.makeText(this, "Something Went Wrong, Contact Kismaat", Toast.LENGTH_SHORT).show();
          e.getStackTrace();
      }
    }

    @Override
    public void onBackPressed() {
        Intent intent6 = new Intent(DownloadTicket.this, UserAccountScreen1.class);
        intent6.putExtra("currentName",currentName);
        intent6.putExtra("currentPhoneNumber", currentUpi);
        intent6.putExtra("currentUserName", currentUsername);
        intent6.putExtra("currentPassword", currentPassword);
        intent6.putExtra("userId",userId);
        intent6.putExtra("gpayamount",amount);
        intent6.putExtra("tickettotal",ticktotal);
        startActivity(intent6);
    }
}