package com.example.anton.assignment1;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Anton on 2017-09-13.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private ArrayList<Transaction> transactions;

    public TransactionAdapter(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rv;
        TextView tvAmount, tvType, tvDate, tvCategory, tvId;
        ImageView imIcon;


        public ViewHolder(View view){
            super(view);
            rv = (RecyclerView) view.findViewById(R.id.rv);
            tvAmount = (TextView) view.findViewById(R.id.tvAmount);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvCategory = (TextView) view.findViewById(R.id.tvCategory);
            tvId = (TextView) view.findViewById(R.id.tvIdentification);
            imIcon = (ImageView) view.findViewById(R.id.imIcon);

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final ViewHolder vh = viewHolder;
        viewHolder.tvAmount.setText(String.valueOf(transactions.get(position).getAmount()) + " kr");
        viewHolder.tvType.setText(transactions.get(position).getTitle());
        viewHolder.tvCategory.setText(transactions.get(position).getCategory());
        viewHolder.tvDate.setText(transactions.get(position).getDate());
        viewHolder.imIcon.setImageResource(setIcon(transactions.get(position).getCategory()));
        viewHolder.tvId.setText(String.valueOf(transactions.get(position).getId()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("TransactionAdapter", vh.tvId.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public int setIcon(String category){
        switch(category){
            case "Other":
                return R.drawable.other;
            case "Salary":
                return R.drawable.salary;
            case "Accommodation":
                return R.drawable.accommodation;
            case "Travel":
                return R.drawable.travel;
            case "Leisure":
                return R.drawable.leisure;
            case "Food":
                return R.drawable.food;
        }
        return 0;
    }




}
