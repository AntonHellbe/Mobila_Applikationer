package com.example.anton.assignment1;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-13.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private ArrayList<Transaction> transactions;

    public TransactionAdapter(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView tvAmount, tvType, tvDate, tvCategory, tvId;

        public ViewHolder(View view){
            super(view);
            cv = (CardView) view.findViewById(R.id.cv);
            tvAmount = (TextView) view.findViewById(R.id.tvAmount);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvCategory = (TextView) view.findViewById(R.id.tvCategory);
            tvId = (TextView) view.findViewById(R.id.tvId);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvAmount.setText(String.valueOf(transactions.get(position).getAmount()) + " kr");
        viewHolder.tvType.setText(transactions.get(position).getTitle());
        viewHolder.tvCategory.setText(transactions.get(position).getCategory());
        viewHolder.tvDate.setText(transactions.get(position).getDate());
        viewHolder.tvId.setText(Integer.toString(transactions.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
