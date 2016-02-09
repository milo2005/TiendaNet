package com.example.estacionvl_tc_014.tienda.net.conn;

import android.content.Context;

import com.example.estacionvl_tc_014.tienda.R;
import com.example.estacionvl_tc_014.tienda.models.Usuario;
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
public class ClientApi<T> implements HttpAsyncTask.HttpI{


    private static final int REQUEST_INSERT = 0;
    private static final int REQUEST_FIND_ID = 2;
    private static final int REQUEST_ALL = 1;


    //region Callbacks
    public interface OnInserted{
        void onInserted(boolean success);
    }

    public interface OnFinded<T>{
        void onFinded(T obj);
    }

    public interface OnListFinded<T>{
        void onListFinded(List<T> data);
    }

    OnInserted onInserted;
    OnFinded onFinded;
    OnListFinded onListFinded;
    //endregion

    Context context;
    Gson gson;
    Class c;


    public ClientApi(Context context) {
        this.context = context;

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    }


    @Override
    public void onResponseReceived(int requestCode, Response response) {
        switch(requestCode){
            case REQUEST_INSERT:
                processInsert(response);
                break;
            case REQUEST_FIND_ID:
                processById(response);
                break;
            case REQUEST_ALL:
                processAll(response);
                break;

        }

    }

    //region Insert
    public void insert(String url,T obj, OnInserted onInserted){
        this.onInserted = onInserted;
        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.METHOD_POST_JSON,REQUEST_INSERT, this);
        task.execute(url,gson.toJson(obj));
    }

    private void processInsert(Response response) {
        if(response == null){
            onInserted.onInserted(false);
        }else{
            String msg = response.getMsg();
            try {
                JSONObject object = new JSONObject(msg);
                String state = object.getString("state");

                if(state.equals("ok")){
                    onInserted.onInserted(true);
                }else{
                    onInserted.onInserted(false);
                }

            } catch (JSONException e) {
                onInserted.onInserted(false);
            }
        }

    }
    //endregion

    //region All Resources
    public void getAll(String url,OnListFinded onListFinded){
        this.onListFinded = onListFinded;

        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.METHOD_GET,REQUEST_ALL,this );
        task.execute(url);
    }

    private void processAll(Response response) {
        Type type = new TypeToken<List<T>>(){}.getType();
        List<T> data = gson.fromJson(response.getMsg(), type);
        onFinded.onFinded(data);
    }
    //endregion

    //region Resource By ID
    public void getById(String url,long id,Class<? extends T> c, OnFinded onFinded){
        this.onFinded = onFinded;
        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.METHOD_GET
                , REQUEST_FIND_ID, this);
        task.execute(url);

    }

    private void processById(Response response) {
        T obj = (T) gson.fromJson(response.getMsg(),c.getClass());
        onFinded.onFinded(obj);
    }
    //endregion





}
