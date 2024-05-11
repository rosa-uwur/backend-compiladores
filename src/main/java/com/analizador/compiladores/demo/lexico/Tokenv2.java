package com.analizador.compiladores.demo.lexico;
import com.analizador.compiladores.demo.lexico.TokenType;



public class Tokenv2 {

    private final TokenType type;
    private final Object value;
    public String lexema;

    public String categoriaLexica;


    public Tokenv2(TokenType type, Object value) {
        this.type = type;
        this.value = value;
        this.lexema = String.valueOf(value);
        this.categoriaLexica = asignarCategoriaLexica(type);
    }

    public String asignarCategoriaLexica(TokenType tokenType) {
        String categoriaLexica = "";
        switch (tokenType) {
            case IF:
                categoriaLexica = "Si";
                break;
            case ELSE:
                categoriaLexica = "Sino";
                break;
            case WHILE:
                categoriaLexica = "Mientras";
                break;
            case FOR:
                categoriaLexica = "Para";
                break;
            case FUNCTION:
                categoriaLexica = "Función";
                break;
            case RETURN:
                categoriaLexica = "Retornar";
                break;
            case BOOLEAN:
                categoriaLexica = "Booleano";
                break;
            case NULL:
                categoriaLexica = "Nulo";
                break;
            case IDENTIFIER:
                categoriaLexica = "Identificador";
                break;
            case INTEGER:
                categoriaLexica = "Entero";
                break;
            case STRING:
                categoriaLexica = "Cadena";
                break;
            case PLUS:
                categoriaLexica = "Más";
                break;
            case MINUS:
                categoriaLexica = "Menos";
                break;
            case MULTIPLY:
                categoriaLexica = "Multiplicar";
                break;
            case DIVIDE:
                categoriaLexica = "Dividir";
                break;
            case ASSIGN:
                categoriaLexica = "Asignar";
                break;
            case EQUAL_TO:
                categoriaLexica = "Igual a";
                break;
            case SEMICOLON:
                categoriaLexica = "Fin linea";
                break;
        }
    return categoriaLexica;
    }
    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
