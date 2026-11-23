package unigo;

/** Lugares del campus que aparecen en la pestana Mapa. */
public final class DatosCampus {
    private DatosCampus() { }

    public static void cargar(Campus campus) {
        
        campus.agregar(new Edificio("Bloque A",
                "Salones de clase. En el piso 1 hay una cafeteria y la biblioteca esta en los pisos 2 al 4.",
                5, "del piso 2 al 5, hasta el 10 u 11 aprox. (ejemplo: A410 = piso 4, salon 10)"));
        campus.agregar(new Edificio("Bloque B",
                "Salones de clase. En el piso 1 hay una cafeteria.",
                6, "del piso 2 al 6, hasta el 10 u 11 aprox. (ejemplo: B205 = piso 2, salon 05)"));
        
        campus.agregar(new Edificio("Bloque C",
                "Salones de clase. En el piso 5 hay una cafeteria.",
                6, "del piso 2 al 6, hasta el 10 u 11 aprox. (ejemplo: C204 = piso 2, salon 04)"));


        campus.agregar(new ZonaServicio("Biblioteca",
                "Esta en el bloque A y ocupa los pisos 2 al 4.", "Por confirmar"));
        campus.agregar(new ZonaServicio("Cafeteria bloque A",
                "Esta en el bloque A, piso 1.", "Por confirmar"));
        campus.agregar(new ZonaServicio("Cafeteria bloque B",
                "Esta en el bloque B, piso 1.", "Por confirmar"));
        campus.agregar(new ZonaServicio("Cafeteria bloque C",
                "Esta en el bloque C, piso 5.", "Por confirmar"));



        campus.agregar(new ZonaServicio("Soporte",
                "Saliendo de los torniquetes, por el pasillo de las casetas blancas. Alli hay varias oficinas utiles, como registro y soporte TI.",
                "Por confirmar"));
        campus.agregar(new ZonaServicio("Enfermeria",
                "Es el area de salud. Esta en el pasillo de las casetas blancas, saliendo de los torniquetes. Acude aqui si necesitas ayuda.",
                "Por confirmar"));

       
        campus.agregar(new ZonaServicio("Bienestar universitario",
                "Saliendo de la universidad, a la derecha pasan unas canchas y, en diagonal hacia la derecha, esta la casa blanca de Bienestar. Ofrece psicologia y otros servicios.",
                "Por confirmar"));
    }
}
