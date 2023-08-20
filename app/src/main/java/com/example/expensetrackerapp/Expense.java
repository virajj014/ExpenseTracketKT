package com.example.expensetrackerapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "exptype")
    private String exptype;

    public Expense(int id, String title, int amount , String exptype) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.exptype = exptype;
    }


    @Ignore
    public Expense(String title, int amount , String exptype) {
        this.title = title;
        this.amount = amount;
        this.exptype = exptype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getExptype() {
        return exptype;
    }

    public void setExptype(String exptype) {
        this.exptype = exptype;
    }
}

