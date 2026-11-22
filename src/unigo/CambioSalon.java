package unigo;

import java.time.LocalDate;

/** Aviso de que una clase cambia de salon en una fecha concreta. */
public class CambioSalon implements Persistible {
    private final Clase clase;
    private final String nuevoSalon;
    private final LocalDate fecha;

    public CambioSalon(Clase clase, String nuevoSalon, LocalDate fecha) {
        this.clase = clase;
        this.nuevoSalon = nuevoSalon;
        this.fecha = fecha;
    }

    public String getNuevoSalon() { return nuevoSalon; }
    public int getClaseId() { return clase.getId(); }
    public Clase getClase() { return clase; }
    public LocalDate getFecha() { return fecha; }

    public boolean aplicaA(Clase otra, LocalDate dia) {
        return clase.getId() == otra.getId() && fecha.equals(dia);
    }

    @Override
    public String aLinea() {
        return Persistible.unir("X", clase.getId(), nuevoSalon, fecha);
    }
}
