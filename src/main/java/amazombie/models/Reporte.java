package amazombie.models;

import java.time.LocalDateTime;

public class Reporte {

    private int id;
    private int usuario_id;
    private String asunto;
    private String descripcion;
    private String respuesta;
    private String estado;
    private LocalDateTime fecha;

    public Reporte(int id, int usuario_id, String asunto, String descripcion, String respuesta, String estado, LocalDateTime fecha) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.respuesta = respuesta;
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

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
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

    public int getReporte_id() {
        return id;
    }

    public void setReporte_id(int id) {
        this.id = id;
    }
}
