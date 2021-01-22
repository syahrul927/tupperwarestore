package com.example.tupperwarestore.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupperwarestore.R;
import com.example.tupperwarestore.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyView> {
    private List<CategoryModel> list = new ArrayList<>();
    public CategoryAdapter() {
    }

    public CategoryAdapter(List<CategoryModel> list) {
        this.list = list;
    }


    public void updateData(List<CategoryModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_item,
                        parent,
                        false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        CategoryModel model = list.get(position);
        holder.textView.setText(model.getCategory());
        int colorText = model.getStatus() ? Color.rgb(255,255,255 ):Color.rgb(0,0,0);
        int colorBackground = model.getStatus() ? Color.rgb(253, 136, 27 ):Color.rgb(255,255,255);
        holder.textView.setTextColor(colorText);
        holder.textView.setBackgroundTintList(ColorStateList.valueOf(colorBackground));
        holder.textView.setOnClickListener(v -> {
            for (int i = 0; i<list.size();i++){
                list.get(i).setStatus(i == position);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView textView;
        public MyView(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.titleCategory);

        }
    }
}
