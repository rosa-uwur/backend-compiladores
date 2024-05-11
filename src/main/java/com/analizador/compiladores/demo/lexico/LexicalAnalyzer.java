package com.analizador.compiladores.demo.lexico;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexicalAnalyzer {

    private Map<String, Integer> palabrasMap = new HashMap<>();
    private void llenaPalabras(){
         String[] palabrasReservadas = new String[]{"¡gta", "gta", "chop", "trucos", "asaltos", "armas", "policia", "mismo", "michael", "lester", "trevor", "franklin", "encendido", "apagado", "santos", "emboscada", "lugar", "big", "andreas", "san", "trafico", "modo", "robo", "peligro", "buscar", "nivel", "negocio", "ilegal", "traficante", "vuelo", "avion", "vender"};


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
                tablaTokens.add(new Tokenv2(TokenType.IDENTIFIER, identifier));
            } else if (Character.isDigit(currentChar)) {
                int start = position;
                while (position < code.length() && Character.isDigit(code.charAt(position))) {
                    position++;
                }
                int value = Integer.parseInt(code.substring(start, position));
                tablaTokens.add(new Tokenv2(TokenType.INTEGER, value));
            } else if (currentChar == '"') {
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
            } else {
                switch (currentChar) {
                    case '+':
                        tablaTokens.add(new Tokenv2(TokenType.PLUS, null));
                        position++;
                        break;
                    case '-':
                        tablaTokens.add(new Tokenv2(TokenType.MINUS, null));
                        position++;
                        break;
                    case '*':
                        tablaTokens.add(new Tokenv2(TokenType.MULTIPLY, null));
                        position++;
                        break;
                    case '/':
                        tablaTokens.add(new Tokenv2(TokenType.DIVIDE, null));
                        position++;
                        break;
                    case '=':
                        if (position + 1 < code.length() && code.charAt(position + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.EQUAL_TO, null));
                            position += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.ASSIGN, null));
                            position++;
                        }
                        break;
                    case '!':
                        if (position + 1 < code.length() && code.charAt(position + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.NOT_EQUAL_TO, null));
                            position += 2;
                        } else {
                            tablaErrores.add(new LexicalError("Caracteres no validos: !", position));
                            position++;
                        }
                        break;
                    case '<':
                        if (position + 1 < code.length() && code.charAt(position + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.LESS_THAN_OR_EQUAL_TO, null));
                            position += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.LESS_THAN, null));
                            position++;
                        }
                        break;
                    case '>':
                        if (position + 1 < code.length() && code.charAt(position + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.GREATER_THAN_OR_EQUAL_TO, null));
                            position += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.GREATER_THAN, null));
                            position++;
                        }
                        break;
                    case '(':
                        tablaTokens.add(new Tokenv2(TokenType.LEFT_PAREN, null));
                        position++;
                        break;
                    case ')':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_PAREN, null));
                        position++;
                        break;
                    case '{':
                        tablaTokens.add(new Tokenv2(TokenType.LEFT_BRACE, null));
                        position++;
                        break;
                    case '}':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, null));
                        position++;
                        break;
                    case ',':
                        tablaTokens.add(new Tokenv2(TokenType.COMMA, null));
                        position++;
                        break;
                    case ';':
                        tablaTokens.add(new Tokenv2(TokenType.SEMICOLON, null));
                        position++;
                        break;
                    case '#':
                        tablaTokens.add(new Tokenv2(TokenType.IDENTIFIER, null));
                        position++;
                        break;
                    case '.':
                        tablaTokens.add(new Tokenv2(TokenType.SEMICOLON, null));
                        position++;
                        break;
                    case '[':
                        tablaTokens.add(new Tokenv2(TokenType.LEFT_BRACE, null));
                        position++;
                        break;
                    case ']':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, null));
                        position++;
                        break;
                    case '%':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, null));
                        position++;
                        break;
                    case '¡':
                        tablaTokens.add(new Tokenv2(TokenType.ASSIGN, null));
                        position++;
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