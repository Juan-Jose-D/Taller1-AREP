package com.arep.clase;

import java.net.URI;

public class HttpRequest {

    URI requri = null;

    HttpRequest(URI requesturi){
        requri = requesturi;
    }
    public String getValue(String paramName){
        String paramValue = paramName.split("=")[1];
        return paramValue;
    }
}
