package ec.edu.ups.vista;

import ec.edu.ups.dao.UsuarioDAO;

import javax.swing.*;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JTextField txtUsername;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;

    public LoginView() {
        setContentPane(panelPrincipal);
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
    public void inicializarEventos(UsuarioDAO usuarioDAO) {
        btnRegistrarse.addActionListener(e -> {
            RegistroView registroView = new RegistroView(usuarioDAO);
            registroView.mostrar();
        });

        btnIniciarSesion.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtContrasena.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                mostrarMensaje("Ingrese usuario y contraseña.");
                return;
            }

            if (usuarioDAO.autenticar(user, pass) != null) {
                mostrarMensaje("Inicio de sesión exitoso.");
            } else {
                mostrarMensaje("Credenciales incorrectas.");
            }
        });
    }


    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JPanel getPanelSecundario() {
        return panelSecundario;
    }

    public void setPanelSecundario(JPanel panelSecundario) {
        this.panelSecundario = panelSecundario;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    public void setTxtContrasena(JPasswordField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }
}
