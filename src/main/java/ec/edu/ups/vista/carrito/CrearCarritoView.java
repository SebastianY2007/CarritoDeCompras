package ec.edu.ups.vista.carrito;

import javax.swing.*;

public class CrearCarritoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JComboBox<Integer> cbxCantidad;
    private JButton btnAgregar;
    private JTable tblProductos;
    private JButton btnCrearCarrito;
    private JLabel txtNombre;
    private JLabel txtPrecio;
    private JLabel txtSubtotal;
    private JLabel txtIVA;
    private JLabel txtTotal;

    public CrearCarritoView() {
        setTitle("Crear Carrito");
        setContentPane(panelPrincipal);
        setSize(600, 500);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }

    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JComboBox<Integer> getCbxCantidad() { return cbxCantidad; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JTable getTblProductos() { return tblProductos; }
    public JButton getBtnCrearCarrito() { return btnCrearCarrito; }
    public JLabel getTxtNombre() { return txtNombre; }
    public JLabel getTxtPrecio() { return txtPrecio; }
    public JLabel getTxtSubtotal() { return txtSubtotal; }
    public JLabel getTxtIVA() { return txtIVA; }
    public JLabel getTxtTotal() { return txtTotal; }
}