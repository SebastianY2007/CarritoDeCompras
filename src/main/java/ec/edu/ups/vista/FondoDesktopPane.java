package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Clase FondoDesktopPane
 * <p>
 * Extiende {@link JDesktopPane} para crear un panel de escritorio personalizado
 * con una estética de alta tecnología. Este componente está diseñado para ser
 * el fondo principal de una ventana de aplicación, mostrando un gradiente oscuro,
 * un gráfico central de un procesador y varios iconos de periféricos.
 * <p>
 * La clase gestiona todo el renderizado gráfico personalizado a través del método
 * {@code paintComponent}.
 *
 * @author Sebastian Yupangui
 * @version 1.2
 * @since 19/07/2025
 */
public class FondoDesktopPane extends JDesktopPane {

    /**
     * Sobrescribe el método de pintado principal del componente para renderizar el fondo personalizado.
     * <p>
     * Este método se invoca automáticamente por el sistema de renderizado de Swing.
     * La secuencia de dibujado es la siguiente:
     * <ol>
     * <li>Fondo con gradiente oscuro.</li>
     * <li>Patrón de líneas de circuito tenues.</li>
     * <li>Icono del procesador moderno en el centro.</li>
     * <li>Iconos de periféricos (monitor, teclado, ratón) en una disposición diagonal.</li>
     * <li>Texto de bienvenida con efecto de sombra.</li>
     * </ol>
     *
     * @param g El contexto gráfico {@link Graphics} proporcionado por Swing para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        // Activa el antialiasing para suavizar los bordes de las figuras.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibuja el fondo con un gradiente de color oscuro y moderno.
        Color colorSuperior = new Color(25, 35, 45);
        Color colorInferior = new Color(10, 15, 25);
        GradientPaint gp = new GradientPaint(0, 0, colorSuperior, 0, getHeight(), colorInferior);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Dibuja los elementos gráficos decorativos.
        dibujarPatronDeCircuitos(g2d);
        dibujarProcesadorModerno(g2d);

        // Define las dimensiones y posiciones de los iconos de periféricos.
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int monitorWidth = 130;
        int tecladoWidth = 160;

        // Dibuja los iconos en sus posiciones finales.
        dibujarIconoMonitor(g2d, centerX - (monitorWidth / 2), centerY - 290);
        dibujarIconoTeclado(g2d, centerX - tecladoWidth - 30, centerY + 160);
        dibujarIconoMouse(g2d, centerX + 80, centerY + 160);

        // Dibuja el texto de bienvenida centrado en la parte superior.
        String textoBienvenida = "TechStore";
        g2d.setFont(new Font("Monospaced", Font.BOLD, 50));
        FontMetrics fm = g2d.getFontMetrics();
        int xTexto = (getWidth() - fm.stringWidth(textoBienvenida)) / 2;
        int yTexto = 100;

        // Dibuja una sombra para el texto para mejorar la legibilidad.
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(textoBienvenida, xTexto + 3, yTexto + 3);

        // Dibuja el texto principal.
        g2d.setColor(Color.WHITE);
        g2d.drawString(textoBienvenida, xTexto, yTexto);
    }

    /**
     * Dibuja el gráfico central que simula un procesador (CPU) con un estilo moderno.
     * <p>
     * Crea un icono compuesto por un cuerpo principal, un núcleo (die) con efecto
     * metálico gracias a un gradiente, y líneas de circuito que emanan del centro.
     *
     * @param g2d El contexto gráfico 2D en el que se va a dibujar.
     */
    private void dibujarProcesadorModerno(Graphics2D g2d) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int size = 120; // Tamaño del cuerpo del procesador.

        // Guarda el estado original del contexto gráfico para restaurarlo al final.
        Stroke originalStroke = g2d.getStroke();
        Paint originalPaint = g2d.getPaint();

        // Dibuja las líneas de circuito que salen del procesador.
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(0, 150, 200, 40));
        g2d.drawLine(centerX - 150, centerY - 150, centerX - 40, centerY - 40);
        g2d.drawLine(centerX + 150, centerY - 150, centerX + 40, centerY - 40);
        g2d.drawLine(centerX - 150, centerY + 150, centerX - 40, centerY + 40);
        g2d.drawLine(centerX + 150, centerY + 150, centerX + 40, centerY + 40);
        g2d.drawLine(centerX, centerY - 200, centerX, centerY - 60);
        g2d.drawLine(centerX, centerY + 200, centerX, centerY + 60);
        g2d.drawLine(centerX - 200, centerY, centerX - 60, centerY);
        g2d.drawLine(centerX + 200, centerY, centerX + 60, centerY);

        // Dibuja el cuerpo principal del procesador.
        g2d.setColor(new Color(50, 55, 60));
        g2d.fill(new RoundRectangle2D.Double(centerX - size / 2.0, centerY - size / 2.0, size, size, 20, 20));
        g2d.setColor(new Color(70, 75, 80));
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(new RoundRectangle2D.Double(centerX - size / 2.0, centerY - size / 2.0, size, size, 20, 20));

        // Dibuja el núcleo del procesador (die) con un gradiente metálico.
        int dieSize = 60;
        GradientPaint dieGradient = new GradientPaint(
                centerX, centerY - dieSize / 2.0f, new Color(180, 185, 190),
                centerX, centerY + dieSize / 2.0f, new Color(120, 125, 130)
        );
        g2d.setPaint(dieGradient);
        g2d.fillRect(centerX - dieSize / 2, centerY - dieSize / 2, dieSize, dieSize);
        g2d.setColor(new Color(190, 195, 200, 150));
        g2d.drawRect(centerX - dieSize / 2, centerY - dieSize / 2, dieSize, dieSize);

        // Restaura el contexto gráfico a su estado original.
        g2d.setPaint(originalPaint);
        g2d.setStroke(originalStroke);
    }

    /**
     * Dibuja un patrón de líneas de circuito tenues y un borde rectangular
     * para añadir profundidad y detalle al fondo.
     *
     * @param g2d El contexto gráfico 2D en el que se va a dibujar.
     */
    private void dibujarPatronDeCircuitos(Graphics2D g2d) {
        g2d.setColor(new Color(0, 200, 255, 15));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(0, getHeight() / 4, getWidth() / 3, getHeight() / 4);
        g2d.drawLine(getWidth() / 3, getHeight() / 4, getWidth() / 3, getHeight() / 2);
        g2d.drawLine(getWidth(), (int)(getHeight() / 1.5), (int)(getWidth() / 1.5), (int)(getHeight() / 1.5));
        g2d.drawLine((int)(getWidth() / 1.5), (int)(getHeight() / 1.5), (int)(getWidth() / 1.5), getHeight());
        g2d.draw(new RoundRectangle2D.Double(50, 50, getWidth() - 100, getHeight() - 100, 50, 50));
    }

    /**
     * Dibuja un icono estilizado de un monitor de PC.
     *
     * @param g2d El contexto gráfico 2D.
     * @param x   La coordenada X de la esquina superior izquierda del icono.
     * @param y   La coordenada Y de la esquina superior izquierda del icono.
     */
    private void dibujarIconoMonitor(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(110, 120, 130, 130));
        g2d.fill(new RoundRectangle2D.Double(x, y, 130, 80, 15, 15));
        g2d.setColor(new Color(0, 200, 255, 80));
        g2d.fillRect(x + 13, y + 13, 104, 54);
        g2d.setColor(new Color(110, 120, 130, 130));
        g2d.fillRect(x + 59, y + 80, 12, 12);
        g2d.fillRect(x + 45, y + 92, 40, 7);
    }

    /**
     * Dibuja un icono estilizado de un teclado.
     *
     * @param g2d El contexto gráfico 2D.
     * @param x   La coordenada X de la esquina superior izquierda del icono.
     * @param y   La coordenada Y de la esquina superior izquierda del icono.
     */
    private void dibujarIconoTeclado(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(110, 120, 130, 130));
        g2d.fill(new RoundRectangle2D.Double(x, y, 160, 50, 10, 10));
        g2d.setColor(new Color(80, 90, 100, 130));
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                g2d.fillRect(x + 9 + (i * 22), y + 9 + (j * 17), 18, 13);
            }
        }
    }

    /**
     * Dibuja un icono estilizado de un ratón de PC.
     *
     * @param g2d El contexto gráfico 2D.
     * @param x   La coordenada X de la esquina superior izquierda del icono.
     * @param y   La coordenada Y de la esquina superior izquierda del icono.
     */
    private void dibujarIconoMouse(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(110, 120, 130, 130));
        g2d.fill(new RoundRectangle2D.Double(x, y, 50, 75, 28, 28));
        g2d.setColor(new Color(80, 90, 100, 130));
        g2d.drawLine(x + 25, y + 15, x + 25, y + 38);
    }
}
