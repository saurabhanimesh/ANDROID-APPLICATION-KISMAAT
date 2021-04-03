package com.ksmt.Kismaat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpScreen1 extends AppCompatActivity {

    private EditText firstName;
    private EditText secondName;
    private Button next;
    private TextView info1;
    private ConstraintLayout cl;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_screen1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Intent intent2 =getIntent();

        firstName=(EditText)findViewById(R.id.firstName);
        secondName=(EditText)findViewById(R.id.secondName);
        next=(Button)findViewById(R.id.next);
        info1=(TextView)findViewById(R.id.info1);
        cl=(ConstraintLayout)findViewById(R.id.lay);


        cl.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                }
                catch (Exception p)
                {
                    p.getStackTrace();
                }

            }
        });



      if(hasInternetConnectivity()) {

         try {

             next.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     String firstN = (String) firstName.getText().toString();
                     String secondN = (String) secondName.getText().toString();
                     String currentName = firstN + " " + secondN;
                     Pattern p = Pattern.compile("[^A-Za-z0-9.]");
                     Matcher m = p.matcher(firstN);
                     boolean b = m.find();
                     Pattern p2 = Pattern.compile("[^A-Za-z0-9.]");
                     Matcher m2 = p2.matcher(secondN);
                     boolean b2 = m2.find();

                     if (TextUtils.isEmpty(firstN) || TextUtils.isEmpty(secondN)) {
                         firstName.setText(null);
                         secondName.setText(null);
                         info1.setText("Enter a Name");
                     } else {
                         if (b || b2) {
                             firstName.setText(null);
                             secondName.setText(null);
                             info1.setText("Invalid character in name");
                         } else {
                             info1.setText(" ");
                             Intent intent3 = new Intent(SignUpScreen1.this, SignUpScreen2.class);
                             intent3.putExtra("current name", currentName);
                             startActivity(intent3);
                         }
                     }
                 }

             });
         }
         catch (Exception e)
         {
             Toast.makeText(this, "Something Went Wrong, please try again", Toast.LENGTH_SHORT).show();
             e.getStackTrace();
         }
      }
      else
      {
          next.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent noint=new Intent(SignUpScreen1.this,NoInternet.class);
                  startActivity(noint);
              }
          });
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