package unigo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/** Pestana "Soy nuevo": lista de pasos con barra de progreso. */
public class PanelGuia extends PanelBase {
    private final List<JCheckBox> casillas = new ArrayList<>();
    private final JProgressBar barra = new JProgressBar(0, 100);

    public PanelGuia(Contexto ctx) {
        super(ctx);
        Box pasos = Box.createVerticalBox();
        for (int i = 0; i < ctx.guia.tamano(); i++) {
            final int numero = i + 1;
            JCheckBox c = new JCheckBox(numero + ". " + ctx.guia.texto(i));
            c.setFont(c.getFont().deriveFont(Font.PLAIN, 15f));
            c.addActionListener(e -> { ctx.guia.alternar(numero); ctx.cambiar(); });
            casillas.add(c);
            pasos.add(c);
            pasos.add(Box.createVerticalStrut(10));
        }
        barra.setStringPainted(true);
        add(new JLabel("Sigue estos pasos para ubicarte en tu primera semana:"), BorderLayout.NORTH);
        add(new JScrollPane(pasos), BorderLayout.CENTER);
        add(barra, BorderLayout.SOUTH);
        refrescar();
    }

    @Override
    public void refrescar() {
        for (int i = 0; i < casillas.size(); i++) casillas.get(i).setSelected(ctx.guia.hecho(i));
        barra.setValue(ctx.guia.progreso());
    }
}
