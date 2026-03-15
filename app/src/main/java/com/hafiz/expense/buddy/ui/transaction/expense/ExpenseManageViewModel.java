package com.hafiz.expense.buddy.ui.transaction.expense;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.hafiz.expense.buddy.data.local.entity.CategoryEntity;
import com.hafiz.expense.buddy.data.local.entity.TransactionEntity;
import com.hafiz.expense.buddy.data.repository.CategoryRepository;
import com.hafiz.expense.buddy.utils.FileUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseManageViewModel extends AndroidViewModel {

//    private final TransactionRepository repository;

    private final CategoryRepository categoryRepository;

    public LiveData<List<CategoryEntity>> categoryList;

    private final MutableLiveData<String> selectedImagePath = new MutableLiveData<>();

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    // Stored as long (epoch ms) — matches Room column type
    private final MutableLiveData<Long> selectedDateMillis =
            new MutableLiveData<>(System.currentTimeMillis()); // default: today

    // Derived from selectedDateMillis — drives tvSelectedDate via DataBinding
    // Transformations.map keeps formatting off the UI thread and in the ViewModel where it belongs
    public final LiveData<String> formattedDate = Transformations.map(
            selectedDateMillis,
            millis -> DATE_FORMAT.format(new Date(millis))
    );
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ExpenseManageViewModel(@NonNull Application application) {
        super(application);

//        repository = new TransactionRepository;

        categoryRepository = CategoryRepository.getInstance(application.getApplicationContext());
        categoryList = categoryRepository.getAll();
    }

    public void saveExpense(
            double amount,
            String note,
            long date,
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

    public void onImageSelected(Uri uri) {
        executor.execute(() -> {
            String path = FileUtil.copyUriToInternalStorage(getApplication(), uri);
            if (path != null) {
//                repository.save(path);
                selectedImagePath.postValue(path); // postValue — called from background thread
            }
        });
    }

    public LiveData<String> getSelectedImagePath() {
        return selectedImagePath;
    }

    public void onDateSelected(long epochMillis) {
        selectedDateMillis.setValue(epochMillis);
    }

    /**
     * Returns the raw long for saving to Room
     */
    public long getSelectedDateMillis() {
        Long value = selectedDateMillis.getValue();
        return value != null ? value : System.currentTimeMillis();
    }

}