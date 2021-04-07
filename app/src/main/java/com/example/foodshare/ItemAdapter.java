package com.example.foodshare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.foodshare.models.TaskItem;

import org.parceler.Parcels;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<TaskItem> mItems;
    Context context;

    public ItemAdapter(List<TaskItem> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.listing, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // get data according to position
        final TaskItem item = mItems.get(position);
        // populate the views according to this data
        holder.titleTextview.setText(item.title);
        holder.descriptionTextview.setText(item.description);
        holder.tagsTextview.setText(item.tags);
        holder.locationTextview.setText(item.location);
        holder.foodImage.setImageBitmap(item.image);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleTextview;
        public TextView descriptionTextview;
        public TextView tagsTextview;
        public TextView locationTextview;
        public ImageView foodImage;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextview = itemView.findViewById(R.id.foodName);
            descriptionTextview = itemView.findViewById(R.id.foodDescription);
            tagsTextview = itemView.findViewById(R.id.foodTags);
            locationTextview = itemView.findViewById(R.id.foodLocation);
            foodImage = itemView.findViewById(R.id.foodImage);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // determine which row was clicked
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                // retrieve the item at that row
                TaskItem item = mItems.get(pos);
                // create an intent from current activity (context) -> DetailActivity
                Intent intent = new Intent(context, DetailActivity.class);
                // include the item as an extra, wrapping it with Parceler library
                intent.putExtra(TaskItem.class.getSimpleName(), Parcels.wrap(item));
                // execute the intent
                context.startActivity(intent);


            }
        }
    }
}
