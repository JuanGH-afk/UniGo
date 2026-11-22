package unigo;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/** Pestana para avisar cambios de salon. */
public class PanelCambios extends PanelBase {
    private final JComboBox<Clase> clase = new JComboBox<>();
    private final JTextField nuevoSalon = new JTextField(8);
    private final JSpinner fecha = Campos.selectorFecha();
    private final DefaultTableModel modelo = Campos.modelo("Clase", "Salon original", "Nuevo salon", "Fecha");
    private final JTable tabla = Campos.tabla(modelo);
    private final List<CambioSalon> visibles = new ArrayList<>();

    public PanelCambios(Contexto ctx) {
        super(ctx);
        clase.setRenderer(Campos.texto(o -> ((Clase) o).getTitulo() + " (" + Campos.dia(((Clase) o).getDia())
                + " " + ((Clase) o).getInicio() + ")"));
        JButton registrar = new JButton("Registrar cambio");
        registrar.addActionListener(e -> registrar());
        JButton quitar = new JButton("Quitar cambio seleccionado");
        quitar.addActionListener(e -> quitar());

        add(Campos.fila("Clase:", clase, "Nuevo salon:", nuevoSalon, "Fecha:", fecha, registrar), BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(Campos.fila(quitar), BorderLayout.SOUTH);
        refrescar();
    }

    private void registrar() {
        Clase c = (Clase) clase.getSelectedItem();
        String salon = nuevoSalon.getText().trim();
        if (c == null || salon.isEmpty()) {
            Campos.error(this, "Elige una clase (primero agrega clases en el horario) y escribe el nuevo salon.");
            return;
        }
        ctx.org.registrarCambio(new CambioSalon(c, salon, Campos.fecha(fecha)));
        nuevoSalon.setText("");
        ctx.cambiar();
    }

    private void quitar() {
        int fila = Campos.filaSeleccionada(tabla, this);
        if (fila < 0) return;
        ctx.org.eliminarCambio(visibles.get(fila));
        ctx.cambiar();
    }

    @Override
    public void refrescar() {
        clase.setModel(new DefaultComboBoxModel<>(ctx.org.getClases().toArray(new Clase[0])));
        modelo.setRowCount(0);
        visibles.clear();
        for (CambioSalon x : ctx.org.getCambios()) {
            visibles.add(x);
            modelo.addRow(new Object[] {x.getClase().getTitulo(), x.getClase().getSalon(), x.getNuevoSalon(),
                    x.getFecha().format(Tarea.FORMATO_FECHA)});
        }
    }
}
