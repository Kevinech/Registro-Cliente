package com.evaluacion2.controllers;

import com.evaluacion2.Main;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;

public class PrincipalController {
    @FXML
    private void abrirRegistro(ActionEvent event) {
        Main.mostrarVista("registro-cliente.fxml");
    }

    @FXML
    private void abrirConsulta(ActionEvent event) {
        Main.mostrarVista("consulta-clientes.fxml");
    }

    @FXML
    private void seleccionarCarpeta(ActionEvent event) {
        DirectoryChooser selector = new DirectoryChooser();
        selector.setTitle("Seleccionar carpeta");
        File carpeta = selector.showDialog(Main.getVentana());
        if (carpeta != null) {
            Main.mostrarAlerta(Alert.AlertType.INFORMATION, "Carpeta seleccionada",
                    carpeta.getAbsolutePath());
        }
    }

    @FXML
    private void mostrarAcercaDe(ActionEvent event) {
        Dialog<Void> dialogo = new Dialog<>();
        dialogo.initOwner(Main.getVentana());
        dialogo.setTitle("Acerca de");
        dialogo.setHeaderText("Registro de clientes");
        Label mensaje = new Label("Aplicación desarrollada para la evaluación de\n"
                + "Programación de Aplicaciones de Escritorio.");
        mensaje.setWrapText(true);
        dialogo.getDialogPane().setContent(mensaje);
        dialogo.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialogo.showAndWait();
    }

    @FXML
    private void salir(ActionEvent event) {
        Main.confirmarSalida();
    }

    @FXML
    private void manejarTecla(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            event.consume();
            Main.confirmarSalida();
        }
    }
}
