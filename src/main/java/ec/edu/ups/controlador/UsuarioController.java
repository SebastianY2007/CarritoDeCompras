package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.usuario.ActualizarUsuarioView;
import ec.edu.ups.vista.usuario.AnadirUsuarioView;
import ec.edu.ups.vista.usuario.GestionDeUsuariosView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final GestionDeUsuariosView gestionDeUsuariosView;
    private final AnadirUsuarioView anadirUsuarioView;
    private final ActualizarUsuarioView actualizarUsuarioView;
    private final JDesktopPane desktopPane;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    private Usuario usuarioParaActualizar;

    public UsuarioController(UsuarioDAO usuarioDAO, GestionDeUsuariosView gestionDeUsuariosView, AnadirUsuarioView anadirUsuarioView, ActualizarUsuarioView actualizarUsuarioView, JDesktopPane desktopPane, MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioDAO = usuarioDAO;
        this.gestionDeUsuariosView = gestionDeUsuariosView;
        this.anadirUsuarioView = anadirUsuarioView;
        this.actualizarUsuarioView = actualizarUsuarioView;
        this.desktopPane = desktopPane;
        this.mensajeHandler = mensajeHandler;
    }

    public void initListeners() {
        gestionDeUsuariosView.getBtnListar().addActionListener(e -> listarUsuarios());
        gestionDeUsuariosView.getBtnBuscar().addActionListener(e -> buscarUsuarioPorUsername());
        gestionDeUsuariosView.getBtnAgregar().addActionListener(e -> abrirAnadirUsuario());
        gestionDeUsuariosView.getBtnEliminar().addActionListener(e -> eliminarUsuarioSeleccionado());
        gestionDeUsuariosView.getBtnActualizar().addActionListener(e -> abrirDialogoActualizar());

        anadirUsuarioView.getBtnAgregar().addActionListener(e -> crearNuevoUsuario());
        anadirUsuarioView.getBtnLimpiar().addActionListener(e -> limpiarCamposAnadirUsuario());

        actualizarUsuarioView.getBtnActualizar().addActionListener(e -> actualizarCampoSeleccionado());
        actualizarUsuarioView.getBtnCancelar().addActionListener(e -> actualizarUsuarioView.dispose());
    }

    public void listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        DefaultTableModel model = (DefaultTableModel) gestionDeUsuariosView.getTblUsuarios().getModel();
        model.setColumnIdentifiers(new Object[]{"Username", "Email", "Rol"});
        model.setRowCount(0);
        for (Usuario u : usuarios) {
            model.addRow(new Object[]{u.getUsername(), u.getCorreoElectronico(), u.getRol().name()});
        }
    }

    private void buscarUsuarioPorUsername() {
        String username = gestionDeUsuariosView.getTxtNombre().getText();
        if (username.trim().isEmpty()) {
            listarUsuarios();
            return;
        }
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        DefaultTableModel model = (DefaultTableModel) gestionDeUsuariosView.getTblUsuarios().getModel();
        model.setRowCount(0);
        if (usuario != null) {
            model.addRow(new Object[]{usuario.getUsername(), usuario.getCorreoElectronico(), usuario.getRol().name()});
        } else {
            JOptionPane.showMessageDialog(gestionDeUsuariosView, "Usuario no encontrado.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void abrirAnadirUsuario() {
        limpiarCamposAnadirUsuario();
        if (!anadirUsuarioView.isVisible()) {
            desktopPane.add(anadirUsuarioView);
            anadirUsuarioView.setVisible(true);
        }
        anadirUsuarioView.toFront();
    }

    private void crearNuevoUsuario() {
        String username = anadirUsuarioView.getTxtNombreUsuario().getText();
        String password = new String(anadirUsuarioView.getTxtContrasena().getPassword());
        String confirmPassword = new String(anadirUsuarioView.getTxtConfirmarContrasena().getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(anadirUsuarioView, "Username y Contraseña son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(anadirUsuarioView, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (usuarioDAO.buscarPorUsername(username) != null) {
            JOptionPane.showMessageDialog(anadirUsuarioView, "El nombre de usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, password, "email@por.defecto");
        nuevoUsuario.setRol(Rol.USUARIO);

        usuarioDAO.crear(nuevoUsuario);
        JOptionPane.showMessageDialog(anadirUsuarioView, "Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        anadirUsuarioView.dispose();
        listarUsuarios();
    }

    private void limpiarCamposAnadirUsuario() {
        anadirUsuarioView.getTxtNombreUsuario().setText("");
        anadirUsuarioView.getTxtContrasena().setText("");
        anadirUsuarioView.getTxtConfirmarContrasena().setText("");
    }

    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = gestionDeUsuariosView.getTblUsuarios().getSelectedRow();
        if (filaSeleccionada >= 0) {
            String username = (String) gestionDeUsuariosView.getTblUsuarios().getValueAt(filaSeleccionada, 0);

            if (username.equals("admin")) {
                JOptionPane.showMessageDialog(gestionDeUsuariosView, "No se puede eliminar al usuario administrador principal.", "Acción no permitida", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(gestionDeUsuariosView, "¿Seguro que desea eliminar al usuario '" + username + "'?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                usuarioDAO.eliminar(username);
                listarUsuarios();
            }
        } else {
            JOptionPane.showMessageDialog(gestionDeUsuariosView, "Por favor, seleccione un usuario de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirDialogoActualizar() {
        int filaSeleccionada = gestionDeUsuariosView.getTblUsuarios().getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(gestionDeUsuariosView, "Por favor, seleccione un usuario de la tabla para actualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = (String) gestionDeUsuariosView.getTblUsuarios().getValueAt(filaSeleccionada, 0);
        this.usuarioParaActualizar = usuarioDAO.buscarPorUsername(username);

        if (usuarioParaActualizar != null) {
            actualizarUsuarioView.getTxtNuevoValor().setText("");
            if (!actualizarUsuarioView.isVisible()) {
                desktopPane.add(actualizarUsuarioView);
                actualizarUsuarioView.setVisible(true);
            }
            actualizarUsuarioView.toFront();
        }
    }

    private void actualizarCampoSeleccionado() {
        if (usuarioParaActualizar == null) {
            JOptionPane.showMessageDialog(actualizarUsuarioView, "No hay un usuario seleccionado para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String campo = (String) actualizarUsuarioView.getCbxCampo().getSelectedItem();
        String nuevoValor = actualizarUsuarioView.getTxtNuevoValor().getText();

        if (nuevoValor.trim().isEmpty() && !campo.equalsIgnoreCase("Contraseña")) {
            JOptionPane.showMessageDialog(actualizarUsuarioView, "El nuevo valor no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (campo.toLowerCase()) {
            case "nombre de usuario":
                if (usuarioDAO.buscarPorUsername(nuevoValor) != null && !nuevoValor.equals(usuarioParaActualizar.getUsername())) {
                    JOptionPane.showMessageDialog(actualizarUsuarioView, "El nuevo nombre de usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usuarioDAO.eliminar(usuarioParaActualizar.getUsername());
                usuarioParaActualizar.setUsername(nuevoValor);
                usuarioDAO.crear(usuarioParaActualizar);
                break;
            case "email":
                usuarioParaActualizar.setCorreoElectronico(nuevoValor);
                usuarioDAO.actualizar(usuarioParaActualizar);
                break;
            case "rol":
                try {
                    Rol nuevoRol = Rol.valueOf(nuevoValor.toUpperCase());
                    usuarioParaActualizar.setRol(nuevoRol);
                    usuarioDAO.actualizar(usuarioParaActualizar);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(actualizarUsuarioView, "Rol no válido. Ingrese 'USUARIO' o 'ADMINISTRADOR'.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                break;
            case "contraseña":
                if (nuevoValor.length() < 8 && !nuevoValor.isEmpty()) {
                    JOptionPane.showMessageDialog(actualizarUsuarioView, "La contraseña debe tener al menos 8 caracteres.", "Contraseña Débil", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!nuevoValor.isEmpty()){
                    usuarioParaActualizar.setContrasena(nuevoValor);
                    usuarioDAO.actualizar(usuarioParaActualizar);
                } else {
                    JOptionPane.showMessageDialog(actualizarUsuarioView, "No se realizaron cambios en la contraseña (campo vacío).", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                break;
        }

        JOptionPane.showMessageDialog(actualizarUsuarioView, "Usuario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        actualizarUsuarioView.dispose();
        listarUsuarios();
    }

    public boolean actualizarContrasena(String username, String nuevaContrasena) throws Exception {
        if (nuevaContrasena == null || nuevaContrasena.trim().isEmpty()) {
            throw new Exception("La contraseña no puede estar vacía.");
        }
        if (nuevaContrasena.length() < 3) {
            throw new Exception("La contraseña debe tener al menos 3 caracteres.");
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario != null) {
            usuario.setContrasena(nuevaContrasena);

            usuarioDAO.actualizar(usuario);

            return true;
        }

        return false;
    }
}