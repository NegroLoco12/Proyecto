package com.foysaltech.drawersliding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class verificacion_phone extends AppCompatActivity {
    private EditText txt_1,txt_2,txt_3,txt_4,txt_5,txt_6;
    private TextView txt_verificacion;

    private  String intenAuth,nombre,apellido,numero,correo,clave;
    private  FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        txt_1=findViewById(R.id.txt_1);
        txt_2=findViewById(R.id.txt_2);
        txt_3=findViewById(R.id.txt_3);
        txt_4=findViewById(R.id.txt_4);
        txt_5=findViewById(R.id.txt_5);
        txt_6=findViewById(R.id.txt_6);
        txt_verificacion=findViewById(R.id.txt_verificacion);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        intenAuth=getIntent().getStringExtra("auth");
        nombre=getIntent().getStringExtra("nombre");
        apellido=getIntent().getStringExtra("apellido");
        numero=getIntent().getStringExtra("numero");
        correo=getIntent().getStringExtra("correo");
        clave=getIntent().getStringExtra("clave");
        txt_verificacion.setText(" +595 "+numero+"");


    }
    public void lanzar(View v){
        String codigo=txt_1.getText().toString()+txt_2.getText().toString()+txt_3.getText().toString()+txt_4.getText().toString()+txt_5.getText().toString()+txt_6.getText().toString();
        //Toast.makeText(verificacion_phone.this,codigo+"",Toast.LENGTH_LONG).show();
       PhoneAuthCredential credential= PhoneAuthProvider.getCredential(intenAuth,codigo);
       iniciarSesion(credential);

    }
    private void iniciarSesion(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   mAuth.createUserWithEmailAndPassword(correo, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               Map<String, Object> map = new HashMap<>();
                               map.put("nombre", nombre);
                               map.put("apellido", apellido);
                               map.put("telefono", "+595"+ numero);
                               map.put("correo", correo);
                               map.put("clave", clave);

                               String id = mAuth.getCurrentUser().getUid();
                               mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task2) {
                                       if (task2.isSuccessful()) {
                                           iniciarHome();
                                       }
                                   }
                               });
                           }
                       }
                   });

               }
            }
        });
    }
    private void iniciarHome() {
        Intent intent=new Intent(verificacion_phone.this, MainActivity.class);
        startActivity(intent);

    }
}