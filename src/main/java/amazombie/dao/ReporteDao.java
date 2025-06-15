/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.dao;

import amazombie.models.Faq;
import amazombie.models.Paquete;
import amazombie.models.Reporte;
import amazombie.models.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ReporteDao {

    private static ReporteDao instancia;

    public static ReporteDao getInstancia() {
        if (instancia == null) {
            instancia = new ReporteDao();
        }
        return instancia;
    }

    public List<Reporte> obtenerReportes() {
        List<Reporte> reportes = new ArrayList<>();

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT id, usuario_id, asunto, descripcion, respuesta, estado, fecha FROM reportes ORDER BY fecha DESC";

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Reporte reporte = new Reporte(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getString("asunto"),
                            rs.getString("descripcion"),
                            rs.getString("respuesta"),
                            rs.getString("estado"),
                            rs.getTimestamp("fecha").toLocalDateTime());
                    reportes.add(reporte);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportes;
    }

    public List<Reporte> obtenerReportesPorUsuario(int usuarioId) {
        List<Reporte> reportes = new ArrayList<>();

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT id, usuario_id, asunto, descripcion, respuesta, estado, fecha FROM reportes WHERE usuario_id = ? ORDER BY fecha DESC";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, usuarioId);
                try (ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        Reporte reporte = new Reporte(
                                rs.getInt("id"),
                                rs.getInt("usuario_id"),
                                rs.getString("asunto"),
                                rs.getString("descripcion"),
                                rs.getString("respuesta"),
                                rs.getString("estado"),
                                rs.getTimestamp("fecha").toLocalDateTime());
                        reportes.add(reporte);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportes;
    }

    public Boolean crearReporte(int usuario_id, String asunto, String descripcion) {
        boolean exito = false;

        String sql = "INSERT INTO reportes (usuario_id, asunto, descripcion, respuesta, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, usuario_id);
            stmt.setString(2, asunto);
            stmt.setString(3, descripcion);
            stmt.setString(4, "");
            stmt.setString(5, "pendiente");

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

    public Boolean actualizarReporte(int id, String respuesta, String estado) {
        boolean exito = false;

        String sql = "UPDATE reportes SET respuesta = ?, estado = ? WHERE id = ?";

        try (Connection connection = ConexionDB.conectar();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, respuesta);
            stmt.setString(2, estado);
            stmt.setInt(3, id);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
}
