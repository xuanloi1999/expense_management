package com.example.expensemanagement.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensemanagement.Model.Category;
import com.example.expensemanagement.Model.Transaction;
import com.example.expensemanagement.R;
import android.content.res.Resources;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Category> categories;

    public CategoryAdapter(Context context, int layout, List<Category> categories) {
        this.context = context;
        this.layout = layout;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class CategoryHolder {
        ImageView imgIcon;
        TextView txtCategory;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CategoryHolder viewHolder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new CategoryHolder();

            viewHolder.imgIcon = view.findViewById(R.id.imgIcon);
            viewHolder.txtCategory = view.findViewById(R.id.txtCategory);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (CategoryHolder) view.getTag();
        }
        viewHolder.imgIcon.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.txtCategory.setText(categories.get(i).getName());
        return view;
    }
}
