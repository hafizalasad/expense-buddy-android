package com.hafiz.expense.buddy.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction")
public class TransactionEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
