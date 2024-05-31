package com.analizador.compiladores.demo.lexico;

public enum TokenType {
    IF, ELSE, WHILE, FOR, FUNCTION, RETURN, BOOLEAN, NULL,
    IDENTIFIER, INTEGER, STRING,
    PLUS, MINUS, MULTIPLY, DIVIDE,
    ASSIGN, EQUAL_TO, NOT_EQUAL_TO, LESS_THAN, LESS_THAN_OR_EQUAL_TO,
    GREATER_THAN, GREATER_THAN_OR_EQUAL_TO,
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, SEMICOLON, ENDPROGRAM, RESERVED_WORD, DIGIT, DO_WHILE, END_LINE, PRINTLN, EXCEPTION, TRY, CATCH, FOREACH
}