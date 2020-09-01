package com.onepercent.mymeal.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.onepercent.mymeal.models.Meal;
import com.onepercent.mymeal.repositories.MealRepository;

import java.util.List;

public class MealListViewModel extends ViewModel {

    private MealRepository mMealRepository;
    private boolean mIsPerformingQuery;
    private String mMealQuery;
    private boolean mDidRetrieveMealList;

    public MealListViewModel() {
        mMealRepository = MealRepository.getInstance();
        mIsPerformingQuery = false;
        mDidRetrieveMealList = false;
    }

    public LiveData<List<Meal>> getMeals() {
        return mMealRepository.getMeals();
    }

    public LiveData<Boolean> isMealListRequestTimedOut() {
        return mMealRepository.isMealListRequestTimedOut();
    }

    public void searchMealsApi(String query) {
        mMealQuery = query;
        mIsPerformingQuery = true;
        mMealRepository.searchMealsApi(query);
    }

    public void setIsPerformingQuery(boolean isPerformingQuery) {
        mIsPerformingQuery = isPerformingQuery;
    }

    public boolean isPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void cancelRequest() {
        if (mIsPerformingQuery) {
            mMealRepository.cancelRequest();
            mIsPerformingQuery = false;
        }
    }

    public String getMealQuery() {
        return mMealQuery;
    }

    public void setRetrievedMealList(boolean retrievedMealList) {
        mDidRetrieveMealList = retrievedMealList;
    }

    public boolean didRetrieveMealList() {
        return mDidRetrieveMealList;
    }
}
