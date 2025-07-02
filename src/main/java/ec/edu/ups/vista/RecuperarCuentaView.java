package ec.edu.ups.vista;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RecuperarCuentaView extends JFrame {
    private JPanel panelPrincipal;

    private JTextField txtUsuario;
    private JButton btnValidarUsuario;

    private JLabel lblP1;
    private JTextField txtP1;
    private JLabel lblP2;
    private JTextField txtP2;
    private JLabel lblP3;
    private JTextField txtP3;
    private JButton btnValidarPreguntas;

    private JPasswordField pwdNuevaContrasena;
    private JPasswordField pwdConfirmarContrasena;
    private JButton btnCambiarContrasena;

    private UsuarioDAO usuarioDAO;
    private Usuario usuarioActual;

    public RecuperarCuentaView(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;

        setTitle("Recuperar Cuenta");
        setContentPane(panelPrincipal);
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setPreguntasSeguridadVisible(false);
        setPreguntasSeguridadEnabled(false);
        setNuevaContrasenaEnabled(false);

        btnValidarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUsuarioAction();
            }
        });

        btnValidarPreguntas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarPreguntasAction();
            }
        });

        btnCambiarContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarContrasenaAction();
            }
        });
    }

    private void setPreguntasSeguridadVisible(boolean visible) {
        lblP1.setVisible(visible);
        txtP1.setVisible(visible);
        lblP2.setVisible(visible);
        txtP2.setVisible(visible);
        lblP3.setVisible(visible);
        txtP3.setVisible(visible);
        btnValidarPreguntas.setVisible(visible);
    }

    private void setPreguntasSeguridadEnabled(boolean enabled) {
        txtP1.setEnabled(enabled);
        txtP2.setEnabled(enabled);
        txtP3.setEnabled(enabled);
        btnValidarPreguntas.setEnabled(enabled);
    }

    private void setNuevaContrasenaEnabled(boolean enabled) {
        pwdNuevaContrasena.setEnabled(enabled);
        pwdConfirmarContrasena.setEnabled(enabled);
        btnCambiarContrasena.setEnabled(enabled);
    }

    // Lógica de Acciones
    private void validarUsuarioAction() {
        String username = txtUsuario.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un nombre de usuario.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuarioActual = usuarioDAO.buscarPorUsername(username);

        if (usuarioActual != null) {
            if (usuarioActual.getPreguntaSeguridad1() != null && !usuarioActual.getPreguntaSeguridad1().isEmpty() &&
                    usuarioActual.getPreguntaSeguridad2() != null && !usuarioActual.getPreguntaSeguridad2().isEmpty() &&
                    usuarioActual.getPreguntaSeguridad3() != null && !usuarioActual.getPreguntaSeguridad3().isEmpty()) {

                lblP1.setText(usuarioActual.getPreguntaSeguridad1());
                lblP2.setText(usuarioActual.getPreguntaSeguridad2());
                lblP3.setText(usuarioActual.getPreguntaSeguridad3());

                setPreguntasSeguridadVisible(true);
                setPreguntasSeguridadEnabled(true);

                txtP1.setText("");
                txtP2.setText("");
                txtP3.setText("");

                txtUsuario.setEnabled(false);
                btnValidarUsuario.setEnabled(false);

                JOptionPane.showMessageDialog(this, "Usuario encontrado. Por favor, responda las preguntas de seguridad.", "Usuario Encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "El usuario no tiene preguntas de seguridad configuradas. Contacte a soporte.", "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
            setPreguntasSeguridadVisible(false);
            setNuevaContrasenaEnabled(false);
        }
    }

    private void validarPreguntasAction() {
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this, "Primero debe validar un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String res1 = txtP1.getText().trim();
        String res2 = txtP2.getText().trim();
        String res3 = txtP3.getText().trim();

        if (res1.isEmpty() || res2.isEmpty() || res3.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, responda todas las preguntas de seguridad.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean correcta1 = res1.equalsIgnoreCase(usuarioActual.getRespuestaSeguridad1());
        boolean correcta2 = res2.equalsIgnoreCase(usuarioActual.getRespuestaSeguridad2());
        boolean correcta3 = res3.equalsIgnoreCase(usuarioActual.getRespuestaSeguridad3());

        if (correcta1 && correcta2 && correcta3) {
            JOptionPane.showMessageDialog(this, "Respuestas correctas. Ahora puede cambiar su contraseña.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            setPreguntasSeguridadEnabled(false);

            setNuevaContrasenaEnabled(true);

            pwdNuevaContrasena.setText("");
            pwdConfirmarContrasena.setText("");

        } else {
            JOptionPane.showMessageDialog(this, "Al menos una de las respuestas es incorrecta. Inténtelo de nuevo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            txtP1.setText("");
            txtP2.setText("");
            txtP3.setText("");
        }
    }

    private void cambiarContrasenaAction() {
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this, "Error: No hay un usuario cargado para cambiar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        char[] nuevaContrasenaChars = pwdNuevaContrasena.getPassword();
        char[] confirmarContrasenaChars = pwdConfirmarContrasena.getPassword();

        String nuevaContrasena = new String(nuevaContrasenaChars);
        String confirmarContrasena = new String(confirmarContrasenaChars);

        if (nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese y confirme la nueva contraseña.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            Arrays.fill(nuevaContrasenaChars, ' ');
            Arrays.fill(confirmarContrasenaChars, ' ');
            return;
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            JOptionPane.showMessageDialog(this, "Las nuevas contraseñas no coinciden.", "Error de Contraseña", JOptionPane.ERROR_MESSAGE);
            pwdNuevaContrasena.setText("");
            pwdConfirmarContrasena.setText("");
            Arrays.fill(nuevaContrasenaChars, ' ');
            Arrays.fill(confirmarContrasenaChars, ' ');
            return;
        }

        usuarioActual.setContrasena(nuevaContrasena);
        usuarioDAO.actualizar(usuarioActual);

        JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();

        Arrays.fill(nuevaContrasenaChars, ' ');
        Arrays.fill(confirmarContrasenaChars, ' ');
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JButton getBtnValidarUsuario() {
        return btnValidarUsuario;
    }

    public void setBtnValidarUsuario(JButton btnValidarUsuario) {
        this.btnValidarUsuario = btnValidarUsuario;
    }

    public JLabel getLblP1() {
        return lblP1;
    }

    public void setLblP1(JLabel lblP1) {
        this.lblP1 = lblP1;
    }

    public JTextField getTxtP1() {
        return txtP1;
    }

    public void setTxtP1(JTextField txtP1) {
        this.txtP1 = txtP1;
    }

    public JLabel getLblP2() {
        return lblP2;
    }

    public void setLblP2(JLabel lblP2) {
        this.lblP2 = lblP2;
    }

    public JTextField getTxtP2() {
        return txtP2;
    }

    public void setTxtP2(JTextField txtP2) {
        this.txtP2 = txtP2;
    }

    public JLabel getLblP3() {
        return lblP3;
    }

    public void setLblP3(JLabel lblP3) {
        this.lblP3 = lblP3;
    }

    public JTextField getTxtP3() {
        return txtP3;
    }

    public void setTxtP3(JTextField txtP3) {
        this.txtP3 = txtP3;
    }

    public JButton getBtnValidarPreguntas() {
        return btnValidarPreguntas;
    }

    public void setBtnValidarPreguntas(JButton btnValidarPreguntas) {
        this.btnValidarPreguntas = btnValidarPreguntas;
    }

    public JPasswordField getPwdNuevaContrasena() {
        return pwdNuevaContrasena;
    }

    public void setPwdNuevaContrasena(JPasswordField pwdNuevaContrasena) {
        this.pwdNuevaContrasena = pwdNuevaContrasena;
    }

    public JPasswordField getPwdConfirmarContrasena() {
        return pwdConfirmarContrasena;
    }

    public void setPwdConfirmarContrasena(JPasswordField pwdConfirmarContrasena) {
        this.pwdConfirmarContrasena = pwdConfirmarContrasena;
    }

    public JButton getBtnCambiarContrasena() {
        return btnCambiarContrasena;
    }

    public void setBtnCambiarContrasena(JButton btnCambiarContrasena) {
        this.btnCambiarContrasena = btnCambiarContrasena;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public void mostrar() {
        setVisible(true);
    }
}