package ec.edu.ups.poo.vista;

import javax.swing.*;

public class CarritoVIew extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField textField1;
    private JTextField textField2;
    private JButton agregarProductoButton;
    private JButton listarProductosButton;
    private JButton eliminarProductosButton;
    private JLabel lblNombre;
    private JLabel lblCantidad;
    private JCheckBox productoConDescuentoCheckBox;
    private JCheckBox productoSinDescuentoCheckBox;

    public CarritoVIew(){
        setContentPane(panelPrincipal);
        setTitle("Carrito de compras");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setIconifiable(true);
        setClosable(true);
        setResizable(true);
        setVisible(true);
    }

    public static void main(String[] args){
        new CarritoVIew();
    }
}