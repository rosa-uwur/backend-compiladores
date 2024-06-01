package com.analizador.compiladores.demo.sintactico;

import com.analizador.compiladores.demo.lexico.ErrorLexico;
import com.analizador.compiladores.demo.lexico.TokenType;
import com.analizador.compiladores.demo.lexico.Tokenv2;
import org.apache.el.parser.Token;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.SQLOutput;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleParser {
    private Iterator<Tokenv2> tokens;
    private Tokenv2 currentToken;
    int linea = 0;

    public   List<ErrorLexico> tablaErrores = new ArrayList<>();

    public SimpleParser(List<Tokenv2> tokens) {
        this.tokens = tokens.iterator();
        advance(); // Load the first token
        parse();
    }

    private void advance() {

        if (tokens.hasNext()) {
            currentToken = tokens.next();
            linea = currentToken.linea;
        } else {
            currentToken = new Tokenv2(TokenType.ENDPROGRAM, null, linea);
        }
    }

    public void parse() {
        program();
    }

    private void program() {
        while (currentToken.type != TokenType.ENDPROGRAM) {
            System.out.println("Lexema: " + currentToken.lexema);
            statement();
            advance();
        }
        System.out.println("Parsing completed.");
    }

    private void statement() {
        advance();
        switch (currentToken.type) {
            case IDENTIFIER:
                assignment();
                break;
            case IF:
                ifStatement();
                break;
            case WHILE:
                whileStatement();
                break;
            case FUNCTION:
                functionDeclaration();
                break;
            case ARRAY:
                arrayDeclaration();
                break;
            case PRINTLN:
                printlnDeclaration();
                break;
            case FOREACH:
                foreachDeclararion();
                break;
            case ASSIGN:
                assignDeclaration();
                break;
            default:
                //throw new RuntimeException("Unexpected token: " + currentToken.type);
                tablaErrores.add(new ErrorLexico("Token no esperado " + currentToken.lexema + " (" + currentToken.type.toString() + ")", currentToken.linea,"Sintactico"));
                break;
        }
    }

    private void assignment() {
        match(TokenType.IDENTIFIER);

        advance();
        switch (currentToken.type){
            case ARRAY:
                match(TokenType.LEFT_PAREN);
                while(currentToken.type != TokenType.RIGHT_PAREN && (currentToken.type == TokenType.STRING || currentToken.type == TokenType.COMMA || currentToken.type == TokenType.INTEGER  ) ){
                    if (currentToken.type != TokenType.STRING && currentToken.type != TokenType.COMMA && currentToken.type != TokenType.INTEGER ){
                        tablaErrores.add(new ErrorLexico("Token inesperado " + currentToken.lexema + " se esperaba asignacion", currentToken.linea,"Sintactico"));
                    }
                    advance();
                }
                break;
            default:
                match(TokenType.ASSIGN);
                expression();
                break;
        }

        match(TokenType.END_LINE);
    }


    private void assignDeclaration(){
        advance();
        switch (currentToken.type){
            case ARRAY:
                match(TokenType.ARRAY);
                match(TokenType.LEFT_PAREN);
                while(currentToken.type != TokenType.RIGHT_PAREN && (currentToken.type == TokenType.STRING || currentToken.type == TokenType.COMMA || currentToken.type == TokenType.INTEGER  ) ){
                    if (currentToken.type != TokenType.STRING && currentToken.type != TokenType.COMMA && currentToken.type != TokenType.INTEGER ){
                        tablaErrores.add(new ErrorLexico("Token inesperado " + currentToken.lexema + " se esperaba asignacion", currentToken.linea,"Sintactico"));
                    }
                    advance();
                }
                match(TokenType.RIGHT_PAREN);
                break;
            default:
                expression();
                break;
        }
        match(TokenType.END_LINE);
    }


    private void printlnDeclaration() {
        match(TokenType.PRINTLN);
        statement();
        match(TokenType.END_LINE);
    }

    private void ifStatement() {
        match(TokenType.IF);
        match(TokenType.LEFT_PAREN);
        expression();
        match(TokenType.RIGHT_PAREN);
        match(TokenType.LEFT_BRACE);
        while (currentToken.type != TokenType.RIGHT_BRACE) {
            statement();
        }
        match(TokenType.RIGHT_BRACE);
        if (currentToken.type == TokenType.ELSE) {
            match(TokenType.ELSE);
            match(TokenType.LEFT_BRACE);
            while (currentToken.type != TokenType.RIGHT_BRACE) {
                statement();
            }
            match(TokenType.RIGHT_BRACE);
        }
    }

    private void arrayDeclaration() {
        match(TokenType.ARRAY);
        match(TokenType.LEFT_PAREN);
        expression();
        match(TokenType.RIGHT_PAREN);
        match(TokenType.END_LINE);
    }

    private void whileStatement() {
        match(TokenType.WHILE);
        match(TokenType.LEFT_PAREN);
        expression();
        match(TokenType.RIGHT_PAREN);
        match(TokenType.LEFT_BRACE);
        while (currentToken.type != TokenType.RIGHT_BRACE) {
            statement();
        }
        match(TokenType.RIGHT_BRACE);
    }

    private void foreachDeclararion() {

        match(TokenType.LEFT_PAREN);
        expression();
        match(TokenType.RIGHT_PAREN);
        match(TokenType.LEFT_BRACE);

        while (currentToken.type != TokenType.RIGHT_BRACE) {
            statement();
        }
        match(TokenType.RIGHT_BRACE);
    }

    private void functionDeclaration() {
        match(TokenType.FUNCTION);
        match(TokenType.IDENTIFIER);
        match(TokenType.LEFT_PAREN);
        if (currentToken.type == TokenType.IDENTIFIER) {
            parameters();
        }
        match(TokenType.RIGHT_PAREN);
        match(TokenType.LEFT_BRACE);
        while (currentToken.type != TokenType.RIGHT_BRACE) {
            statement();
        }
        match(TokenType.RIGHT_BRACE);
    }

    private void parameters() {
        match(TokenType.IDENTIFIER);
        while (currentToken.type == TokenType.COMMA) {
            match(TokenType.COMMA);
            match(TokenType.IDENTIFIER);
        }
    }

    private void expression() {
        term();
        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS || currentToken.type == TokenType.ARRAY || currentToken.type == TokenType.AS) {
            advance();
            term();
        }
    }

    private void term() {
        factor();
        while (currentToken.type == TokenType.MULTIPLY || currentToken.type == TokenType.DIVIDE) {
            advance();
            factor();
        }
    }

    private void factor() {
        switch (currentToken.type) {
            case IDENTIFIER:
            case INTEGER:
            case STRING:
            case ARRAY:
                advance();
                break;
            case LEFT_PAREN:
                match(TokenType.LEFT_PAREN);
                expression();
                match(TokenType.RIGHT_PAREN);
                break;
            default:
                //throw new RuntimeException("Unexpected token in factor: " + currentToken.type);
                match(TokenType.END_LINE);
                //tablaErrores.add(new ErrorLexico("Token no esperado en factor: " + currentToken.lexema, currentToken.linea));
        }
    }

    private void match(TokenType expected) {
        if (currentToken.type == expected) {
            advance();
        } else {
            tablaErrores.add(new ErrorLexico("Se esperaba "+ expected + " pero se encontro: " + currentToken.lexema + " (" + currentToken.type.toString() +")", currentToken.linea,"Sintactico"));
        }
    }

}
