package unigo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Una tarea con fecha de entrega y prioridad. HERENCIA: extiende ItemAcademico. */
public class Tarea extends ItemAcademico implements Comparable<Tarea>, Persistible {
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LocalDate fechaLimite;
    private final Prioridad prioridad;
    private boolean completada;

    public Tarea(String titulo, String materia, LocalDate fechaLimite, Prioridad prioridad) {
        super(titulo, materia);
        this.fechaLimite = fechaLimite;
        this.prioridad = prioridad;
    }

    /** Constructor usado al cargar desde el archivo. */
    public Tarea(int id, String titulo, String materia, LocalDate fechaLimite, Prioridad prioridad) {
        super(id, titulo, materia);
        this.fechaLimite = fechaLimite;
        this.prioridad = prioridad;
    }

    public void completar() { this.completada = true; }
    public boolean isCompletada() { return completada; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public Prioridad getPrioridad() { return prioridad; }

    public boolean estaVencida() {
        return !completada && fechaLimite.isBefore(LocalDate.now());
    }

    @Override
    public String resumen() {
        return String.format("%s %s (%s) - entrega %s - prioridad %s%s",
                completada ? "[x]" : "[ ]", getTitulo(), getMateria(),
                fechaLimite.format(FORMATO_FECHA), prioridad, estaVencida() ? " - VENCIDA" : "");
    }

    @Override
    public String aLinea() {
        return Persistible.unir("T", getId(), getTitulo(), getMateria(), fechaLimite, prioridad, completada);
    }

    public String getTipo() { return "Tarea"; }

    public String getDetalle() { return getTitulo(); }

    /** Orden natural: pendientes primero, luego por fecha y por prioridad. */
    @Override
    public int compareTo(Tarea otra) {
        int c = Boolean.compare(completada, otra.completada);
        if (c != 0) return c;
        c = fechaLimite.compareTo(otra.fechaLimite);
        return c != 0 ? c : prioridad.compareTo(otra.prioridad);
    }
}
