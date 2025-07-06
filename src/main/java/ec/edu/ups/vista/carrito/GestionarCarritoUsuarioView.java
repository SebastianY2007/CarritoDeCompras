package ec.edu.ups.vista.carrito;

import javax.swing.*;

public class GestionarCarritoUsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblMisCarritos;
    private JButton btnListarMisCarritos;
    private JTextField txtCodigoCarrito;
    private JButton btnBuscar;
    private JTable tblCarrito;
    private JButton btnAgregarProducto;
    private JButton btnModificarCantidad;
    private JButton btnEliminarProducto;
    private JButton btnGuardarCarrito;
    private JButton btnEliminarCarrito;

    public GestionarCarritoUsuarioView() {
        setTitle("Gestionar Mis Carritos");
        setContentPane(panelPrincipal);
        setSize(800, 600);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
    }

    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public JTable getTblMisCarritos() { return tblMisCarritos; }
    public JButton getBtnListarMisCarritos() { return btnListarMisCarritos; }
    public JTextField getTxtCodigoCarrito() { return txtCodigoCarrito; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JTable getTblCarrito() { return tblCarrito; }
    public JButton getBtnAgregarProducto() { return btnAgregarProducto; }
    public JButton getBtnModificarCantidad() { return btnModificarCantidad; }
    public JButton getBtnEliminarProducto() { return btnEliminarProducto; }
    public JButton getBtnGuardarCarrito() { return btnGuardarCarrito; }
    public JButton getBtnEliminarCarrito() { return btnEliminarCarrito; }
}