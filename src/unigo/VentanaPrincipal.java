package unigo;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/** Ventana principal de UniGo con una pestana por funcionalidad. */
public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal(Contexto ctx) {
        super("UniGo - Organizador Universitario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Inicio", new PanelResumen(ctx));
        pestanas.addTab("Tareas", new PanelTareas(ctx));
        pestanas.addTab("Horario", new PanelHorario(ctx));
        pestanas.addTab("Cambio de salon", new PanelCambios(ctx));
        pestanas.addTab("Mapa", new PanelMapa(ctx));
        pestanas.addTab("Soy nuevo", new PanelGuia(ctx));
        // Al cambiar de pestana se refresca, para que siempre muestre datos al dia
        pestanas.addChangeListener(e -> ((PanelBase) pestanas.getSelectedComponent()).refrescar());

        add(pestanas);
        setSize(1000, 650);
        setLocationRelativeTo(null);
    }
}
