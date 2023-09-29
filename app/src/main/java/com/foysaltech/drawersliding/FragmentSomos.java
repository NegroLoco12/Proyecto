package com.foysaltech.drawersliding;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class FragmentSomos extends Fragment {

    ImageView face,instagram;
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
      View view =inflater.inflate(R.layout.fragment_somos, container, false);

    face=view.findViewById(R.id.face);
        instagram=view.findViewById(R.id.instagram);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse("https://www.instagram.com/prodiser_saic/?hl=es");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        face=view.findViewById(R.id.face);

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse("https://www.facebook.com/ProdiserSaic/photos?locale=es_LA");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse("https://www.instagram.com/prodiser_saic/?hl=es");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        return view;
    }
}