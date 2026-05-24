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

![Imagen proyecto de arquitectura_bus_evento](./vision_solucion.png)


## 3. Arquitectura de solución / Arquitectura Lógica ##

![Imagen proyecto de arquitectura_bus_evento](./arquitectura_event_driven.png)


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

**4.1.3. Operacion:**

integracion-camel: Proyecto integracionCamel
Ingresar url http://localhost:8080/api/credit/request
![Imagen proyecto de arquitectura_bus_evento](./soap-ui-new.PNG)


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
