package com.example.smartpantrymanager;
import java.util.List;
import java.io.Serializable;

public class Recipe implements Serializable {
    private int id;
    private String name;
    private String ingredientsList; // comma separated simple names for checking
    private String steps;

    public Recipe(int id, String name, String ingredientsList, String steps) {
        this.id = id;
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.steps = steps;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getIngredientsList() { return ingredientsList; }
    public String getSteps() { return steps; }
}
