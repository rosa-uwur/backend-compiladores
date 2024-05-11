package com.analizador.compiladores.demo.lexico;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexicalAnalyzer {

    private Map<String, Integer> palabrasMap = new HashMap<>();
    private void llenaPalabras(){
         String[] palabrasReservadas = new String[]{"negocio", "vigilancia", "lugar","misiones" ,"control","¡gta", "gta", "chop", "trucos", "asaltos", "armas", "policia", "mismo", "michael", "lester", "trevor", "franklin", "encendido", "apagado", "santos", "emboscada", "lugar", "big", "andreas", "san", "trafico", "modo", "robo", "peligro", "buscar", "nivel", "negocio", "ilegal", "traficante", "vuelo", "avion", "vender"};


        for(String palabra : palabrasReservadas) {
            palabrasMap.put(palabra, palabra.length());
        }

        // Imprimir el HashMap
        for (Map.Entry<String, Integer> entry : palabrasMap.entrySet()) {
            System.out.println("Palabra: " + entry.getKey() + ", Longitud: " + entry.getValue());
        }
    }

    public LexicalAnalyzer(){
        llenaPalabras();
    }

    public   List<Tokenv2> tablaTokens = new ArrayList<>();
    public   List<LexicalError> tablaErrores = new ArrayList<>();
    public  void analyze(ArrayList<String> cadenas) {
        for (String code : cadenas) {
            analyze(code);
            // Imprimir la tabla de tablaTokens para cada cadena
            System.out.println("Tabla de tablaTokens para la cadena:");
            for (Tokenv2 Tokenv2 : tablaTokens) {
                System.out.println(Tokenv2);
            }

            // Imprimir la tabla de errores para cada cadena
            System.out.println("\nTabla de errores para la cadena:");
            for (LexicalError error : tablaErrores) {
                System.out.println(error);
            }
        }
    }


    public void analyze(String code) {
        int position = 0;
        while (position < code.length()) {
            char currentChar = code.charAt(position);

            if (Character.isWhitespace(currentChar) || currentChar == ' ') {
                position++;
            } else if (Character.isLetter(currentChar) || currentChar == '_') {
                int start = position;
                while (position < code.length() && (Character.isLetterOrDigit(code.charAt(position)) || code.charAt(position) == '_')) {
                    position++;
                }
                String identifier = code.substring(start, position);

                // Verificar si el identificador es una palabra reservada mal escrita
                // Validar que el identificador comience con '#' seguido de letras
                if (identifier.matches("^#[a-zA-Z]+$")) {
                    tablaTokens.add(new Tokenv2(TokenType.IDENTIFIER, identifier));
                } else if (palabrasMap.containsKey(identifier)) {
                    tablaTokens.add(new Tokenv2(TokenType.RESERVED_WORD, identifier));
                }else if (identifier.matches("^#[a-zA-Z]+$")) {
                    tablaTokens.add(new Tokenv2(TokenType.IDENTIFIER, identifier));
                }


            }else if (Character.isDigit(currentChar)) {
                int start = position;
                while (position < code.length() && Character.isDigit(code.charAt(position))) {
                    position++;
                }
                String numericLiteral = code.substring(start, position);
                int value = Integer.parseInt(numericLiteral);
                if (value >= 0 && value <= 9) {
                    tablaTokens.add(new Tokenv2(TokenType.DIGIT, value));
                } else {
                    tablaTokens.add(new Tokenv2(TokenType.INTEGER, value));
                }
            }else if (currentChar == '"') {
                int start = position;
                position++;
                while (position < code.length() && code.charAt(position) != '"') {
                    if (code.charAt(position) == '\\') {
                        position++;
                    }
                    position++;
                }
                if (position == code.length()) {
                    tablaErrores.add(new LexicalError("String no encerrada en comillas", start));
                } else {
                    String literal = code.substring(start + 1, position);
                    tablaTokens.add(new Tokenv2(TokenType.STRING, literal));
                    position++;
                }
            }
            else {
                switch (currentChar) {
                    case '+':
                        tablaTokens.add(new Tokenv2(TokenType.PLUS, currentChar));
                        position++;
                        break;
                    case '-':
                        tablaTokens.add(new Tokenv2(TokenType.MINUS, currentChar));
                        position++;
                        break;
                    case '*':
                        tablaTokens.add(new Tokenv2(TokenType.MULTIPLY, currentChar));
                        position++;
                        break;
                    case '/':
                        tablaTokens.add(new Tokenv2(TokenType.DIVIDE, currentChar));
                        position++;
                        break;
                    case '=':
                        if (position + 1 < code.length() && code.charAt(position + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.EQUAL_TO, currentChar));
                            position += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.ASSIGN, currentChar));
                            position++;
                        }
                        break;
                    case '!':
                        if (position + 1 < code.length() && code.charAt(position + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.NOT_EQUAL_TO, currentChar));
                            position += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.ENDPROGRAM, currentChar));
                            position += 2;
                        }
                        break;
                    case '<':
                        if (position + 1 < code.length() && code.charAt(position + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.LESS_THAN_OR_EQUAL_TO, currentChar));
                            position += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.LESS_THAN, currentChar));
                            position++;
                        }
                        break;
                    case '>':
                        if (position + 1 < code.length() && code.charAt(position + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.GREATER_THAN_OR_EQUAL_TO, currentChar));
                            position += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.GREATER_THAN, currentChar));
                            position++;
                        }
                        break;
                    case '(':
                        tablaTokens.add(new Tokenv2(TokenType.LEFT_PAREN, currentChar));
                        position++;
                        break;
                    case ')':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_PAREN, currentChar));
                        position++;
                        break;
                    case '{':
                        tablaTokens.add(new Tokenv2(TokenType.LEFT_BRACE, currentChar));
                        position++;
                        break;
                    case '}':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, currentChar));
                        position++;
                        break;
                    case ',':
                        tablaTokens.add(new Tokenv2(TokenType.COMMA, currentChar));
                        position++;
                        break;
                    case ';':
                        tablaTokens.add(new Tokenv2(TokenType.SEMICOLON, currentChar));
                        position++;
                        break;
                    case '#':
                        tablaTokens.add(new Tokenv2(TokenType.IDENTIFIER, currentChar));
                        position++;
                        break;
                    case '.':
                        tablaTokens.add(new Tokenv2(TokenType.SEMICOLON, currentChar));
                        position++;
                        break;
                    case '[':
                        tablaTokens.add(new Tokenv2(TokenType.LEFT_BRACE, currentChar));
                        position++;
                        break;
                    case ']':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, currentChar));
                        position++;
                        break;
                    case '%':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, currentChar));
                        position++;
                        break;
                    case '¡':
                        tablaTokens.add(new Tokenv2(TokenType.ASSIGN, currentChar));
                        position++;
                        break;
                    case '"':
                    case ':':
                        break;
                    default:
                        tablaErrores.add(new LexicalError("Caracter no valido: " + currentChar, position));
                        position++;
                        break;
                }
            }
        }

        // Imprimir la tabla de tablaTokens
        System.out.println("Tabla de tablaTokens:");
        for (Tokenv2 Tokenv2 : tablaTokens) {
            System.out.println(Tokenv2);
        }

        // Imprimir la tabla de errores
        System.out.println("\nTabla de errores:");
        for (LexicalError error : tablaErrores) {
            System.out.println(error);
        }
    }








}