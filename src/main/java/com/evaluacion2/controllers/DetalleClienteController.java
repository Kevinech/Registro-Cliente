package com.evaluacion2.controllers;

import com.evaluacion2.Main;
import com.evaluacion2.model.Cliente;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DetalleClienteController {
    @FXML private Label nombreLabel;
    @FXML private Label tipoLabel;
    @FXML private Label ciudadLabel;
    @FXML private Label fechaLabel;
    @FXML private Label solicitudLabel;
    @FXML private Label serviciosLabel;
    @FXML private Label fotoLabel;
    @FXML private ImageView fotoView;

    public void setCliente(Cliente cliente) {
        nombreLabel.setText(cliente.getNombreCompleto());
        tipoLabel.setText(cliente.getTipoCliente());
        ciudadLabel.setText(cliente.getCiudad());
        fechaLabel.setText(cliente.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        solicitudLabel.setText(cliente.getTipoSolicitud());
        serviciosLabel.setText(cliente.getServiciosInteres().isEmpty()
                ? "Sin servicios seleccionados" : String.join(", ", cliente.getServiciosInteres()));
        mostrarFoto(cliente.getRutaFoto());
    }

    private void mostrarFoto(String ruta) {
        fotoView.setImage(null);
        fotoLabel.setText("Sin fotografía");
        if (ruta == null || ruta.isBlank()) return;
        try {
            Image imagen = new Image(ruta, 600, 600, true, true);
            if (imagen.isError()) {
                fotoLabel.setText("Fotografía no disponible");
            } else {
                fotoView.setImage(imagen);
                fotoLabel.setText("Fotografía del cliente");
            }
        } catch (IllegalArgumentException ex) {
            fotoLabel.setText("Fotografía no disponible");
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        Main.mostrarVista("consulta-clientes.fxml");
    }

    @FXML
    private void manejarTecla(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            event.consume();
            Main.mostrarVista("consulta-clientes.fxml");
        }
    }
}
