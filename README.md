## Arquitectura de modernización para procesamiento crediticio desacoplado orientada a eventos, diseñada para integrar plataformas digitales con Core Banking legacy minimizando acoplamiento, mejorando resiliencia operacional y habilitando escalabilidad horizontal

## Apache Kafka + Apache Camel + Spring Boot + Bantotal Integration ##

## 1. Problema del negocio: ##

### 1.1. Situación actual: ###
Los sistemas core bancarios legacy presentan:
- alto acoplamiento
- integraciones síncronas frágiles
- baja escalabilidad
- dificultad para incorporar nuevos canales digitales

### 1.1. Propuesta
Implementar una arquitectura event-driven desacoplada basada en Apache Kafka y Apache Camel para separar decisiones de negocio, integración y procesamiento operacional.

### 1. 1. Beneficios
- Resiliencia ante fallas del Core Banking
- Integración desacoplada
- Escalabilidad independiente
- Reprocesamiento seguro
- Mayor observabilidad
- Evolución incremental
- Menor impacto en sistemas legacy

### 1.4. Capacidad futura
La arquitectura permite incorporar nuevos consumidores de eventos sin afectar servicios existentes:
- fraude
- analytics
- auditoría
- machine learning
- omnicanalidad

## 2. Vision de Solución ##

![Imagen Vision de Solucion](./vision_solucion.png)


## 3. Arquitectura de solución / Arquitectura Lógica ##

![Imagen de arquitectura de solucion](./arquitectura_event_driven.png)


## 4. Implementación Incremental ##
### 4.1. Fase 1 Conectividad Core-to-Event Hub (SOAP a Kafka) ###
Demostrar el flujo EDA básico.

**4.1.1. Componentes:**

| Categoría | tecnologia | Descripción | Notas tecnicas |
| :--- | :--- | :--- | :--- |
| **Integración** | String boot/Apache Camel | Microservicio de integracion de canales a bus de eventos |Archivo docker-compose.yml container_name: kafka-ui-EB |
| **Bus de eventos** | Apache Kafka | Servicio Kafka que implementa bus de eventos |Archivo docker-compose.yml container_name: kafka-EB Imagen oficial de Docker para kafka container_name: kafka-ui-EB: Una interfaz web para monitorizar clústeres de Apache Kafka |
| **Servicio de dominio** | String boot/Apache Camel | Microservicio de Scoring de credito |Archivo docker-compose.yml container_name: credit-scoring-service |

**4.1.2. Implementación:**
- Instalar docker en el PC  
- Crear un directorio en el PC, por ejemplo arquitectura_camel_kafka  
- Descargar los proyectos integracionCamel y credit-scoring-service y ubicarlos dentro del directorio arquitectura_camel_kafka  
- Desde el cmd ingresar al directorio arquitectura_camel_kafka  
- Ejecutar el comando docker compose up --build  
- Validar la ejecucion de los contenedores dockers
![Imagen Docker Container](./docker-containers.PNG)

**4.1.3. Operacion:**

**Realizar un request a través de Soap UI**
- Ingresar url http://localhost:8080/api/credit/request
![Imagen Soap UI](./soap-ui-new.PNG)
- Llenar los datos del mensaje Customerid=C003
![Imagen Soap UI Request](./soap-ui-request.PNG)
- Ejecutar request y validar respuesta
![Imagen Soap UI response](./soap-ui-response.PNG)

**Validar el viaje de los mensajes a travez de los Topics kafka**
- Ingresar a kafka UI, ingresar a la siguiente url http://localhost:8081/
- Luego seleccionar la opcion Topics, se visualizará las Topics creadas
![Imagen Kafka](./kafka-ui.PNG)  
- Seleccionar el topic credit.requested, luego clic en la pestaña Messages, seleccionamos un mensaje, luego visualizaremos el request almacenado en el topic, de esta forma verificamos que el request llegó topic requested
![Imagen Topic credict.request](./kafka-ui_topic_credit-requested.PNG)
- Seleccionar el topic credit.scored, luego clic en la pestaña Messages, visualizaremos el mensaje publicado del scored, aqui se puede verificar que el mensaje generado es de diferente tipo, el contenido es generado por el microservicio scored
![Imagen Topic credit.request](./kafka-ui_topic_credit-scored.PNG)


- Revision de flujos en Hawtio
![Imagen proyecto de arquitectura_bus_evento](./hawtio.PNG)


### 4.2. Fase 2 Integración bancaria ###
Agrega Core Banking Adapter 
- consume credit.scored
- publica credit.approved
- consume credit.approved
- simule llamada Bantotal
- publique credit.disbursed

**4.1.4. Decision Service**
**3 camel-credit-demo:**


### Fase 3 — Notification Service
Consume eventos finales.

### Fase 4 — Resiliencia
Agrega:
- retries
- DLQ
- idempotencia

### Fase 5 — Observabilidad
- correlation-id
- tracing
- logs estructurados
