package unigo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** Logica principal: tareas, horario y cambios de salon. */
public class Organizador {
    private final List<Tarea> tareas = new ArrayList<>();
    private final List<Clase> clases = new ArrayList<>();
    private final List<CambioSalon> cambios = new ArrayList<>();

    // ----- Tareas (tareas y examenes se guardan juntos: POLIMORFISMO) -----
    public void agregarTarea(Tarea t) { tareas.add(t); }

    public List<Tarea> getTareas() { return Collections.unmodifiableList(tareas); }

    public List<Tarea> tareasOrdenadas() {
        List<Tarea> copia = new ArrayList<>(tareas);
        Collections.sort(copia);
        return copia;
    }

    public boolean completarTarea(int id) {
        return tareas.stream().filter(t -> t.getId() == id).findFirst()
                .map(t -> { t.completar(); return true; }).orElse(false);
    }

    public boolean eliminarTarea(int id) { return tareas.removeIf(t -> t.getId() == id); }

    public long contarPendientes() { return tareas.stream().filter(t -> !t.isCompletada()).count(); }

    public long contarVencidas() { return tareas.stream().filter(Tarea::estaVencida).count(); }

    public List<Tarea> proximasEntregas(int maximo) {
        return tareasOrdenadas().stream().filter(t -> !t.isCompletada()).limit(maximo).collect(Collectors.toList());
    }

    // ----- Horario -----
    public void agregarClase(Clase c) { clases.add(c); }

    public List<Clase> getClases() { return Collections.unmodifiableList(clases); }

    public Optional<Clase> buscarClase(int id) {
        return clases.stream().filter(c -> c.getId() == id).findFirst();
    }

    /** Al eliminar una clase tambien se eliminan sus cambios de salon. */
    public boolean eliminarClase(int id) {
        cambios.removeIf(x -> x.getClaseId() == id);
        return clases.removeIf(c -> c.getId() == id);
    }

    public List<Clase> clasesDelDia(DayOfWeek dia) {
        return clases.stream().filter(c -> c.getDia() == dia)
                .sorted(Comparator.comparing(Clase::getInicio)).collect(Collectors.toList());
    }

    // ----- Cambios de salon -----
    public void registrarCambio(CambioSalon cambio) { cambios.add(cambio); }

    public List<CambioSalon> getCambios() { return Collections.unmodifiableList(cambios); }

    public boolean eliminarCambio(CambioSalon c) { return cambios.remove(c); }

    /** Devuelve el salon real de una clase en una fecha, considerando los cambios. */
    public String salonEfectivo(Clase clase, LocalDate fecha) {
        String salon = clase.getSalon();
        for (CambioSalon c : cambios) {
            if (c.aplicaA(clase, fecha)) salon = c.getNuevoSalon();
        }
        return salon;
    }
}
