package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public class ListaCompra {

  private List<ItemCompra> lista = new ArrayList<ItemCompra>();

  public List<ItemCompra> getLista() {
    return lista;
  }

  public void setLista(List<ItemCompra> lista) {
    this.lista = lista;
  }
  /*
  List<ItemCompra> lista =
          servicioListaCompras.generarLista(plan.getComidas());
          +
 */
}
