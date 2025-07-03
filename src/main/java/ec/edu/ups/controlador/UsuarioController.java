package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.vista.usuario.AnadirUsuarioView;
import ec.edu.ups.vista.usuario.GestionDeUsuariosView;
import ec.edu.ups.vista.usuario.ActualizarUsuarioView;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class UsuarioController {

    // --- Vistas que controla ---
    private GestionDeUsuariosView gestionDeUsuariosView;
    private AnadirUsuarioView anadirUsuarioView;
    private ActualizarUsuarioView actualizarUsuarioView;

    // --- Modelo ---
    private UsuarioDAO usuarioDAO;

    // --- Utilidades ---
    private JDesktopPane desktopPane;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public UsuarioController(UsuarioDAO usuarioDAO, GestionDeUsuariosView gestionDeUsuariosView, AnadirUsuarioView anadirUsuarioView, ActualizarUsuarioView actualizarUsuarioView, JDesktopPane desktopPane, MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioDAO = usuarioDAO;
        this.gestionDeUsuariosView = gestionDeUsuariosView;
        this.anadirUsuarioView = anadirUsuarioView;
        this.actualizarUsuarioView = actualizarUsuarioView;
        this.desktopPane = desktopPane;
        this.mensajeHandler = mensajeHandler;

        if (this.gestionDeUsuariosView != null) this.gestionDeUsuariosView.setUsuarioController(this);
        if (this.anadirUsuarioView != null) this.anadirUsuarioView.setUsuarioController(this);
        if (this.actualizarUsuarioView != null) this.actualizarUsuarioView.setUsuarioController(this);

        addListeners();
    }

    private void addListeners() {
        if (gestionDeUsuariosView != null) {
            gestionDeUsuariosView.getBtnBuscar().addActionListener(e -> buscarUsuario());
            gestionDeUsuariosView.getBtnListar().addActionListener(e -> listarUsuarios());
            gestionDeUsuariosView.getBtnEliminar().addActionListener(e -> eliminarUsuario());
            gestionDeUsuariosView.getBtnActualizar().addActionListener(e -> abrirVentanaActualizar());
            gestionDeUsuariosView.getBtnAgregar().addActionListener(e -> abrirVentanaAnadir());
        }

        if (anadirUsuarioView != null) {
            anadirUsuarioView.getBtnAgregar().addActionListener(e -> anadirUsuario());
            anadirUsuarioView.getBtnLimpiar().addActionListener(e -> anadirUsuarioView.limpiarCampos());
        }

        if (actualizarUsuarioView != null) {
            actualizarUsuarioView.getBtnActualizar().addActionListener(e -> actualizarUsuario());
            actualizarUsuarioView.getBtnCancelar().addActionListener(e -> actualizarUsuarioView.dispose());
        }
    }

    // --- Lógica de la Vista de Añadir ---
    public void anadirUsuario() {
        String username = anadirUsuarioView.getTxtNombreUsuario().getText().trim();
        String contrasena = new String(anadirUsuarioView.getTxtContrasena().getPassword()).trim();
        String confirmarContrasena = new String(anadirUsuarioView.getTxtConfirmarContrasena().getPassword()).trim();

        if (username.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(anadirUsuarioView, "El nombre de usuario y la contraseña son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!contrasena.equals(confirmarContrasena)) {
            JOptionPane.showMessageDialog(anadirUsuarioView, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (usuarioDAO.buscarPorUsername(username) != null) {
            JOptionPane.showMessageDialog(anadirUsuarioView, "El nombre de usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, contrasena, "", username);
        nuevoUsuario.setRol(Rol.USUARIO);
        usuarioDAO.crear(nuevoUsuario);

        JOptionPane.showMessageDialog(anadirUsuarioView, "Usuario añadido exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        anadirUsuarioView.dispose();
        listarUsuarios();
    }

    // --- Lógica de la Vista de Gestión ---
    public void listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        DefaultTableModel model = (DefaultTableModel) gestionDeUsuariosView.getTblUsuarios().getModel();
        model.setRowCount(0);
        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{usuario.getUsername(), usuario.getCorreoElectronico(), usuario.getRol().name(), usuario.getNombre()});
        }
    }

    public void abrirVentanaAnadir() {
        if (anadirUsuarioView != null) {
            anadirUsuarioView.limpiarCampos();
            mostrarVentana(anadirUsuarioView);
        }
    }

    // --- Lógica para los botones que faltaban ---

    public void buscarUsuario() {
        String username = gestionDeUsuariosView.getTxtBuscar().getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(gestionDeUsuariosView, "Por favor, ingrese un nombre de usuario para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        DefaultTableModel model = (DefaultTableModel) gestionDeUsuariosView.getTblUsuarios().getModel();
        model.setRowCount(0);
        if (usuario != null) {
            model.addRow(new Object[]{usuario.getUsername(), usuario.getCorreoElectronico(), usuario.getRol().name(), usuario.getNombre()});
        } else {
            JOptionPane.showMessageDialog(gestionDeUsuariosView, "Usuario no encontrado.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void eliminarUsuario() {
        int selectedRow = gestionDeUsuariosView.getTblUsuarios().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(gestionDeUsuariosView, "Por favor, seleccione un usuario de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String username = (String) gestionDeUsuariosView.getTblUsuarios().getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(gestionDeUsuariosView, "¿Está seguro de que desea eliminar al usuario '" + username + "'?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            usuarioDAO.eliminar(username);
            listarUsuarios();
            JOptionPane.showMessageDialog(gestionDeUsuariosView, "Usuario eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void abrirVentanaActualizar() {
        int selectedRow = gestionDeUsuariosView.getTblUsuarios().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(gestionDeUsuariosView, "Por favor, seleccione un usuario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String username = (String) gestionDeUsuariosView.getTblUsuarios().getValueAt(selectedRow, 0);
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario != null && actualizarUsuarioView != null) {
            actualizarUsuarioView.cargarDatosUsuario(usuario);
            mostrarVentana(actualizarUsuarioView);
        }
    }

    public void actualizarUsuario() {
        String usernameOriginal = actualizarUsuarioView.getUsernameActual();
        if (usernameOriginal == null) {
            JOptionPane.showMessageDialog(actualizarUsuarioView, "No hay un usuario cargado para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String campoSeleccionado = (String) actualizarUsuarioView.getCbxElegir().getSelectedItem();
        String nuevoValor = actualizarUsuarioView.getTxtValorNuevo().getText().trim();

        if (nuevoValor.isEmpty()) {
            JOptionPane.showMessageDialog(actualizarUsuarioView, "El nuevo valor no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(usernameOriginal);
        ResourceBundle mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        if (campoSeleccionado.equals(mensajes.getString("actualizarUsuario.opcion.username"))) {
            usuario.setUsername(nuevoValor);
        } else if (campoSeleccionado.equals(mensajes.getString("actualizarUsuario.opcion.contrasena"))) {
            usuario.setContrasena(nuevoValor);
        } else if (campoSeleccionado.equals(mensajes.getString("actualizarUsuario.opcion.email"))) {
            usuario.setCorreoElectronico(nuevoValor);
        } else if (campoSeleccionado.equals(mensajes.getString("actualizarUsuario.opcion.rol"))) {
            String rolUpper = nuevoValor.toUpperCase();
            if (rolUpper.equals("USUARIO") || rolUpper.equals("ADMINISTRADOR")) {
                usuario.setRol(Rol.valueOf(rolUpper));
            } else {
                JOptionPane.showMessageDialog(actualizarUsuarioView, "Rol inválido. Ingrese USUARIO o ADMINISTRADOR.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        usuarioDAO.actualizar(usuario);

        JOptionPane.showMessageDialog(actualizarUsuarioView, "Usuario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        actualizarUsuarioView.dispose();
        listarUsuarios();
    }

    private void mostrarVentana(JInternalFrame frame) {
        if (frame != null) {
            if (!frame.isVisible()) {
                desktopPane.add(frame);
                frame.setVisible(true);
            }
            frame.toFront();
        }
    }
}
