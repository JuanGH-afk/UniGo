package unigo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** Mapa del campus: guarda los lugares y permite buscarlos. */
public class Campus {
    private final List<Lugar> lugares = new ArrayList<>();

    public void agregar(Lugar lugar) { lugares.add(lugar); }

    public List<Lugar> getLugares() { return Collections.unmodifiableList(lugares); }

    public List<Lugar> buscar(String texto) {
        String t = texto.toLowerCase();
        return lugares.stream()
                .filter(l -> l.getNombre().toLowerCase().contains(t))
                .collect(Collectors.toList());
    }
}
