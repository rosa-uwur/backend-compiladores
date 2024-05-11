package com.analizador.compiladores.demo.lexico;

public  class LexicalError {
    private final String message;
    private final int position;

    public String mensaje;
    public int linea;

    public LexicalError(String message, int position) {
        this.message = message;
        this.position = position;
        this.mensaje = message;
        this.linea = position;
    }

    @Override
    public String toString() {
        return "LexicalError{" +
                "message='" + message + '\'' +
                ", position=" + position +
                '}';
    }
}