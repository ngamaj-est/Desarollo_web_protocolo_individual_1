CREATE DATABASE IF NOT EXISTS GestionEnfermedades_db;
USE GestionEnfermedades_db;

DROP TABLE IF EXISTS Usuarios;

CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL CHECK (role IN ('Administrador','Medico','Usuario'))
);

DROP TABLE IF EXISTS Enfermedades;

CREATE TABLE Enfermedades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    nombreCientifico VARCHAR(100) NOT NULL,
    nivelGravedad VARCHAR(20) NOT NULL CHECK (nivelGravedad IN('Leve', 'Moderada', 'Grave', 'Critica')),
    sintomas VARCHAR(500) NOT NULL,
    medicamentos VARCHAR(200) NOT NULL,
    esContagiosa TINYINT(1) NOT NULL DEFAULT 0,
    esCubiertaPorPos TINYINT(1) NOT NULL DEFAULT 0,
    requiereIncapacidad TINYINT(1) NOT NULL DEFAULT 0
);

INSERT INTO Usuarios (name, email, password, role) VALUES
('Nathan Gama', 'nathan.gama@admin.com', 'admin', 'Administrador'),
('Antonio Lopez', 'antonio.lopez@medico.com', 'medico123', 'Medico');

INSERT INTO Enfermedades
    (nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, esContagiosa, esCubiertaPorPos, requiereIncapacidad)
VALUES
('Gripe común', 'Influenza', 'Leve', 'Fiebre, tos, congestión nasal, dolor de garganta', 'Paracetamol, Loratadina', 1, 1, 0),
('COVID-19', 'SARS-CoV-2', 'Grave', 'Fiebre, tos seca, dificultad respiratoria, anosmia', 'Paracetamol, Oxígeno suplementario', 1, 1, 1),
('Varicela', 'Varicella zoster', 'Moderada', 'Erupción cutánea, picazón, fiebre', 'Aciclovir, Antihistamínicos', 1, 1, 1),
('Hipertensión arterial', 'Hipertensión esencial', 'Moderada', 'Dolor de cabeza, mareo, zumbido en los oídos', 'Losartán, Enalapril', 0, 1, 0),
('Diabetes tipo 2', 'Diabetes mellitus tipo 2', 'Grave', 'Sed excesiva, fatiga, visión borrosa', 'Metformina, Insulina', 0, 1, 1),
('Resfriado común', 'Rhinovirus', 'Leve', 'Estornudos, congestión nasal, malestar leve', 'Descongestionante, Vitamina C', 1, 0, 0);