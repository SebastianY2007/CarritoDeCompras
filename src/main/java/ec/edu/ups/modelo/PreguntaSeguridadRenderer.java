package ec.edu.ups.modelo;

import ec.edu.ups.modelo.PreguntaSeguridad;
import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class PreguntaSeguridadRenderer extends DefaultListCellRenderer {
    private ResourceBundle mensajes;

    public PreguntaSeguridadRenderer(ResourceBundle mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof PreguntaSeguridad) {
            PreguntaSeguridad pregunta = (PreguntaSeguridad) value;
            String textoTraducido = mensajes.getString(pregunta.getPregunta());
            setText(textoTraducido);
        }
        return this;
    }
}