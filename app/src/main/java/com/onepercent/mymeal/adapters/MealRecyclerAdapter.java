package com.onepercent.mymeal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepercent.mymeal.R;

import com.onepercent.mymeal.models.Meal;
import com.onepercent.mymeal.models.MealCategory;

import java.util.List;

public class MealRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MealRecyclerAdapter";
    
    private static final int MEAL_TYPE = 1;
    private static final int MEAL_CATEGORY_TYPE = 2;

    private List<Meal> mMeals;
    private List<MealCategory> mMealCategories;
    private OnMealListener mOnMealListener;

    private int mListSize = 0;

    public MealRecyclerAdapter(OnMealListener mOnMealListener) {
        this.mOnMealListener = mOnMealListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {

            case MEAL_TYPE:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_meal_list_item, parent, false);
                return new MealViewHolder(view, mOnMealListener);
            }

            case MEAL_CATEGORY_TYPE:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_category_list_item, parent, false);
                return new MealCategoryViewHolder(view, mOnMealListener);
            }

            default:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_meal_list_item, parent, false);
                return new MealViewHolder(view, mOnMealListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);

        if (itemViewType == MEAL_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mMeals.get(position).getStrMealThumb())
                    .into(((MealViewHolder)holder).imageView);

            ((MealViewHolder)holder).title.setText(mMeals.get(position).getStrMeal());
        }
        else if (itemViewType == MEAL_CATEGORY_TYPE) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background);

                Glide.with(holder.itemView.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(mMealCategories.get(position).getStrCategoryThumb())
                        .into(((MealCategoryViewHolder)holder).categoryImage);

                ((MealCategoryViewHolder)holder).categoryTitle
                        .setText(mMealCategories.get(position).getStrCategory());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mMealCategories != null) {
            return MEAL_CATEGORY_TYPE;
        }
        else if (mMeals != null){
            return MEAL_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mListSize;
    }

    public void setMeals(List<Meal> meals) {
        mMeals = meals;
        if (mMeals == null) {
            mListSize = 0;
        } else {
            mListSize = mMeals.size();
        }

        notifyDataSetChanged();
    }

    public void setMealCategories(List<MealCategory> mealCategories) {
        mMealCategories = mealCategories;
        mListSize = mMealCategories.size();
        notifyDataSetChanged();
    }
}
