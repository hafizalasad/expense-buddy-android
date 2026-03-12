package com.hafiz.expense.buddy.ui.transaction.expense;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hafiz.expense.buddy.R;
import com.hafiz.expense.buddy.databinding.ExpenseManageFragmentBinding;

public class ExpenseManageFragment extends Fragment {

    private ExpenseManageViewModel viewModel;

    private ExpenseManageFragmentBinding binding;

    private long selectedCategoryId = -1;

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

//        viewModel.categoryList.observe(getViewLifecycleOwner(), categories -> {
//
//            binding.llCategory.removeAllViews();
//
//            for(CategoryEntity category : categories){
//
//                TextView chip = new TextView(getContext());
//
//                chip.setText(category.getName());
//                chip.setPadding(32,16,32,16);
//
//                chip.setBackgroundResource(android.R.drawable.btn_default);
//
//                chip.setOnClickListener(v -> {
//
//                    selectedCategoryId = category.getId();
//                });
//
//                binding.llCategory.addView(chip);
//            }
//
//        });
    }

    private void setupListeners() {

        binding.btnSaveExpense.setOnClickListener(v -> saveExpense());

        binding.tvAddCategory.setOnClickListener(v -> openCategoryDialog());
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

    private void openCategoryDialog() {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_category, null);

        EditText etCategoryName = view.findViewById(R.id.etCategoryName);

        new AlertDialog.Builder(getContext())
                .setTitle("Add Category")
                .setView(view)
                .setPositiveButton("Save", (dialog, which) -> {

                    String name = etCategoryName.getText().toString();

                    if (!name.isEmpty()) {

                        viewModel.createCategory(name);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}