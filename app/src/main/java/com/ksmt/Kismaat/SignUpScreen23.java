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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SignUpScreen23 extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView messageotp;
    private String codesent;
    private Button confirm;
    private EditText usercode;
    private Button againcode;
    private ConstraintLayout otp;
    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String uc;
    private String enterUpi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_screen23);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);


        Intent intent4=getIntent();
        currentUpi=(String)intent4.getStringExtra("currentPhoneNumber");
        currentUsername=(String)intent4.getStringExtra("currentUserName");
        currentPassword=(String)intent4.getStringExtra("currentPassword");
        currentName=(String)intent4.getStringExtra("currentName");
        userId=(String)intent4.getStringExtra("userId");

        auth=FirebaseAuth.getInstance();


        messageotp=(TextView)findViewById(R.id.messageotp);
        confirm=(Button)findViewById(R.id.confirm);
        usercode=(EditText)findViewById(R.id.usercode);
        againcode=(Button)findViewById(R.id.againcode);
        otp=(ConstraintLayout)findViewById(R.id.otp);


        otp.setOnClickListener(new View.OnClickListener() {
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

               confirm.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       uc = (String) usercode.getText().toString();

                       if (TextUtils.isEmpty(uc)) {
                           messageotp.setText("Please enter a otp");
                       } else {

                           if (codesent != null) {
                               PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, uc);
                               signInWithPhoneAuthCredential(credential);
                           } else {
                               messageotp.setText("Invalid Otp");
                           }
                       }
                   }
               });


               againcode.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       recallcode(currentUpi);
                   }
               });

               Log.d("UPI", "onCreate: " + currentUpi);

               enterUpi = "+91" + currentUpi;


               PhoneAuthProvider.getInstance().verifyPhoneNumber(
                       enterUpi,        // Phone number to verify
                       60,                 // Timeout duration
                       TimeUnit.SECONDS,   // Unit of timeout
                       this,               // Activity (for callback binding)
                       new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                           @Override
                           public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                           }

                           @Override
                           public void onVerificationFailed(@NonNull FirebaseException e) {
                               Log.d("fail222", "onCreate: ");

                           }

                           @Override
                           public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                               super.onCodeSent(s, forceResendingToken);
                               Log.d("checkuser1", "onCreate: " + s + " " + codesent);
                               codesent = s;
                           }
                       });
           }
           catch (Exception e)
           {
               Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
           }
      }
      else
      {
          confirm.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent noint=new Intent(SignUpScreen23.this,NoInternet.class);
                  startActivity(noint);
              }
          });

          againcode.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent noint=new Intent(SignUpScreen23.this,NoInternet.class);
                  startActivity(noint);

              }
          });
      }
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userId = Objects.requireNonNull(auth.getCurrentUser()).getUid().toString();
                          try {

                              // Sign in success, update UI with the signed-in user's information
                              Intent intent5 = new Intent(SignUpScreen23.this, SignUpScreen3.class);
                              intent5.putExtra("currentUserName", currentUsername);
                              intent5.putExtra("currentPassword", currentPassword);
                              intent5.putExtra("currentPhoneNumber", currentUpi);
                              intent5.putExtra("currentName", currentName);
                              intent5.putExtra("userId", userId);
                              intent5.putExtra("otpsent", codesent);
                              intent5.putExtra("enteredotp", uc);
                              startActivity(intent5);
                          }
                          catch (Exception e)
                          {
                              Toast.makeText(SignUpScreen23.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                              e.getStackTrace();
                          }
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                messageotp.setText("Incorrect OTP entered");
                            }
                        }
                    }
                });
    }

    private void recallcode(String number)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        codesent=s;

                    }
                });        // OnVerificationStateChangedCallbacks

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