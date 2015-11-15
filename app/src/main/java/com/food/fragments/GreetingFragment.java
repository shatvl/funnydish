package com.food.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.food.food.R;

import static com.food.food.R.color.blue;

public class GreetingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewCatalog = inflater.inflate(R.layout.greeting_fragment, container, false);

        return viewCatalog;

    }
}
