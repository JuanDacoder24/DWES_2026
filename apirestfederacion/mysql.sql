-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS examen_spring_dwes
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE examen_spring_dwes;

-- Eliminar tablas existentes (para empezar de cero)
DROP TABLE IF EXISTS partidos;
DROP TABLE IF EXISTS jugadores;
DROP TABLE IF EXISTS arbitros;
DROP TABLE IF EXISTS equipos;

-- Tabla EQUIPOS (cambiar a UUID para consistencia)
CREATE TABLE IF NOT EXISTS equipos (
  id CHAR(36) NOT NULL, -- UUID
  nombre_equipo VARCHAR(100) NOT NULL,
  sede VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Insertar equipos con UUIDs específicos
INSERT INTO equipos (id, nombre_equipo, sede) 
VALUES 
  (UUID(), 'Racing', 'Santander'),
  (UUID(), 'Real Madrid', 'Madrid'),
  (UUID(), 'Barcelona', 'Barcelona'),
  (UUID(), 'Alianza Lima', 'Lima');

-- Tabla JUGADORES (ahora con equipo_id CHAR(36))
CREATE TABLE IF NOT EXISTS jugadores (
  id CHAR(36) NOT NULL DEFAULT (UUID()), -- UUID automático
  dorsal INT NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  apellido1 VARCHAR(100) NOT NULL,
  apellido2 VARCHAR(100) NOT NULL,
  posicion ENUM('PORTERO', 'DEFENSA', 'MEDIO', 'DELANTERO') NOT NULL,
  equipo_id CHAR(36) NOT NULL, -- FK a equipos.id
  PRIMARY KEY (id),
  INDEX idx_jugadores_equipo (equipo_id),
  CONSTRAINT fk_jugadores_equipos
    FOREIGN KEY (equipo_id)
    REFERENCES equipos(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  UNIQUE KEY uk_jugadores_equipo_dorsal (equipo_id, dorsal)
) ENGINE=InnoDB;

-- Insertar jugadores (necesitas los UUIDs reales de los equipos)
-- Primero, obtén los IDs de los equipos:
SET @racing_id = (SELECT id FROM equipos WHERE nombre_equipo = 'Racing');
SET @real_madrid_id = (SELECT id FROM equipos WHERE nombre_equipo = 'Real Madrid');
SET @barcelona_id = (SELECT id FROM equipos WHERE nombre_equipo = 'Barcelona');
SET @alianza_id = (SELECT id FROM equipos WHERE nombre_equipo = 'Alianza Lima');

-- Ahora inserta los jugadores
INSERT INTO jugadores (id, dorsal, nombre, apellido1, apellido2, posicion, equipo_id) 
VALUES 
  (UUID(), 10, 'Sergio', 'Marin', 'Perez', 'DEFENSA', @racing_id),
  (UUID(), 5, 'Enrique', 'Sainz', 'Sainz', 'DEFENSA', @racing_id),
  (UUID(), 11, 'Juan', 'Gonzales', 'Noriega', 'MEDIO', @real_madrid_id),
  (UUID(), 19, 'Pedro', 'Salaverry', 'Guzman', 'DEFENSA', @real_madrid_id),
  (UUID(), 2, 'Manuel', 'Rejas', 'Pinto', 'DELANTERO', @barcelona_id),
  (UUID(), 15, 'Alex', 'Argumosa', 'Salazar', 'MEDIO', @barcelona_id),
  (UUID(), 4, 'Andres', 'Picanto', 'Kia', 'DEFENSA', @alianza_id),
  (UUID(), 12, 'Gonzalo', 'Servellon', 'Bastidas', 'DELANTERO', @alianza_id),
  (UUID(), 20, 'Felix', 'Quispe', 'Mamani', 'MEDIO', @alianza_id);

-- Tabla ÁRBITROS
CREATE TABLE IF NOT EXISTS arbitros (
  id CHAR(36) NOT NULL DEFAULT (UUID()), -- UUID automático
  nombre VARCHAR(100) NOT NULL,
  apellido1 VARCHAR(100) NOT NULL,
  apellido2 VARCHAR(100) NOT NULL,
  rol ENUM('PRINCIPAL', 'ASISTENTE') NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Insertar algunos árbitros de ejemplo
INSERT INTO arbitros (nombre, apellido1, apellido2, rol) VALUES
  ('Juan', 'García', 'López', 'PRINCIPAL'),
  ('Carlos', 'Martínez', 'Sánchez', 'ASISTENTE'),
  ('María', 'Rodríguez', 'Fernández', 'PRINCIPAL');

-- Tabla PARTIDOS
CREATE TABLE IF NOT EXISTS partidos (
  id CHAR(36) NOT NULL DEFAULT (UUID()), -- UUID automático
  equipo1_id CHAR(36) NOT NULL, -- FK a equipos.id
  equipo2_id CHAR(36) NOT NULL, -- FK a equipos.id
  arbitro1_id CHAR(36) NOT NULL, -- FK a arbitros.id
  arbitro2_id CHAR(36) NOT NULL, -- FK a arbitros.id
  fecha_partido DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX idx_partidos_equipo1 (equipo1_id),
  INDEX idx_partidos_equipo2 (equipo2_id),
  INDEX idx_partidos_arbitro1 (arbitro1_id),
  INDEX idx_partidos_arbitro2 (arbitro2_id),
  CONSTRAINT fk_partidos_equipo1
    FOREIGN KEY (equipo1_id)
    REFERENCES equipos(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  CONSTRAINT fk_partidos_equipo2
    FOREIGN KEY (equipo2_id)
    REFERENCES equipos(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  CONSTRAINT fk_partidos_arbitro1
    FOREIGN KEY (arbitro1_id)
    REFERENCES arbitros(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  CONSTRAINT fk_partidos_arbitro2
    FOREIGN KEY (arbitro2_id)
    REFERENCES arbitros(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB;