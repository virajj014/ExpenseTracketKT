package com.example.expensetrackerapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetrackerapp.databinding.ActivityMainBinding


import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private  lateinit var recyclerExpenses: RecyclerView
    private lateinit var txtTotalExpense: TextView
    private lateinit var txtTotalIncome: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        databaseHelper = DatabaseHelper.getInstance(this)
        recyclerExpenses = binding.recyclerExpenses
        recyclerExpenses.layoutManager = GridLayoutManager(this, 1)
        txtTotalExpense = binding.txtTotalExpense
        txtTotalIncome = binding.txtTotalIncome

        showExpense()

//        popup

        fabAdd = binding.fabAdd

        fabAdd.setOnClickListener {
            val dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.add_expense_lay)
            val edtTitle: EditText
            val edtAmount: EditText
            val edtRadioExpense: RadioButton
            val btnAdd: Button

            edtTitle = dialog.findViewById(R.id.edtTitle)
            edtAmount = dialog.findViewById(R.id.edtAmount)
            edtRadioExpense = dialog.findViewById(R.id.radioExpense)

            btnAdd = dialog.findViewById(R.id.btnAdd)


//            btnAdd.setOnClickListener {
//                val radioButtonValue = if (edtRadioExpense.isChecked) "Expense" else "Income"
//                println("Radio Button Value: $radioButtonValue")
//            }


            btnAdd.setOnClickListener{
                val radioButtonValue = if (edtRadioExpense.isChecked) "Expense" else "Income"
                val title = edtTitle.text.toString()
                val amountStr = edtAmount.text.toString()

if(title.isNotBlank() && amountStr.isNotBlank()){
    try {
        val amount = amountStr.toInt() // Convert the amount to an integer

        // Now you can use the 'amount' integer value to create an Expense object
        val newExpense = Expense(title, amount, radioButtonValue)

        // Assuming 'databaseHelper' is your Room database instance
        databaseHelper.expenseDao().addExpense(newExpense)
        val successMessage = "Saved"
        Toast.makeText(this@MainActivity, successMessage, Toast.LENGTH_SHORT).show()
        showExpense()
        dialog.dismiss()
    } catch (e: NumberFormatException) {
        // Handle the case where the amount couldn't be converted to an integer
        val errorMessage = "Invalid amount format"
        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
                else{
    val errorMessage = "Fields can't be empty"
    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            dialog.show()
        }



    }


    fun showExpense(){
        val arrExpense = databaseHelper.expenseDao().getExpense()
        if (arrExpense.isNotEmpty()) {
            recyclerExpenses.visibility = View.VISIBLE

            recyclerExpenses.adapter = RecyclerExpenseAdapter(this ,
            arrExpense as  ArrayList<Expense>, databaseHelper
                )

            var totalExpense = 0
            var totalIncome = 0



            for (expense in arrExpense) {
                if (expense.exptype == "Expense") {
                    totalExpense += expense.amount
                } else {
                    totalIncome += expense.amount
                }
            }

            txtTotalExpense.text = "$totalExpense"
            txtTotalIncome.text = "$totalIncome"

//                    for (expense in arrExpense) {
////            println("ID: ${expense.id}, Amount: ${expense.amount}, Expense: ${expense.amount}, Expense Type: ${expense.exptype}")
//        }
        }

        else{
            println("DB IS EMPTY")
        }

    }
}