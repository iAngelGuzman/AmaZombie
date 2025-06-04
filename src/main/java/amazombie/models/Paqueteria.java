/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.models;

import amazombie.dao.PaqueteriaDao;
import amazombie.dao.UsuarioDao;
import java.util.List;

/**
 *
 * @author JoseANG3L
 */
public class Paqueteria {
    private String nombre;
    private List<Paquete> paquetes;
    private PaqueteriaDao paqueteriaDao;
    private List<Usuario> usuarios;
    private UsuarioDao usuarioDao;
    
    public Paqueteria() {
        paqueteriaDao = new PaqueteriaDao();
        usuarioDao = new UsuarioDao();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }
    
    public Boolean agregarPaquete(Paquete paquete, int usuarioId) {
        return paqueteriaDao.agregarPaquete(paquete, usuarioId);
    }
    
    public Boolean existeUsuario(String usuario) {
        return usuarioDao.existeUsuario(usuario);
    }
    
    public Boolean agregarUsuario(String usuario, String contrasena, String rol) {
        return usuarioDao.agregarUsuario(usuario, contrasena, rol);
    }
}
