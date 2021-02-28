package com.example.foodshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import com.example.foodshare.models.TaskItem;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TaskItem> foodList;
    private RecyclerView itemsRecyclerView;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodList = new ArrayList<>();
        TaskItem fakeItem = new TaskItem("Food1", "Description of food", "Peanuts", "123 Mohave Terrace");
        foodList.add(fakeItem);
        TaskItem fakeItem2 = new TaskItem("Food2", "Description of food2!", "None", "Local store");
        foodList.add(fakeItem2);

        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(foodList);
        itemsRecyclerView.setAdapter(itemAdapter);
    }
}
