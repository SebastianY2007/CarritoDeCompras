package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoListaView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JLabel lblBuscar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton eliminarProductoButton;
    private JTable table1;
    private JButton listarButton;

    public ProductoListaView(){

        setContentPane(panelPrincipal);
        setTitle("Listado de Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setIconifiable(true);
        setClosable(true);
        setResizable(true);
        setVisible(true);
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JLabel getLblBuscar() {
        return lblBuscar;
    }

    public void setLblBuscar(JLabel lblBuscar) {
        this.lblBuscar = lblBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public void cargarDatos(List<Producto> listProcutos) {
        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Nombre", "Precio"};
        modelo.setColumnIdentifiers(columnas);

        for (Producto producto : listProcutos) {
            Object[] fila = {producto.getCodigo(), producto.getNombre(), producto.getPrecio()};
            modelo.addRow(fila);
        }

        tblProductos.setModel(modelo);
    }

    public static void main(String[] args){
        new ProductoListaView();
    }
}
