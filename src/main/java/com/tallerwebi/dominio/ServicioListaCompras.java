package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioListaCompras {
  List<ItemCompra> generarListaCompras(List<Comida> Comidas);
  ItemCompra buscarItemCompra(List<ItemCompra> itemsCompra, ItemComida itemComida);
  void calcularPrecios(List<ItemCompra> listaCompra);
  Double calcularTotalListaCompras(List<ItemCompra> listaCompra);
  Alimento buscarAlimentoPorId(Long id);
}
