package com.example.expensetrackerapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Query("select * from expense")
    List<Expense> getExpense();

    @Insert
    void addExpense(Expense expense);

    @Delete
    void deleteNote(Expense expense);
}
