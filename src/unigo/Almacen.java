package unigo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia: guarda y carga todos los datos en un archivo de texto (una linea por objeto).
 * Escribe primero en un archivo temporal para no danar los datos si algo falla a mitad.
 */
public class Almacen {
    private final Path archivo;

    public Almacen(String ruta) { this.archivo = Paths.get(ruta); }

    public boolean existe() { return Files.exists(archivo); }

    public String getRuta() { return archivo.toString(); }

    public void guardar(Organizador org, GuiaNuevo guia) throws IOException {
        List<String> lineas = new ArrayList<>();
        org.getTareas().forEach(t -> lineas.add(t.aLinea()));      // T y E (polimorfismo)
        org.getClases().forEach(c -> lineas.add(c.aLinea()));      // C
        org.getCambios().forEach(x -> lineas.add(x.aLinea()));     // X (despues de las clases)
        lineas.add(Persistible.unir("G", guia.estado()));          // G (guia "Soy nuevo")

        if (archivo.getParent() != null) Files.createDirectories(archivo.getParent());
        Path temporal = Paths.get(archivo + ".tmp");
        Files.write(temporal, lineas, StandardCharsets.UTF_8);
        Files.move(temporal, archivo, StandardCopyOption.REPLACE_EXISTING);
    }

    public void cargar(Organizador org, GuiaNuevo guia) throws IOException {
        for (String linea : Files.readAllLines(archivo, StandardCharsets.UTF_8)) {
            if (linea.trim().isEmpty()) continue;
            String[] p = Persistible.separar(linea);
            try {
                switch (p[0]) {
                    case "T": {
                        Tarea t = new Tarea(Integer.parseInt(p[1]), p[2], p[3], LocalDate.parse(p[4]), Prioridad.desde(p[5]));
                        if (Boolean.parseBoolean(p[6])) t.completar();
                        org.agregarTarea(t);
                        break;
                    }
                    case "E": {
                        Examen examen = new Examen(Integer.parseInt(p[1]), p[2], LocalDate.parse(p[3]), p[4]);
                        if (Boolean.parseBoolean(p[5])) examen.completar();
                        org.agregarTarea(examen);
                        break;
                    }
                    case "C":
                        org.agregarClase(new Clase(Integer.parseInt(p[1]), p[2], DayOfWeek.of(Integer.parseInt(p[3])),
                                LocalTime.parse(p[4]), LocalTime.parse(p[5]), p[6], p[7]));
                        break;
                    case "X":
                        org.buscarClase(Integer.parseInt(p[1]))
                                .ifPresent(c -> org.registrarCambio(new CambioSalon(c, p[2], LocalDate.parse(p[3]))));
                        break;
                    case "G":
                        guia.restaurar(p[1]);
                        break;
                    default:
                        break;
                }
            } catch (RuntimeException ex) {
                System.out.println("Aviso: se omitio una linea danada del archivo de datos.");
            }
        }
    }
}
