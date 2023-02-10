package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;


public class CuentaFragment extends Fragment {

    private TextInputLayout view_mail,view_telefono, view_nombre, view_apellido;
    private FirebaseAuth mAuth;
    private EditText txt_nombre, txt_apellido,txt_mail, txt_telefono;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cuenta, container, false);
        view_nombre=view.findViewById(R.id.view_nombre);
        view_apellido=view.findViewById(R.id.view_apellido);
        view_telefono=view.findViewById(R.id.view_telefono);
        view_mail=view.findViewById(R.id.view_mail);
        txt_nombre=view.findViewById(R.id.txt_nombre);
        txt_apellido=view.findViewById(R.id.txt_apellido);
        txt_mail=(EditText)view.findViewById(R.id.txt_mail);
        txt_telefono=view.findViewById(R.id.txt_telefono);
        //////////////////////////////////////////////////////

        cargar();
        return  view;

    }
    public void cargar(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String mail=user.getEmail();
        DatabaseReference correo = mDatabase.child("Usuarios");
        Query nombre = correo.orderByChild("correo").equalTo(mail);

        nombre.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot snapshot) {

               for (DataSnapshot datos : snapshot.getChildren()) {
                    try {
                       String a =     datos.getValue().toString();
                        JSONObject obj = new JSONObject(a);
                       txt_nombre.setText(obj.getString("nombre"));
                        txt_apellido.setText(obj.getString("apellido"));
                        txt_telefono.setText(obj.getString("telefono"));
                        txt_mail.setText(obj.getString("correo"));
                    } catch (JSONException e) {

                    }




                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }
}