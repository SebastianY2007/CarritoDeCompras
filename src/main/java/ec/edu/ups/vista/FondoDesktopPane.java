package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Clase FondoDesktopPane
 *
 * Esta clase extiende JDesktopPane para crear un fondo de escritorio personalizado
 * para la interfaz de múltiples documentos (MDI). Dibuja un fondo con un
 * gradiente de color y un gráfico personalizado de un carrito de compras.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class FondoDesktopPane extends JDesktopPane {

    /**
     * Sobrescribe el método de pintado del componente para dibujar el fondo.
     *
     * Este método se invoca automáticamente para dibujar el componente. Se encarga
     * de renderizar un gradiente de color, un ícono de carrito de compras y un
     * texto de bienvenida en el centro del panel.
     *
     * @param g El contexto gráfico en el que se debe pintar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibuja el fondo con un gradiente de color
        Color colorSuperior = new Color(200, 200, 200);
        Color colorInferior = new Color(150, 150, 150);
        GradientPaint gp = new GradientPaint(0, 0, colorSuperior, 0, getHeight(), colorInferior);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Parámetros para dibujar el carrito de compras
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int carritoAncho = 120;
        int carritoAlto = 80;
        int asaAncho = 30;
        int asaAlto = 40;
        int ruedaRadio = 10;
        int separacionRuedas = 20;

        // Dibuja el cuerpo y el asa del carrito
        Color colorCarrito = new Color(80, 80, 100);
        g2d.setColor(colorCarrito);
        RoundRectangle2D cuerpo = new RoundRectangle2D.Double(
                centerX - carritoAncho / 2,
                centerY - carritoAlto / 2,
                carritoAncho,
                carritoAlto,
                20, 20
        );
        g2d.fill(cuerpo);
        g2d.setColor(colorCarrito.darker());
        g2d.fillRect(
                centerX + carritoAncho / 2 - asaAncho,
                centerY - carritoAlto / 2 - asaAlto + 10,
                asaAncho,
                asaAlto
        );

        // Dibuja las ruedas del carrito
        g2d.setColor(Color.BLACK);
        g2d.fillOval(
                centerX - carritoAncho / 2 + ruedaRadio,
                centerY + carritoAlto / 2 - ruedaRadio * 2 + 5,
                ruedaRadio * 2,
                ruedaRadio * 2
        );
        g2d.fillOval(
                centerX - carritoAncho / 2 + ruedaRadio + separacionRuedas + ruedaRadio * 2,
                centerY + carritoAlto / 2 - ruedaRadio * 2 + 5,
                ruedaRadio * 2,
                ruedaRadio * 2
        );
        g2d.fillOval(
                centerX + separacionRuedas / 2,
                centerY + carritoAlto / 2 - ruedaRadio * 2 + 5,
                ruedaRadio * 2,
                ruedaRadio * 2
        );

        // Dibuja el texto de bienvenida
        String textoBienvenida = "Carrito de Compras en Linea";
        g2d.setFont(new Font("SansSerif", Font.BOLD, 50));
        g2d.setColor(new Color(0, 0, 0, 80));
        java.awt.FontMetrics fm = g2d.getFontMetrics();
        int xTexto = (getWidth() - fm.stringWidth(textoBienvenida)) / 2;
        int yTexto = centerY - carritoAlto / 2 - asaAlto - 30;
        g2d.drawString(textoBienvenida, xTexto, yTexto);
    }
}
