package unigo;

import java.awt.BorderLayout;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/** Pestana del horario semanal. */
public class PanelHorario extends PanelBase {
    private final JTextField materia = new JTextField(14);
    private final JComboBox<DayOfWeek> dia = new JComboBox<>(DayOfWeek.values());
    private final JSpinner inicio = Campos.selectorHora(7, 0);
    private final JSpinner fin = Campos.selectorHora(9, 0);
    private final JTextField salon = new JTextField(8);
    private final JTextField docente = new JTextField(14);
    private final DefaultTableModel modelo = Campos.modelo("Dia", "Inicio", "Fin", "Materia", "Salon", "Docente");
    private final JTable tabla = Campos.tabla(modelo);
    private final List<Clase> visibles = new ArrayList<>();

    public PanelHorario(Contexto ctx) {
        super(ctx);
        dia.setRenderer(Campos.texto(o -> Campos.dia((DayOfWeek) o)));
        JButton agregar = new JButton("Agregar clase");
        agregar.addActionListener(e -> agregar());
        JButton eliminar = new JButton("Eliminar clase seleccionada");
        eliminar.addActionListener(e -> eliminar());

        add(Campos.apilar(
                Campos.fila("Materia:", materia, "Dia:", dia, "Inicio:", inicio, "Fin:", fin),
                Campos.fila("Salon:", salon, "Docente:", docente, agregar)), BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(Campos.fila(eliminar), BorderLayout.SOUTH);
        refrescar();
    }

    private void agregar() {
        String mat = materia.getText().trim(), sal = salon.getText().trim(), doc = docente.getText().trim();
        if (mat.isEmpty() || sal.isEmpty() || doc.isEmpty()) {
            Campos.error(this, "Completa materia, salon y docente.");
            return;
        }
        LocalTime i = Campos.hora(inicio), f = Campos.hora(fin);
        if (!f.isAfter(i)) {
            Campos.error(this, "La hora de fin debe ser despues de la de inicio.");
            return;
        }
        ctx.org.agregarClase(new Clase(mat, (DayOfWeek) dia.getSelectedItem(), i, f, sal, doc));
        materia.setText("");
        salon.setText("");
        ctx.cambiar();
    }

    private void eliminar() {
        int fila = Campos.filaSeleccionada(tabla, this);
        if (fila < 0 || !Campos.confirmar(this, "Eliminar esta clase y sus cambios de salon?")) return;
        ctx.org.eliminarClase(visibles.get(fila).getId());
        ctx.cambiar();
    }

    @Override
    public void refrescar() {
        modelo.setRowCount(0);
        visibles.clear();
        for (DayOfWeek d : DayOfWeek.values()) {
            for (Clase c : ctx.org.clasesDelDia(d)) {
                visibles.add(c);
                modelo.addRow(new Object[] {Campos.dia(d), c.getInicio(), c.getFin(), c.getTitulo(), c.getSalon(), c.getDocente()});
            }
        }
    }
}
