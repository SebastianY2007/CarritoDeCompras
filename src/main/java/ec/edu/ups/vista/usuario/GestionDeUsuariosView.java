package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Clase GestionDeUsuariosView
 *
 * Esta clase representa la vista principal (un JInternalFrame) para la gestión
 * de usuarios por parte del administrador. Permite listar, buscar, agregar,
 * actualizar y eliminar usuarios del sistema.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class GestionDeUsuariosView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JTable tblUsuarios;
    private JButton btnBuscar;
    private JButton btnListar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnAgregar;

    private DefaultTableModel tableModel;
    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Constructor de GestionDeUsuariosView.
     *
     * @param handler El manejador de internacionalización para los textos de la UI.
     */
    public GestionDeUsuariosView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(1000, 600);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        configurarTabla();
        updateTexts();
        configurarIconos();
    }

    /**
     * Redimensiona un icono a un tamaño específico.
     * @param icono El ImageIcon original.
     * @param ancho El nuevo ancho del icono.
     * @param alto El nuevo alto del icono.
     * @return un nuevo ImageIcon redimensionado.
     */
    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    /**
     * Configura los iconos para todos los botones de la vista.
     */
    private void configurarIconos() {
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_usuario.png");
        java.net.URL urlIconoEliminar = getClass().getResource("/icons/icono_basurero.png");
        java.net.URL urlIconoActualizar = getClass().getResource("/icons/icono_modificar.png");
        java.net.URL urlIconoBuscar = getClass().getResource("/icons/icono_buscar_usuario.png");
        java.net.URL urlIconoListar = getClass().getResource("/icons/icono_listar.png");

        if (urlIconoAgregar != null) {
            btnAgregar.setIcon(redimensionarIcono(new ImageIcon(urlIconoAgregar), 16, 16));
        } else {
            System.err.println("Icono no encontrado: /icons/icono_agregar_usuario.png");
        }

        if (urlIconoEliminar != null) {
            btnEliminar.setIcon(redimensionarIcono(new ImageIcon(urlIconoEliminar), 16, 16));
        } else {
            System.err.println("Icono no encontrado: /icons/icono_basurero.png");
        }

        if (urlIconoActualizar != null) {
            btnActualizar.setIcon(redimensionarIcono(new ImageIcon(urlIconoActualizar), 16, 16));
        } else {
            System.err.println("Icono no encontrado: /icons/icono_modificar.png");
        }

        if (urlIconoBuscar != null) {
            btnBuscar.setIcon(redimensionarIcono(new ImageIcon(urlIconoBuscar), 16, 16));
        } else {
            System.err.println("Icono no encontrado: /icons/icono_buscar_usuario.png");
        }

        if (urlIconoListar != null) {
            btnListar.setIcon(redimensionarIcono(new ImageIcon(urlIconoListar), 16, 16));
        } else {
            System.err.println("Icono no encontrado: /icons/icono_listar.png");
        }
    }

    /**
     * Configura el modelo inicial de la tabla de usuarios.
     * Establece que las celdas no sean editables directamente por el usuario.
     */
    private void configurarTabla() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuarios.setModel(tableModel);
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("gestionUsuarios.titulo"));
        lblNombre.setText(mensajes.getString("gestionUsuarios.label.buscar"));
        btnBuscar.setText(mensajes.getString("global.boton.buscar"));
        btnListar.setText(mensajes.getString("global.boton.listar"));
        btnAgregar.setText(mensajes.getString("global.boton.agregar"));
        btnActualizar.setText(mensajes.getString("global.boton.actualizar"));
        btnEliminar.setText(mensajes.getString("global.boton.eliminar"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.getString("gestionUsuarios.tabla.username"),
                mensajes.getString("gestionUsuarios.tabla.email"),
                mensajes.getString("gestionUsuarios.tabla.rol"),
                mensajes.getString("gestionUsuarios.tabla.nombre")
        });
    }

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    /**
     * Obtiene el panel principal de la vista.
     * @return el JPanel principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Obtiene la etiqueta del campo de búsqueda.
     * @return la JLabel de búsqueda.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Obtiene el campo de texto para buscar usuarios (por cédula).
     * @return el JTextField de búsqueda.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Obtiene la tabla donde se muestran los usuarios.
     * @return el JTable de usuarios.
     */
    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    /**
     * Obtiene el botón para buscar usuarios.
     * @return el JButton de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene el botón para listar todos los usuarios.
     * @return el JButton de listar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Obtiene el botón para eliminar un usuario.
     * @return el JButton de eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Obtiene el botón para actualizar un usuario.
     * @return el JButton de actualizar.
     */
    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    /**
     * Obtiene el botón para agregar un nuevo usuario.
     * @return el JButton de agregar.
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }
}
