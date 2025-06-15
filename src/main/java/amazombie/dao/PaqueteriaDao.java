/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.dao;

import amazombie.models.Paquete;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PaqueteriaDao {

    private static PaqueteriaDao instancia;

    public static PaqueteriaDao getInstancia() {
        if (instancia == null) {
            instancia = new PaqueteriaDao();
        }
        return instancia;
    }

    public List<Paquete> obtenerPaquetes() {
        List<Paquete> paquetes = new ArrayList<>();

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT id, usuario_id, nombre, descripcion, precio, estado, ruta, guia, fecha, origen, destino FROM paquetes";

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Paquete paquete = new Paquete(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("estado"),
                            rs.getString("ruta"),
                            rs.getString("guia"),
                            rs.getTimestamp("fecha").toLocalDateTime(),
                            rs.getString("origen"),
                            rs.getString("destino"));
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
            String sql = "SELECT id, usuario_id, nombre, descripcion, precio, estado, ruta, guia, fecha, origen, destino FROM paquetes WHERE id = ?";

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
                                rs.getString("estado"),
                                rs.getString("ruta"),
                                rs.getString("guia"),
                                rs.getTimestamp("fecha").toLocalDateTime(),
                                rs.getString("origen"),
                                rs.getString("destino"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paquete;
    }

    public Boolean agregarPaquete(String nombre, String descripcion, Double precio, String estado, String ruta, String guia, int usuarioId, String origen, String destino) {
        boolean exito = false;

        String sql = "INSERT INTO paquetes (nombre, descripcion, precio, estado, ruta, guia, usuario_id, origen, destino) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, precio);
            stmt.setString(4, estado);
            stmt.setString(5, ruta);
            stmt.setString(6, guia);
            stmt.setInt(7, usuarioId);
            stmt.setString(8, origen);
            stmt.setString(9, destino);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

    public boolean actualizarPaquete(Paquete paquete) {
        boolean exito = false;

        String sql = "UPDATE paquetes SET nombre = ?, descripcion = ?, precio = ?, estado = ?, ruta = ?, guia = ?, fecha = ?, origen = ?, destino = ? WHERE id = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, paquete.getNombre());
            stmt.setString(2, paquete.getDescripcion());
            stmt.setDouble(3, paquete.getPrecio());
            stmt.setString(4, paquete.getEstado());
            stmt.setString(5, paquete.getRuta());
            stmt.setString(6, paquete.getGuia());
            stmt.setTimestamp(7, java.sql.Timestamp.valueOf(paquete.getFecha()));
            stmt.setString(8, paquete.getOrigen());
            stmt.setString(9, paquete.getDestino());
            stmt.setInt(10, paquete.getId());

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

    public boolean guiaExiste(String guia) {
        boolean existe = false;

        String sql = "SELECT COUNT(*) FROM paquetes WHERE guia = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, guia);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    existe = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }

    public Paquete obtenerPaquetePorGuia(String guia) {
        Paquete paquete = null;

        String sql = "SELECT id, usuario_id, nombre, descripcion, precio, estado, ruta, guia, fecha, origen, destino FROM paquetes WHERE guia = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, guia);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paquete = new Paquete(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("estado"),
                            rs.getString("ruta"),
                            guia,
                            rs.getTimestamp("fecha").toLocalDateTime(),
                            rs.getString("origen"),
                            rs.getString("destino"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paquete;
    }

}
