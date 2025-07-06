package ec.edu.ups.vista.registro;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmar;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JComboBox<String> cbxP1;
    private JTextField txtP1;
    private JComboBox<String> cbxP2;
    private JTextField txtP2;
    private JComboBox<String> cbxP3;
    private JTextField txtP3;
    private JComboBox<Integer> cbxDia;
    private JComboBox<String> cbxMes;
    private JComboBox<Integer> cbxAnio;
    private JButton btnRegistrarse;

    private UsuarioDAO usuarioDAO;
    private PreguntaSeguridadDAO preguntaSeguridadDAO;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;
    private final String MENSAJE_SELECCIONE_PREGUNTA_KEY = "registro.pregunta.seleccionar";

    public RegistroView(UsuarioDAO usuarioDAO, PreguntaSeguridadDAO preguntaSeguridadDAO, MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaSeguridadDAO = preguntaSeguridadDAO;
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("registro.title"));
        setContentPane(panelPrincipal);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        SwingUtilities.invokeLater(() -> {
            setupListeners();
            setupFechaNacimientoComboBoxes();
            cargarPreguntasSeguridad();
            updateTexts();
        });
    }

    private void setupListeners() {
        btnRegistrarse.addActionListener(e -> registrarUsuario());

        ItemListener listener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                JComboBox<?> sourceComboBox = (JComboBox<?>) e.getSource();
                JTextField targetTextField = null;
                if (sourceComboBox == cbxP1) targetTextField = txtP1;
                else if (sourceComboBox == cbxP2) targetTextField = txtP2;
                else if (sourceComboBox == cbxP3) targetTextField = txtP3;

                if (targetTextField != null) {
                    boolean isPlaceholder = sourceComboBox.getSelectedItem().equals(mensajes.getString(MENSAJE_SELECCIONE_PREGUNTA_KEY));
                    targetTextField.setEnabled(!isPlaceholder);
                    if (isPlaceholder) {
                        targetTextField.setText("");
                    }
                }
            }
        };
        cbxP1.addItemListener(listener);
        cbxP2.addItemListener(listener);
        cbxP3.addItemListener(listener);

        cbxMes.addActionListener(e -> updateCbxDia());
        cbxAnio.addActionListener(e -> updateCbxDia());
    }

    private void registrarUsuario() {
        String username = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
        String confirmar = new String(txtConfirmar.getPassword()).trim();
        String nombre = txtNombre.getText().trim();
        String apellido = "";
        String correoElectronico = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        Integer dia = (Integer) cbxDia.getSelectedItem();
        String mesStr = (String) cbxMes.getSelectedItem();
        Integer anio = (Integer) cbxAnio.getSelectedItem();
        String pregunta1 = (String) cbxP1.getSelectedItem();
        String respuesta1 = txtP1.getText().trim();
        String pregunta2 = (String) cbxP2.getSelectedItem();
        String respuesta2 = txtP2.getText().trim();
        String pregunta3 = (String) cbxP3.getSelectedItem();
        String respuesta3 = txtP3.getText().trim();

        if (username.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || correoElectronico.isEmpty() ||
                pregunta1.equals(mensajes.getString(MENSAJE_SELECCIONE_PREGUNTA_KEY)) || respuesta1.isEmpty() ||
                pregunta2.equals(mensajes.getString(MENSAJE_SELECCIONE_PREGUNTA_KEY)) || respuesta2.isEmpty() ||
                pregunta3.equals(mensajes.getString(MENSAJE_SELECCIONE_PREGUNTA_KEY)) || respuesta3.isEmpty()) {
            mostrarMensaje(mensajes.getString("registro.mensaje.camposVacios"), "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!contrasena.equals(confirmar)) {
            mostrarMensaje(mensajes.getString("registro.mensaje.contrasenasNoCoinciden"), "Error de Contraseña", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            mostrarMensaje(mensajes.getString("registro.mensaje.usuarioExiste"), "Usuario Existente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (pregunta1.equals(pregunta2) || pregunta1.equals(pregunta3) || pregunta2.equals(pregunta3)) {
            mostrarMensaje(mensajes.getString("registro.mensaje.preguntasDuplicadas"), "Preguntas Duplicadas", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numeroMes = obtenerNumeroMes(mesStr);
        Usuario nuevoUsuario = new Usuario(username, contrasena, correoElectronico, telefono, dia, numeroMes, anio, Rol.USUARIO, pregunta1, respuesta1, pregunta2, respuesta2, pregunta3, respuesta3);
        usuarioDAO.crear(nuevoUsuario);
        mostrarMensaje(mensajes.getString("registro.mensaje.exito"), "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
        dispose();
    }

    private void cargarPreguntasSeguridad() {
        List<PreguntaSeguridad> preguntasDisponibles = preguntaSeguridadDAO.findAll();

        cbxP1.removeAllItems();
        cbxP2.removeAllItems();
        cbxP3.removeAllItems();

        String placeholder = mensajes.getString(MENSAJE_SELECCIONE_PREGUNTA_KEY);
        cbxP1.addItem(placeholder);
        cbxP2.addItem(placeholder);
        cbxP3.addItem(placeholder);

        for (PreguntaSeguridad pregunta : preguntasDisponibles) {
            cbxP1.addItem(pregunta.getPregunta());
            cbxP2.addItem(pregunta.getPregunta());
            cbxP3.addItem(pregunta.getPregunta());
        }
    }

    private void setupFechaNacimientoComboBoxes() {
        String[] meses = {
                mensajes.getString("fecha.mes.enero"), mensajes.getString("fecha.mes.febrero"), mensajes.getString("fecha.mes.marzo"),
                mensajes.getString("fecha.mes.abril"), mensajes.getString("fecha.mes.mayo"), mensajes.getString("fecha.mes.junio"),
                mensajes.getString("fecha.mes.julio"), mensajes.getString("fecha.mes.agosto"), mensajes.getString("fecha.mes.septiembre"),
                mensajes.getString("fecha.mes.octubre"), mensajes.getString("fecha.mes.noviembre"), mensajes.getString("fecha.mes.diciembre")
        };

        cbxMes.removeAllItems();
        for (String mes : meses) {
            cbxMes.addItem(mes);
        }

        cbxAnio.removeAllItems();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear; year >= 1900; year--) {
            cbxAnio.addItem(year);
        }

        updateCbxDia();
    }

    private void updateCbxDia() {
        Object anioSeleccionado = cbxAnio.getSelectedItem();
        if (anioSeleccionado == null) {
            return;
        }

        cbxDia.removeAllItems();
        int mesIndex = cbxMes.getSelectedIndex();
        if (mesIndex == -1) return;

        int anio = (int) anioSeleccionado;
        Calendar cal = new GregorianCalendar(anio, mesIndex, 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= daysInMonth; i++) {
            cbxDia.addItem(i);
        }
    }

    private int obtenerNumeroMes(String nombreMes) {
        String[] meses = {
                mensajes.getString("fecha.mes.enero"), mensajes.getString("fecha.mes.febrero"), mensajes.getString("fecha.mes.marzo"),
                mensajes.getString("fecha.mes.abril"), mensajes.getString("fecha.mes.mayo"), mensajes.getString("fecha.mes.junio"),
                mensajes.getString("fecha.mes.julio"), mensajes.getString("fecha.mes.agosto"), mensajes.getString("fecha.mes.septiembre"),
                mensajes.getString("fecha.mes.octubre"), mensajes.getString("fecha.mes.noviembre"), mensajes.getString("fecha.mes.diciembre")
        };
        for (int i = 0; i < meses.length; i++) {
            if (meses[i].equals(nombreMes)) {
                return i + 1;
            }
        }
        return 0;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipoMensaje);
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtConfirmar.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        cbxDia.setSelectedIndex(0);
        cbxMes.setSelectedIndex(0);
        cbxAnio.setSelectedIndex(0);
        cbxP1.setSelectedIndex(0);
        cbxP2.setSelectedIndex(0);
        cbxP3.setSelectedIndex(0);
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));
        setTitle(mensajes.getString("registro.title"));

        lblNombre.setText(mensajes.getString("registro.label.nombre"));

        btnRegistrarse.setText(mensajes.getString("registro.boton.registrar"));

        cargarPreguntasSeguridad();
        setupFechaNacimientoComboBoxes();

        revalidate();
        repaint();
    }
}