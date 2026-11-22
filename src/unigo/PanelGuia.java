package unigo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/** Pestana "Soy nuevo": lista de pasos con barra de progreso y una guia rapida de la universidad. */
public class PanelGuia extends PanelBase {
    private static final String INFO_UNIVERSIDAD =
            "GUIA RAPIDA DE LA UNIVERSIDAD\n\n"
            + "- Bloques: los salones estan en tres bloques, A, B y C.\n\n"
            + "- Como leer un salon: la letra es el bloque, el primer numero es el piso y los dos ultimos "
            + "son el numero del salon. Ejemplo: A410 es el bloque A, piso 4, salon 10. B205 es el bloque B, piso 2, salon 05.\n\n"
            + "- Pisos: el bloque A va del piso 2 al 5, y los bloques B y C del piso 2 al 6.\n\n"
            + "- Cafeterias: hay una en el bloque A (piso 1), otra en el bloque B (piso 1) y otra en el bloque C (piso 5).\n\n"
            + "- Biblioteca: esta en el bloque A y ocupa los pisos 2 al 4.\n\n"
            + "- Pasillo de las casetas blancas: al pasar los torniquetes encuentras varias oficinas utiles, como registro, "
            + "soporte TI y la enfermeria (area de salud). Ve alli si necesitas ayuda.\n\n"
            + "- Bienestar universitario: saliendo de la universidad, a la derecha pasan unas canchas y, en diagonal hacia "
            + "la derecha, esta una casa blanca. Alli ofrecen psicologia y otros servicios.\n\n"
            + "Consejo: usa la pestana Mapa para ubicar cada lugar.";

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
            pasos.add(Box.createVerticalStrut(6));
        }

        // Texto informativo que aparece despues de las casillas
        JTextArea info = new JTextArea(INFO_UNIVERSIDAD);
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setFont(info.getFont().deriveFont(Font.PLAIN, 14f));
        info.setMargin(new Insets(8, 8, 8, 8));
        info.setCaretPosition(0);

        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.add(pasos, BorderLayout.NORTH);
        centro.add(new JScrollPane(info), BorderLayout.CENTER);

        barra.setStringPainted(true);
        add(new JLabel("Sigue estos pasos para ubicarte en tu primera semana:"), BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(barra, BorderLayout.SOUTH);
        refrescar();
    }

    @Override
    public void refrescar() {
        for (int i = 0; i < casillas.size(); i++) casillas.get(i).setSelected(ctx.guia.hecho(i));
        barra.setValue(ctx.guia.progreso());
    }
}
