package unigo;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/** Datos compartidos por toda la interfaz: modelo, persistencia y refresco de los paneles. */
public class Contexto {
    public final Organizador org = new Organizador();
    public final Campus campus = new Campus();
    public final GuiaNuevo guia = new GuiaNuevo();
    private final Almacen almacen = new Almacen("datos/unigo.txt");
    private final List<PanelBase> paneles = new ArrayList<>();

    public void iniciar() {
        cargarCampus();
        try {
            if (almacen.existe()) {
                almacen.cargar(org, guia);
            } else {
                cargarDatosEjemplo();
                guardar();
            }
        } catch (IOException e) {
            Campos.error(null, "No se pudo leer el archivo de datos: " + e.getMessage());
        }
    }

    public void registrar(PanelBase panel) { paneles.add(panel); }

    public void guardar() {
        try {
            almacen.guardar(org, guia);
        } catch (IOException e) {
            Campos.error(null, "No se pudo guardar: " + e.getMessage());
        }
    }

    /** Se llama despues de cualquier cambio: guarda y actualiza todos los paneles. */
    public void cambiar() {
        guardar();
        paneles.forEach(PanelBase::refrescar);
    }

    // Edita estos datos con tu campus real
    private void cargarCampus() {
        campus.agregar(new Edificio("Bloque A", "Aulas de los primeros semestres", 3, "A-101 a A-312"));
        campus.agregar(new Edificio("Laboratorio de sistemas", "Salas de computo", 2, "L-1 a L-6"));
        campus.agregar(new ZonaServicio("Biblioteca", "Libros, cubiculos y estudio", "Lunes a viernes 7:00 - 20:00"));
        campus.agregar(new ZonaServicio("Cafeteria", "Almuerzos y descanso", "Todos los dias 7:00 - 18:00"));
        campus.agregar(new ZonaServicio("Bienestar universitario", "Apoyo, deportes y cultura", "Lunes a viernes 8:00 - 17:00"));
        campus.agregar(new Edificio("Administracion", "Matriculas, certificados y tramites", 2, "Oficinas"));
    }

    /** Solo se usa la primera vez, cuando todavia no existe el archivo de datos. */
    private void cargarDatosEjemplo() {
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
