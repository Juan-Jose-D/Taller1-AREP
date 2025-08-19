# AREP Taller 1 

Este proyecto implementa un servidor web en Java que maneja múltiples solicitudes de manera secuencial (no concurrente). El servidor es capaz de leer archivos del disco local y devolver cualquier archivo solicitado, incluyendo páginas HTML, archivos JavaScript, CSS e imágenes. Implementa uuna arquitectura de servidow web sencillo basado en java, donde el servidor HTTP básico se ejecuta, acepta solicitudes de clientes, las procesa y envía las respuestas.

---

## Capturas de pantalla de la aplicación

La aplicación web funciona como una lista para guarda los libros, películas y series que el usuario haya visto y quiera guardar para futuros recuerdos, puede añadir una descripción y una calificación.

![alt text](/public/images/image.png)

Puede especificar si es un libro, película o serie.

![alt text](/public/images/image2.png)

![alt text](/public/images/image3.png)

En la opción de Network de la herramienta de inspección del navegador podemos observar que hace un post al endpoint /api/components para crear el componente.

![alt text](/public/images/image5.png)

Y justo después hce un get a /api/components y recibe la información del componente en formato json

![alt text](/public/images/image4.png)


---

## Ejecutando el proyecto

Estas instrucciones permiten obtener una copia del proyecto en funcionamiento en la máquina local para desarrollo y pruebas.

El servidor se implementa utilizando sockets en Java y gestiona solicitudes GET y POST para obtener y agregar componentes a través de una API REST básica sin hacer uso de frameworks como spring.

### Pre-requisitos

Para ejecutar este proyecto necesitas instalar lo siguiente:

- Java 17 o superior
- Maven 3.8.1 o superior (la versión en el entorno donde fue creado es la 3.9.9)
- Un navegador web
  
En caso de no tener maven instalado, aquí se encuentra un tutorial [Instalación de Maven]([/guides/content/editing-an-existing-page](https://dev.to/vanessa_corredor/instalar-manualmente-maven-en-windows-10-50pb)). 

### Instalación

Siga estos pasos para obtener un entorno de desarrollo funcional:

Clone este repositorio:

```bash
git clone https://github.com/AnaDuranB/Taller-01-AREP.git
```

Ingresa al directorio del proyecto:
cd Taller-01-AREP
En caso de no contar con un IDE de java que se haga responsable de la compilación y ejecución:

Compila el proyecto con Maven:
mvn clean compile
Ejecuta el servidor:
java -cp target/classes org.example.HttpServer