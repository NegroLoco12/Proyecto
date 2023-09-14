package com.foysaltech.drawersliding;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;



public class FragmentPago extends Fragment {

    Button btn_pago;
    String order;

    public FragmentPago() {
        // Required empty public constructor
    }

    public static FragmentPago newInstance(String param1, String param2) {
        FragmentPago fragment = new FragmentPago();
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
        View view=inflater.inflate(R.layout.fragment_pago, container, false);
        btn_pago=view.findViewById(R.id.btn_pago);
        getParentFragmentManager().setFragmentResultListener("keytoquen", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                order= result.getString("token");
                Toast.makeText(getContext(),""+order,Toast.LENGTH_SHORT).show();
            }
        });
        btn_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureOrder(order);
            }
        });
        return view;
    }



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
                    //redirect back to home page of app
                    //  Fragment selectedScreen = new CenteredTextFragment() ;
                    //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();

                } catch (JSONException e) {

                }
            }




        });
    }
}

