package com.example.foodshare.models;

import android.graphics.Bitmap;
import android.net.Uri;

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
    public String postId;
    public String postCreator;

    public TaskItem() {
    }

    public TaskItem(String title, String description, String tags, String location) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.location = location;
    }

}
