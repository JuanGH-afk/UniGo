package unigo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

/** Pestana de inicio: contadores, clases de hoy y proximas entregas. */
public class PanelResumen extends PanelBase {
    private final JLabel pendientes;
    private final JLabel vencidas;
    private final JLabel clasesHoy;
    private final JTextArea detalle = new JTextArea();

    public PanelResumen(Contexto ctx) {
        super(ctx);
        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 10, 0));
        pendientes = tarjeta(tarjetas, "Tareas pendientes");
        vencidas = tarjeta(tarjetas, "Tareas vencidas");
        clasesHoy = tarjeta(tarjetas, "Clases hoy");
        detalle.setEditable(false);
        detalle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        add(tarjetas, BorderLayout.NORTH);
        add(new JScrollPane(detalle), BorderLayout.CENTER);
        refrescar();
    }

    private JLabel tarjeta(JPanel fila, String texto) {
        JLabel numero = new JLabel("0", SwingConstants.CENTER);
        numero.setFont(numero.getFont().deriveFont(Font.BOLD, 38f));
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(texto));
        p.add(numero, BorderLayout.CENTER);
        fila.add(p);
        return numero;
    }

    @Override
    public void refrescar() {
        LocalDate hoy = LocalDate.now();
        List<Clase> deHoy = ctx.org.clasesDelDia(hoy.getDayOfWeek());
        pendientes.setText(String.valueOf(ctx.org.contarPendientes()));
        vencidas.setText(String.valueOf(ctx.org.contarVencidas()));
        clasesHoy.setText(String.valueOf(deHoy.size()));

        StringBuilder sb = new StringBuilder();
        sb.append("Hoy es ").append(Campos.dia(hoy.getDayOfWeek())).append(" ")
          .append(hoy.format(Tarea.FORMATO_FECHA)).append("\n\nCLASES DE HOY\n");
        if (deHoy.isEmpty()) sb.append("  No tienes clases hoy.\n");
        for (Clase c : deHoy) {
            sb.append("  ").append(c.getInicio()).append("  ").append(c.getTitulo())
              .append("  -  salon ").append(ctx.org.salonEfectivo(c, hoy));
            if (!ctx.org.salonEfectivo(c, hoy).equals(c.getSalon())) sb.append("  (CAMBIO DE SALON)");
            sb.append("\n");
        }
        sb.append("\nPROXIMAS ENTREGAS\n");
        List<Tarea> proximas = ctx.org.proximasEntregas(5);
        if (proximas.isEmpty()) sb.append("  Sin entregas pendientes.\n");
        for (Tarea t : proximas) {
            sb.append("  ").append(t.getFechaLimite().format(Tarea.FORMATO_FECHA)).append("  ")
              .append(t.getTipo()).append(": ").append(t.getDetalle()).append(" (").append(t.getMateria()).append(")")
              .append(t.estaVencida() ? "  - VENCIDA" : "").append("\n");
        }
        detalle.setText(sb.toString());
        detalle.setCaretPosition(0);
    }
}
