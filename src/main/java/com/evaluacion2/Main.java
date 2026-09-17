package com.evaluacion2;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage ventana;

    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        ventana.setTitle("Registro de clientes");
        ventana.setMinWidth(900);
        ventana.setMinHeight(700);
        Parent inicio = FXMLLoader.load(Main.class.getResource("/com/evaluacion2/login.fxml"));
        ventana.setScene(new Scene(inicio, 960, 720));
        ventana.setOnCloseRequest(event -> {
            event.consume();
            confirmarSalida();
        });
        ventana.show();
    }

    // Todas las pantallas utilizan la misma ventana.
    public static void mostrarVista(String archivo) {
        try {
            Parent contenido = FXMLLoader.load(Main.class.getResource("/com/evaluacion2/" + archivo));
            mostrarContenido(contenido);
        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo abrir la pantalla",
                    "No fue posible cargar " + archivo + ".");
        }
    }

    public static void mostrarContenido(Parent contenido) {
        ventana.getScene().setRoot(contenido);
    }

    public static Stage getVentana() {
        return ventana;
    }

    public static void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.initOwner(ventana);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void confirmarSalida() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Deseas salir? Los clientes registrados en memoria se perderán.",
                ButtonType.YES, ButtonType.NO);
        alerta.initOwner(ventana);
        alerta.setTitle("Salir de la aplicación");
        alerta.setHeaderText(null);
        if (alerta.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
