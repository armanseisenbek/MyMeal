package com.onepercent.mymeal.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.onepercent.mymeal.models.Meal;

@Database(entities = {Meal.class}, version = 1)
public abstract class MealDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "meals_db";

    private static MealDatabase instance;

    public static MealDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MealDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

}
