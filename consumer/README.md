# Ejemplo de Trazabilidad con Kafka

## Objetivo
Proyecto base que funciona como kafka producer, para poder evaluar la trazabilidad de este en zipkin utilizando
Spring Boot 3 y Java 17.

## Componentes
En la carpeta base, hay un archivo docker-compose que nos permite 
levantar un kafka broker (junto a la Landoop UI, zookeeper, REST Proxy, Schema Registry, 
Kafka Connect ports y JMX Ports), zipkin, el producer y el consumer.
Cuando haya algún mensaje en kafka sin procesar, en el tópico para el que consumer está configurado,
este lo va a recibir e imprimir algunos datos por consola.

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

El último detalle a tener en cuenta es que al momento de configurar la clase KafkaListenerContainerFactory, que nos va a servir para recibir
los mensajes desde Kafka, hay que agregar el seteo de la observabilidad a true:
```java	
@Bean
public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.getContainerProperties().setObservationEnabled(true);
    return factory;
}
```
Siendo esta línea **factory.setObservationEnabled(true);** la importante para la trazabilidad

## Ejemplos

Existe un github de micrometer en la cual dan ejemplos adicionales sobre como obtener y manejar las trazas desde el código.
Se deja a forma de documentación extra:
- [Proyecto base con todos los ejemplos](https://github.com/micrometer-metrics/micrometer-samples)
- [Ejemplo para Kafka Consumer](https://github.com/micrometer-metrics/micrometer-samples/blob/main/kafka-consumer/src/main/java/com/example/micrometer/KafkaConsumerApplication.java)
