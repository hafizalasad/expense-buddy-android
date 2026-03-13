package com.hafiz.expense.buddy.ui.transaction.expense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hafiz.expense.buddy.data.local.entity.CategoryEntity;
import com.hafiz.expense.buddy.data.local.entity.TransactionEntity;
import com.hafiz.expense.buddy.data.repository.CategoryRepository;

import java.util.List;

public class ExpenseManageViewModel extends AndroidViewModel {

//    private final TransactionRepository repository;

    private final CategoryRepository categoryRepository;

    public LiveData<List<CategoryEntity>> categoryList;

    public ExpenseManageViewModel(@NonNull Application application) {
        super(application);

//        repository = new TransactionRepository;

        categoryRepository = CategoryRepository.getInstance(application.getApplicationContext());
        categoryList = categoryRepository.getAll();
    }

    public void saveExpense(
            double amount,
            String note,
            String date,
            long categoryId,
            String paymentType) {

        TransactionEntity expense = new TransactionEntity();

        expense.amount = amount;
        expense.note = note;
        expense.date = date;
        expense.categoryId = categoryId;
        expense.paymentType = paymentType;

        //repository.insertExpense(expense);
    }

    public void saveCategory(CategoryEntity categoryEntity) {
         categoryRepository.insert(categoryEntity);
    }
}