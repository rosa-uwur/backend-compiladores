package com.analizador.compiladores.demo.lexico;

/**
 * Clase para los errores léxicos
 */
public class ErrorLexico {
    private  String mensaje = new String();
    private  int linea;
    private  String token = new String();

    /**
     * Instanciar un error léxico
     * @param mensaje Motivo del error
     * @param linea Línea en el programa donde se generó el error
     */
    public ErrorLexico(String mensaje, int linea, String token) {
        if (mensaje!=null) {
            this.mensaje = mensaje;
        }
        this.linea = linea;
        if (token!=null){
            this.token = token;
        }
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getLinea() {
        return linea;
    }

    public String getToken() {
        return token;
    }

    /**
     * @return Error léxico en formato de texto
     */
    @Override
    public String toString() { return "Error lexico en la línea " + linea + ", " + mensaje; }
}
