package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    TextView tvRecipeName, tvIngredients, tvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        
        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvSteps = findViewById(R.id.tvSteps);
        
        if (getIntent().hasExtra("recipe")) {
            Recipe r = (Recipe) getIntent().getSerializableExtra("recipe");
            tvRecipeName.setText(r.getName());
            tvIngredients.setText(r.getIngredientsList());
            tvSteps.setText(r.getSteps());
        }
    }
}
