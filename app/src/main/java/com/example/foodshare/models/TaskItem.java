package com.example.foodshare.models;

import android.graphics.Bitmap;

import org.parceler.Parcel;

@Parcel
public class TaskItem {

    // list of attributes
    public String title;
    public String description;
    public String tags;
    public String location;
    public Bitmap image;
    public String portions;



    public TaskItem() {
        // all items are initially incomplete
    }

    public TaskItem(String title, String description, String tags, String location, String portions) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.location = location;
        this.portions = portions;
    }

}
