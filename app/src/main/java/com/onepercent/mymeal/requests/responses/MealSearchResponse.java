package com.onepercent.mymeal.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.onepercent.mymeal.models.Meal;

import java.util.List;

public class MealSearchResponse {

    @SerializedName("meals")
    @Expose()
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    @Override
    public String toString() {
        return "MealsResponse{" +
                "meals=" + meals +
                '}';
    }
}
