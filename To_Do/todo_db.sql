-- Crear base de datos
DROP DATABASE IF EXISTS todo_db;
CREATE DATABASE IF NOT EXISTS todo_db;

USE todo_db;

-- Crear tabla Task
CREATE TABLE IF NOT EXISTS task (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    due_date DATE,
    PRIMARY KEY (id)
);