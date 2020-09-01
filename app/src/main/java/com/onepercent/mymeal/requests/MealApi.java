package com.onepercent.mymeal.requests;

import com.onepercent.mymeal.models.MealCategory;
import com.onepercent.mymeal.requests.responses.MealCategoryResponse;
import com.onepercent.mymeal.requests.responses.MealSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {

    // SEARCH MEALS
    @GET("search.php")
    Call<MealSearchResponse> searchMeal(@Query("s") String query);

    // GET MEAL CATEGORIES
    @GET("categories.php")
    Call<MealCategoryResponse> getMealCategories();
}
