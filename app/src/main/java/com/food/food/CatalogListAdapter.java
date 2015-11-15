package com.food.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.food.food.R;

import java.util.ArrayList;

public class CatalogListAdapter extends ArrayAdapter<String>{

        private final Context context;
        private final ArrayList<String> names;

        public CatalogListAdapter(Context context, ArrayList<String> names) {
            super(context, R.layout.catalog_list_item, names);
            this.context = context;
            this.names = names;

        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            int[] dishImagesId ={
                    R.drawable.napitki,
                    R.drawable.zakuski,
                    R.drawable.lapsha,
                    R.drawable.rolly,
                    R.drawable.dobavki,
                    R.drawable.teploe,
                    R.drawable.salaty,
                    R.drawable.supy,
                    R.drawable.deserty,
                    R.drawable.sety,
                    R.drawable.sushi,
                    R.drawable.shashlyki,
                    R.drawable.pizza
            };

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(view == null) {
                view = inflater.inflate(R.layout.catalog_list_item, parent, false);
            }
            TextView txtTitle = (TextView) view.findViewById(R.id.catalog_list_item_text);
            txtTitle.setText(names.get(position));
            ImageView imageView = (ImageView) view.findViewById(R.id.catalog_list_item_image);
            imageView.setImageResource(dishImagesId[position]);
            return view;
        }
}

