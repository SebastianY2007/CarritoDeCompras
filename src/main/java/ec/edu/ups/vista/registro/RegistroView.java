package ec.edu.ups.vista.registro;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.excepciones.UsuarioYaExistenteException;
import ec.edu.ups.excepciones.ValidacionException;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.modelo.PreguntaSeguridadRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmar;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JComboBox<Object> cbxP1;
    private JTextField txtP1;
    private JComboBox<Object> cbxP2;
    private JTextField txtP2;
    private JComboBox<Object> cbxP3;
    private JTextField txtP3;
    private JComboBox<Integer> cbxDia;
    private JComboBox<String> cbxMes;
    private JComboBox<Integer> cbxAnio;
    private JButton btnRegistrarse;
    private JLabel lblFecha;
    private JLabel lblCorreo;
    private JLabel lblTelefono;
    private JLabel lblContrasena;
    private JLabel lblConfirmarContrasena;
    private JLabel lblPreguntas;
    private JTextField txtCedula;
    private JLabel lblCedula;

    private List<PreguntaSeguridad> todasLasPreguntas;
    private PreguntaSeguridadRenderer renderer;

    private UsuarioDAO usuarioDAO;
    private PreguntaSeguridadDAO preguntaSeguridadDAO;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;
    private final String MENSAJE_SELECCIONE_PREGUNTA_KEY = "registro.pregunta.seleccionar";

    public RegistroView(UsuarioDAO usuarioDAO, PreguntaSeguridadDAO preguntaSeguridadDAO, MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaSeguridadDAO = preguntaSeguridadDAO;
        this.mensajeHandler = mensajeHandler;
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("registro.title"));
        setContentPane(panelPrincipal);
        setSize(700, 900);
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
                    boolean isPlaceholder = !(sourceComboBox.getSelectedItem() instanceof PreguntaSeguridad);
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
        try {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String cedula = txtCedula.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());
            String confirmar = new String(txtConfirmar.getPassword());
            Object itemP1 = cbxP1.getSelectedItem();
            Object itemP2 = cbxP2.getSelectedItem();
            Object itemP3 = cbxP3.getSelectedItem();
            String respuesta1 = txtP1.getText().trim();
            String respuesta2 = txtP2.getText().trim();
            String respuesta3 = txtP3.getText().trim();

            validarDatos(nombre, correo, cedula, telefono, contrasena, confirmar, itemP1, itemP2, itemP3, respuesta1, respuesta2, respuesta3);

            String pregunta1 = ((PreguntaSeguridad) itemP1).getPregunta();
            String pregunta2 = ((PreguntaSeguridad) itemP2).getPregunta();
            String pregunta3 = ((PreguntaSeguridad) itemP3).getPregunta();
            Integer dia = (Integer) cbxDia.getSelectedItem();
            String mesStr = (String) cbxMes.getSelectedItem();
            Integer anio = (Integer) cbxAnio.getSelectedItem();
            int numeroMes = obtenerNumeroMes(mesStr);

            Usuario nuevoUsuario = new Usuario(cedula, nombre, contrasena.trim(), correo, telefono, dia, numeroMes, anio, Rol.USUARIO, pregunta1, respuesta1, pregunta2, respuesta2, pregunta3, respuesta3);
            usuarioDAO.crear(nuevoUsuario);

            mostrarMensaje(mensajes.getString("registro.mensaje.exito"), mensajes.getString("global.exito.titulo"), JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            dispose();

        } catch (ValidacionException | UsuarioYaExistenteException e) {
            mostrarMensaje(e.getMessage(), mensajes.getString("global.error.titulo"), JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Ocurrió un error inesperado. Contacte al administrador.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarDatos(String nombre, String correo, String cedula, String telefono, String contrasena, String confirmar,
                              Object p1, Object p2, Object p3, String r1, String r2, String r3)
            throws ValidacionException, UsuarioYaExistenteException {

        if (nombre.isEmpty() || correo.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty() ||
                r1.isEmpty() || r2.isEmpty() || r3.isEmpty() || !(p1 instanceof PreguntaSeguridad) || !(p2 instanceof PreguntaSeguridad) || !(p3 instanceof PreguntaSeguridad)) {
            throw new ValidacionException(mensajes.getString("registro.error.camposVacios"));
        }

        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new ValidacionException(mensajes.getString("registro.error.nombreInvalido"));
        }
        if (!Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$").matcher(correo).matches()) {
            throw new ValidacionException(mensajes.getString("registro.error.correoInvalido"));
        }
        if (!telefono.matches("^\\d{10}$")) {
            throw new ValidacionException(mensajes.getString("registro.error.telefonoInvalido"));
        }
        if (!validarCedula(cedula)) {
            throw new ValidacionException(mensajes.getString("registro.mensaje.cedulaInvalida"));
        }
        if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@_-]).{6,}$").matcher(contrasena).matches()) {
            throw new ValidacionException(mensajes.getString("registro.error.contrasenaInvalida"));
        }

        if (!contrasena.equals(confirmar)) {
            throw new ValidacionException(mensajes.getString("registro.mensaje.contrasenasNoCoinciden"));
        }
        if (((PreguntaSeguridad) p1).getPregunta().equals(((PreguntaSeguridad) p2).getPregunta()) ||
                ((PreguntaSeguridad) p1).getPregunta().equals(((PreguntaSeguridad) p3).getPregunta()) ||
                ((PreguntaSeguridad) p2).getPregunta().equals(((PreguntaSeguridad) p3).getPregunta())) {
            throw new ValidacionException(mensajes.getString("registro.mensaje.preguntasDuplicadas"));
        }

        if (usuarioDAO.buscarPorCedula(cedula) != null) {
            throw new UsuarioYaExistenteException(mensajes.getString("registro.mensaje.usuarioExiste"));
        }
    }

    private boolean validarFormatoNombre(String nombre) { return nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"); }
    private boolean validarFormatoCorreo(String correo) { String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"; return Pattern.compile(regex).matcher(correo).matches(); }
    private boolean validarFormatoTelefono(String telefono) { return telefono.matches("^\\d{10}$"); }
    private boolean validarContrasena(String contrasena) { String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@_-]).{6,}$"; return Pattern.compile(regex).matcher(contrasena).matches(); }
    private boolean validarCedula(String cedula) { if (cedula == null || cedula.length() != 10) return false; if (!cedula.matches("\\d+")) return false; try { int provincia = Integer.parseInt(cedula.substring(0, 2)); if (provincia < 1 || provincia > 24) return false; int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2}; int digitoVerificador = Integer.parseInt(String.valueOf(cedula.charAt(9))); int suma = 0; for (int i = 0; i < 9; i++) { int digito = Integer.parseInt(String.valueOf(cedula.charAt(i))); int producto = digito * coeficientes[i]; suma += (producto >= 10) ? producto - 9 : producto; } int residuo = suma % 10; int resultado = (residuo == 0) ? 0 : 10 - residuo; return resultado == digitoVerificador; } catch (NumberFormatException e) { return false; } }

    public void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
        String mensajeHtml = "<html><body style='width: 350px;'>"
                + "<p style='font-size: 13pt; font-family: sans-serif;'>"
                + mensaje.replaceAll("\n", "<br>")
                + "</p></body></html>";

        JLabel labelConHtml = new JLabel(mensajeHtml);

        JOptionPane.showMessageDialog(this, labelConHtml, titulo, tipoMensaje);
    }

    private void cargarPreguntasSeguridad() {
        this.todasLasPreguntas = preguntaSeguridadDAO.findAll();
        this.renderer = new PreguntaSeguridadRenderer(this.mensajes);
        cbxP1.setRenderer(renderer);
        cbxP2.setRenderer(renderer);
        cbxP3.setRenderer(renderer);
        cbxP1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarOpcionesCbx2();
            }
        });
        cbxP2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarOpcionesCbx3();
            }
        });
        actualizarOpcionesCbx1();
    }

    private void actualizarOpcionesCbx1() {
        cbxP1.removeAllItems();
        String placeholder = mensajes.getString(MENSAJE_SELECCIONE_PREGUNTA_KEY);
        cbxP1.addItem(placeholder);
        for (PreguntaSeguridad pregunta : todasLasPreguntas) {
            cbxP1.addItem(pregunta);
        }
        cbxP2.removeAllItems();
        cbxP2.setEnabled(false);
        txtP2.setEnabled(false);
        cbxP3.removeAllItems();
        cbxP3.setEnabled(false);
        txtP3.setEnabled(false);
    }

    private void actualizarOpcionesCbx2() {
        Object seleccionP1 = cbxP1.getSelectedItem();
        cbxP2.removeAllItems();
        cbxP3.removeAllItems();
        cbxP3.setEnabled(false);
        txtP3.setEnabled(false);
        if (seleccionP1 instanceof PreguntaSeguridad) {
            cbxP2.setEnabled(true);
            String placeholder = mensajes.getString(MENSAJE_SELECCIONE_PREGUNTA_KEY);
            cbxP2.addItem(placeholder);
            for (PreguntaSeguridad pregunta : todasLasPreguntas) {
                if (!pregunta.equals(seleccionP1)) {
                    cbxP2.addItem(pregunta);
                }
            }
        } else {
            cbxP2.setEnabled(false);
            txtP2.setEnabled(false);
        }
    }

    private void actualizarOpcionesCbx3() {
        Object seleccionP1 = cbxP1.getSelectedItem();
        Object seleccionP2 = cbxP2.getSelectedItem();
        cbxP3.removeAllItems();
        if (seleccionP2 instanceof PreguntaSeguridad) {
            cbxP3.setEnabled(true);
            String placeholder = mensajes.getString(MENSAJE_SELECCIONE_PREGUNTA_KEY);
            cbxP3.addItem(placeholder);
            for (PreguntaSeguridad pregunta : todasLasPreguntas) {
                if (!pregunta.equals(seleccionP1) && !pregunta.equals(seleccionP2)) {
                    cbxP3.addItem(pregunta);
                }
            }
        } else {
            cbxP3.setEnabled(false);
            txtP3.setEnabled(false);
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
        if (anioSeleccionado == null) return;
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

    public void limpiarCampos() {
        txtCedula.setText("");
        txtContrasena.setText("");
        txtConfirmar.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        if (cbxDia.getItemCount() > 0) cbxDia.setSelectedIndex(0);
        if (cbxMes.getItemCount() > 0) cbxMes.setSelectedIndex(0);
        if (cbxAnio.getItemCount() > 0) cbxAnio.setSelectedIndex(0);
        txtP1.setText("");
        txtP2.setText("");
        txtP3.setText("");
        cargarPreguntasSeguridad();
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        setTitle(mensajes.getString("registro.title"));
        lblCedula.setText(mensajes.getString("registro.label.cedula"));
        lblNombre.setText(mensajes.getString("registro.label.nombre"));
        lblCorreo.setText(mensajes.getString("registro.label.email"));
        lblTelefono.setText(mensajes.getString("registro.label.telefono"));
        lblContrasena.setText(mensajes.getString("registro.label.contrasena"));
        lblConfirmarContrasena.setText(mensajes.getString("registro.label.confirmarContrasena"));
        lblPreguntas.setText(mensajes.getString("registro.label.preguntasSeguridad"));
        btnRegistrarse.setText(mensajes.getString("registro.boton.registrar"));
        cargarPreguntasSeguridad();
        setupFechaNacimientoComboBoxes();
        revalidate();
        repaint();
        configurarIconos();
    }

    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void configurarIconos() {
        java.net.URL urlIconoRegistrar = getClass().getResource("/icons/icono_registrarse.png");
        if (urlIconoRegistrar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoRegistrar);
            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);
            btnRegistrarse.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_registrarse.png");
        }
    }
}
