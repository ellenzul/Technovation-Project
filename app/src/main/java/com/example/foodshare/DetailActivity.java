package com.example.foodshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodshare.models.TaskItem;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private ImageView itemImageView;
    private TaskItem item;
    private Button btnConfirm;
    private Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        itemImageView = findViewById(R.id.itemImageView);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnComplete = findViewById(R.id.btnComplete);

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
    }

    public void openNewActivity() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }
}
