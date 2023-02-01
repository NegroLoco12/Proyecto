package com.foysaltech.drawersliding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class verificacion_phone extends AppCompatActivity {
private EditText txt_uno;
private TextView txt_verificacion;
    String intenAuth,numero;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        txt_uno=findViewById(R.id.txt_uno);
        txt_verificacion=findViewById(R.id.txt_verificacion);
        mAuth=FirebaseAuth.getInstance();
        intenAuth=getIntent().getStringExtra("auth");
        numero=getIntent().getStringExtra("numero");
        txt_verificacion.setText(" +595 "+numero+"");


    }
    public void lanzar(View v){
        String codigo=txt_uno.getText().toString();
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(intenAuth,codigo);
        iniciarSesion(credential);

    }
    private void iniciarSesion(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   iniciarHome();
               }
            }
        });
    }
    private void iniciarHome() {
        Intent intent=new Intent(verificacion_phone.this, MainActivity.class);

        startActivity(intent);

    }
}