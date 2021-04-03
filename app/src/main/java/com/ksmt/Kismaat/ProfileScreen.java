package com.ksmt.Kismaat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileScreen extends AppCompatActivity {

    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String addressOffice;
    private String chairman;
    private String OfficeNumber;
    private String workingHour;
    private TextView nameShow;
    private TextView userNameShow;
    private TextView passShow;
    private TextView phoneShow;
    private TextView techNumber;
    private TextView addressOff;
    private TextView chairPerson;
    private TextView hourOffice;
    private Button editName;
    private Button editUsername;
    private Button editPassword;
    private Button editNumber;
    private Button confirmchanges;
    private EditText usernameNew;
    private EditText nameNew;
    private EditText passNew;
    private EditText upiNumberNew;
    private ConstraintLayout layoutside;
    private Button revertback;
    private String userNN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);


        Intent profile=getIntent();
        currentUpi=(String)profile.getStringExtra("currentPhoneNumber");
        currentUsername=(String)profile.getStringExtra("currentUserName");
        currentPassword=(String)profile.getStringExtra("currentPassword");
        currentName=(String)profile.getStringExtra("currentName");
        userId=(String)profile.getStringExtra("userId");
        addressOffice=(String)profile.getStringExtra("AddressOffice");
        chairman=(String)profile.getStringExtra("ChairMan");
        OfficeNumber=(String)profile.getStringExtra("SupportNumber");
        workingHour=(String)profile.getStringExtra("OfficeHour");


        nameShow=(TextView)findViewById(R.id.nameShow);
        userNameShow=(TextView)findViewById(R.id.userNameShow);
        passShow=(TextView)findViewById(R.id.passShow);
        phoneShow=(TextView)findViewById(R.id.phoneShow);
        techNumber=(TextView)findViewById(R.id.techNumber);
        addressOff=(TextView)findViewById(R.id.addressOff);
        chairPerson=(TextView)findViewById(R.id.chairPerson);
        hourOffice=(TextView)findViewById(R.id.hourOffice);
        editName=(Button)findViewById(R.id.editName);
        editNumber=(Button)findViewById(R.id.editNumber);
        confirmchanges=(Button)findViewById(R.id.confirmchanges);
        usernameNew=(EditText)findViewById(R.id.usernameNew);
        nameNew=(EditText)findViewById(R.id.nameNew);
        passNew=(EditText)findViewById(R.id.passNew);
        layoutside=(ConstraintLayout)findViewById(R.id.layoutside);
        revertback=(Button)findViewById(R.id.revertchange);


        confirmchanges.setVisibility(View.INVISIBLE);
        usernameNew.setVisibility(View.INVISIBLE);
        passNew.setVisibility(View.INVISIBLE);
        nameNew.setVisibility(View.INVISIBLE);





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


            nameShow.setText(currentName);
            userNameShow.setText(currentUsername);
            passShow.setText(currentPassword);
            phoneShow.setText(currentUpi);
            techNumber.setText(OfficeNumber);
            addressOff.setText(addressOffice);
            chairPerson.setText(chairman);
            hourOffice.setText(workingHour);

          try {


              layoutside.setOnClickListener(new View.OnClickListener() {
                  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                  @Override
                  public void onClick(View view) {
                      try {
                          InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                          imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                      } catch (Exception p) {
                          p.getStackTrace();
                      }
                  }
              });

              revertback.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      editName.setVisibility(View.VISIBLE);
                      confirmchanges.setVisibility(View.INVISIBLE);
                      usernameNew.setVisibility(View.INVISIBLE);
                      nameNew.setVisibility(View.INVISIBLE);
                      passNew.setVisibility(View.INVISIBLE);

                      userNameShow.setText(currentUsername);
                      nameShow.setText(currentName);
                      passShow.setText(currentPassword);

                  }
              });

              editName.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {


                      editName.setVisibility(View.INVISIBLE);

                      userNameShow.setText(null);
                      nameShow.setText(null);
                      passShow.setText(null);


                      confirmchanges.setVisibility(View.VISIBLE);
                      usernameNew.setVisibility(View.VISIBLE);
                      nameNew.setVisibility(View.VISIBLE);
                      passNew.setVisibility(View.VISIBLE);


                  }
              });


              confirmchanges.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      if (TextUtils.isEmpty(nameNew.getText().toString()) || TextUtils.isEmpty(usernameNew.getText().toString()) || TextUtils.isEmpty(passNew.getText().toString())) {
                          Toast.makeText(ProfileScreen.this, "Invalid Entries", Toast.LENGTH_SHORT).show();
                          confirmchanges.setVisibility(View.INVISIBLE);
                          usernameNew.setVisibility(View.INVISIBLE);
                          nameNew.setVisibility(View.INVISIBLE);
                          passNew.setVisibility(View.INVISIBLE);

                          userNameShow.setText(currentUsername);
                          nameShow.setText(currentName);
                          passShow.setText(currentPassword);
                      } else {

                          userNN = usernameNew.getText().toString();

                          Pattern p = Pattern.compile("[^A-Za-z0-9.]");
                          Matcher m = p.matcher(userNN);
                          boolean b = m.find();

                          if (b || passNew.getText().toString().length() < 5) {
                              confirmchanges.setVisibility(View.INVISIBLE);
                              usernameNew.setVisibility(View.INVISIBLE);
                              nameNew.setVisibility(View.INVISIBLE);
                              passNew.setVisibility(View.INVISIBLE);

                              userNameShow.setText(currentUsername);
                              nameShow.setText(currentName);
                              passShow.setText(currentPassword);
                              if (b) {
                                  Toast.makeText(ProfileScreen.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                              } else {
                                  Toast.makeText(ProfileScreen.this, "Weak Password", Toast.LENGTH_SHORT).show();
                              }
                          } else {
                              HashMap<String, Object> name = new HashMap<>();
                              name.put("Name", nameNew.getText().toString());
                              name.put("Username", usernameNew.getText().toString());
                              name.put("Password", passNew.getText().toString());

                              Log.d("bobo1 ", " " + currentUsername + " " + currentName + " " + currentPassword + " " + userId);

                              DocumentReference fc = FirebaseFirestore.getInstance().collection("REGISTERED NAME").document(userId);
                              fc.update(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {

                                      if (task.isSuccessful()) {

                                          Log.d("bobo3 ", " " + currentUsername + " " + currentName + " " + currentPassword + " " + userId);
                                          Log.d("bobo4 ", " " + nameNew + " " + usernameNew + " " + " " + passNew);
                                          currentName = nameNew.getText().toString();
                                          currentUsername = usernameNew.getText().toString();
                                          currentPassword = passNew.getText().toString();


                                          userNameShow.setText(currentUsername);
                                          nameShow.setText(currentName);
                                          passShow.setText(currentPassword);

                                          editName.setVisibility(View.VISIBLE);
                                          confirmchanges.setVisibility(View.INVISIBLE);
                                          usernameNew.setVisibility(View.INVISIBLE);
                                          nameNew.setVisibility(View.INVISIBLE);
                                          passNew.setVisibility(View.INVISIBLE);

                                          Log.d("bobo2 ", " " + currentUsername + " " + currentName + " " + currentPassword + " " + userId);
                                      } else {
                                          Toast.makeText(ProfileScreen.this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                                      }
                                  }


                              });
                          }
                      }

                  }
              });


              editNumber.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Toast.makeText(ProfileScreen.this, "Sorry Phone number cannot be changed. You can create new account", Toast.LENGTH_SHORT).show();
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

            revertback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(ProfileScreen.this,NoInternet.class);
                    startActivity(noint);


                }
            });
            Intent noint=new Intent(ProfileScreen.this,NoInternet.class);
            startActivity(noint);

            editNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(ProfileScreen.this,NoInternet.class);
                    startActivity(noint);
                }
            });


            editName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(ProfileScreen.this,NoInternet.class);
                    startActivity(noint);
                }
            });

            confirmchanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(ProfileScreen.this,NoInternet.class);
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

    public void onBackPressed() {
        Intent intent6 = new Intent(ProfileScreen.this, UserAccountScreen1.class);
        intent6.putExtra("currentName",currentName);
        intent6.putExtra("currentPhoneNumber", currentUpi);
        intent6.putExtra("currentUserName", currentUsername);
        intent6.putExtra("currentPassword", currentPassword);
        intent6.putExtra("userId",userId);
        startActivity(intent6);
    }

}