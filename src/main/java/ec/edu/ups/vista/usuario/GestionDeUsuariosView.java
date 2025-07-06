package ec.edu.ups.vista.usuario;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;
import java.util.ResourceBundle;

public class GestionDeUsuariosView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JTable tblUsuarios;
    private JButton btnBuscar;
    private JButton btnListar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnAgregar;
    private JScrollPane scrollPane;

    private DefaultTableModel modelo;
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;

    public GestionDeUsuariosView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(1000, 600);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        configurarTabla();

        SwingUtilities.invokeLater(this::updateTexts);
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuarios.setModel(modelo);
    }

    public JTextField getTxtBuscar() {
        return txtNombre;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        updateTexts();
    }

    public void updateTexts() {
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("gestionUsuarios.titulo"));

        if (lblNombre != null) {
            lblNombre.setText(mensajes.getString("gestionUsuarios.label.buscar"));
        }

        btnBuscar.setText(mensajes.getString("gestionUsuarios.boton.buscar"));
        btnListar.setText(mensajes.getString("gestionUsuarios.boton.listar"));
        btnAgregar.setText(mensajes.getString("gestionUsuarios.boton.agregar"));
        btnActualizar.setText(mensajes.getString("gestionUsuarios.boton.actualizar"));
        btnEliminar.setText(mensajes.getString("gestionUsuarios.boton.eliminar"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajes.getString("gestionUsuarios.tabla.username"),
                mensajes.getString("gestionUsuarios.tabla.email"),
                mensajes.getString("gestionUsuarios.tabla.rol")
        });

        revalidate();
        repaint();
    }
}