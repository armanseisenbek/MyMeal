package com.onepercent.mymeal.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.onepercent.mymeal.models.MealCategory;

import java.util.List;

public class MealCategoryResponse {

    @SerializedName("categories")
    @Expose()
    private List<MealCategory> mealCategories;

    public List<MealCategory> getMealCategories() {
        return mealCategories;
    }

    @Override
    public String toString() {
        return "MealCategoryResponse{" +
                "mealCategories=" + mealCategories +
                '}';
    }
}
