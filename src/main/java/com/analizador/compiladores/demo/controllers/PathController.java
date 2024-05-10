package com.analizador.compiladores.demo.controllers;

import com.analizador.compiladores.demo.estructuras.TextoRequest;
import com.analizador.compiladores.demo.lexico.AnalizadorLexico;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@RestController
public class PathController {
    @RequestMapping("/")
    String hellow()
    {
        return "Hello World!";
    }


    @PostMapping("/analisisLexico")
    public AnalizadorLexico recibirContenido(@RequestBody TextoRequest texto) throws IOException {
        try {
            if (texto == null) {
                throw new IllegalArgumentException("El objeto 'texto' es nulo.");
            }
            ArrayList<String> contenido = new ArrayList<>();

            String[] lineas = texto.getTexto().split("\\r?\\n");

            for (String linea : lineas) {
                contenido.add(linea);
                System.out.println(linea);
            }

            AnalizadorLexico lexico = new AnalizadorLexico(contenido);

            lexico.imprimirSimbolos();

            System.out.println("a");
            return lexico;
        } catch (IllegalArgumentException e) {
            // Manejo de la excepción de argumento inválido
            System.err.println("Error: " + e.getMessage());
            return null; // o lanzar una excepción específica de tu aplicación
        }
    }

}
