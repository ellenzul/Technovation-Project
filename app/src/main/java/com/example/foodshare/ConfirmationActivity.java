package com.example.foodshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodshare.models.TaskItem;

import org.parceler.Parcels;

public class ConfirmationActivity extends AppCompatActivity {

    private Button exit_button;
    private TextView requestConfirm;
    private TaskItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        exit_button = findViewById(R.id.exit_button);
        requestConfirm = findViewById(R.id.requestConfirm);

        exit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {openNewActivity();
            }
        });
    }

    public void openNewActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        item = Parcels.unwrap(getIntent().getParcelableExtra(TaskItem.class.getSimpleName()));
    }
}