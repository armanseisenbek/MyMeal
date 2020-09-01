package com.onepercent.mymeal.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.onepercent.mymeal.AppExecutors;
import com.onepercent.mymeal.models.Meal;
import com.onepercent.mymeal.models.MealCategory;
import com.onepercent.mymeal.requests.responses.MealCategoryResponse;
import com.onepercent.mymeal.requests.responses.MealSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.onepercent.mymeal.util.Constants.NETWORK_TIMEOUT;

public class MealApiClient {

    private static final String TAG = "MealApiClient";

    private static MealApiClient instance;

    private MutableLiveData<List<Meal>> mMeals;
    private MutableLiveData<List<MealCategory>> mMealCategories;

    private RetrieveMealsRunnable mRetrieveMealsRunnable;
    private RetrieveMealCategoriesRunnable mRetrieveMealCategoriesRunnable;

    private MutableLiveData<Boolean> mMealListRequestTimeout = new MutableLiveData<>();
    private MutableLiveData<Boolean> mMealCategoryRequestTimeout = new MutableLiveData<>();

    // Singleton
    public static MealApiClient getInstance() {
        if (instance == null) {
            instance = new MealApiClient();
        }
        return instance;
    }

    private MealApiClient() {
        mMeals = new MutableLiveData<>();
        mMealCategories = new MutableLiveData<>();
    }

    public LiveData<List<Meal>> getMeals() {
        return mMeals;
    }

    public LiveData<List<MealCategory>> getMealCategories() {
        return mMealCategories;
    }

    public LiveData<Boolean> isMealListRequestTimedOut() {
        return mMealListRequestTimeout;
    }

    public LiveData<Boolean> isMealCategoryRequestTimedOut() {
        return mMealCategoryRequestTimeout;
    }

    public void searchMealsApi(String query) {
        if (mRetrieveMealsRunnable != null) {
            mRetrieveMealsRunnable = null;
        }
        mMeals.setValue(null);
        mRetrieveMealsRunnable = new RetrieveMealsRunnable(query);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveMealsRunnable);

        mMealListRequestTimeout.setValue(false);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                mMealListRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMealsRunnable implements Runnable {

        private String query;
        boolean cancelRequest;

        public RetrieveMealsRunnable(String query) {
            this.query = query;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {

                Response response = getMeals(query).execute();

                if (cancelRequest) {
                    Log.d(TAG, "run: request cancelled");
                    return;
                }

                if (response.code() == 200) {
                    List<Meal> list = new ArrayList<>(((MealSearchResponse) response.body()).getMeals());
                    mMeals.postValue(list);
                }
                else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mMeals.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMeals.postValue(null);
            }

        }

        private Call<MealSearchResponse> getMeals(String query) {
            return ServiceGenerator.getMealApi().searchMeal(query);
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: cancelling the search request");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if (mRetrieveMealsRunnable != null) {
            mRetrieveMealsRunnable.cancelRequest();
        }
    }

    public void retrieveMealCategories() {

        if (mRetrieveMealCategoriesRunnable != null) {
            mRetrieveMealCategoriesRunnable = null;
        }
        mRetrieveMealCategoriesRunnable = new RetrieveMealCategoriesRunnable();
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveMealCategoriesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                mMealCategoryRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class RetrieveMealCategoriesRunnable implements Runnable {

        public RetrieveMealCategoriesRunnable() {
        }

        @Override
        public void run() {
            try {
                Response response = getMealCategories().execute();
                if (response.code() == 200) {
                    List<MealCategory> list =
                            new ArrayList<>(((MealCategoryResponse) response.body()).getMealCategories());
                    mMealCategories.postValue(list);
                }
                else {
                    String error = response.errorBody().string();
                    Log.d(TAG, "run: " + error);
                    mMealCategories.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMealCategories.postValue(null);
            }

        }

        private Call<MealCategoryResponse> getMealCategories() {
            return ServiceGenerator.getMealApi().getMealCategories();
        }
    }
}
