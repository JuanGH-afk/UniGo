package unigo;

/** Contrato para todo lo que se puede ubicar en el mapa del campus. */
public interface Ubicable {
    String getNombre();
    String getDescripcion();

    /** Metodo por defecto que arma la ficha del lugar. */
    default String ficha() {
        return getNombre() + ": " + getDescripcion();
    }
}
