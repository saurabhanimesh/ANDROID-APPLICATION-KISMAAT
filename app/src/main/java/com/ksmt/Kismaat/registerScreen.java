package com.ksmt.Kismaat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registerScreen extends AppCompatActivity {

    private Button signUp;
    private Button signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        Intent intent1=getIntent();
        Intent signinreg=getIntent();


        signUp=(Button)findViewById(R.id.signUp);
        signIn=(Button)findViewById(R.id.signIn);

        if(hasInternetConnectivity()) {

          try {

              signIn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent signIn = new Intent(registerScreen.this, SignInScreen.class);
                      startActivity(signIn);
                  }
              });

              signUp.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent2 = new Intent(registerScreen.this, SignUpScreen1.class);
                      startActivity(intent2);
                  }
              });
          }
          catch (Exception e)
          {
              Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
              e.getStackTrace();
          }
        }
        else
        {
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(registerScreen.this,NoInternet.class);
                    startActivity(noint);
                }
            });

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(registerScreen.this,NoInternet.class);
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


    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

}