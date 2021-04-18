## Manual de Instalación del sistema

### Pre-Requisitos

* Java 11
* Maven 3.6
* Xampp Server

### Instalación

Para poder descargar el proyecto únicamente tenemos que clonarlo o descargarlo como zip de la rama **develop**:

![alt text](/mdimages/cloneRepo.jpg "Clonar repositorio")

Una vez que hemos clonado el repositorio, debemos importar el archivo de la base de datos a nuestra base de datos local (para ello haremos uso de Xampp, utilizando mysql). Así pues, el archivo de la base de datos empleado para el proyecto puede encontrarse en este proyecto en src/main/resources.

Ya que importamos la base de datos y tenemos prendido nuestro servidor local, procederemos a comprobar nuestro entorno de desarrollo, desde nuestra consola, empleando el comando que se muestra a continuación:
```
java -version
```
Como se menciona al inicio, es necesario que el entorno sea [Java 11](https://adoptopenjdk.net/).

Una vez que hemos comprobado que el entorno es el correcto, comprobaremos que Maven también ha sido instalado correctamente con el comando
```
mvn -v
```

Ya que nos hemos asegurado de tener los elementos necesarios para el funcionamiento de la API, procederemos a añadir el archivo **application.properties** a nuestro folder src/main/resources. Este archivo contiene las credenciales para acceder a nuestra base de datos, por lo que es importante asegurarnos de que tenemos los datos correctos.
Así pues, el archivo debe quedar de la siguiente manera:
```
server.error.include-message=always

spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.username= #usuario de la base de datos
spring.datasource.password= #contraseña de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/streaming_api?serverTimezone=UTC

#Token Configuration
jwt.secret="haha, php goes brrr <br>"
#Expiración en Minutos
jwt.expiration=5

#Configuracion Video
spring.servlet.multipart.max-file-size=5000MB
spring.servlet.multipart.max-request-size=5000MB
spring.servlet.multipart.enabled=true
```

### Funcionamiento

Una vez que ya hemos terminado con la instalación y que hemos prendido nuestro servidor para levantar la base de datos, únicamente tenemos que situarnos en la carpeta raíz del proyecto, abrir una terminal de comandos y compilar el programa como sigue:
```
mvn clean install
```
Una vez que la API se ha compilado, notaremos que se creó una carpeta llamada _target_ y que dentro de ella existe como tal la aplicación que deseamos correr con el nombre _streaming-0.0.1-SNAPSHOT_ y, para poder ejecutarla, debemos correr el siguiente comando:
```
java -jar target/streaming-0.0.1-SNAPSHOT
```
Una vez que hemos completado los comandos anteriores, la aplicación debería estar ejecutándose correctamente.