package com.hafiz.expense.buddy.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hafiz.expense.buddy.data.local.entity.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(TransactionEntity transaction);

    @Query("SELECT * FROM user_transaction ORDER BY id DESC")
    LiveData<List<TransactionEntity>> getAllTransactions();
}
