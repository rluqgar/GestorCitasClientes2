
# Sistema de Gestión de Citas y Clientes – Aplicación Java

Repositorio: https://github.com/rluqgar/GestorCitasClientes

---

## 1. Descripción del Proyecto

Esta aplicación ha sido desarrollada en **Java** con interfaz gráfica **Swing** y tiene como objetivo gestionar la base de datos de una tienda.
El sistema permite realizar operaciones de administración sobre **clientes y citas** desde una ventana con dos pestañas.

La aplicación implementa operaciones **CRUD (Create, Read, Update, Delete)** que permiten:

- Añadir, consultar, editar y eliminar clientes
- Gestionar citas asociadas a cada cliente
- Visualizar toda la información en tablas interactivas

Al arrancar muestra una barra superior con el logo de la empresa. Si se produce un error de conexión con la base de datos, se informa al usuario antes de abrir la ventana principal.

---

## 2. Arquitectura del Proyecto

El proyecto está organizado siguiendo el patrón de diseño **MVC (Model – View – Controller)** combinado con el patrón **DAO (Data Access Object)**.

### Model
Representa las entidades del sistema: `Cliente` y `Cita`. Son clases POJO puras sin lógica de negocio ni dependencias externas.

### Controller
Contiene las validaciones y reglas de negocio. Actúa de intermediario entre la interfaz gráfica y el DAO. Ninguna consulta SQL se escribe aquí.

### DAO (Data Access Object)
Centraliza todas las operaciones contra MySQL. Usa `PreparedStatement` en todas las consultas para prevenir inyección SQL.

### Util
Contiene `ConexionBD.java`, que implementa el patrón **Singleton** para mantener una única conexión JDBC activa durante toda la ejecución.

### App
Contiene `Main.java` (punto de entrada) y `VentanaPrincipal.java` (interfaz Swing con pestañas de clientes y citas).

---

## 3. Estructura de Carpetas

```
GestorCitasClientes/
│
├── src/
│   ├── model/
│   │   ├── Cliente.java
│   │   └── Cita.java
│   │
│   ├── controller/
│   │   ├── ClienteController.java
│   │   └── CitaController.java
│   │
│   ├── dao/
│   │   ├── ClienteDAO.java
│   │   └── CitaDAO.java
│   │
│   ├── util/
│   │   └── ConexionBD.java
│   │
│   ├── app/
│   │   ├── Main.java
│   │   └── VentanaPrincipal.java
│   │
│   └── resources/
│       └── img/
│           └── logo.png
│
├── bd/
│   └── tienda_db.sql
│
└── .gitignore
```

---

## 4. Guía de Instalación

### Requisitos del sistema

- **Java JDK 17 o superior** — https://adoptium.net/
- **MySQL Server 8.0 o superior** — https://dev.mysql.com/downloads/mysql/
- **MySQL Workbench 8.0** (opcional, para gestionar la BD visualmente)
- Un entorno de desarrollo como **IntelliJ IDEA** o **Eclipse**
- El conector **mysql-connector-j 8.x** añadido manualmente como librería externa

> Este proyecto no usa Maven ni Gradle. El archivo `.jar` del conector JDBC debe añadirse a mano en el IDE.

### Paso 1 – Clonar el repositorio

```bash
git clone https://github.com/rluqgar/GestorCitasClientes.git
cd GestorCitasClientes
```

### Paso 2 – Crear la base de datos

Abre MySQL Workbench y ejecuta el script incluido en el proyecto:

```
bd/tienda_db.sql
```

O cópialo y ejecútalo directamente en el cliente MySQL. El script crea la base de datos, las tablas e inserta algunos datos de ejemplo.

### Paso 3 – Añadir el conector JDBC

1. Descarga `mysql-connector-j-8.x.x.jar` desde https://dev.mysql.com/downloads/connector/j/
2. Crea una carpeta `lib/` en la raíz del proyecto y copia el `.jar` dentro.
3. En **IntelliJ IDEA**: `File → Project Structure → Modules → Dependencies → + → JARs or Directories`
4. En **Eclipse**: botón derecho sobre el proyecto → `Build Path → Add External Archives`

### Paso 4 – Configurar la conexión

Abre `src/util/ConexionBD.java` y edita estas tres líneas con tus datos de MySQL:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/tienda_db?useSSL=false&serverTimezone=UTC";
private static final String USUARIO  = "root";        // tu usuario de MySQL
private static final String PASSWORD = "";            // tu contraseña de MySQL
```

### Paso 5 – Marcar resources como Resources Root

Para que el logo cargue correctamente en tiempo de ejecución:

- En **IntelliJ IDEA**: botón derecho sobre la carpeta `src/resources` → `Mark Directory As → Resources Root`
- En **Eclipse**: la carpeta `src` ya actúa como raíz de recursos por defecto

### Paso 6 – Ejecutar el proyecto

Desde el IDE: abre `src/app/Main.java` y pulsa **Run**.

Desde la terminal en Windows:

```bash
javac -cp ".;lib/mysql-connector-j-8.x.x.jar" src/**/*.java -d out/
java  -cp "out;lib/mysql-connector-j-8.x.x.jar" app.Main
```

Desde la terminal en Linux / macOS:

```bash
javac -cp ".:lib/mysql-connector-j-8.x.x.jar" src/**/*.java -d out/
java  -cp "out:lib/mysql-connector-j-8.x.x.jar" app.Main
```

---

## 5. Variables de Entorno

No se utilizan variables de entorno del sistema operativo. Toda la configuración se centraliza en `src/util/ConexionBD.java`:

| Constante | Valor por defecto | Descripción |
|---|---|---|
| `URL` | `localhost:3306/tienda_db` | Host, puerto y nombre de la base de datos |
| `USUARIO` | `root` | Usuario de MySQL |
| `PASSWORD` | *(vacío)* | Contraseña de MySQL |

---

## 6. Diagrama de la Base de Datos

**Tabla `clientes`**

| Campo | Tipo | Restricción |
|---|---|---|
| id | INT | PK, AUTO_INCREMENT |
| dni | VARCHAR(9) | NOT NULL, UNIQUE |
| nombre | VARCHAR(100) | NOT NULL |
| edad | INT | — |
| departamento | VARCHAR(50) | — |

**Tabla `citas`**

| Campo | Tipo | Restricción |
|---|---|---|
| id | INT | PK, AUTO_INCREMENT |
| cliente_id | INT | FK → clientes(id) |
| fecha | DATETIME | NOT NULL |
| descripcion | TEXT | — |

**Relación:** un `Cliente` puede tener muchas `Citas` (1:N).
Al eliminar un cliente, sus citas se borran automáticamente (`ON DELETE CASCADE`).

---

## 7. Manual de Usuario

Al abrir la aplicación se muestra una barra azul con el logo de la empresa y dos pestañas: **Clientes** y **Citas**.

### Pestaña Clientes

| Acción | Cómo hacerlo |
|---|---|
| Añadir cliente | Rellena el formulario inferior y pulsa **Añadir** |
| Editar cliente | Haz clic en una fila de la tabla, modifica los campos y pulsa **Editar** |
| Eliminar cliente | Selecciona una fila y pulsa **Eliminar** (pide confirmación) |
| Limpiar formulario | Pulsa **Limpiar** para deseleccionar y vaciar los campos |

### Pestaña Citas

| Acción | Cómo hacerlo |
|---|---|
| Añadir cita | Selecciona un cliente del desplegable, introduce la fecha en formato `dd/MM/yyyy HH:mm` y una descripción, luego pulsa **Añadir** |
| Editar cita | Haz clic en una fila, modifica los campos y pulsa **Editar** |
| Eliminar cita | Selecciona una fila y pulsa **Eliminar** |

> El sistema no permite DNIs duplicados. Si introduces un DNI ya registrado al crear un cliente, se mostrará un aviso.

> Si MySQL no está en marcha al abrir la aplicación, aparecerá un mensaje de error indicando qué revisar y la aplicación se cerrará sin abrir la ventana principal.

---

## 8. Cómo Cambiar el Logo

El logo se encuentra en:

```
src/resources/img/logo.png
```

### Método rápido

1. Prepara tu imagen en formato `.png` (se recomienda fondo transparente).
2. Renómbrala `logo.png`.
3. Cópiala en `src/resources/img/` sobreescribiendo la anterior.
4. Reconstruye el proyecto: en IntelliJ `Build → Rebuild Project`, en Eclipse `Project → Clean`.

### Cambiar el nombre o la ruta en el código

Localiza en `VentanaPrincipal.java` el método `construirBarraSuperior()` y edita esta línea:

```java
// Antes
java.net.URL urlLogo = getClass().getResource("/resources/img/logo.png");

// Después (con tu nueva ruta)
java.net.URL urlLogo = getClass().getResource("/resources/img/mi_logo.png");
```

> En IntelliJ IDEA, asegúrate de que `src/resources` esté marcado como **Resources Root**, de lo contrario el logo no se encontrará al ejecutar.

---

## 9. Guía para Nuevos Desarrolladores

### Cómo añadir una nueva entidad (ejemplo: `Servicio`)

1. Crea `src/model/Servicio.java` con sus atributos, constructor y getters/setters.
2. Añade el `CREATE TABLE servicios (...)` al script `bd/tienda_db.sql`.
3. Crea `src/dao/ServicioDAO.java` con los métodos `insertar`, `listar`, `buscarPorId`, `actualizar` y `eliminar`, siguiendo el patrón de `ClienteDAO.java`.
4. Crea `src/controller/ServicioController.java` con las validaciones necesarias.
5. Añade una nueva pestaña en `VentanaPrincipal.java` siguiendo el patrón de `construirPestanaClientes()`.

### Convenciones de código

- `camelCase` para variables y métodos, `PascalCase` para clases.
- Documenta los métodos públicos con Javadoc (`/** ... */`).
- Usa siempre `PreparedStatement` para las consultas SQL.
- Cierra `Connection`, `Statement` y `ResultSet` con try-with-resources.
- No escribas lógica SQL en los Controllers ni lógica de validación en los DAOs.

---

## 10. Historial de Versiones

### v2.0.0 – 2026-05-26

- Interfaz gráfica completa con Java Swing y dos pestañas (Clientes y Citas)
- Barra superior con logo de empresa cargado desde `resources/img/logo.png`
- Conexión a MySQL mediante JDBC con patrón Singleton en `ConexionBD`
- Implementación completa de CRUD para clientes y citas con validaciones
- Comprobación de conexión a BD al arrancar con mensaje de error descriptivo
- Script SQL incluido en `bd/tienda_db.sql` con datos de ejemplo

### v1.0.0 – 2026-03-10

- Implementación inicial del modelo `Empleado` con atributos `dni`, `nombre`, `edad` y `departamento`
- `EmpleadoService` con métodos `addEmpleado` y `listarEmpleados` usando `ArrayList` en memoria
- Estructura base del proyecto con capas `model`, `service` y `util`

---

## Autor

**rluqgar**
https://github.com/rluqgar/GestorCitasClientes
