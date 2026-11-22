package unigo;

/** Lugares del campus que aparecen en la pestana Mapa. */
public final class DatosCampus {
    private DatosCampus() { }

    public static void cargar(Campus campus) {
        campus.agregar(new Edificio("Bloque A", "Aulas de los primeros semestres", 3, "A-101 a A-312"));
        campus.agregar(new Edificio("Laboratorio de sistemas", "Salas de computo", 2, "L-1 a L-6"));
        campus.agregar(new ZonaServicio("Biblioteca", "Libros, cubiculos y estudio", "Lunes a viernes 7:00 - 20:00"));
        campus.agregar(new ZonaServicio("Cafeteria", "Almuerzos y descanso", "Todos los dias 7:00 - 18:00"));
        campus.agregar(new ZonaServicio("Bienestar universitario", "Apoyo, deportes y cultura", "Lunes a viernes 8:00 - 17:00"));
        campus.agregar(new Edificio("Administracion", "Matriculas, certificados y tramites", 2, "Oficinas"));
    }
}
