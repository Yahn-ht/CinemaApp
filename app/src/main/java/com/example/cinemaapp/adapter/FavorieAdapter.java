package com.example.cinemaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.cinemaapp.R;
import com.example.cinemaapp.ui.favorie.FavorieItem;

import java.util.List;

public class FavorieAdapter extends BaseAdapter {
    private final Context context;
    private final List<FavorieItem> FavorieItems;

    public FavorieAdapter(Context context, List<FavorieItem> FavorieItems) {
        this.context = context;
        this.FavorieItems = FavorieItems;
    }


    public int getCount() {
        return FavorieItems.size();
    }

    public Object getItem(int position) {
        return FavorieItems.get(position);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.favorie_item, parent, false);
        }

        FavorieItem currentItem = FavorieItems.get(position);

        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        ImageButton itemFavori = convertView.findViewById(R.id.itemFavori);
        TextView title = convertView.findViewById(R.id.title);
        TextView movieDescription = convertView.findViewById(R.id.movieDescription);
        TextView cinematitle = convertView.findViewById(R.id.cinematitle);

        itemImage.setImageResource(currentItem.getImageResId());
        itemFavori.setImageResource(currentItem.getImagefav());
        title.setText(currentItem.getTitle());
        movieDescription.setText(currentItem.getDescription());
        cinematitle.setText(currentItem.getCinemalocate());

        return convertView;
    }
}

