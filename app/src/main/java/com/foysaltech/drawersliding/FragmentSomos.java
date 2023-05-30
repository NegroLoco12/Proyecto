package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentSomos extends Fragment {


    public FragmentSomos() {

    }

    public static FragmentSomos newInstance(String param1, String param2) {
        FragmentSomos fragment = new FragmentSomos();

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
        return inflater.inflate(R.layout.fragment_somos, container, false);
    }
}