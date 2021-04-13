package com.example.foodshare.models;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.parceler.Parcel;

@Parcel
public class TaskItem {

    // list of attributes
    public String title;
    public String description;
    public String tags;
    public String location;
    public Uri imageUri;
    public Bitmap fullImage;

    public TaskItem() {
        // all items are initially incomplete
    }

    public TaskItem(String title, String description, String tags, String location) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.location = location;
    }

}
