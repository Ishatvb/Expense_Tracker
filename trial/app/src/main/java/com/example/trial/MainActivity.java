package com.example.trial;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.net.Uri;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    private TextView mytextview;
    String regex = "(?i)(?:(?:RS|INR|MRP|debited|Debited)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mytextview = findViewById(R.id.textView);


        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
    }
    public void READ_SMS(View view){
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        cursor.moveToFirst();
        Pattern pattern = Pattern.compile(regex);
        String text=cursor.getString(12);
//            mytextview.setText(cursor.getString(12));
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        String to_show=matcher.group();
        mytextview.setText(to_show);


    }
}