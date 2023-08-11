package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentHistorial extends Fragment {


    public FragmentHistorial() {
        // Required empty public constructor
    }

    public static FragmentHistorial newInstance(String param1, String param2) {
        FragmentHistorial fragment = new FragmentHistorial();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_historial, container, false);
    }
}