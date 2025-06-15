/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.dao;

import amazombie.models.Solicitud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDao {

    private static SolicitudDao instancia;

    public static SolicitudDao getInstancia() {
        if (instancia == null) {
            instancia = new SolicitudDao();
        }
        return instancia;
    }

    public List<Solicitud> obtenerSolicitudes() {
        List<Solicitud> solicitudes = new ArrayList<>();

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT id, usuario_id, razon, estado, fecha FROM solicitudes ORDER BY fecha DESC";

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Solicitud solicitud = new Solicitud(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getString("razon"),
                            rs.getString("estado"),
                            rs.getTimestamp("fecha").toLocalDateTime());
                    solicitudes.add(solicitud);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    public Boolean crearSolicitud(int usuario_id, String razon) {
        boolean exito = false;

        String sql = "INSERT INTO solicitudes (usuario_id, razon, estado, fecha) VALUES (?, ?, 'pendiente', NOW())";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, usuario_id);
            stmt.setString(2, razon);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exito;
    }

    public Boolean usuarioEnvioSolicitud(int usuarioId) {
        boolean existe = false;

        String sql = "SELECT COUNT(*) FROM solicitudes WHERE usuario_id = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    existe = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return existe;
    }

    public Boolean cancelarSolicitud(int id) {
        boolean exito = false;

        String sql = "DELETE FROM solicitudes WHERE usuario_id = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return exito;
    }

    public Boolean aceptarSolicitud(int id) {
        boolean exito = false;

        String sql = "UPDATE solicitudes SET estado = 'aceptada' WHERE id = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return exito;
    }

    public Boolean rechazarSolicitud(int id) {
        boolean exito = false;

        String sql = "UPDATE solicitudes SET estado = 'rechazada' WHERE id = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return exito;
    }

    public Boolean solicitudAceptada(int id) {
        boolean aceptada = false;

        String sql = "SELECT estado FROM solicitudes WHERE usuario_id = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aceptada = "aceptada".equals(rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return aceptada;
    }

    public Boolean solicitudRechazada(int id) {
        boolean rechazada = false;

        String sql = "SELECT estado FROM solicitudes WHERE usuario_id = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rechazada = "rechazada".equals(rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return rechazada;
    }

    public Boolean eliminarSolicitud(int id) {
        boolean exito = false;

        String sql = "DELETE FROM solicitudes WHERE id = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return exito;
    }
}
