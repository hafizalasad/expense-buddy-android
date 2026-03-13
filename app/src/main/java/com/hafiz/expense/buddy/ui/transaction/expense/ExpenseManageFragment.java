package com.hafiz.expense.buddy.ui.transaction.expense;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hafiz.expense.buddy.data.CategoryColorPalette;
import com.hafiz.expense.buddy.data.CategoryIconEnum;
import com.hafiz.expense.buddy.data.local.entity.CategoryEntity;
import com.hafiz.expense.buddy.databinding.DialogAddCategoryBinding;
import com.hafiz.expense.buddy.databinding.ExpenseManageFragmentBinding;
import com.hafiz.expense.buddy.ui.color.ColorAdapter;
import com.hafiz.expense.buddy.ui.emoji.EmojiAdapter;
import com.hafiz.expense.buddy.utils.ColorUtils;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.recent.RecentEmojiManager;

public class ExpenseManageFragment extends Fragment {

    private ExpenseManageFragmentBinding binding;
    private ExpenseManageViewModel viewModel;

    private long selectedCategoryId = -1;
    public static final String[] EMOJIS = {
            "🍱","👶","🥩", "💰","🍎","🍜",
            "🚗","🚌","✈️","⛽","🚕","🚆",
            "🏠","💡","📱","💻","🛜",
            "🎮","🎬","⚽","🎵","📚",
            "🛒","👕","💊","🎁","💰"
    };

    public static ExpenseManageFragment newInstance() {
        return new ExpenseManageFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        binding = ExpenseManageFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ExpenseManageViewModel.class);

        setupObservers();

        setupListeners();
    }

    private void setupObservers() {

        viewModel.categoryList.observe(getViewLifecycleOwner(), categories -> {

            binding.llCategory.removeAllViews();

            for (CategoryEntity category : categories) {

                TextView chip = new TextView(getContext());

                chip.setText(category.getName());
                chip.setPadding(32, 16, 32, 16);

                chip.setBackgroundResource(android.R.drawable.btn_default);

                chip.setOnClickListener(v -> {

                    selectedCategoryId = category.getId();
                });

                binding.llCategory.addView(chip);
            }

        });
    }

    private void setupListeners() {

        binding.btnSaveExpense.setOnClickListener(v -> saveExpense());

        binding.tvAddCategory.setOnClickListener(v -> handleAddCategory());
    }

    private void saveExpense() {

        String amountStr = binding.etAmount.getText().toString();

        if (amountStr.isEmpty()) {
            binding.etAmount.setError("Enter amount");
            return;
        }

        double amount = Double.parseDouble(amountStr);

        String note = binding.etNote.getText().toString();

        String date = binding.tvSelectedDate.getText().toString();

        viewModel.saveExpense(
                amount,
                note,
                date,
                selectedCategoryId,
                "Cash"
        );

        Toast.makeText(getContext(), "Expense Saved", Toast.LENGTH_SHORT).show();
    }


    private void populateEmojiList(RecyclerView rvEmoji, TextView emojiTextView,CategoryEntity categoryEntity) {

        EmojiAdapter adapter = new EmojiAdapter(
                CategoryIconEnum.values(),
                selectedIconEnum -> {
                    categoryEntity.setIcon(selectedIconEnum.name());
                    rvEmoji.setVisibility(View.GONE);
                },getContext()
        );

        rvEmoji.setLayoutManager(new GridLayoutManager(getContext(), 6));
        rvEmoji.setAdapter(adapter);
    }

    private void handleAddCategory() {

        CategoryEntity categoryEntity = new CategoryEntity();
        DialogAddCategoryBinding binding =
                DialogAddCategoryBinding.inflate(LayoutInflater.from(getContext()));
        populateEmojiList(binding.rvEmoji,binding.tvAddIcon,categoryEntity);

        ColorAdapter adapter = new ColorAdapter(
                CategoryColorPalette.COLORS,
                selectedColor -> {
                    String backgroundColor;

                    // generate background
                    backgroundColor =
                            ColorUtils.generateLightColor(selectedColor);
                    categoryEntity.setColor(selectedColor);
                    categoryEntity.setBackgroundColor(backgroundColor);

                });

        binding.rvColors.setLayoutManager(new GridLayoutManager(getContext(), 4));

        binding.rvColors.setAdapter(adapter);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Add Category")
                .setView(binding.getRoot())
                .setPositiveButton("Save", (dialog, which) -> {
                })
                .setNegativeButton("Cancel", null)
                .show();

        binding.tvAddIcon.setOnClickListener(v-> {
            binding.rvEmoji.setVisibility(View.VISIBLE);
        });

        binding.btnDone.setOnClickListener(v-> {
            String name = binding.etCategoryName.getText().toString();

            if (!name.isEmpty()) {
                 categoryEntity.setName(name);
                viewModel.saveCategory(categoryEntity);
            }
            alertDialog.dismiss();
        });
    }
}