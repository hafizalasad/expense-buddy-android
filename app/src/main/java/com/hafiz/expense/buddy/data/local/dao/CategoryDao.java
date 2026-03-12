package com.hafiz.expense.buddy.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hafiz.expense.buddy.data.local.entity.CategoryEntity;
import com.hafiz.expense.buddy.data.local.entity.TransactionEntity;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  void insert(CategoryEntity entity);

    @Query("SELECT * FROM category ORDER BY id DESC")
    LiveData<List<CategoryEntity>> getAll();
}
