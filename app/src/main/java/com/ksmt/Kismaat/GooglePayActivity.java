package com.ksmt.Kismaat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GooglePayActivity extends AppCompatActivity {

    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String ticktotal;
    private String amount;
    private Button proceedpayment;
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    private String name="Saurabh Animesh";
    private final int UPI_PAYMENT=0;


    //@Override
    /*protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

          if(resultCode == RESULT_OK || resultCode == 11) {

              String status=data.getStringExtra("response");
              int indexstatus=status.indexOf("Status");
              String successcheck;
              successcheck=status.substring(indexstatus+7,indexstatus+14);

              if(successcheck.equals("SUCCESS")) {
                  Log.d("message", "onActivityResult: " + successcheck + " " + status);
                  Intent afterpay = new Intent(GooglePayActivity.this, AfterPayment.class);
                  afterpay.putExtra("currentName", currentName);
                  afterpay.putExtra("currentPhoneNumber", currentUpi);
                  afterpay.putExtra("currentUserName", currentUsername);
                  afterpay.putExtra("currentPassword", currentPassword);
                  afterpay.putExtra("userId", userId);
                  afterpay.putExtra("tickettotal",ticktotal);
                  afterpay.putExtra("googlepayamount",amount);
                  startActivity(afterpay);
              }
              else
              {
                  Intent paymentfailed=new Intent(GooglePayActivity.this,PaymentFailed.class);
                  startActivity(paymentfailed);
              }
          }
          else
          {
              Intent paymentfailed=new Intent(GooglePayActivity.this,PaymentFailed.class);
              startActivity(paymentfailed);
          }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            switch (requestCode) {
                case UPI_PAYMENT:
                    if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                        if (data != null) {
                            String trxt = data.getStringExtra("response");
                            Log.d("UPIX", "onActivityResult: " + trxt);
                            ArrayList<String> dataList = new ArrayList<>();
                            dataList.add(trxt);
                            upiPaymentDataOperation(dataList);
                        } else {
                            Log.d("UPI", "onActivityResult: " + "Return data is null");
                            ArrayList<String> dataList = new ArrayList<>();
                            dataList.add("nothing");
                            upiPaymentDataOperation(dataList);
                        }
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                    break;
            }
        } catch (Exception e)
        {
            Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_google_pay);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        Intent payg=getIntent();
        currentUpi=(String)payg.getStringExtra("currentPhoneNumber");
        currentUsername=(String)payg.getStringExtra("currentUserName");
        currentPassword=(String)payg.getStringExtra("currentPassword");
        currentName=(String)payg.getStringExtra("currentName");
        userId=(String)payg.getStringExtra("userId");
        amount=(String)payg.getStringExtra("googlepayamount");
        ticktotal=(String)payg.getStringExtra("tickettotal");

        proceedpayment=(Button)findViewById(R.id.proceedpayment);

       if(hasInternetConnectivity()) {

           proceedpayment.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   Uri uri =Uri.parse("upi://pay").buildUpon()
                                   .appendQueryParameter("pa", "animesh90655@oksbi")  //animesh90655@oksbisourav.kingworld@okhdfcbank
                                   .appendQueryParameter("pn", name)
                                   //.appendQueryParameter("mc", "your-merchant-code")
                                   //.appendQueryParameter("tr", "your-transaction-ref-id")
                                   //.appendQueryParameter("tn", "KismaatServices")
                                   .appendQueryParameter("am", amount)
                                   .appendQueryParameter("cu", "INR")
                                   .appendQueryParameter("tr", "261433")
                                   //.appendQueryParameter("url", "your-transaction-url")
                                   .build();
                   Intent intentupi = new Intent(Intent.ACTION_VIEW);
                   intentupi.setData(uri);

                   Intent chooser = Intent.createChooser(intentupi,"Pay with");



                   //intentupi.setPackage(GOOGLE_PAY_PACKAGE_NAME);

                   //Intent chooser=Intent.createChooser(intentupi,"Pay with");

                   if(null != intentupi.resolveActivity(getPackageManager())){
                       startActivityForResult(intentupi,UPI_PAYMENT);
                   }
                   else
                   {
                       Toast.makeText(GooglePayActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                   }
               }
           });
       }
       else
       {
           Intent noint=new Intent(GooglePayActivity.this,NoInternet.class);
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

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (hasInternetConnectivity()) {
            try {
                String str = data.get(0);
                Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
                String paymentCancel = "";
                if (str == null) str = "discard";
                String status = "";
                String approvalRefNo = "";
                String response[] = str.split("&");
                for (int i = 0; i < response.length; i++) {
                    String equalStr[] = response[i].split("=");
                    if (equalStr.length >= 2) {
                        if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                            status = equalStr[1].toLowerCase();
                        } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                            approvalRefNo = equalStr[1];
                        }
                    } else {
                        paymentCancel = "Payment cancelled by user.";
                    }
                }

                if (status.equals("success")) {
                    //Code to handle successful transaction here.
                    Intent afterpay = new Intent(GooglePayActivity.this, AfterPayment.class);
                    afterpay.putExtra("currentName", currentName);
                    afterpay.putExtra("currentPhoneNumber", currentUpi);
                    afterpay.putExtra("currentUserName", currentUsername);
                    afterpay.putExtra("currentPassword", currentPassword);
                    afterpay.putExtra("userId", userId);
                    afterpay.putExtra("tickettotal", ticktotal);
                    afterpay.putExtra("googlepayamount", amount);
                    startActivity(afterpay);
                    Log.d("UPI", "responseStr: " + approvalRefNo);
                } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                    Log.d("userJek", "upiPaymentDataOperation: 1");
                    Intent paymentfailed = new Intent(GooglePayActivity.this, PaymentFailed.class);
                    startActivity(paymentfailed);
                } else {
                    Log.d("userJek", "upiPaymentDataOperation: 2");
                    Intent paymentfailed = new Intent(GooglePayActivity.this, PaymentFailed.class);
                    startActivity(paymentfailed);
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                e.getStackTrace();
            }
        }
           else {
            Intent noint=new Intent(GooglePayActivity.this,NoInternet.class);
            startActivity(noint);
        }
    }
}