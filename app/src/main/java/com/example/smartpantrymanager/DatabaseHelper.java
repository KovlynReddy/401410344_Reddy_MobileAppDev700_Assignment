package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pantry.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantryTable = "CREATE TABLE pantry (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity REAL, unit TEXT)";
        db.execSQL(createPantryTable);

        String createRecipeTable = "CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, ingredients TEXT, steps TEXT)";
        db.execSQL(createRecipeTable);

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pantry");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }

    public void addIngredient(Ingredient ing) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", ing.getName());
        cv.put("quantity", ing.getQuantity());
        cv.put("unit", ing.getUnit());
        db.insert("pantry", null, cv);
        db.close();
    }

    public void updateIngredient(Ingredient ing) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", ing.getName());
        cv.put("quantity", ing.getQuantity());
        cv.put("unit", ing.getUnit());
        db.update("pantry", cv, "id=?", new String[]{String.valueOf(ing.getId())});
        db.close();
    }

    public void deleteIngredient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("pantry", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pantry", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Ingredient(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    private void seedRecipes(SQLiteDatabase db) {
        // We will insert 15 recipes here
        String[][] recipes = {
            {"Toast", "Bread,Butter", "1. Toast bread. 2. Spread butter."},
            {"Boiled Egg", "Egg,Water", "1. Boil water. 2. Add egg for 7 mins."},
            {"Fried Egg", "Egg,Oil", "1. Heat oil. 2. Fry egg."},
            {"Cheese Sandwich", "Bread,Cheese,Butter", "1. Butter bread. 2. Add cheese. 3. Close sandwich."},
            {"Omelette", "Egg,Milk,Cheese", "1. Whisk egg and milk. 2. Cook in pan. 3. Add cheese."},
            {"Tomato Soup", "Tomato,Water,Salt", "1. Boil tomatoes in water. 2. Blend. 3. Add salt."},
            {"Salad", "Lettuce,Tomato,Cucumber", "1. Chop vegetables. 2. Mix together."},
            {"Pasta", "Pasta,Water,Salt", "1. Boil water with salt. 2. Add pasta and cook 10 mins."},
            {"Tomato Pasta", "Pasta,Tomato,Garlic,Oil", "1. Cook pasta. 2. Fry garlic in oil, add tomatoes. 3. Mix."},
            {"Chicken Rice", "Chicken,Rice,Water", "1. Cook rice. 2. Fry chicken. 3. Serve together."},
            {"Pancakes", "Flour,Milk,Egg,Sugar", "1. Mix ingredients. 2. Fry batter in pan."},
            {"Oatmeal", "Oats,Milk,Honey", "1. Boil milk. 2. Add oats. 3. Top with honey."},
            {"Fruit Smoothie", "Banana,Milk,Honey", "1. Blend banana, milk and honey."},
            {"Garlic Bread", "Bread,Garlic,Butter", "1. Mix garlic and butter. 2. Spread on bread. 3. Bake."},
            {"Scrambled Eggs", "Egg,Butter,Salt", "1. Melt butter. 2. Whisk eggs with salt and cook."}
        };

        for (String[] r : recipes) {
            ContentValues cv = new ContentValues();
            cv.put("name", r[0]);
            cv.put("ingredients", r[1]);
            cv.put("steps", r[2]);
            db.insert("recipes", null, cv);
        }
    }
}
