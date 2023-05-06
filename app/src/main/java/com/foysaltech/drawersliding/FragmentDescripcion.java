package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDescripcion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDescripcion extends Fragment {

    public FragmentDescripcion() {
        // Required empty public constructor
    }

    public static FragmentDescripcion newInstance(String param1, String param2) {
        FragmentDescripcion fragment = new FragmentDescripcion();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_descripcion, container, false);
    }
}