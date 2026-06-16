package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioListaCompras {
  List<ItemCompra> generarListaCompras(List<Comida> Comidas);
  ItemCompra buscarItemCompra(List<ItemCompra> itemsCompra, Ingrediente ingrediente);
  void calcularPrecios(List<ItemCompra> listaCompra);
  Double calcularTotalListaCompras(List<ItemCompra> listaCompra);
}
