package unigo;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

/** Pestana del mapa del campus: buscador, plano clicable y ficha del lugar. */
public class PanelMapa extends PanelBase {
    private final JTextField busqueda = new JTextField(16);
    private final DefaultListModel<Lugar> modelo = new DefaultListModel<>();
    private final JList<Lugar> lista = new JList<>(modelo);
    private final JTextArea ficha = new JTextArea(4, 30);
    private final Plano plano = new Plano();

    public PanelMapa(Contexto ctx) {
        super(ctx);
        lista.setCellRenderer(Campos.texto(o -> ((Lugar) o).getNombre()));
        lista.addListSelectionListener(e -> {
            Lugar l = lista.getSelectedValue();
            ficha.setText(l == null ? "Selecciona un lugar de la lista o del plano." : l.ficha().replace(" | ", "\n"));
            plano.repaint();
        });
        Campos.alEscribir(busqueda, this::refrescar);
        ficha.setEditable(false);
        ficha.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        JPanel izquierda = new JPanel(new BorderLayout(0, 6));
        izquierda.add(Campos.fila("Buscar:", busqueda), BorderLayout.NORTH);
        izquierda.add(new JScrollPane(lista), BorderLayout.CENTER);
        izquierda.setPreferredSize(new Dimension(260, 0));

        add(izquierda, BorderLayout.WEST);
        add(plano, BorderLayout.CENTER);
        add(new JScrollPane(ficha), BorderLayout.SOUTH);
        refrescar();
    }

    @Override
    public void refrescar() {
        String q = busqueda.getText().trim().toLowerCase();
        modelo.clear();
        ctx.campus.getLugares().stream()
                .filter(l -> q.isEmpty() || l.getNombre().toLowerCase().contains(q))
                .forEach(modelo::addElement);
        plano.repaint();
    }

    /** Dibujo esquematico del campus: cada lugar es un bloque que se puede seleccionar con el mouse. */
    private class Plano extends JPanel {
        private final Map<Lugar, Rectangle> zonas = new LinkedHashMap<>();

        Plano() {
            setPreferredSize(new Dimension(420, 280));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    zonas.forEach((l, r) -> { if (r.contains(e.getPoint())) lista.setSelectedValue(l, true); });
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            zonas.clear();
            List<Lugar> lugares = ctx.campus.getLugares();
            int columnas = 3, filas = Math.max(1, (lugares.size() + columnas - 1) / columnas);
            int w = getWidth() / columnas, h = getHeight() / filas;
            String q = busqueda.getText().trim().toLowerCase();
            for (int i = 0; i < lugares.size(); i++) {
                Lugar l = lugares.get(i);
                Rectangle r = new Rectangle((i % columnas) * w + 8, (i / columnas) * h + 8, w - 16, h - 16);
                zonas.put(l, r);
                boolean seleccionado = l == lista.getSelectedValue();
                boolean coincide = !q.isEmpty() && l.getNombre().toLowerCase().contains(q);
                g2.setColor(seleccionado ? new Color(29, 78, 216) : coincide ? new Color(245, 167, 0) : new Color(221, 227, 238));
                g2.fillRoundRect(r.x, r.y, r.width, r.height, 16, 16);
                g2.setColor(seleccionado ? Color.WHITE : new Color(20, 33, 61));
                g2.drawString(l.getNombre(), r.x + 10, r.y + r.height / 2);
            }
        }
    }
}
