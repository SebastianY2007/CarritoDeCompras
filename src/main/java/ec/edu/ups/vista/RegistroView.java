package ec.edu.ups.vista;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegistroView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JLabel lblNombre;
    private JTextField txtUsuario; // Este será el campo para el nombre de usuario de la cuenta
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmar;
    private JLabel lblUsuario; // Etiqueta para txtUsuario
    private JLabel lblContrasena;
    private JLabel lblConfirmar;
    private JButton btnRegistrarse;
    private JComboBox<String> cbxP1;
    private JTextField txtP1;
    private JComboBox<String> cbxP2;
    private JTextField txtP2;
    private JComboBox<String> cbxP3;
    private JTextField txtP3;
    private JComboBox<Integer> cbxDia;
    private JComboBox<String> cbxMes;
    private JComboBox<Integer> cbxAnio;
    private JTextField txtCorreo; // Campo de texto para Correo Electronico
    private JTextField txtTelefono; // NUEVO: Campo de texto para Teléfono

    private UsuarioDAO usuarioDAO;

    private final String[] preguntasSeguridad = {
            "¿Cuál era el nombre de tu primera mascota de la infancia?",
            "¿Cuál es el segundo nombre de tu madre?",
            "¿En qué ciudad conociste a tu mejor amigo/a?",
            "¿Cómo se llamaba tu escuela primaria?",
            "¿Cuál fue el primer concierto al que asististe?",
            "¿En qué ciudad se conocieron tus padres?",
            "¿Cuál es tu película favorita de todos los tiempos?",
            "Si pudieras tener un superpoder, ¿cuál sería?",
            "¿Qué canción te sabes de memoria?",
            "¿Cuál es el mejor consejo que te han dado?"
    };

    public RegistroView(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;

        setTitle("Registro");
        setContentPane(panelPrincipal);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Configuración de las preguntas de seguridad
        for (String pregunta : preguntasSeguridad) {
            cbxP1.addItem(pregunta);
            cbxP2.addItem(pregunta);
            cbxP3.addItem(pregunta);
        }

        cbxP1.setSelectedIndex(0);
        cbxP2.setSelectedIndex(1);
        cbxP3.setSelectedIndex(2);

        // Inicializar ComboBoxes de fecha de nacimiento
        setupFechaNacimientoComboBoxes();

        // Listener del botón Registrarse
        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ahora usamos txtUsuario para el nombre de usuario de la cuenta
                String username = txtUsuario.getText().trim();
                String contrasena = new String(txtContrasena.getPassword()).trim();
                String confirmar = new String(txtConfirmar.getPassword()).trim();

                String nombreCompleto = txtNombre.getText().trim(); // Obtener Nombre Completo
                String correoElectronico = txtCorreo.getText().trim(); // Obtener Correo Electronico
                String telefono = txtTelefono.getText().trim(); // NUEVO: Obtener Teléfono

                // Obtener fecha de nacimiento seleccionada
                Integer dia = (Integer) cbxDia.getSelectedItem();
                String mesStr = (String) cbxMes.getSelectedItem();
                Integer anio = (Integer) cbxAnio.getSelectedItem();

                String pregunta1 = (String) cbxP1.getSelectedItem();
                String respuesta1 = txtP1.getText().trim();
                String pregunta2 = (String) cbxP2.getSelectedItem();
                String respuesta2 = txtP2.getText().trim();
                String pregunta3 = (String) cbxP3.getSelectedItem();
                String respuesta3 = txtP3.getText().trim();

                Rol rol;

                // Validaciones de campos (incluyendo el nuevo campo de teléfono)
                if (username.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty() ||
                        nombreCompleto.isEmpty() || correoElectronico.isEmpty() || telefono.isEmpty() || // Validar teléfono
                        dia == null || mesStr == null || anio == null ||
                        respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty()) {
                    JOptionPane.showMessageDialog(RegistroView.this, "Complete todos los campos, incluyendo la fecha de nacimiento y las respuestas de seguridad.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!contrasena.equals(confirmar)) {
                    JOptionPane.showMessageDialog(RegistroView.this, "Las contraseñas no coinciden.", "Error de Contraseña", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (usuarioDAO.buscarPorUsername(username) != null) {
                    JOptionPane.showMessageDialog(RegistroView.this, "El nombre de usuario ya existe. Por favor, elija otro.", "Usuario Existente", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (pregunta1.equals(pregunta2) || pregunta1.equals(pregunta3) || pregunta2.equals(pregunta3)) {
                    JOptionPane.showMessageDialog(RegistroView.this, "Las preguntas de seguridad deben ser diferentes entre sí. Por favor, seleccione preguntas únicas.", "Preguntas Duplicadas", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                rol = Rol.USUARIO; // Por defecto, se registra como USUARIO

                // Crear el usuario con la información extendida, incluyendo el teléfono
                Usuario nuevo = new Usuario(username, contrasena, rol,
                        nombreCompleto, correoElectronico, telefono, // Pasa el teléfono al constructor
                        dia, obtenerNumeroMes(mesStr), anio,
                        pregunta1, respuesta1,
                        pregunta2, respuesta2,
                        pregunta3, respuesta3);
                usuarioDAO.crear(nuevo);

                JOptionPane.showMessageDialog(RegistroView.this, "Usuario registrado correctamente como " + rol + ".", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cerrar la ventana de registro
            }
        });
    }

    private void setupFechaNacimientoComboBoxes() {
        // Rellenar cbxMes
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (String mes : meses) {
            cbxMes.addItem(mes);
        }

        // Rellenar cbxAnio utilizando GregorianCalendar para obtener el año actual
        GregorianCalendar now = new GregorianCalendar();
        int currentYear = now.get(Calendar.YEAR);
        for (int year = currentYear; year >= 1900; year--) { // Rango desde el año actual hasta 1900
            cbxAnio.addItem(year);
        }

        // Establecer un valor por defecto si es necesario
        cbxMes.setSelectedIndex(0); // Enero por defecto
        cbxAnio.setSelectedIndex(0); // Año actual por defecto

        // Añadir listeners para actualizar los días cuando cambie el mes o el año
        cbxMes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCbxDia();
            }
        });

        cbxAnio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCbxDia();
            }
        });

        // Llamar a updateCbxDia inicialmente para llenar los días
        updateCbxDia();
    }

    private void updateCbxDia() {
        cbxDia.removeAllItems(); // Limpiar items existentes

        Integer selectedMesIndex = cbxMes.getSelectedIndex();
        Integer selectedAnio = (Integer) cbxAnio.getSelectedItem();

        if (selectedMesIndex == -1 || selectedAnio == null) {
            return; // No hay selección, no podemos calcular los días
        }

        // Utilizar GregorianCalendar para obtener el número de días del mes
        // Los meses en Calendar y GregorianCalendar son base 0 (Enero = 0, Febrero = 1, ...)
        GregorianCalendar gc = new GregorianCalendar(selectedAnio, selectedMesIndex, 1);
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= daysInMonth; i++) {
            cbxDia.addItem(i);
        }

        // Seleccionar el primer día por defecto si no hay nada seleccionado
        if (cbxDia.getItemCount() > 0) {
            cbxDia.setSelectedIndex(0);
        }
    }

    // Método auxiliar para convertir el nombre del mes a su número (1-12)
    private int obtenerNumeroMes(String nombreMes) {
        switch (nombreMes) {
            case "Enero": return 1;
            case "Febrero": return 2;
            case "Marzo": return 3;
            case "Abril": return 4;
            case "Mayo": return 5;
            case "Junio": return 6;
            case "Julio": return 7;
            case "Agosto": return 8;
            case "Septiembre": return 9;
            case "Octubre": return 10;
            case "Noviembre": return 11;
            case "Diciembre": return 12;
            default: return 0; // Manejo de error o valor por defecto
        }
    }

    public void mostrar() {
        setVisible(true);
    }
}