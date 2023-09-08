package com.foysaltech.drawersliding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class LoginActivity extends AppCompatActivity {
    private SpinKitView spinKitView;
    private Button singIn;
    private  LinearLayout singUpLayout,logInLayout;
    private TextView singUp,logIn,txt_olvidar_clave;
    private EditText txt_nombre, txt_apellido, txt_clave,txt_mail, txt_telefono, eMail,passwords;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private String mensaje="No existe";
    private TextInputLayout TextView_Email,TextView_Password,view_mail,view_telefono, view_nombre, view_apellido, view_clave;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;



    private DatabaseReference mDatabase;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        singUpLayout = (LinearLayout) findViewById(R.id.singUpLayout);
        logInLayout = (LinearLayout) findViewById(R.id.logInLayout);
        logIn = (TextView) findViewById(R.id.logIn);
        singUp = (TextView) findViewById(R.id.singUp);
        txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        txt_apellido = (EditText) findViewById(R.id.txt_apellido);
        txt_telefono = (EditText) findViewById(R.id.txt_telefono);
        txt_mail = (EditText) findViewById(R.id.txt_mail);
        txt_clave = (EditText) findViewById(R.id.txt_clave);
        passwords = (EditText) findViewById(R.id.passwords);
        eMail = (EditText) findViewById(R.id.eMail);
        TextView_Email = (TextInputLayout) findViewById(R.id.TextView_Email);
        TextView_Password = (TextInputLayout) findViewById(R.id.TextView_Password);
        view_mail=(TextInputLayout) findViewById(R.id.view_mail);
        view_telefono=(TextInputLayout) findViewById(R.id.view_telefono);
        view_nombre=(TextInputLayout) findViewById(R.id.view_nombre);
        view_apellido=(TextInputLayout) findViewById(R.id.view_apellido);
        view_clave=(TextInputLayout) findViewById(R.id.view_clave);
        txt_olvidar_clave=(TextView) findViewById(R.id.txt_olvidar_clave);
        spinKitView=(SpinKitView) findViewById(R.id.spin_kit);
        singIn=(Button) findViewById(R.id.singIn);

       // mAuth=FirebaseAuth.getInstance();
        //mDatabase= FirebaseDatabase.getInstance().getReference();
      //  mAuth.setLanguageCode("es");


          findViewById(R.id.boton_correo).setOnClickListener(new View.OnClickListener() {
          @Override
             public void onClick(View view) {
        signIn();
    }
          });

          txt_olvidar_clave.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent=new Intent(LoginActivity.this,RecuperarActivity.class);
                  startActivity(intent);
              }
          });

          singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                singUp.setBackgroundResource(R.drawable.switch_trcks);
                singUp.setTextColor(Color.parseColor("#ffffff"));
                logIn.setBackgroundResource(0);
                singUpLayout.setVisibility(View.VISIBLE);
                logInLayout.setVisibility(View.GONE);
                logIn.setTextColor(Color.parseColor("#FF018786"));


            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singUp.setBackgroundResource(0);
                singUp.setTextColor(Color.parseColor("#FF018786"));
                logIn.setBackgroundResource(R.drawable.switch_trcks);
                singUpLayout.setVisibility(View.GONE);
                logInLayout.setVisibility(View.VISIBLE);
                logIn.setTextColor(Color.parseColor("#ffffff"));
            }
        });
       /////////////////////////////////////////////////////////////////////////////////////////////7



        ////////////////////////////////////////////////////////////////////////////////////////////
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_ide))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();


        ////////////////////////////////////////////////////////////////////////////////////////////////////
        txt_apellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view_apellido.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_nombre.addTextChangedListener(new TextWatcher() {
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

        txt_clave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view_clave.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eMail.addTextChangedListener(new TextWatcher() {
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

        txt_telefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view_telefono.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view_mail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView_Password.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////7///
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                iniciarSesion(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "NO FUNCIONAAAAA  ", e);

            //si es que falla
              //      MotionToast.Companion.createColorToast(LoginActivity.this,//Toast Personalizado
                    //    "Advertencia",
                      //  "Problemas con el Nro de Teléfono",
                        //MotionToastStyle.WARNING,
                        //MotionToast.GRAVITY_BOTTOM,
                        //MotionToast.LONG_DURATION,
                        //ResourcesCompat.getFont(LoginActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));
                singIn.setVisibility(View.VISIBLE);
                spinKitView.setVisibility(View.GONE);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
            //    Toast.makeText(LoginActivity.this,"El codigo de verificacion fue enviado",Toast.LENGTH_LONG).show();
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                    Intent intent=new Intent(LoginActivity.this,verificacion_phone.class);
                    intent.putExtra("auth",s);
                   intent.putExtra("nombre",txt_nombre.getText().toString());
                   intent.putExtra("apellido",txt_apellido.getText().toString());
                   intent.putExtra("numero",txt_telefono.getText().toString());
                   intent.putExtra("correo",txt_mail.getText().toString());
                   intent.putExtra("clave",txt_clave.getText().toString());
                    startActivity(intent);
               }
           },1000);
            }
        };

    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {


            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                              FirebaseUser user = mAuth.getCurrentUser();
                         updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this,mensaje+"",Toast.LENGTH_LONG).show();

                            updateUI(null);
                        }
                    }
                });
    }
    //////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void signIn() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
       startActivityForResult(signInIntent, RC_SIGN_IN);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void updateUI(FirebaseUser user) {
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if (user1 != null) {
            if (!user1.isEmailVerified()) {
                MotionToast.Companion.createColorToast(LoginActivity.this,//Toast Personalizado
                        "Advertencia",
                        "Correo Electrònico no verificado",
                        MotionToastStyle.WARNING,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(LoginActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));

            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

   ////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean validar_registro() {
        boolean retorno = true;
        String nombre,apellido,telefono,mail, clave;
        nombre=txt_nombre.getText().toString();
        apellido=txt_apellido.getText().toString();
        telefono=txt_telefono.getText().toString();
        mail=txt_mail.getText().toString();
        clave=txt_clave.getText().toString();
        if (nombre.isEmpty()) {
            view_nombre.setError("Favor ingrese Nombre");
            retorno = false;

        } else {
            view_nombre.setErrorEnabled(false);
        }
        if (apellido.isEmpty()) {
            view_apellido.setError("Favor ingrese Apellido");
            retorno = false;

        } else {
            view_apellido.setErrorEnabled(false);
        }
        if (telefono.isEmpty()) {
            view_telefono.setError("Favor ingrese Teléfono");
            retorno = false;

        } else {
            view_telefono.setErrorEnabled(false);
        }
        if (mail.isEmpty()) {
            view_mail.setError("Favor ingrese correo");
            retorno = false;

        } else {
            view_mail.setErrorEnabled(false);
        }
        if (clave.isEmpty()) {
            view_clave.setError("Favor ingrese Clave");
            retorno = false;

        } else {
            view_clave.setErrorEnabled(false);
        }
        if (clave.length()<=5) {
            view_clave.setError("Debe tener al menos seis caracteres");
            retorno = false;

        } else {
            view_clave.setErrorEnabled(false);
        }
        return retorno;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean validar() {
        boolean retorno = true;
        String usuario, clave;
        usuario = eMail.getText().toString();
        clave = passwords.getText().toString();
        if (usuario.isEmpty()) {
            TextView_Email.setError("Favor ingrese su correo");
            retorno = false;

        } else {
            TextView_Email.setErrorEnabled(false);
        }
        if (clave.isEmpty()) {
            TextView_Password.setError("Favor ingrese una contraseña");
            retorno = false;

        } else {
            TextView_Password.setErrorEnabled(false);
        }
        return retorno;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public void ingresar(View v){
        ocultar();
        if(validar()){
            String usuario, clave;
            usuario = eMail.getText().toString().trim();
            clave = passwords.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(usuario,clave).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task5) {
                    if (task5.isSuccessful()) {
                        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                        if (!user1.isEmailVerified()) {
                            MotionToast.Companion.createColorToast(LoginActivity.this,//Toast Personalizado
                                    "Advertencia",
                                    "Correo Electrònico no verificado",
                                    MotionToastStyle.WARNING,
                                    MotionToast.GRAVITY_BOTTOM,
                                    MotionToast.LONG_DURATION,
                                    ResourcesCompat.getFont(LoginActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));

                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        MotionToast.Companion.createColorToast(LoginActivity.this,//Toast Personalizado
                                "Advertencia",
                                "Datos Invalidos",
                                MotionToastStyle.WARNING,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(LoginActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));
                        eMail.setText("");
                        passwords.setText("");
                    }
                }
            });
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   private void limpiar(){
        txt_nombre.setText("");
       txt_apellido.setText("");
       txt_clave.setText("");
       txt_mail.setText("");
       txt_telefono.setText("");
       }
       ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public void validar_telefono(View v){

        singIn.setVisibility(View.GONE);
       spinKitView.setVisibility(View.VISIBLE);
       ocultar();
       String nombre,apellido,numero,correo, clave;
       nombre=txt_nombre.getText().toString();
       apellido=txt_apellido.getText().toString();
       numero=txt_telefono.getText().toString();
       correo=txt_mail.getText().toString();
       clave=txt_clave.getText().toString();
       if (validar_registro()) {
           mAuth.createUserWithEmailAndPassword(correo, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task2) {
                   if (task2.isSuccessful()) {
                   FirebaseUser user= mAuth.getCurrentUser();
                       user.sendEmailVerification();
                       Map<String, Object> map = new HashMap<>();
                       map.put("nombre", nombre);
                       map.put("apellido", apellido);
                       map.put("telefono", "+595"+ numero);
                       map.put("correo", correo);
                       map.put("clave", clave);

                       String id = mAuth.getCurrentUser().getUid();
                       mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task3) {
                               if (task3.isSuccessful()) {

                                   singIn.setVisibility(View.VISIBLE);
                                   spinKitView.setVisibility(View.GONE);
                                   iniciarHome();
                                   MotionToast.Companion.createColorToast(LoginActivity.this,//Toast Personalizado
                                           "Registrado",
                                           "Registrado sin problemas!",
                                           MotionToastStyle.SUCCESS,
                                           MotionToast.GRAVITY_BOTTOM,
                                           MotionToast.LONG_DURATION,
                                           ResourcesCompat.getFont(LoginActivity.this, www.sanju.motiontoast.R.font.helvetica_regular));
                                   limpiar();
                               }
                           }
                       });
                   }
               }
           });
          // String numero = "+595" + txt_telefono.getText().toString();
         //  PhoneAuthOptions options =
           //        PhoneAuthOptions.newBuilder(mAuth)
             //              .setPhoneNumber(numero)
               //            .setTimeout(60L, TimeUnit.SECONDS)
                 //          .setActivity(LoginActivity.this)
                   //        .setCallbacks(mCallbacks)
                     //      .build();
             }
   }
   /////////////////////////////////////////////////////////////////////////////////////////////////
    private void iniciarSesion(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    iniciarHome();
                }
            }


        });
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void iniciarHome() {
        Intent intent=new Intent(LoginActivity.this, MainActivity.class);

        startActivity(intent);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void ocultar() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imn = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imn.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}