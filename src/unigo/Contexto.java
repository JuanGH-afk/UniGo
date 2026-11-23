package unigo;

import java.io.IOException;
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
        DatosCampus.cargar(campus);
        try {
            if (almacen.existe()) {
                almacen.cargar(org, guia);
            } else {
                DatosEjemplo.cargar(org);
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
}
