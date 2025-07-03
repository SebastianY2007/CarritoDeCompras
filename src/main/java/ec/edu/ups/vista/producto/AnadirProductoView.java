package ec.edu.ups.vista.producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler; // Importar MensajeInternacionalizacionHandler

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;       // Importar Locale
import java.util.ResourceBundle; // Importar ResourceBundle

public class AnadirProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel codigoLabel;
    private JLabel nombreLabel;
    private JLabel precioLabel;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAgregar;
    private JButton btnLimpiar;

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler; // NUEVO: Referencia al handler
    private ResourceBundle mensajes; // NUEVO: Para cargar los mensajes

    // MODIFICADO: Constructor para recibir el MensajeInternacionalizacionHandler
    public AnadirProductoView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        // Carga inicial del ResourceBundle
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setContentPane(panelPrincipal);
        // El título y los textos se establecerán en updateTexts()
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        updateTexts(); // Llamar a updateTexts para establecer los textos iniciales
    }

    // NUEVO: Setter para el MensajeInternacionalizacionHandler
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        updateTexts(); // Actualizar textos si se cambia el handler dinámicamente
    }

    // NUEVO MÉTODO: updateTexts()
    public void updateTexts() {
        // Recargar el ResourceBundle para el idioma actual
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        // Actualizar el título de la ventana interna
        setTitle(mensajes.getString("anadirProducto.titulo")); // NUEVO: Clave para el título

        // Actualizar el texto de los JLabels
        codigoLabel.setText(mensajes.getString("anadirProducto.label.codigo")); // NUEVO: Clave para JLabel Código
        nombreLabel.setText(mensajes.getString("anadirProducto.label.nombre")); // NUEVO: Clave para JLabel Nombre
        precioLabel.setText(mensajes.getString("anadirProducto.label.precio")); // NUEVO: Clave para JLabel Precio

        btnAgregar.setText(mensajes.getString("anadirProducto.boton.agregar"));
        btnLimpiar.setText(mensajes.getString("anadirProducto.boton.limpiar"));

        revalidate();
        repaint();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    // Cambiado getBtnAceptar a getBtnAgregar para consistencia con la variable btnAgregar
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    // Cambiado setBtnAceptar a setBtnAgregar
    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public void mostrarMensaje(String mensaje, int tipoDeMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje del Sistema"  , tipoDeMensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
}