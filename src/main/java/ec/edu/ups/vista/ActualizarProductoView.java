package ec.edu.ups.vista;

import javax.swing.*;

public class ActualizarProductoView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JComboBox cbxElegir;
    private JTextField txtElegir;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private int idActual;
    private int codigoActual;


    public ActualizarProductoView(){
        setContentPane(panelPrincipal);
        setTitle("Actualizar Producto");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        cbxElegir.addItem("Codigo");
        cbxElegir.addItem("Nombre");
        cbxElegir.addItem("Precio");
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JComboBox getCbxElegir() {
        return cbxElegir;
    }

    public void setCbxElegir(JComboBox cbxElegir) {
        this.cbxElegir = cbxElegir;
    }

    public JTextField getTxtElegir() {
        return txtElegir;
    }

    public void setTxtElegir(JTextField txtElegir) {
        this.txtElegir = txtElegir;
    }

    public void actualizarProducto(){

    }

    public int getIdActual() {
        return idActual;
    }

    public void setIdActual(int idActual) {
        this.idActual = idActual;
    }

    public int getCodigoActual() {
        return codigoActual;
    }

    public void setCodigoActual(int codigoActual) {
        this.codigoActual = codigoActual;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void limpiarCampoElegir(){
        if (txtElegir != null) {
            txtElegir.setText("");
        }
        if (cbxElegir != null && cbxElegir.getItemCount() > 0) {
            cbxElegir.setSelectedIndex(0);
        }
    }
}
