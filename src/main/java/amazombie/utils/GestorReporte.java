/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package amazombie.utils;

import amazombie.models.Reporte;


public class GestorReporte {

    private static Reporte reporteActual;    

    public static void setReporteActual(Reporte reporte) {
        reporteActual = reporte;
    }

    // Obtener usuario actual
    public static Reporte getReporteActual() {
        return reporteActual;
    }
    
}
