/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.utils;

import amazombie.models.Solicitud;


public class GestorSolicitud {

    private static Solicitud solicitudActual;    

    public static void setSolicitudActual(Solicitud solicitud) {
        solicitudActual = solicitud;
    }

    // Obtener usuario actual
    public static Solicitud getSolicitudActual() {
        return solicitudActual;
    }
    
}
