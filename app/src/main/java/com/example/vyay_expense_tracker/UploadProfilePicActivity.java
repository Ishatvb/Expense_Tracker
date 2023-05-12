package com.example.vyay_expense_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class UploadProfilePicActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView imageViewUploadPic;
    private FirebaseAuth authProfile;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;


//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload_profile_pic);
//
//        getSupportActionBar().setTitle("Upload Profile Picture");
//android:layout_below="@id/RL_name"
//        Button buttonUploadPicChoose=findViewById(R.id.upload_pic_choose_button);
//        Button buttonUploadPic = findViewById(R.id.upload_pic_button);
//        progressBar=findViewById(R.id.progressBar);
//        imageViewUploadPic=findViewById(R.id.imageView_profile_dp);
//
//        authProfile=FirebaseAuth.getInstance();
//        firebaseUser=authProfile.getCurrentUser();
//
//        storageReference= FirebaseStorage.getInstance().getReference("DisplayPics");
//        Uri uri=firebaseUser.getPhotoUrl();
//        //set user's current DP in Imageview (if uploaded already). we will use Picasso since imageViewer setImage
//        //Regular URIs
//        Picasso.with(UploadProfilePicActivity.this).load(uri).into(imageViewUploadPic);
//
//
//
//
//    }
//    //creating action bar menu
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //Inflate menu items
//        getMenuInflater().inflate(R.menu.common_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//    //when any menu itm is selected
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id =item.getItemId();
//
//        if(id==R.id.menu_refresh){
//            //refresh activity
//            startActivity(getIntent());
//            finish();
//            overridePendingTransition(0,0);
//        }
//        else if(id==R.id.menu_update_profile){
//            Intent intent=new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
//            startActivity(intent);
//        }
//        else if(id==R.id.menu_update_email){
//            Intent intent=new Intent(UserProfileActivity.this,UpdateEmailActivity.class);
//            startActivity(intent);
//        }
//        else if(id==R.id.menu_settings){
//            Toast.makeText(UserProfileActivity.this, "Menu Settings", Toast.LENGTH_SHORT).show();
//        }
//          else if(id==R.id.menu_change_password){
//            Intent intent=new Intent(UserProfileActivity.this,ChangePasswordActivity.class);
//            startActivity(intent);
//        }
//        else if(id==R.id.menu_delete_profile){
//            Intent intent=new Intent(UserProfileActivity.this,DeleteProfileActivity.class);
//            startActivity(intent);
//        }
//        else if(id ==R.id.menu_logout){
//            authProfile.signOut();
//            Toast.makeText(UploadProfilePicActivity.this, "Logget Out!", Toast.LENGTH_SHORT).show();
//            Intent intent=new Intent(UploadProfilePicActivity.this,MainActivity.class);
//
//            //clear the stack to prevent user coming back to userprofileactivity on pressing back button
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();//close userprofileactivity
//        }
//        else {
//            Toast.makeText(UploadProfilePicActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
}