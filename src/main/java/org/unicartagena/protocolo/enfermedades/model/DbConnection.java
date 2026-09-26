/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.unicartagena.protocolo.enfermedades.model;

/**
 *
 * @author Hanezawa
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DbConnection {
    protected String driver = "com.mysql.cj.jdbc.Driver";

    protected String stringConnDb = System.getenv("MYSQLHOST") != null ? System.getenv("MYSQLHOST") : "localhost";
    protected String url = "jdbc:mysql://";
    protected int portDb = System.getenv("MYSQLPORT") != null ? Integer.parseInt(System.getenv("MYSQLPORT")) : 3306;
    protected String userDb = System.getenv("MYSQLUSER") != null ? System.getenv("MYSQLUSER") : "root";
    protected String passUserDb = System.getenv("MYSQLPASSWORD") != null ? System.getenv("MYSQLPASSWORD") : "";
    protected String nameDb = System.getenv("MYSQL_DATABASE") != null ? System.getenv("MYSQL_DATABASE") : "GestionEnfermedades_db";
    private Connection conn;

    public DbConnection() throws Exception {
        url = url + stringConnDb + ":" + portDb + "/" + nameDb + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        this.connect();
    }

    public Connection getConnection() {
        return conn;
    }

    public void connect() throws Exception {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            throw new Exception("Error de Driver " + ex.getMessage());
        }
        try {
            conn = DriverManager.getConnection(url, userDb, passUserDb);
        } catch (SQLException ex) {
            throw new Exception("Error de Conexion \n Codigo:" + ex.getErrorCode() + " Explicacion:" + ex.getMessage());
        }
    }

    public int update(PreparedStatement sentencia) throws Exception {
        try {
            int res = sentencia.executeUpdate();
            return res;
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar sentencia BD Conexion \n Codigo:" + ex.getErrorCode() + " Explicacion:" + ex.getMessage());
        }
    }

    public ResultSet consult(PreparedStatement sentencia) throws Exception {
        try {
            ResultSet rowsDb = sentencia.executeQuery();
            return rowsDb;
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar sentencia BD Conexion " + ex.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            conn = null;
        }
    }

    public PreparedStatement createSentence(String sql) throws Exception {
        try {
            PreparedStatement sentence = conn.prepareStatement(sql);
            return sentence;
        } catch (SQLException ex) {
            throw new SQLException("Error de Sentencia DB \n Codigo:" + ex.getErrorCode() + " Explicacion: " + ex.getMessage());
        }
    }
}
