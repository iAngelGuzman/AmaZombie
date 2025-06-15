/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.utils;

import amazombie.models.Paquete;
import amazombie.models.Usuario;


public class GestorPaquete {

    private static Paquete paqueteActual;

    public static void setPaqueteActual(Paquete paquete) {
        paqueteActual = paquete;
    }

    // Obtener usuario actual
    public static Paquete getPaqueteActual() {
        return paqueteActual;
    }
}
