package com.hafiz.expense.buddy.ui.transaction;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactionTabHolderViewModel extends ViewModel {

    // Backing field — private, mutable
    private final MutableLiveData<TransactionTypeEnum> selectedTab =
            new MutableLiveData<>(TransactionTypeEnum.EXPENSE); // default

    // Public API — immutable for observers
    public LiveData<TransactionTypeEnum> getSelectedTab() {
        return selectedTab;
    }

    /**
     * Call this to switch the active tab.
     * No-op if the same tab is already selected (avoids redundant fragment swaps).
     *
     * @param tab the tab to activate
     */
    public void selectTab(@NonNull TransactionTypeEnum tab) {
        if (tab != selectedTab.getValue()) {
            selectedTab.setValue(tab);
        }
    }

    /** Convenience helpers used by unit tests or external callers. */
    public boolean isExpenseTabSelected() {
        return selectedTab.getValue() == TransactionTypeEnum.EXPENSE;
    }

    public boolean isIncomeTabSelected() {
        return selectedTab.getValue() == TransactionTypeEnum.INCOME;
    }
}