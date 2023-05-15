package com.example.vyay_expense_tracker;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

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
    private static final int PICK_IMAGE_REQUEST=1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_pic);

        getSupportActionBar().setTitle("Upload Profile Picture");
        Button buttonUploadPicChoose=findViewById(R.id.upload_pic_choose_button);
        Button buttonUploadPic = findViewById(R.id.upload_pic_button);
        progressBar=findViewById(R.id.progressBar);
        imageViewUploadPic=findViewById(R.id.imageView_profile_dp);

        authProfile=FirebaseAuth.getInstance();
        firebaseUser=authProfile.getCurrentUser();

        storageReference= FirebaseStorage.getInstance().getReference("DisplayPics");
        Uri uri=firebaseUser.getPhotoUrl();
        //set user's current DP in Imageview (if uploaded already). we will use Picasso since imageViewer setImage
        //Regular URIs
        Picasso.with(UploadProfilePicActivity.this).load(uri).into(imageViewUploadPic);


//        buttonUploadPicChoose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                openFileChooser();
//            }
//        });
//    }
//    private void openFileChooser(){
//        Intent intent; new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,PICK_IMAGE_REQUEST);




    //creating action bar menu

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //Inflate menu items
//        getMenuInflater().inflate(R.menu.common_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
    //when any menu itm is selected
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
//            Intent intent=new Intent(UploadProfilePicActivity.this,UpdateProfileActivity.class);
//            startActivity(intent);
//        }
//        else if(id==R.id.menu_update_email){
//            Intent intent=new Intent(UploadProfilePicActivity.this,UpdateEmailActivity.class);
//            startActivity(intent);
//        }
//        else if(id==R.id.menu_settings){
//            Toast.makeText(UploadProfilePicActivity.this, "Menu Settings", Toast.LENGTH_SHORT).show();
//        }
//          else if(id==R.id.menu_change_password){
//            Intent intent=new Intent(UploadProfilePicActivity.this,ChangePasswordActivity.class);
//            startActivity(intent);
//        }
//        else if(id==R.id.menu_delete_profile){
//            Intent intent=new Intent(UploadProfilePicActivity.this,DeleteProfileActivity.class);
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
}}