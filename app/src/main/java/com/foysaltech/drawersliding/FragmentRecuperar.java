package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRecuperar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRecuperar extends Fragment {



    public FragmentRecuperar() {

    }

    public static FragmentRecuperar newInstance(String param1, String param2) {
        FragmentRecuperar fragment = new FragmentRecuperar();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recuperar, container, false);

        return view;
    }
}