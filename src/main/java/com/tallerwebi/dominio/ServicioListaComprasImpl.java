package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioListaComprasImpl implements ServicioListaCompras {

  @Override
  public List<ItemCompra> generarListaCompras(List<Comida> Comidas) {
    List<ItemCompra> listaFinal = new ArrayList<>();

    for (Comida comida : Comidas) {
      for (ItemComida itemComida : comida.getItems()) {
        ItemCompra itemExistente = buscarItemCompra(listaFinal, itemComida.getAlimento());

        if (itemExistente != null) {
          Double nuevaCantidad = itemExistente.getCantidadTotal() + itemComida.getCantidadGramos();
          itemExistente.setCantidadTotal(nuevaCantidad);
        } else {
          ItemCompra nuevoItem = new ItemCompra();
          nuevoItem.setAlimento(itemComida.getAlimento());
          nuevoItem.setCantidadTotal(itemComida.getCantidadGramos());

          listaFinal.add(nuevoItem);
        }
      }
    }
    return listaFinal;
  }

  @Override
  public ItemCompra buscarItemCompra(List<ItemCompra> itemsCompra, Alimento alimento) {
    for (ItemCompra itemCompra : itemsCompra) {
      if (itemCompra.getAlimento().getNombre().equals(alimento.getNombre())) {
        return itemCompra;
      }
    }
    return null;
  }
}
