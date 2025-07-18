package ec.edu.ups.modelo;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Clase PreguntaSeguridadRenderer
 *
 * Esta clase personaliza la forma en que los objetos PreguntaSeguridad se
 * muestran en un JComboBox. En lugar de mostrar el resultado del método
 * toString(), traduce la clave de la pregunta al idioma actual.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class PreguntaSeguridadRenderer extends DefaultListCellRenderer {
    private ResourceBundle mensajes;

    /**
     * Constructor de PreguntaSeguridadRenderer.
     *
     * @param mensajes El ResourceBundle que contiene las traducciones de las preguntas.
     */
    public PreguntaSeguridadRenderer(ResourceBundle mensajes) {
        this.mensajes = mensajes;
    }

    /**
     * Personaliza el componente de la celda de la lista.
     *
     * Este método se invoca para cada elemento del JComboBox. Si el elemento
     * es una instancia de PreguntaSeguridad, obtiene su clave y la traduce
     * usando el ResourceBundle antes de mostrarla.
     *
     * @param list El JList que estamos pintando.
     * @param value El valor a renderizar.
     * @param index El índice de la celda.
     * @param isSelected Verdadero si la celda está seleccionada.
     * @param cellHasFocus Verdadero si la celda tiene el foco.
     * @return El componente configurado para mostrarse en la lista.
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof PreguntaSeguridad) {
            PreguntaSeguridad pregunta = (PreguntaSeguridad) value;
            // Traduce la clave de la pregunta al texto visible
            String textoTraducido = mensajes.getString(pregunta.getPregunta());
            setText(textoTraducido);
        }
        return this;
    }
}
