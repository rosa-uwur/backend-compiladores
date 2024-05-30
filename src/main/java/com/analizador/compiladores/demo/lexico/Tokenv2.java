package com.analizador.compiladores.demo.lexico;
import com.analizador.compiladores.demo.lexico.TokenType;



public class Tokenv2 {

    public final TokenType type;
    private final Object value;
    public String lexema;

    public int linea;

    public String categoriaLexica;


    public Tokenv2(TokenType type, Object value, int linea) {
        this.type = type;
        this.value = value;
        this.linea = linea;
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
            case ENDPROGRAM:
                categoriaLexica = "Fin programa";
                break;
            case RESERVED_WORD:
                categoriaLexica = "Palabra reservada";
                break;
            case DIGIT:
                categoriaLexica = "Digito";
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

    public TokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getCategoriaLexica() {
        return categoriaLexica;
    }

    public void setCategoriaLexica(String categoriaLexica) {
        this.categoriaLexica = categoriaLexica;
    }
}
