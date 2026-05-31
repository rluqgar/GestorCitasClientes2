#  Sistema de Gestión de Citas y Clientes

> Aplicación de escritorio en Java con interfaz gráfica Swing para gestionar clientes y citas de una tienda, conectada a MySQL mediante JDBC.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Swing](https://img.shields.io/badge/Interfaz-Swing-lightgrey)
![Patrón](https://img.shields.io/badge/Patrón-MVC+DAO-green)
![Licencia](https://img.shields.io/badge/Licencia-Privada-red)

---

## Descripción

Aplicación de escritorio desarrollada en **Java** con interfaz gráfica **Swing** para gestionar clientes y citas de una tienda.

Implementa operaciones **CRUD** completas conectadas a una base de datos **MySQL** mediante JDBC, siguiendo el patrón de diseño **MVC + DAO**.

Al arrancar muestra una barra superior con el logo de la empresa. Si MySQL no está disponible, informa al usuario antes de abrir la ventana.

---

## Tecnologías

| Herramienta | Versión |
|---|---|
| Java JDK | 17 LTS |
| Java Swing | — |
| MySQL Server | 8.0 |
| mysql-connector-j | 8.x |

---

## Instalación rápida

1. Clona el repositorio
2. Ejecuta `bd/tienda_db.sql` en MySQL
3. Añade el conector JDBC como librería externa en tu IDE
4. Edita `src/util/ConexionBD.java` con tu usuario y contraseña de MySQL
5. Ejecuta `src/app/Main.java`

> Documentación completa en la [Wiki del proyecto](../../wiki)

---

## Estructura del proyecto

| Carpeta / Archivo | Descripción |
|---|---|
| `src/model/` | Entidades de dominio: Cliente.java, Cita.java |
| `src/dao/` | Acceso a base de datos: ClienteDAO.java, CitaDAO.java |
| `src/controller/` | Lógica de negocio: ClienteController.java, CitaController.java |
| `src/util/` | Conexión JDBC: ConexionBD.java |
| `src/app/` | Interfaz y arranque: Main.java, VentanaPrincipal.java |
| `src/resources/img/` | Logo de la aplicación: logo.png |
| `bd/` | Script SQL: tienda_db.sql |

---

## Documentación

Toda la documentación detallada está disponible en la **[Wiki del proyecto](../../wiki)**:

- [Instalación paso a paso](../../wiki/Instalación)
- [Arquitectura y patrones de diseño](../../wiki/Arquitectura)
- [Base de datos](../../wiki/Base-de-Datos)
- [Manual de usuario](../../wiki/Manual-de-Usuario)

---

## Autor

**rluqgar**
https://github.com/rluqgar/GestorCitasClientes2
