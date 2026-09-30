package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {
    EditText editName, editQty, editUnit;
    Button btnSave;
    DatabaseHelper db;
    int updateId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        
        db = new DatabaseHelper(this);
        editName = findViewById(R.id.editName);
        editQty = findViewById(R.id.editQty);
        editUnit = findViewById(R.id.editUnit);
        btnSave = findViewById(R.id.btnSave);
        
        if (getIntent().hasExtra("id")) {
            updateId = getIntent().getIntExtra("id", -1);
            editName.setText(getIntent().getStringExtra("name"));
            editQty.setText(String.valueOf(getIntent().getDoubleExtra("qty", 0)));
            editUnit.setText(getIntent().getStringExtra("unit"));
        }
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String qtyStr = editQty.getText().toString().trim();
                String unit = editUnit.getText().toString().trim();
                
                if (name.isEmpty() || qtyStr.isEmpty() || unit.isEmpty()) {
                    Toast.makeText(AddEditIngredientActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                double qty = 0;
                try {
                    qty = Double.parseDouble(qtyStr);
                } catch (Exception e) {
                    Toast.makeText(AddEditIngredientActivity.this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                if (updateId == -1) {
                    Ingredient ing = new Ingredient(0, name, qty, unit);
                    db.addIngredient(ing);
                    Toast.makeText(AddEditIngredientActivity.this, "Added to pantry", Toast.LENGTH_SHORT).show();
                } else {
                    Ingredient ing = new Ingredient(updateId, name, qty, unit);
                    db.updateIngredient(ing);
                    Toast.makeText(AddEditIngredientActivity.this, "Updated pantry", Toast.LENGTH_SHORT).show();
                }
                
                finish();
            }
        });
    }
}
