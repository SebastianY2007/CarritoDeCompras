package ec.edu.ups.vista.carrito;

import javax.swing.*;

public class GestionarCarritoAdministradorView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JButton btnListar;

    public GestionarCarritoAdministradorView() {
        setTitle("Gestion de Carritos (Administrador)");
        setContentPane(panelPrincipal);
        setSize(600, 450);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public JButton getBtnListar() {
        return btnListar;
    }
}