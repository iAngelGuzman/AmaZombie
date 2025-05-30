/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.models;

/**
 *
 * @author JoseAngel
 */
public class Usuario {
    
    private int id;
    private String nombre;
    private String rol;

    // Constructor
    public Usuario(int id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }
    
    public int getId() {
        return id;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean esAdmin() {
        return rol.equals("admin");
    }
}
