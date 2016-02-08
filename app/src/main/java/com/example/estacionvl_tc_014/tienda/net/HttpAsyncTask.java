package com.example.estacionvl_tc_014.tienda.net;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by EstacionVL-TC-014 on 3/02/16.
 */
public class HttpAsyncTask extends AsyncTask<String, Integer, String> {

    public static final int METHOD_GET = 0;
    public static final int METHOD_POST_FORM = 1;
    public static final int METHOD_POST_JSON = 2;

    public interface HttpI{
        void onResponseReceived(String response);
    }


    int method;
    HttpI httpI;

    public HttpAsyncTask(int method, HttpI httpI){
        this.method = method;
        this.httpI = httpI;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpConnection con = new HttpConnection();
        String rta = null;
        try{
            switch (method){
                case METHOD_GET:
                    rta = con.requestByGet(params[0]);
                    break;
                case METHOD_POST_FORM:
                    rta = con.requestByPostForm(params[0], params[1]);
                    break;
                case METHOD_POST_JSON:
                    rta = con.requestByPostJson(params[0], params[1]);
                    break;
            }
        }catch(IOException e){

        }

        return rta;
    }

    @Override
    protected void onPostExecute(String s) {
        httpI.onResponseReceived(s);
    }
}
