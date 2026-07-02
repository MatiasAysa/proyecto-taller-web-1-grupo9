package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public class DiaListaComprasDTO {

  private Integer numeroDia;
  private List<Comida> comidas = new ArrayList<>();

  public Integer getNumeroDia() {
    return numeroDia;
  }

  public void setNumeroDia(Integer numeroDia) {
    this.numeroDia = numeroDia;
  }

  public List<Comida> getComidas() {
    return comidas;
  }

  public void setComidas(List<Comida> comidas) {
    this.comidas = comidas;
  }
}
