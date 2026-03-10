package com.hafiz.expense.buddy.ui.transaction.expense;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hafiz.expense.buddy.R;

public class ExpenseAddFragment extends Fragment {

    private ExpenseAddViewModel mViewModel;

    public static ExpenseAddFragment newInstance() {
        return new ExpenseAddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.expense_add_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ExpenseAddViewModel.class);
        // TODO: Use the ViewModel
    }

}