package unigo;

import java.time.DayOfWeek;
import java.time.LocalTime;

/** Una clase del horario semanal. */
public class Clase extends ItemAcademico implements Persistible {
    private final DayOfWeek dia;
    private final LocalTime inicio;
    private final LocalTime fin;
    private final String salon;
    private final String docente;

    public Clase(String materia, DayOfWeek dia, LocalTime inicio, LocalTime fin, String salon, String docente) {
        super(materia, materia);
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
        this.salon = salon;
        this.docente = docente;
    }

    /** Constructor usado al cargar desde el archivo. */
    public Clase(int id, String materia, DayOfWeek dia, LocalTime inicio, LocalTime fin, String salon, String docente) {
        super(id, materia, materia);
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
        this.salon = salon;
        this.docente = docente;
    }

    public DayOfWeek getDia() { return dia; }
    public LocalTime getInicio() { return inicio; }
    public LocalTime getFin() { return fin; }
    public String getSalon() { return salon; }
    public String getDocente() { return docente; }

    @Override
    public String resumen() {
        return String.format("%s - %s a %s - salon %s - %s", getTitulo(), inicio, fin, salon, docente);
    }

    @Override
    public String aLinea() {
        return Persistible.unir("C", getId(), getTitulo(), dia.getValue(), inicio, fin, salon, docente);
    }
}
