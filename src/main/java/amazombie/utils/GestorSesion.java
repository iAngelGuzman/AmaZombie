/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.utils;

import amazombie.models.Usuario;


public class GestorSesion {

    private static Usuario usuarioActual;

    // Establecer usuario en sesión
    public static void iniciarSesion(Usuario usuario) {
        usuarioActual = usuario;
    }

    // Obtener usuario actual
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Verificar si hay sesión activa
    public static boolean existeSesion() {
        return usuarioActual != null;
    }

    // Cerrar sesión
    public static void cerrarSesion() {
        usuarioActual = null;
    }
}
