# AREP Taller 1 

Este proyecto implementa un servidor web en Java que maneja múltiples solicitudes de manera secuencial (no concurrente). El servidor es capaz de leer archivos del disco local y devolver cualquier archivo solicitado, incluyendo páginas HTML, archivos JavaScript, CSS e imágenes. Implementa uuna arquitectura de servidow web sencillo basado en java, donde el servidor HTTP básico se ejecuta, acepta solicitudes de clientes, las procesa y envía las respuestas.

---

## Capturas de pantalla de la aplicación

La aplicación web funciona como una lista para guarda los libros, películas y series que el usuario haya visto y quiera guardar para futuros recuerdos, puede añadir una descripción y una calificación.

![screenshot](/public/images/image.png)

Puede especificar si es un libro, película o serie.

![screenshot](/public/images/image2.png)

![screenshot](/public/images/image3.png)

En la opción de Network de la herramienta de inspección del navegador podemos observar que hace un post al endpoint /api/components para crear el componente.

![screenshot](/public/images/image5.png)

Y justo después hce un get a /api/components y recibe la información del componente en formato json

![screenshot](/public/images/image4.png)


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

Ingrese al directorio del proyecto:

```bash
cd Taller1-AREP
```

Compile el proyecto con Maven:

```bash
mvn clean compile
```

Y ejecute el servidor:

```bash
java -cp target/classes com.arep.HttpServer
```

![screenshot](/public/images/image6.png)

Abra su navegador y acceda a:

```bash
http://localhost:35000/index.html
```

---

## Pruebas

Para ejecutar las pruebas automatizadas use este comando de maven:

```bash
mvn test
```

---

## Despliegue

Para desplegar este servidor en un sistema en producción.

Empaquete el proyecto en un JAR ejecutable:

```bash
mvn clean package
```

Ejecute el JAR generado:

```bash
java -jar target/taller1-arep-1.0-SNAPSHOT.jar 
```

---

## Herramientas usadas

- Java SE – Lenguaje de programación orientado a objetos para aplicaciones de propósito general.
- Maven – Herramienta de gestión de dependencias y automatización de la construcción de proyectos Java.
- HTML5 – Lenguaje de marcado estándar para estructurar el contenido de páginas web.
- JavaScript – Lenguaje de programación que permite crear interactividad y lógica en aplicaciones web del lado del cliente (y también del servidor con Node.js).
- CSS – Lenguaje de estilos que define la presentación y diseño visual de documentos HTML.

---

## Autor

Juan José Díaz - [github](https://github.com/Juan-Jose-D)

Escuela Colombiana de ingeniería Julio Garavito