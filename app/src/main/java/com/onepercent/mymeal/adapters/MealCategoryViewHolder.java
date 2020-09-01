package com.onepercent.mymeal.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.onepercent.mymeal.R;

public class MealCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnMealListener onMealListener;

    ShapeableImageView categoryImage;
    TextView categoryTitle;

    public MealCategoryViewHolder(@NonNull View itemView, OnMealListener onMealListener) {
        super(itemView);

        this.onMealListener = onMealListener;

        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMealListener.onMealCategoryClick(categoryTitle.getText().toString());
    }
}
