package com.food.food;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.food.fragments.DescriptionFragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadingRecyclerViewAdapter extends RecyclerView.Adapter<LoadingRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Dish> cards;
    private Context context;
    private AQuery aQuery;

    public  LoadingRecyclerViewAdapter(Context context,  ArrayList<Dish> cards){
        super();
        this.context = context;
        this.cards = cards;
        aQuery = new AQuery(context);
    }

    @Override
    public LoadingRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(LoadingRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        Dish cardView = cards.get(i);
        viewHolder.offerId = cardView.getOfferId();
        aQuery.id(viewHolder.name).text(cardView.getName());
        aQuery.id(viewHolder.price).text(cardView.getPrice());
        aQuery.id(viewHolder.weight).text(cardView.getTotalDescription().get("Вес"));
        if (TextUtils.equals(cardView.getUlrPicture(), ""))
            aQuery.id(viewHolder.image).gone();
            else{
                aQuery.id(viewHolder.image).visible().image(cardView.getUlrPicture(), true, true, 640, 0, null, AQuery.FADE_IN, AQuery.RATIO_PRESERVE);
             }
        //Picasso.with(context).load(cardView.getUlrPicture()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        if(cards != null) {
            return cards.size();
        } else {
            return 0;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView price;
        private TextView weight;
        private ImageView image;
        private Integer offerId;

        public ViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.card_name);
            name.setTextColor(context.getResources().getColor(R.color.blue));
            price = (TextView)view.findViewById(R.id.card_price);
            price.setTextColor(context.getResources().getColor(R.color.blue));
            weight = (TextView)view.findViewById(R.id.card_weight);
            weight.setTextColor(context.getResources().getColor(R.color.blue));
            image = (ImageView)view.findViewById(R.id.card_image);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionFragment df = new DescriptionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("description", findDish(offerId, cards));
                    df.setArguments(bundle);
                    ((Greeting) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.catalog, df)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    public static Dish findDish(Integer offerId, ArrayList<Dish> dishes){
        for(Dish temp : dishes){
            if(temp.getOfferId().equals(offerId)){
                return temp;
            } else {
                continue;
            }
        }
        return null;
    }

    public AQuery getaQuery() {
        return aQuery;
    }
}
