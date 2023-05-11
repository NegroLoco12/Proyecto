package com.foysaltech.drawersliding;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener  {
    private CircleImageView img;

    String aa;
    private DatabaseReference mDatabase;
    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_CART = 2;
    private static final int POS_SOMOS = 3;
    private static final int POS_LOGOUT = 4;
    private static final int POS_LOGOUT1 = 5;
    private static final int POS_LOGOUT2 = 6;
    private List<User> elements;
    User users=new User();
    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

mDatabase= FirebaseDatabase.getInstance().getReference();

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

                createItemFor(6)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.lista);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
        ///////////////////////////////////////////////////////////////////////////////
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name=user.getEmail();
          DatabaseReference correo = mDatabase.child("Usuarios");
        Query nombre = correo.orderByChild("correo").equalTo(name);

        nombre.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    users=dataSnapshot.getValue(User.class);
                    // productos.setKey(dataSnapshot.getKey());
                   // elements.add(users); // try {
                    //    String a =     datos.getValue().toString();
                      //  JSONObject obj = new JSONObject(a);
                      //  aa=obj.getString("nombre");
                  //    Toast.makeText(MainActivity.this,users.getNombre()+"",Toast.LENGTH_LONG).show();
                        DrawerAdapter adapter2 = new DrawerAdapter(Arrays.asList(createItemFor2(7,users.getNombre())));

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
        if (position == POS_SOMOS) {
            Fragment selectedScreen = new CuentaFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();
        }
        if (position == POS_ACCOUNT) {
            BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(
                    MainActivity.this, R.style.BottonSheetDialogTheme
            );
            View bottomSheetView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_botton_sheet,(LinearLayout)findViewById(R.id.bottomShetContainer));
bottomSheetView.findViewById(R.id.btnUbi).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        bottomSheetDialog.dismiss();
        Fragment selectedScreen = new MapaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();

    }
});
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        }
        if (position == POS_LOGOUT2) {

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
}