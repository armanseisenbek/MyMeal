package com.onepercent.mymeal;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepercent.mymeal.models.Meal;
import com.onepercent.mymeal.viewmodels.MealListViewModel;

import java.util.List;

public class MealActivity extends AppCompatActivity {

    private static final String TAG = "MealActivity";

    // ui
    private AppCompatImageView mMealImage;
    private TextView mMealInstructions;
    private Toolbar mToolbar;

    // vars
    private MealListViewModel mealListViewModel;
    private int mMealPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        mMealImage = findViewById(R.id.meal_image);
        mMealInstructions = findViewById(R.id.instructions);

        mealListViewModel = new ViewModelProvider(this).get(MealListViewModel.class);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getIncomingIntent();
        subscribeObservers();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("mealPosition")) {
            mMealPosition = getIntent().getIntExtra("mealPosition", 0);
        }
    }

    private void subscribeObservers() {
        mealListViewModel.getMeals().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (meals != null) {
                    setMealProperties(meals.get(mMealPosition));
                }
            }
        });
    }

    private void setMealProperties(Meal meal) {
        if (meal != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(meal.getStrMealThumb())
                    .into(mMealImage);

            this.setTitle(meal.getStrMeal());
            mMealInstructions.setText(meal.getStrInstructions());
        }
    }

}
