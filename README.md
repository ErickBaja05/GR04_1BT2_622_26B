# Aplicación Web Java EE: Arquitectura MVC con JSP, Servlets y Hibernate

Este repositorio tiene como finalidad académica el aprendizaje y la consolidación de conceptos fundamentales para la construcción de aplicaciones web en **Java**, implementando y comprendiendo el patrón de diseño **Modelo-Vista-Controlador (MVC)** y la integración de persistencia con **ORM**.

---

## Propósito del Proyecto

El objetivo central es dominar el flujo de datos y la separación de responsabilidades dentro de una arquitectura multicapa en Java EE:

* **Controlador (Servlets):** Se encargan de interceptar las peticiones HTTP (`GET`, `POST`), procesar parámetros de entrada, invocar la lógica de negocio/servicios correspondiente y despachar el flujo hacia la vista adecuada.
* **Vista (JSP - JavaServer Pages):** Responsable exclusiva de la presentación visual y la interacción con el usuario, renderizando dinámicamente los datos proporcionados por el controlador mediante JSTL / Expression Language (EL).
* **Modelo y Persistencia (ORM con Hibernate):** Representa las entidades del dominio y encapsula el acceso a la base de datos relacional. Mediante Hibernate, se mapean las clases Java a tablas SQL, gestionando transacciones y consultas de forma orientada a objetos sin acoplamiento a SQL nativo.

> 💡 **Nota sobre el código:** A lo largo de las distintas capas del proyecto (controladores, vistas, entidades y clases de configuración) se han incluido **comentarios explicativos** detallados que sustentan las decisiones de diseño tomadas y demuestran el entendimiento y cumplimiento de cada componente en la arquitectura.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java
* **Gestor de dependencias / construcción:** Apache Maven
* **Capa Web:** Java Servlets & JSP
* **Capa de Persistencia:** ORM (Hibernate)
* **Servidor de Aplicaciones:** Apache Tomcat

---

## 👥 Desarrolladores

* Erick Bajaña
* Anderson Pilataxi
* Franciel Tipantuña
* Andrés Veas
* Sabina Zabala

