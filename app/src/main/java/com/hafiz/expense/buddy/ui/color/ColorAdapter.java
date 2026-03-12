package com.hafiz.expense.buddy.ui.color;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hafiz.expense.buddy.R;
import com.hafiz.expense.buddy.databinding.ColorItemLayoutBinding;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private String[] colors;
    private OnColorClick listener;

    public interface OnColorClick {
        void onClick(String color);
    }

    public ColorAdapter(String[] colors, OnColorClick listener) {
        this.colors = colors;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ColorItemLayoutBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.color_item_layout, parent, false);
        return new ColorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {

        String color = colors[position];

        holder.binding.viewColor.setBackgroundTintList(
                ColorStateList.valueOf(Color.parseColor(color))
        );

        holder.binding.viewColor.setOnClickListener(v -> {
            listener.onClick(color);
        });
    }

    @Override
    public int getItemCount() {
        return colors.length;
    }


    public static class ColorViewHolder extends RecyclerView.ViewHolder {

        ColorItemLayoutBinding binding;

        public ColorViewHolder(ColorItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}