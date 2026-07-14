package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioDespensa {
  List<ItemDespensa> obtenerDespensaDelUsuario(Usuario usuario);

  void guardarOActualizarDespensa(ItemDespensa item);

  void eliminarItemDespensa(Long id);

  void agregarItemDespensa(Usuario usuario, Alimento alimento, Double cantidad);

  void agregarItemDespensaNuevo(Usuario usuario, ItemDespensa itemDespensa);

  ItemDespensa obtenerItemDespensaPorId(Long id);
}
