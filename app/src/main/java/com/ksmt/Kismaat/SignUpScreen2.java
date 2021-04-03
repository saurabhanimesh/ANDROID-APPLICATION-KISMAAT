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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpScreen2 extends AppCompatActivity {

    private int check;
    private EditText userName;
    private EditText password;
    private EditText passwordRe;
    private TextView info;
    private EditText upiphone;
    private ConstraintLayout cl2;
    private int sameUser=0;
    private FirebaseAuth auth;
    private int checkphonel = 0;
    private String userN;
    private String pass;
    private String phone;
    private int checkcorrectl = 0;
    private String currentName;
    private Button next2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_screen2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);




        Intent intent3=getIntent();
        currentName=(String)intent3.getStringExtra("current name");

        auth=FirebaseAuth.getInstance();

        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        passwordRe=(EditText)findViewById(R.id.passwordRe);
        next2=(Button)findViewById(R.id.next2);
        info=(TextView)findViewById(R.id.info);
        upiphone=(EditText)findViewById(R.id.upiphone);
        cl2=(ConstraintLayout)findViewById(R.id.lay2);


        cl2.setOnClickListener(new View.OnClickListener() {
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

            next2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userN = userName.getText().toString();
                    pass = password.getText().toString();
                    String passre = passwordRe.getText().toString();
                    phone = upiphone.getText().toString();
                    Pattern p = Pattern.compile("[^A-Za-z0-9.]");
                    Matcher m = p.matcher(userN);
                    boolean b = m.find();


                    if (TextUtils.isEmpty(userN) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(passre) || TextUtils.isEmpty(phone) || phone.length() < 10 || pass.length() < 5) {
                        userName.setText(null);
                        password.setText(null);
                        passwordRe.setText(null);
                        upiphone.setText(null);
                        if (phone.length() < 10) {
                            info.setText("Please enter a valid phone number");
                        } else {
                            if (pass.length() < 5) {
                                info.setText("Weak Password");
                            } else {
                                info.setText("Invalid Inputs");
                            }
                        }
                    } else {

                        FirebaseFirestore check = FirebaseFirestore.getInstance();

                        if (b || !(passwordRe.getText().toString().equals(password.getText().toString()))) {
                            userName.setText(null);
                            password.setText(null);
                            passwordRe.setText(null);
                            upiphone.setText(null);
                            if (b) {
                                info.setText("Invalid character/Number");
                            } else {
                                info.setText("Password mismatch");
                            }
                        } else {

                            checkcorrectl = 0;
                            checkphonel = 0;

                            check.collection("REGISTERED NAME").whereEqualTo("UPI PHONE", phone).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            info.setText("Phone number already registered");
                                            checkcorrectl = 1;
                                        }
                                        checkProceed(currentName, checkcorrectl);
                                    }
                                }
                            });
                        }

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
      else
      {
          next2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent noint=new Intent(SignUpScreen2.this,NoInternet.class);
                  startActivity(noint);
              }
          });
      }

    }




    /*public void infoGet(final HashMap username, final HashMap emailid, final HashMap password, final HashMap upiphone, String currentName, final String emailq, final String pascode, final String phonenumber)
    {
        final FirebaseFirestore clientinfo=FirebaseFirestore.getInstance();
        clientinfo.collection("REGISTERED NAME").whereEqualTo("Name",currentName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        String docu=(String)doc.getId();
                        clientinfo.collection("REGISTERED NAME").document(docu).set(username, SetOptions.merge());
                        clientinfo.collection("REGISTERED NAME").document(docu).set(emailid, SetOptions.merge());
                        clientinfo.collection("REGISTERED NAME").document(docu).set(password, SetOptions.merge());
                        clientinfo.collection("REGISTERED NAME").document(docu).set(upiphone , SetOptions.merge());
                        info.setText(" ");

                        Intent intent4=new Intent(SignUpScreen2.this, SignUpScreen23.class);
                        intent4.putExtra("currentPhoneNumber",phonenumber);
                        intent4.putExtra("currentUserName",emailq);
                        intent4.putExtra("currentPassword",pascode);
                        startActivity(intent4);
                    }
                }
                else {
                    info.setText("Something went wrong");
                }

            }
        });


    }*/

    public void checkProceed(String currentName,int value)
    {
        if(value!=1) {
            Intent intent4 = new Intent(SignUpScreen2.this, SignUpScreen23.class);
            intent4.putExtra("currentPhoneNumber", phone);
            intent4.putExtra("currentUserName", userN);
            intent4.putExtra("currentPassword", pass);
            intent4.putExtra("currentName", currentName);
            startActivity(intent4);
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