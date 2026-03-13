package com.hafiz.expense.buddy.ui.emoji;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hafiz.expense.buddy.databinding.ColorItemLayoutBinding;
import com.hafiz.expense.buddy.databinding.DialogAddCategoryBinding;
import com.hafiz.expense.buddy.databinding.EmojiItemLayoutBinding;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {

    private String[] emojis;
    private OnEmojiClick listener;

    public interface OnEmojiClick {
        void onClick(String emoji);
    }

    public EmojiAdapter(String[] emojis, OnEmojiClick listener) {
        this.emojis = emojis;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EmojiItemLayoutBinding binding =  EmojiItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String emoji = emojis[position];

        holder.binding.tvEmoji.setText(emoji);

        holder.itemView.setOnClickListener(v -> {
            listener.onClick(emoji);
        });
    }

    @Override
    public int getItemCount() {
        return emojis.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        EmojiItemLayoutBinding binding;

        public ViewHolder(EmojiItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}