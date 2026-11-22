package unigo;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** UniGo - Organizador Universitario (version beta). Punto de entrada de la aplicacion. */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, Swing usa su aspecto por defecto
        }
        SwingUtilities.invokeLater(() -> {
            Contexto ctx = new Contexto();
            ctx.iniciar();
            new VentanaPrincipal(ctx).setVisible(true);
        });
    }
}
