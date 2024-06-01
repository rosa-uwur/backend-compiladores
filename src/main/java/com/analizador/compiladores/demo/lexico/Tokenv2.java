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
            case NOT_EQUAL_TO:
                categoriaLexica = "Distinto de";
                break;
            case LESS_THAN:
                categoriaLexica = "Menor que";
                break;
            case LESS_THAN_OR_EQUAL_TO:
                categoriaLexica = "Menor o igual que";
                break;
            case GREATER_THAN:
                categoriaLexica = "Mayor que";
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                categoriaLexica = "Mayor o igual que";
                break;
            case LEFT_PAREN:
                categoriaLexica = "Paréntesis izquierdo";
                break;
            case RIGHT_PAREN:
                categoriaLexica = "Paréntesis derecho";
                break;
            case LEFT_BRACE:
                categoriaLexica = "Llave izquierda";
                break;
            case RIGHT_BRACE:
                categoriaLexica = "Llave derecha";
                break;
            case COMMA:
                categoriaLexica = "Coma";
                break;
            case DO_WHILE:
                categoriaLexica = "Hacer mientras";
                break;
            case END_LINE:
                categoriaLexica = "Fin linea";
                break;
            case PRINTLN:
                categoriaLexica = "Fin linea";
                break;
            case EXCEPTION:
                categoriaLexica = "Excepcion";
                break;
            case TRY:
                categoriaLexica = "Try";
                break;
            case CATCH:
                categoriaLexica = "Catch";
                break;
            case FOREACH:
                categoriaLexica = "For each";
                break;
            case CLASS:
                categoriaLexica = "Clase";
                break;
            case STATIC:
                categoriaLexica = "Static";
                break;
            case CONTS:
                categoriaLexica = "Const";
                break;
            case NAMESPACE:
                categoriaLexica = "Namespace";
                break;
            case USE:
                categoriaLexica = "Use";
                break;
            case INCLUDE:
                categoriaLexica = "Include";
                break;
            case CONSTRUCT:
                categoriaLexica = "Construct";
                break;
            case TRUE:
                categoriaLexica = "True";
                break;
            case FALSE:
                categoriaLexica = "False";
                break;
            case NEW:
                categoriaLexica = "New";
                break;
            case SWITCH:
                categoriaLexica = "Switch";
                break;
            case CASE:
                categoriaLexica = "Case";
                break;
            case BREAK:
                categoriaLexica = "Break";
                break;
            case REQUIRE:
                categoriaLexica = "Require";
                break;
            case THIS:
                categoriaLexica = "For each";
                break;
            case ARRAY:
                categoriaLexica = "Array";
                break;
            case EXTENDS:
                categoriaLexica = "Extends";
                break;
            case FINALLY:
                categoriaLexica = "Finally";
                break;
            case SELF:
                categoriaLexica = "Self";
                break;
            case AS:
                categoriaLexica = "as";
                break;
            case DEFAULT:
                categoriaLexica = "Default";
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
