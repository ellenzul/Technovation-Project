package com.example.foodshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodshare.models.TaskItem;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    private Button btnComplete;
    private TextView tvTitle;
    private TextView tvDescription;
    private ImageView imageView;
    private TaskItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle.setText(item.title);
        tvDescription.setText(item.description);
        imageView.setImageBitmap(item.image);

        btnComplete = findViewById(R.id.btnComplete);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        imageView = findViewById(R.id.itemImageView);
        item = Parcels.unwrap(getIntent().getParcelableExtra(TaskItem.class.getSimpleName()));

    }
}
