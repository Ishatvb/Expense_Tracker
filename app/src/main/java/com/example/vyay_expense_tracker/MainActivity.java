package com.example.vyay_expense_tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public DBHandler dbHandler;
    private static final int REQ_USER_CONSENT = 200;
    MySmsReceiver smsBroadcastReceiver;

    private ImageView logoIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //====================================================================================
        dbHandler=new DBHandler(MainActivity.this);
        startSmsUserConsent();
        //====================================================================================
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // on below line we are initializing our variables.
        logoIV = findViewById(R.id.imageView_logo);

        // on below line we are setting background color to transparent
        logoIV.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        //set the title
        getSupportActionBar().setTitle("Expense Manager");

        //Open login activity
        Button buttonLogin =findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        //Open registration activity
        Button buttonRegister =findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
//##################################################################################################
    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                getAmountFromMessage(message);
            }
        }
    }
    String regex = "(?i)(?:(?:RS|INR|MRP|debited|Debited)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)";
    String regex2 = "(\\d{1,2}-\\d{1,2}-\\d{2}|\\d{1,2}-\\d{1,2}-\\d{4})";

    String regex3 = "^[01]?[0-9]([:.][0-9]{2})?(\\s?[ap]m)?$";
    private void getAmountFromMessage(String message) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        String amount;
        if (matcher.find()){
            amount=matcher.group(0);
            dbHandler=new DBHandler(MainActivity.this);
            int f=dbHandler.newPayment(amount,"Burger");
            if(f>0)
                Toast.makeText(this, "Amount "+amount+" noted", Toast.LENGTH_SHORT).show();
            dbHandler.close();

        }

    }

//    make store func to store vals from the three functions
    private void getDateFromMessage(String message) {
        Cursor cursor2 = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        cursor2.moveToFirst();
        Pattern pattern2 = Pattern.compile(regex2);
        String text2 = cursor2.getString(12);

        Matcher matcher2 = pattern2.matcher(text2);
        matcher2.find();
        String date_string = matcher2.group();

        Toast.makeText(null, date_string, Toast.LENGTH_SHORT).show();


    }
    private void getTimeFromMessage(String message) {
        Cursor cursor3 = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        cursor3.moveToFirst();
        Pattern pattern3 = Pattern.compile(regex3);
        String text3 = cursor3.getString(12);

        Matcher  matcher3= pattern3.matcher(text3);
        matcher3.find();
        String time_string = matcher3.group();

        Toast.makeText(null, time_string, Toast.LENGTH_SHORT).show();
//dont have code for this
    }



    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new MySmsReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener = new MySmsReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent, REQ_USER_CONSENT);

                //on.launch(intent);
            }
            @Override
            public void onFailure() {
            }
        };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

//    void push_into_db()
//    public void insertIntoDB(String date_string, String time_string, String amount){
//        dbHandler=new this.getWri;
//        dbHandler.this.getWr
//    }
    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }
//##################################################################################################
}

