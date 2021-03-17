package com.example.foodshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodshare.models.TaskItem;

import org.parceler.Parcels;

public class AddActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etDescription;
    private EditText etTags;
    private EditText etPortion;
    private EditText etLocation;
    private Button btnAddImage;
    private ImageView addedImageView;
    private Button btnAddItem;
    private Bitmap addedImage;
    final int CAMERA_REQUEST_ID = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etTags = findViewById(R.id.etTags);
        etPortion = findViewById(R.id.etPortions);
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
        }

    }

    public void onAddItem(View v) {

        TaskItem newItem = new TaskItem(etTitle.getText().toString(), etDescription.getText().toString(), etTags.getText().toString(), etPortion.getText().toString(), etLocation.getText().toString());
        newItem.image = addedImage;
        Intent data = new Intent();
        data.putExtra("task_item", Parcels.wrap(newItem));
        setResult(RESULT_OK, data);
        finish();
    }
}