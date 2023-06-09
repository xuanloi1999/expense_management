package com.example.expensemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensemanagement.Model.ItemMenu;
import com.example.expensemanagement.Model.Transaction;
import com.example.expensemanagement.R;

import java.util.List;

public class TransactionAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Transaction> transactionList;

    public TransactionAdapter(Context context, int layout, List<Transaction> transactionList) {
        this.context = context;
        this.layout = layout;
        this.transactionList = transactionList;
    }

    @Override
    public int getCount() {
        return transactionList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class TransactionHolder{
        TextView txtCategory;
        TextView txtExpense;
        ImageView imgIcon;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TransactionHolder viewHolder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new TransactionHolder();

            viewHolder.imgIcon = view.findViewById(R.id.imgIcon);
            viewHolder.txtCategory = view.findViewById(R.id.txtCategory);
            viewHolder.txtExpense = view.findViewById(R.id.txtExpense);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (TransactionHolder) view.getTag();
        }

        viewHolder.imgIcon.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.txtExpense.setText(transactionList.get(i).getExpense() + "");
        viewHolder.txtCategory.setText(transactionList.get(i).getCategory());
        return view;
    }
}
