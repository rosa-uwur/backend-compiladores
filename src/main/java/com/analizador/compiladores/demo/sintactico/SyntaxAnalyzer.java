package com.analizador.compiladores.demo.sintactico;
import com.analizador.compiladores.demo.lexico.TokenType;
import com.analizador.compiladores.demo.lexico.Tokenv2;

import java.util.ArrayList;
import java.util.List;

public class SyntaxAnalyzer {

    private List<Tokenv2> tokens;
    private int currentTokenIndex;
    private List<String> errorTable;

    public SyntaxAnalyzer(List<Tokenv2> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
        this.errorTable = new ArrayList<>();
    }

    public void analyze() {
        // Lógica de análisis sintáctico aquí
        // Puedes implementar reglas de gramática para el lenguaje PHP-like
        // y recorrer la lista de tokens para validar la estructura del código

        // Ejemplo de análisis básico
        while (currentTokenIndex < tokens.size()) {
            Tokenv2 currentToken = tokens.get(currentTokenIndex);

            // Ejemplo de validación de un token específico
            if (currentToken.getType() == TokenType.IF) {
                // Realizar acciones específicas para el token IF
            } else {
                // Manejar otros casos según la gramática del lenguaje
                errorTable.add("Error: Token inesperado - " + currentToken.getType());
            }

            currentTokenIndex++;
        }
    }

    public List<String> getErrorTable() {
        return errorTable;
    }

}