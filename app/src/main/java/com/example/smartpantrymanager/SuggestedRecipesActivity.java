package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {
    ListView recipesListView;
    TextView tvNoMatch;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);
        
        db = new DatabaseHelper(this);
        recipesListView = findViewById(R.id.recipesListView);
        tvNoMatch = findViewById(R.id.tvNoMatch);
        
        findMatches();
    }

    private void findMatches() {
        List<Ingredient> pantry = db.getAllIngredients();
        List<Recipe> allRecipes = db.getAllRecipes();
        List<Recipe> matched = new ArrayList<>();
        
        for (Recipe r : allRecipes) {
            String[] required = r.getIngredientsList().split(",");
            boolean canMake = true;
            
            for (String req : required) {
                req = req.trim().toLowerCase();
                // strip trailing s for simple plural matching
                if (req.endsWith("s")) {
                    req = req.substring(0, req.length() - 1);
                }
                
                boolean found = false;
                for (Ingredient p : pantry) {
                    String pName = p.getName().trim().toLowerCase();
                    if (pName.contains(req) || req.contains(pName)) {
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    canMake = false;
                    break;
                }
            }
            
            if (canMake) {
                matched.add(r);
            }
        }
        
        if (matched.isEmpty()) {
            tvNoMatch.setVisibility(View.VISIBLE);
            recipesListView.setVisibility(View.GONE);
        } else {
            tvNoMatch.setVisibility(View.GONE);
            recipesListView.setVisibility(View.VISIBLE);
            RecipeAdapter adapter = new RecipeAdapter(this, matched);
            recipesListView.setAdapter(adapter);
        }
    }
}
