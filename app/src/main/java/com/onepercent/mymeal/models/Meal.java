package com.onepercent.mymeal.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meals")
public class Meal implements Parcelable {

    @PrimaryKey
    @NonNull
    private String idMeal;

    @ColumnInfo(name = "strMeal")
    private String strMeal;

    @ColumnInfo(name = "strInstructions")
    private String strInstructions;

    @ColumnInfo(name = "strMealThumb")
    private String strMealThumb;

    @ColumnInfo(name = "timestamp")
    private int timestamp;

    public Meal(@NonNull String idMeal, String strMeal, String strInstructions,
                String strMealThumb, int timestamp) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.timestamp = timestamp;
    }

    public Meal() {
    }

    protected Meal(Parcel in) {
        idMeal = in.readString();
        strMeal = in.readString();
        strInstructions = in.readString();
        strMealThumb = in.readString();
        timestamp = in.readInt();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "idMeal='" + idMeal + '\'' +
                ", strMeal='" + strMeal + '\'' +
                ", strInstructions='" + strInstructions + '\'' +
                ", strMealThumb='" + strMealThumb + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idMeal);
        dest.writeString(strMeal);
        dest.writeString(strInstructions);
        dest.writeString(strMealThumb);
        dest.writeInt(timestamp);
    }
}
