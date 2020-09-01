package com.onepercent.mymeal.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.onepercent.mymeal.R;

public class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title;
    AppCompatImageView imageView;
    OnMealListener onMealListener;

    public MealViewHolder(@NonNull View itemView, OnMealListener onMealListener) {
        super(itemView);

        this.onMealListener = onMealListener;

        title = itemView.findViewById(R.id.meal_title);
        imageView = itemView.findViewById(R.id.meal_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMealListener.onMealClick(getAdapterPosition());
    }
}
