package com.evaluacion2.controllers;

import com.evaluacion2.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class LoginController {
    @FXML private VBox pantalla;
    @FXML private TextField usuarioField;
    @FXML private PasswordField contrasenaField;

    @FXML
    private void initialize() {
        // El filtro permite usar ENTER incluso cuando un botón tiene el foco.
        pantalla.addEventFilter(KeyEvent.KEY_PRESSED, this::manejarTecla);
    }

    private static final String USUARIO_VALIDO = "admin";
    private static final String CLAVE_VALIDA = "1234";

    @FXML
    private void ingresar(ActionEvent event) {
        iniciarSesion();
    }

    private void iniciarSesion() {

        String usuario = usuarioField.getText().trim();
        String clave = contrasenaField.getText();

        if (usuario.isBlank() || clave.isBlank()) {
            Main.mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Datos incompletos",
                    "Debes ingresar usuario y contraseña."
            );
            return;
        }

        if (usuario.equals(USUARIO_VALIDO) &&
                clave.equals(CLAVE_VALIDA)) {

            Main.mostrarVista("principal.fxml");

        } else {
            Main.mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Acceso denegado",
                    "Usuario o contraseña incorrectos."
            );
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        Main.confirmarSalida();
    }

    private void manejarTecla(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            event.consume();
            iniciarSesion();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            event.consume();
            Main.confirmarSalida();
        }
    }
}
