
package com.foysaltech.drawersliding;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener  {
    private CircleImageView img;
    private TextView txt_nombre_usu_ubi;
    private  LinearLayout linear_menu;
    public static final int REQUEST_CODE = 1;
    String aa;
    BottomSheetDialog bottomSheetDialog;
    private DatabaseReference mDatabase;
    private ImageView botton_menu_ubi;
    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_CART = 2;
    private static final int POS_SOMOS = 3;
    private static final int POS_LOGOUT = 4;
    private static final int POS_LOGOUT1 = 5;
    private static final int POS_LOGOUT2 = 6;
    private List<User> elements;
    User users=new User();
   private AlertDialog dialog;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;
    private RecyclerView contenedorUbi,contenedorContri;
    private RecyclerView list ;
    Ubicaciones ubicaciones=new Ubicaciones();
    Contribuyentes contribuyentes=new Contribuyentes();
    private FirebaseAuth mAuth;
    private    AdapterUbicaciones listUbi;
    private    AdapterContribuyente listContri;
    private List<Ubicaciones> elementsUbi;
    private List<Contribuyentes> elementsContri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri redirectUri = appLinkIntent.getData();


            ObtenerCoordendasActual();
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mAuth.setLanguageCode("es");
            mDatabase = FirebaseDatabase.getInstance().getReference();

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            slidingRootNav = new SlidingRootNavBuilder(this)
                    .withToolbarMenuToggle(toolbar)
                    .withMenuOpened(false)
                    .withContentClickableWhenMenuOpened(false)
                    .withSavedState(savedInstanceState)
                    .withMenuLayout(R.layout.menu_left_drawer)
                    .inject();

            screenIcons = loadScreenIcons();
            screenTitles = loadScreenTitles();

            DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                    createItemFor(0).setChecked(true),
                    createItemFor(1),

                    createItemFor(2),
                    createItemFor(3),
                    createItemFor(4),
                    createItemFor(5),
                    createItemFor(6),

                    createItemFor(7)));
            adapter.setListener(this);

            list = findViewById(R.id.lista);
            list.setNestedScrollingEnabled(false);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(adapter);
        if (redirectUri != null) {
            captureOrder(redirectUri.getQueryParameter("token"));
//            Bundle bundle = new Bundle();
//            bundle.putString("token", redirectUri.getQueryParameter("token"));
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().add(R.id.container, new FragmentPago()).addToBackStack(null).commit();
//            getSupportFragmentManager().setFragmentResult("keytoquen", bundle);

       //     Toast.makeText(MainActivity.this, redirectUri.getQueryParameter("token")+ "", Toast.LENGTH_LONG).show();
        }
            adapter.setSelected(POS_DASHBOARD);

            ///////////////////////////////////////////////////////////////////////////////
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String name = user.getEmail();
            DatabaseReference correo = mDatabase.child("Usuarios");
            Query nombre = correo.orderByChild("correo").equalTo(name);

            nombre.addValueEventListener(new ValueEventListener() {

                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        users = dataSnapshot.getValue(User.class);
                        // productos.setKey(dataSnapshot.getKey());
                        // elements.add(users); // try {
                        //    String a =     datos.getValue().toString();
                        //  JSONObject obj = new JSONObject(a);
                        //  aa=obj.getString("nombre");
                        //    Toast.makeText(MainActivity.this,users.getNombre()+"",Toast.LENGTH_LONG).show();
                        DrawerAdapter adapter2 = new DrawerAdapter(Arrays.asList(createItemFor2(8, users.getNombre())));

                        RecyclerView list2 = findViewById(R.id.list2);
                        list2.setNestedScrollingEnabled(false);
                        list2.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        list2.setAdapter(adapter2);
                        //   } catch (JSONException e) {

                        //  }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });


/////////////////////////////////////////////////////////////////////////////////////

    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_DASHBOARD) {
            Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();
        }
        if (position == 6) {
            Fragment selectedScreen = new FragmentSomos();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();
        }
        if (position == 2) {
            Fragment selectedScreen = new FragmentHistorial();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();
        }
        if (position == 5) {
            if (Carritos.pedido.size()>0) {
                Fragment selectedScreen = new FragmentCarrito();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();
            }else{
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                View view=getLayoutInflater().inflate(R.layout.carrito_vacio,null);
                builder.setView(view);
                builder.setCancelable(true);
                AlertDialog  alertDialog= builder.create();
                alertDialog.show();
            }
        }
        if (position == 4) {
            Fragment selectedScreen = new CuentaFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (position == POS_ACCOUNT) {

            bottomSheetDialog=new BottomSheetDialog(
                    MainActivity.this, R.style.BottonSheetDialogTheme
            );
            View bottomSheetView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_botton_sheet,(LinearLayout)findViewById(R.id.bottomShetContainer));
            contenedorUbi=bottomSheetView.findViewById(R.id.contenedorUbicaciones);
            txt_nombre_usu_ubi= bottomSheetView.findViewById(R.id.txt_nombre_usu_ubi);
            txt_nombre_usu_ubi.setText("Hola "+users.getNombre()+" !");

            bottomSheetView.findViewById(R.id.btnUbi).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                    if (Ubicaciones.guardado.size() == 0) {

                    } else {

                          MapaFragment fragment1=new MapaFragment();
                           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragment1).addToBackStack(null).commit();

                         Bundle data = new Bundle();
                          data.putString("dato", "Nuevo");
                        fragment1.setArguments(data);;
                        bottomSheetDialog.dismiss();
                    }
                }
            });
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
            cargarUbi();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (position == 3) {

            bottomSheetDialog=new BottomSheetDialog(
                    MainActivity.this, R.style.BottonSheetDialogTheme
            );
            View bottomSheetView2= LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_datos_fac,(LinearLayout)findViewById(R.id.bottomDatosContainer));
            contenedorContri=bottomSheetView2.findViewById(R.id.contenedorContribuyentes);


            bottomSheetView2.findViewById(R.id.btnContri).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                    View view=getLayoutInflater().inflate(R.layout.custom_dialog_contribuyente,null);
                    builder.setView(view);
                    builder.setCancelable(true);
                    AlertDialog  alertDialog= builder.create();
                    alertDialog.show();
                    view.findViewById(R.id.btn_guardar_contri).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String cod_usuario=mAuth.getCurrentUser().getUid();
                            EditText txt_ruc=view.findViewById(R.id.txt_ruc);
                            EditText txt_razonsocial=view.findViewById(R.id.txt_razonsocial);
                            String nombre=txt_razonsocial.getText().toString();
                            String ruc=txt_ruc.getText().toString();
                            Map<String, Object> map = new HashMap<>();
                            map.put("razon_social", nombre);
                            map.put("documento", ruc);
                            map.put("cod_usuario", cod_usuario);
                            String id = mAuth.getCurrentUser().getUid();
                            mDatabase.child("Contribuyentes").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if (task2.isSuccessful()) {
                                        MotionToast.Companion.createColorToast(MainActivity.this,//Toast Personalizado
                                                "Registrado",
                                                "Registrado sin problemas!",
                                                MotionToastStyle.SUCCESS,
                                                MotionToast.GRAVITY_BOTTOM,
                                                MotionToast.LONG_DURATION,
                                                ResourcesCompat.getFont(MainActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));
                                        alertDialog.dismiss();
                                    }
                                }
                            });
                        }
                    });

                    // alertDialog.setCanceledOnTouchOutside(false);
                   // bottomSheetDialog.dismiss();

                }
            });
            bottomSheetDialog.setContentView(bottomSheetView2);
            bottomSheetDialog.show();
          cargarContribuyentes();
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (position == 7) {

            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            finish();
        }

        slidingRootNav.closeMenu();


    }

    private void showFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position],"")
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }
    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor2(int position, String Usuario) {


        return new SimpleItem(screenIcons[position], screenTitles[position],Usuario)
                .withIconTint(color(R.color.colorAccent))
                .withTextTint(color(R.color.colorAccent))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }


    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }


    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


    @Override
    public void onBackPressed()
    {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void ObtenerCoordendasActual() {


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {

            getCoordenada();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCoordenada();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
    private void getCoordenada() {

        try {

            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            if (ActivityCompat.checkSelfPermission(MainActivity.this
                    , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                    super.onLocationAvailability(locationAvailability);
                }

                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
                    if (locationResult != null && locationResult.getLocations().size() > 0) {
                        int latestLocationIndex = locationResult.getLocations().size() - 1;
                        Ubicaciones ubi=new Ubicaciones();
                        double lat=locationResult.getLocations().get(latestLocationIndex).getLatitude();
                        double longi=locationResult.getLocations().get(latestLocationIndex).getLongitude();
                        ubi.setLatitudActual(lat );
                        ubi.setLongitulActual(longi);
                        ubi.guardarUbi(ubi);
                        ubi.guardado.get(0).getLatitudActual();
                    }
                }
            }, Looper.myLooper());
        } catch (Exception ex) {
            System.out.println("Error es :" + ex);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////7
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //startLocService();
                } else {
                    Toast.makeText(MainActivity.this, "Give me permissions", Toast.LENGTH_LONG).show();
                }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void cargarUbi( ){

        elementsUbi = new ArrayList<>();
        listUbi = new AdapterUbicaciones(this, elementsUbi, new AdapterUbicaciones.OnItemClickListener() {
            @Override
            public void onItemClick(Ubicaciones item) {
borrarUbi(item);
            }
            @Override
            public void onItemClick2(Ubicaciones item) {
EditarUbi(item);
            }
        });
        contenedorUbi.setHasFixedSize(true);
        contenedorUbi.setLayoutManager(new LinearLayoutManager(this));
        contenedorUbi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        contenedorUbi.setAdapter(listUbi);
        // Toast.makeText(getContext(),codigo+"",Toast.LENGTH_LONG).show();

        DatabaseReference correo = mDatabase.child("Direcciones");
        Query nombre = correo.orderByChild("cod_usuario").equalTo(mAuth.getCurrentUser().getUid());

        nombre.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ubicaciones=dataSnapshot.getValue(Ubicaciones.class);
                    ubicaciones.setKey(dataSnapshot.getKey());
                    elementsUbi.add(ubicaciones);

                 //   Toast.makeText(getApplicationContext(),elementsUbi+"",Toast.LENGTH_LONG).show();


                }
                listUbi.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void borrarUbi(Ubicaciones item) {

        mDatabase.child("Direcciones").child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                MotionToast.Companion.createColorToast(MainActivity.this,//Toast Personalizado
                        "Exito!",
                        "Se ha Borrado",
                        MotionToastStyle.DELETE,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(MainActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));

            }
        });
        DatabaseReference correo = mDatabase.child("Direcciones");
        Query nombre = correo.orderByChild("cod_usuario").equalTo(mAuth.getCurrentUser().getUid());

        nombre.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ubicaciones=dataSnapshot.getValue(Ubicaciones.class);
                    ubicaciones.setKey(dataSnapshot.getKey());
                    elementsUbi.add(ubicaciones);

                    //   Toast.makeText(getApplicationContext(),elementsUbi+"",Toast.LENGTH_LONG).show();


                }
                listUbi.actualizar(elementsUbi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private void EditarUbi(Ubicaciones item) {

    MapaFragment fragment1=new MapaFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.container, fragment1).addToBackStack(null).commit();
    Bundle data = new Bundle();
    data.putString("dato", "Editar");
    data.putString("key", item.getKey());
    data.putString("calle1", item.getCalle1());
    data.putString("calle2", item.getCalle2());
    data.putString("nro_casa", item.getNro_casa());
    data.putString("nombre", item.getNombre_direccion());
    data.putDouble("latitud", item.getLatitud());
    data.putDouble("longitud", item.getLongitud());
    data.putString("referencia", item.getReferencia());
    data.putInt("icono", item.getIcono());

    fragment1.setArguments(data);;
    bottomSheetDialog.dismiss();
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void cargarContribuyentes( ){


    elementsContri = new ArrayList<>();
    DatabaseReference correo = mDatabase.child("Contribuyentes");
    Query nombre = correo.orderByChild("cod_usuario").equalTo(mAuth.getCurrentUser().getUid());

    nombre.addValueEventListener(new ValueEventListener() {
        @Override

        public void onDataChange(@NonNull DataSnapshot snapshot) {
            elementsContri.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                contribuyentes=dataSnapshot.getValue(Contribuyentes.class);
                contribuyentes.setKey(dataSnapshot.getKey());
                elementsContri.add(contribuyentes);

//                 Toast.makeText(getApplicationContext(),elementsUbi.size()+"",Toast.LENGTH_LONG).show();


            }
            listContri.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }


    });
    listContri = new AdapterContribuyente(elementsContri, this, new AdapterContribuyente.OnItemClickListener() {
        @Override
        public void onItemClick(Contribuyentes item) {
            mDatabase.child("Contribuyentes").child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    MotionToast.Companion.createColorToast(MainActivity.this,//Toast Personalizado
                            "Exito!",
                            "Se ha Borrado",
                            MotionToastStyle.DELETE,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(MainActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));

                }
            });

        }
        public void onItemClick2(Contribuyentes item) {
            TextInputEditText txt_razon,txt_ruc ;
            Button btn_guardar_contri;
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

            View view=getLayoutInflater().inflate(R.layout.custom_dialog_contribuyente,null);
            builder.setView(view);
            builder.setCancelable(true);
            AlertDialog  alertDialog= builder.create();
            alertDialog.show();
         txt_razon=   view.findViewById(R.id.txt_razonsocial);
            txt_ruc=   view.findViewById(R.id.txt_ruc);
            btn_guardar_contri=   view.findViewById(R.id.btn_guardar_contri);
            txt_razon.setText(item.getRazon_social()+"");
            txt_ruc.setText(item.getDocumento()+"");
            btn_guardar_contri.setText("Modificar");
            btn_guardar_contri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.child("Contribuyentes").child(item.getKey()).child("documento").setValue(txt_ruc.getText().toString());
                    mDatabase.child("Contribuyentes").child(item.getKey()).child("razon_social").setValue(txt_razon.getText().toString());
                    MotionToast.Companion.createColorToast(MainActivity.this,//Toast Personalizado
                            "Exito!",
                            "Se ha Modificado",
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(MainActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));
                    alertDialog.dismiss();
                }
            });
        }
    });

    contenedorContri.setHasFixedSize(true);
    contenedorContri.setLayoutManager(new LinearLayoutManager(this));
    contenedorContri.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    contenedorContri.setAdapter(listContri);
    // Toast.makeText(getContext(),codigo+"",Toast.LENGTH_LONG).show();


}
//////////////////////////////////////////////////////////////////////////////////////////////////////////
void captureOrder(String orderID){
    //get the accessToken from MainActivity
    String accessToken = FragmentDetallePedido.getMyAccessToken();

    AsyncHttpClient client = new AsyncHttpClient();
    client.addHeader("Accept", "application/json");
    client.addHeader("Content-type", "application/json");
    client.addHeader("Authorization", "Bearer " + accessToken);

    client.post("https://api.sandbox.paypal.com/v2/checkout/orders/"+orderID+"/capture", new TextHttpResponseHandler() {
        @Override
        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
            Log.e("RESPONSE", responseString);
        }

        @Override
        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
            try {
                JSONObject jobj = new JSONObject(responseString);
              //  Toast.makeText(getApplicationContext(),responseString+"",Toast.LENGTH_LONG).show();
Log.e("esteeeeeeeeeeee",responseString);
                //redirect back to home page of app
                //  Fragment selectedScreen = new CenteredTextFragment() ;
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();

            } catch (JSONException e) {

            }
        }




    });
}

}