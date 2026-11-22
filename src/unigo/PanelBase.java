package unigo;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/** Panel abstracto: todas las pestanas heredan de el y deben saber refrescarse (POLIMORFISMO). */
public abstract class PanelBase extends JPanel {
    protected final Contexto ctx;

    protected PanelBase(Contexto ctx) {
        super(new BorderLayout(10, 10));
        this.ctx = ctx;
        setBorder(Campos.margen());
        ctx.registrar(this);
    }

    /** Vuelve a leer los datos del modelo y actualiza lo que se ve en pantalla. */
    public abstract void refrescar();
}
