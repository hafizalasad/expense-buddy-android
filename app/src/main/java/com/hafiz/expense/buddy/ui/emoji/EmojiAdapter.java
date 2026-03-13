package com.hafiz.expense.buddy.ui.emoji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hafiz.expense.buddy.data.CategoryIconEnum;
import com.hafiz.expense.buddy.databinding.ColorItemLayoutBinding;
import com.hafiz.expense.buddy.databinding.DialogAddCategoryBinding;
import com.hafiz.expense.buddy.databinding.EmojiItemLayoutBinding;

import java.util.List;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {

    private  CategoryIconEnum[] iconList;
    private OnEmojiClick listener;

    private Context context;

    public interface OnEmojiClick {
        void onClick(CategoryIconEnum selectedIcon);
    }

    public EmojiAdapter(CategoryIconEnum[] iconList, OnEmojiClick listener, Context context) {
        this.iconList = iconList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        EmojiItemLayoutBinding binding =
                EmojiItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CategoryIconEnum iconEnum = iconList[position];

       holder.binding.tvEmoji.setImageResource(iconEnum.getIcon());

        holder.itemView.setOnClickListener(v -> {
            listener.onClick(iconEnum);
        });
    }

    @Override
    public int getItemCount() {
        return iconList.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        EmojiItemLayoutBinding binding;

        public ViewHolder(EmojiItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}