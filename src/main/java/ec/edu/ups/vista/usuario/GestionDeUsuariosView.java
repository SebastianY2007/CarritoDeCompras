package ec.edu.ups.vista.usuario;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;
import java.util.ResourceBundle;

public class GestionDeUsuariosView extends JInternalFrame {

    // --- Componentes de la UI ---
    private JPanel panelPrincipal;
    private JTable tblUsuarios;
    private JTextField txtNombre; // Asumo que este es tu campo de búsqueda
    private JButton btnBuscar;
    private JButton btnListar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnAgregar;
    private JLabel lblNombre; // Etiqueta para el campo de búsqueda

    // --- Dependencias ---
    private DefaultTableModel modelo;
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;

    // Vistas que puede abrir
    private AnadirUsuarioView anadirUsuarioView;
    private ActualizarUsuarioView actualizarUsuarioView;

    public GestionDeUsuariosView(MensajeInternacionalizacionHandler msgHandler) {
        this.mensajeInternacionalizacionHandler = msgHandler;
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(msgHandler.getLenguajeActual(), msgHandler.getPaisActual()));

        // CORRECCIÓN: Se usa la clave "titulo" en lugar de "title"
        setTitle(mensajes.getString("gestionUsuarios.titulo"));

        setContentPane(panelPrincipal);
        setSize(800, 600);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Configuración del modelo de la tabla
        modelo = new DefaultTableModel();
        tblUsuarios.setModel(modelo);

        // Se llama a updateTexts() externamente después de que los componentes se inicialicen
    }

    // --- Getters para el Controlador ---
    public JTextField getTxtBuscar() {
        return txtNombre; // Devuelve el campo de texto correcto
    }

    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnListar() { return btnListar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JTable getTblUsuarios() { return tblUsuarios; }
    public DefaultTableModel getModelo() { return modelo; }

    // --- Setters para Inyección de Dependencias ---
    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        updateTexts();
    }

    public void setAnadirUsuarioView(AnadirUsuarioView anadirUsuarioView) {
        this.anadirUsuarioView = anadirUsuarioView;
    }

    public void setActualizarUsuarioView(ActualizarUsuarioView actualizarUsuarioView) {
        this.actualizarUsuarioView = actualizarUsuarioView;
    }

    // --- Métodos de la Vista ---
    public void mostrarMensaje(String mensaje, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.getString("global.info"), tipoMensaje);
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("gestionUsuarios.titulo"));

        if (lblNombre != null) {
            lblNombre.setText(mensajes.getString("gestionUsuarios.label.buscar"));
        }

        // CORRECCIÓN: Se usa "boton" en lugar de "btn" o "button"
        btnBuscar.setText(mensajes.getString("gestionUsuarios.boton.buscar"));
        btnListar.setText(mensajes.getString("gestionUsuarios.boton.listar"));
        btnAgregar.setText(mensajes.getString("gestionUsuarios.boton.agregar"));
        btnActualizar.setText(mensajes.getString("gestionUsuarios.boton.actualizar"));
        btnEliminar.setText(mensajes.getString("gestionUsuarios.boton.eliminar"));

        // Actualizar encabezados de la tabla
        modelo.setColumnIdentifiers(new Object[]{
                mensajes.getString("gestionUsuarios.tabla.username"),
                mensajes.getString("gestionUsuarios.tabla.email"),
                mensajes.getString("gestionUsuarios.tabla.rol"),
                mensajes.getString("gestionUsuarios.tabla.nombre"),
                mensajes.getString("gestionUsuarios.tabla.apellido")
        });

        revalidate();
        repaint();
    }
}
