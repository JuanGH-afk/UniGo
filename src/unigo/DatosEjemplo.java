package unigo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/** Datos de ejemplo (horario y tareas) que se cargan la primera vez que se abre UniGo. */
public final class DatosEjemplo {
    private DatosEjemplo() { }

    public static void cargar(Organizador org) {
        org.agregarClase(new Clase("Programacion Orientada a Objetos", DayOfWeek.MONDAY,
                LocalTime.of(7, 0), LocalTime.of(9, 0), "L-2", "Fernando Ramirez Gonzalez"));
        org.agregarClase(new Clase("Calculo Diferencial", DayOfWeek.TUESDAY,
                LocalTime.of(9, 0), LocalTime.of(11, 0), "A-204", "Por definir"));

        LocalDate hoy = LocalDate.now();
        org.agregarTarea(new Tarea("Entregar documento de UniGo", "Programacion Orientada a Objetos",
                hoy.plusDays(7), Prioridad.ALTA));
        org.agregarTarea(new Examen("Calculo Diferencial", hoy.plusDays(10), "Derivadas y recta tangente"));
    }
}
