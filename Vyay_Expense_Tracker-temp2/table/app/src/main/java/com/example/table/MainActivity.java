package com.example.table;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.view.View;
import android.net.Uri;

import com.example.table.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
//    private TextView selected_date_show;
    String regex = "(?i)(?:(?:RS|INR|MRP|debited|Debited)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)";

    String regex2 = "(\\d{1,2}-\\d{1,2}-\\d{2}|\\d{1,2}-\\d{1,2}-\\d{4})";

    CalendarView calendarView ;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1= findViewById(R.id.textView1);
        textView2= findViewById(R.id.textView2);
        textView3= findViewById(R.id.textView3);

        calendarView=findViewById(R.id.calendarView);
//        date = findViewById(R.id.date);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                String todaydate=(month+1)+"-"+dayOfMonth+"-"+ year;
                Log.d("date", todaydate);
                textView3.setText(todaydate);
            }
        });
    }


//    public void READ_SMS(View view) {
//        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
//        cursor.moveToFirst();
//        Pattern pattern = Pattern.compile(regex);
//        String text = cursor.getString(12);
////            mytextview.setText(cursor.getString(12));
//
//        Matcher matcher = pattern.matcher(text);
//        matcher.find();
//        String to_show = matcher.group();
//        mytextview.setText(to_show);
//
//
//    }

    public void READ_AMOUNT(View view) {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        cursor.moveToFirst();
        Pattern pattern = Pattern.compile(regex);
        String text = cursor.getString(12);
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            String to_show = matcher.group();
            textView1.setText(to_show);
        }

        else textView1.setText("Null");


    }
    public void READ_DATE(View view) {
        Cursor cursor2 = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        cursor2.moveToFirst();
        Pattern pattern2 = Pattern.compile(regex2);
        String text2 = cursor2.getString(12);

        Matcher matcher2 = pattern2.matcher(text2);
       if( matcher2.find()) {
           String date_string = matcher2.group();
           textView2.setText(date_string);

       }

       else textView2.setText("Null");


    }


}