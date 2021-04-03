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
import android.text.util.Linkify;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class ResultLottery extends AppCompatActivity {

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
    private TextView winnercode1;
    private TextView winnername1;
    private TextView winnercode2;
    private TextView winnername2;
    private TextView winnercode3;
    private TextView winnername3;
    private TextView winnercode4;
    private TextView winnername4;
    private TextView messageforwinner;
    private String nameFind;
    private String nameFind2;
    private String nameFind3;
    private String nameFind4;
    private int i;
    private TextView paymentmessage;
    private  int[] ex ;
    private String timeDisplay;
    private String liveLink;
    private TextView getLiveLink;
    private String phoneFind;
    private String phoneFind2;
    private String phoneFind3;
    private String phoneFind4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result_lottery);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Intent result = getIntent();
        currentUpi = (String) result.getStringExtra("currentPhoneNumber");
        currentUsername = (String) result.getStringExtra("currentUserName");
        currentPassword = (String) result.getStringExtra("currentPassword");
        currentName = (String) result.getStringExtra("currentName");
        userId = (String) result.getStringExtra("userId");
        ticktotal = (String) result.getStringExtra("tickettotal");
        firstValueLottery = (String) result.getStringExtra("lotterycode");
        amount = (String) result.getStringExtra("gpayamount");
        nameFind = (String) result.getStringExtra("nameFind");
        nameFind2 = (String) result.getStringExtra("nameFind2");
        nameFind3 = (String) result.getStringExtra("nameFind3");
        nameFind4 = (String) result.getStringExtra("nameFind4");
        winningNumber = (String) result.getStringExtra("Winner1");
        winningNumber2 = (String) result.getStringExtra("Winner2");
        winningNumber3 = (String) result.getStringExtra("Winner3");
        winningNumber4 = (String) result.getStringExtra("Winner4");
        timeDisplay=(String)result.getStringExtra("TimeLive");
        liveLink=(String)result.getStringExtra("LinkLive");
        phoneFind=(String)result.getStringExtra("phoneFind");
        phoneFind2=(String)result.getStringExtra("phoneFind2");
        phoneFind3=(String)result.getStringExtra("phoneFind3");
        phoneFind4=(String)result.getStringExtra("phoneFind4");





        winnercode1 = (TextView) findViewById(R.id.winnercode1);
        winnername1 = (TextView) findViewById(R.id.winnername1);
        winnercode2 = (TextView) findViewById(R.id.winnercode2);
        winnername2 = (TextView) findViewById(R.id.winnername2);
        winnercode3 = (TextView) findViewById(R.id.winnercode3);
        winnername3 = (TextView) findViewById(R.id.winnername3);
        winnercode4 = (TextView) findViewById(R.id.winnercode4);
        winnername4 = (TextView) findViewById(R.id.winnername4);
        messageforwinner = (TextView) findViewById(R.id.messageforwinner);
        getLiveLink=(TextView)findViewById(R.id.linklive);



        index1 = firstValueLottery.indexOf("c");
        index2 = firstValueLottery.indexOf("A");
        contestnamevalue = firstValueLottery.substring(index1 + 1, index2);

        x = Integer.parseInt(contestnamevalue);


        index3 = firstValueLottery.indexOf("A");
        index4 = firstValueLottery.indexOf("N");
        contestnamevalue2 = firstValueLottery.substring(index3 + 1, index4);

        Log.d("nkop", "onCreate:1 "+ nameFind+ " "+ nameFind2 + " "+ nameFind3+ " "+ nameFind4 + " "+ contestnamevalue+ " "+ " "+contestnamevalue2 + " "+ " "+ winningNumber+" "+winningNumber2+ " "+winningNumber3+ " "+winningNumber4+" "+ firstValueLottery+ " "+ticktotal+ " "+ amount+ " "+ liveLink + " "+timeDisplay+ " "+phoneFind+ " "+phoneFind2+ " "+phoneFind3+ " "+phoneFind4);


        if(hasInternetConnectivity())
        {

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


               /*if (phoneFind != null) {
                   HashMap<String, Object> listsave = new HashMap<>();
                   listsave.put("PhoneNumber1", phoneFind);
                   listsave.put("PhoneNumber2", phoneFind2);
                   listsave.put("PhoneNumber3", phoneFind3);
                   listsave.put("PhoneNumber4", phoneFind4);

                   FirebaseFirestore fc = FirebaseFirestore.getInstance();
                   fc.collection("REGISTERED NAME").document("WinnersPhone"+contestnamevalue+"A"+contestnamevalue2).set(listsave).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                           } else {
                               Toast.makeText(ResultLottery.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }*/

               if (winningNumber != null) {
                   Log.d("namecheck ", nameFind);
                   winnercode1.setText("kc"+contestnamevalue+"A"+contestnamevalue2+"N"+winningNumber);
                   winnername1.setText(nameFind);
                   winnercode2.setText("kc"+contestnamevalue+"A"+contestnamevalue2+"N"+winningNumber2);
                   winnername2.setText(nameFind2);
                   winnercode3.setText("kc"+contestnamevalue+"A"+contestnamevalue2+"N"+winningNumber3);
                   winnername3.setText(nameFind3);
                   winnercode4.setText("kc"+contestnamevalue+"A"+contestnamevalue2+"N"+winningNumber4);
                   winnername4.setText(nameFind4);
                   messageforwinner.setText("Congratulations to everyone from our Team.");
                   getLiveLink.setText("Your prize money will get transfered within 5 hours.");
               } else {
                   if (timeDisplay != null) {
                       winnercode1.setText("To be declared soon");
                       winnername1.setText("To be declared soon");
                       winnercode2.setText("To be declared soon");
                       winnername2.setText("To be declared soon");
                       winnercode3.setText("To be declared soon");
                       winnername3.setText("To be declared soon");
                       winnercode4.setText("To be declared soon");
                       winnername4.setText("To be declared soon");
                       messageforwinner.setText("We will go Live for picking out the Lottery at " + timeDisplay + "pm today. Visit this link below at " + timeDisplay + "pm");
                       getLiveLink.setText(liveLink);
                       Linkify.addLinks(getLiveLink, Linkify.ALL);
                   } else {
                       winnercode1.setText("To be declared soon");
                       winnername1.setText("To be declared soon");
                       winnercode2.setText("To be declared soon");
                       winnername2.setText("To be declared soon");
                       winnercode3.setText("To be declared soon");
                       winnername3.setText("To be declared soon");
                       winnercode4.setText("To be declared soon");
                       winnername4.setText("To be declared soon");
                       messageforwinner.setText("There will be a Live Withdrawal of Lottery , once all tickets are sold out.");
                       getLiveLink.setText("We will provide you with the Live link , date and time here. We will notify you soon.");
                   }
               }
           }
           catch (Exception e)
           {
               Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
               e.getStackTrace();
           }

        }
        else
        {
            Intent noint=new Intent(ResultLottery.this,NoInternet.class);
            startActivity(noint);
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
        Intent intent6 = new Intent(ResultLottery.this, UserAccountScreen1.class);
        intent6.putExtra("currentName",currentName);
        intent6.putExtra("currentPhoneNumber", currentUpi);
        intent6.putExtra("currentUserName", currentUsername);
        intent6.putExtra("currentPassword", currentPassword);
        intent6.putExtra("userId",userId);
        startActivity(intent6);
    }

}