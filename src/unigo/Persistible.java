package unigo;

/**
 * Contrato para los objetos que se guardan en el archivo de datos.
 * Cada objeto sabe convertirse en una linea de texto (POLIMORFISMO).
 */
public interface Persistible {
    String aLinea();

    /** Une los campos con tabuladores, escapando los caracteres especiales. */
    static String unir(Object... campos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < campos.length; i++) {
            if (i > 0) sb.append('\t');
            sb.append(String.valueOf(campos[i])
                    .replace("\\", "\\\\").replace("\t", "\\t").replace("\n", "\\n"));
        }
        return sb.toString();
    }

    /** Operacion inversa de unir(): separa una linea en sus campos originales. */
    static String[] separar(String linea) {
        String[] partes = linea.split("\t", -1);
        for (int i = 0; i < partes.length; i++) partes[i] = desescapar(partes[i]);
        return partes;
    }

    static String desescapar(String texto) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '\\' && i + 1 < texto.length()) {
                char siguiente = texto.charAt(++i);
                sb.append(siguiente == 't' ? '\t' : siguiente == 'n' ? '\n' : siguiente);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
