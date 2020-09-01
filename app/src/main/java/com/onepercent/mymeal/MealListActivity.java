package com.onepercent.mymeal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onepercent.mymeal.adapters.MealRecyclerAdapter;
import com.onepercent.mymeal.adapters.OnMealListener;
import com.onepercent.mymeal.models.Meal;
import com.onepercent.mymeal.viewmodels.MealListViewModel;

import java.util.List;


public class MealListActivity extends AppCompatActivity implements OnMealListener {

    private static final String TAG = "MealListActivity";

    private MealListViewModel mMealListViewModel;
    private MealRecyclerAdapter mMealRecyclerAdapter;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mErrorText;

    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        mMealListViewModel = new ViewModelProvider(this).get(MealListViewModel.class);
        mRecyclerView = findViewById(R.id.meal_list);

        mProgressBar = findViewById(R.id.progress_bar);
        mErrorText = findViewById(R.id.error_text);

        showProgressBar(true);
        
        getIncomingIntent();
        initRecyclerView();
        subscribeObservers();
        initSearchView();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("category")){
            mQuery = getIntent().getStringExtra("category");
            mMealListViewModel.searchMealsApi(mQuery);
        }
    }

    private void subscribeObservers() {
        mMealListViewModel.getMeals().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (meals != null) {
                    if (mQuery.equals(mMealListViewModel.getMealQuery())) {
                        mMealListViewModel.setIsPerformingQuery(false);
                        mMealRecyclerAdapter.setMeals(meals);
                        mMealListViewModel.setRetrievedMealList(true);
                        showRecyclerView(true);
                    }
                }
            }
        });

        mMealListViewModel.isMealListRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !mMealListViewModel.didRetrieveMealList()) {
                    displayErrorScreen(true);
                }
            }
        });
    }

    private void initRecyclerView() {
        mMealRecyclerAdapter = new MealRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mMealRecyclerAdapter);
    }

    private void initSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgressBar(true);
                mMealListViewModel.setRetrievedMealList(false);
                mQuery = query;
                mMealListViewModel.searchMealsApi(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void displayErrorScreen(boolean visibility) {
        if (visibility) {
            showProgressBar(false);
            showRecyclerView(false);
        }
        mErrorText.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    public void showProgressBar(boolean visibility){
        if (visibility) {
            displayErrorScreen(false);
            showRecyclerView(false);
        }
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    public void showRecyclerView(boolean visibility){
        if (visibility) {
            showProgressBar(false);
            displayErrorScreen(false);
        }
        mRecyclerView.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onMealClick(int position) {
        Intent intent = new Intent(this, MealActivity.class);
        intent.putExtra("mealPosition", position);
        startActivity(intent);
    }

    @Override
    public void onMealCategoryClick(String category) {
    }

    @Override
    public void onBackPressed() {
        mMealListViewModel.cancelRequest();
        super.onBackPressed();
    }
}