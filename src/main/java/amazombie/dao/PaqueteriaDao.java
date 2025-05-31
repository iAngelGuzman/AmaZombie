/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.dao;

import amazombie.models.Paquete;
import amazombie.models.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoseANG3L
 */
public class PaqueteriaDao {

    public List<Paquete> obtenerPaquetes() {
        List<Paquete> paquetes = new ArrayList<>();

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT id, usuario_id, nombre, descripcion, precio, estado FROM paquetes";

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Paquete paquete = new Paquete(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("estado"));
                    paquetes.add(paquete);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paquetes;
    }

    public Paquete obtenerPaquete(int id) {
        Paquete paquete = null;

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT id, usuario_id, nombre, descripcion, precio, estado FROM paquetes WHERE id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        paquete = new Paquete(
                                rs.getInt("id"),
                                rs.getInt("usuario_id"),
                                rs.getString("nombre"),
                                rs.getString("descripcion"),
                                rs.getDouble("precio"),
                                rs.getString("estado"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paquete;
    }

    public Boolean agregarPaquete(Paquete paquete, int usuarioId) {
        boolean exito = false;

        String sql = "INSERT INTO paquetes (nombre, descripcion, precio, estado, usuario_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, paquete.getNombre());
            stmt.setString(2, paquete.getDescripcion());
            stmt.setDouble(3, paquete.getPrecio());
            stmt.setString(4, paquete.getEstado());
            stmt.setInt(5, usuarioId);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

}
