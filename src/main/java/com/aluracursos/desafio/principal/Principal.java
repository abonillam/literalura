package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.Datos;
import com.aluracursos.desafio.model.DatosLibros;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    public void muestraElMenu(){
        var opcion = 100;
        while (opcion != 0) {
            var menu = """
                    1 - Consultar por Título de libro
                    2 - Consultar por Autor
                    3 - Libros por Idioma
                    4 - Autores vivos por año
                    5 - Top de Libros más descargados
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    //Consultar libros
                    System.out.println("Ingrese el título del libro que desea buscar: ");
                    var tituloLibro = teclado.nextLine();
                    var json1 = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "%20"));
                    //System.out.println(json1);
                    var datosLibro = conversor.obtenerDatos(json1, Datos.class);
                    datosLibro.resultados().stream()
                            //.sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                            .limit(100)
                            .map(l -> "Título: " + l.titulo().toUpperCase() + " (Autor: " + l.autor() +", Idioma: " + l.idiomas() + ")" )
                            .forEach(System.out::println);
                    System.out.println("\n");
                    break;
                case 2:
                    //Consultar Autor
                    System.out.println("Ingrese el autor del libro que desea buscar: ");
                    var autorLibro = teclado.nextLine();
                    var json2 = consumoAPI.obtenerDatos(URL_BASE + "?search=" + autorLibro.replace(" ", "%20"));
                    //System.out.println(json2);
                    var libroAutor = conversor.obtenerDatos(json2, Datos.class);
                    libroAutor.resultados().stream()
                            //.sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                            .limit(100)
                            .map(l -> "Título: " + l.titulo().toUpperCase() + " (Autor: " + l.autor() +", Idioma: " + l.idiomas() + ")" )
                            .forEach(System.out::println);
                    System.out.println("\n");
                    break;
                case 3:
                    //Libros por Idioma
                    System.out.println("Ingrese el idioma del libro que desea buscar: ");
                    var idiomaLibro = teclado.nextLine();
                    var json3 = consumoAPI.obtenerDatos(URL_BASE + "?languages=" + idiomaLibro.replace(" ", ""));
                    //System.out.println(json3);
                    var libroIdioma = conversor.obtenerDatos(json3, Datos.class);
                    libroIdioma.resultados().stream()
                            //.sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                            .limit(100)
                            .map(l -> "Título: " + l.titulo().toUpperCase() + " (Autor: " + l.autor() +", Idioma: " + l.idiomas() + ")" )
                            .forEach(System.out::println);
                    System.out.println("\n");
                    break;
                case 4:
                    //Autores vivos por año
                    System.out.println("Ingrese el año que desea buscar: ");
                    var anioLibro = teclado.nextLine();
                    var json4 = consumoAPI.obtenerDatos(URL_BASE + "?author_year_end=" + anioLibro.replace(" ", ""));
                    //System.out.println(json4);
                    var libroAnio = conversor.obtenerDatos(json4, Datos.class);
                    libroAnio.resultados().stream()
                            //.sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                            .limit(100)
                            .map(l -> "Título: " + l.titulo().toUpperCase() + " (Autor: " + l.autor() +", Idioma: " + l.idiomas() + ")" )
                            .forEach(System.out::println);
                    System.out.println("\n");
                    break;
                case 5:
                    //Top de Libros más descargados
                    var json5 = consumoAPI.obtenerDatos(URL_BASE);
                    //System.out.println(json5);
                    var datos5 = conversor.obtenerDatos(json5, Datos.class);
                    //System.out.println(datos5);

                    //Top 10 libros más descargados
                    System.out.println("Top 10 libros más descargados");
                    datos5.resultados().stream()
                            .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                            .limit(100)
                            .map(l -> "Título: " + l.titulo().toUpperCase() + " (Autor: " + l.autor() +", Idioma: " + l.idiomas() + ")" )
                            .forEach(System.out::println);
                    System.out.println("\n");

                    break;
                case 0:
                    System.out.println("Gracias por usar la aplicación.\nCerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}
