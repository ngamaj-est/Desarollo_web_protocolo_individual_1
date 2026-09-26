package org.unicartagena.protocolo.enfermedades.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class enfermedadesCRUD {
    private Enfermedades enfermedad = new Enfermedades();
    private DbConnection db;

    public Enfermedades getEnfermedad() { return enfermedad; }
    public void setEnfermedad(Enfermedades enfermedad) { this.enfermedad = enfermedad; }

    public void agregarEnfermedad() throws Exception {
        validarEnfermedad(enfermedad);
        String sql = "INSERT INTO Enfermedades (nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, esContagiosa, esCubiertaPorPos, requiereIncapacidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            db = new DbConnection();
            PreparedStatement ps = db.createSentence(sql);
            ps.setString(1, enfermedad.getNombre());
            ps.setString(2, enfermedad.getNombreCientifico());
            ps.setString(3, enfermedad.getNivelGravedad());
            ps.setString(4, enfermedad.getSintomas());
            ps.setString(5, enfermedad.getMedicamentos());
            ps.setBoolean(6, enfermedad.isEsContagiosa());
            ps.setBoolean(7, enfermedad.isEsCubiertaPorPos());
            ps.setBoolean(8, enfermedad.isRequiereIncapacidad());
            db.update(ps);
        } catch (Exception e) {
            throw new Exception("Error al agregar la enfermedad: " + e.getMessage());
        } finally {
            if (db != null) db.disconnect();
        }
    }

    public static Enfermedades[] listarEnfermedades() throws Exception {
        DbConnection db = null;
        String sql = "SELECT * FROM Enfermedades ORDER BY id";

        try {
            db = new DbConnection();
            PreparedStatement ps = db.createSentence(sql);
            ResultSet rs = db.consult(ps);

            rs.last();
            int totalRows = rs.getRow();
            rs.beforeFirst();

            if (totalRows <= 0) return new Enfermedades[0];

            Enfermedades[] listado = new Enfermedades[totalRows];
            int i = 0;
            while (rs.next()) {
                Enfermedades e = new Enfermedades();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setNombreCientifico(rs.getString("nombreCientifico"));
                e.setNivelGravedad(rs.getString("nivelGravedad"));
                e.setSintomas(rs.getString("sintomas"));
                e.setMedicamentos(rs.getString("medicamentos"));
                e.setEsContagiosa(rs.getBoolean("esContagiosa"));
                e.setEsCubiertaPorPos(rs.getBoolean("esCubiertaPorPos"));
                e.setRequiereIncapacidad(rs.getBoolean("requiereIncapacidad"));
                listado[i] = e;
                i++;
            }
            return listado;
        } catch (Exception e) {
            throw new Exception("Error al listar enfermedades: " + e.getMessage());
        } finally {
            if (db != null) db.disconnect();
        }
    }

    public Enfermedades buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM Enfermedades WHERE id = ?";
        try {
            db = new DbConnection();
            PreparedStatement ps = db.createSentence(sql);
            ps.setInt(1, id);
            ResultSet rs = db.consult(ps);

            if (rs.next()) {
                Enfermedades e = new Enfermedades();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setNombreCientifico(rs.getString("nombreCientifico"));
                e.setNivelGravedad(rs.getString("nivelGravedad"));
                e.setSintomas(rs.getString("sintomas"));
                e.setMedicamentos(rs.getString("medicamentos"));
                e.setEsContagiosa(rs.getBoolean("esContagiosa"));
                e.setEsCubiertaPorPos(rs.getBoolean("esCubiertaPorPos"));
                e.setRequiereIncapacidad(rs.getBoolean("requiereIncapacidad"));
                return e;
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Error al buscar la enfermedad: " + e.getMessage());
        } finally {
            if (db != null) db.disconnect();
        }
    }

    public void actualizarEnfermedad(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El id de la enfermedad es obligatorio");
        }
        validarEnfermedad(enfermedad);

        String sql = "UPDATE Enfermedades SET nombre = ?, nombreCientifico = ?, nivelGravedad = ?, sintomas = ?, medicamentos = ?, esContagiosa = ?, esCubiertaPorPos = ?, requiereIncapacidad = ? WHERE id = ?";
        try {
            db = new DbConnection();
            PreparedStatement ps = db.createSentence(sql);
            ps.setString(1, enfermedad.getNombre());
            ps.setString(2, enfermedad.getNombreCientifico());
            ps.setString(3, enfermedad.getNivelGravedad());
            ps.setString(4, enfermedad.getSintomas());
            ps.setString(5, enfermedad.getMedicamentos());
            ps.setBoolean(6, enfermedad.isEsContagiosa());
            ps.setBoolean(7, enfermedad.isEsCubiertaPorPos());
            ps.setBoolean(8, enfermedad.isRequiereIncapacidad());
            ps.setInt(9, id);
            db.update(ps);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la enfermedad: " + e.getMessage());
        } finally {
            if (db != null) db.disconnect();
        }
    }

    public void eliminarEnfermedad(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El id de la enfermedad es obligatorio");
        }

        String sql = "DELETE FROM Enfermedades WHERE id = ?";
        try {
            db = new DbConnection();
            PreparedStatement ps = db.createSentence(sql);
            ps.setInt(1, id);
            db.update(ps);
        } catch (Exception e) {
            throw new Exception("Error al eliminar la enfermedad: " + e.getMessage());
        } finally {
            if (db != null) db.disconnect();
        }
    }

    private void validarEnfermedad(Enfermedades e) throws Exception {
        if (e == null) {
            throw new Exception("La enfermedad no puede ser null");
        }
        if (e.getNombre() == null || e.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre de la enfermedad es obligatorio");
        }
        if (e.getNombreCientifico() == null || e.getNombreCientifico().trim().isEmpty()) {
            throw new Exception("El nombre científico es obligatorio");
        }
        if (e.getNivelGravedad() == null || e.getNivelGravedad().trim().isEmpty()) {
            throw new Exception("El nivel de gravedad es obligatorio");
        }
        if (e.getSintomas() == null || e.getSintomas().trim().isEmpty()) {
            throw new Exception("Los síntomas son obligatorios");
        }
        if (e.getMedicamentos() == null || e.getMedicamentos().trim().isEmpty()) {
            throw new Exception("Los medicamentos son obligatorios");
        }
    }
}
