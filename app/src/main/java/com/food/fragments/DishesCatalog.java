package com.food.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.food.food.Dish;
import com.food.food.Greeting;
import com.food.food.LoadingRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import com.food.food.R;


public class DishesCatalog extends Fragment{

    private RecyclerView recyclerView;
    private LoadingRecyclerViewAdapter adapter;
    private HashMap<Integer, ArrayList<Dish>> dishes;
    private Integer id;
    private GridLayoutManager grid;
    private ArrayList<Dish> dishArray;
    private int ival = 0;
    private int loadLimit = 1;
    private boolean isLoading = true;
    private int previousTotal = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View viewCatalog = inflater.inflate(R.layout.recycler_fragment, container, false);
        recyclerView = (RecyclerView) viewCatalog.findViewById(R.id.recycler_view);
        dishes = new HashMap<>();
        dishes = ((Greeting)getActivity()).getDishes();
        dishArray = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        grid = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(grid);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(getArguments() != null){
            id = getArguments().getInt("id");
        }
        loadData();
        adapter = new LoadingRecyclerViewAdapter(getActivity(), dishArray);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = grid.getItemCount();
                int lastCompletely = grid.findLastCompletelyVisibleItemPosition();
                int lastvisible = grid.findLastVisibleItemPosition();

                if (isLoading) {
                    if (totalItemCount > previousTotal) {
                        isLoading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!isLoading && ((totalItemCount - 1) == lastCompletely)) {
                    // End has been reached
                    // Do something
                    AQuery aq = adapter.getaQuery();
                    if (aq.getCachedImage(dishArray.get(lastvisible).getUlrPicture()) == null) {
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Прогрузка данных...");
                        progressDialog.show();
                        loadMoreData();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                    }
                                }, 1200);

                        isLoading = true;
                    } else {
                        loadMoreData();
                        isLoading = true;
                    }
                }

            }
        });
        return viewCatalog;
    }

    private void loadData(){
        for(int i = ival; i <= loadLimit; i++){
            if(ival < dishes.get(id).size()) {
                Dish tempdish = dishes.get(id).get(ival);
                dishArray.add(tempdish);
            }
            ival++;
        }
    }

    private void loadMoreData(){
        loadLimit = ival + 1;
        for(int i = ival; i <= loadLimit; i++){
            if(ival < dishes.get(id).size()) {
                Dish tempdish = dishes.get(id).get(ival);
                dishArray.add(tempdish);
            }
            ival++;
        }
        adapter.notifyDataSetChanged();
    }


    public LoadingRecyclerViewAdapter getAdapter() {
        return adapter;
    }
}
