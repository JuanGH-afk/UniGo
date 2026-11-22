package unigo;

import java.awt.Component;
import java.awt.FlowLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.function.Function;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/** Utilidades de interfaz reutilizadas por todos los paneles (selectores, tablas, mensajes). */
public final class Campos {
    private Campos() { }

    public static JSpinner selectorFecha() {
        JSpinner s = new JSpinner(new SpinnerDateModel());
        s.setEditor(new JSpinner.DateEditor(s, "dd/MM/yyyy"));
        return s;
    }

    public static JSpinner selectorHora(int hora, int minuto) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hora);
        c.set(Calendar.MINUTE, minuto);
        JSpinner s = new JSpinner(new SpinnerDateModel(c.getTime(), null, null, Calendar.MINUTE));
        s.setEditor(new JSpinner.DateEditor(s, "HH:mm"));
        return s;
    }

    public static LocalDate fecha(JSpinner s) {
        return ((Date) s.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime hora(JSpinner s) {
        return ((Date) s.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);
    }

    public static String dia(DayOfWeek d) {
        String s = d.getDisplayName(TextStyle.FULL, new Locale("es", "CO"));
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static Border margen() { return BorderFactory.createEmptyBorder(12, 12, 12, 12); }

    public static DefaultTableModel modelo(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int fila, int columna) { return false; }
        };
    }

    public static JTable tabla(DefaultTableModel modelo) {
        JTable t = new JTable(modelo);
        t.setRowHeight(26);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setFillsViewportHeight(true);
        t.getTableHeader().setReorderingAllowed(false);
        return t;
    }

    /** Fila de componentes; los textos se convierten en etiquetas. */
    public static JPanel fila(Object... elementos) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        for (Object o : elementos) p.add(o instanceof String ? new JLabel((String) o) : (Component) o);
        return p;
    }

    public static JPanel apilar(JPanel... filas) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        for (JPanel f : filas) p.add(f);
        return p;
    }

    public static ListCellRenderer<Object> texto(Function<Object, String> f) {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean c) {
                super.getListCellRendererComponent(l, v, i, s, c);
                setText(v == null ? "" : f.apply(v));
                return this;
            }
        };
    }

    public static void alEscribir(JTextField campo, Runnable accion) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { accion.run(); }
            @Override public void removeUpdate(DocumentEvent e) { accion.run(); }
            @Override public void changedUpdate(DocumentEvent e) { accion.run(); }
        });
    }

    /** Devuelve la fila seleccionada o -1 (avisando al usuario). */
    public static int filaSeleccionada(JTable tabla, Component padre) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) aviso(padre, "Selecciona primero una fila de la tabla.");
        return fila;
    }

    public static void aviso(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "UniGo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "UniGo", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirmar(Component padre, String mensaje) {
        return JOptionPane.showConfirmDialog(padre, mensaje, "UniGo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
