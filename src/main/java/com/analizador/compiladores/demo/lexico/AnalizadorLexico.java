package com.analizador.compiladores.demo.lexico;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalizadorLexico {

    public Map<String, Integer> palabrasMap = new HashMap<>();

    Map<String, String> palabrasReservadas = new HashMap<>();
    private void llenaPalabras(){
         String[] palabrasReservadasString = new String[]{"negocio", "vigilancia", "lugar","misiones" ,"control","¡gta", "gta", "chop", "trucos", "asaltos", "armas", "policia", "mismo", "michael", "lester", "trevor", "franklin", "encendido", "apagado", "santos", "emboscada", "lugar", "big", "andreas", "san", "trafico", "modo", "robo", "peligro", "buscar", "nivel", "negocio", "ilegal", "traficante", "vuelo", "avion", "vender"};


        for(String palabra : palabrasReservadasString) {
            palabrasMap.put(palabra, palabra.length());
        }

        palabrasReservadas.put("emboscada", "IF");
        palabrasReservadas.put("lugar", "ELSE");
        palabrasReservadas.put("andreas", "WHILE");
        palabrasReservadas.put("big", "FOR");

        // Imprimir el HashMap
        for (Map.Entry<String, Integer> entry : palabrasMap.entrySet()) {
            System.out.println("Palabra: " + entry.getKey() + ", Longitud: " + entry.getValue());
        }
    }

    public AnalizadorLexico(){
        llenaPalabras();
    }

    public   List<Tokenv2> tablaTokens = new ArrayList<>();
    public   List<ErrorLexico> tablaErrores = new ArrayList<>();
    public  void analizar(ArrayList<String> cadenas) {
        int numlinea = 0;
        for (String code : cadenas) {
            numlinea++;
            analizar(code, numlinea);
            // Imprimir la tabla de tablaTokens para cada cadena
            System.out.println("Tabla de tablaTokens para la cadena:");
            for (Tokenv2 Tokenv2 : tablaTokens) {
                System.out.println(Tokenv2);
            }

            // Imprimir la tabla de errores para cada cadena
            System.out.println("\nTabla de errores para la cadena:");
            for (ErrorLexico error : tablaErrores) {
                System.out.println(error);
            }
        }
    }


    public void analizar(String code,int  l) {
        int posicion = 0;
        while (posicion < code.length()) {
            char caracterActual = code.charAt(posicion);

            if (Character.isWhitespace(caracterActual) || caracterActual == ' ') {
                posicion++;
            } else if (Character.isLetter(caracterActual) || caracterActual == '_') {
                int start = posicion;
                while (posicion < code.length() && (Character.isLetterOrDigit(code.charAt(posicion)) || code.charAt(posicion) == '_')) {
                    posicion++;
                }
                String identifier = code.substring(start, posicion);

                // Verificar si el identificador es una palabra reservada mal escrita
                if (palabrasReservadas.containsKey(identifier)) {
                    String tokenType = palabrasReservadas.get(identifier);
                    // Buscar el token en el enum TokenType
                    for (TokenType type : TokenType.values()) {
                        if (type.name().equalsIgnoreCase(tokenType)) {
                            tablaTokens.add(new Tokenv2(type, identifier, l));
                            return; // Salir del método después de agregar el token
                        }
                    }
                    // Si no se encontró en el enum, agregar como palabra reservada
                    tablaTokens.add(new Tokenv2(TokenType.RESERVED_WORD, identifier, l));
                }
// Validar que el identificador comience con '#' seguido de letras
                else if (identifier.matches("^#[a-zA-Z]+$")) {
                    tablaTokens.add(new Tokenv2(TokenType.IDENTIFIER, identifier, l));
                }// Mostrar un mensaje de error si el identificador no cumple con el formato esperado
                else {
                    tablaErrores.add(new ErrorLexico("Identificador incorrecto: " + identifier, l));
                }


            }else if (Character.isDigit(caracterActual)) {
                int start = posicion;
                while (posicion < code.length() && Character.isDigit(code.charAt(posicion))) {
                    posicion++;
                }
                String numericLiteral = code.substring(start, posicion);
                int value = Integer.parseInt(numericLiteral);
                if (value >= 0 && value <= 9) {
                    tablaTokens.add(new Tokenv2(TokenType.DIGIT, value, l));
                } else {
                    tablaTokens.add(new Tokenv2(TokenType.INTEGER, value, l));
                }
            }else if (caracterActual == '"') {
                int start = posicion;
                posicion++;
                while (posicion < code.length() && code.charAt(posicion) != '"') {
                    if (code.charAt(posicion) == '\\') {
                        posicion++;
                    }
                    posicion++;
                }
                if (posicion == code.length()) {
                    tablaErrores.add(new ErrorLexico("String no encerrada en comillas", l));
                } else {
                    String literal = code.substring(start + 1, posicion);
                    tablaTokens.add(new Tokenv2(TokenType.STRING, literal, l));
                    posicion++;
                }

            }
            else {
                switch (caracterActual) {
                    case '+':
                        tablaTokens.add(new Tokenv2(TokenType.PLUS, caracterActual, l));
                        posicion++;
                        break;
                    case '-':
                        tablaTokens.add(new Tokenv2(TokenType.MINUS, caracterActual, l));
                        posicion++;
                        break;
                    case '*':
                        tablaTokens.add(new Tokenv2(TokenType.MULTIPLY, caracterActual, l));
                        posicion++;
                        break;
                    case '/':
                        tablaTokens.add(new Tokenv2(TokenType.DIVIDE, caracterActual, l));
                        posicion++;
                        break;
                    case '=':
                        if (posicion + 1 < code.length() && code.charAt(posicion + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.EQUAL_TO, caracterActual, l));
                            posicion += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.ASSIGN, caracterActual, l));
                            posicion++;
                        }
                        break;
                    case '!':
                        if (posicion + 1 < code.length() && code.charAt(posicion + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.NOT_EQUAL_TO, caracterActual, l));
                            posicion += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.ENDPROGRAM, caracterActual, l));
                            posicion += 2;
                        }
                        break;
                    case '<':
                        if (posicion + 1 < code.length() && code.charAt(posicion + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.LESS_THAN_OR_EQUAL_TO, caracterActual, l));
                            posicion += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.LESS_THAN, caracterActual, l));
                            posicion++;
                        }
                        break;
                    case '>':
                        if (posicion + 1 < code.length() && code.charAt(posicion + 1) == '=') {
                            tablaTokens.add(new Tokenv2(TokenType.GREATER_THAN_OR_EQUAL_TO, caracterActual, l));
                            posicion += 2;
                        } else {
                            tablaTokens.add(new Tokenv2(TokenType.GREATER_THAN, caracterActual, l));
                            posicion++;
                        }
                        break;
                    case '(':
                        tablaTokens.add(new Tokenv2(TokenType.LEFT_PAREN, caracterActual, l));
                        posicion++;
                        break;
                    case ')':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_PAREN, caracterActual, l));
                        posicion++;
                        break;
                    case '{':
                        tablaTokens.add(new Tokenv2(TokenType.LEFT_BRACE, caracterActual, l));
                        posicion++;
                        break;
                    case '}':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, caracterActual, l));
                        posicion++;
                        break;
                    case ',':
                        tablaTokens.add(new Tokenv2(TokenType.COMMA, caracterActual, l));
                        posicion++;
                        break;
                    case ';':
                        tablaTokens.add(new Tokenv2(TokenType.SEMICOLON, caracterActual, l));
                        posicion++;
                        break;
                    case '#':
                        tablaTokens.add(new Tokenv2(TokenType.IDENTIFIER, caracterActual, l));
                        posicion++;
                        break;
                    case '.':
                        tablaTokens.add(new Tokenv2(TokenType.SEMICOLON, caracterActual, l));
                        posicion++;
                        break;
                    case '[':
                        tablaTokens.add(new Tokenv2(TokenType.LEFT_BRACE, caracterActual, l));
                        posicion++;
                        break;
                    case ']':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, caracterActual, l));
                        posicion++;
                        break;
                    case '%':
                        tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, caracterActual, l));
                        posicion++;
                        break;
                    case '¡':
                        tablaTokens.add(new Tokenv2(TokenType.ASSIGN, caracterActual, l));
                        posicion++;
                        break;
                    case '"':
                    case ':':
                        break;
                    default:
                        tablaErrores.add(new ErrorLexico("Caracter no valido: " + caracterActual, posicion));
                        posicion++;
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
        for (ErrorLexico error : tablaErrores) {
            System.out.println(error);
        }
    }








}