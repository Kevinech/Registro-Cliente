package com.evaluacion2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatosClientes {
    // Esta única lista se comparte durante la ejecución. No hay base de datos.
    public static final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
}
