package com.analizador.compiladores.demo.lexico;

public  class ErrorLexico {
    private final String message;
    private final int position;
    public String tipoError;
    public String mensaje;
    public int linea;

    public ErrorLexico(String message, int position, String tipoError) {
        this.message = message;
        this.position = position;
        this.mensaje = message;
        this.linea = position;
        this.tipoError = tipoError;
    }

    @Override
    public String toString() {
        return "LexicalError{" +
                "message='" + message + '\'' +
                ", position=" + position +
                '}';
    }
}