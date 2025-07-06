package ec.edu.ups.vista.carrito;

import javax.swing.*;

public class ModificarCantidadProductoCarritoView extends JDialog {
    private JPanel panelPrincipal;
    private JComboBox<Integer> cbxCantidad;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public ModificarCantidadProductoCarritoView(JFrame parent) {
        super(parent, "Modificar Cantidad", true);
        this.setContentPane(panelPrincipal);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);

        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }

    public JComboBox<Integer> getCbxCantidad() { return cbxCantidad; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}