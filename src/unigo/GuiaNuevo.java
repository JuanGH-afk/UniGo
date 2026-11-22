package unigo;

import java.util.ArrayList;
import java.util.List;

/** Modo "Soy nuevo": lista de pasos para la primera semana. */
public class GuiaNuevo {
    private static class Paso {
        final String texto;
        boolean hecho;
        Paso(String texto) { this.texto = texto; }
    }

    private final List<Paso> pasos = new ArrayList<>();

    public GuiaNuevo() {
        String[] textos = {
            "Activa tu correo institucional",
            "Ingresa a la plataforma virtual y revisa tus materias",
            "Guarda tu horario en UniGo",
            "Ubica tus salones en el mapa del campus",
            "Visita la biblioteca y pregunta por el carne de prestamo",
            "Conoce Bienestar universitario y sus servicios",
            "Agrega tus primeras tareas en UniGo"
        };
        for (String t : textos) pasos.add(new Paso(t));
    }

    /** Marca o desmarca un paso (numero base 1). */
    public boolean alternar(int numero) {
        if (numero < 1 || numero > pasos.size()) return false;
        Paso p = pasos.get(numero - 1);
        p.hecho = !p.hecho;
        return true;
    }

    public int tamano() { return pasos.size(); }

    public String texto(int indice) { return pasos.get(indice).texto; }

    public boolean hecho(int indice) { return pasos.get(indice).hecho; }

    public int progreso() {
        long hechos = pasos.stream().filter(p -> p.hecho).count();
        return (int) (hechos * 100 / pasos.size());
    }

    /** Pasos completados como texto, por ejemplo "1,3,4" (para guardar en el archivo). */
    public String estado() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pasos.size(); i++) {
            if (pasos.get(i).hecho) sb.append(sb.length() > 0 ? "," : "").append(i + 1);
        }
        return sb.toString();
    }

    /** Operacion inversa de estado(): marca los pasos indicados. */
    public void restaurar(String estado) {
        for (String parte : estado.split(",")) {
            if (parte.trim().isEmpty()) continue;
            int n = Integer.parseInt(parte.trim());
            if (n >= 1 && n <= pasos.size()) pasos.get(n - 1).hecho = true;
        }
    }

    public void mostrar() {
        for (int i = 0; i < pasos.size(); i++) {
            Paso p = pasos.get(i);
            System.out.printf("%d. %s %s%n", i + 1, p.hecho ? "[x]" : "[ ]", p.texto);
        }
        System.out.println("Progreso: " + progreso() + "%");
    }
}
