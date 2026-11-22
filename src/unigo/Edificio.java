package unigo;

/** Edificio con salones repartidos en pisos. */
public class Edificio extends Lugar {
    private final int pisos;
    private final String salones;

    public Edificio(String nombre, String descripcion, int pisos, String salones) {
        super(nombre, descripcion);
        this.pisos = pisos;
        this.salones = salones;
    }

    @Override
    protected String detalle() {
        return "Pisos: " + pisos + " - Salones: " + salones;
    }
}
