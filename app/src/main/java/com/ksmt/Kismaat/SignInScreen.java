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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SignInScreen extends AppCompatActivity {

    private EditText phoneLogin;
    private EditText otplogin;
    private Button loginbutton;
    private TextView infologin;
    private FirebaseAuth auth;
    private ConstraintLayout loginlay;
    String unl;
    String psl;
    private String codesent;
    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String phoneLogins;
    private String otplogins;
    private Button sendlogin;
    private int checking=0;
    private String uc;
    private String enterUpi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent signIn = getIntent();
        Intent signout=getIntent();

        auth=FirebaseAuth.getInstance();

        phoneLogin=(EditText)findViewById(R.id.phoneLogin);
        otplogin=(EditText)findViewById(R.id.otplogin);
        loginbutton=(Button)findViewById(R.id.loginbutton);
        infologin=(TextView)findViewById(R.id.infologin);
        loginlay=(ConstraintLayout)findViewById(R.id.loginlay);
        sendlogin=(Button)findViewById(R.id.sendlogin) ;


        loginlay.setOnClickListener(new View.OnClickListener() {
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

            try{

            sendlogin.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {

                    checking = 1;
                    FirebaseFirestore client = FirebaseFirestore.getInstance();
                    phoneLogins = (String) phoneLogin.getText().toString();
                    otplogins = (String) otplogin.getText().toString();


                    if (TextUtils.isEmpty(phoneLogins)) {
                        infologin.setText("Please Enter your number");
                    } else {

                        client.collection("REGISTERED NAME").whereEqualTo("UPI PHONE", phoneLogins).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (queryDocumentSnapshots.isEmpty()) {
                                    infologin.setText("Phone number not registered");
                                } else {
                                    recallcode(phoneLogin.getText().toString());
                                }
                            }
                        });

                    }
                }
            });


            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phoneLogins = (String) phoneLogin.getText().toString();
                    otplogins = (String) otplogin.getText().toString();
                    FirebaseFirestore client = FirebaseFirestore.getInstance();
                    if (TextUtils.isEmpty(phoneLogins) || TextUtils.isEmpty(otplogins)) {
                        infologin.setText("Please Enter your number/otp");
                    } else {
                        client.collection("REGISTERED NAME").whereEqualTo("UPI PHONE", phoneLogins).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (queryDocumentSnapshots.isEmpty()) {
                                    infologin.setText("Please enter valid details");
                                } else {
                                    FirebaseFirestore fs = FirebaseFirestore.getInstance();
                                    fs.collection("REGISTERED NAME").whereEqualTo("UPI PHONE", phoneLogins).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                                    currentUsername = (String) doc.get("Username");
                                                    currentName = (String) doc.get("Name");
                                                    currentPassword = (String) doc.get("Password");
                                                    userId = (String) doc.getId();
                                                }
                                            }
                                        }
                                    });


                                    if (checking == 1) {
                                        uc = otplogin.getText().toString();
                                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, uc);
                                        signInWithPhoneAuthCredential(credential);
                                    } else {
                                        infologin.setText("Please retrieve otp first");
                                    }
                                }
                            }
                        });

                    }


                }
            });
        }catch (Exception e)
            {
                Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noint=new Intent(SignInScreen.this,NoInternet.class);
                    startActivity(noint);
                }
            });

        }
    }
    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intentin=new Intent(SignInScreen.this,UserAccountScreen1.class);
                            intentin.putExtra("currentName", currentName);
                            intentin.putExtra("currentPhoneNumber", phoneLogins);
                            intentin.putExtra("currentUserName", currentUsername);
                            intentin.putExtra("currentPassword", currentPassword);
                            intentin.putExtra("userId", userId);
                            intentin.putExtra("otpsent",codesent);
                            intentin.putExtra("enteredotp",uc);
                            startActivity(intentin);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                infologin.setText("Incorrect OTP entered");
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
        Intent signinreg=new Intent(SignInScreen.this,registerScreen.class);
        startActivity(signinreg);
    }

}