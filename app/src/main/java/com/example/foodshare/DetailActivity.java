package com.example.foodshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodshare.models.TaskItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnegative;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private ImageView itemImageView;
    private TaskItem item;
    private Button btnConfirm;
    private Button btnBack;
    private Button deletepost;
    //public String currentUserRef;
  //  public String postCreator;
   // private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

      //  currentUserRef = getIntent().getExtras().get("current_user").toString();
        //postCreator = getIntent().getExtras().get("postCreator").toString();
        //db = FirebaseFirestore.getInstance();

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        itemImageView = findViewById(R.id.itemImageView);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);
       // deletepost = findViewById(R.id.deletepost);


        item = Parcels.unwrap(getIntent().getParcelableExtra(TaskItem.class.getSimpleName()));
        tvTitle.setText(item.title);
        tvDescription.setText(item.description);
        if (item.fullImage != null) {
            Bitmap scaledImage = Bitmap.createScaledBitmap(item.fullImage, 200, 300, false);
            itemImageView.setImageBitmap(scaledImage);
        } else {
            itemImageView.setImageBitmap(null);
        }

        itemImageView.setImageBitmap(item.fullImage);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }

        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openActivity();
            }
       // });


      //  deletepost.setOnClickListener(new Button.OnClickListener() {
           // public void onClick(View v) {
              //  db.collection("posts").document("task_item")
                   //     .delete()
                       // .addOnSuccessListener(new OnSuccessListener<Void>() {
                       //     @Override
                        //    public void onSuccess(Void aVoid) {
                       //         Log.d("DetailActivity", "Post successfully deleted.");
                      //      }
                     //   })
                   //  .addOnFailureListener(new OnFailureListener() {
                   // @Override
                  //  public void onFailure(@NonNull Exception e) {
                   //     Log.w("DetailActivity", "Error deleting post", e);
                  //  }
          //      });
          //  }
       // });

     //   if (postCreator.equals(currentUserRef)) {
           // deletepost.setVisibility(View.VISIBLE);
        });
    }
        public void openNewActivity() {
            Intent intent = new Intent(this, ConfirmationActivity.class);
            startActivity(intent);
        }

        public void openActivity() {
            finish();
        }
    }

