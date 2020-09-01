package com.onepercent.mymeal.repositories;

import androidx.lifecycle.LiveData;

import com.onepercent.mymeal.models.Meal;
import com.onepercent.mymeal.models.MealCategory;
import com.onepercent.mymeal.requests.MealApiClient;

import java.util.List;

public class MealRepository {

    private static MealRepository instance;

    private MealApiClient mMealApiClient;

    public static MealRepository getInstance() {
        if (instance == null) {
            instance = new MealRepository();
        }
        return instance;
    }

    private MealRepository() {
        mMealApiClient = MealApiClient.getInstance();
    }

    public LiveData<List<Meal>> getMeals() {
        return mMealApiClient.getMeals();
    }

    public LiveData<List<MealCategory>> getMealCategories() {
        return mMealApiClient.getMealCategories();
    }

    public void retrieveMealCategories() {
        mMealApiClient.retrieveMealCategories();
    }

    public LiveData<Boolean> isMealListRequestTimedOut() {
        return mMealApiClient.isMealListRequestTimedOut();
    }

    public LiveData<Boolean> isMealCategoryRequestTimedOut() {
        return mMealApiClient.isMealCategoryRequestTimedOut();
    }

    public void searchMealsApi(String query) {
        mMealApiClient.searchMealsApi(query);
    }

    public void cancelRequest() {
        mMealApiClient.cancelRequest();
    }



}

