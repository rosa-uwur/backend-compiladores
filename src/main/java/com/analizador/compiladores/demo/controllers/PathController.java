package com.analizador.compiladores.demo.controllers;

import com.analizador.compiladores.demo.estructuras.TextoRequest;
import com.analizador.compiladores.demo.lexico.AnalizadorLexico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
    @PostMapping("/analisisLexico")
    public ResponseEntity<AnalizadorLexico> recibirContenido(@RequestBody TextoRequest texto) throws IOException {
        System.out.println("ENTRA ANALISIS LEXICO");
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
            return ResponseEntity.ok(lexico); // Devuelve el objeto lexico con status HTTP OK
        } catch (IllegalArgumentException e) {
            // Manejo de la excepción de argumento inválido
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Devuelve un status de error 400
        }
    }

}
