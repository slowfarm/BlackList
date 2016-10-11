package com.bis_idea.blacklist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FilterFragment extends Fragment {


    public static FilterFragment newInstance() {
        FilterFragment fragmentFirst = new FilterFragment();
        Bundle args = new Bundle();
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_layout, container, false);

        final Spinner countrySpinner = (Spinner)view.findViewById(R.id.countrySpinner);
        final Spinner citySpinner = (Spinner)view.findViewById(R.id.citySpinner);
        setAdapter(countrySpinner, view, R.array.country);
        setAdapter(citySpinner,view, R.array.city);
        return view;
    }

    public void setAdapter(Spinner spinner, View view, int array) {
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(view.getContext(), array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}