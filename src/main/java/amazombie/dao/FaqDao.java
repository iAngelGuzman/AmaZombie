/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.dao;

import amazombie.models.Faq;
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


public class FaqDao {

    public List<Faq> obtenerFaqs() {
        List<Faq> faqs = new ArrayList<>();

        try (Connection connection = ConexionDB.conectar()) {
            String sql = "SELECT id, pregunta, respuesta FROM faq";

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Faq faq = new Faq(
                            rs.getInt("id"),
                            rs.getString("pregunta"),
                            rs.getString("respuesta"));
                    faqs.add(faq);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return faqs;
    }

}
