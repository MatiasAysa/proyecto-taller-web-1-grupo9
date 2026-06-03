package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioListaCompras {
  List<ItemCompra> generarListaCompras(List<Comida> Comidas);
  ItemCompra buscarItemCompra(List<ItemCompra> itemsCompra, Alimento alimento);
}
