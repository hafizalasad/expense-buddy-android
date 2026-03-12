package com.hafiz.expense.buddy.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.hafiz.expense.buddy.data.local.AppDatabase;
import com.hafiz.expense.buddy.data.local.dao.CategoryDao;
import com.hafiz.expense.buddy.data.local.entity.CategoryEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private final CategoryDao dao;
    private final ExecutorService executorService;
    private static volatile CategoryRepository instance;


    private CategoryRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        dao = db.categoryDao();
        executorService = Executors.newSingleThreadExecutor();

    }

    public static CategoryRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (CategoryRepository.class) {
                if (instance == null) {
                    instance = new CategoryRepository(context);
                }
            }
        }
        return instance;
    }


    public void insert(CategoryEntity entity) {
        executorService.execute(() -> dao.insert(entity));
    }

    public LiveData<List<CategoryEntity>> getAll() {
        return dao.getAll();
    }

//    // Batch Insert (Recommended)
//    public void insertLogs(List<AppLogEntity> logs) {
//        executorService.execute(() -> appLogDao.insertAll(logs));
//    }

//    public void getLogs(int limit, Callback<List<AppLogEntity>> callback) {
//        executorService.execute(() -> {
//            List<AppLogEntity> logs = appLogDao.getLogs(limit);
//            callback.onResult(logs);
//        });
//    }
//
//    public void deleteLogs(List<Long> ids) {
//        executorService.execute(() -> appLogDao.deleteByIds(ids));
//    }
//
//    public interface Callback<T> {
//        void onResult(T data);
//    }
}
