package ec.edu.ups.vista.producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler; // Importar MensajeInternacionalizacionHandler

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ResourceBundle; // Importar ResourceBundle
import java.util.Locale; // Importar Locale

public class GestionDeProductosView extends JInternalFrame {

    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnAgregar;
    private JLabel lblNombre;
    private DefaultTableModel modelo;

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler; // NUEVO: Referencia al handler de internacionalización
    private ResourceBundle mensajes; // NUEVO: Para cargar los mensajes


    // MODIFICADO: Constructor para recibir el MensajeInternacionalizacionHandler
    public GestionDeProductosView(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        // Carga inicial del ResourceBundle
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setContentPane(panelPrincipal);
        // El título se establecerá en updateTexts()
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        // Las columnas ahora se definen con claves de internacionalización
        Object[] columnas = {
                mensajes.getString("gestionProductos.tabla.codigo"), // NUEVO: Clave de internacionalización
                mensajes.getString("gestionProductos.tabla.nombre"), // NUEVO: Clave de internacionalización
                mensajes.getString("gestionProductos.tabla.precio")  // NUEVO: Clave de internacionalización
        };

        modelo = new DefaultTableModel(new Object[][]{}, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblProductos.setModel(modelo);

        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProductos.setRowSelectionAllowed(true);
        tblProductos.setColumnSelectionAllowed(false);

        updateTexts(); // Llamar a updateTexts para establecer los textos iniciales
    }

    // NUEVO: Setter para el MensajeInternacionalizacionHandler
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        updateTexts(); // Actualizar textos si se cambia el handler dinámicamente
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("gestionProductos.titulo"));

        // CORRECCIÓN: Se usa "boton" en lugar de "btn" para todas las claves.
        btnBuscar.setText(mensajes.getString("gestionProductos.boton.buscar"));
        btnListar.setText(mensajes.getString("gestionProductos.boton.listar"));
        btnAgregar.setText(mensajes.getString("gestionProductos.boton.agregar"));
        btnActualizar.setText(mensajes.getString("gestionProductos.boton.actualizar"));
        btnEliminar.setText(mensajes.getString("gestionProductos.boton.eliminar"));

        // También puedes actualizar la etiqueta de búsqueda si la tienes
        // lblBuscar.setText(mensajes.getString("gestionProductos.label.buscar"));

        // Actualizar encabezados de la tabla
        modelo.setColumnIdentifiers(new Object[]{
                mensajes.getString("gestionProductos.tabla.codigo"),
                mensajes.getString("gestionProductos.tabla.nombre"),
                mensajes.getString("gestionProductos.tabla.precio")
        });

        revalidate();
        repaint();
    }


    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    // ... (resto de getters y setters existentes) ...

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public MensajeInternacionalizacionHandler getMensajeInternacionalizacionHandler() {
        return mensajeInternacionalizacionHandler;
    }

    public ResourceBundle getMensajes() {
        return mensajes;
    }

    public void setMensajes(ResourceBundle mensajes) {
        this.mensajes = mensajes;
    }

    public void cargarDatos(List<Producto> listaProductos) {
        // 'modelo' debe ser la variable de tu DefaultTableModel
        modelo.setRowCount(0); // Limpia las filas existentes

        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            };
            modelo.addRow(fila);
        }
    }
}