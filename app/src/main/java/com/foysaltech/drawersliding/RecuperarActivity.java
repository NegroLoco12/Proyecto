package com.foysaltech.drawersliding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class RecuperarActivity extends AppCompatActivity {
    private EditText txt_correo;
    private TextInputLayout view_correito ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        txt_correo=findViewById(R.id.correito);
        view_correito=findViewById(R.id.recuperar_mail);

    }
    public void recuperar(){
        String mail=txt_correo.getText().toString().trim();
        if(mail.isEmpty()||  !Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            view_correito.setError("Correo Invalido");
            return;
        }
        sendEmail(mail);
    }
    public  void sendEmail(String mail){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String a=mail;
        firebaseAuth.sendPasswordResetEmail(a).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    MotionToast.Companion.createColorToast(RecuperarActivity.this,//Toast Personalizado
                            "Advertencia",
                            "Correo Electrònico Enviado!",
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(RecuperarActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));

                }
            }
        });
    }
}