package org.unicartagena.protocolo.enfermedades.model;

/**
 *
 * @author Hanezawa
 */
public class User {
    private String id;
    private String password;
    private String nombre;
    private String email;
    private String role;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
