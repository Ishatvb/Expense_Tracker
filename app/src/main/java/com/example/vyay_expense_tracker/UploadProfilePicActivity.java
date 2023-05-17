package com.example.vyay_expense_tracker;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class UploadProfilePicActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView imageViewUploadPic;
    private FirebaseAuth authProfile;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private static final int PICK_IMAGE_REQUEST=1;
    private Uri uriImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_pic);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Upload Profile Picture");
        Button buttonUploadPicChoose = findViewById(R.id.upload_pic_choose_button);
        Button buttonUploadPic = findViewById(R.id.upload_pic_button);
        progressBar = findViewById(R.id.progressBar);
        imageViewUploadPic = findViewById(R.id.imageView_profile_dp);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference("DisplayPics");
        Uri uri = firebaseUser.getPhotoUrl();
        //set user's current DP in Imageview (if uploaded already). we will use Picasso since imageViewer setImage
        //Regular URIs
//        Picasso.with(UploadProfilePicActivity.this).load(uri).into(imageViewUploadPic);
        Picasso.get().load(uri).into(imageViewUploadPic);
        //choose image to upload


        buttonUploadPicChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        //upload image
        buttonUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                UploadPic();
            }
        });
    }
    private void openFileChooser() {
        Intent intent=new Intent();
        intent.setType("Image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    private void UploadPic(){
        if(uriImage!=null){
            //save the image with id of the currently logged user
            StorageReference fileReference= storageReference.child(authProfile.getCurrentUser().getUid()+"."+ getFileExtension(uriImage));
            //upload pic to storage
            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri=uri;
                            firebaseUser=authProfile.getCurrentUser();
                            //finally set the display image of the user after upload
                            UserProfileChangeRequest profileUpdates=new UserProfileChangeRequest.Builder().setPhotoUri(downloadUri).build();
                            firebaseUser.updateProfile(profileUpdates);
                        }
                    });
                }
            });
        }
    }
    //obtain File Extension of the image
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            imageViewUploadPic.setImageURI(uriImage);
        }

    }
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//                uriImage = data.getData();
//                Picasso.get().load(uriImage).into(imageViewUploadPic);
//            }
//        }



    //creating action bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu items
        getMenuInflater().inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
//    when any menu itm is selected
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
        else if(id ==R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(UploadProfilePicActivity.this, "Logget Out!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(UploadProfilePicActivity.this,MainActivity.class);

            //clear the stack to prevent user coming back to userprofileactivity on pressing back button
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();//close userprofileactivity
        }
        else {
            Toast.makeText(UploadProfilePicActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}