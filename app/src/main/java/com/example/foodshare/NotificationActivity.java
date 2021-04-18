package com.example.foodshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodshare.models.TaskItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    private ArrayList<TaskItem> myPostsList;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private RecyclerView myPostsRecyclerView;
    private ItemAdapter itemAdapter;
    public CardView cardView;

    public String currentUserRef;

    final long ONE_MEGABYTE = 1024 * 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        currentUserRef = getIntent().getExtras().get("current_user").toString();
        myPostsList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        fetchMyPostsFromFirebase();

        myPostsRecyclerView = findViewById(R.id.myPostsRecyclerView);
        myPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(myPostsList);
        myPostsRecyclerView.setAdapter(itemAdapter);

    }


    public void fetchMyPostsFromFirebase() {
        db.collection("posts")
                .whereEqualTo("postCreator", currentUserRef)
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
                                if (postData.get("imageUrl") != null) {
                                    String imageUrl = postData.get("imageUrl").toString();
                                    newPost.imageUri = Uri.parse(imageUrl);
                                    StorageReference httpsReference = storage.getReferenceFromUrl(imageUrl);
                                    httpsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            newPost.fullImage = bmp;
                                            itemAdapter.notifyDataSetChanged();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Toast.makeText(NotificationActivity.this, "Image download failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                myPostsList.add(newPost);
                                itemAdapter.notifyItemInserted(myPostsList.size() - 1);
                            }
                        } else {
                            Log.w("NotificationActivity", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}



