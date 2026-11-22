package unigo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/** Pestana de tareas y examenes. */
public class PanelTareas extends PanelBase {
    private final JTextField titulo = new JTextField(16);
    private final JTextField materia = new JTextField(12);
    private final JSpinner fecha = Campos.selectorFecha();
    private final JComboBox<Prioridad> prioridad = new JComboBox<>(Prioridad.values());
    private final JCheckBox esExamen = new JCheckBox("Es examen (el texto son los temas)");
    private final JComboBox<String> filtro = new JComboBox<>(new String[] {"Todas", "Pendientes", "Completadas"});
    private final DefaultTableModel modelo = Campos.modelo("Hecha", "Tipo", "Detalle", "Materia", "Entrega", "Prioridad");
    private final JTable tabla = Campos.tabla(modelo);
    private List<Tarea> visibles = new ArrayList<>();

    public PanelTareas(Contexto ctx) {
        super(ctx);
        JButton agregar = new JButton("Agregar");
        agregar.addActionListener(e -> agregar());
        JPanel arriba = Campos.apilar(
                Campos.fila("Titulo / temas:", titulo, "Materia:", materia),
                Campos.fila("Entrega:", fecha, "Prioridad:", prioridad, esExamen, agregar));

        JButton completar = new JButton("Marcar como completada");
        completar.addActionListener(e -> completar());
        JButton eliminar = new JButton("Eliminar");
        eliminar.addActionListener(e -> eliminar());
        filtro.addActionListener(e -> refrescar());

        // Las tareas vencidas se pintan de rojo
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foco, int fila, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foco, fila, col);
                boolean vencida = fila < visibles.size() && visibles.get(fila).estaVencida();
                c.setForeground(sel ? t.getSelectionForeground() : vencida ? new Color(194, 65, 12) : t.getForeground());
                return c;
            }
        });

        add(arriba, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(Campos.fila("Mostrar:", filtro, completar, eliminar), BorderLayout.SOUTH);
        refrescar();
    }

    private void agregar() {
        String texto = titulo.getText().trim();
        String mat = materia.getText().trim();
        if (texto.isEmpty() || mat.isEmpty()) {
            Campos.error(this, "Escribe el titulo (o los temas) y la materia.");
            return;
        }
        LocalDate f = Campos.fecha(fecha);
        Tarea t = esExamen.isSelected() ? new Examen(mat, f, texto)
                                        : new Tarea(texto, mat, f, (Prioridad) prioridad.getSelectedItem());
        ctx.org.agregarTarea(t);
        titulo.setText("");
        ctx.cambiar();
    }

    private void completar() {
        int fila = Campos.filaSeleccionada(tabla, this);
        if (fila < 0) return;
        ctx.org.completarTarea(visibles.get(fila).getId());
        ctx.cambiar();
    }

    private void eliminar() {
        int fila = Campos.filaSeleccionada(tabla, this);
        if (fila < 0 || !Campos.confirmar(this, "Eliminar esta tarea?")) return;
        ctx.org.eliminarTarea(visibles.get(fila).getId());
        ctx.cambiar();
    }

    @Override
    public void refrescar() {
        String f = (String) filtro.getSelectedItem();
        visibles = ctx.org.tareasOrdenadas().stream()
                .filter(t -> "Todas".equals(f) || ("Completadas".equals(f) == t.isCompletada()))
                .collect(Collectors.toList());
        modelo.setRowCount(0);
        for (Tarea t : visibles) {
            modelo.addRow(new Object[] {t.isCompletada() ? "Si" : "No", t.getTipo(), t.getDetalle(),
                    t.getMateria(), t.getFechaLimite().format(Tarea.FORMATO_FECHA), t.getPrioridad()});
        }
    }
}
