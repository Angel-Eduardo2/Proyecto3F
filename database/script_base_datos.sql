CREATE DATABASE IF NOT EXISTS locksecure;
USE locksecure;

CREATE TABLE IF NOT EXISTS franquicia (
    id_franquicia INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    direccion VARCHAR(150),
    telefono VARCHAR(20),
    correo_contacto VARCHAR(100),
    modo_operacion VARCHAR(50),
    fecha_alta VARCHAR(20),
    estado VARCHAR(20) NOT NULL,
    cuota_mensual DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS sede (
    id_sede INT PRIMARY KEY,
    id_franquicia INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(150),
    ciudad VARCHAR(50),
    capacidad_total_lockers INT NOT NULL,
    fecha_apertura VARCHAR(20),
    administrador_sede VARCHAR(100),
    CONSTRAINT fk_sede_franquicia FOREIGN KEY (id_franquicia) REFERENCES franquicia(id_franquicia) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT PRIMARY KEY,
    id_sede INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    fecha_registro VARCHAR(20),
    estado VARCHAR(20) NOT NULL,
    CONSTRAINT fk_usuario_sede FOREIGN KEY (id_sede) REFERENCES sede(id_sede) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS administrador (
    id_admin INT PRIMARY KEY,
    id_franquicia INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    fecha_ultimo_acceso VARCHAR(20),
    CONSTRAINT fk_admin_franquicia FOREIGN KEY (id_franquicia) REFERENCES franquicia(id_franquicia) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS locker (
    codigo_identificador VARCHAR(20) PRIMARY KEY,
    id_sede INT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_ultimo_mantenimiento VARCHAR(20),
    cantidad_veces_usado INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_locker_sede FOREIGN KEY (id_sede) REFERENCES sede(id_sede) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS sesion_de_uso (
    id_sesion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    codigo_identificador VARCHAR(20) NOT NULL,
    fecha_inicio VARCHAR(30) NOT NULL,
    fecha_fin VARCHAR(30),
    duracion_minutos INT DEFAULT 0,
    metodo_apertura VARCHAR(30),
    hubo_incidencias BOOLEAN DEFAULT FALSE,
    costo_total DOUBLE DEFAULT 0.0,
    CONSTRAINT fk_sesion_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_sesion_locker FOREIGN KEY (codigo_identificador) REFERENCES locker(codigo_identificador) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO franquicia (id_franquicia, nombre, tipo, direccion, telefono, correo_contacto, modo_operacion, fecha_alta, estado, cuota_mensual)
VALUES (1, 'Universidad Libre', 'academica', 'Calle 53 # 10-20', '3000000000', 'contacto@unilibre.edu.co', 'autogestionado', '2026-09-01', 'activo', 1000000.0);

INSERT INTO sede (id_sede, id_franquicia, nombre, direccion, ciudad, capacidad_total_lockers, fecha_apertura, administrador_sede)
VALUES (1, 1, 'Sede Principal Candelaria', 'Carrera 6 # 8-06', 'Bogota', 50, '2026-09-01', 'Carlos Diaz');

INSERT INTO usuario (id_usuario, id_sede, nombre, apellido, tipo, correo_electronico, telefono, fecha_registro, estado)
VALUES (1, 1, 'Ana', 'Lopez', 'estudiante', 'ana@mail.com', '3000000000', '2026-09-27', 'activo');

INSERT INTO usuario (id_usuario, id_sede, nombre, apellido, tipo, correo_electronico, telefono, fecha_registro, estado)
VALUES (2, 1, 'Juan', 'Perez', 'empleado', 'juan@mail.com', '3000000001', '2026-09-27', 'activo');

INSERT INTO usuario (id_usuario, id_sede, nombre, apellido, tipo, correo_electronico, telefono, fecha_registro, estado)
VALUES (3, 1, 'Luis', 'Gomez', 'visitante', 'luis@mail.com', '3000000002', '2026-09-27', 'activo');

INSERT INTO locker (codigo_identificador, id_sede, tipo, estado, fecha_ultimo_mantenimiento, cantidad_veces_usado)
VALUES ('L001', 1, 'estandar', 'disponible', '2026-09-01', 5);

INSERT INTO locker (codigo_identificador, id_sede, tipo, estado, fecha_ultimo_mantenimiento, cantidad_veces_usado)
VALUES ('L002', 1, 'refrigerado', 'averiado', '2026-09-05', 10);

INSERT INTO locker (codigo_identificador, id_sede, tipo, estado, fecha_ultimo_mantenimiento, cantidad_veces_usado)
VALUES ('L003', 1, 'grande', 'disponible', '2026-09-10', 2);
