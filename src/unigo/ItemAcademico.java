package unigo;

/**
 * Clase base abstracta de todo lo que el estudiante organiza (tareas, examenes, clases).
 * Aplica ABSTRACCION y ENCAPSULAMIENTO: atributos privados y un metodo abstracto resumen().
 */
public abstract class ItemAcademico {
    private static int contador = 0;

    private final int id;
    private final String titulo;
    private final String materia;

    /** Constructor para objetos nuevos: asigna el siguiente id disponible. */
    protected ItemAcademico(String titulo, String materia) {
        this(contador + 1, titulo, materia);
    }

    /** Constructor para objetos cargados del archivo: conserva su id original. */
    protected ItemAcademico(int id, String titulo, String materia) {
        this.id = id;
        this.titulo = titulo;
        this.materia = materia;
        contador = Math.max(contador, id);
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMateria() { return materia; }

    /** Cada subclase decide como se muestra (POLIMORFISMO). */
    public abstract String resumen();

    @Override
    public String toString() {
        return "#" + id + " " + resumen();
    }
}
