package com.foysaltech.drawersliding;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;


public class MapaFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    public static final int REQUEST_CODE = 1;
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    Marker marker;
    private  int iconoo;
    private TextView txt_paso1,txt_paso2, ViewPaso1,ViewPaso2;
    private EditText txt_calle1, txt_calle2, txt_referencia,txt_nro_casa,txt_nombre_direccion;
    private LinearLayout seccion1,seccion2;
    private RadioButton radio0,radio1,radio2,radio3;
    private Button btn_siguiente;
    double latitud ;
    String KeyUbi;
    double longitu ;
    double lat, longitude;
    private LocationManager locationManager;
    private TextInputLayout  view_calle1, view_calle2, view_referencia, view_nro_casa, view_nombre;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ImageView imageView2;
    public MapaFragment() {

    }

    public static MapaFragment newInstance(String param1, String param2) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_mapa, container, false);
        txt_paso1=view.findViewById(R.id.txt_paso1);
        txt_paso2=view.findViewById(R.id.txt_paso2);
        ViewPaso1=view.findViewById(R.id.ViewPaso1);
        ViewPaso2=view.findViewById(R.id.ViewPaso2);

        txt_paso2=view.findViewById(R.id.txt_paso2);
        seccion1=view.findViewById(R.id.seccion1);
        seccion2=view.findViewById(R.id.seccion2);
        btn_siguiente=view.findViewById(R.id.btn_1);

        txt_calle1=view.findViewById(R.id.txt_calle1);
        txt_calle2=view.findViewById(R.id.txt_calle2);
        txt_nro_casa=view.findViewById(R.id.txt_nro_casa);
        txt_referencia=view.findViewById(R.id.txt_referencia);
        txt_nombre_direccion=view.findViewById(R.id.txt_nombre_direccion);

        view_calle1=view.findViewById(R.id.view_calle1);
        view_calle2 =view.findViewById(R.id.view_calle2);
        view_referencia=view.findViewById(R.id.view_referencia);
        view_nro_casa=view.findViewById(R.id.view_nro_casa);
        view_nombre=view.findViewById(R.id.view_nombre);
        imageView2=view.findViewById(R.id.imageView22);

        radio0=view.findViewById(R.id.radio0);
        radio1=view.findViewById(R.id.radio1);
        radio2=view.findViewById(R.id.radio2);
        radio3=view.findViewById(R.id.radio3);


        String data =getArguments().getString("dato");
       if(data.equals("Editar")){
           KeyUbi=getArguments().getString("key");
           txt_calle1.setText(getArguments().getString("calle1"));
           txt_calle2.setText(getArguments().getString("calle2"));
           txt_referencia.setText(getArguments().getString("referencia"));
           txt_nombre_direccion.setText(getArguments().getString("nombre"));
           txt_nro_casa.setText(getArguments().getString("nro_casa"));
           latitud=getArguments().getDouble("latitud");
           longitu=getArguments().getDouble("longitud");
           iconoo=getArguments().getInt("icono");
        if (iconoo==0){
        radio0.setChecked(true);
        }
           if (iconoo==1){
               radio1.setChecked(true);
           }
           if (iconoo==2){
               radio2.setChecked(true);
           }
           if (iconoo==3){
               radio3.setChecked(true);
           }
           view.findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {editar_ubi();
               }
           });
       } else if (data.equals("Nuevo")) {
           latitud = Ubicaciones.guardado.get(0).getLatitudActual();
           longitu =Ubicaciones.guardado.get(0).getLongitulActual();

           view.findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {guardar_ubi();
               }
           });
       }


        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAuth.setLanguageCode("es");

        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewPaso1.setTextColor(Color.parseColor("#c2c2c2"));
                txt_paso1.setTextColor(Color.parseColor("#c2c2c2"));
                txt_paso2.setBackgroundResource(0);
                seccion2.setVisibility(View.VISIBLE);
                seccion1.setVisibility(View.GONE);
                txt_paso2.setTextColor(Color.parseColor("#FF018786"));
                ViewPaso2.setTextColor(Color.parseColor("#292929"));
                imageView2.setImageResource(R.drawable.comprobado_adobe_express);

            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////
        txt_calle1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view_calle1.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        txt_calle2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view_calle2.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        txt_nro_casa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view_nro_casa.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        txt_referencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view_referencia.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        txt_nombre_direccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view_nombre.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        super.onStart();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Ubicaciones coordenadas = new Ubicaciones();
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng latLng = new LatLng(latitud,longitu);
        //   Toast.makeText(getContext(), latitud+""+longitu, Toast.LENGTH_SHORT).show();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        marker = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onMapClick(LatLng latLng) {
       // Ubicaciones ubi=new Ubicaciones();
       // ubi.eliminarUbi();
       // double lat=latLng.latitude;
      //  double longi=latLng.longitude;
      //  ubi.setLatitudActual(lat );
      //  ubi.setLongitulActual(longi);
      //  ubi.guardarUbi(ubi);
        mMap.clear();
        latitud = latLng.latitude;
        longitu =latLng.longitude;
        LatLng latLng1 = new LatLng(latitud,longitu);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng1);
        marker = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));


    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean validar() {
        boolean retorno = true;

        String calle1, calle2,nro_casa,referencia,nombre_direccion;

        calle1 = txt_calle1.getText().toString();
        calle2 = txt_calle2.getText().toString();
        nro_casa = txt_nro_casa.getText().toString();
        referencia = txt_referencia.getText().toString();
        nombre_direccion = txt_nombre_direccion.getText().toString();

        if (calle1.isEmpty()) {
            view_calle1.setError("Favor ingrese la Calle");
            retorno = false;

        } else {
            view_calle1.setErrorEnabled(false);
        }
        if (calle2.isEmpty()) {
            view_calle2.setError("Favor ingrese la Calle Nro 2");
            retorno = false;

        } else {
            view_calle2.setErrorEnabled(false);
        }
        if (nro_casa.isEmpty()) {
            view_nro_casa.setError("Favor ingrese el Nro de Casa");
            retorno = false;

        } else {
            view_nro_casa.setErrorEnabled(false);
        }
        if (referencia.isEmpty()) {
            view_referencia.setError("Favor ingrese una Referencia");
            retorno = false;

        } else {
            view_referencia.setErrorEnabled(false);
        }
        if (nombre_direccion.isEmpty()) {
            view_nombre.setError("Favor ingrese un Nombre");
            retorno = false;

        } else {
            view_nombre.setErrorEnabled(false);
        }
        return retorno;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void guardar_ubi(){
        if(validar()){
            String calle1, calle2,nro_casa,referencia,nombre_direccion,cod_usuario;
            int icono=0;
            if(radio0.isChecked()){
                icono=0;
            } else if (radio1.isChecked()) {
                icono=1;

              } else if (radio2.isChecked()) {
                icono=2;
            } else if (radio3.isChecked()) {
                icono=3;
            }


            calle1 = txt_calle1.getText().toString();
            calle2 = txt_calle2.getText().toString();
            nro_casa = txt_nro_casa.getText().toString();
            referencia = txt_referencia.getText().toString();
            nombre_direccion = txt_nombre_direccion.getText().toString();
             cod_usuario=mAuth.getCurrentUser().getUid();

            Map<String, Object> map = new HashMap<>();
            map.put("calle1", calle1);
            map.put("calle2", calle2);
            map.put("nro_casa", nro_casa);
            map.put("referencia", referencia);
            map.put("nombre_direccion", nombre_direccion);
            map.put("latitud", latitud);
            map.put("longitud", longitu);
            map.put("cod_usuario", cod_usuario);
            map.put("icono", icono);

            mDatabase.child("Direcciones").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        MotionToast.Companion.createColorToast(getActivity(),
                                "Registrado",
                                "Registrado sin problemas!",
                                MotionToastStyle.SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(getContext(), www.sanju.motiontoast.R.font.helvetica_regular));
                                limpiar();
                    }
                }
            });
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    private void limpiar(){
       txt_calle1.setText(" ");
       txt_calle2.setText(" ");
       txt_nombre_direccion.setText(" ");
       txt_nro_casa.setText(" ");
       txt_referencia.setText(" ");
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void editar_ubi(){
        if(validar()){
            String calle1, calle2,nro_casa,referencia,nombre_direccion,cod_usuario;
          //  double latitud,longitud;
            int icono=0;
            if(radio0.isChecked()){
                icono=0;
            } else if (radio1.isChecked()) {
                icono=1;

            } else if (radio2.isChecked()) {
                icono=2;
            } else if (radio3.isChecked()) {
                icono=3;
            }
            calle1 = txt_calle1.getText().toString();
            calle2 = txt_calle2.getText().toString();
            nro_casa = txt_nro_casa.getText().toString();
            referencia = txt_referencia.getText().toString();
            nombre_direccion = txt_nombre_direccion.getText().toString();
             cod_usuario=mAuth.getCurrentUser().getUid();
            mDatabase.child("Direcciones").child(KeyUbi).child("calle1").setValue(calle1);
            mDatabase.child("Direcciones").child(KeyUbi).child("calle2").setValue(calle2);
            mDatabase.child("Direcciones").child(KeyUbi).child("latitud").setValue(latitud);
           mDatabase.child("Direcciones").child(KeyUbi).child("longitud").setValue(longitu);
           mDatabase.child("Direcciones").child(KeyUbi).child("nombre_direccion").setValue(nombre_direccion);
          mDatabase.child("Direcciones").child(KeyUbi).child("nro_casa").setValue(nro_casa);
          mDatabase.child("Direcciones").child(KeyUbi).child("referencia").setValue(referencia);
            mDatabase.child("Direcciones").child(KeyUbi).child("icono").setValue(icono);



                        MotionToast.Companion.createColorToast(getActivity(),
                                "SUCCESS",
                                "Editado sin problemas!",
                                MotionToastStyle.SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(getContext(), www.sanju.motiontoast.R.font.helvetica_regular));

        }
    }

}
