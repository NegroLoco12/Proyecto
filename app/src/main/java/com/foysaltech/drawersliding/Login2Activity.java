package com.foysaltech.drawersliding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class Login2Activity extends AppCompatActivity {
    private EditText txt_mail;
    private TextInputLayout TextView_Email;
    boolean retorno;
    private DatabaseReference mDatabase;
    String nombre_usu,apellido,clave,a ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login2);
        txt_mail = (EditText) findViewById(R.id.txt_mail);
        TextView_Email = (TextInputLayout) findViewById(R.id.TextView_Email);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        findViewById(R.id.singIn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmar();
            }
        });
        txt_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView_Email.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void buttonSendEmail() {

        try {
            String stringSenderEmail = "gh1smati@gmail.com";
            String stringReceiverEmail = txt_mail.getText().toString();
            String stringPasswordSenderEmail = "ahnlwqqrgivegzlh";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Prueba");
            mimeMessage.setText("Hola "+nombre_usu+" "+apellido+"  tu contraseña es: "+clave);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {

                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean validar() {
        boolean retorno = true;
        String correo;
        correo = txt_mail.getText().toString();

        if (correo.isEmpty()) {
            TextView_Email.setError("Favor ingrese su correo");
            retorno = false;

        } else {
            TextView_Email.setErrorEnabled(false);
        }

        return retorno;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void consultar() {
        try {


            ocultar();

            String email = txt_mail.getText().toString();

            DatabaseReference correo = mDatabase.child("Usuarios");
            Query nombre = correo.orderByChild("correo").equalTo(email);

            nombre.addValueEventListener(new ValueEventListener() {

                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot datos : snapshot.getChildren()) {
                        try {
                            a =     datos.getValue().toString();
                            JSONObject obj = new JSONObject(a);
                            nombre_usu=obj.getString("nombre");
                            apellido=obj.getString("apellido");
                            clave=obj.getString("clave");
                            buttonSendEmail();
                                //txt_mail.setText(obj.getString("clave")+"");

                            MotionToast.Companion.createColorToast(Login2Activity.this,//Toast Personalizado
                                    "Éxito!",
                                    "La Clave fue enviada en el Correo",
                                    MotionToastStyle.SUCCESS,
                                    MotionToast.GRAVITY_BOTTOM,
                                    MotionToast.LONG_DURATION,
                                    ResourcesCompat.getFont(Login2Activity.this, www.sanju.motiontoast.R.font.helvetica_regular));

                        } catch (JSONException e) {
                           Toast.makeText(Login2Activity.this,e+"",Toast.LENGTH_LONG).show();
                        }




                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }catch (Exception e){
            Toast.makeText(Login2Activity.this,a+"",Toast.LENGTH_LONG).show();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////

    private void confirmar() {
        consultar();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void ocultar() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imn = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imn.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}