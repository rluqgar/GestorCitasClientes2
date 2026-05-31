# Sistema de Gestión de Citas y Clientes – Aplicación Java

Repositorio: https://github.com/rluqgar/GestorCitasClientes2

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

```
src/
├── model/       → Cliente.java, Cita.java
├── dao/         → ClienteDAO.java, CitaDAO.java
├── controller/  → ClienteController.java, CitaController.java
├── util/        → ConexionBD.java
├── app/         → Main.java, VentanaPrincipal.java
└── resources/   → img/logo.png

bd/
└── tienda_db.sql
```

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
