package com.hafiz.expense.buddy.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.hafiz.expense.buddy.data.local.entity.CategoryEntity;
import com.hafiz.expense.buddy.data.local.entity.TransactionEntity;

@Dao
public abstract class CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CategoryEntity entity);
}
