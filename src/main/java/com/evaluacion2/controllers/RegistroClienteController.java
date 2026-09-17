package com.evaluacion2.controllers;

import com.evaluacion2.Main;
import com.evaluacion2.model.Cliente;
import com.evaluacion2.model.DatosClientes;
import java.io.File;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class RegistroClienteController {
    @FXML private TextField nombresField;
    @FXML private TextField apellidosField;
    @FXML private ComboBox<String> tipoClienteCombo;
    @FXML private ComboBox<String> ciudadCombo;
    @FXML private DatePicker fechaNacimientoPicker;
    @FXML private ToggleGroup solicitudGroup;
    @FXML private CheckBox asesoriaCheck;
    @FXML private CheckBox soporteCheck;
    @FXML private CheckBox seguimientoCheck;
    @FXML private ListView<String> serviciosList;
    @FXML private ImageView fotoView;
    @FXML private Label fotoLabel;
    private String rutaFoto;

    @FXML
    private void initialize() {
        tipoClienteCombo.getItems().addAll("Regular", "Preferencial", "Empresarial");
        ciudadCombo.getItems().addAll("Managua", "León", "Granada", "Masaya", "Chinandega");
        serviciosList.setPlaceholder(new Label("Sin servicios seleccionados"));
    }

    @FXML
    private void actualizarServicios(ActionEvent event) {
        serviciosList.getItems().clear();
        if (asesoriaCheck.isSelected()) serviciosList.getItems().add("Asesoría");
        if (soporteCheck.isSelected()) serviciosList.getItems().add("Soporte");
        if (seguimientoCheck.isSelected()) serviciosList.getItems().add("Seguimiento");
    }

    @FXML
    private void seleccionarFoto(ActionEvent event) {
        FileChooser selector = new FileChooser();
        selector.setTitle("Seleccionar fotografía");
        selector.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes (PNG, JPG, GIF)", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File archivo = selector.showOpenDialog(Main.getVentana());
        if (archivo == null) return;

        try {
            Image imagen = new Image(archivo.toURI().toString(), 600, 600, true, true);
            if (imagen.isError()) {
                Main.mostrarAlerta(Alert.AlertType.ERROR, "Imagen no válida",
                        "No se pudo leer la imagen. Selecciona otro archivo.");
                return;
            }
            rutaFoto = archivo.toURI().toString();
            fotoView.setImage(imagen);
            fotoLabel.setText(archivo.getName());
        } catch (IllegalArgumentException ex) {
            Main.mostrarAlerta(Alert.AlertType.ERROR, "Imagen no válida",
                    "No se pudo abrir la fotografía seleccionada.");
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (!validarFormulario()) return;
        RadioButton solicitud = (RadioButton) solicitudGroup.getSelectedToggle();
        Cliente cliente = new Cliente(nombresField.getText().trim(), apellidosField.getText().trim(),
                tipoClienteCombo.getValue(), ciudadCombo.getValue(), fechaNacimientoPicker.getValue(),
                solicitud.getText(), serviciosList.getItems(), rutaFoto);
        DatosClientes.clientes.add(cliente);
        Main.mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente registrado",
                "Se guardó correctamente a " + cliente.getNombreCompleto() + ".");
        limpiarFormulario();
    }

    private boolean validarFormulario() {
        // Convierte también la fecha escrita a mano, antes de consultar su valor.
        try {
            fechaNacimientoPicker.commitValue();
        } catch (RuntimeException ex) {
            Main.mostrarAlerta(Alert.AlertType.WARNING, "Fecha no válida",
                    "Selecciona una fecha válida usando el calendario.");
            return false;
        }
        if (nombresField.getText().isBlank() || apellidosField.getText().isBlank()
                || tipoClienteCombo.getValue() == null || ciudadCombo.getValue() == null
                || fechaNacimientoPicker.getValue() == null || solicitudGroup.getSelectedToggle() == null) {
            Main.mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos",
                    "Completa nombres, apellidos, tipo de cliente, ciudad, fecha de nacimiento y tipo de solicitud.");
            return false;
        }
        if (fechaNacimientoPicker.getValue().isAfter(LocalDate.now())) {
            Main.mostrarAlerta(Alert.AlertType.WARNING, "Fecha no válida",
                    "La fecha de nacimiento no puede estar en el futuro.");
            return false;
        }
        return true;
    }

    @FXML
    private void limpiar(ActionEvent event) {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        nombresField.clear();
        apellidosField.clear();
        tipoClienteCombo.getSelectionModel().clearSelection();
        ciudadCombo.getSelectionModel().clearSelection();
        fechaNacimientoPicker.setValue(null);
        fechaNacimientoPicker.getEditor().clear();
        solicitudGroup.selectToggle(null);
        asesoriaCheck.setSelected(false);
        soporteCheck.setSelected(false);
        seguimientoCheck.setSelected(false);
        serviciosList.getItems().clear();
        rutaFoto = null;
        fotoView.setImage(null);
        fotoLabel.setText("Sin fotografía");
        nombresField.requestFocus();
    }

    @FXML
    private void volver(ActionEvent event) {
        Main.mostrarVista("principal.fxml");
    }

    @FXML
    private void manejarTecla(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            event.consume();
            Main.mostrarVista("principal.fxml");
        }
    }
}
