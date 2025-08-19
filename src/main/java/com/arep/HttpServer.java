package com.arep;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta clase implementa un servidor HTTP básico que maneja solicitudes GET y POST para una API,
 * así como solicitudes para servir archivos estáticos (HTML, CSS, JS, imágenes) desde el servidor.
 * Escucha en el puerto 35000 y gestiona componentes que se almacenan en una lista.
 * No usa frameworks, solo bibliotecas estándar de Java.
 */

public class HttpServer {

    // Lista de componentes que almacenará el servidor
    public static final List<Component> components = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            // Iniciamos el servidor en el puesto 35000
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Servidor iniciado en el puerto 35000");
                // El servidor espera y acepta una nueva conexión de cliente
                clientSocket = serverSocket.accept();
                handleRequest(clientSocket);
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

        }

    }
    /**
     * Maneja una solicitud HTTP de un cliente.
     *
     * @param clientSocket el socket de la conexión del cliente.
     * @throws IOException si ocurre un error al leer la solicitud o enviar la respuesta.
     */
    private static void handleRequest(Socket clientSocket) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        // Lee la primera línea de la solicitud HTTP
        String requestLine = in.readLine();
        if (requestLine == null) return;

        System.out.println("Solicitud: " + requestLine);
        String[] requestParts = requestLine.split(" ");
        String method = requestParts[0];
        String path = requestParts[1];

        if (path.startsWith("/api/components")){
            handleApiRequest(method, path, in, out);
        } else {
            serveStaticFile(path, out);
        }

        in.close();
        out.close();
        clientSocket.close();
    }

    /**
     * Maneja solicitudes API para obtener o agregar componentes (GET y POST).
     * Si el método es GET, responde con la lista de componentes en formato JSON.
     * Si el método es POST, agrega un nuevo componente a la lista.
     *
     * @param method el método HTTP (GET o POST).
     * @param path la ruta solicitada.
     * @param in el BufferedReader para leer el cuerpo de la solicitud.
     * @param out el OutputStream para enviar la respuesta.
     * @throws IOException si ocurre un error al leer o escribir los datos.
     */
    static void handleApiRequest(String method, String path, BufferedReader in, OutputStream out) throws IOException {
        String response;
        if (method.equals("GET")) {
            // Construir JSON manualmente para mantener el formato correcto
            StringBuilder jsonResponse = new StringBuilder("[");
            for (int i = 0; i < components.size(); i++) {
                Component c = components.get(i);
                jsonResponse.append("{\"name\":\"").append(c.getName())
                           .append("\", \"type\":\"").append(c.getType())
                           .append("\", \"description\":\"").append(c.getDescription())
                           .append("\", \"rating\":").append(c.getRating()).append("}");
                if (i < components.size() - 1) {
                    jsonResponse.append(",");
                }
            }
            jsonResponse.append("]");
            
            response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + jsonResponse;
        } else if (method.equals("POST")) {
            String body = readRequestBody(in);
            System.out.println("Cuerpo recibido: " + body);

            try {
                body = body.trim();
                if (!body.startsWith("{") || !body.endsWith("}")) {
                    throw new IllegalArgumentException("Formato JSON inválido");
                }

                Map<String, String> data = parseJson(body);
                if (data.containsKey("name") && data.containsKey("type") && 
                    data.containsKey("description") && data.containsKey("rating")) {
                    
                    components.add(new Component(
                        data.get("name"), 
                        data.get("type"), 
                        data.get("description"),
                        Integer.parseInt(data.get("rating"))
                    ));
                    response = "HTTP/1.1 201 Created\r\nContent-Type: text/plain\r\n\r\nComponent added";
                } else {
                    response = "HTTP/1.1 400 Bad Request\r\n\r\nMissing fields";
                }
            } catch (Exception e) {
                response = "HTTP/1.1 400 Bad Request\r\n\r\nInvalid JSON format";
            }
        } else {
            response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n";
        }
        out.write(response.getBytes());
        out.flush();
    }

    /**
     * Lee el cuerpo de una solicitud HTTP POST.
     * El cuerpo es extraído según el encabezado "Content-Length".
     *
     * @param in el BufferedReader para leer el cuerpo.
     * @return el cuerpo de la solicitud como una cadena.
     */
    private static String readRequestBody(BufferedReader in) throws IOException {
        StringBuilder body = new StringBuilder();
        int contentLength = 0;

        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.substring(15).trim());
            }
        }

        if (contentLength > 0) {
            char[] buffer = new char[contentLength];
            in.read(buffer, 0, contentLength);
            body.append(buffer);
        }

        String jsonBody = body.toString();
        System.out.println("JSON Recibido: " + jsonBody);
        return jsonBody;
    }

    /**
     * Parsea un JSON simple en un mapa clave-valor.
     *
     * @param json la cadena JSON a parsear.
     */
    private static Map<String, String> parseJson(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.substring(1, json.length() - 1); // Elimina los corchetes { }
        String[] pairs = json.split(",");

        // Procesa cada par clave-valor en el JSON.
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().replace("\"", "");
                String value = keyValue[1].trim().replace("\"", "");
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * Sirve un archivo estático en respuesta a una solicitud HTTP.
     *
     * @param path la ruta solicitada del archivo.
     * @param out el OutputStream para enviar la respuesta.
     */
    private static void serveStaticFile(String path, OutputStream out) throws IOException {
        if (path.equals("/")) path = "/index.html";

        // Busca el archivo estático en la carpeta "src/main/webapp"
        File file = new File("public" + path);
        if (file.exists() && !file.isDirectory()) {
            // Determina el tipo de contenido según la extensión del archivo
            String contentType = getContentType(path);
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            out.write(("HTTP/1.1 200 OK\r\nContent-Type: " + contentType + "\r\n\r\n").getBytes());
            out.write(fileBytes);
        } else {
            // Si el archivo no existe, responde con 404.
            out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
        }
    }

    /**
     * Devuelve el tipo de contenido (MIME type) basado en la extensión del archivo.
     *
     * @param path la ruta del archivo.
     * @return el tipo de contenido correspondiente.
     */
    private static String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }
}