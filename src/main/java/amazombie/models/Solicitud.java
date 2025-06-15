package amazombie.models;

import java.time.LocalDateTime;

public class Solicitud {

    private int id;
    private int usuario_id;
    private String razon;
    private String estado;
    private LocalDateTime fecha;

    public Solicitud(int id, int usuario_id, String razon, String estado, LocalDateTime fecha) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.razon = razon;
        this.estado = estado;
        this.fecha = fecha;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuario_id;
    }

    public void setUsuarioId(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
