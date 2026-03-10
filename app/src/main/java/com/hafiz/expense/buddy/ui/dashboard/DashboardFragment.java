package com.hafiz.expense.buddy.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.hafiz.expense.buddy.R;
import com.hafiz.expense.buddy.databinding.DashboardFragmentBinding;

public class DashboardFragment extends Fragment {

    private DashboardFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = DashboardFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fabAddExpense.setOnClickListener(v ->

                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.dest_transaction)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}