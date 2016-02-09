package com.example.estacionvl_tc_014.tienda.net.conn;

/**
 * Created by EstacionVL-TC-014 on 8/02/16.
 */
public class Response {

    String msg;
    int statusCode;

    public Response(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
