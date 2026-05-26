-- ============================================================
--  Script de creación de la base de datos
--  GestorCitasClientes
--
--  Ejecuta este archivo en MySQL Workbench o en cualquier
--  cliente MySQL antes de arrancar la aplicación.
-- ============================================================

CREATE DATABASE IF NOT EXISTS tienda_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tienda_db;

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    dni          VARCHAR(9)   NOT NULL UNIQUE,
    nombre       VARCHAR(100) NOT NULL,
    edad         INT,
    departamento VARCHAR(50)
);

-- Tabla de citas (relacionada con clientes)
-- ON DELETE CASCADE: al borrar un cliente se borran también sus citas
CREATE TABLE IF NOT EXISTS citas (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id  INT      NOT NULL,
    fecha       DATETIME NOT NULL,
    descripcion TEXT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);

-- Datos de ejemplo (opcional, elimina estas líneas si no los necesitas)
INSERT INTO clientes (dni, nombre, edad, departamento) VALUES
    ('12345678A', 'Ana García',    32, 'Ventas'),
    ('87654321B', 'Carlos López',  45, 'Soporte'),
    ('11223344C', 'María Sánchez', 28, 'Administración');

INSERT INTO citas (cliente_id, fecha, descripcion) VALUES
    (1, '2025-06-15 10:00:00', 'Revisión de contrato anual'),
    (1, '2025-07-20 12:30:00', 'Seguimiento pedido pendiente'),
    (2, '2025-06-18 09:00:00', 'Incidencia técnica equipo portátil');
