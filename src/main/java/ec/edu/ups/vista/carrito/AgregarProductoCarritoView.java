package ec.edu.ups.vista.carrito;

import javax.swing.*;

public class AgregarProductoCarritoView extends JDialog {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel txtNombre;
    private JLabel txtPrecio;
    private JComboBox<Integer> cbxCantidad;
    private JButton btnAgregar;
    private JButton btnCancelar;

    public AgregarProductoCarritoView(JFrame parent) {
        super(parent, "Agregar Producto al Carrito", true);
        this.setContentPane(panelPrincipal);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);

        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JLabel getTxtNombre() { return txtNombre; }
    public JLabel getTxtPrecio() { return txtPrecio; }
    public JComboBox<Integer> getCbxCantidad() { return cbxCantidad; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}