package com.food.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.androidquery.AQuery;
import com.food.food.Dish;
import com.food.food.Greeting;
import com.food.food.LoadingRecyclerViewAdapter;
import com.food.food.R;

import java.sql.Array;
import java.util.*;

public class DescriptionFragment extends Fragment {

    private Dish dish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewCatalog = inflater.inflate(R.layout.dish_description, container, false);

        if(getArguments() != null) {
            dish = getArguments().getParcelable("description");
        }

        ImageView picture = (ImageView) viewCatalog.findViewById(R.id.image_descr);

        LoadingRecyclerViewAdapter adapter = ((Greeting) getActivity()).getCatalogFragment().getDishesCatalog().getAdapter();
        if (TextUtils.equals(dish.getUlrPicture(), ""))
            adapter.getaQuery().id(picture).gone();
        else{
            adapter.getaQuery().id(picture).visible().image(dish.getUlrPicture(), true, true, 640, 0, null, AQuery.FADE_IN, AQuery.RATIO_PRESERVE);
        }

        TreeSet<String> keySet = new TreeSet<>(dish.getTotalDescription().keySet());

        ArrayList<String> descriptions = new ArrayList<>();
        Iterator<String> iterator = keySet.iterator();
        for(int i = 0; i < keySet.size(); i++){
            descriptions.add(iterator.next());
        }

        TextView name = (TextView) viewCatalog.findViewById(R.id.name_descr);
        name.setTextColor(getResources().getColor(R.color.blue));
        name.setTextSize(30);
        name.setText(dish.getName());

        TextView price = (TextView) viewCatalog.findViewById(R.id.price_descr);
        price.setTextColor(getResources().getColor(R.color.blue));
        price.setTextSize(30);
        price.setText(dish.getPrice() + " руб");

        TextView description = (TextView) viewCatalog.findViewById(R.id.description_descr);
        description.setTextColor(getResources().getColor(R.color.blue));
        description.setTextSize(15);

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < keySet.size(); i++) {
            sb.append(descriptions.get(i) + ": " + dish.getTotalDescription().get(descriptions.get(i)) + "\n");
        }

        description.setText(sb.toString());
        return viewCatalog;
    }
}
