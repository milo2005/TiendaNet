package com.example.estacionvl_tc_014.tienda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.estacionvl_tc_014.tienda.models.Usuario;
import com.example.estacionvl_tc_014.tienda.net.UsuarioApi;
import com.example.estacionvl_tc_014.tienda.net.conn.ClientApi;

import java.util.List;



public class MainActivity extends AppCompatActivity implements ClientApi.OnListFinded<Usuario> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClientApi<Usuario> client  = new ClientApi<>(this);
        client.getAll("",this);



    }


    @Override
    public void onListFinded(List<Usuario> data) {

    }
}
