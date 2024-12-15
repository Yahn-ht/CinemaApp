package com.example.cinemaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.ui.snack.SnackItem;

import java.util.List;

public class SnackAdapter extends BaseAdapter {

    private final Context context;
    private final List<SnackItem> snackItems;
    private  String category = null;

    public SnackAdapter(Context context, List<SnackItem> snackItems,String category) {
        this.context = context;
        this.snackItems = snackItems;
        this.category = category;
    }

    @Override
    public int getCount() {
        return snackItems.size();
    }

    @Override
    public Object getItem(int position) {
        return snackItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        SnackItem currentItem = snackItems.get(position);

        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        TextView itemTitle = convertView.findViewById(R.id.itemTitle);
        TextView itemPrice = convertView.findViewById(R.id.itemPrice);

        itemImage.setImageResource(currentItem.getImageResId());
        itemTitle.setText(currentItem.getTitle());
        itemPrice.setText(currentItem.getPrice());

        return convertView;
    }
}
