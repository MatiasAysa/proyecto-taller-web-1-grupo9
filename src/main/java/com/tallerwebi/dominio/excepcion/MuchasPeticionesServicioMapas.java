package com.tallerwebi.dominio.excepcion;

public class MuchasPeticionesServicioMapas extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public MuchasPeticionesServicioMapas(String message, Throwable causa) {
    super(message, causa);
  }
}
