IF DB_ID('GestionEnfermedades_db') IS NULL
BEGIN
    CREATE DATABASE GestionEnfermedades_db;
END
GO

USE GestionEnfermedades_db;
GO

IF OBJECT_ID('dbo.Usuarios', 'U') IS NOT NULL DROP TABLE dbo.Usuarios;
GO

CREATE TABLE Usuarios (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30)  NOT NULL CHECK (role IN ('Administrador','Medico','Usuario'))
);

IF OBJECT_ID('dbo.Enfermedades', 'U') IS NOT NULL DROP TABLE dbo.Enfermedades;
GO

CREATE TABLE Enfermedades (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    nombreCientifico VARCHAR(100) NOT NULL,
    nivelGravedad VARCHAR(20)  NOT NULL CHECK (nivelGravedad IN('Leve', 'Moderada', 'Grave', 'Critica')),
    sintomas VARCHAR(500) NOT NULL,
    medicamentos VARCHAR(200) NOT NULL,
    esContagiosa BIT NOT NULL DEFAULT 0,
    esCubiertaPorPos BIT NOT NULL DEFAULT 0,
    requiereIncapacidad BIT NOT NULL DEFAULT 0
);
GO

INSERT INTO Usuarios (name, email, password, role) VALUES
('Nathan Gama', N'nathan.gama@admin.com', 'admin', 'Administrador'),
('Antonio Lopez', N'antonio.lopez@medico.com', 'medico123', 'Medico');
GO

INSERT INTO Enfermedades
    (nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, esContagiosa, esCubiertaPorPos, requiereIncapacidad)
VALUES
(N'Gripe común', N'Influenza', N'Leve',N'Fiebre, tos, congestión nasal, dolor de garganta',N'Paracetamol, Loratadina', 1, 1, 0),
(N'COVID-19', N'SARS-CoV-2', N'Grave',N'Fiebre, tos seca, dificultad respiratoria, anosmia', N'Paracetamol, Oxígeno suplementario', 1, 1, 1),
(N'Varicela', N'Varicella zoster', N'Moderada', N'Erupción cutánea, picazón, fiebre', N'Aciclovir, Antihistamínicos', 1, 1, 1),
(N'Hipertensión arterial', N'Hipertensión esencial',   N'Moderada', N'Dolor de cabeza, mareo, zumbido en los oídos', N'Losartán, Enalapril',                0, 1, 0),
(N'Diabetes tipo 2', N'Diabetes mellitus tipo 2', N'Grave',   N'Sed excesiva, fatiga, visión borrosa', N'Metformina, Insulina', 0, 1, 1),
(N'Resfriado común', N'Rhinovirus', N'Leve', N'Estornudos, congestión nasal, malestar leve', N'Descongestionante, Vitamina C', 1, 0, 0);
GO
