package com.example.expensetrackerapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerExpenseAdapter extends RecyclerView.Adapter<RecyclerExpenseAdapter.ViewHolder> {
    Context context;
    ArrayList<Expense> arrExpense = new ArrayList<>(  );
    DatabaseHelper databaseHelper;

    public RecyclerExpenseAdapter(Context context, ArrayList<Expense> arrExpense, DatabaseHelper databaseHelper) {
        this.context = context;
        this.arrExpense = arrExpense;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public RecyclerExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerExpenseAdapter.ViewHolder holder, int position) {
        holder.txtTitle.setText(arrExpense.get(position).getTitle());
        int amountValue = arrExpense.get(position).getAmount();
        String amountString = String.valueOf(amountValue);
        holder.txtAmount.setText(amountString);
        String expType = arrExpense.get(position).getExptype();

        if(expType.equals("Expense")){
            holder.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.red)); // Change to your desired color resource
        }
        else{
            holder.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.green)); // Change to your desired color resource

        }

        holder.llrow.setOnLongClickListener(new View.OnLongClickListener( ) {
            @Override
            public boolean onLongClick(View view) {
                deleteItem(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrExpense.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtAmount;
        LinearLayout llrow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAmount= itemView.findViewById(R.id.txtAmount);
            llrow = itemView.findViewById(R.id.llRow);
        }
    }


    public void deleteItem(int pos){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Expense expenseToDelete = arrExpense.get(pos); // Get the expense object at the specified position
                        databaseHelper.expenseDao().deleteNote(expenseToDelete);

                        ((MainActivity) context).showExpense();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}
