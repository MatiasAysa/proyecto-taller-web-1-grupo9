package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioListaDeComprasImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioListaComprasImpl implements ServicioListaCompras {

  private final double CANTIDAD_KILO_A_GRAMOS = 1000.0;
  private RepositorioListaDeComprasImpl repositorioListaDeCompras;

  @Autowired
  public ServicioListaComprasImpl(RepositorioListaDeComprasImpl repositorioListaDeCompras) {
    this.repositorioListaDeCompras = repositorioListaDeCompras;
  }

  @Override
  public List<ItemCompra> generarListaCompras(List<Comida> Comidas) {
    List<ItemCompra> listaFinalCompra = new ArrayList<>();

    for (Comida comida : Comidas) {
      for (Ingrediente ingrediente : comida.getIngredientes()) {
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

  //CAMBIAR A PRIVAATO ESTE METODO EN UN FUTURO
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
  public void calcularPreciosParaCadaAlimento(List<ItemCompra> listaCompra) {
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

  @Override
  public List<Comida> obtenerComidas() {
    return this.repositorioListaDeCompras.obtenerListaComidas();
  }
}
