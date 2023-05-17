package com.example.vyay_expense_tracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {

    private TextView textViewWelcome , textViewFullName ,textViewEmail,textViewDob,textViewGender,textViewMobile;
    private ProgressBar progressBar;
    private String fullName,email,doB,gender,mobile;
    private ImageView imageView;
    private FirebaseAuth authProfile;

   public String  cal_date;
    public DBHandler dbHandler_cal;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        dbHandler_cal=new DBHandler(ExpenseActivity.this);


        CalendarView calendarView2 ;
        calendarView2=findViewById(R.id.calendarView2);

        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                cal_date = dayOfMonth + "-" + (month + 1) + "-" + year;
                Log.d("date", cal_date);

                String query="SELECT * FROM " + DBHandler.TABLE_NAME + " WHERE " + DBHandler.DATE + " = "+ cal_date;

                SQLiteDatabase db = dbHandler_cal.getReadableDatabase();
                Cursor cursor = db.rawQuery(query,null);
                //String data=cursor.getString(0);
//                String c1=cursor.getString(1);
//                String c2=cursor.getString(2);
//                String c3=cursor.getString(3);
//                Log.d("data",c1=" "+c2+" "+c3);
//                String data=c1=" "+c2+" "+c3;
                //Toast.makeText(ExpenseActivity.this, data, Toast.LENGTH_SHORT).show();
                cursor.close();
                /*ArrayList<model> arrDetails =dbHandler_cal.fetchContact(cal_date);
                int size2 = arrDetails.size();
                String st="";
//                for (int i = 0; i < size2; i++) {
//                    Log.d("amount",  arrDetails.get(0).amount, "date",arrDetails.get(0).date_string );
                Log.d("amount", arrDetails.get(3).amount );
                Log.d("date", arrDetails.get(1).date_string );
                Log.d("time", arrDetails.get(2).time_string );
*/
//                    st= st+" : "+arrDetails.get(i).toString();
//                }
//                Toast.makeText(this, Toast.LENGTH_SHORT).show();
//              textView3.setText(todaydate);
            }


        });

        }
//    we have cal_date

//creating action bar menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu items
        getMenuInflater().inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //when any menu itm is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();

        if(id==R.id.menu_refresh){
            //refresh activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }
//        else if(id==R.id.menu_update_profile){
//            Intent intent=new Intent(ExpenseActivity.this,UpdateProfileActivity.class);
//            startActivity(intent);
//        }
//        else if(id==R.id.menu_update_email){
//            Intent intent=new Intent(ExpenseActivity.this,UpdateEmailActivity.class);
//            startActivity(intent);
//        }
//        else if(id==R.id.menu_settings){
//            Toast.makeText(ExpenseActivity.this, "Menu Settings", Toast.LENGTH_SHORT).show();
//        }
//          else if(id==R.id.menu_change_password){
//            Intent intent=new Intent(ExpenseActivity.this,ChangePasswordActivity.class);
//            startActivity(intent);
//        }
//        else if(id==R.id.menu_delete_profile){
//            Intent intent=new Intent(ExpenseActivity.this,DeleteProfileActivity.class);
//            startActivity(intent);
//        }
        else if(id ==R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(ExpenseActivity.this, "Logget Out!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(ExpenseActivity.this,MainActivity.class);

            //clear the stack to prevent user coming back to userprofileactivity on pressing back button
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();//close userprofileactivity
        }
        else {
            Toast.makeText(ExpenseActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }}