package com.tallerwebi.dominio.excepcion;

import java.util.List;

public class NombreDeAlimentoInexistenteException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private List<Integer> alimentosInexistentes;

  public NombreDeAlimentoInexistenteException(List<Integer> alimentosInexistentes) {
    this.alimentosInexistentes = alimentosInexistentes;
  }

  public List<Integer> getAlimentosInexistentes() {
    return alimentosInexistentes;
  }
}
