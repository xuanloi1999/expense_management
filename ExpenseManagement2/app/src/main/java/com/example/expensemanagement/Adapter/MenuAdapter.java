package com.example.expensemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensemanagement.Model.ItemMenu;
import com.example.expensemanagement.R;

import java.util.List;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ItemMenu> itemMenuList;

    public MenuAdapter(Context context, int layout, List<ItemMenu> itemMenuList) {
        this.context = context;
        this.layout = layout;
        this.itemMenuList = itemMenuList;
    }

    @Override
    public int getCount() {
        return itemMenuList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private  class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();

            viewHolder.textView = view.findViewById(R.id.itemName);
            viewHolder.imageView = view.findViewById(R.id.itemIcon);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(itemMenuList.get(i).getItemName());
        viewHolder.imageView.setImageResource(itemMenuList.get(i).getIcon());
        return view;
    }
}
