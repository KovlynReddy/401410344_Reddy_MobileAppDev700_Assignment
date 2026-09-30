package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecipeAdapter extends BaseAdapter {
    private Context context;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public int getCount() { return recipes.size(); }

    @Override
    public Object getItem(int position) { return recipes.get(position); }

    @Override
    public long getItemId(int position) { return recipes.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        }
        
        Recipe r = recipes.get(position);
        TextView tvName = convertView.findViewById(R.id.tvRecipeTitle);
        TextView tvIng = convertView.findViewById(R.id.tvRecipeIngList);
        
        tvName.setText(r.getName());
        tvIng.setText("Needs: " + r.getIngredientsList());
        
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RecipeDetailActivity.class);
                i.putExtra("recipe", r);
                context.startActivity(i);
            }
        });
        
        return convertView;
    }
}
