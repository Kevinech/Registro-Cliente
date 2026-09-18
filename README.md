# Registro de clientes

Aplicación universitaria de escritorio con JavaFX, FXML y cinco controladores.
Permite registrar y consultar clientes durante la ejecución, sin base de datos.

## Ejecutar en IntelliJ IDEA

1. Abre el proyecto mediante `pom.xml`.
2. Usa un JDK 21 o superior y recarga Maven con **Reload All Maven Projects**.
3. Selecciona **Iniciar JavaFX** y pulsa Run, o ejecuta `javafx:run` en la ventana Maven.

La clase principal es **`com.evaluacion2.Main`**.
Si tienes Maven en el PATH, puedes usar:

```text
mvn compile
mvn javafx:run
```

No hay credenciales fijas: escribe cualquier usuario y contraseña que no estén vacíos
ni contengan únicamente espacios. Por ejemplo, `estudiante` y `123`.

## Uso

1. Inicia sesión. ENTER intenta ingresar; ESC solicita confirmar la salida.
2. Desde el menú principal, abre **Registrar cliente**.
3. Completa nombres, apellidos, tipo de cliente, ciudad, fecha de nacimiento y solicitud.
4. Opcionalmente, marca servicios y selecciona una fotografía PNG, JPG o GIF.
5. Pulsa **Guardar**. Se confirma el registro y se limpia el formulario.
6. Vuelve al menú y abre **Consultar clientes**.
7. Haz doble clic en una fila, o selecciónala y pulsa **Ver detalle**.
8. Usa **Volver** o ESC para regresar.

La ventana principal también incluye **Seleccionar carpeta** y **Acerca de**.
El clic derecho sobre su botón central **Registrar cliente** abre un menú contextual
con accesos al registro y a la consulta. La salida, incluso desde la X de la ventana,
solicita confirmación.

Los servicios y la fotografía son opcionales. Las fechas inválidas o futuras no se aceptan.
La foto se referencia mediante su URI local; no se copia ni se sube a ningún servidor.
Si el archivo deja de estar disponible, el detalle muestra un mensaje.
Los clientes se pierden al cerrar la aplicación.

## Organización

| Archivo | Responsabilidad |
| --- | --- |
| `Main.java` | Inicia el login, reutiliza el mismo Stage y contiene las operaciones comunes de navegación y alertas. |
| `controllers/LoginController.java` | Valida el acceso y maneja ENTER y ESC. |
| `controllers/PrincipalController.java` | Menú, barra de herramientas, carpeta, diálogo Acerca de y salida. |
| `controllers/RegistroClienteController.java` | Valida y guarda clientes, selecciona fotografía y limpia el formulario. |
| `controllers/ConsultaClientesController.java` | Llena la tabla y entrega el cliente seleccionado al detalle. |
| `controllers/DetalleClienteController.java` | Recibe y muestra los datos del cliente. |
| `model/Cliente.java` | Datos sencillos, constructor y getters; no necesita setters porque no hay edición. |
| `model/DatosClientes.java` | Una única ObservableList compartida en memoria. |

Las clases están en `src/main/java/com/evaluacion2/`.
Las cinco vistas, con estilos inline en FXML, están en `src/main/resources/com/evaluacion2/`.
Cada FXML declara su controlador y su CSS relativo, y puede abrirse en Scene Builder.
No se añadieron capas, clases de producción ni dependencias adicionales.
Se conservaron las versiones del `pom.xml`.

## Criterios de la evaluación

| Requisito | Ubicación |
| --- | --- |
| ActionEvent | Botones, opciones de menú, navegación, guardar, limpiar y CheckBox. |
| MouseEvent | Doble clic izquierdo sobre una fila con cliente en Consulta. Las filas vacías se ignoran. |
| KeyEvent | ENTER en Login; ESC en Login y Principal confirma salida; ESC en Registro, Consulta y Detalle regresa. |
| Alert WARNING | Campos incompletos, fecha inválida o falta de selección en la tabla. |
| Alert ERROR | Imagen ilegible o error al cargar una vista. |
| Alert INFORMATION | Registro correcto y ruta de carpeta seleccionada. |
| Alert CONFIRMATION | Salida mediante botón, menú, ESC o cierre de ventana. |
| Dialog | Acerca de, en Principal. |
| FileChooser | Seleccionar fotografía, en Registro. |
| DirectoryChooser | Seleccionar carpeta, en Principal. |
| ListView | Servicios elegidos con los tres CheckBox, en Registro. |
| ToggleGroup | Una sola solicitud entre Información, Servicio y Reclamo. |
| MenuBar, ToolBar, ContextMenu | Principal, con accesos funcionales al registro y consulta. |

## Paso de datos

1. Registro crea un `Cliente` y lo agrega a `DatosClientes.clientes`.
2. Consulta usa `clientesTable.setItems(DatosClientes.clientes)`: no crea otra lista.
3. Consulta carga `detalle-cliente.fxml`, obtiene su controlador con
   `cargador.getController()` y llama a `controlador.setCliente(cliente)`.
4. Detalle muestra ese mismo objeto recibido. No vuelve a buscarlo en la lista.

El constructor de Cliente copia la lista de servicios para que limpiar el formulario
no borre los servicios del cliente que ya se guardó.

## Verificación realizada

- Compilación con Maven: **BUILD SUCCESS**.
- Arranque real mediante `javafx:run`.
- Carga de los cinco FXML y comprobación de todos los campos inyectados.
- Prueba temporal de interfaz con 67 comprobaciones correctas: login vacío y válido,
  ENTER y ESC, validaciones de registro, servicios, selección real de fotografía,
  guardado, limpieza, tabla, doble clic, detalle, navegación, menú contextual,
  Acerca de, foto ausente y salida cancelada y confirmada.
- Selección real de carpeta y Alert con su ruta, verificados en una ejecución anterior.
- Revisión visual de Registro, Consulta y Detalle.

La prueba temporal y las imágenes de prueba están dentro de `target/verificacion/`,
que está excluido por Git; no forman parte de la aplicación ni se ejecutan con `mvn test`.

## Entorno Maven y certificados

En este equipo, Java no validaba la cadena HTTPS de Maven Central (error PKIX).
Para completar las descargas se usó, solo durante los procesos de Maven de verificación,
el almacén de certificados ya confiados por Windows:

```text
-Djavax.net.ssl.trustStoreType=Windows-ROOT -Djavax.net.ssl.trustStore=NONE
```

Se mantuvo la validación HTTPS. No se modificaron certificados, configuraciones
persistentes, versiones ni repositorios. Después de descargar, `mvn -o compile`
también terminó correctamente sin esas opciones.

Si vuelve a aparecer PKIX al descargar en este equipo, revisa el JDK de Maven Importer
y Maven Runner. En Windows, las opciones anteriores pueden usarse temporalmente en
las opciones de la JVM de Maven si el almacén de confianza del sistema está configurado
correctamente. Este mecanismo está documentado por
[Oracle para SunMSCAPI y Windows-ROOT](https://docs.oracle.com/en/java/javase/17/security/oracle-providers.html).
No desactives SSL ni la validación de certificados.

Con el JDK 27 instalado aparecen avisos de JavaFX 21 sobre acceso nativo y APIs
obsoletas del JDK; en las pruebas no impidieron compilar ni ejecutar.
