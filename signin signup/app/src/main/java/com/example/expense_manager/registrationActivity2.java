package com.example.expense_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class registrationActivity2 extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPass;
    private Button btnReg;
    private TextView mSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        registration();
    }
    private void registration(){
        mEmail=findViewById(R.id.email_register);
        mPass=findViewById(R.id.password_register);
        btnReg=findViewById(R.id.button_registration);
        mSignin=findViewById(R.id.signin_here);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText().toString().trim();
                String pass=mPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email Required :) ");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    mPass.setError("Password Required :)");
                    return;
                }
            }
        });

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

}