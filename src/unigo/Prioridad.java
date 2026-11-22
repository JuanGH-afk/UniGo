package unigo;

/** Niveles de prioridad de una tarea (el orden del enum define el orden de importancia). */
public enum Prioridad {
    ALTA, MEDIA, BAJA;

    /** Convierte un texto en prioridad; si no coincide, devuelve MEDIA. */
    public static Prioridad desde(String texto) {
        for (Prioridad p : values()) {
            if (p.name().equalsIgnoreCase(texto.trim())) return p;
        }
        return MEDIA;
    }
}
