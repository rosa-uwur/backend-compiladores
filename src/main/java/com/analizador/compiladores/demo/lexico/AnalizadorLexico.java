package com.analizador.compiladores.demo.lexico;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalizadorLexico {

    public Map<String, Integer> palabrasMap = new HashMap<>();
    public Map<String, String> identificadores = new HashMap<>();
    Map<String, String> palabrasReservadas = new HashMap<>();
    int posicion = 0;
    private void llenaPalabras(){
         String[] palabrasReservadasString = new String[]{"negocio", "vigilancia", "lugar","misiones" ,"control","¡gta", "gta", "chop", "trucos", "asaltos", "armas", "policia", "mismo", "michael", "lester", "trevor", "franklin", "encendido", "apagado", "santos", "emboscada", "lugar", "big", "andreas", "san", "trafico", "modo", "robo", "peligro", "buscar", "nivel", "negocio", "ilegal", "traficante", "vuelo", "avion", "vender"};


        for(String palabra : palabrasReservadasString) {
            palabrasMap.put(palabra, palabra.length());
        }

        palabrasReservadas.put("emboscada", "IF");
        palabrasReservadas.put("lugar", "ELSE");
        palabrasReservadas.put("andreas", "WHILE");
        palabrasReservadas.put("big", "FOR");
        palabrasReservadas.put("san andreas", "DO_WHILE");
        palabrasReservadas.put("..", "END_LINE");
        palabrasReservadas.put("chop", "PRINTLN");
        palabrasReservadas.put("Exception", "EXCEPTION");
        palabrasReservadas.put("negocio", "TRY");
        palabrasReservadas.put("ilegal", "CATCH");
        palabrasReservadas.put("/gta!", "END_PROGRAM");
        palabrasReservadas.put("¡gta", "START_PROGRAM");
        palabrasReservadas.put("vigilancia", "FOREACH");
        palabrasReservadas.put("misiones", "ARRAY");
        palabrasReservadas.put("trucos", "CLASS");
        palabrasReservadas.put("asaltos", "FUNCTION");
        palabrasReservadas.put("armas", "STATIC");
        palabrasReservadas.put("mismo", "CONTS");
        palabrasReservadas.put("michael", "NAMESPACE");
        palabrasReservadas.put("lester", "USE");
        palabrasReservadas.put("trevor", "INCLUDE");
        palabrasReservadas.put("franklin", "CONSTRUCT");
        palabrasReservadas.put("encendido", "TRUE");
        palabrasReservadas.put("apagado", "FALSE");
        palabrasReservadas.put("santos", "NEW");
        palabrasReservadas.put("trafico", "SWITCH");
        palabrasReservadas.put("modo", "CASE");
        palabrasReservadas.put("robo", "BREAK");
        palabrasReservadas.put("peligro", "REQUIRE");
        palabrasReservadas.put("buscar", "THIS");
        palabrasReservadas.put("nivel", "EXTENDS");
        palabrasReservadas.put("traficante", "FINALLY");
        palabrasReservadas.put("vuelo", "USE");
        palabrasReservadas.put("avion", "SELF");
        palabrasReservadas.put("vender", "DEFAULT");
        palabrasReservadas.put("misiones", "ARRAY");
        palabrasReservadas.put("as", "AS");

        // Imprimir el HashMap
        for (Map.Entry<String, Integer> entry : palabrasMap.entrySet()) {
            System.out.println("Palabra: " + entry.getKey() + ", Longitud: " + entry.getValue());
        }
    }

    public AnalizadorLexico(){
        llenaPalabras();
    }

    public   List<Tokenv2> tablaTokens = new ArrayList<>();
    int numLinea = 0;
    public   List<ErrorLexico> tablaErrores = new ArrayList<>();
    public void analizar(ArrayList<String> cadenas) {
        for (String code : cadenas) {
            analizar(code);
        }
    }

    public void analizar(String code) {
        numLinea++;
        analizarLinea(code, numLinea);
        imprimirTablas();
    }

    private void analizarLinea(String code, int numLinea) {
        posicion = 0;
        while (posicion < code.length()) {
            char caracterActual = code.charAt(posicion);
            if (Character.isWhitespace(caracterActual) || caracterActual == ' ' ) {
                posicion++;
            } else if (caracterActual == '/' && posicion + 1 < code.length() && code.charAt(posicion + 1) == '/') {
                break;
            } else if (caracterActual == '(') {
                tablaTokens.add(new Tokenv2(TokenType.LEFT_PAREN, caracterActual, numLinea));
                posicion = procesarExpresion(code, numLinea);
            }else if (caracterActual == '"' || caracterActual == '\'') {
                posicion = procesarString(code, numLinea);
            }else if (Character.isLetter(caracterActual) || caracterActual == '_' || caracterActual == '#' || caracterActual == '¡' || caracterActual == '/') {
                posicion = procesarIdentificador(code, numLinea);
            }else if (Character.isDigit(caracterActual)) {
                posicion = procesarNumerico(code, numLinea);
            }  else {
                //tablaTokens.add(new Tokenv2(TokenType.SPECIAL_CHAR, caracterActual, numLinea));
                if(!Character.isLetter(caracterActual)){
                    validaCaracter(code, caracterActual, numLinea);
                }

                //tablaErrores.add(new ErrorLexico("Caracter incorrecto: " + caracterActual, numLinea));
                posicion++;
            }
        }
    }

    private int procesarExpresion(String code, int numLinea) {
        int start = posicion;
        posicion++;
        while (posicion < code.length() && code.charAt(posicion) != ')') {
            if (Character.isWhitespace(code.charAt(posicion)) || code.charAt(posicion) == ' ') {
                posicion++;
            } else {
                //posicion++;
                //String identifier = code.substring(start, posicion);
                //tablaTokens.add(new Tokenv2(TokenType.STRING, identifier, numLinea));
                if (code.charAt(posicion) == '"'){
                    procesarString(code, numLinea);
                }else {
                    posicion = procesarIdentificador(code, numLinea);
                }


                posicion++;

            }
        }
        //tablaTokens.add(new Tokenv2(TokenType.RIGHT_PAREN, ")", numLinea));

        return posicion;
    }

    private int procesarIdentificador(String code, int numLinea) {
        int start = posicion;
        char caracterActual = code.charAt(posicion);
        while (posicion < code.length() && (Character.isLetterOrDigit(code.charAt(posicion)) || code.charAt(posicion) == '_' || code.charAt(posicion) == '#' || caracterActual == '¡' || caracterActual == '/' || caracterActual == '.'   )) {
            posicion++;
        }

        String identifier = code.substring(start, posicion);
        System.out.println("identifier" + identifier );
        System.out.println(palabrasReservadas.containsKey(identifier));
        if (palabrasReservadas.containsKey(identifier)) {
            String tokenType = palabrasReservadas.get(identifier);
            for (TokenType type : TokenType.values()) {
                System.out.println("aaa " + type.name() + " = " +tokenType );
                if (type.name().equalsIgnoreCase(tokenType)) {
                    System.out.println("type: "+ type);
                    tablaTokens.add(new Tokenv2(type, identifier, numLinea));
                    return posicion;
                }
            }
        }else{
            if (identifier.matches("^#[a-zA-Z]+$")) {
                tablaTokens.add(new Tokenv2(TokenType.IDENTIFIER, identifier, numLinea));
                identificadores.put(identifier, TokenType.IDENTIFIER.toString());
            } else {
                System.out.println("error: " + identifier);
                if (identifier.matches("^[a-zA-Z_][a-zA-Z0-9_]*\\\\(.*\\\\)$")) {
                    tablaTokens.add(new Tokenv2(TokenType.FUNCTION, identifier, numLinea));
                    identificadores.put(identifier, TokenType.IDENTIFIER.toString());
                } else {

                    if(!Character.isLetter(caracterActual) && !Character.isDigit(caracterActual)){
                        validaCaracter(code, caracterActual, numLinea);
                    }else{
                        if (!Character.isDigit(caracterActual)){
                            tablaErrores.add(new ErrorLexico("Identificador incorrecto: " + identifier, numLinea, "Lexico"));
                        }

                    }

                }
            }
        }

        return posicion;
    }

    private int procesarNumerico(String code, int numLinea) {
        int start = posicion;
        while (posicion < code.length() && Character.isDigit(code.charAt(posicion))) {
            posicion++;
        }
        String numericLiteral = code.substring(start, posicion);
        int value = Integer.parseInt(numericLiteral);
        if (value >= 0 && value <= 9) {
            tablaTokens.add(new Tokenv2(TokenType.DIGIT, value, numLinea));
        } else {
            tablaTokens.add(new Tokenv2(TokenType.INTEGER, value, numLinea));
        }
        return posicion;
    }

    private int procesarString(String code, int numLinea) {
        System.out.println("code -> " + code + " line " + numLinea);
        int start = posicion;
        String lit = "";
        posicion++;
        while (posicion < code.length() && code.charAt(posicion) != '"') {
            System.out.println("char: "+ code.charAt(posicion) );
            if (code.charAt(posicion) == '\\') {
                posicion++;
                posicion++;
            }else{
                lit += code.charAt(posicion);
            }


            posicion++;


        }

        if(posicion < code.length() && lit.length() >0){
            if(code.charAt(posicion) == '"'){
                String literal = code.substring(start + 1, posicion);
                tablaTokens.add(new Tokenv2(TokenType.STRING, literal, numLinea));
            }else{
                tablaErrores.add(new ErrorLexico("String no encerrada en comillas", numLinea, "Lexico"));
            }
        }

        return posicion;
    }

    private void imprimirTablas() {
        System.out.println("Tabla de tablaTokens:");
        for (Tokenv2 token : tablaTokens) {
            System.out.println(token);
        }
        System.out.println("\nTabla de errores:");
        for (ErrorLexico error : tablaErrores) {
            System.out.println(error);
        }
    }


    private void validaCaracter(String code, char caracterActual, int numLinea){
        switch (caracterActual) {
            case '+':
                tablaTokens.add(new Tokenv2(TokenType.PLUS, caracterActual, numLinea));
                break;
            case '-':
                tablaTokens.add(new Tokenv2(TokenType.MINUS, caracterActual, numLinea));
                break;
            case '*':
                tablaTokens.add(new Tokenv2(TokenType.MULTIPLY, caracterActual, numLinea));
                break;
            case '/':
                tablaTokens.add(new Tokenv2(TokenType.DIVIDE, caracterActual, numLinea));
                break;
            case '=':
                if (posicion + 1 < code.length() && code.charAt(posicion + 1) == '=') {
                    tablaTokens.add(new Tokenv2(TokenType.EQUAL_TO, caracterActual, numLinea));
                    posicion += 2;
                } else {
                    tablaTokens.add(new Tokenv2(TokenType.ASSIGN, caracterActual, numLinea));
                    posicion++;
                }
                break;
            case '!':
                if (posicion + 1 < code.length() && code.charAt(posicion + 1) == '=') {
                    tablaTokens.add(new Tokenv2(TokenType.NOT_EQUAL_TO, caracterActual, numLinea));
                    posicion += 2;
                } else {
                    tablaTokens.add(new Tokenv2(TokenType.ENDPROGRAM, caracterActual, numLinea));
                    posicion += 2;
                }
                break;
            case '<':
                if (posicion + 1 < code.length() && code.charAt(posicion + 1) == '=') {
                    tablaTokens.add(new Tokenv2(TokenType.LESS_THAN_OR_EQUAL_TO, caracterActual, numLinea));
                    posicion += 2;
                } else {
                    tablaTokens.add(new Tokenv2(TokenType.LESS_THAN, caracterActual, numLinea));
                    posicion++;
                }
                break;
            case '>':
                if (posicion + 1 < code.length() && code.charAt(posicion + 1) == '=') {
                    tablaTokens.add(new Tokenv2(TokenType.GREATER_THAN_OR_EQUAL_TO, caracterActual, numLinea));
                    posicion += 2;
                } else {
                    tablaTokens.add(new Tokenv2(TokenType.GREATER_THAN, caracterActual, numLinea));
                    posicion++;
                }
                break;
            case '(':
                tablaTokens.add(new Tokenv2(TokenType.LEFT_PAREN, caracterActual, numLinea));
                break;
            case ')':
                tablaTokens.add(new Tokenv2(TokenType.RIGHT_PAREN, caracterActual, numLinea));
                break;
            case '{':
                tablaTokens.add(new Tokenv2(TokenType.LEFT_BRACE, caracterActual, numLinea));
                break;
            case '}':
                tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, caracterActual, numLinea));
                break;
            case ',':
                tablaTokens.add(new Tokenv2(TokenType.COMMA, caracterActual, numLinea));
                break;
            case ';':
                tablaTokens.add(new Tokenv2(TokenType.SEMICOLON, caracterActual, numLinea));
                break;
            case '#':
                tablaTokens.add(new Tokenv2(TokenType.IDENTIFIER, caracterActual, numLinea));
                break;
            case '.':
                if (code.matches("^[\\w\\W]*..$")){
                    tablaTokens.add(new Tokenv2(TokenType.END_LINE, "..", numLinea));
                    posicion++;
                }else{
                    tablaErrores.add(new ErrorLexico("Caracter no esperado: " + caracterActual, numLinea, "Lexico"));
                }

                break;
            case '[':
                tablaTokens.add(new Tokenv2(TokenType.LEFT_BRACE, caracterActual, numLinea));
                break;
            case ']':
                tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, caracterActual, numLinea));
                break;
            case '%':
                tablaTokens.add(new Tokenv2(TokenType.RIGHT_BRACE, caracterActual, numLinea));
                break;
            case '¡':
                tablaTokens.add(new Tokenv2(TokenType.ASSIGN, caracterActual, numLinea));
                break;
            case '"':
            case ':':
                break;
            default:
                tablaErrores.add(new ErrorLexico("Caracter no valido: " + caracterActual, numLinea, "Lexico"));
                break;
        }
    }






}