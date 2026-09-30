package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class IngredientAdapter extends BaseAdapter {
    private Context context;
    private List<Ingredient> items;
    private DatabaseHelper db;

    public IngredientAdapter(Context context, List<Ingredient> items) {
        this.context = context;
        this.items = items;
        this.db = new DatabaseHelper(context);
    }

    @Override
    public int getCount() { return items.size(); }

    @Override
    public Object getItem(int position) { return items.get(position); }

    @Override
    public long getItemId(int position) { return items.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false);
        }
        
        Ingredient ing = items.get(position);
        
        TextView tvName = convertView.findViewById(R.id.tvItemName);
        TextView tvQty = convertView.findViewById(R.id.tvItemQty);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        
        tvName.setText(ing.getName());
        tvQty.setText(ing.getQuantity() + " " + ing.getUnit());
        
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteIngredient(ing.getId());
                items.remove(position);
                notifyDataSetChanged();
            }
        });
        
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddEditIngredientActivity.class);
                i.putExtra("id", ing.getId());
                i.putExtra("name", ing.getName());
                i.putExtra("qty", ing.getQuantity());
                i.putExtra("unit", ing.getUnit());
                context.startActivity(i);
            }
        });
        
        return convertView;
    }
}
