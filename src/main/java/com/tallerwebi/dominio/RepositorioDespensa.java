package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioDespensa {
  List<ItemDespensa> obtenerAlacenaDelUsuario(Usuario usuario);
 
  void guardarOActualizarDespensa(ItemDespensa item);

  void eliminarItemDespensa(String email, ItemDespensa itemDespensa);
}
