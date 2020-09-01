package com.onepercent.mymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onepercent.mymeal.adapters.MealRecyclerAdapter;
import com.onepercent.mymeal.adapters.OnMealListener;
import com.onepercent.mymeal.models.MealCategory;
import com.onepercent.mymeal.viewmodels.MealCategoryViewModel;

import java.util.List;

public class MealCategoryActivity extends AppCompatActivity implements OnMealListener {

    private static final String TAG = "MealCategoryActivity";

    private MealCategoryViewModel mMealCategoryViewModel;
    private MealRecyclerAdapter mMealRecyclerAdapter;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_category);

        mMealCategoryViewModel = new ViewModelProvider(this).get(MealCategoryViewModel.class);
        mRecyclerView = findViewById(R.id.meal_category_list);
        mProgressBar = findViewById(R.id.progress_bar);
        mErrorText = findViewById(R.id.error_text);

        showProgressBar(true);

        mMealCategoryViewModel.retrieveMealCategories();

        initRecyclerView();
        subscribeObservers();
        initSearchView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMealRecyclerAdapter = new MealRecyclerAdapter(this);
        mRecyclerView.setAdapter(mMealRecyclerAdapter);
    }

    private void subscribeObservers() {

        mMealCategoryViewModel.getMealCategories().observe(this, new Observer<List<MealCategory>>() {
            @Override
            public void onChanged(List<MealCategory> mealCategories) {
                if (mealCategories != null) {
                    mMealRecyclerAdapter.setMealCategories(mealCategories);
                    showProgressBar(false);
                    mMealCategoryViewModel.setRetrievedMealCategory(true);
                }
            }
        });

        mMealCategoryViewModel.isMealCategoryRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !mMealCategoryViewModel.didRetrieveMealCategory()) {
                    displayErrorScreen();
                }
            }
        });

    }

    private void initSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onMealCategoryClick(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void displayErrorScreen() {
        showProgressBar(false);
        mErrorText.setVisibility(View.VISIBLE);
    }

    public void showProgressBar(boolean visibility){
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onMealClick(int position) {
    }

    @Override
    public void onMealCategoryClick(String category) {
        Intent intent = new Intent(this, MealListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}