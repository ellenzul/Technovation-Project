package com.example.foodshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodshare.models.TaskItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;

public class AddActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etDescription;
    private EditText etTags;
    private EditText etLocation;
    private Button btnAddImage;
    private ImageView addedImageView;
    private Button btnAddItem;
    private Bitmap addedImage;
    final int CAMERA_REQUEST_ID = 50;

    FirebaseStorage storage;
    StorageReference imagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        imagesRef = storageRef.child("images");

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etTags = findViewById(R.id.etTags);
        etLocation = findViewById(R.id.etLocation);

        btnAddImage = findViewById(R.id.btnAddImage);
        addedImageView = findViewById(R.id.addedImageView);
        addedImageView.setImageBitmap(null); // set the image to nothing initially
        btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(v -> onAddItem(v));

        btnAddImage.setOnClickListener(v -> {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePhotoIntent, CAMERA_REQUEST_ID);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_ID && resultCode == RESULT_OK) {
            // extract and save image
            addedImage = (Bitmap) data.getExtras().get("data");
            // set image preview
            addedImageView.setImageBitmap(addedImage);
            addedImageView.setVisibility(View.VISIBLE);
        }
    }

    public void onAddItem(View v) {
        TaskItem newItem = new TaskItem(
                etTitle.getText().toString(),
                etDescription.getText().toString(),
                etTags.getText().toString(),
                etLocation.getText().toString()
        );
        // save image to firebase storage
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US);
        String nowString = dateFormat.format(currentTime);
        StorageReference newPostImageRef = imagesRef.child("post_" + nowString + ".jpg");

        Bitmap bitmap = ((BitmapDrawable) addedImageView.getDrawable()).getBitmap();
        newItem.fullImage = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        UploadTask uploadTask = newPostImageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AddActivity.this, "Image upload failed. Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                newPostImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        newItem.imageUri = downloadUrl;
                        Intent intentData = new Intent();
                        intentData.putExtra("task_item", Parcels.wrap(newItem));
                        setResult(RESULT_OK, intentData);
                        finish();
                    }
                });
            }
        });
    }

}