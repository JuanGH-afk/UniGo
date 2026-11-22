package unigo;

/** Lugar del campus (clase abstracta que implementa la interfaz Ubicable). */
public abstract class Lugar implements Ubicable {
    private final String nombre;
    private final String descripcion;

    protected Lugar(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @Override public String getNombre() { return nombre; }
    @Override public String getDescripcion() { return descripcion; }

    /** Dato extra propio de cada tipo de lugar. */
    protected abstract String detalle();

    @Override
    public String ficha() {
        return Ubicable.super.ficha() + " | " + detalle();
    }
}
