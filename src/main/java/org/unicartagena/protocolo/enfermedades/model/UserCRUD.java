package org.unicartagena.protocolo.enfermedades.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserCRUD {

    public static User Login(String email, String password) throws Exception {
        return login(email, password);
    }

    public static User login(String email, String password) throws Exception {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new Exception("El email y la contraseña son obligatorios");
        }

        DbConnection baseDatos = null;
        String sql = "SELECT * FROM Usuarios WHERE email = ? AND password = ?";

        try {
            baseDatos = new DbConnection();
            PreparedStatement ps = baseDatos.getConnection().prepareStatement(sql);
            ps.setString(1, email.trim());
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

            throw new Exception("Credenciales inválidas");
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Credenciales inválidas")) {
                throw e;
            }
            throw new Exception("Error en inicio de sesión: " + e.getMessage(), e);
        } finally {
            if (baseDatos != null) {
                baseDatos.disconnect();
            }
        }
    }

    public static User getUserById(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El id del usuario es obligatorio");
        }

        DbConnection baseDatos = null;
        String sql = "SELECT * FROM Usuarios WHERE id = ?";

        try {
            baseDatos = new DbConnection();
            PreparedStatement ps = baseDatos.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

            throw new Exception("Usuario no encontrado");
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Usuario no encontrado")) {
                throw e;
            }
            throw new Exception("Error consultando usuario por id: " + e.getMessage(), e);
        } finally {
            if (baseDatos != null) {
                baseDatos.disconnect();
            }
        }
    }

    public static User getUserByEmail(String email) throws Exception {
        if (email == null || email.trim().isEmpty()) {
            throw new Exception("El email es obligatorio");
        }

        DbConnection baseDatos = null;
        String sql = "SELECT * FROM Usuarios WHERE email = ?";

        try {
            baseDatos = new DbConnection();
            PreparedStatement ps = baseDatos.getConnection().prepareStatement(sql);
            ps.setString(1, email.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

            throw new Exception("Usuario no encontrado");
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Usuario no encontrado")) {
                throw e;
            }
            throw new Exception("Error consultando usuario por email: " + e.getMessage(), e);
        } finally {
            if (baseDatos != null) {
                baseDatos.disconnect();
            }
        }
    }

    public static List<User> getAllUsers() throws Exception {
        DbConnection baseDatos = null;
        List<User> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios ORDER BY id ASC";

        try {
            baseDatos = new DbConnection();
            PreparedStatement ps = baseDatos.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;
        } catch (Exception e) {
            throw new Exception("Error consultando usuarios: " + e.getMessage(), e);
        } finally {
            if (baseDatos != null) {
                baseDatos.disconnect();
            }
        }
    }

    public static boolean createUser(User usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("El usuario es obligatorio");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new Exception("El email es obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            throw new Exception("La contraseña es obligatoria");
        }

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }

        DbConnection baseDatos = null;
        String sql = "INSERT INTO Usuarios (name, email, password, role) VALUES (?, ?, ?, ?)";

        try {
            baseDatos = new DbConnection();
            PreparedStatement ps = baseDatos.getConnection().prepareStatement(sql);
            ps.setString(1, usuario.getNombre().trim());
            ps.setString(2, usuario.getEmail().trim());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRole() != null ? usuario.getRole() : "usuario");

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            throw new Exception("Error creando usuario: " + e.getMessage(), e);
        } finally {
            if (baseDatos != null) {
                baseDatos.disconnect();
            }
        }
    }

    public static boolean updateUser(User usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("El usuario es obligatorio");
        }

        if (usuario.getId() == null || usuario.getId().trim().isEmpty()) {
            throw new Exception("El id del usuario es obligatorio");
        }

        DbConnection baseDatos = null;
        String sql = "UPDATE Usuarios SET name = ?, email = ?, password = ?, role = ? WHERE id = ?";

        try {
            baseDatos = new DbConnection();
            PreparedStatement ps = baseDatos.getConnection().prepareStatement(sql);
            ps.setString(1, usuario.getNombre() != null ? usuario.getNombre().trim() : null);
            ps.setString(2, usuario.getEmail() != null ? usuario.getEmail().trim() : null);
            ps.setString(3, usuario.getPassword() != null ? usuario.getPassword() : null);
            ps.setString(4, usuario.getRole() != null ? usuario.getRole() : "usuario");
            ps.setInt(5, Integer.parseInt(usuario.getId()));

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            throw new Exception("Error actualizando usuario: " + e.getMessage(), e);
        } finally {
            if (baseDatos != null) {
                baseDatos.disconnect();
            }
        }
    }

    public static boolean deleteUser(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El id del usuario es obligatorio");
        }

        DbConnection baseDatos = null;
        String sql = "DELETE FROM Usuarios WHERE id = ?";

        try {
            baseDatos = new DbConnection();
            PreparedStatement ps = baseDatos.getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            throw new Exception("Error eliminando usuario: " + e.getMessage(), e);
        } finally {
            if (baseDatos != null) {
                baseDatos.disconnect();
            }
        }
    }

    private static User mapearUsuario(ResultSet rs) throws Exception {
        User usuario = new User();
        usuario.setId(String.valueOf(rs.getInt("id")));
        usuario.setNombre(rs.getString("name"));
        usuario.setEmail(rs.getString("email"));
        usuario.setPassword(rs.getString("password"));
        usuario.setRole(rs.getString("role"));
        return usuario;
    }
}

