package com.example.estacionvl_tc_014.tienda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.estacionvl_tc_014.tienda.models.Usuario;
import com.example.estacionvl_tc_014.tienda.net.UsuarioApi;

import java.util.List;


public class MainActivity extends AppCompatActivity implements UsuarioApi.OnUsuariosFinded {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsuarioApi usuarioApi = new UsuarioApi(this);
        usuarioApi.getAllUsuario(this);

    }

    @Override
    public void onUsuariosFinded(List<Usuario> data) {
        Log.i("","");
    }
}
