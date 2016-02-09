package com.example.estacionvl_tc_014.tienda.net.conn;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by EstacionVL-TC-014 on 3/02/16.
 */
public class HttpConnection {

    public Response requestByGet(String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();

        con.setRequestMethod("GET");
        con.setDoInput(true);
        con.connect();

        InputStream iS = con.getInputStream();

        Response response = new Response(streamToString(iS)
                , con.getResponseCode());

        return response;
    }

    public Response requestByPostForm(String url, String form) throws IOException {
        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);

        con.setRequestProperty("Content-Type", "application/x-www-urlencoded");

        con.connect();

        OutputStream out = con.getOutputStream();

        DataOutputStream writer = new DataOutputStream(out);
        writer.writeBytes(URLEncoder.encode(form, "UTF-8"));

        writer.flush();
        writer.close();

        InputStream iS = con.getInputStream();

        Response response = new Response(streamToString(iS)
                , con.getResponseCode());

        return response;
    }

    public Response requestByPostJson(String url, String json) throws IOException {
        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);

        con.setRequestProperty("Content-Type", "application/json");

        con.connect();

        OutputStream out = con.getOutputStream();

        DataOutputStream writer = new DataOutputStream(out);
        writer.writeBytes(URLEncoder.encode(json,"UTF-8"));

        writer.flush();
        writer.close();

        InputStream iS = con.getInputStream();

        Response response = new Response(streamToString(iS)
                , con.getResponseCode());

        return response;
    }

    private String streamToString(InputStream iS) throws IOException {
        InputStreamReader reader = new InputStreamReader(iS);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        int ch;
        while((ch = reader.read())!=-1){
            stream.write(ch);
        }

        String rta = new String(stream.toByteArray());

        reader.close();
        stream.close();
        iS.close();

        return rta;
    }

}
