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

public class ExpenseManageFragment extends Fragment {

    private ExpenseManageViewModel mViewModel;

    public static ExpenseManageFragment newInstance() {
        return new ExpenseManageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.expense_manage_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ExpenseManageViewModel.class);
        // TODO: Use the ViewModel
    }

}