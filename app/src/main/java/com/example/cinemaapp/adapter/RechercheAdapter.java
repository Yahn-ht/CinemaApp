package com.example.cinemaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.ui.recherche.RechercheItem;

import java.util.List;

    public class RechercheAdapter extends BaseAdapter {

        private final Context context;
        private final List<RechercheItem> RechercheItems;

        public RechercheAdapter(Context context, List<RechercheItem> RechercheItems) {
            this.context = context;
            this.RechercheItems = RechercheItems;
        }

        @Override
        public int getCount() {
            return RechercheItems.size();
        }

        @Override
        public Object getItem(int position) {
            return RechercheItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.recherche_item, parent, false);
            }

            RechercheItem currentItem = RechercheItems.get(position);

            ImageView itemImage = convertView.findViewById(R.id.itemImage);
            TextView title = convertView.findViewById(R.id.title);
            TextView MovieDescription = convertView.findViewById(R.id.movieDescription);

            itemImage.setImageResource(currentItem.getImageResId());
            title.setText(currentItem.getTitle());
            MovieDescription.setText(currentItem.getDescription());

            return convertView;
        }
    }


