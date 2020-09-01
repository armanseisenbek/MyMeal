package com.onepercent.mymeal.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.onepercent.mymeal.models.MealCategory;
import com.onepercent.mymeal.repositories.MealRepository;

import java.util.List;

public class MealCategoryViewModel extends ViewModel {

    private MealRepository mMealRepository;
    private boolean mDidRetrieveMealCategory;

    public MealCategoryViewModel() {
        mMealRepository = MealRepository.getInstance();
        mDidRetrieveMealCategory = false;
    }

    public LiveData<List<MealCategory>> getMealCategories() {
        return mMealRepository.getMealCategories();
    }

    public LiveData<Boolean> isMealCategoryRequestTimedOut() {
        return mMealRepository.isMealCategoryRequestTimedOut();
    }

    public void retrieveMealCategories() {
        mMealRepository.retrieveMealCategories();
    }

    public void setRetrievedMealCategory(boolean retrievedMealCategory) {
        mDidRetrieveMealCategory = retrievedMealCategory;
    }

    public boolean didRetrieveMealCategory() {
        return mDidRetrieveMealCategory;
    }

}
