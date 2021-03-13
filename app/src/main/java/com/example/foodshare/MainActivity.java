package com.example.foodshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import com.example.foodshare.models.TaskItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TaskItem> foodList;
    private RecyclerView itemsRecyclerView;
    private ItemAdapter itemAdapter;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openactivity_add();
            }
        });

        foodList = new ArrayList<>();
        TaskItem fakeItem = new TaskItem("Food1", "Description of food", "Peanuts", "123 Mohave Terrace", "2 Portions Available");
        fakeItem.image = BitmapFactory.decodeResource(this.getResources(), R.drawable.foodimage);
        foodList.add(fakeItem);
        TaskItem fakeItem2 = new TaskItem("Food2", "Description of food2!", "None", "Local store", "5 Portions Available");
        fakeItem2.image = BitmapFactory.decodeResource(this.getResources(), R.drawable.foodimage);
        foodList.add(fakeItem2);


        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(foodList);
        itemsRecyclerView.setAdapter(itemAdapter);
    }

    public void openactivity_add() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

}