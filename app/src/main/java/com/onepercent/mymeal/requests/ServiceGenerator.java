package com.onepercent.mymeal.requests;


import com.onepercent.mymeal.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static MealApi mealApi = retrofit.create(MealApi.class);

    public static MealApi getMealApi(){
        return mealApi;
    }
}
