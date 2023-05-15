# Ejemplo de Trazabilidad con Kafka

En esta carpeta se encuentra el archivo docker-compose para poder levantar 
todo el proyecto sin necesidad de ejecutar individualmente los proyectos. 

En este archivo están definidas las imágenes del broker de kafka, zipkin,
el consumer y producer.

Para las imágenes del consumer y el producer se deja un Dockerfile por 
proyecto para poder levantar las imágenes localmente.

Para crear la imagen del producer y el consumer no es necesario ningún comando extra,
al querer levantar el docker compose, va a levantar las imágenes.

Finalmente, para correr toda la aplicación hay que ejecutar el comando
```
docker compose up
```

Si no se quieren ver los logs de las aplicaciones se puede agregar **-d** al final de cada comando 