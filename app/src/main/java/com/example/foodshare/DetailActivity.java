package com.example.foodshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodshare.models.TaskItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvPhoneNumber;
    private TextView tvFoodTags;
    private ImageView itemImageView;
    private TaskItem item;
    private Button btnConfirm;
    private Button btnBack;
    private Button deletePost;
    public String currentUserRef;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = FirebaseFirestore.getInstance();

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvPhoneNumber = findViewById(R.id.tvphoneno);
        tvFoodTags = findViewById(R.id.tvTags);
        itemImageView = findViewById(R.id.itemImageView);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);
        deletePost = findViewById(R.id.deletepost);

        currentUserRef = getIntent().getExtras().get("current_user").toString();
        item = Parcels.unwrap(getIntent().getParcelableExtra(TaskItem.class.getSimpleName()));
        if (currentUserRef.equals(item.postCreator)) {
            deletePost.setVisibility(View.VISIBLE);
        }
        tvTitle.setText(item.title);
        tvDescription.setText(item.description);
        tvPhoneNumber.setText(item.location);
        tvFoodTags.setText(item.tags);
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
        });

        deletePost.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                db.collection("posts").document(item.postId)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("DetailActivity", "Post successfully deleted.");
                                Toast.makeText(DetailActivity.this, "Post successfully deleted.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("DetailActivity", "Error deleting post", e);
                            }
                        });
            }
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
