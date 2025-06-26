package ec.edu.ups.vista;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol; // Asegúrate de que esta importación sea correcta
import ec.edu.ups.modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmar;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JLabel lblConfirmar;
    private JButton btnRegistrarse;
    private JComboBox<String> cbxElegir;

    private UsuarioDAO usuarioDAO;

    public RegistroView(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;

        setTitle("Registro");
        setContentPane(panelPrincipal);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cbxElegir.addItem("Usuario");
        cbxElegir.addItem("Administrador");

        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());
                String confirmar = new String(txtConfirmar.getPassword());

                String rolSeleccionadoStr = (String) cbxElegir.getSelectedItem();
                Rol rol;

                if (usuario.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
                    JOptionPane.showMessageDialog(RegistroView.this, "Complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!contrasena.equals(confirmar)) {
                    JOptionPane.showMessageDialog(RegistroView.this, "Las contraseñas no coinciden.", "Error de contraseña", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (usuarioDAO.buscarPorUsername(usuario) != null) {
                    JOptionPane.showMessageDialog(RegistroView.this, "El nombre de usuario ya existe.", "Usuario existente", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if ("Administrador".equals(rolSeleccionadoStr)) {
                    rol = Rol.ADMINISTRADOR;
                } else {
                    rol = Rol.USUARIO;
                }

                Usuario nuevo = new Usuario(usuario, contrasena, rol);
                usuarioDAO.crear(nuevo);

                JOptionPane.showMessageDialog(RegistroView.this, "Usuario registrado correctamente como " + rolSeleccionadoStr + ".", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
    }

    public void mostrar() {
        setVisible(true);
    }
}