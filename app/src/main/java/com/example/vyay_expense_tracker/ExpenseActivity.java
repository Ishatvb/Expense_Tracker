package com.example.vyay_expense_tracker;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {
    String  cal_date;
    public DBHandler dbHandler_cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        dbHandler_cal=new DBHandler(ExpenseActivity.this);


        CalendarView calendarView2 ;
        calendarView2=findViewById(R.id.calendarView2);

        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                cal_date = (month + 1) + "-" + dayOfMonth + "-" + year;
                Log.d("date", cal_date);


//                                                     textView3.setText(todaydate);
            }


        });
        ArrayList<model> arrDetails =dbHandler_cal.fetchContact(cal_date);
        int size2 = arrDetails.size();

        for (int i = 0; i < size2; i++) {
            Log.d("amount",  arrDetails.get(i).amount);
        }
        }
    }


//int i;


//    we have cal_date
//}
