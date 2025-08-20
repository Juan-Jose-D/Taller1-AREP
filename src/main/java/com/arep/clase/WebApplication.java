package com.arep.clase;

import static com.arep.clase.HttpServer.get;
import static com.arep.clase.HttpServer.staticfiles;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.arep.clase.HttpServer.startServer;

public class WebApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        staticfiles("/webroot");
        get("/hello", (req, resp) -> "Hello " + req.getValue("name"));
        get("/pi", (req, resp) -> {
            return String.valueOf(Math.PI); 
        });

        startServer(args);
    }


}