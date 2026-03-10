package com.hafiz.expense.buddy.ui.transaction;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.hafiz.expense.buddy.R;
import com.hafiz.expense.buddy.databinding.TransactionTabHolderFragmentBinding;
import com.hafiz.expense.buddy.ui.transaction.expense.ExpenseAddFragment;

public class TransactionTabHolderFragment extends Fragment {
    // ─── Constants ────────────────────────────────────────────────────────────


    // ─── State ────────────────────────────────────────────────────────────────
    private TransactionTabHolderFragmentBinding binding;
    private TransactionTabHolderViewModel viewModel;

    private static final String TAG_EXPENSE = "expense_add";
    private static final String TAG_INCOME = "income_add";
    private static final int ANIM_DURATION_MS = 200;

    // ─── Factory ──────────────────────────────────────────────────────────────
    public static TransactionTabHolderFragment newInstance() {
        return new TransactionTabHolderFragment();
    }

    // ─── Lifecycle ────────────────────────────────────────────────────────────

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TransactionTabHolderFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TransactionTabHolderViewModel.class);

        setupClickListeners();
        observeViewModel();

        // Load default tab only on first creation (avoid re-adding on rotation)
        if (savedInstanceState == null) {
            viewModel.selectTab(TransactionTab.EXPENSE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }

    // ─── Setup ────────────────────────────────────────────────────────────────

    private void setupClickListeners() {
        binding.tabExpense.setOnClickListener(v -> viewModel.selectTab(TransactionTab.EXPENSE));
        binding.tabIncome.setOnClickListener(v -> viewModel.selectTab(TransactionTab.INCOME));
        binding.ivClose.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void observeViewModel() {
        viewModel.getSelectedTab().observe(getViewLifecycleOwner(), this::onTabChanged);
    }

    // ─── Tab Logic ────────────────────────────────────────────────────────────

    /**
     * Called whenever the selected tab changes.
     * Updates visual state and swaps the child fragment.
     */
    private void onTabChanged(@NonNull TransactionTab tab) {
        updateTabIndicator(tab);
        updateTabTextStyles(tab);
        swapFragment(tab);
    }

    /**
     * Translates the white pill indicator to align with the active tab.
     */
    private void updateTabIndicator(@NonNull TransactionTab tab) {
        binding.getRoot().post(() -> {
            // Target X is the left-edge of the active tab TextView
            float targetX = (tab == TransactionTab.EXPENSE)
                    ? binding.tabExpense.getLeft()
                    : binding.tabIncome.getLeft();

            ObjectAnimator.ofFloat(binding.viewTabIndicator, View.TRANSLATION_X,
                            binding.viewTabIndicator.getTranslationX(), targetX)
                    .setDuration(ANIM_DURATION_MS)
                    .start();
        });
    }

    /**
     * Adjusts text color + font weight for selected / unselected tabs.
     */
    private void updateTabTextStyles(@NonNull TransactionTab tab) {
        boolean isExpense = (tab == TransactionTab.EXPENSE);

        binding.tabExpense.setTextColor(
                requireContext().getColor(isExpense
                        ? R.color.tab_text_selected
                        : R.color.tab_text_unselected));

        binding.tabIncome.setTextColor(
                requireContext().getColor(!isExpense
                        ? R.color.tab_text_selected
                        : R.color.tab_text_unselected));

        // Swap font weight via typeface tag (set tags in font resources)
//        binding.tabExpense.setTypeface(
//                requireContext().getResources().getFont(
//                        isExpense ? R.font.poppins_semibold : R.font.poppins_medium));
//
//        binding.tabIncome.setTypeface(
//                requireContext().getResources().getFont(
//                        !isExpense ? R.font.poppins_semibold : R.font.poppins_medium));
    }

    /**
     * Replaces the child fragment based on the selected tab.
     * Uses hide/show when both fragments have already been added,
     * to preserve their state (scroll position, entered values, etc.).
     */
    private void swapFragment(@NonNull TransactionTab tab) {
        Fragment expenseFrag = getChildFragmentManager().findFragmentByTag(TAG_EXPENSE);
        Fragment incomeFrag = getChildFragmentManager().findFragmentByTag(TAG_INCOME);

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        if (tab == TransactionTab.EXPENSE) {
            if (expenseFrag == null) {
                ft.add(R.id.fragment_container, ExpenseAddFragment.newInstance(), TAG_EXPENSE);
            } else {
                ft.show(expenseFrag);
            }
            if (incomeFrag != null) ft.hide(incomeFrag);
        } else {
            if (incomeFrag == null) {
                //ft.add(R.id.fragment_container, IncomeAddFragment.newInstance(), TAG_INCOME);
            } else {
                ft.show(incomeFrag);
            }
            if (expenseFrag != null) ft.hide(expenseFrag);
        }

        ft.commit();
    }

}