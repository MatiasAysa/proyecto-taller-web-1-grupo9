package com.tallerwebi.dominio.excepcion;

public class UsuarioInexistenteException extends Exception {

  private static final long serialVersionUID = 1L;

  public UsuarioInexistenteException(String mensaje) {
    super(mensaje);
  }
}
