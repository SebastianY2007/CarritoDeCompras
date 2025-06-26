package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.RegistroView;

import javax.swing.*;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;
    private LoginView loginView;
    private Usuario usuarioAutenticado;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;

        initListeners();
    }

    private void initListeners() {
        loginView.getBtnIniciarSesion().addActionListener(e -> {
            String username = loginView.getTxtUsername().getText();
            String password = new String(loginView.getTxtContrasena().getPassword());

            Usuario usuario = usuarioDAO.autenticar(username, password);
            if (usuario != null) {
                usuarioAutenticado = usuario;
                loginView.mostrarMensaje("Inicio de sesión exitoso.");
                loginView.dispose();
            } else {
                loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
            }
        });

        loginView.getBtnRegistrarse().addActionListener(e -> {
            RegistroView registroView = new RegistroView(usuarioDAO); // pasar mismo DAO
            registroView.setVisible(true);
        });
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}
