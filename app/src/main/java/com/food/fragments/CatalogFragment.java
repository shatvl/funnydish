package com.food.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.food.CatalogListAdapter;
import com.food.food.CatalogItems;
import com.food.food.Greeting;
import com.food.food.R;

import java.util.ArrayList;

public class CatalogFragment extends Fragment{

    private ListView listView;
    private ArrayList<CatalogItems> names;
    private DishesCatalog dishesCatalog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewCatalog = inflater.inflate(R.layout.catalog_fragment, container, false);
        if(getArguments()!= null) {
            names = getArguments().getParcelableArrayList("catalogNames");
        }
        ArrayList<String> n = new ArrayList<>();
        for(CatalogItems temp : names){
            n.add(temp.getName());
        }

        listView = (ListView) viewCatalog.findViewById(R.id.list);
        CatalogListAdapter adapter = new CatalogListAdapter(getContext(), n);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer idName = names.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", idName);
                dishesCatalog = new DishesCatalog();
                dishesCatalog.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.catalog, dishesCatalog)
                        .addToBackStack(null)
                        .commit();

                //Toast.makeText(getActivity(), Integer.toString(idName), Toast.LENGTH_SHORT).show();
            }
        });
        return viewCatalog;
    }

    public DishesCatalog getDishesCatalog() {
        return dishesCatalog;
    }
}
