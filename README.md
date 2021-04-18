# SongCloud

## Autores:
- Diego Avila Morales
- Manuel Martin Rico
- Miguel Ángel Quiñones Ramírez
- Carlos Alejandro Pool Quintal

## Documento de Arquitectura de Software

## Índice
- [SongCloud](#songcloud)
  - [Autores:](#autores)
  - [Documento de Arquitectura de Software](#documento-de-arquitectura-de-software)
  - [Índice](#índice)
  - [Introducción](#introducción)
    - [Propósito](#propósito)
    - [Alcance](#alcance)
    - [Documentos de referencia](#documentos-de-referencia)
  - [Arquitectura](#arquitectura)
    - [Descripción de la arquitectura utilizada (Capas)](#descripción-de-la-arquitectura-utilizada-capas)
    - [Definición teórica de Microservicios](#definición-teórica-de-microservicios)
    - [Diagrama de arquitectura con descripción](#diagrama-de-arquitectura-con-descripción)
    - [Diagrama de secuencia para los procesos descritos en la descripción de la App 1 y la App 2](#diagrama-de-secuencia-para-los-procesos-descritos-en-la-descripción-de-la-app-1-y-la-app-2)
    - [Diagrama de la base de datos](#diagrama-de-la-base-de-datos)
    - [Descripción de las entidades](#descripción-de-las-entidades)
    - [Diagrama entidad-relación](#diagrama-entidad-relación)
  - [Documentación de la API (Aquí le entra el POSTMAN)](#documentación-de-la-api-aquí-le-entra-el-postman)
  - [Criterios de calidad](#criterios-de-calidad)

---

## Introducción

### Propósito

>  Un propósito

### Alcance

>  Un alcance

### Documentos de referencia

>  Documentos de referencia
>  Utilice este enlace para saber más sobre microservicios https://microservices.io/ 
>  Opción alterna https://www.redhat.com/es/topics/microservices/what-are-microservices

---

## Arquitectura

### Descripción de la arquitectura utilizada (Capas)

La arquitectura basada en capas se enfoca en la distribución de roles y responsabilidades de forma jerárquica proveyendo una forma muy efectiva de separación de responsabilidades. El rol indica el modo y tipo de interacción con otras capas, y la responsabilidad indica la funcionalidad que está siendo desarrollada.

### Definición teórica de Microservicios

Los microservicios se tratan de un estilo arquitectónico mediante el cual una aplicación se estructura en función a una colección de **servicios** que deben ser:
* Altamente mantenibles y probables
* Débilmente acoplados
* Independientes en su despliegue
* Organizados de acuerdo a las capacidades de la empresa
* Liderados por un equipo de desarrollo pequeño

Esto quiere decir que permite un despliegue rápido, frecuente y confiable de aplicaciones grandes y complejas. Es un elemento fundamental de la optimización del desarrollo de aplicaciones hacia un modelo nativo de la **nube**.

![alt text](mdimages/microservicios.png "Arquitectura de Microservicios")

Cada función se denomina servicio y se puede diseñar e implementar de forma independiente. Es más, desarrollar utilizando este estilo arquitectónico provee beneficios como:
* Aplicaciones más rápidas para comercializarse
* Gran capacidad de expansión
* Capacidad de recuperación 
* Facilidad de implementación
* Accesibilidad para los desarrolladores
* Aplicaciones más abiertas

### Diagrama de arquitectura con descripción

Este diagrama puede encontrarse en la carpeta del proyecto, la cual se encuenta enlazada en el Plan de Mantenimiento.

### Diagrama de secuencia para los procesos descritos en la descripción de la App 1 y la App 2

> Diagrama de secuencias

### Diagrama de la base de datos

![alt text](mdimages/StreamServiceAPI.png "Diagrama de la base de datos para SongCloud")

### Descripción de las entidades

Para una descripción más precisa de las entidades involucradas en la base de datos, se anexa a continuación el siguiente documento de [diseño de la base de datos](https://docs.google.com/document/d/1nUIwVgMMC-rgfiYJIb37Z4NiLlI71eAl7fttU49Pfq0/edit?usp=sharing)

### Diagrama entidad-relación

![alt text](mdimages/Diagrama%20ER.png "Diagrama entidad-relación para SongCloud")

---

## Documentación de la API

**Postman** es una herramienta que nos permite crear peticiones sobre APIs creando un cliente por nosotros para evitar la creación de los mismos y poder probar su funcionamiento sin tantas complicaciones. Postman permite almacenar un espacio de trabajo y peticiones personalizadas para facilitar la tarea de probar APIs.

---

## Criterios de calidad

> Para los criterios de calidad de acuerdo a la ISO/IEC

- Funcionalidad
- Portabilidad
- Mantenibilidad
- Eficiencia
- Usabilidad
- Confiabilidad