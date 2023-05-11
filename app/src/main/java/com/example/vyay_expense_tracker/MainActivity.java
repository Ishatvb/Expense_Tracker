package com.example.vyay_expense_tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
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
        //getSupportActionBar().setTitle("Expense Manager");

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

    private void getAmountFromMessage(String message) {
        String regex = "(?i)(?:(?:RS|INR|MRP|debited|Debited)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)";
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
    private void getDateFromMessage(String message) {

    }
    private void getTimeFromMessage(String message) {

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

