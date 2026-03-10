package com.hafiz.expense.buddy.ui.transaction;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactionTabHolderViewModel extends ViewModel {

    // Backing field — private, mutable
    private final MutableLiveData<TransactionTab> selectedTab =
            new MutableLiveData<>(TransactionTab.EXPENSE); // default

    // Public API — immutable for observers
    public LiveData<TransactionTab> getSelectedTab() {
        return selectedTab;
    }

    /**
     * Call this to switch the active tab.
     * No-op if the same tab is already selected (avoids redundant fragment swaps).
     *
     * @param tab the tab to activate
     */
    public void selectTab(@NonNull TransactionTab tab) {
        if (tab != selectedTab.getValue()) {
            selectedTab.setValue(tab);
        }
    }

    /** Convenience helpers used by unit tests or external callers. */
    public boolean isExpenseTabSelected() {
        return selectedTab.getValue() == TransactionTab.EXPENSE;
    }

    public boolean isIncomeTabSelected() {
        return selectedTab.getValue() == TransactionTab.INCOME;
    }
}