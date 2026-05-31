# GestorCitasClientes

**Aplicación de escritorio en Java para la gestión de clientes y citas de una tienda.**  
Interfaz gráfica con Swing, base de datos MySQL y arquitectura MVC + DAO.

![Java](https://img.shields.io/badge/Java-17_LTS-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/Conexión-JDBC-lightgrey?style=flat-square)
![Arquitectura](https://img.shields.io/badge/Arquitectura-MVC+DAO-2ea44f?style=flat-square)
![Estado](https://img.shields.io/badge/Estado-Completado-success?style=flat-square)

---

## ¿Qué hace esta aplicación?

Permite a cualquier empleado de la tienda gestionar desde una sola ventana:

- El registro completo de clientes (añadir, consultar, editar y eliminar)
- Las citas asociadas a cada cliente con fecha y descripción
- Todo conectado en tiempo real a una base de datos MySQL local

Al arrancar comprueba automáticamente si MySQL está disponible. Si no lo está, muestra un aviso antes de cerrar.

---

## Puesta en marcha

**Requisitos previos:**  
Java JDK 17 · MySQL 8.0 · IntelliJ IDEA o Eclipse · mysql-connector-j 8.x

**Pasos:**

1. Clona el repositorio
2. Ejecuta `bd/tienda_db.sql` en MySQL (crea las tablas e inserta datos de ejemplo)
3. Descarga `mysql-connector-j-9.x.x.jar` desde https://dev.mysql.com/downloads/connector/j/ eligiendo **Platform Independent**, crea una carpeta `lib/` en la raíz del proyecto y copia el `.jar` dentro. Luego en IntelliJ: `File → Project Structure → Modules → Dependencies → + → JARs or Directories` y selecciona el `.jar`
4. Abre `src/util/ConexionBD.java` y pon tu usuario y contraseña de MySQL
5. En IntelliJ, haz clic derecho sobre la carpeta `src/resources` → `Mark Directory As → Resources Root`
6. Ejecuta `src/app/Main.java`

> Para una guía detallada paso a paso consulta la [Wiki del proyecto](../../wiki/Instalación)

---

## Estructura

| Paquete | Contenido |
|---|---|
| `model` | `Cliente.java` · `Cita.java` |
| `dao` | `ClienteDAO.java` · `CitaDAO.java` |
| `controller` | `ClienteController.java` · `CitaController.java` |
| `util` | `ConexionBD.java` |
| `app` | `Main.java` · `VentanaPrincipal.java` |
| `resources/img` | `logo.png` |
| `bd` | `tienda_db.sql` |

---

## Documentación completa

| Sección | Enlace |
|---|---|
| Instalación paso a paso | [Wiki → Instalación](../../wiki/Instalación) |
| Arquitectura y patrones | [Wiki → Arquitectura](../../wiki/Arquitectura) |
| Esquema de base de datos | [Wiki → Base de Datos](../../wiki/Base-de-Datos) |
| Manual de usuario | [Wiki → Manual de Usuario](../../wiki/Manual-de-Usuario) |

---

## Autor

Desarrollado por **rluqgar** · [github.com/rluqgar](https://github.com/rluqgar)
