/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import amazombie.models.Usuario;

/**
 *
 * @author JoseANG3L
 */
public class UsuarioDao {

    private static UsuarioDao instancia;
    private int idUsuarioAEditar;

    public static UsuarioDao getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioDao();
        }
        return instancia;
    }

    public void setIdUsuarioAEditar(int idUsuarioAEditar) {
        this.idUsuarioAEditar = idUsuarioAEditar;
    }

    public int getIdUsuarioAEditar() {
        return idUsuarioAEditar;
    }

    public Usuario iniciarSesion(String nombre, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?";

        try (Connection connection = ConexionDB.conectar()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Login fallido
    }

    public Boolean existeUsuario(String usuario) {
        boolean existe = false;

        // Establecer la conexión a la base de datos
        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE nombre = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, usuario);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    existe = count > 0;
                }
            }

        } catch (SQLException e) {
        }

        return existe;
    }

    public Boolean agregarUsuario(String usuario, String contrasena, String rol) {
        boolean exito = false;

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "INSERT INTO usuarios (nombre, contrasena, rol) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, usuario);
                stmt.setString(2, contrasena);
                stmt.setString(3, rol);

                int filasAfectadas = stmt.executeUpdate();
                exito = filasAfectadas > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Muestra errores por consola (útil para depurar)
        }

        return exito;
    }

    public Usuario obtenerUsuario(int id) {
        Usuario usuario = null;

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT * FROM usuarios WHERE id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        usuario = new Usuario(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("rol"));
                    }
                }
            }
        } catch (SQLException e) {
        }

        return usuario;
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT * FROM usuarios where rol = 'usuario'";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        usuarios.add(new Usuario(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("rol")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public List<Usuario> obtenerEmpleados() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT * FROM usuarios where rol = 'admin'";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        usuarios.add(new Usuario(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("rol")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public Boolean actualizarUsuario(Usuario usuario) {
        boolean exito = false;

        String sql = "UPDATE usuarios SET nombre = ?, rol = ? WHERE id = ?";

        try (Connection connection = ConexionDB.conectar()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRol());
            stmt.setInt(3, usuario.getId());

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Muestra errores por consola (útil para depurar)
        }

        return exito;
    }

    public Boolean eliminarUsuario(int id) {
        boolean exito = false;

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection connection = ConexionDB.conectar()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Muestra errores por consola (útil para depurar)
        }

        return exito;
    }
}
