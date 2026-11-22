package unigo;

import java.time.LocalDate;

/** Un examen es una Tarea especial: siempre prioridad ALTA y con temas a estudiar. */
public class Examen extends Tarea {
    private final String temas;

    public Examen(String materia, LocalDate fecha, String temas) {
        super("Examen", materia, fecha, Prioridad.ALTA);
        this.temas = temas;
    }

    /** Constructor usado al cargar desde el archivo. */
    public Examen(int id, String materia, LocalDate fecha, String temas) {
        super(id, "Examen", materia, fecha, Prioridad.ALTA);
        this.temas = temas;
    }

    public String getTemas() { return temas; }

    @Override
    public String getTipo() { return "Examen"; }

    @Override
    public String getDetalle() { return temas; }

    /** SOBREESCRITURA: reutiliza el resumen de Tarea y agrega los temas. */
    @Override
    public String resumen() {
        return "EXAMEN " + super.resumen() + " - temas: " + temas;
    }

    @Override
    public String aLinea() {
        return Persistible.unir("E", getId(), getMateria(), getFechaLimite(), temas, isCompletada());
    }
}
