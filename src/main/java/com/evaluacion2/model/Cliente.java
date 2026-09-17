package com.evaluacion2.model;

import java.time.LocalDate;
import java.util.List;

public class Cliente {
    private final String nombres;
    private final String apellidos;
    private final String tipoCliente;
    private final String ciudad;
    private final LocalDate fechaNacimiento;
    private final String tipoSolicitud;
    private final List<String> serviciosInteres;
    private final String rutaFoto;

    public Cliente(String nombres, String apellidos, String tipoCliente, String ciudad,
                   LocalDate fechaNacimiento, String tipoSolicitud,
                   List<String> serviciosInteres, String rutaFoto) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoCliente = tipoCliente;
        this.ciudad = ciudad;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSolicitud = tipoSolicitud;
        // Copia independiente: limpiar el formulario no modifica al cliente guardado.
        this.serviciosInteres = List.copyOf(serviciosInteres);
        this.rutaFoto = rutaFoto;
    }

    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getNombreCompleto() { return nombres + " " + apellidos; }
    public String getTipoCliente() { return tipoCliente; }
    public String getCiudad() { return ciudad; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTipoSolicitud() { return tipoSolicitud; }
    public List<String> getServiciosInteres() { return serviciosInteres; }
    public String getRutaFoto() { return rutaFoto; }
}
