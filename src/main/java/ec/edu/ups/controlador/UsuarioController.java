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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;
    private AnadirUsuarioView anadirUsuarioView;
    private GestionDeUsuariosView gestionDeUsuariosView;
    private ActualizarUsuarioView actualizarUsuarioView;
    private JDesktopPane desktopPane;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    public UsuarioController(UsuarioDAO usuarioDAO, AnadirUsuarioView anadirUsuarioView,
                             GestionDeUsuariosView gestionDeUsuariosView, ActualizarUsuarioView actualizarUsuarioView,
                             JDesktopPane desktopPane, MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.usuarioDAO = usuarioDAO;
        this.anadirUsuarioView = anadirUsuarioView;
        this.gestionDeUsuariosView = gestionDeUsuariosView;
        this.actualizarUsuarioView = actualizarUsuarioView;
        this.desktopPane = desktopPane;
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;

        if (this.anadirUsuarioView != null) {
            this.anadirUsuarioView.setUsuarioController(this);
            this.anadirUsuarioView.setMensajeInternacionalizacionHandler(mensajeInternacionalizacionHandler);
        }
        if (this.gestionDeUsuariosView != null) {
            this.gestionDeUsuariosView.setUsuarioController(this);
            this.gestionDeUsuariosView.setMensajeInternacionalizacionHandler(mensajeInternacionalizacionHandler);
            this.gestionDeUsuariosView.setAnadirUsuarioView(this.anadirUsuarioView);
            this.gestionDeUsuariosView.setActualizarUsuarioView(this.actualizarUsuarioView);
        }
        if (this.actualizarUsuarioView != null) {
            this.actualizarUsuarioView.setUsuarioController(this);
            // Asegurarse de que ActualizarUsuarioView tenga este setter
            this.actualizarUsuarioView.setMensajeInternacionalizacionHandler(mensajeInternacionalizacionHandler);
        }

        setupAnadirUsuarioViewListeners();
        setupActualizarUsuarioViewListeners();
        setupGestionDeUsuariosViewListeners();
    }

    private void setupAnadirUsuarioViewListeners() {
        if (anadirUsuarioView != null) {
            anadirUsuarioView.getBtnAgregar().addActionListener(e -> agregarUsuarioDesdeVista());
            anadirUsuarioView.getBtnLimpiar().addActionListener(e -> anadirUsuarioView.limpiarCampos());
        }
    }

    private void setupActualizarUsuarioViewListeners() {
        if (actualizarUsuarioView != null) {
            actualizarUsuarioView.getBtnActualizar().addActionListener(e -> actualizarUsuarioDesdeVista());
            actualizarUsuarioView.getBtnCancelar().addActionListener(e -> {
                actualizarUsuarioView.dispose();
                actualizarUsuarioView.limpiarCampos();
            });
        }
    }

    private void setupGestionDeUsuariosViewListeners() {
        if (gestionDeUsuariosView != null) {
            gestionDeUsuariosView.getBtnListar().addActionListener(e -> listarUsuarios());
            gestionDeUsuariosView.getBtnBuscar().addActionListener(e -> {
                String username = gestionDeUsuariosView.getTxtBuscar().getText().trim();
                if (!username.isEmpty()) {
                    Usuario foundUser = buscarUsuario(username);
                    DefaultTableModel model = gestionDeUsuariosView.getModelo();
                    model.setRowCount(0);
                    if (foundUser != null) {
                        model.addRow(new Object[]{
                                foundUser.getUsername(),
                                foundUser.getCorreoElectronico(),
                                foundUser.getRol().name(),
                                foundUser.getNombre(),
                        });
                    } else {
                        gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.usuarioNoEncontrado"), JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.ingreseUsuarioBuscar"), JOptionPane.WARNING_MESSAGE);
                }
            });

            gestionDeUsuariosView.getBtnEliminar().addActionListener(e -> {
                int selectedRow = gestionDeUsuariosView.getTblUsuarios().getSelectedRow();
                if (selectedRow >= 0) {
                    String username = (String) gestionDeUsuariosView.getModelo().getValueAt(selectedRow, 0);
                    eliminarUsuario(username);
                } else {
                    gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.seleccioneUsuarioEliminar"), JOptionPane.WARNING_MESSAGE);
                }
            });

            gestionDeUsuariosView.getBtnActualizar().addActionListener(e -> {
                int selectedRow = gestionDeUsuariosView.getTblUsuarios().getSelectedRow();
                if (selectedRow >= 0) {
                    String username = (String) gestionDeUsuariosView.getModelo().getValueAt(selectedRow, 0);
                    Usuario usuarioAActualizar = buscarUsuario(username);
                    if (usuarioAActualizar != null) {
                        if (actualizarUsuarioView != null) {
                            actualizarUsuarioView.cargarDatosUsuario(usuarioAActualizar);
                            if (desktopPane != null && !actualizarUsuarioView.isVisible()) {
                                desktopPane.add(actualizarUsuarioView);
                                actualizarUsuarioView.setVisible(true);
                            }
                            actualizarUsuarioView.toFront();
                        } else {
                            gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.vistaActualizarNoInicializada"), JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.errorCargarDatosActualizar"), JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.seleccioneUsuarioActualizar"), JOptionPane.WARNING_MESSAGE);
                }
            });

            gestionDeUsuariosView.getBtnAgregar().addActionListener(e -> {
                if (anadirUsuarioView != null) {
                    if (desktopPane != null && !anadirUsuarioView.isVisible()) {
                        desktopPane.add(anadirUsuarioView);
                        anadirUsuarioView.setVisible(true);
                    }
                    anadirUsuarioView.toFront();
                    anadirUsuarioView.limpiarCampos();
                } else {
                    gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.vistaAnadirNoInicializada"), JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    public void agregarUsuarioDesdeVista() {
        String username = anadirUsuarioView.getTxtNombreUsuario().getText().trim();
        String password = new String(anadirUsuarioView.getTxtContrasena().getPassword());
        String confirmPassword = new String(anadirUsuarioView.getTxtConfirmarContrasena().getPassword());
        String nombre = anadirUsuarioView.getTxtNombre().getText().trim();
        String apellido = anadirUsuarioView.getTxtApellido().getText().trim();
        String email = anadirUsuarioView.getTxtEmail().getText().trim();
        String telefono = anadirUsuarioView.getTxtTelefono().getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            anadirUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("anadirUsuario.mensaje.camposObligatorios"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            anadirUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("anadirUsuario.mensaje.contrasenasNoCoinciden"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            anadirUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("anadirUsuario.mensaje.usuarioExiste"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, password, email, nombre);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setRol(Rol.USUARIO);

        try { // Manteniendo el try-catch
            usuarioDAO.crear(nuevoUsuario);
            anadirUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("anadirUsuario.mensaje.exito"), JOptionPane.INFORMATION_MESSAGE);
            anadirUsuarioView.limpiarCampos();
            anadirUsuarioView.dispose();
            listarUsuarios();
        } catch (Exception e) {
            anadirUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("anadirUsuario.mensaje.errorAgregar") + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Usuario buscarUsuario(String username) {
        return usuarioDAO.buscarPorUsername(username);
    }

    public void actualizarUsuarioDesdeVista() {
        String usernameOriginal = actualizarUsuarioView.getUsernameActual();
        String campo = (String) actualizarUsuarioView.getCbxElegir().getSelectedItem();
        String nuevoValor = actualizarUsuarioView.getTxtValorNuevo().getText().trim();

        if (usernameOriginal == null || usernameOriginal.isEmpty()) {
            actualizarUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("actualizarUsuario.mensaje.noUsuarioSeleccionado"), JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (nuevoValor.isEmpty()) {
            actualizarUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("actualizarUsuario.mensaje.valorVacio"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuarioAActualizar = usuarioDAO.buscarPorUsername(usernameOriginal);
        if (usuarioAActualizar == null) {
            actualizarUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("actualizarUsuario.mensaje.usuarioNoEncontrado"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        try { // Manteniendo el try-catch
            String campoUsername = mensajeInternacionalizacionHandler.get("actualizarUsuario.campo.username");
            String campoEmail = mensajeInternacionalizacionHandler.get("actualizarUsuario.campo.email");
            String campoContrasena = mensajeInternacionalizacionHandler.get("actualizarUsuario.campo.contrasena");
            String campoRol = mensajeInternacionalizacionHandler.get("actualizarUsuario.campo.rol");
            String campoNombre = mensajeInternacionalizacionHandler.get("actualizarUsuario.campo.nombre");
            String campoApellido = mensajeInternacionalizacionHandler.get("actualizarUsuario.campo.apellido");
            String campoTelefono = mensajeInternacionalizacionHandler.get("actualizarUsuario.campo.telefono");

            if (campo.equals(campoUsername)) {
                if (usuarioDAO.buscarPorUsername(nuevoValor) != null && !nuevoValor.equals(usernameOriginal)) {
                    actualizarUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("actualizarUsuario.mensaje.nuevoUsuarioExiste"), JOptionPane.WARNING_MESSAGE);
                    return;
                }
                usuarioAActualizar.setUsername(nuevoValor);

            } else if (campo.equals(campoEmail)) {
                usuarioAActualizar.setCorreoElectronico(nuevoValor);
            } else if (campo.equals(campoContrasena)) {
                usuarioAActualizar.setContrasena(nuevoValor);
            } else if (campo.equals(campoRol)) {
                try {
                    Rol nuevoRol = Rol.valueOf(nuevoValor.toUpperCase());
                    usuarioAActualizar.setRol(nuevoRol);
                } catch (IllegalArgumentException e) {
                    actualizarUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("actualizarUsuario.mensaje.rolInvalido"), JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (campo.equals(campoNombre)) {
                usuarioAActualizar.setNombre(nuevoValor);
            } else if (campo.equals(campoTelefono)) {
                usuarioAActualizar.setTelefono(nuevoValor);
            } else {
                actualizarUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("actualizarUsuario.mensaje.campoNoReconocido"), JOptionPane.WARNING_MESSAGE);
                return;
            }

            usuarioDAO.actualizar(usuarioAActualizar);
            actualizarUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("actualizarUsuario.mensaje.exito"), JOptionPane.INFORMATION_MESSAGE);
            actualizarUsuarioView.dispose();
            actualizarUsuarioView.limpiarCampos();
            listarUsuarios();
        } catch (Exception e) {
            actualizarUsuarioView.mostrarMensaje(mensajeInternacionalizacionHandler.get("actualizarUsuario.mensaje.errorActualizar") + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void eliminarUsuario(String username) {
        int confirm = JOptionPane.showConfirmDialog(gestionDeUsuariosView,
                mensajeInternacionalizacionHandler.get("gestionUsuarios.confirmarEliminar.pregunta") + username + "?",
                mensajeInternacionalizacionHandler.get("gestionUsuarios.confirmarEliminar.titulo"),
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try { // Manteniendo el try-catch
                usuarioDAO.eliminar(username);
                gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.usuarioEliminado"), JOptionPane.INFORMATION_MESSAGE);
                listarUsuarios();
            } catch (Exception e) {
                gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.errorEliminar") + e.getMessage(), JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void listarUsuarios() {
        DefaultTableModel model = gestionDeUsuariosView.getModelo();
        model.setRowCount(0);

        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{
                    usuario.getUsername(),
                    usuario.getCorreoElectronico(),
                    usuario.getRol().name(),
                    usuario.getNombre(),
            });
        }
        if (usuarios.isEmpty()) {
            gestionDeUsuariosView.mostrarMensaje(mensajeInternacionalizacionHandler.get("gestionUsuarios.mensaje.noUsuarios"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public AnadirUsuarioView getAnadirUsuarioView() {
        return anadirUsuarioView;
    }

    public GestionDeUsuariosView getGestionDeUsuariosView() {
        return gestionDeUsuariosView;
    }

    public ActualizarUsuarioView getActualizarUsuarioView() {
        return actualizarUsuarioView;
    }

    // El método autenticarUsuario que solicitaste
    public Usuario autenticarUsuario(String username, String password) {
        // 1. Buscar el usuario por nombre de usuario
        Usuario usuario = usuarioDAO.buscarPorUsername(username); // Asume que UsuarioDAO tiene este método 'buscarPorUsername'

        // 2. Verificar si el usuario existe y si la contraseña coincide
        // CORRECCIÓN: Usar getContrasena() en lugar de getPassword() si ese es el getter en tu modelo Usuario
        if (usuario != null && usuario.getContrasena().equals(password)) {
            return usuario; // Autenticación exitosa
        } else {
            return null; // Autenticación fallida
        }
    }
}