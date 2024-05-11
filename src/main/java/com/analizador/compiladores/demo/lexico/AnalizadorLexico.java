package com.analizador.compiladores.demo.lexico;

import com.analizador.compiladores.demo.estructuras.Archivo;
import com.analizador.compiladores.demo.estructuras.Lista;
import com.analizador.compiladores.demo.estructuras.Nodo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para el analizador léxico
 */
public class AnalizadorLexico {
  ArrayList<String> lineas;
  private final AFN automata;
  public Lista<Token> simbolos;

  public List<ErrorLexico> tablaErrores = new ArrayList<>();


  public AnalizadorLexico(ArrayList<String> lineas) {
    this.lineas = lineas;
    automata = new AFN(lineas);

    for (String linea: lineas){
      getSiguienteToken();
    }
  }

  /**
   * @return Siguiente token válido en el programa
   */
  private Token getSiguienteToken() { return automata.obtenerSiguientToken(); }

  /**
   * Mostrar los identificadores encontrados en el programa
   */
  public void imprimirSimbolos() {
    simbolos = automata.getSimbolos();
    Nodo<Token> nodoActual = simbolos.getCabeza();

    System.out.println("\nIdentificadores: ");
    while (nodoActual != null) {
      System.out.println(nodoActual.getDato().getLexema());
      nodoActual = nodoActual.getSiguiente();
    }
    System.out.println("Tabla de errores");
    this.tablaErrores = automata.tablaErrores;
    for(ErrorLexico error : automata.tablaErrores) {
      System.out.println("Mensaje: " + error.getMensaje());
      System.out.println("Línea: " + error.getLinea());
      System.out.println("Token: " + error.getToken());
      System.out.println(); // Salto de línea para separar cada instancia de ErrorLexico
    }
  }

  /**
   * Mostrar el programa a analizar
   */
  public void imprimirArchivo() {
    System.out.println("\nPrograma a analizar: ");
    for (String linea: lineas) {
      System.out.println(linea);
    }
  }
}
