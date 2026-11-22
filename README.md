# UniGo - Organizador Universitario (versión beta)

Aplicación de escritorio en Java (Swing) que ayuda a los estudiantes a organizar su vida universitaria.
Proyecto de **Programación Orientada a Objetos** — UNIMINUTO, Ingeniería de Sistemas.

## Funcionalidades
- **Inicio:** resumen del día (tareas pendientes y vencidas, clases de hoy, próximas entregas).
- **Tareas y exámenes:** agregar, completar y eliminar; filtro y orden por fecha y prioridad (las vencidas salen en rojo).
- **Horario semanal:** agregar y eliminar clases con salón y docente.
- **Cambio de salón:** registra el aviso y se refleja en el resumen del día.
- **Mapa del campus:** buscador, plano clicable y ficha de cada lugar.
- **Soy nuevo:** guía de primera semana con barra de progreso.
- **Persistencia:** todo se guarda automáticamente en `datos/unigo.txt` y se carga al abrir.

## Conceptos de POO aplicados
| Concepto | Dónde |
|---|---|
| Abstracción | `ItemAcademico`, `Lugar` y `PanelBase` (clases abstractas) |
| Herencia | `Tarea`, `Clase` → `ItemAcademico`; `Examen` → `Tarea`; `Edificio`, `ZonaServicio` → `Lugar`; cada `Panel...` → `PanelBase` |
| Polimorfismo | `resumen()`, `ficha()`, `aLinea()`, `getTipo()` y `refrescar()` se comportan distinto según el objeto |
| Interfaces | `Ubicable`, `Persistible`, `Comparable<Tarea>` |
| Encapsulamiento | Atributos privados y acceso por métodos |
| Sobreescritura | `Examen.resumen()`, `Examen.aLinea()`, `Examen.getTipo()` |
| Enumeraciones | `Prioridad` |

## Estructura (paquete `unigo`)
- **Modelo:** `ItemAcademico`, `Tarea`, `Examen`, `Clase`, `CambioSalon`, `Prioridad`, `Lugar`, `Edificio`, `ZonaServicio`, `Ubicable`, `Campus`, `GuiaNuevo`
- **Lógica:** `Organizador`
- **Persistencia:** `Almacen`, `Persistible`
- **Interfaz:** `Main`, `VentanaPrincipal`, `PanelBase` y sus paneles (`PanelResumen`, `PanelTareas`, `PanelHorario`, `PanelCambios`, `PanelMapa`, `PanelGuia`), `Campos`, `Contexto`

## Cómo ejecutarlo
**Eclipse:** File → New → Java Project, copia la carpeta `unigo` (dentro de `src`) en el `src` del proyecto y ejecuta `Main`
(Run As → Java Application). Requiere Java 9 o superior.

**Terminal:**
```
javac -d bin $(find src -name "*.java")
java -cp bin unigo.Main
```

## Autores
Juan José Raigosa Cruz, Santiago Ortega Díaz, Juan Manuel Urbano Arboleda.
Docente: Fernando Ramírez González.
