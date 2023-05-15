# Ejemplo de Trazabilidad con Kafka

## Objetivo
Proyecto base que funciona como kafka producer, para poder evaluar la trazabilidad de este en zipkin utilizando
Spring Boot 3 y Java 17.

## Componentes
Este proyecto cuenta con un archivo docker-compose que nos permite levantar un kafka broker (junto a la Landoop UI, 
zookeeper, REST Proxy, Schema Registry, Kafka Connect ports y JMX Ports), zipkin, el producer y el consumer.
También hay que tener en cuenta que se debe habilitar el perfil de spring **local**.

## Ejecución
Para enviar un mensaje a kafka, hay que realizar un POST al endpoint */v1/example/clients* (create Client), y
luego ir a localhost:9411 (zipkin) para poder observar las trazas, buscando por el serviceName (producer), o por id de traza 
que se ve en el request del producer.

## Configuración
En spring boot 2 existía la dependencia sleuth que nos permitía el manejo de trazas distribuidas (y que funciona para
kafka sin mayores dificultades). En spring boot 3, ya no existe esta dependencia, por lo que hay que optar por micrometer
para el manejo de trazas. Se deben agregar las siguientes dependencias al pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-observation</artifactId>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-tracing-bridge-brave</artifactId>
    </dependency>
    <dependency>
        <groupId>io.zipkin.reporter2</groupId>
        <artifactId>zipkin-reporter-brave</artifactId>
    </dependency>
</dependencies>
```
Por otro lado, a diferencia de zipkin, con micrometer para que las trazas lleguen a zipkin no es necesario definir la url
de zipkin ya que por defecto viene el valor **localhost:9411**, pero si es necesario modificarlo es posible.

El último detalle a tener en cuenta es que al momento de configurar la clase KafkaTemplate, que nos va a servir para mandar
los mensajes a Kafka, hay que agregar el seteo de la observabilidad a true:
```java	
@Bean
public KafkaTemplate<K, V> kafkaTemplate() {
    KafkaTemplate<K, V> kafkaTemplate = new KafkaTemplate<>(producerFactory());
    kafkaTemplate.setObservationEnabled(true);
    return kafkaTemplate;
}
```
Siendo esta línea **kafkaTemplate.setObservationEnabled(true);** la importante para la trazabilidad

## Ejemplos

Existe un github de micrometer en la cual dan ejemplos adicionales sobre como obtener y manejar las trazas desde el código.
Se deja a forma de documentación extra:
- [Proyecto base con todos los ejemplos](https://github.com/micrometer-metrics/micrometer-samples)
- [Ejemplo para Kafka Producer](https://github.com/micrometer-metrics/micrometer-samples/blob/main/kafka-producer/src/main/java/com/example/micrometer/KafkaProducerApplication.java)
