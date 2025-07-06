package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    private DefaultTableModel tableModel;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public GestionDeUsuariosView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(1000, 600);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        configurarTabla();
        updateTexts();
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuarios.setModel(tableModel);
    }

    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("gestionUsuarios.titulo"));
        lblNombre.setText(mensajes.getString("gestionUsuarios.label.buscar"));
        btnBuscar.setText(mensajes.getString("global.boton.buscar"));
        btnListar.setText(mensajes.getString("global.boton.listar"));
        btnAgregar.setText(mensajes.getString("global.boton.agregar"));
        btnActualizar.setText(mensajes.getString("global.boton.actualizar"));
        btnEliminar.setText(mensajes.getString("global.boton.eliminar"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.getString("gestionUsuarios.tabla.username"),
                mensajes.getString("gestionUsuarios.tabla.email"),
                mensajes.getString("gestionUsuarios.tabla.rol"),
                mensajes.getString("gestionUsuarios.tabla.nombre")
        });
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
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

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}