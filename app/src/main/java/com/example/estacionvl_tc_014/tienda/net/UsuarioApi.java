package com.example.estacionvl_tc_014.tienda.net;

import android.content.Context;

import com.example.estacionvl_tc_014.tienda.R;
import com.example.estacionvl_tc_014.tienda.models.Usuario;
import com.example.estacionvl_tc_014.tienda.net.conn.HttpAsyncTask;
import com.example.estacionvl_tc_014.tienda.net.conn.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by EstacionVL-TC-014 on 8/02/16.
 */
public class UsuarioApi implements HttpAsyncTask.HttpI{

    private static final int URL_BASE = R.string.url_base;
    private static final int INSERT = R.string.url_usuario_insert;
    private static final int ALL = R.string.url_usuario_all;
    private static final int FIND_ID = R.string.url_usuario_id;

    private static final int REQUEST_INSERT = 0;
    private static final int REQUEST_USUARIO_ID = 2;
    private static final int REQUEST_ALL = 1;


    //region Callbacks
    public interface OnUsuarioInserted{
        void onUsuarioInserted(boolean success);
    }

    public interface OnUsuarioFinded{
        void onUsuarioFinded(Usuario usuario);
    }

    public interface OnUsuariosFinded{
        void onUsuariosFinded(List<Usuario> data);
    }

    OnUsuarioInserted onUsuarioInserted;
    OnUsuarioFinded onUsuarioFinded;
    OnUsuariosFinded onUsuariosFinded;
    //endregion

    Context context;
    Gson gson;
    String base;

    public UsuarioApi(Context context) {
        this.context = context;

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        base = context.getString(URL_BASE);
    }


    @Override
    public void onResponseReceived(int requestCode, Response response) {
        switch(requestCode){
            case REQUEST_INSERT:
                processInsertUsuario(response);
                break;
            case REQUEST_USUARIO_ID:
                processUsuarioById(response);
                break;
            case REQUEST_ALL:
                processAllUsuario(response);
                break;

        }

    }

    //region Insert
    public void insertUsuario(Usuario usuario, OnUsuarioInserted onUsuarioInserted){
        this.onUsuarioInserted = onUsuarioInserted;
        String url = context.getString(URL_BASE)+context.getString(INSERT);
        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.METHOD_POST_JSON,REQUEST_INSERT, this);
        task.execute(url,gson.toJson(usuario));
    }

    private void processInsertUsuario(Response response) {
        if(response == null){
            onUsuarioInserted.onUsuarioInserted(false);
        }else{
            String msg = response.getMsg();
            try {
                JSONObject object = new JSONObject(msg);
                String state = object.getString("state");

                if(state.equals("ok")){
                    onUsuarioInserted.onUsuarioInserted(true);
                }else{
                    onUsuarioInserted.onUsuarioInserted(false);
                }

            } catch (JSONException e) {
                onUsuarioInserted.onUsuarioInserted(false);
            }
        }

    }
    //endregion

    //region All usuario
    public void getAllUsuario(OnUsuariosFinded onUsuariosFinded){
        this.onUsuariosFinded = onUsuariosFinded;
        String url = base+context.getString(ALL);
        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.METHOD_GET,REQUEST_ALL,this );
        task.execute(url);
    }

    private void processAllUsuario(Response response) {
        Type type = new TypeToken<List<Usuario>>(){}.getType();
        List<Usuario> data = gson.fromJson(response.getMsg(), type);
        onUsuariosFinded.onUsuariosFinded(data);
    }
    //endregion

    //region UsuarioByID
    public void getUsuarioById(long id, OnUsuarioFinded onUsuarioFinded){
        this.onUsuarioFinded = onUsuarioFinded;
        String url = context.getString(URL_BASE)+context.getString(FIND_ID)+id;
        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.METHOD_GET
                , REQUEST_USUARIO_ID, this);
        task.execute(url);

    }

    private void processUsuarioById(Response response) {
        Usuario usuario = gson.fromJson(response.getMsg(), Usuario.class);
        onUsuarioFinded.onUsuarioFinded(usuario);
    }
    //endregion





}
