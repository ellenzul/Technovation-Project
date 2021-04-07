package com.example.foodshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.foodshare.models.TaskItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TaskItem> foodList;
    private RecyclerView itemsRecyclerView;
    private ItemAdapter itemAdapter;
    private Button addButton;
    static final int ADD_REQUEST_ID = 10;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> openAddActivity());

        foodList = new ArrayList<>();

        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(foodList);
        itemsRecyclerView.setAdapter(itemAdapter);

        db = FirebaseFirestore.getInstance();
        fetchPostsFromFirebase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miLogout:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetchPostsFromFirebase() {
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("MainActivity", document.getId() + " => " + document.getData());
                                Map<String, Object> postData = document.getData();
                                TaskItem newPost = new TaskItem(
                                        postData.get("name").toString(),
                                        postData.get("description").toString(),
                                        postData.get("tags").toString(),
                                        postData.get("location").toString()
                                );
                                foodList.add(newPost);
                                itemAdapter.notifyItemInserted(foodList.size() - 1);
                            }
                        } else {
                            Log.w("MainActivity", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void addPostToFirebase(TaskItem item) {
        Map<String, Object> dbItem = new HashMap<>();
        dbItem.put("name", item.title);
        dbItem.put("description", item.description);
        dbItem.put("tags", item.tags);
        dbItem.put("location", new GeoPoint(50, 50)); // Fake location for now!!

        db.collection("posts").add(dbItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("MainActivity", "New post added successfully: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MainActivity", "Error adding post", e);
                    }
                });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void openAddActivity() {
        Intent intent = new Intent(this, AddActivity.class);
        this.startActivityForResult(intent, ADD_REQUEST_ID);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        if (requestCode == ADD_REQUEST_ID) {
            TaskItem newItem = Parcels.unwrap(data.getParcelableExtra("task_item"));
            addPostToFirebase(newItem);
            foodList.add(0, newItem);
            itemAdapter.notifyItemInserted(0);
            itemsRecyclerView.scrollToPosition(0);
        }
    }
}

