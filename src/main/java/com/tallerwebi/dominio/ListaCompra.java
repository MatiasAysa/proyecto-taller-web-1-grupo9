package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class ListaCompra {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(cascade = CascadeType.ALL)
  private List<ItemCompra> lista = new ArrayList<ItemCompra>();

  public List<ItemCompra> getLista() {
    return lista;
  }

  public void setLista(List<ItemCompra> lista) {
    this.lista = lista;
  }
}
