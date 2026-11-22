package unigo;

/** Zona de servicio (biblioteca, cafeteria, bienestar...) con horario de atencion. */
public class ZonaServicio extends Lugar {
    private final String horario;

    public ZonaServicio(String nombre, String descripcion, String horario) {
        super(nombre, descripcion);
        this.horario = horario;
    }

    @Override
    protected String detalle() {
        return "Horario: " + horario;
    }
}
