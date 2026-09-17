package com.evaluacion2.controllers;

import com.evaluacion2.Main;
import com.evaluacion2.model.Cliente;
import com.evaluacion2.model.DatosClientes;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ConsultaClientesController {
    @FXML private TableView<Cliente> clientesTable;
    @FXML private TableColumn<Cliente, String> nombreColumn;
    @FXML private TableColumn<Cliente, String> tipoColumn;
    @FXML private TableColumn<Cliente, String> ciudadColumn;
    @FXML private TableColumn<Cliente, String> fechaColumn;
    @FXML private TableColumn<Cliente, String> solicitudColumn;
    @FXML private Label cantidadLabel;

    @FXML
    private void initialize() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // El modelo usa campos normales; los wrappers solo adaptan los valores a la tabla.
        nombreColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNombreCompleto()));
        tipoColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTipoCliente()));
        ciudadColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getCiudad()));
        fechaColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getFechaNacimiento().format(formato)));
        solicitudColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTipoSolicitud()));
        clientesTable.setItems(DatosClientes.clientes);
        cantidadLabel.setText("Clientes registrados: " + DatosClientes.clientes.size());
        clientesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        clientesTable.setRowFactory(tabla -> {
            TableRow<Cliente> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> abrirConDobleClic(event, fila));
            return fila;
        });
    }

    private void abrirConDobleClic(MouseEvent event, TableRow<Cliente> fila) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !fila.isEmpty()) {
            clientesTable.getSelectionModel().select(fila.getItem());
            mostrarDetalle(fila.getItem());
        }
    }

    @FXML
    private void verDetalle(ActionEvent event) {
        Cliente seleccionado = clientesTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Main.mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un cliente",
                    "Selecciona una fila para ver sus datos.");
            return;
        }
        mostrarDetalle(seleccionado);
    }

    private void mostrarDetalle(Cliente cliente) {
        try {
            FXMLLoader cargador = new FXMLLoader(Main.class.getResource("/com/evaluacion2/detalle-cliente.fxml"));
            Parent vista = cargador.load();
            DetalleClienteController controlador = cargador.getController();
            // Se entrega el mismo objeto de la tabla, sin volver a buscarlo.
            controlador.setCliente(cliente);
            Main.mostrarContenido(vista);
        } catch (IOException ex) {
            ex.printStackTrace();
            Main.mostrarAlerta(Alert.AlertType.ERROR, "No se pudo abrir el detalle",
                    "No fue posible cargar los datos del cliente.");
        }
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
