package unigo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

/** Datos de ejemplo (horario y tareas) que se cargan la primera vez que se abre UniGo. */
public final class DatosEjemplo {
    private DatosEjemplo() { }

    public static void cargar(Organizador org) {
        // ----- Horario -----
        String poo = "Programacion Orientada a Objetos";
        String calculo = "Calculo Diferencial";
        String algebra = "Algebra Lineal";
        String metodologia = "Metodologia de la Investigacion";

        org.agregarClase(new Clase(poo, DayOfWeek.MONDAY, LocalTime.of(13, 45), LocalTime.of(15, 15), "A406", "Fernando Ramirez Gonzalez"));
        org.agregarClase(new Clase(poo, DayOfWeek.TUESDAY, LocalTime.of(13, 45), LocalTime.of(15, 15), "A408", "Fernando Ramirez Gonzalez"));

        org.agregarClase(new Clase(calculo, DayOfWeek.TUESDAY, LocalTime.of(15, 15), LocalTime.of(16, 45), "B310", "Por definir"));
        org.agregarClase(new Clase(calculo, DayOfWeek.THURSDAY, LocalTime.of(13, 45), LocalTime.of(15, 15), "B310", "Por definir"));

        org.agregarClase(new Clase(algebra, DayOfWeek.MONDAY, LocalTime.of(16, 45), LocalTime.of(18, 15), "B310", "Por definir"));
        org.agregarClase(new Clase(algebra, DayOfWeek.WEDNESDAY, LocalTime.of(13, 45), LocalTime.of(15, 15), "B310", "Por definir"));

        org.agregarClase(new Clase(metodologia, DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(18, 15), "Por definir", "Por definir"));

        // ----- Tareas (todas para el proximo lunes) -----
        LocalDate lunes = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        org.agregarTarea(new Examen(algebra, lunes, "Parcial de Algebra Lineal"));
        org.agregarTarea(new Tarea("Entrega proyecto de programacion", poo, lunes, Prioridad.ALTA));
        org.agregarTarea(new Tarea("CRAI en ingles", poo, lunes, Prioridad.MEDIA));
    }
}
