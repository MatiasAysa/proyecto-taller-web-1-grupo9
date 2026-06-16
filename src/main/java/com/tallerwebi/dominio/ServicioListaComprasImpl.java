package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioListaComprasImpl implements ServicioListaCompras {

  private final double CANTIDAD_KILO_A_GRAMOS = 1000.0;

  @Override
  public List<ItemCompra> generarListaCompras(List<Comida> Comidas) {
    List<ItemCompra> listaFinalCompra = new ArrayList<>();

    for (Comida comida : Comidas) {
      for (Ingrediente ingrediente : comida.getItems()) {
        ItemCompra itemExistente = buscarItemCompra(listaFinalCompra, ingrediente);

        if (itemExistente != null) {
          Double nuevaCantidad = itemExistente.getCantidadTotal() + ingrediente.getCantidadGramos();
          itemExistente.setCantidadTotal(nuevaCantidad);
        } else {
          ItemCompra nuevoItem = new ItemCompra();
          nuevoItem.setAlimento(ingrediente.getAlimento());
          nuevoItem.setCantidadTotal(ingrediente.getCantidadGramos());

          listaFinalCompra.add(nuevoItem);
        }
      }
    }
    return listaFinalCompra;
  }

  @Override
  public ItemCompra buscarItemCompra(List<ItemCompra> itemsCompra, Ingrediente ingrediente) {
    for (ItemCompra itemCompra : itemsCompra) {
      if (itemCompra.getAlimento().getNombre().equals(ingrediente.getAlimento().getNombre())) {
        return itemCompra;
      }
    }
    return null;
  }

  @Override
  public void calcularPrecios(List<ItemCompra> listaCompra) {
    for (ItemCompra item : listaCompra) {
      Double precioPorKilo = item.getAlimento().getPrecioEstimado();
      Double precioTotal = (item.getCantidadTotal() / CANTIDAD_KILO_A_GRAMOS) * precioPorKilo;
      item.setPrecoTotal(precioTotal);
    }
  }

  @Override
  public Double calcularTotalListaCompras(List<ItemCompra> listaCompra) {
    Double total = 0.0;
    for (ItemCompra item : listaCompra) {
      total += item.getPrecoTotal();
    }
    return total;
  }
}
